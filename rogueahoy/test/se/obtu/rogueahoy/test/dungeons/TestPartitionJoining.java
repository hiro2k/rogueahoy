package se.obtu.rogueahoy.test.dungeons;

import org.junit.Test;

import se.obtu.rogueahoy.dungeons.DungeonPartitionJ;

public class TestPartitionJoining {

	@Test
	public void test() {
		DungeonPartitionJ parent = new DungeonPartitionJ(0, 0, 17, 17);
		parent.setHorizontalSplit(false);
		
		DungeonPartitionJ leftChild = new DungeonPartitionJ(0, 0, 8, 17);
		DungeonPartitionJ rightChild = new DungeonPartitionJ(9, 0, 17, 17);
	}

}
