package normchan.crapssim.evolve.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.bets.AnySeven;
import normchan.crapssim.engine.bets.Come;
import normchan.crapssim.engine.bets.Field;
import normchan.crapssim.engine.bets.FixedNumberBet;
import normchan.crapssim.engine.bets.PassLine;
import normchan.crapssim.engine.bets.PassOrCome;
import normchan.crapssim.evolve.CrapsEvolver;
import normchan.crapssim.simulation.strategy.ProgressiveRollStrategy;

/**
 * This is the strategy that is used by the evolution components and populated with random values to compete in a bucket of 
 *  EvolveStrategies.  Winning EvolveStrategies are bred and tested in order to find the most profitable strategy.
 * 
 * @author bknapp
 *
 */
@AutoProperty
public class EvolveStrategy extends ProgressiveRollStrategy {


	public int PASS_LINE_BET = 5;

	public int PLAY4 = 0;
	public int PLAY5 = 0;
	public int PLAY6 = 0;
	public int PLAY8 = 0;
	public int PLAY9 = 0;
	public int PLAY10 = 0;

	public int PASS_LINE_ODDS_BET4 = 15;
	public int PASS_LINE_ODDS_BET5 = 10;
	public int PASS_LINE_ODDS_BET6 = 20;
	public int PASS_LINE_ODDS_BET8 = 20;
	public int PASS_LINE_ODDS_BET9 = 10;
	public int PASS_LINE_ODDS_BET10 = 15;

	public int BET_COME_AMT_NONE = 5;
	public int BET_COME_AMT_ONE = 15;
	public int BET_COME_AMT_TWO = 15;
	public int BET_COME_AMT_THREE = 5;
	public int BET_COME_AMT_FOUR = 5;
	public int BET_COME_AMT_FIVE = 0;

	public int ODDS_COME4 = 15;
	public int ODDS_COME5 = 10;
	public int ODDS_COME6 = 20;
	public int ODDS_COME8 = 20;
	public int ODDS_COME9 = 10;
	public int ODDS_COME10 = 15;

	public int BET_FIELD_1_ON_COME = 0;
	public int BET_FIELD_2_ON_COME = 0;
	public int BET_FIELD_3_ON_COME = 0;
	public int BET_FIELD_4_ON_COME = 0;
	public int BET_FIELD_5_ON_COME = 0;
	
	public int ANY_SEVEN_MIN_AMOUNT_AT_RISK = 120;
	public int ANY_SEVEN_BET =5;
	

	public EvolveStrategy(GameManager manager) {
		super(manager, 0);

	}

	public void init(Random rng) {

		this.PASS_LINE_BET = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
		// always need a pass line bet
		this.PASS_LINE_BET = this.PASS_LINE_BET <= GameManager.MIN_BET ? 5 : this.PASS_LINE_BET;

		this.BET_COME_AMT_NONE = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
		if (BET_COME_AMT_NONE > 0) {
			this.BET_COME_AMT_ONE = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
			this.BET_FIELD_1_ON_COME = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
			if (BET_COME_AMT_ONE > 0) {
				this.BET_COME_AMT_TWO = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
				this.BET_FIELD_2_ON_COME = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));

				if (BET_COME_AMT_TWO > 0) {
					this.BET_COME_AMT_THREE = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
					this.BET_FIELD_3_ON_COME = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));

					if (BET_COME_AMT_THREE > 0) {
						this.BET_COME_AMT_FOUR = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
						this.BET_FIELD_4_ON_COME = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));

						if (BET_COME_AMT_FOUR > 0) {
							this.BET_COME_AMT_FIVE = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
							this.BET_FIELD_5_ON_COME = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));

						}
					}
				}
			}
		}
		Optional<Integer> minComeGreaterThan0 = min(Arrays.asList(BET_COME_AMT_NONE, BET_COME_AMT_ONE, BET_COME_AMT_TWO,
				BET_COME_AMT_THREE, BET_COME_AMT_FOUR, BET_COME_AMT_FIVE));

		this.PLAY4 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
		this.PLAY5 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
		this.PLAY6 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
		this.PLAY8 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
		this.PLAY9 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
		this.PLAY10 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));

		if (minComeGreaterThan0.isPresent()) {
			this.ODDS_COME4 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
					PassOrCome.getMaxOddsBet(4, minComeGreaterThan0.get()));
			this.ODDS_COME5 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
					PassOrCome.getMaxOddsBet(5, minComeGreaterThan0.get()));
			this.ODDS_COME6 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
					PassOrCome.getMaxOddsBet(6, minComeGreaterThan0.get()));
			this.ODDS_COME8 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
					PassOrCome.getMaxOddsBet(8, minComeGreaterThan0.get()));
			this.ODDS_COME9 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
					PassOrCome.getMaxOddsBet(9, minComeGreaterThan0.get()));
			this.ODDS_COME10 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
					PassOrCome.getMaxOddsBet(10, minComeGreaterThan0.get()));
		}
		this.PASS_LINE_ODDS_BET4 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
				PassOrCome.getMaxOddsBet(4, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET5 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
				PassOrCome.getMaxOddsBet(5, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET6 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
				PassOrCome.getMaxOddsBet(6, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET8 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
				PassOrCome.getMaxOddsBet(8, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET9 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
				PassOrCome.getMaxOddsBet(9, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET10 = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET),
				PassOrCome.getMaxOddsBet(10, PASS_LINE_BET));
		
		ANY_SEVEN_MIN_AMOUNT_AT_RISK = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
		ANY_SEVEN_BET = safeRandom(rng.nextInt(CrapsEvolver.MAX_BET));
	}

	private Optional<Integer> min(List<Integer> iList) {
		Integer min = null;
		for (int val : iList) {
			if (val == 0) {
				continue;
			}
			if (min == null || val < min) {
				min = val;
			}
		}
		return Optional.ofNullable(min);
	}

	public void validate() {

		if (BET_COME_AMT_NONE == 0) {
			this.BET_COME_AMT_ONE = 0;
			this.BET_COME_AMT_TWO = 0;
			this.BET_COME_AMT_THREE = 0;
			this.BET_COME_AMT_FOUR = 0;
			this.BET_COME_AMT_FIVE = 0;
			this.BET_FIELD_1_ON_COME = 0;
			this.BET_FIELD_2_ON_COME = 0;
			this.BET_FIELD_3_ON_COME = 0;
			this.BET_FIELD_4_ON_COME = 0;
			this.BET_FIELD_5_ON_COME = 0;
			
		} else if (BET_COME_AMT_ONE == 0) {
			this.BET_COME_AMT_TWO = 0;
			this.BET_COME_AMT_THREE = 0;
			this.BET_COME_AMT_FOUR = 0;
			this.BET_COME_AMT_FIVE = 0;
			this.BET_FIELD_2_ON_COME = 0;
			this.BET_FIELD_3_ON_COME = 0;
			this.BET_FIELD_4_ON_COME = 0;
			this.BET_FIELD_5_ON_COME = 0;
			
		} else if (BET_COME_AMT_TWO == 0) {
			this.BET_COME_AMT_THREE = 0;
			this.BET_COME_AMT_FOUR = 0;
			this.BET_COME_AMT_FIVE = 0;
			this.BET_FIELD_3_ON_COME = 0;
			this.BET_FIELD_4_ON_COME = 0;
			this.BET_FIELD_5_ON_COME = 0;
			
		} else if (BET_COME_AMT_THREE == 0) {
			this.BET_COME_AMT_FOUR = 0;
			this.BET_COME_AMT_FIVE = 0;
			
			this.BET_FIELD_4_ON_COME = 0;
			this.BET_FIELD_5_ON_COME = 0;
			
		} else if (BET_COME_AMT_FOUR == 0) {
			this.BET_COME_AMT_FIVE = 0;
			this.BET_FIELD_5_ON_COME = 0;
			
		}

		Optional<Integer> minComeGreaterThan0 = min(Arrays.asList(BET_COME_AMT_NONE, BET_COME_AMT_ONE, BET_COME_AMT_TWO,
				BET_COME_AMT_THREE, BET_COME_AMT_FOUR, BET_COME_AMT_FIVE));

		if (minComeGreaterThan0.isPresent()) {
			this.ODDS_COME4 = safeRandom(ODDS_COME4, PassOrCome.getMaxOddsBet(4, minComeGreaterThan0.get()));
			this.ODDS_COME5 = safeRandom(ODDS_COME5, PassOrCome.getMaxOddsBet(5, minComeGreaterThan0.get()));
			this.ODDS_COME6 = safeRandom(ODDS_COME6, PassOrCome.getMaxOddsBet(6, minComeGreaterThan0.get()));
			this.ODDS_COME8 = safeRandom(ODDS_COME8, PassOrCome.getMaxOddsBet(8, minComeGreaterThan0.get()));
			this.ODDS_COME9 = safeRandom(ODDS_COME9, PassOrCome.getMaxOddsBet(9, minComeGreaterThan0.get()));
			this.ODDS_COME10 = safeRandom(ODDS_COME10, PassOrCome.getMaxOddsBet(10, minComeGreaterThan0.get()));
		}
		this.PASS_LINE_ODDS_BET4 = safeRandom(PASS_LINE_ODDS_BET4, PassOrCome.getMaxOddsBet(4, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET5 = safeRandom(PASS_LINE_ODDS_BET5, PassOrCome.getMaxOddsBet(5, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET6 = safeRandom(PASS_LINE_ODDS_BET6, PassOrCome.getMaxOddsBet(6, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET8 = safeRandom(PASS_LINE_ODDS_BET8, PassOrCome.getMaxOddsBet(8, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET9 = safeRandom(PASS_LINE_ODDS_BET9, PassOrCome.getMaxOddsBet(9, PASS_LINE_BET));
		this.PASS_LINE_ODDS_BET10 = safeRandom(PASS_LINE_ODDS_BET10, PassOrCome.getMaxOddsBet(10, PASS_LINE_BET));

	}

	protected int safeRandom(int betAmout, int maxOddsBet) {
		int amt = betAmout <= maxOddsBet ? betAmout : maxOddsBet;
		return amt;
	}

	private int safeRandom(int nextInt) {
		int val = nextInt < GameManager.MIN_BET ? 0 : nextInt;
	//	return val;
		 return 5 * (Math.round(val / 5));
	}

	@Override
	public void bet() {
		try {
			if (!layout.isNumberEstablished()) {
				handleAnySeven(layout);
				addBet(new PassLine(layout, player, PASS_LINE_BET));
			} else {
				handlePassLineOdds(layout);
				addPlaceBets(layout);
				handleComeBet(layout);
				handleComeOdds(layout);
				// loser bet
				handleFieldBet(layout);
		
			}
			beforeRoll();
		} catch (Exception e) {
			e.printStackTrace();
			this.getPlayer().setBalance(0);
		}
	}

	private void handleAnySeven(Layout layout) {
		
		Double amountAtRisk = layout.getBets().stream().mapToDouble(s -> s.getTotalAmount()).sum();
		if(this.ANY_SEVEN_MIN_AMOUNT_AT_RISK < amountAtRisk) {
			layout.addBet(new AnySeven(layout, player, ANY_SEVEN_BET));
		}
		
	}

	private void handleComeOdds(Layout layout) {
		comeBetsWithoutOdds(layout).stream().forEach(s -> addComeOdds(s));

	}

	private void addComeOdds(Come come) {
		if (come.getOddsBet() > 0) {
			return;
		}
		try {
			switch (come.getNumber()) {
			case 4:
				come.updateOddsBet(ODDS_COME4);
				break;
			case 5:
				come.updateOddsBet(ODDS_COME5);
				break;
			case 6:
				come.updateOddsBet(ODDS_COME6);
				break;
			case 8:
				come.updateOddsBet(ODDS_COME8);
				break;
			case 9:
				come.updateOddsBet(ODDS_COME9);
				break;
			case 10:
				come.updateOddsBet(ODDS_COME10);
				break;

			}
		} catch (Exception e) {
			System.out.println("Failed to add odds to Come: " + come);
			e.printStackTrace();
		}
	}

	private void handleFieldBet(Layout layout) {

		Long totalComeBet = totalComeBetsCount(layout);

		switch (totalComeBet.intValue()) {
		case 1:
			addFieldBet(layout, BET_FIELD_1_ON_COME);
			break;
		case 2:
			addFieldBet(layout, BET_FIELD_2_ON_COME);
			break;
		case 3:
			addFieldBet(layout, BET_FIELD_3_ON_COME);
			break;
		case 4:
			addFieldBet(layout, BET_FIELD_4_ON_COME);
			break;
		case 5:
			addFieldBet(layout, BET_FIELD_5_ON_COME);
			break;

		}

	}

	private void addFieldBet(Layout layout, int amount){
		if(amount > 0) {
		layout.addBet(new Field(layout, player, amount));
		}
	}

	private Integer totalComeBetsSum(Layout layout) {
		return layout.getBets().stream().filter(s -> s instanceof Come).map(s -> (Come) s).mapToInt(s -> s.getMainBet())
				.sum();

	}

	private Long totalComeBetsCount(Layout layout) {
		return layout.getBets().stream().filter(s -> s instanceof Come).map(s -> (Come) s).count();

	}

	private List<Come> comeBetsWithoutOdds(Layout layout) {
		return layout.getBets().stream().filter(s -> s instanceof Come).map(s -> (Come) s)
				.filter(s -> s.getNumber() > 0).filter(s -> s.getMainBet() > 0).filter(s -> s.getOddsBet() == 0)
				.collect(Collectors.toList());

	}

	private void handleComeBet(Layout layout) {
		Long totalComeBetsCount = totalComeBetsCount(layout);

		int newBet = 0;

		switch (totalComeBetsCount.intValue()) {

		case 0:
			newBet = BET_COME_AMT_NONE;
			break;
		case 1:
			newBet = BET_COME_AMT_ONE;
			break;
		case 2:
			newBet = BET_COME_AMT_TWO;
			break;
		case 3:
			newBet = BET_COME_AMT_THREE;
			break;
		case 4:
			newBet = BET_COME_AMT_FOUR;
			break;
		case 5:
			newBet = BET_COME_AMT_FIVE;
			break;

		}

		if (newBet == 0) {
			return;
		}

		layout.addBet(new Come(layout, player, newBet));

	}

	private void handlePassLineOdds(Layout layout) {

		Optional<PassLine> bet = layout.getBets().stream().filter(s -> s instanceof PassLine).map(s -> (PassLine) s)
				.filter(s -> s.getOddsBet() == 0).findFirst();
		if (bet.isPresent()) {
			switch (bet.get().getNumber()) {

			case 4:
				bet.get().updateOddsBet(PASS_LINE_ODDS_BET4);
				break;
			case 5:
				bet.get().updateOddsBet(PASS_LINE_ODDS_BET5);
				break;
			case 6:
				bet.get().updateOddsBet(PASS_LINE_ODDS_BET6);
				break;
			case 8:
				bet.get().updateOddsBet(PASS_LINE_ODDS_BET8);
				break;
			case 9:
				bet.get().updateOddsBet(PASS_LINE_ODDS_BET9);
				break;
			case 10:
				bet.get().updateOddsBet(PASS_LINE_ODDS_BET10);
				break;
			}

		}

	}

	private void addPlaceBets(Layout layout) {
		if (PLAY4 > 0 && layout.getNumber() != 4 && !layout.getBets().stream().filter(s -> s instanceof FixedNumberBet)
				.map(s -> (FixedNumberBet) s).filter(s -> s.getNumber() == 4).findFirst().isPresent()) {
			handlePlaceBet(4, PLAY4);
		}
		if (PLAY5 > 0 && layout.getNumber() != 5 && !layout.getBets().stream().filter(s -> s instanceof FixedNumberBet)
				.map(s -> (FixedNumberBet) s).filter(s -> s.getNumber() == 5).findFirst().isPresent()) {
			handlePlaceBet(5, PLAY5);
		}
		if (PLAY6 > 0 && layout.getNumber() != 6 && !layout.getBets().stream().filter(s -> s instanceof FixedNumberBet)
				.map(s -> (FixedNumberBet) s).filter(s -> s.getNumber() == 6).findFirst().isPresent()) {
			handlePlaceBet(6, PLAY6);
		}
		if (PLAY8 > 0 && layout.getNumber() != 8 && !layout.getBets().stream().filter(s -> s instanceof FixedNumberBet)
				.map(s -> (FixedNumberBet) s).filter(s -> s.getNumber() == 8).findFirst().isPresent()) {
			handlePlaceBet(8, PLAY8);
		}
		if (PLAY9 > 0 && layout.getNumber() != 9 && !layout.getBets().stream().filter(s -> s instanceof FixedNumberBet)
				.map(s -> (FixedNumberBet) s).filter(s -> s.getNumber() == 9).findFirst().isPresent()) {
			handlePlaceBet(9, PLAY9);
		}
		if (PLAY10 > 0 && layout.getNumber() != 10
				&& !layout.getBets().stream().filter(s -> s instanceof FixedNumberBet).map(s -> (FixedNumberBet) s)
						.filter(s -> s.getNumber() == 10).findFirst().isPresent()) {
			handlePlaceBet(10, PLAY10);
		}

	}

	@Override
	protected void beforeRoll() {

	}

	@Override
	public boolean equals(Object other) {
		return Pojomatic.equals(this, other);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

}
