package se.obtu.rogueahoy.dungeons

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.Color
import scala.beans.BeanProperty
import scala.None

abstract class PartitionContents;
case class Partitions(var leftChild: DungeonPartition, var rightChild: DungeonPartition) extends PartitionContents;
case object Empty extends PartitionContents

class DungeonPartition(
	@BeanProperty var startX: Int,
	@BeanProperty var startY: Int,
	@BeanProperty var endX: Int,
	@BeanProperty var endY: Int,
	@BeanProperty var id: Int) {
	@BeanProperty var horizontalSplit: Boolean = false;
	@BeanProperty var corridorVertices: Option[List[Vector2]] = None;
	@BeanProperty var color: Color = Color.WHITE;
	@BeanProperty var contents: PartitionContents = Empty;
	
	def hasRoom = contents.isInstanceOf[Room];
	def hasPartitions = contents.isInstanceOf[Partitions];
	
	def leftmostGrandchild(): Room = {
		contents match {
			case r: Room => r;
			case p: Partitions => p.leftChild.leftmostGrandchild();
			//Case empty, you done fucked up
		}
	}
	
	def rightmostGrandchild(): Room = {
		contents match {
			case r: Room => r;
			case p: Partitions => p.rightChild.rightmostGrandchild();
		}
	}
	
	def height = (endY - startY) + 1;
	def width = (endX - startX) + 1;
	def size = width * height;
	
	override def toString(): String = {
		s"{($startX, $startY)-($endX, $endY), id: $id}";
	}
}