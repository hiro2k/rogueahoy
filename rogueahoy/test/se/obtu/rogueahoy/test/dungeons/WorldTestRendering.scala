package se.obtu.rogueahoy.test.dungeons

import com.badlogic.gdx.Game
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL10
import se.obtu.rogueahoy.dungeons.BspDungeonGeneration
import se.obtu.rogueahoy.dungeons.Level
import se.obtu.rogueahoy.renderers.WorldRenderer
import com.badlogic.gdx.math.Rectangle
import se.obtu.rogueahoy.dungeons.DungeonTransformer
import se.obtu.rogueahoy.dungeons.PartitionJoiner
import com.badlogic.gdx.Input.Keys

object WorldTestRendering extends App {
	var cfg = new LwjglApplicationConfiguration();
	cfg.title = "rogueahoy";
	cfg.useGL20 = false;
	cfg.width = 1280;
	cfg.height = 800;
	cfg.resizable = false;
	
	var generator = new BspDungeonGeneration();
	generator.setHeight(40);
	generator.setWidth(40);
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
	var partitionJoiner = new PartitionJoiner(level);
	partitionJoiner.joinDungeon(rootPartition);
	
	var renderer = new WorldRenderer(level);
	
	var game = new WorldTestRendering(level);

	new LwjglApplication(game, cfg);
}

class WorldTestRendering(var level: Level) extends Game {
	val worldRenderer: WorldRenderer = new WorldRenderer(level);
	var wasRDown = false;
	
	override def create(): Unit = {
		worldRenderer.create();
	}
	
	override def render(): Unit = {
		update()
		if (Gdx.graphics.getDeltaTime() > 1/30) {
			super.render();
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			worldRenderer.render(new Rectangle(0, 0, 1280, 800));			
		}
	}
	
	def regenerateWorld() {
		var generator = new BspDungeonGeneration();
		generator.setHeight(40);
		generator.setWidth(40);
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
		
		worldRenderer.level = level;
	}
	
	def update() {
		if (Gdx.input.isKeyPressed(Keys.R)) {
			wasRDown = true;
		}
		else if (wasRDown) {
			regenerateWorld();
			wasRDown = false;
		}
	}
}