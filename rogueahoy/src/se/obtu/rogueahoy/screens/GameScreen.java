package se.obtu.rogueahoy.screens;

import se.obtu.rogueahoy.RogueahoyGame;
import se.obtu.rogueahoy.controllers.WorldController;
import se.obtu.rogueahoy.model.GameState;
import se.obtu.rogueahoy.model.entities.PlayerCharacter;
import se.obtu.rogueahoy.model.logging.GameLog;
import se.obtu.rogueahoy.model.world.Cell;
import se.obtu.rogueahoy.model.world.Cell.CellType;
import se.obtu.rogueahoy.model.world.Level;
import se.obtu.rogueahoy.renderers.SideDisplayRenderer;
import se.obtu.rogueahoy.renderers.WorldRenderer;
import se.obtu.rogueahoy.tbs.TurnManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen, InputProcessor {
	
	private static final int SIDE_MENU_WIDTH = 352;
	WorldRenderer renderer;
	SideDisplayRenderer sideDisplayRenderer;
	WorldController controller;
	
	public GameScreen(RogueahoyGame game) {
		Level defaultLevel = new Level(100, 100);
		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				Cell cell = new Cell();
				if (x == 0 || x == 99 || y == 0 || y == 99) {
					cell.cellType = CellType.Wall;
				}
				else {
					cell.cellType = CellType.Floor;
				}
				
				defaultLevel.cells[x][y] = cell;
			}
		}
		
		PlayerCharacter pc = new PlayerCharacter();
		pc.position.x = 1;
		pc.position.y = 1;
		
		GameState gameState = new GameState(defaultLevel, pc, new GameLog(50));
		TurnManager turnManager = new TurnManager(gameState.gameLog);
		
		//renderer = new WorldRenderer(defaultLevel, pc);
		
		Rectangle sideDisplayBounds = new Rectangle(1280-SIDE_MENU_WIDTH, 0, SIDE_MENU_WIDTH, 800);
		sideDisplayRenderer = new SideDisplayRenderer(sideDisplayBounds, gameState);
		
		
		controller = new WorldController(gameState, turnManager);
		InputMultiplexer inputProcessors = new InputMultiplexer(this);
		inputProcessors.addProcessor(controller);
		
		Gdx.input.setInputProcessor(inputProcessors);
	}

	@Override
	public void render(float delta) {
		controller.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Rectangle usableScreen = new Rectangle(0,0, 1280 - SIDE_MENU_WIDTH, 800);
		renderer.render(usableScreen);
		sideDisplayRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE) {
			Gdx.app.exit();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
