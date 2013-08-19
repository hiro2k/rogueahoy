package se.obtu.rogueahoy.dungeons

import se.obtu.rogueahoy.Log
import com.badlogic.gdx.math.Vector2
import se.obtu.rogueahoy.pathfinding.AStarPathfinding

class PartitionJoiner(var level: Level) {
	
	def joinDungeon(dungeon: DungeonPartition) {
		
		dungeon.contents match {
			case Partitions(leftChild, rightChild) =>
				joinDungeon(leftChild);
				joinDungeon(rightChild);
				
				(leftChild.contents, rightChild.contents) match {
					
					case (leftRoom: Room, rightRoom: Room) =>
						joinRooms(leftRoom, rightRoom, dungeon);
						
					case _ =>
						joinPartitions(leftChild, rightChild, dungeon);
				}
			case _ => Unit
		}
	}

	def joinPartitions(
		leftPartition: DungeonPartition,
		rightPartition: DungeonPartition,
		parentPartition: DungeonPartition): Unit = {
		
		(leftPartition.contents, rightPartition.contents) match {
			case (leftRoom: Room, rightRoom: Room) =>
				joinRooms(leftRoom, rightRoom, parentPartition);
				
			case (leftRoom: Room, Partitions(leftChild, rightChild)) =>
				joinRooms(leftRoom, rightPartition.leftmostGrandchild, parentPartition);
				
			case (Partitions(_,_), rightRoom: Room) =>
				joinRooms(leftPartition.rightmostGrandchild, rightRoom, parentPartition);
				
			case (Partitions(_, _), Partitions(_, _)) =>
				
				joinRooms(leftPartition.rightmostGrandchild, rightPartition.leftmostGrandchild, parentPartition);
		}
	}

	def joinRooms(leftRoom: Room, rightRoom: Room, parentPartition: DungeonPartition): Unit = {
		//pick coords in both rooms
		Log.logger.debug(s"Joining rooms $leftRoom and $rightRoom");
		var start = (leftRoom.startX + 1, leftRoom.startY+1);
		var end = (rightRoom.startX + 1, rightRoom.startY+1);
		
		var path = AStarPathfinding.findRoute(start, end, level);
		
		path foreach {pathfindingEntry =>
			var (y, x) = pathfindingEntry.coordinates;
			level.map(x)(y) = Floor;
		}
	}
}