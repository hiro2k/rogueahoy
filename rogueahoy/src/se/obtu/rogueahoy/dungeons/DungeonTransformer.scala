package se.obtu.rogueahoy.dungeons

import scala.collection.mutable;

object DungeonTransformer {

	def transformDungeonToLevel(rootPartition: DungeonPartition): Array[Array[Cell]] = {
		var dungeonArray = Array.fill[Cell](rootPartition.height, rootPartition.width) { Wall() }
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
		for (y <- room.startY to room.endY) {
			for (x <- room.startX to room.endX) {
				var cell = 
					if (x > room.startX && x < room.endX && y > room.startY && y < room.endY) Floor()
					else Wall()
					
				level(y)(x) = cell;
			}
		}
	}
}