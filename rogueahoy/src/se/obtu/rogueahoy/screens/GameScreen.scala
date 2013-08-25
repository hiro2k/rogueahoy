package se.obtu.rogueahoy.screens

import com.badlogic.gdx.Screen
import se.obtu.rogueahoy.model.world.GameState
import se.obtu.rogueahoy.dungeons.DungeonGeneration
import se.obtu.rogueahoy.scene.WorldRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import se.obtu.rogueahoy.scene.PlayerCharacterActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL10
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import se.obtu.rogueahoy.ui.UiGroup

class GameScreen(var gameState: GameState) extends Screen with DefaultGameScreen {
	
	
	var stage = new Stage();
	var uiGroup = new UiGroup(gameState, 0f, 0f, 1280f, 800f);
	var worldGroup = new Group();
	
	stage.addActor(uiGroup);
	
	worldGroup.setBounds(0, 0, 980, 736);
	var renderer = new WorldRenderer(gameState);
	renderer.setBounds(0, 80, 980, 736)
	worldGroup.addActor(renderer);
		
	var pcActor = PlayerCharacterActor.apply(renderer.getCamera(), gameState);
	worldGroup.addActor(pcActor);
	stage.addActor(worldGroup);

	def render(delta: Float) {
		stage.act(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}
}