package se.obtu.rogueahoy.scene

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.Event


trait InputHandler {
	def keyDown(inputEvent: InputEvent, keyCode:Int): Boolean = {
		false
	}
}

class InputListenerProxy(val inputDelegate: InputHandler) extends InputListener {
	override def keyDown(event:InputEvent, keycode:Int):Boolean = {
		return inputDelegate.keyDown(event, keycode);
	}
}

class InputHandlerHelper(var inputHandler: InputHandler) extends EventListener {
	val inputListenerProxy = new InputListenerProxy(inputHandler);
	
	def handle(event: Event):Boolean = {
		inputListenerProxy.handle(event);
	}
}