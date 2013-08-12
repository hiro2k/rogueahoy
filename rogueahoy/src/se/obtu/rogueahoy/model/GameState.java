package se.obtu.rogueahoy.model;

import se.obtu.rogueahoy.model.entities.PlayerCharacter;
import se.obtu.rogueahoy.model.logging.GameLog;
import se.obtu.rogueahoy.model.world.Level;

/**
 * A generic holder for all game state
 * @author Devon
 *
 */
public class GameState {

	public Level currentLevel;
	public PlayerCharacter playerCharacter;
	public GameLog gameLog;
	
	public GameState(Level currentLevel, PlayerCharacter character, GameLog gameLog) {
		this.currentLevel = currentLevel;
		this.playerCharacter = character;
		this.gameLog = gameLog;
	}
}
