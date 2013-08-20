package se.obtu.rogueahoy.test

import org.scalatest.FlatSpec
import se.obtu.rogueahoy.dungeons.BspDungeonGeneration
import se.obtu.rogueahoy.dungeons.DungeonTransformer
import se.obtu.rogueahoy.dungeons.Level
import se.obtu.rogueahoy.dungeons.DungeonPrinter
import java.io.File
import se.obtu.rogueahoy.dungeons.EmptyCell
import se.obtu.rogueahoy.dungeons.Room
import se.obtu.rogueahoy.dungeons.Cell

class PrintTest extends FlatSpec {

	"The dungeon printer" should "print the dungeon" in {
		var generator = new BspDungeonGeneration();
		generator.setHeight(50);
		generator.setWidth(50);
		generator.setMinHeight(7);
		generator.setMinWidth(7);
		generator.setMaxHeight(17);
		generator.setMaxWidth(17);
		generator.setMinRoomHeight(6);
		generator.setMinRoomWidth(6);
		generator.setMinPartitionSize(7*7);
		generator.setMaxPartitionSize(17*17);
		var rootPartition = generator.generate(1376728261342l);
		
		var map = DungeonTransformer.transformDungeonToLevel(rootPartition);
		var level = new Level(map);
		
		DungeonPrinter.printLevel(new File("test.txt"), level);
		assert(true)
	}
	
	"The dungeon printer" should "print a 5x5 dungeon starting at 0x0" in {
		var map: Array[Array[Cell]] = Array.fill(35, 35){ EmptyCell() }
		
		var room = new Room(0, 0, 5, 5, 0);
		var room2 = new Room(0, 30, 5, 5, 0);
		
		DungeonTransformer.addRoomToLevel(room, map);
		DungeonTransformer.addRoomToLevel(room2, map);
		
		
		var level = new Level(map);
		DungeonPrinter.printLevel(new File("test/test2.txt"), level);
	}
}