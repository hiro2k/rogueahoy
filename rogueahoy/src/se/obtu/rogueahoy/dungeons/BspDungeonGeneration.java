package se.obtu.rogueahoy.dungeons;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class BspDungeonGeneration {
	
	private int width;
	private int height;
	private int minPartitionSize;
	private int maxPartitionSize;
	private int minHeight;
	private int minWidth;
	private int maxHeight;
	private int maxWidth;
	private long randomSeed;
	private Random random;
	private List<DungeonPartition> leafPartitions;
	private Deque<DungeonPartition> partitionQueue;
	private int count = 0;

	public DungeonPartition generate(long randomSeed) {
		random = new Random(randomSeed);
		leafPartitions = new ArrayList<>();
		partitionQueue = new ArrayDeque<>();
		
		DungeonPartition startingPartition = new DungeonPartition();
		startingPartition.startX = 0;
		startingPartition.startY = 0;
		startingPartition.endX = width - 1;
		startingPartition.endY = height - 1;
		startingPartition.setId(count);
		count++;
		
		partitionQueue.push(startingPartition);
		while(partitionQueue.peek() != null) {
			this.partitionDungeon(partitionQueue.pop());
		}
		
		return startingPartition;
	}
	
	private int randomInRange(int min, int max, Random random) {
		int randomNum = random.nextInt(max - min + 1) + min;
		return randomNum;
	}

	private boolean partitionDungeon(DungeonPartition partition) {
		System.out.println("Starting partition: " + partition.toString());
		boolean shouldPartition = true;
		int partitionSize = partition.getSize();
		
		if (partitionSize <= minPartitionSize) {
			shouldPartition = false;
		}
		
		int currentPartitionHeight = partition.getHeight();
		int currentPartitionWidth = partition.getWidth();
		
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
			
			int p1StartX, p1EndX, p1StartY, p1EndY,
				p2StartX, p2EndX, p2StartY, p2EndY;
				
			//Determine the start coords of the bottom partition
			if (splitHorizontal) {
				System.out.println("Splitting horizontal");
				p1StartX = partition.startX;
				p1StartY = partition.startY;
				p1EndX = partition.endX;
				
				int partitionHeight = -1;
				boolean goodHeightFound = false;
				while (!goodHeightFound) {
					partitionHeight = this.randomInRange(minHeight, Math.max(maxHeight, currentPartitionHeight), random);
					int potentialP2Height = (partition.endY - (partition.startY + partitionHeight)) + 1;
					if (potentialP2Height >= minHeight) {
						goodHeightFound = true;
					}
				}
				
				assert(partitionHeight > 0);
				
				p1EndY = partition.startY + partitionHeight - 1;
				p2StartX = partition.startX;
				p2EndX = partition.endX;
				p2StartY = p1EndY + 1;
				p2EndY = partition.endY;
			}
			else {
				System.out.println("Splitting vertical");
				p1StartX = partition.startX;
				p1StartY = partition.startY;
				p1EndY = partition.endY;
				
				int newPartitionWidth = -1;
				boolean goodWidthFound = false;
				while (!goodWidthFound) {
					newPartitionWidth = this.randomInRange(minWidth, Math.min(maxWidth, currentPartitionWidth), random);
					int potentialP2Width = (partition.endX - (partition.startX + newPartitionWidth)) + 1;
					if (potentialP2Width >= minWidth) {
						goodWidthFound = true;
					}
				}
				
				p1EndX = partition.startX + newPartitionWidth - 1;	
				p2StartX = p1EndX + 1;
				p2EndX = partition.endX;
				p2StartY = partition.startY;
				p2EndY = partition.endY;
			}

			DungeonPartition p1 = new DungeonPartition(p1StartX, p1StartY, p1EndX, p1EndY);
			p1.setId(count++);
			DungeonPartition p2 = new DungeonPartition(p2StartX, p2StartY, p2EndX, p2EndY);
			p2.setId(count++);
			
			System.out.println("Partition 1 is " + p1);
			System.out.println("Partition 2 is " + p2);
			
//			this.partitionDungeon(p1);
//			this.partitionDungeon(p2);
			
			partitionQueue.push(p1);
			partitionQueue.push(p2);
			
			partition.setLeftChild(p1);
			partition.setRightChild(p2);
			
			return true;
		}
		else {
			//done with this partition
			System.out.println("Partition finished");
			leafPartitions.add(partition);
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

	public List<DungeonPartition> getLeafPartitions() {
		return leafPartitions;
	}

	public void setLeafPartitions(List<DungeonPartition> leafPartitions) {
		this.leafPartitions = leafPartitions;
	}

	public Deque<DungeonPartition> getPartitionQueue() {
		return partitionQueue;
	}

	public void setPartitionQueue(Deque<DungeonPartition> partitionQueue) {
		this.partitionQueue = partitionQueue;
	}
}
