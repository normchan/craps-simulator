package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.bets.Field;

public class FieldStrategy extends PlayerStrategy {

	public FieldStrategy(Player player, Layout layout) {
		super(player, layout);
	}

	@Override
	public void bet() {
//		System.out.println("Making new $10 field bet.");
		layout.addBet(new Field(layout, player, 10));
	}

}
