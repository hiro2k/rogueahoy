package se.obtu.rogueahoy.controllers;

import se.obtu.rogueahoy.model.GameState;
import se.obtu.rogueahoy.model.entities.PlayerCharacter;
import se.obtu.rogueahoy.model.logging.GameLog;
import se.obtu.rogueahoy.tbs.MoveAction;
import se.obtu.rogueahoy.tbs.TurnManager;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class WorldController extends InputAdapter {
	
	private PlayerCharacter pc;
	private GameLog gameLog;
	private TurnManager turnManager;
	private GameState state;
	
	public WorldController(GameState gameState, TurnManager turnManager) {
		this.pc = gameState.playerCharacter;
		this.gameLog = gameState.gameLog;
		this.turnManager = turnManager;
		this.state = gameState;
	}

	@Override
	public boolean keyDown(int keycode) {
		Vector2 potentialPosition = new Vector2(this.pc.position);
		switch (keycode) {
		case Keys.DOWN:
			potentialPosition.y--;
			break;
		case Keys.UP:
			potentialPosition.y++;
			break;
		case Keys.RIGHT:
			potentialPosition.x++;
			break;
		case Keys.LEFT:
			potentialPosition.x--;
			break;
		default:
			break;
		}
		
		MoveAction moveAction = new MoveAction(state, (int)potentialPosition.x, (int)potentialPosition.y);
		if (moveAction.isValid()) {
			pc.nextTurn = moveAction;
			if (!turnManager.actionQueue.contains(pc)) {
				turnManager.actionQueue.add(pc);
			}
		}
		
		return false;
	}
	
	public void update() {
		turnManager.resolveActions();
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
}
