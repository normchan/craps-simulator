package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.PassLine;

public class PassLineStrategy extends PlayerStrategy {

	public PassLineStrategy(Player player, Layout layout) {
		super(player, layout);
	}

	@Override
	public void bet() {
		if (!layout.isNumberEstablished()) {
			layout.addBet(new PassLine(layout, player, 25));
		} else {
			for (Bet b : layout.getBets()) {
				if (b instanceof PassLine) {
					PassLine bet = (PassLine)b;
					int odds = bet.getMaxOddsBet();
					bet.updateOddsBet(odds);
				}
			}
		}
	}

}
