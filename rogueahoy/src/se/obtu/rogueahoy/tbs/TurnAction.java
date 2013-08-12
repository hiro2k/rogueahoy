package se.obtu.rogueahoy.tbs;

import se.obtu.rogueahoy.model.logging.LogEntry;

public interface TurnAction {

	public void resolve();
	public LogEntry getLogEntry();
}
