package se.obtu.rogueahoy.model.entities;

import se.obtu.rogueahoy.tbs.HasTurnAction;
import se.obtu.rogueahoy.tbs.TurnAction;

import com.badlogic.gdx.math.Vector2;

public class PlayerCharacter implements HasTurnAction {
	public Vector2 position = new Vector2();
	public TurnAction nextTurn;
	@Override
	public TurnAction getTurnAction() {
		// TODO Auto-generated method stub
		return nextTurn;
	}
}
