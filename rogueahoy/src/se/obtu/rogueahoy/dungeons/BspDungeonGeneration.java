package se.obtu.rogueahoy.dungeons;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class BspDungeonGeneration {
	
	private int width;
	private int height;
	private int minPartitionSize;
	private int maxPartitionSize;
	private int minHeight;
	private int minWidth;
	private int maxHeight;
	private int maxWidth;
	private int minRoomWidth;
	private int minRoomHeight;
	private long randomSeed;
	private Random random;
	private Deque<DungeonPartition> partitionQueue;
	private int count = 0;

	public DungeonPartition generate(long randomSeed) {
		random = new Random(randomSeed);
		partitionQueue = new ArrayDeque<>();
		
		DungeonPartition startingPartition = new DungeonPartition(
				0, 0, width - 1, height - 1, count++);
		
		partitionQueue.push(startingPartition);
		while(partitionQueue.peek() != null) {
			this.partitionDungeon(partitionQueue.pop());
		}
		
		fillPartitions(startingPartition);
		return startingPartition;
	}
	
	public void fillPartitions(DungeonPartition partition) {
		
		if (partition.hasPartitions()) {
			Partitions p = (Partitions) partition.contents();
			fillPartitions(p.leftChild());
			fillPartitions(p.rightChild());
		}
		else {
			//No children, fill this partition with a room
			addRoom(partition);
		}
	}

	private void addRoom(DungeonPartition partition) {

		int roomWidth = this.randomInRange(minRoomWidth, partition.width(), random);
		int roomHeight = this.randomInRange(minRoomHeight, partition.height(), random);
		
		//find a placement for the room that doesn't violate the partitions bounds
		boolean xPlacementFound = false;
		Integer roomStartX = null;
		while (!xPlacementFound) {
			int startX = randomInRange(partition.startX(), partition.endX(), random);
			if (startX + roomWidth <= partition.endX() + 1) {
				roomStartX = new Integer(startX);
				xPlacementFound = true;
			}
		}
		
		boolean yPlacementFound = false;
		Integer roomStartY = null;
		while (!yPlacementFound) {
			int startY = randomInRange(partition.startY(), partition.endY(), random);
			if (startY + roomHeight <= partition.endY() + 1) {
				roomStartY = new Integer(startY);
				yPlacementFound = true;
			}
		}
		
		Room room = new Room(roomStartX, roomStartY, roomWidth, roomHeight, partition.getId());
		partition.setContents(room);
	}

	private int randomInRange(int min, int max, Random random) {
		int randomNum = random.nextInt(max - min + 1) + min;
		return randomNum;
	}

	private boolean partitionDungeon(DungeonPartition partition) {
		boolean shouldPartition = true;
		int partitionSize = partition.size();
		
		if (partitionSize <= minPartitionSize) {
			shouldPartition = false;
		}
		
		int currentPartitionHeight = partition.height();
		int currentPartitionWidth = partition.width();
		
		boolean splitHorizontal = random.nextBoolean();
		if (currentPartitionHeight/2 < minHeight) {
			splitHorizontal = false;
			
			if (currentPartitionWidth/2 < minWidth) {
				//Partition can't be partitioned in a way that won't violate
				//minimum partition height and width
				shouldPartition = false;
			}
		}
		else if (currentPartitionWidth/2 < minWidth) { 
			splitHorizontal = true;
			if (currentPartitionHeight/2 < minHeight) {
				shouldPartition = false;
			}
		}
		
		if (shouldPartition && partitionSize < maxPartitionSize && partitionSize > minPartitionSize) {
			shouldPartition = random.nextBoolean();
		}
		
		if (shouldPartition) {
			partition.setHorizontalSplit(splitHorizontal);
			
			DungeonPartition p1, p2;
			
			int p1StartX, p1EndX, p1StartY, p1EndY,
				p2StartX, p2EndX, p2StartY, p2EndY;
				
			//Determine the start coords of the bottom partition
			if (splitHorizontal) {
				p1StartX = partition.startX();
				p1StartY = partition.startY();
				p1EndX = partition.endX();
				
				int partitionHeight = -1;
				boolean goodHeightFound = false;
				while (!goodHeightFound) {
					partitionHeight = this.randomInRange(minHeight, Math.max(maxHeight, currentPartitionHeight), random);
					int potentialP2Height = (partition.endY() - (partition.startY() + partitionHeight)) + 1;
					if (potentialP2Height >= minHeight) {
						goodHeightFound = true;
					}
				}
				
				assert(partitionHeight > 0);
				
				p1EndY = partition.startY() + partitionHeight - 1;
				p2StartX = partition.startX();
				p2EndX = partition.endX();
				p2StartY = p1EndY + 1;
				p2EndY = partition.endY();
			}
			else {
				p1StartX = partition.startX();
				p1StartY = partition.startY();
				p1EndY = partition.endY();
				
				int newPartitionWidth = -1;
				boolean goodWidthFound = false;
				while (!goodWidthFound) {
					newPartitionWidth = this.randomInRange(minWidth, Math.min(maxWidth, currentPartitionWidth), random);
					int potentialP2Width = (partition.endX() - (partition.startX() + newPartitionWidth)) + 1;
					if (potentialP2Width >= minWidth) {
						goodWidthFound = true;
					}
				}
				
				p1EndX = partition.startX() + newPartitionWidth - 1;	
				p2StartX = p1EndX + 1;
				p2EndX = partition.endX();
				p2StartY = partition.startY();
				p2EndY = partition.endY();
			}

			p1 = new DungeonPartition(p1StartX, p1StartY, p1EndX, p1EndY, count++);
			p1.setColor(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1));
			p2 = new DungeonPartition(p2StartX, p2StartY, p2EndX, p2EndY, count++);
			p2.setColor(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1));
			
			partitionDungeon(p1);
			partitionDungeon(p2);
			partition.setContents(new Partitions(p1, p2));
			
			return true;
		}
		else {
			return false;
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMaxPartitionSize() {
		return maxPartitionSize;
	}

	public void setMaxPartitionSize(int maxPartitionSize) {
		this.maxPartitionSize = maxPartitionSize;
	}

	public long getRandomSeed() {
		return randomSeed;
	}

	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}
	
	public int getMinPartitionSize() {
		return minPartitionSize;
	}

	public void setMinPartitionSize(int minPartitionSize) {
		this.minPartitionSize = minPartitionSize;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Deque<DungeonPartition> getPartitionQueue() {
		return partitionQueue;
	}

	public void setPartitionQueue(Deque<DungeonPartition> partitionQueue) {
		this.partitionQueue = partitionQueue;
	}

	public int getMinRoomWidth() {
		return minRoomWidth;
	}

	public void setMinRoomWidth(int minRoomWidth) {
		this.minRoomWidth = minRoomWidth;
	}

	public int getMinRoomHeight() {
		return minRoomHeight;
	}

	public void setMinRoomHeight(int minRoomHeight) {
		this.minRoomHeight = minRoomHeight;
	}
}
