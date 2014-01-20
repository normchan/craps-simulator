package normchan.crapssim.engine.bets;

import java.util.Observable;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.event.GameEvent;
import normchan.crapssim.engine.exception.InvalidBetException;

public abstract class Bet extends Observable {
	protected final Layout layout;
	protected final Player player;
	protected int mainBet;

	public Bet(Layout layout, Player player, int bet) {
		this.layout = layout;
		this.player = player;
		this.mainBet = bet;
		
		player.deduct(bet);
	}
	
	protected void checkBetLimits() {
		if (mainBet < GameManager.MIN_BET)
			throw new InvalidBetException(getClass().getSimpleName()+" bet is less than the minimum bet.");
		else if (mainBet > GameManager.MAX_BET)
			throw new InvalidBetException(getClass().getSimpleName()+" bet is more than the maximum bet.");
	}
	
	protected void betWon() {
		betWon(mainBet * 2);
	}
	
	protected void betWon(int amount) {
		notifyObservers(new BetEvent(BetEvent.EventType.WIN, this+" won.  Adding $"+amount+" to player balance."));
		player.payOff(amount);
	}
	
	protected void betLost() {
		notifyObservers(new BetEvent(BetEvent.EventType.LOSS, this+" lost."));
	}
	
	protected void betPushed() {
		notifyObservers(new BetEvent(BetEvent.EventType.PUSH, this+" pushed."));
		player.payOff(mainBet);
	}
	
	public int getMainBet() {
		return mainBet;
	}
	
	public int getTotalAmount() {
		return mainBet;
	}

	public void retractBet() {
		notifyObservers(new BetEvent(BetEvent.EventType.RETRACT, "Retracting "+getClass().getSimpleName()+" bet."));
		layout.removeBet(this);
		player.payOff(mainBet);
	}	
	
	public void updateBet(int amount) {
		if (mainBet != amount) {
			notifyObservers(new BetEvent(BetEvent.EventType.UPDATE, "Updating "+this+" to $"+amount));
			player.deduct(amount - this.mainBet);
			this.mainBet = amount;
		}
	}
	
	protected void notifyObservers(GameEvent event) {
		setChanged();
		super.notifyObservers(event);
	}
	
	public String toString() {
		return getClass().getSimpleName()+" bet worth $"+mainBet;
	}
	
	public abstract boolean diceRolled();
}
