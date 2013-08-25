package se.obtu.rogueahoy.ui

import se.obtu.rogueahoy.model.world.GameState
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import se.obtu.rogueahoy.GameResources
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.graphics.g2d.BitmapFont

class BottomConsoleGroup(var gameState: GameState) extends Actor {
	
	var skin = GameResources.asset[Skin]("data/uiskin.json");
	var bitmapFont = skin.get("default-font", classOf[BitmapFont]);
	
	override def act(delta: Float) {
		
	}
	
	override def draw(spriteBatch: SpriteBatch, alpha: Float) {
		this.clipBegin();
		
		var startY =22;
		var padding = 6;
		
		var height = this.bitmapFont.drawWrapped(spriteBatch, "Here is a text log here is a text log here is a text log here is a text log here is a text log here is a text log here is a text log ", 0, startY, 980).height;
		this.bitmapFont.drawWrapped(spriteBatch, "Here is another game log", 0, startY + height + padding, 980);
		
		this.clipEnd();
	}
	
}