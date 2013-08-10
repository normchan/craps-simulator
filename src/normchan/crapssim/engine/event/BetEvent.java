package normchan.crapssim.engine.event;

public class BetEvent extends GameEvent {
	public enum EventType {
		UPDATE, RETRACT, WIN, LOSS, PUSH, NUMBER_ESTABLISHED
	}
	
	private final EventType type;

	public BetEvent(EventType type, String message) {
		super(message);
		this.type = type;
	}

	public EventType getType() {
		return type;
	}
}
