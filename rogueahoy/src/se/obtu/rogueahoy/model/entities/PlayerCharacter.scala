package se.obtu.rogueahoy.model.entities

import se.obtu.rogueahoy.model.HasCoordinates

class PlayerCharacter(
	var coordinates: (Int, Int),
	var name: String = "Hrongar the Barbarian",
	var maxHp: Int = 12,
	var maxMp: Int = 6,
	var strength: Int = 6,
	var dexterity: Int = 6,
	var intelligence: Int = 6) extends HasCoordinates {

	var currentHp = maxHp;
	var currentMp = maxMp;

	def coordinates(x: Int, y: Int) {
		this.coordinates = (x, y);
	}
}