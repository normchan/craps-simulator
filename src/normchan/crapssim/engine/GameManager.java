package normchan.crapssim.engine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import normchan.crapssim.simulation.strategy.PlayerStrategy;

public class GameManager {
	public static int INITIAL_BALANCE = 5000;
	public static int MIN_BET = 5;
	public static int MAX_BET = 2000;

	private final Player player;
	private final Layout layout;
	
	private boolean logGameDetails = true;
	
	public GameManager(Dice dice, Class<? extends PlayerStrategy> playerStrategyClass) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		this.layout = new Layout(dice);
		this.player = new Player(INITIAL_BALANCE);

		Constructor<? extends PlayerStrategy> constructor = playerStrategyClass.getConstructor(Player.class, Layout.class);
		this.player.setStrategy((PlayerStrategy)constructor.newInstance(player, layout));

		Logger logger = new Logger(this);
		this.layout.addObserver(logger);
		this.player.addObserver(logger);
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
