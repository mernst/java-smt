package org.sosy_lab.java_smt.solvers.boolector;

public enum BtorOption {
  BTOR_OPT_MODEL_GEN(0),
  BTOR_OPT_INCREMENTAL(1),
  BTOR_OPT_INCREMENTAL_SMT1(2),
  BTOR_OPT_INPUT_FORMAT(3),
  BTOR_OPT_OUTPUT_NUMBER_FORMAT(4),
  BTOR_OPT_OUTPUT_FORMAT(5),
  BTOR_OPT_ENGINE(6),
  BTOR_OPT_SAT_ENGINE(7),
  BTOR_OPT_AUTO_CLEANUP(8),
  BTOR_OPT_PRETTY_PRINT(9),
  BTOR_OPT_EXIT_CODES(10),
  BTOR_OPT_SEED(11),
  BTOR_OPT_VERBOSITY(12),
  BTOR_OPT_LOGLEVEL(13),
  BTOR_OPT_REWRITE_LEVEL(14),
  BTOR_OPT_SKELETON_PREPROC(15),
  BTOR_OPT_ACKERMANN(16),
  BTOR_OPT_BETA_REDUCE_ALL(17),
  BTOR_OPT_ELIMINATE_SLICES(18),
  BTOR_OPT_VAR_SUBST(19),
  BTOR_OPT_UCOPT(20),
  BTOR_OPT_MERGE_LAMBDAS(21),
  BTOR_OPT_EXTRACT_LAMBDAS(22),
  BTOR_OPT_NORMALIZE(23),
  BTOR_OPT_NORMALIZE_ADD(24),
  BTOR_OPT_FUN_PREPROP(25),
  BTOR_OPT_FUN_PRESLS(26),
  BTOR_OPT_FUN_DUAL_PROP(27),
  BTOR_OPT_FUN_DUAL_PROP_QSORT(28),
  BTOR_OPT_FUN_JUST(29),
  BTOR_OPT_FUN_JUST_HEURISTIC(30),
  BTOR_OPT_FUN_LAZY_SYNTHESIZE(31),
  BTOR_OPT_FUN_EAGER_LEMMAS(32),
  BTOR_OPT_FUN_STORE_LAMBDAS(33),
  BTOR_OPT_SLS_NFLIPS(34),
  BTOR_OPT_SLS_STRATEGY(35),
  BTOR_OPT_SLS_JUST(36),
  BTOR_OPT_SLS_MOVE_GW(37),
  BTOR_OPT_SLS_MOVE_RANGE(38),
  BTOR_OPT_SLS_MOVE_SEGMENT(39),
  BTOR_OPT_SLS_MOVE_RAND_WALK(40),
  BTOR_OPT_SLS_PROB_MOVE_RAND_WALK(41),
  BTOR_OPT_SLS_MOVE_RAND_ALL(42),
  BTOR_OPT_SLS_MOVE_RAND_RANGE(43),
  BTOR_OPT_SLS_MOVE_PROP(44),
  BTOR_OPT_SLS_MOVE_PROP_N_PROP(45),
  BTOR_OPT_SLS_MOVE_PROP_N_SLS(46),
  BTOR_OPT_SLS_MOVE_PROP_FORCE_RW(47),
  BTOR_OPT_SLS_MOVE_INC_MOVE_TEST(48),
  BTOR_OPT_SLS_USE_RESTARTS(49),
  BTOR_OPT_SLS_USE_BANDIT(50),
  BTOR_OPT_PROP_NPROPS(51),
  BTOR_OPT_PROP_USE_RESTARTS(52),
  BTOR_OPT_PROP_USE_BANDIT(53),
  BTOR_OPT_PROP_PATH_SEL(54),
  BTOR_OPT_PROP_PROB_USE_INV_VALUE(55),
  BTOR_OPT_PROP_PROB_FLIP_COND(56),
  BTOR_OPT_PROP_PROB_FLIP_COND_CONST(57),
  BTOR_OPT_PROP_FLIP_COND_CONST_DELTA(58),
  BTOR_OPT_PROP_FLIP_COND_CONST_NPATHSEL(59),
  BTOR_OPT_PROP_PROB_SLICE_KEEP_DC(60),
  BTOR_OPT_PROP_PROB_CONC_FLIP(61),
  BTOR_OPT_PROP_PROB_SLICE_FLIP(62),
  BTOR_OPT_PROP_PROB_EQ_FLIP(63),
  BTOR_OPT_PROP_PROB_AND_FLIP(64),
  BTOR_OPT_PROP_NO_MOVE_ON_CONFLICT(65),
  BTOR_OPT_AIGPROP_USE_RESTARTS(66),
  BTOR_OPT_AIGPROP_USE_BANDIT(67),
  BTOR_OPT_QUANT_SYNTH(68),
  BTOR_OPT_QUANT_DUAL_SOLVER(69),
  BTOR_OPT_QUANT_SYNTH_LIMIT(70),
  BTOR_OPT_QUANT_SYNTH_QI(71),
  BTOR_OPT_QUANT_DER(72),
  BTOR_OPT_QUANT_CER(73),
  BTOR_OPT_QUANT_MINISCOPE(74),
  BTOR_OPT_DEFAULT_TO_CADICAL(75),
  BTOR_OPT_SORT_EXP(76),
  BTOR_OPT_SORT_AIG(77),
  BTOR_OPT_SORT_AIGVEC(78),
  BTOR_OPT_AUTO_CLEANUP_INTERNAL(79),
  BTOR_OPT_SIMPLIFY_CONSTRAINTS(80),
  BTOR_OPT_CHK_FAILED_ASSUMPTIONS(81),
  BTOR_OPT_CHK_MODEL(82),
  BTOR_OPT_CHK_UNCONSTRAINED(83),
  BTOR_OPT_PARSE_INTERACTIVE(84),
  BTOR_OPT_SAT_ENGINE_LGL_FORK(85),
  BTOR_OPT_INCREMENTAL_RW(86),
  BTOR_OPT_DECLSORT_BV_WIDTH(87),
  BTOR_OPT_QUANT_SYNTH_ITE_COMPLETE(88),
  BTOR_OPT_QUANT_FIXSYNTH(89);

  private final int value;

  BtorOption(int pValue) {
    value = pValue;
  }

  public final int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return name() + "(" + getValue() + ")";
  }
}
