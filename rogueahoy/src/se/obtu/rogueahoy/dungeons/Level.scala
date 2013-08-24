package se.obtu.rogueahoy.dungeons

import rlforj.los.ILosBoard

class Level(var map: Array[Array[Cell]]) extends ILosBoard {
	
	var entryPoint: (Int, Int) = (0, 0);
	
	def entryX = entryPoint._1;
	def entryY = entryPoint._2;
	
	var currentFov = Array.fill(map.size, map(0).size) { false }

	def contains(x:Int, y: Int): Boolean = {
		y >= 0 && y < map.size && x >= 0 && x < map(y).size
	}
	
	def isObstacle(x:Int, y: Int): Boolean = {
		if (this.contains(x, y)) {
			map(y)(x) match {
				case Floor() => false;
				case Wall() => true;
				case EmptyCell() => true;
			}			
		}
		else {
			true
		}
	}
	
	def visit(x:Int, y:Int) {
		if (this.contains(x, y)) {
			currentFov(y)(x) = true;
			map(y)(x).visited = true;			
		}
	}
	
	def passable(x:Int, y:Int) = {
		!isObstacle(x, y);
	}
	
	def resetFov() {
		currentFov = Array.fill(map.size, map(0).size) { false }
	}
	
	def asResistanceMap(): Array[Array[Float]] = {
		var resistanceMap = Array.fill(this.map(0).size, this.map.size){0.0f}
		for(y <- 0 until this.map.size) {
			for (x <- 0 until this.map(y).size) {
				resistanceMap(x)(y) = if (map(y)(x).passable) 0.0f else 1.0f
			}
		}
		
		resistanceMap
	}
}