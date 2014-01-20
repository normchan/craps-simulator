package normchan.crapssim.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.Come;
import normchan.crapssim.engine.bets.HardWay;
import normchan.crapssim.engine.bets.NumberBet;
import normchan.crapssim.engine.bets.PassLine;
import normchan.crapssim.engine.bets.PassOrCome;
import normchan.crapssim.engine.bets.Place;
import normchan.crapssim.engine.event.GameEvent;
import normchan.crapssim.engine.event.NewBetEvent;
import normchan.crapssim.engine.event.SevenOutEvent;

public class Layout extends Observable {
	private List<Bet> bets;
	private final Dice dice;
	private int number = 0;

	public Layout(Dice dice) {
		super();
		this.bets = new ArrayList<Bet>();
		this.dice = dice == null ? new Dice() : dice;
	}
	
	@Override
	public synchronized void addObserver(Observer o) {
		dice.addObserver(o);
		super.addObserver(o);
	}

	public void roll() {
		listBets();
		dice.roll();
		if (isNumberEstablished() && dice.getTotal() == 7) {
			setChanged();
			notifyObservers(new SevenOutEvent("Seven Out!"));
		}
		
		Iterator<Bet> iterator = bets.iterator();
		while (iterator.hasNext()) {
			Bet bet = iterator.next();
			if (bet.diceRolled()) {
				iterator.remove();
			}
		}

		updateState();
	}
	
	private void updateState() {
		if (number == 0) {
			if ((dice.getTotal() >= 4 && dice.getTotal() <= 6) ||
				(dice.getTotal() >= 8 && dice.getTotal() <= 10))
				number = dice.getTotal();
		} else {
			if (dice.getTotal() == 7 || dice.getTotal() == number)
				number = 0;
		}
		
		String message = number == 0 ? "Coming out roll!" : "The point is "+number+"!";
		setChanged();
		notifyObservers(new GameEvent("All bets paid. "+message+"\n"));
	}
	
	private void listBets() {
		String message = "All bets:\n";
		for (Bet bet : bets) {
			message += bet + "\n";
		}
		setChanged();
		notifyObservers(new GameEvent(message));
	}
	
	public List<Bet> getBets() {
		return bets;
	}
	
	public int getAmountAtRisk() {
		int amount = 0;
		for (Bet bet : bets) {
			amount += bet.getTotalAmount();
		}
		return amount;
	}
	
	public PassLine getPassLine() {
		for (Bet bet : bets) {
			if (bet instanceof PassLine) return (PassLine)bet;
		}
		return null;
	}
	
	public NumberBet getNumberBetOn(Class clazz, int number) {
		for (Bet bet : bets) {
			if (clazz.isInstance(bet)) {
				if (((NumberBet)bet).getNumber() == number) {
					return (NumberBet)bet;
				}
			}
		}
		return null;
	}
	
	public Place getPlaceOn(int number) {
		return (Place)getNumberBetOn(Place.class, number);
	}
	
	public PassOrCome getPassOrComeOn(int number) {
		return (PassOrCome)getNumberBetOn(PassOrCome.class, number);
	}
	
	public Come getComeOn(int number) {
		return (Come)getNumberBetOn(Come.class, number);
	}
	
	public HardWay getHardWayOn(int number) {
		return (HardWay)getNumberBetOn(HardWay.class, number);
	}
	
	public void addBet(Bet bet) {
		bets.add(bet);
		setChanged();
		notifyObservers(new NewBetEvent(bet, "Making new "+bet));
	}
	
	public void removeBet(Bet bet) {
		bet.deleteObservers();
		bets.remove(bet);
	}

	public Dice getDice() {
		return dice;
	}
	
	public boolean isNumberEstablished() {
		return number != 0;
	}
	
	public int getNumber() {
		return number;
	}
}
