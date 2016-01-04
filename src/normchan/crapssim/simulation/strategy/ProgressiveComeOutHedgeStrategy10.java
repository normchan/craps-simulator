package normchan.crapssim.simulation.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.bets.AnyCraps;
import normchan.crapssim.engine.bets.AnySeven;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.Come;
import normchan.crapssim.engine.bets.PassLine;
import normchan.crapssim.engine.bets.PassOrCome;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.event.RollCompleteEvent;

public class ProgressiveComeOutHedgeStrategy10 extends ProgressiveRollStrategy10 {
	private boolean hedgeNeeded = false;
	private boolean hedgeReckoning = false;
	private List<Come> comeBets = null;
	
	public ProgressiveComeOutHedgeStrategy10(Player player, Layout layout) {
		super(player, layout);
	}

	@Override
	protected void beforeRoll() {
		super.beforeRoll();
		if (hedgeNeeded) {
			addBet(new AnySeven(layout, player, unitSize));
			addBet(new AnyCraps(layout, player, 2));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof BetEvent) {
			BetEvent.EventType eventType = ((BetEvent)arg).getType();
			if (hedgeReckoning) {
				System.out.println("ComeOutHedge: received event "+eventType+" for "+(Bet)o);
			}
			
			if (eventType == BetEvent.EventType.POINT_MADE) {
				// Come out roll after making point
				List<Come> comeBets = getComeBets();
//				System.out.println("ComeOutHedge: number of come bets when point made: "+comeBets.size());
				if (comeBets.size() > 1) {
					this.hedgeNeeded = true;
					this.hedgeReckoning = true;
					this.comeBets = comeBets;
					if (layout.getDice().isTrickDice())
						layout.getDice().toggleTrickDice();
					System.out.println("ComeOutHedge: hedging...");
				}
			} else if (hedgeNeeded && o instanceof PassLine && eventType == BetEvent.EventType.NUMBER_ESTABLISHED) {
				this.hedgeNeeded = false;
				this.comeBets = null;
				if (!layout.getDice().isTrickDice())
					layout.getDice().toggleTrickDice();
//				System.out.println("ComeOutHedge: hedge not needed");
			}
		} else if (hedgeReckoning && !hedgeNeeded && arg instanceof RollCompleteEvent) {
			hedgeReckoning = false;
		}
		super.update(o, arg);
	}

	private List<Come> getComeBets() {
		List<Come> comeBets = new ArrayList<Come>();
		for (Bet bet : layout.getBets()) {
			if (bet instanceof Come)
				comeBets.add((Come)bet);
		}
		return comeBets;
	}
}
