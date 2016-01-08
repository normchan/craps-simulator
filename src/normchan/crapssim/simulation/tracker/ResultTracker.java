package normchan.crapssim.simulation.tracker;

import java.io.PrintStream;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.event.GameEvent;
import normchan.crapssim.engine.event.NewBetEvent;

public class ResultTracker implements Tracker, Observer {
	private class Stats {
		int winAmount = 0;
		int lossAmount = 0;
	}
	private Stats stats = new Stats();
	
	private Set<Observable> observeds = new HashSet<Observable>();

	public ResultTracker(Layout layout) {
		observeds.add(layout);
		for (Bet bet : layout.getBets()) {
			observeds.add(bet);
		}
		
		for (Observable subject : observeds) {
			subject.addObserver(this);
		}
	}
	
	public void cleanup() {
		printResults(System.out);
		for (Observable subject : observeds) {
			subject.deleteObserver(this);
		}
	}

	@Override
	public void calculateStats() {
	}

	@Override
	public void printResults(PrintStream stream) {
		Formatter formatter = new Formatter(stream);
		formatter.format("Win during observation period: $%d\n", stats.winAmount);
		formatter.format("Loss during observation period: $%d\n", stats.lossAmount);
		formatter.format("Net total: $%d\n\n", stats.winAmount - stats.lossAmount);
		formatter.flush();
		stream.flush();
	}

	public void update(Observable o, Object arg) {
		String message = ((GameEvent)arg).getMessage();
//		System.out.println("ResultTracker: received "+arg.getClass()+": "+message.substring(0, message.length() > 40 ? 40 : message.length()));
		if (arg instanceof NewBetEvent) {
			Bet bet = ((NewBetEvent)arg).getBet();
			observeds.add(bet);
			bet.addObserver(this);
		} else if (arg instanceof BetEvent) {
			BetEvent event = (BetEvent)arg;
			BetEvent.EventType eventType = (event).getType();
//			System.out.println("ResultTracker: received event "+eventType+" for "+(Bet)o);
			if (eventType == BetEvent.EventType.WIN) {
				stats.winAmount += event.getAmount();
			} else if (eventType == BetEvent.EventType.LOSS) {
				stats.lossAmount += event.getAmount();
			}
		}
	}

}
