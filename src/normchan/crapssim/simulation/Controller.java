package normchan.crapssim.simulation;

import normchan.crapssim.engine.GameManager;

public class Controller {
	protected GameManager manager;
	private int counter = 0;
	private boolean numberPuckOn = false;
	
	public void setManager(GameManager manager) {
		this.manager = manager;
	}
	
	public void reset() {
		counter = 0;
		numberPuckOn = false;
	}

	public boolean isSimulationComplete() {
		if (manager.getLayout().isNumberEstablished())
			numberPuckOn = true;
		else if (numberPuckOn) {
			numberPuckOn = false;
			counter++;
		}

		if (counter > 10 && manager.getLayout().getBets().isEmpty())
			return true;
		return false;
	}
}
