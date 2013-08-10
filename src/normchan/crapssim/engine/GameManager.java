package normchan.crapssim.engine;

import normchan.crapssim.engine.exception.BankruptException;
import normchan.crapssim.simulation.Controller;
import normchan.crapssim.simulation.strategy.ComePassStrategy;
import normchan.crapssim.simulation.strategy.DontStrategy;
import normchan.crapssim.simulation.strategy.FieldStrategy;
import normchan.crapssim.simulation.strategy.MaxStrategy;
import normchan.crapssim.simulation.strategy.OptimalStrategy1;
import normchan.crapssim.simulation.strategy.OptimalStrategy2;
import normchan.crapssim.simulation.strategy.SuckerStrategy;

public class GameManager {
	private static int INITIAL_BALANCE = 1000;
	public static int MIN_BET = 5;
	public static int MAX_BET = 2000;

	private final Controller controller;
	private final Player player;
	private final Layout layout;
	
	private boolean logGameDetails = true;
	
	public GameManager(Controller controller) {
		this.controller = controller;
		this.layout = new Layout();
		this.player = new Player(INITIAL_BALANCE);

		Logger logger = new Logger(this);
		this.layout.addObserver(logger);
		this.player.addObserver(logger);
		
//		player.setStrategy(new MaxStrategy(player, layout));
//		player.setStrategy(new SuckerStrategy(player, layout));
//		player.setStrategy(new FieldStrategy(player, layout));
//		player.setStrategy(new ComePassStrategy(player, layout));
		player.setStrategy(new OptimalStrategy2(player, layout));
	}
	
	public void run() {
		try {
			while (!controller.isSimulationComplete() && !player.isBroke()) {
				player.makeBet();
				layout.roll();
			}
		} catch (BankruptException e) {
			// TODO: handle this in the course of play and just stop betting and let the rolls play out
			player.setBalance(0);
		}
		
		if (player.isBroke()) {
			System.out.println("Player is broke!");
		} else {
			System.out.println("Player balance is $"+player.getBalance());
		}
	}
	
	public void resetPlayer() {
		player.setBalance(INITIAL_BALANCE);
	}

	public Player getPlayer() {
		return player;
	}

	public Layout getLayout() {
		return layout;
	}

	public boolean isLogGameDetails() {
		return logGameDetails;
	}

	public void setLogGameDetails(boolean logGameDetails) {
		this.logGameDetails = logGameDetails;
	}
}
