/*
 *  JavaSMT is an API wrapper for a collection of SMT solvers.
 *  This file is part of JavaSMT.
 *
 *  Copyright (C) 2007-2016  Dirk Beyer
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
package org.sosy_lab.java_smt.solvers.z3;

import com.microsoft.z3.Native;
import org.sosy_lab.java_smt.api.Formula;
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.api.FormulaType.ArrayFormulaType;
import org.sosy_lab.java_smt.basicimpl.AbstractArrayFormulaManager;

class Z3ArrayFormulaManager extends AbstractArrayFormulaManager<Long, Long, Long, Long> {

  private final long z3context;

  Z3ArrayFormulaManager(Z3FormulaCreator creator) {
    super(creator);
    this.z3context = creator.getEnv();
  }

  @Override
  protected Long select(Long pArray, Long pIndex) {
    return Native.mkSelect(z3context, pArray, pIndex);
  }

  @Override
  protected Long store(Long pArray, Long pIndex, Long pValue) {
    return Native.mkStore(z3context, pArray, pIndex, pValue);
  }

  @Override
  @SuppressWarnings("MethodTypeParameterName")
  protected <TI extends Formula, TE extends Formula> Long internalMakeArray(
      String pName, FormulaType<TI> pIndexType, FormulaType<TE> pElementType) {

    final ArrayFormulaType<TI, TE> arrayFormulaType =
        FormulaType.getArrayType(pIndexType, pElementType);
    final Long z3ArrayType = toSolverType(arrayFormulaType);

    return getFormulaCreator().makeVariable(z3ArrayType, pName);
  }

  @Override
  protected Long equivalence(Long pArray1, Long pArray2) {
    return Native.mkEq(z3context, pArray1, pArray2);
  }
}
