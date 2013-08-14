package se.obtu.rogueahoy.dungeons;

public class DungeonPartition {

	public int startX;
	public int startY;
	public int endX;
	public int endY;
	private int id;
	
	private DungeonPartition leftChild;
	private DungeonPartition rightChild;
	
	public DungeonPartition(int startX, int startY, int endX, int endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	public DungeonPartition() {
	}

	public DungeonPartition getRightChild() {
		return rightChild;
	}
	
	public void setRightChild(DungeonPartition rightChild) {
		this.rightChild = rightChild;
	}
	
	public DungeonPartition getLeftChild() {
		return leftChild;
	}
	
	public void setLeftChild(DungeonPartition leftChild) {
		this.leftChild = leftChild;
	}
	
	public int getHeight() {
		return (endY - startY) + 1;
	}
	
	public int getWidth() {
		return (endX - startX) + 1;
	}
	
	public int getSize() {
		int width = (endX - startX) + 1;
		int height = (endY - startY) + 1;
		
		return width * height;
	}
	
	@Override
	public String toString() {
		String string = "Partition: {(" + startX + ", " + startY + "), (" + endX + ", " + endY + ")}"; 
		return string;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
