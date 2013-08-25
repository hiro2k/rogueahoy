package se.obtu.rogueahoy.ui

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.Input
import se.obtu.rogueahoy.model.world.GameState

class UiGroup(var gameState: GameState, originX: Float, originY: Float, width: Float, height: Float) extends Group {
	this.setBounds(originX, originY, width, height);
	
	var sidebarGroup = new SidebarGroup(gameState);
	this.addActor(sidebarGroup)
	
	var bottomConsole = new BottomConsoleGroup(gameState);
	bottomConsole.setBounds(0, 0, 980, 80);
	this.addActor(bottomConsole);
	
	override def draw(batch: SpriteBatch, parentAlpha: Float) {
		super.draw(batch, parentAlpha);
	}
	
	override def act(delta: Float) {
		super.act(delta);
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
}