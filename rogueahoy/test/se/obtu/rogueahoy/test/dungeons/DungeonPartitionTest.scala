package se.obtu.rogueahoy.test.dungeons

import org.scalatest._
import se.obtu.rogueahoy.dungeons.Room

class DungeonPartitionTest extends FlatSpec {
	
	def fixture = {
		new {
			
		}
	}

	"Two rooms overlapping rooms" should "overlap on the X axis" in {
		var leftRoom = new Room(0, 0, 5, 5);
		var rightRoom = new Room(4, 6, 8, 9);
		
		var xOverlap = leftRoom.overlapX(rightRoom);
		assert(xOverlap > 0);
	}
	
	it should "not overlap on the X axis" in {
		var leftRoom = new Room(0, 0, 5, 5);
		var rightRoom = new Room(6, 6, 8, 9);
		
		assert((leftRoom overlapX rightRoom) <= 0);
	}
	
	"Two adjacent rooms" should "be adjacent on the Y axis" in {
		var bottomRoom = new Room(0, 0, 5, 5);
		var topRoom = new Room(0, 6, 5, 9);
		
		assert(bottomRoom adjacentY topRoom);
	}
}