package se.obtu.rogueahoy;

import se.obtu.rogueahoy.dungeons.DungeonGeneration;
import se.obtu.rogueahoy.screens.GameScreen;

import com.badlogic.gdx.Game;

public class RogueahoyGame extends Game {

	@Override
	public void create() {
		GameResources.initializeAssetManager(true);
		GameScreen gameScreen = new GameScreen(DungeonGeneration.randomStartState());
		this.setScreen(gameScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
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
