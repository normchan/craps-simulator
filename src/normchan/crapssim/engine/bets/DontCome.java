package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.event.GameEvent;

public class DontCome extends Dont {

	public DontCome(Layout layout, Player player, int bet) {
		super(layout, player, bet);
	}

	@Override
	protected void betWon() {
		if (!layout.isNumberEstablished() && oddsBet > 0) // Odds are off by default
			super.betWon(mainBet*2 + oddsBet);
		else
			super.betWon();
	}

	@Override
	protected void betLost() {
		if (!layout.isNumberEstablished() && oddsBet > 0) {
			// Odds are off by default
			notifyObservers(new BetEvent(BetEvent.EventType.LOSS, this+" lost.  Odds are off, returning to player"));
			player.payOff(oddsBet);
		} else
			super.betLost();
	}
}
