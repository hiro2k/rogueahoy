package se.obtu.rogueahoy.model.world

import se.obtu.rogueahoy.dungeons.Level
import se.obtu.rogueahoy.model.entities.PlayerCharacter
import se.obtu.rogueahoy.model.HasCoordinates

class GameState(
		var playerCharacter: PlayerCharacter, 
		var level: Level, 
		var currentFocus: HasCoordinates) {
}