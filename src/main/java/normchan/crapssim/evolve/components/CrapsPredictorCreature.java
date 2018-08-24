package normchan.crapssim.evolve.components;

import normchan.crapssim.evolve.strategy.EvolveStrategy;
import normchan.crapssim.simulation.tracker.PlayerTracker;

/**
 *
 * @author barry
 */
public class CrapsPredictorCreature {

	private PlayerTracker playerTracker;
	/**
	 * 
	 */
	private EvolveStrategy strategy;



	public CrapsPredictorCreature(EvolveStrategy strategy) {
		this.strategy = strategy;

	}
	
	@Override
	public String toString() {

		return strategy.toString();
	}
	
	public EvolveStrategy getStrategy() {
		return strategy;
	}


	public void setTracker(PlayerTracker tracker) {
		this.playerTracker = tracker;
		
	}

	public PlayerTracker getTracker() {
		return playerTracker;
	}


}
