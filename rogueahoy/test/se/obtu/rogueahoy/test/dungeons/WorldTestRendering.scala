package se.obtu.rogueahoy.test.dungeons

import com.badlogic.gdx.Game
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL10
import se.obtu.rogueahoy.dungeons.BspDungeonGeneration
import se.obtu.rogueahoy.dungeons.Level
import com.badlogic.gdx.math.Rectangle
import se.obtu.rogueahoy.dungeons.DungeonTransformer
import se.obtu.rogueahoy.dungeons.PartitionJoiner
import com.badlogic.gdx.Input.Keys
import se.obtu.rogueahoy.dungeons.DungeonGeneration
import com.badlogic.gdx.scenes.scene2d.Stage
import se.obtu.rogueahoy.scene.WorldRenderer

object WorldTestRendering extends App {
	var cfg = new LwjglApplicationConfiguration();
	cfg.title = "rogueahoy";
	cfg.useGL20 = false;
	cfg.width = 1280;
	cfg.height = 800;
	cfg.resizable = false;
	
	var game = new WorldTestRendering();

	new LwjglApplication(game, cfg);
}

class WorldTestRendering() extends Game {
	var worldRenderer: Option[WorldRenderer] = None;
	var stage: Option[Stage] = None;
	var wasRDown = false;
	
	override def create(): Unit = {
		worldRenderer = new Some(new WorldRenderer(DungeonGeneration.randomStartState))
		stage = Some(new Stage());
		stage.get.addActor(worldRenderer.get);
	}
	
	override def render(): Unit = {
		update()
		if (Gdx.graphics.getDeltaTime() > 1/30) {
			super.render();
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			stage.get.act();
			stage.get.draw();
		}
	}
	
	def regenerateWorld() {
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