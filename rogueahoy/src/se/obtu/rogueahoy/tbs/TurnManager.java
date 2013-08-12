package se.obtu.rogueahoy.tbs;

import java.util.ArrayDeque;
import java.util.Deque;

import se.obtu.rogueahoy.model.logging.GameLog;

public class TurnManager {
	public Deque<HasTurnAction> actionQueue;
	public GameLog log;

	public TurnManager(GameLog log) {
		this.actionQueue = new ArrayDeque<>();
		this.log = log;
	}
	
	public void resolveActions() {
		while (actionQueue.peek() != null) {
			HasTurnAction turnComponent = actionQueue.pop();
			TurnAction action = turnComponent.getTurnAction();
			action.resolve();
			log.addEntry(action.getLogEntry());
		}
	}
}

