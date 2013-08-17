package se.obtu.rogueahoy.dungeons

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.Color
import scala.beans.BeanProperty

class DungeonPartition(
	@BeanProperty var startX: Int,
	@BeanProperty var startY: Int,
	@BeanProperty var endX: Int,
	@BeanProperty var endY: Int,
	@BeanProperty var id: Int) {
	
	@BeanProperty var horizontalSplit: Boolean = false;
	@BeanProperty var corridorVertices: Option[List[Vector2]] = None;
	@BeanProperty var leftChild: Option[DungeonPartition] = None;
	@BeanProperty var rightChild: Option[DungeonPartition] = None;
	@BeanProperty var color: Color = Color.WHITE;
	@BeanProperty var room: Option[Room] = None;
	
	def height = (endY - startY) + 1;
	def width = (endX - startX) + 1;
	def size = width * height;
}