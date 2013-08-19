package se.obtu.rogueahoy.pathfinding

import se.obtu.rogueahoy.dungeons.Level
import scala.collection.mutable
import se.obtu.rogueahoy.dungeons.Cell
import scala.collection.mutable._
import se.obtu.rogueahoy.dungeons.Floor
import se.obtu.rogueahoy.Log
import se.obtu.rogueahoy.dungeons.Wall
import se.obtu.rogueahoy.dungeons.EmptyCell

class PathfindingEntry(
	var coordinates: (Int, Int),
	var gScore: Int,
	var hScore: Int,
	var parent: Option[PathfindingEntry]) {

	def fScore = gScore + hScore;

	override def equals(that: Any): Boolean = {
		that match {
			case p: PathfindingEntry => this.coordinates == p.coordinates;
			case _ => false;
		}
	}

	override def hashCode(): Int = {
		this.coordinates.hashCode();
	}
	
	override def toString(): String = {
		s"$coordinates, f: $fScore"
	}
}

object AStarPathfinding {

	def findRoute(start: (Int, Int), end: (Int, Int), level: Level): Seq[PathfindingEntry] = {
		var path = ArrayBuffer[PathfindingEntry]();
		var closedSet = Set[PathfindingEntry]();
		var openSet = Set[PathfindingEntry]();
		var currentEntry = new PathfindingEntry(start, 0, manhattenH(start, end), None);
		var targetEntry = new PathfindingEntry(end, 0, 0, None);

		openSet += currentEntry;

		while (!closedSet.contains(targetEntry) && !openSet.isEmpty) {
			var lowestFScoreNode = openSet.minBy(_.fScore);
			openSet.remove(lowestFScoreNode);
			closedSet.add(lowestFScoreNode);
			Log.logger.trace(s"Lowest fscore node is $lowestFScoreNode");

			var potentialNodes = adjacentSquares(lowestFScoreNode, end, level, allPassable);
			potentialNodes foreach { node =>
				if (!openSet.contains(node) && !closedSet.contains(node)) {
					openSet += node;
				} else if (openSet.contains(node)) {
					var potentialGScore = gScoreForCell(cellAt(level, node.coordinates)) + lowestFScoreNode.gScore;
					if (potentialGScore < node.gScore) {
						node.parent = Some(lowestFScoreNode);
						node.gScore = potentialGScore;
					}
				}
			}
			
			Log.logger.trace(s"Closed set: $closedSet");
			Log.logger.trace(s"Open set $openSet");
		}
		
		var target = closedSet.find(_.coordinates == end);
		if (!target.isDefined) {
			Log.logger.debug(s"Couldn't find target $end from $start");
		}
		while (target.isDefined) {
			if (target.isDefined) {
				path += target.get;
			}
			
			target = target.get.parent;
		}

		path;
	}

	def allPassable(level: Level, coords: (Int, Int)): Boolean = {
		val (x, y) = coords;
		return (level.map.indices.contains(x) && level.map(x).indices.contains(y))
	}

	def cellAt(level: Level, pos: (Int, Int)) = level.map(pos._1)(pos._2);

	def gScoreForCell(cell: Cell) = cell match {
		case Floor => 5;
		case Wall => 6;
		case EmptyCell => 10;
	}

	def manhattenH(start: (Int, Int), end: (Int, Int)) =
		math.abs(end._1 - start._1) + math.abs(end._2 - start._2);

	def adjacentSquares(parent: PathfindingEntry, goal: (Int, Int), level: Level, passFunc: ((Level, (Int, Int)) => Boolean)): Seq[PathfindingEntry] = {
		var adjacentSquares = ArrayBuffer[PathfindingEntry]();
		var map = level.map;
		var (x, y) = parent.coordinates;

		if (passFunc(level, (x + 1, y))) {
			var coord = (x + 1, y);
			var cell = cellAt(level, coord);
			var gScore = gScoreForCell(cell);
			adjacentSquares += new PathfindingEntry(coord, parent.gScore + gScore, manhattenH(coord, goal), Some(parent));
		}

		if (passFunc(level, (x - 1, y))) {
			var coord = (x - 1, y);
			var cell = cellAt(level, coord);
			var gScore = gScoreForCell(cell);
			adjacentSquares += new PathfindingEntry(coord, parent.gScore + gScore, manhattenH(coord, goal), Some(parent));
		}

		if (passFunc(level, (x, y + 1))) {
			var coord = (x, y + 1);
			var cell = cellAt(level, coord);
			var gScore = gScoreForCell(cell);
			adjacentSquares += new PathfindingEntry(coord, parent.gScore + gScore, manhattenH(coord, goal), Some(parent));
		}

		if (passFunc(level, (x, y - 1))) {
			var coord = (x, y - 1);
			var cell = cellAt(level, coord);
			var gScore = gScoreForCell(cell);
			adjacentSquares += new PathfindingEntry(coord, parent.gScore + gScore, manhattenH(coord, goal), Some(parent));
		}

		adjacentSquares;
	}
}