package normchan.crapssim.evolve.components;

import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import normchan.crapssim.engine.GameManager;

public class CrapsEvaluator implements FitnessEvaluator<CrapsPredictorCreature> {

	@Override
	public double getFitness(CrapsPredictorCreature candidate, List<? extends CrapsPredictorCreature> population) {

		double fitness  =  candidate.getStrategy().getPlayer().getPlayerTracker().getStats().getMean() / candidate.getStrategy().getPlayer().getPlayerTracker().getStats().getStdDeviation();

		
		if(candidate.getStrategy().getPlayer().getPlayerTracker().getStats().getMean() < GameManager.INITIAL_BALANCE-50) {
			return fitness / 2;
		}
		//return a consistent winner.  high balances with low standard deviations.  Simulation count should be fairly meaningless until you get thousands of runs
		return fitness + candidate.getStrategy().getPlayer().getPlayerTracker().getSimulationCount()/5000;


	}

	/**
	 * higher scores are more fit when true.
	 */
	@Override
	public boolean isNatural() {
		return true;
	}

}
