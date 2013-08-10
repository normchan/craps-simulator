package normchan.crapssim.simulation.strategy;

import java.util.Observable;
import java.util.Observer;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.bets.HardWay;
import normchan.crapssim.engine.bets.Place;

public abstract class PlayerStrategy extends Observable implements Observer {
	protected Player player;
	protected Layout layout;
	
	public PlayerStrategy(Player player, Layout layout) {
		super();
		this.player = player;
		this.layout = layout;
	}
	
	protected void handlePlaceBet(int number, int amount) {
		Place existing = layout.getPlaceOn(number);
		if (existing != null) {
			if (layout.getNumber() == number || layout.getComeOn(number) != null) {
				existing.retractBet();
			} else {
				existing.updateBet(amount);
			}
		} else {
			if (layout.getNumber() != number && layout.getComeOn(number) == null) {
//				System.out.println("Making new $"+amount+" place bet on "+number+".");
				layout.addBet(new Place(layout, player, amount, number));
			}
		}
	}
	
	protected void handleHardWayBet(int number, int amount) {
		HardWay existing = layout.getHardWayOn(number);
		if (existing != null) {
			existing.updateBet(amount);
		} else {
			layout.addBet(new HardWay(layout, player, amount, number));
		}
	}
	
	public void update(Observable o, Object arg) {
	}
	
	public abstract void bet();
}
