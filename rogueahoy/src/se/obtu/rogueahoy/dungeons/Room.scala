package se.obtu.rogueahoy.dungeons

import scala.beans.BeanProperty

class Room(
	@BeanProperty var startX: Int,
	@BeanProperty var startY: Int,
	@BeanProperty var width: Int,
	@BeanProperty var height: Int) {

	def endX = startX + width;
	def endY = startY + height;
	
	/**
	 * Whether the given room is "adjacent" to this room on the Y axis. That is, their borders
	 * meet but don't overlap.
	 * 
	 * @param topRoom the other room. Assumed to be north of this room
	 */
	def adjacentY(topRoom: Room) = this.endY + 1 == topRoom.startY;
	
	def adjacentX(rightRoom: Room) = this.endX + 1 == rightRoom.startX;
	
	def overlapX(rightRoom: Room): Int = {
		this.endX - rightRoom.startX;
	}
	
	def overlapX(rightRoom: Room, by: Int): Boolean = {
		this.overlapX(rightRoom) == by;
	}
}