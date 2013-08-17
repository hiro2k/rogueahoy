package se.obtu.rogueahoy.dungeons

import se.obtu.rogueahoy.Log
import com.badlogic.gdx.math.Vector2

class PartitionJoiner {

	def joinPartitions(
		leftPartition: DungeonPartition,
		rightPartition: DungeonPartition,
		parentPartition: DungeonPartition): Unit = {

		if (leftPartition.room.isDefined && rightPartition.room.isDefined) {
			var leftRoom = leftPartition.room.get;
			var rightRoom = rightPartition.room.get;

			var corridor = joinRooms(leftRoom, rightRoom, parentPartition);
		}
	}

	def joinRooms(leftRoom: Room, rightRoom: Room, parentPartition: DungeonPartition): List[Vector2] = {
		var corridorCoords = List[Vector2]();
		
		if (parentPartition.horizontalSplit) {
			//if the rooms are adjacent and overlap by more than two cells we can just put a "door"
			//between them
			if (leftRoom.adjacentY(rightRoom)) {
				if (leftRoom.overlapX(rightRoom) > 2) {
					Log.logger.debug(s"Rooms $leftRoom and $rightRoom have a south-north door");
				}
			}
		}

		corridorCoords;
	}
}