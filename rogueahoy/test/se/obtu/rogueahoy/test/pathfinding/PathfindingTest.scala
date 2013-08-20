package se.obtu.rogueahoy.test.pathfinding

import org.scalatest.FlatSpec
import se.obtu.rogueahoy.pathfinding.AStarPathfinding
import se.obtu.rogueahoy.dungeons.EmptyCell
import se.obtu.rogueahoy.dungeons.Level
import se.obtu.rogueahoy.dungeons.Cell
import se.obtu.rogueahoy.pathfinding.PathfindingEntry

class PathfindingTest extends FlatSpec {
	
	def fixture = {
		new {
			//create a 35*35 level of all empty squares
			val map: Array[Array[Cell]] = Array.fill(35, 35){EmptyCell()};
			val emptyLevel = new Level(map);
			
			var pfe1 = new PathfindingEntry((0,0), 0, 0, None);
			var pfe2 = new PathfindingEntry((0,0), 0, 0, None);
		}
	}
	
	"Two pathfinding entries with the same coordinates" should "be equal" in {
		assert(fixture.pfe1 == fixture.pfe2);
	}

	"The manhatten heuristic between (0, 0) and (1, 1)" should "be 2" in {
		var start = (0, 0);
		var end = (1, 1);
		
		var mh = AStarPathfinding.manhattenH(start, end);
		
		assert(mh == 2);
	}
	
	"The manhatten heuristic between (1, 1) and (0, 0)" should "also be 2" in {
		var start = (0, 0);
		var end = (1, 1);
		
		var mh = AStarPathfinding.manhattenH(end, start);
		
		assert(mh == 2);
	}
	
	"Pathfinding on an empty map" should "complete successfully" in {
		val f = fixture;
		val start = (0, 0);
		val end = (34, 34);
		
		val coordinates = AStarPathfinding.findRoute(start, end, f.emptyLevel);
	}
}