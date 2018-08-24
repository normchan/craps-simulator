package normchan.crapssim.evolve;

import java.util.ArrayList;
import java.util.List;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.TruncationSelection;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import normchan.crapssim.evolve.components.CalculationCrossover;
import normchan.crapssim.evolve.components.CalculationFactory;
import normchan.crapssim.evolve.components.CalculationMutation;
import normchan.crapssim.evolve.components.CrapsEvaluator;
import normchan.crapssim.evolve.components.CrapsPredictorCreature;
import normchan.crapssim.evolve.components.CustomEvolutionEngine;


/**
 * RUN ME
 * Simple evolutionary algorithm that evolves a population of randomly-generated
 * Craps betting strategies until at least the best strategy is selected
 */
public final class CrapsEvolver {

	//not using this multithreaded approach
	// threads per cpu
	public static int THREADS_PER_CPU = 2;

	public static int CREATURES_IN_POPULATION = 50000;
	// number of creatures that are copied from the total population to the next generation unchanged. ie take
	// the best 5
	public static int ELITISM = 100;
	
	private static double SELECTION_RATIO = new Double(ELITISM) / new Double(CREATURES_IN_POPULATION);
	
	//any winner will be rerun multiple times so this 1000 although initially insignificant will improve as the strategy is reselected as a winner
	public final static int TOTAL_SIMULATION_RUNS = 1000;
	
	public final static int MAX_BET = 120;
	
	//number of attributes that can be set in the strategy.  crossover points max
	public final static int MAX_CROSSOVER_ATTRIBUTES = 70;
	

	/**
	 */
	public static void main(String[] args) {
		try {

			CrapsEvolver crapsEvolver = new CrapsEvolver();

			CrapsPredictorCreature result = crapsEvolver.evolveCreature();

			System.out.println("Evolution result: " + result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private CrapsPredictorCreature evolveCreature() {

		List<EvolutionaryOperator<CrapsPredictorCreature>> operators = new ArrayList<EvolutionaryOperator<CrapsPredictorCreature>>(2);

		operators.add(new CalculationMutation(0.02d));
		operators.add(new CalculationCrossover());

		EvolutionaryOperator<CrapsPredictorCreature> pipeline = new EvolutionPipeline<CrapsPredictorCreature>(
				operators);
		
		//new RouletteWheelSelection()
		SelectionStrategy selectionStrategy = new TruncationSelection(SELECTION_RATIO);
		
		EvolutionEngine<CrapsPredictorCreature> engine =
				new CustomEvolutionEngine<CrapsPredictorCreature>(new CalculationFactory(), pipeline, new CrapsEvaluator(),
						selectionStrategy, new MersenneTwisterRNG());

		// engine.addEvolutionObserver(new EvolutionLogger());

		return engine.evolve(CREATURES_IN_POPULATION, // 100 individuals in the population.
				ELITISM, // 5% elitism.
				new TargetFitness(0, false));
	}


//	/**
//	 * Trivial evolution observer for displaying information at the end of each
//	 * generation.
//	 */
//	private static class EvolutionLogger implements EvolutionObserver<CrapsPredictorCreature> {
//		public void populationUpdate(PopulationData<CrapsPredictorCreature> data) {
//			// System.out.println("Generation " + data.getGenerationNumber() + ": " +
//			// data.getBestCandidate());
//		}
//	}
}
