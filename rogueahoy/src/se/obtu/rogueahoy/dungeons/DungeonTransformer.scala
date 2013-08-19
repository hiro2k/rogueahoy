package se.obtu.rogueahoy.dungeons

import scala.collection.mutable;

object DungeonTransformer {

	def transformDungeonToLevel(rootPartition: DungeonPartition): Array[Array[Cell]] = {
		var dungeonArray = Array.fill[Cell](rootPartition.width, rootPartition.height) { Wall }
		addPartitionToLevel(rootPartition, dungeonArray);
		dungeonArray
	}
	
	def addPartitionToLevel(partition: DungeonPartition, level: Array[Array[Cell]]): Unit = {
		partition.contents match {
			case r:Room => addRoomToLevel(r, level)
			case Partitions(left, right) =>
				addPartitionToLevel(left, level);
				addPartitionToLevel(right, level);
			case se.obtu.rogueahoy.dungeons.Empty => //noop
		}
	}
	
	def addRoomToLevel(room: Room, level: Array[Array[Cell]]): Unit = {
		for (y <- room.startX to room.endX) {
			for (x <- room.startY to room.endY) {
				var cell = 
					if (y > room.startX && y < room.endX && x > room.startY && x < room.endY) Floor
					else Wall
					
				level(x)(y) = cell;
			}
		}
	}
}