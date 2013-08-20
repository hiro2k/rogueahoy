package se.obtu.rogueahoy.renderers;

import se.obtu.rogueahoy.model.world.GameState;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SideDisplayRenderer extends Actor {
	private Rectangle sideDisplayBounds;
	private static final int MARGIN_SIZE = 8;
	private static final int TEXT_MARGIN = 4;
	private static final int GAME_LOG_HEIGHT = 300;
	private GameState gameState;
	private ShapeRenderer shapeRenderer;
	private BitmapFont font;
	private SpriteBatch batch;
	
	public SideDisplayRenderer(Rectangle location, GameState gameState) {
//		this.sideDisplayBounds = location;
//		this.gameState = gameState;
//		
//		shapeRenderer = new ShapeRenderer();
//		font = new BitmapFont(Gdx.files.internal("data/default.fnt"), false);
//		batch = new SpriteBatch();
//		Matrix4 translation = new Matrix4();
//		translation.setToTranslation(sideDisplayBounds.x, sideDisplayBounds.y, 0);
//		batch.setTransformMatrix(translation);
//		shapeRenderer.setTransformMatrix(translation);
	}
	
	public void render() {
//		drawSidebarBackground();
//
//		Rectangle consoleBounds = new Rectangle(1280 - sideDisplayBounds.width, 500, sideDisplayBounds.width, GAME_LOG_HEIGHT);
//		ScissorStack.pushScissors(consoleBounds);
//		
//		batch.begin();
//		batch.setColor(Color.WHITE);
//		
//		int logMessageCount = 0;
//		float textY = 800;
//		TextBounds lastTextBounds = null;
//		for (Iterator<LogEntry> logIterator = this.gameState.gameLog.descendingIterator(); logIterator.hasNext() && logMessageCount < 10; logMessageCount++) {
//			LogEntry entry = logIterator.next();
//			float color = 1f - logMessageCount*0.10f;
//			font.setColor(color, color, color, 1f);
//			lastTextBounds = font.drawWrapped(batch, entry.message, MARGIN_SIZE, textY, sideDisplayBounds.width - MARGIN_SIZE * 2);
//			textY -= lastTextBounds.height + TEXT_MARGIN;
//		}
//		
//		batch.end();
//		ScissorStack.popScissors();
	}

	private void drawSidebarBackground() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(0, 0, sideDisplayBounds.width, 800);
		shapeRenderer.end();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.line(1, 0, 1, sideDisplayBounds.height);
		shapeRenderer.end();
	}

}
