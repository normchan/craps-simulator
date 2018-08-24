package normchan.crapssim.evolve.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import normchan.crapssim.evolve.CrapsEvolver;

/**
 * Mutation of individual characters in a string according to some probability.
 */
public class CalculationMutation implements EvolutionaryOperator<CrapsPredictorCreature> {
	private final double mutationProbability;
	CalculationFactory calculationFactory = new CalculationFactory();

	/**
	 * @param alphabet            The permitted values for each character in a
	 *                            string.
	 * @param mutationProbability The probability that a given character is changed.
	 */
	public CalculationMutation(double mutationProbability) {
		if (mutationProbability <= 0 || mutationProbability > 1) {
			throw new IllegalArgumentException(
					"Mutation probability must be greater than " + "zero and less than or equal to one.");
		}
		this.mutationProbability = mutationProbability;
	}

	/**
	 * mutates by taking a target out of somewhere and sticking it on the end
	 * 
	 * @param s
	 * @param rng
	 * @return
	 */
	private CrapsPredictorCreature mutateCalculation(CrapsPredictorCreature s, Random rng) {
		CrapsPredictorCreature mutant = calculationFactory.generateRandomCandidate(rng);

		CalculationCrossover cross = new CalculationCrossover();
		return cross.mate(mutant, s, rng.nextInt(CrapsEvolver.MAX_CROSSOVER_ATTRIBUTES), rng).get(0);

	}

	@Override
	public List<normchan.crapssim.evolve.components.CrapsPredictorCreature> apply(
			List<normchan.crapssim.evolve.components.CrapsPredictorCreature> selectedCandidates, Random rng) {

		List<CrapsPredictorCreature> mutatedPopulation = new ArrayList<CrapsPredictorCreature>(
				selectedCandidates.size());
		for (CrapsPredictorCreature s : selectedCandidates) {
			mutatedPopulation.add((CrapsPredictorCreature) mutateCalculation(s, rng));
		}
		return mutatedPopulation;
	}
}