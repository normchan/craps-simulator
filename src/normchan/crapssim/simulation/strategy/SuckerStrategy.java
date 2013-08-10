package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.bets.Field;
import normchan.crapssim.engine.bets.PassLine;

public class SuckerStrategy extends PlayerStrategy {

	public SuckerStrategy(Player player, Layout layout) {
		super(player, layout);
	}

	@Override
	public void bet() {
		if (!layout.isNumberEstablished()) {
//			System.out.println("Making new $5 pass line bet.");
			layout.addBet(new PassLine(layout, player, 5));
		} else {
			handlePlaceBet(4, 20);
			handlePlaceBet(10, 20);
		}
		
//		System.out.println("Making new $20 field bet.");
		layout.addBet(new Field(layout, player, 20));
	}

}
