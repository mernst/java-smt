/*
 *  JavaSMT is an API wrapper for a collection of SMT solvers.
 *  This file is part of JavaSMT.
 *
 *  Copyright (C) 2007-2019  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.sosy_lab.java_smt.solvers.cvc4;

import com.google.common.collect.ImmutableList;
import edu.nyu.acsys.CVC4.BitVector;
import edu.nyu.acsys.CVC4.Expr;
import edu.nyu.acsys.CVC4.ExprManager;
import edu.nyu.acsys.CVC4.FloatingPoint;
import edu.nyu.acsys.CVC4.FloatingPointConvertSort;
import edu.nyu.acsys.CVC4.FloatingPointSize;
import edu.nyu.acsys.CVC4.FloatingPointToFPFloatingPoint;
import edu.nyu.acsys.CVC4.FloatingPointToSBV;
import edu.nyu.acsys.CVC4.Kind;
import edu.nyu.acsys.CVC4.RoundingMode;
import edu.nyu.acsys.CVC4.Type;
import java.math.BigDecimal;
import org.sosy_lab.java_smt.api.FloatingPointRoundingMode;
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.api.FormulaType.BitvectorType;
import org.sosy_lab.java_smt.api.FormulaType.FloatingPointType;
import org.sosy_lab.java_smt.basicimpl.AbstractFloatingPointFormulaManager;

public class CVC4FloatingPointFormulaManager
    extends AbstractFloatingPointFormulaManager<Expr, Type, ExprManager, Expr> {

  private final ExprManager exprManager;
  private final Expr roundingMode;

  protected CVC4FloatingPointFormulaManager(
      CVC4FormulaCreator pCreator, FloatingPointRoundingMode pFloatingPointRoundingMode) {
    super(pCreator);
    exprManager = pCreator.getEnv();
    roundingMode = getRoundingModeImpl(pFloatingPointRoundingMode);
  }

  private static FloatingPointSize getFPSize(FloatingPointType pType) {
    long pExponentSize = pType.getExponentSize();
    long pMantissaSize = pType.getMantissaSize();
    FloatingPointSize type = new FloatingPointSize(pExponentSize, pMantissaSize);
    return type;
  }

  @Override
  protected Expr getDefaultRoundingMode() {
    return roundingMode;
  }

  @Override
  protected Expr getRoundingModeImpl(FloatingPointRoundingMode pFloatingPointRoundingMode) {
    switch (pFloatingPointRoundingMode) {
      case NEAREST_TIES_TO_EVEN:
        return exprManager.mkConst(RoundingMode.roundNearestTiesToEven);
      case NEAREST_TIES_AWAY:
        return exprManager.mkConst(RoundingMode.roundNearestTiesToAway);
      case TOWARD_POSITIVE:
        return exprManager.mkConst(RoundingMode.roundTowardPositive);
      case TOWARD_NEGATIVE:
        return exprManager.mkConst(RoundingMode.roundTowardNegative);
      case TOWARD_ZERO:
        return exprManager.mkConst(RoundingMode.roundTowardZero);
      default:
        throw new AssertionError("Unexpected branch");
    }
  }

  @Override
  protected Expr makeNumberImpl(
      double pN, FloatingPointType pType, Expr pFloatingPointRoundingMode) {
    return exprManager.mkConst(
        new FloatingPoint(
            getFPSize(pType),
            pFloatingPointRoundingMode.getConstRoundingMode(),
            new BitVector(64, Double.doubleToLongBits(pN)),
            false));
  }

  @Override
  protected Expr makeNumberImpl(
      BigDecimal pN, FloatingPointType pType, Expr pFloatingPointRoundingMode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  protected Expr makeNumberImpl(
      String pN, FloatingPointType pType, Expr pFloatingPointRoundingMode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  protected Expr makeVariableImpl(String varName, FloatingPointType pType) {
    long pExponentSize = pType.getExponentSize();
    long pMantissaSize = pType.getMantissaSize();
    Type type = exprManager.mkFloatingPointType(pExponentSize, pMantissaSize);
    return exprManager.mkVar(varName, type);
  }

  @Override
  protected Expr makePlusInfinityImpl(FloatingPointType pType) {
    return exprManager.mkConst(FloatingPoint.makeInf(getFPSize(pType), /* sign */ false));
  }

  @Override
  protected Expr makeMinusInfinityImpl(FloatingPointType pType) {
    return exprManager.mkConst(FloatingPoint.makeInf(getFPSize(pType), /* sign */ true));
  }

  @Override
  protected Expr makeNaNImpl(FloatingPointType pType) {
    return exprManager.mkConst(FloatingPoint.makeNaN(getFPSize(pType)));
  }

  @Override
  protected Expr castToImpl(Expr pNumber, FormulaType<?> pTargetType, Expr pRoundingMode) {
    if (pTargetType.isFloatingPointType()) {
      FloatingPointType targetType = (FloatingPointType) pTargetType;
      long pExponentSize = targetType.getExponentSize();
      long pMantissaSize = targetType.getMantissaSize();
      FloatingPointSize fpSize = new FloatingPointSize(pExponentSize, pMantissaSize);
      FloatingPointConvertSort fpConvertSort = new FloatingPointConvertSort(fpSize);
      Expr op = exprManager.mkConst(new FloatingPointToFPFloatingPoint(fpConvertSort));
      return exprManager.mkExpr(op, pRoundingMode, pNumber);

    } else if (pTargetType.isBitvectorType()) {
      BitvectorType targetType = (BitvectorType) pTargetType;
      Expr op = exprManager.mkConst(new FloatingPointToSBV(targetType.getSize()));
      return exprManager.mkExpr(op, pRoundingMode, pNumber);

    } else if (pTargetType.isRationalType()) {
      return exprManager.mkExpr(Kind.FLOATINGPOINT_TO_REAL, pNumber);

    } else {
      return genericCast(pNumber, pTargetType);
    }
  }

  private Expr genericCast(Expr pNumber, FormulaType<?> pTargetType) {
    Type type = pNumber.getType();
    FormulaType<?> argType = getFormulaCreator().getFormulaType(pNumber);
    Expr castFuncDecl =
        getFormulaCreator()
            .declareUFImpl(
                "__cast_" + argType + "_to_" + pTargetType,
                toSolverType(pTargetType),
                ImmutableList.of(type));
    return exprManager.mkExpr(Kind.APPLY_UF, castFuncDecl, pNumber);
  }

  @Override
  protected Expr castFromImpl(
      Expr pNumber, boolean pSigned, FloatingPointType pTargetType, Expr pRoundingMode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  protected Expr negate(Expr pParam1) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_NEG, pParam1);
  }

  @Override
  protected Expr add(Expr pParam1, Expr pParam2, Expr pRoundingMode) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_PLUS, pRoundingMode, pParam1, pParam2);
  }

  @Override
  protected Expr subtract(Expr pParam1, Expr pParam2, Expr pRoundingMode) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_SUB, pRoundingMode, pParam1, pParam2);
  }

  @Override
  protected Expr divide(Expr pParam1, Expr pParam2, Expr pRoundingMode) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_DIV, pRoundingMode, pParam1, pParam2);
  }

  @Override
  protected Expr multiply(Expr pParam1, Expr pParam2, Expr pRoundingMode) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_MULT, pRoundingMode, pParam1, pParam2);
  }

  @Override
  protected Expr assignment(Expr pParam1, Expr pParam2) {
    return exprManager.mkExpr(Kind.EQUAL, pParam1, pParam2);
  }

  @Override
  protected Expr equalWithFPSemantics(Expr pParam1, Expr pParam2) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_EQ, pParam1, pParam2);
  }

  @Override
  protected Expr greaterThan(Expr pParam1, Expr pParam2) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_GT, pParam1, pParam2);
  }

  @Override
  protected Expr greaterOrEquals(Expr pParam1, Expr pParam2) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_GEQ, pParam1, pParam2);
  }

  @Override
  protected Expr lessThan(Expr pParam1, Expr pParam2) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_LT, pParam1, pParam2);
  }

  @Override
  protected Expr lessOrEquals(Expr pParam1, Expr pParam2) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_LEQ, pParam1, pParam2);
  }

  @Override
  protected Expr isNaN(Expr pParam1) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_ISNAN, pParam1);
  }

  @Override
  protected Expr isInfinity(Expr pParam1) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_ISINF, pParam1);
  }

  @Override
  protected Expr isZero(Expr pParam1) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_ISZ, pParam1);
  }

  @Override
  protected Expr isSubnormal(Expr pParam1) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_ISSN, pParam1);
  }

  @Override
  protected Expr isNormal(Expr pParam) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_ISN, pParam);
  }

  @Override
  protected Expr isNegative(Expr pParam) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_ISNEG, pParam);
  }

  @Override
  protected Expr fromIeeeBitvectorImpl(Expr pNumber, FloatingPointType pTargetType) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_FP, pNumber);
  }

  @Override
  protected Expr toIeeeBitvectorImpl(Expr pNumber) {
    return exprManager.mkExpr(Kind.FLOATINGPOINT_TO_FP_IEEE_BITVECTOR, pNumber);
  }

  @Override
  protected Expr round(Expr pFormula, FloatingPointRoundingMode pRoundingMode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }
}
