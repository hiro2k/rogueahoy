package se.obtu.rogueahoy.dungeons

abstract class Cell {
	var passable: Boolean
	var visible: Boolean = false;
	var visited: Boolean = false;
}
case class EmptyCell extends Cell {
	var passable = false;
	override def toString(): String = {
		" "
	}
}
case class Wall extends Cell {
	var passable: Boolean = false;
	override def toString(): String = {
		"#"
	}
}
case class Floor extends Cell {
	var passable: Boolean = true;
	override def toString(): String = {
		"."
	}
}