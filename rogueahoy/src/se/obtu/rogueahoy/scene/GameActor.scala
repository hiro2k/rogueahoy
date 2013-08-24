package se.obtu.rogueahoy.scene

import se.obtu.rogueahoy.model.world.GameState
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputListener

abstract class GameActor(var gameState: GameState) extends Actor with InputHandler {
	val inputHandlerHelper = new InputHandlerHelper(this);
	this.addListener(inputHandlerHelper);
}