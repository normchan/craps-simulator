package normchan.crapssim;

import java.util.ArrayList;
import java.util.List;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.LoadedDice;
import normchan.crapssim.simulation.Controller;
import normchan.crapssim.simulation.SimpleController;
import normchan.crapssim.simulation.tracker.PlayerTracker;

public class Simulator {
	private final static int TOTAL_SIMULATION_RUNS = 100;
	private static Controller controller;
	private static GameManager manager;
	private static List<PlayerTracker> trackers = new ArrayList<PlayerTracker>();

	private final static int[][] DICE_SEQUENCE = { {3, 6}, {3, 3}, {5, 4}, {2, 5}, {6, 2}, {4, 4} };
	
	public static void main(String[] args) {
//		controller = new SimpleController();
		controller = new Controller();
		manager = new GameManager(controller, null);
//		manager = new GameManager(controller, new LoadedDice(DICE_SEQUENCE));
//		manager.setLogGameDetails(false);
		initializeTrackers();

		for (int i = 0; i < TOTAL_SIMULATION_RUNS; i++) {
			setup();
			manager.run();
			takedown();
		}
		
		summarizeData();
	}
	
	private static void initializeTrackers() {
		trackers.add(new PlayerTracker());
		for (PlayerTracker tracker : trackers) {
			tracker.setPlayer(manager.getPlayer());
		}
	}
	
	private static void summarizeData() {
		System.out.println(TOTAL_SIMULATION_RUNS+" runs complete with "+manager.getPlayer().getStrategy().getClass().getSimpleName()+".");
		for (PlayerTracker tracker : trackers) {
			tracker.calculateStats();
			tracker.printResults(System.out);
		}
	}
	
	private static void setup() {
		manager.resetPlayer();
		controller.setManager(manager);
		controller.reset();
	}

	private static void takedown() {
		for (PlayerTracker tracker : trackers) {
			tracker.runComplete();
		}
	}
}
