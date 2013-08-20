package se.obtu.rogueahoy.model.entities

import se.obtu.rogueahoy.model.HasCoordinates

class PlayerCharacter(var coordinates: (Int, Int)) extends HasCoordinates {

	def coordinates(x:Int, y:Int) {
		this.coordinates = (x, y);
	}
}