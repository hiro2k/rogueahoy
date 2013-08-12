package se.obtu.rogueahoy.tbs;

import se.obtu.rogueahoy.model.Direction;
import se.obtu.rogueahoy.model.GameState;
import se.obtu.rogueahoy.model.logging.LogEntry;
import se.obtu.rogueahoy.model.world.Cell;
import se.obtu.rogueahoy.model.world.Cell.CellType;

public class MoveAction extends PlayerCharacterAction {
	
	private GameState state;
	private int targetX;
	private int targetY;
	private boolean valid;
	private LogEntry logEntry;
	private Direction targetDirection;
	
	public MoveAction(GameState state, int targetX, int targetY) {
		this.state = state;
		this.targetX = targetX;
		this.targetY = targetY;
		int currentX = (int) state.playerCharacter.position.x;
		int currentY = (int) state.playerCharacter.position.y;
		
		valid = false;
		if (currentX == targetX && currentY == targetY) {
			valid = false;
		}
		else if (state.currentLevel.cells.length > targetX) {
			if (state.currentLevel.cells[targetX].length > targetY) {
				Cell targetCell = state.currentLevel.cells[targetX][targetY];
				if (canOccupy(targetCell)) {
					valid = true;
					
					if (targetX > currentX) {
						if (targetY > currentY) {
							targetDirection = Direction.NORTH_EAST;
						}
						else if (targetY < currentY) {
							targetDirection = Direction.SOUTH_EAST;
						}
						else {
							targetDirection = Direction.EAST;
						}
					}
					else if (targetX < currentX) {
						if (targetY > currentY) {
							targetDirection = Direction.NORTH_WEST;
						}
						else if (targetY < currentY) {
							targetDirection = Direction.SOUTH_WEST;
						}
						else {
							targetDirection = Direction.WEST;
						}
					}
					else {
						if (targetY > currentY) {
							targetDirection = Direction.NORTH;
						}
						else if (targetY < currentY) {
							targetDirection = Direction.SOUTH;
						}
					}
				}
			}
		}
	}

	
	private boolean canOccupy(Cell targetCell) {
		return targetCell.cellType == CellType.Floor;
	}


	@Override
	public void resolve() {
		
		if (this.isValid()) {
			state.playerCharacter.position.x = this.targetX;
			state.playerCharacter.position.y = this.targetY;
			String logMessage = "You move " + targetDirection.getDirectionString();
			logEntry = new LogEntry(logMessage);
		}
	}


	public boolean isValid() {
		return valid;
	}


	@Override
	public LogEntry getLogEntry() {
		return logEntry;
	}
}
