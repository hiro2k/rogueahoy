package rogueahoy;

import org.junit.Test;

import se.obtu.rogueahoy.dungeons.BspDungeonGeneration;

public class BspDungeonGeneratorTest {

	@Test
	public void test() {
		BspDungeonGeneration generator = new BspDungeonGeneration();
		generator.setHeight(100);
		generator.setWidth(100);
		generator.setMinHeight(4);
		generator.setMinWidth(4);
		generator.setMaxHeight(10);
		generator.setMaxWidth(10);
		generator.setMinPartitionSize(16);
		generator.setMaxPartitionSize(25*25);
		
		generator.generate(1);
	}

}
