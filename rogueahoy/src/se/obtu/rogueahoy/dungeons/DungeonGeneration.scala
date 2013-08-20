package se.obtu.rogueahoy.dungeons

import se.obtu.rogueahoy.model.world.GameState
import se.obtu.rogueahoy.model.entities.PlayerCharacter

object DungeonGeneration {

	def randomLevel(): (Level, DungeonPartition) = {
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
		var rootPartition = generator.generate(System.currentTimeMillis());
		var map = DungeonTransformer.transformDungeonToLevel(rootPartition);
		var level = new Level(map);
		var partitionJoiner = new PartitionJoiner(level);
		partitionJoiner.joinDungeon(rootPartition);
		
		var room = rootPartition.randomRoom;
		level.entryPoint = (room.startX + 1, room.startY + 1)
		
		(level, rootPartition);
	}
	
	def randomStartState(): GameState = {
		var world = randomLevel
		var level = world._1;
		var pc = new PlayerCharacter(level.entryPoint);
		
		new GameState(pc, level, pc);
	}
}