package se.obtu.rogueahoy;

import se.obtu.rogueahoy.screens.GameScreen;

import com.badlogic.gdx.Game;

public class RogueahoyGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		super.render();
	}


	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
