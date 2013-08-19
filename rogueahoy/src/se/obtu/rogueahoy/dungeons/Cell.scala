package se.obtu.rogueahoy.dungeons

abstract class Cell {
	var passable: Boolean
}
case object EmptyCell extends Cell {
	var passable: Boolean = false;
	
	override def toString(): String = {
		" "
	}
}
case object Wall extends Cell {
	var passable: Boolean = false;
	override def toString(): String = {
		"#"
	}
}
case object Floor extends Cell {
	var passable: Boolean = true;
	override def toString(): String = {
		"."
	}
}