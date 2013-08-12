package se.obtu.rogueahoy.model.logging;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class GameLog {
	private Deque<LogEntry> logDeque;
	private int maxSize;
	
	public GameLog(int maxLogSize) {
		logDeque = new ArrayDeque<>();
		this.maxSize = maxLogSize;
	}
	
	public void addEntry(LogEntry entry) {
		logDeque.push(entry);
		
		if (logDeque.size() > maxSize) {
			logDeque.removeLast();
		}
	}
	
	public Iterator<LogEntry> descendingIterator() {
		return logDeque.iterator();
	}
}
