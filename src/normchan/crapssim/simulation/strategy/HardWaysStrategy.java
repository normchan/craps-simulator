package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;

public class HardWaysStrategy extends PlayerStrategy {

	public HardWaysStrategy(Player player, Layout layout) {
		super(player, layout);
	}

	@Override
	public void bet() {
		handleHardWayBet(6, 5);
		handleHardWayBet(8, 5);
		handleHardWayBet(4, 5);
		handleHardWayBet(10, 5);
	}

}
