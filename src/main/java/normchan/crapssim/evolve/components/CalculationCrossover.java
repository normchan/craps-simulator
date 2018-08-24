package normchan.crapssim.evolve.components;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import normchan.crapssim.evolve.strategy.EvolveStrategy;

/**
 * Variable-point (fixed or random) cross-over for String candidates. This
 * implementation assumes that all candidate Strings are the same length. If
 * they are not, an exception will be thrown at runtime.
 * 
 * @author Daniel Dyer
 */
public class CalculationCrossover extends AbstractCrossover<CrapsPredictorCreature> {
	/**
	 * Default is single-point cross-over, applied to all parents.
	 */
	public CalculationCrossover() {
		this(1);
	}

	/**
	 * Cross-over with a fixed number of cross-over points.
	 * 
	 * @param crossoverPoints The constant number of cross-over points to use for
	 *                        all cross-over operations.
	 */
	public CalculationCrossover(int crossoverPoints) {
		super(crossoverPoints);
	}

	/**
	 * Cross-over with a fixed number of cross-over points. Cross-over may or may
	 * not be applied to a given pair of parents depending on the
	 * {@code crossoverProbability}.
	 * 
	 * @param crossoverPoints      The constant number of cross-over points to use
	 *                             for all cross-over operations.
	 * @param crossoverProbability The probability that, once selected, a pair of
	 *                             parents will be subjected to cross-over rather
	 *                             than being copied, unchanged, into the output
	 *                             population. Must be in the range
	 *                             {@literal 0 < crossoverProbability <= 1}
	 */
	public CalculationCrossover(int crossoverPoints, Probability crossoverProbability) {
		super(crossoverPoints, crossoverProbability);
	}

	/**
	 * Cross-over with a variable number of cross-over points.
	 * 
	 * @param crossoverPointsVariable A random variable that provides a number of
	 *                                cross-over points for each cross-over
	 *                                operation.
	 */
	public CalculationCrossover(NumberGenerator<Integer> crossoverPointsVariable) {
		super(crossoverPointsVariable);
	}

	/**
	 * Cross-over with a variable number of cross-over points. Cross-over may or may
	 * not be applied to a given pair of parents depending on the
	 * {@code crossoverProbability}.
	 * 
	 * @param crossoverPointsVariable A random variable that provides a number of
	 *                                cross-over points for each cross-over
	 *                                operation.
	 * @param crossoverProbability    The probability that, once selected, a pair of
	 *                                parents will be subjected to cross-over rather
	 *                                than being copied, unchanged, into the output
	 *                                population. Must be in the range
	 *                                {@literal 0 < crossoverProbability <= 1}
	 */
	public CalculationCrossover(NumberGenerator<Integer> crossoverPointsVariable,
			NumberGenerator<Probability> crossoverProbability) {
		super(crossoverPointsVariable, crossoverProbability);
	}

	protected List<CrapsPredictorCreature> mate(CrapsPredictorCreature parent1, CrapsPredictorCreature parent2,
			int numberOfCrossoverPoints, Random rng) {

		// TODO use numberOfCrosserPoints
		CrapsPredictorCreature child = new CandidateFactory().generateRandomCandidate(rng);

		Field[] fields = EvolveStrategy.class.getDeclaredFields();

		EvolveStrategy strategy = child.getStrategy();

		if (rng.nextBoolean()) {
			// init all fields with random, otherwise all 0s
			strategy.init(rng);
		}

		// populate fields from parents
		for (int i = 0; i < numberOfCrossoverPoints; i++) {
			int randomField = rng.nextInt(numberOfCrossoverPoints);
			if (fields.length > randomField) {
				Field intField = fields[randomField];
				try {
					int parentValue = EvolveStrategy.class.getField(intField.getName())
							.getInt(randomParent(parent1, parent2, rng.nextBoolean()).getStrategy());

					intField.set(strategy, parentValue);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		}
		strategy.validate();

		return Arrays.asList(child);
	}

	private CrapsPredictorCreature randomParent(CrapsPredictorCreature parent1, CrapsPredictorCreature parent2,
			boolean choose) {
		return choose ? parent1 : parent2;
	}

}