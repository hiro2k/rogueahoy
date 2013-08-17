package se.obtu.rogueahoy.dungeons;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class DungeonPartitionJ {

	public int startX;
	public int startY;
	public int endX;
	public int endY;
	private int id;
	private boolean horizontalSplit = false;
	private Integer roomWidth;
	private Integer roomHeight;
	private Integer roomStartX;
	private Integer roomStartY;
	private List<Vector2> cooridorVertices;
	private boolean hasRoom = false;	
	private DungeonPartitionJ leftChild;
	private DungeonPartitionJ rightChild;
	private Color color = Color.BLACK;
	
	public DungeonPartitionJ(int startX, int startY, int endX, int endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	public DungeonPartitionJ() {
	}

	public DungeonPartitionJ getRightChild() {
		return rightChild;
	}
	
	public void setRightChild(DungeonPartitionJ rightChild) {
		this.rightChild = rightChild;
	}
	
	public DungeonPartitionJ getLeftChild() {
		return leftChild;
	}
	
	public void setLeftChild(DungeonPartitionJ leftChild) {
		this.leftChild = leftChild;
	}
	
	public int getHeight() {
		return (endY - startY) + 1;
	}
	
	public int getWidth() {
		return (endX - startX) + 1;
	}
	
	public int getRoomEndX() {
		return roomStartX + roomWidth;
	}
	
	public int getRoomEndY() {
		return roomStartY + roomHeight;
	}
	
	public int getSize() {
		int width = (endX - startX) + 1;
		int height = (endY - startY) + 1;
		
		return width * height;
	}
	
	@Override
	public String toString() {
		String string = "Partition: {(" + startX + ", " + startY + "), (" + endX + ", " + endY + "), id: " + this.id +"}"; 
		return string;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isHorizontalSplit() {
		return horizontalSplit;
	}

	public void setHorizontalSplit(boolean horizontalSplit) {
		this.horizontalSplit = horizontalSplit;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public Integer getRoomWidth() {
		return roomWidth;
	}

	public void setRoomWidth(Integer roomWidth) {
		this.roomWidth = roomWidth;
	}

	public Integer getRoomHeight() {
		return roomHeight;
	}

	public void setRoomHeight(Integer roomHeight) {
		this.roomHeight = roomHeight;
	}

	public Integer getRoomStartX() {
		return roomStartX;
	}

	public void setRoomStartX(Integer roomStartX) {
		this.roomStartX = roomStartX;
	}

	public Integer getRoomStartY() {
		return roomStartY;
	}

	public void setRoomStartY(Integer roomStartY) {
		this.roomStartY = roomStartY;
	}

	public List<Vector2> getCooridorVertices() {
		return cooridorVertices;
	}

	public void setCooridorVertices(List<Vector2> cooridorVertices) {
		this.cooridorVertices = cooridorVertices;
	}

	public boolean isHasRoom() {
		return hasRoom;
	}

	public void setHasRoom(boolean hasRoom) {
		this.hasRoom = hasRoom;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
