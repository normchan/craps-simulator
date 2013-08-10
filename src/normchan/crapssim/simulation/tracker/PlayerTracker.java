package normchan.crapssim.simulation.tracker;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import normchan.crapssim.engine.Player;

public class PlayerTracker {
	private Player player;
	private List<Integer> balances = new ArrayList<Integer>();
	private int min;
	private int max;
	private double mean;
	private double stdDeviation;

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void runComplete() {
		balances.add(new Integer(player.getBalance()));
	}
	
	public void printResults(PrintStream stream) {
		Formatter formatter = new Formatter(stream);
		formatter.format("Player ended with an average balance of $%.2f\n", mean);
		formatter.format("Player ended with an standard deviation of $%.2f\n", stdDeviation);
		formatter.format("Player ended with a max balance of $%d\n", max);
		formatter.format("Player ended with a min balance of $%d\n", min);
		formatter.flush();
		stream.flush();
	}
	public void calculateStats() {
		Collections.sort(balances);
		min = balances.get(0);
		max = balances.get(balances.size() - 1);

		calculateStdDev();
	}
	
	private void calculateStdDev() {
		int sum = 0;
		for (Integer val : balances) {
			sum += val;
		}
		mean = (double)sum / (double)balances.size();
		
		int sumOfSquares = 0;
		for (Integer val : balances) {
			sumOfSquares += Math.pow((double)val - mean, 2);
		}
		stdDeviation = Math.sqrt(sumOfSquares / balances.size());
	}
}
