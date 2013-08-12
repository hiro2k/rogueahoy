package se.obtu.rogueahoy.renderers;

import se.obtu.rogueahoy.model.entities.PlayerCharacter;
import se.obtu.rogueahoy.model.world.Level;
import se.obtu.rogueahoy.model.world.Cell.CellType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class WorldRenderer {

	private static final int TILE_SIZE = 16;
	public Level level;
	public PlayerCharacter playerCharacter;
	private ShapeRenderer debugRenderer = new ShapeRenderer();
	public OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Texture defaultTileset;
	private Sprite floorSprite;
	private Sprite wallSprite;
	private Sprite pcSprite;
	private Sprite blankSprite;
	
	public WorldRenderer(Level level, PlayerCharacter character) {
		this.level = level;
		this.playerCharacter = character;
		spriteBatch = new SpriteBatch();
		
		defaultTileset = new Texture(Gdx.files.internal("data/16_tileset_transparent.png"));
		TextureRegion floorRegion = new TextureRegion(defaultTileset, 14 * TILE_SIZE, 2 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		TextureRegion wallRegion = new TextureRegion(defaultTileset, 1 * TILE_SIZE, 11*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		TextureRegion pcRegion = new TextureRegion(defaultTileset, 0, 4*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		TextureRegion blank = new TextureRegion(defaultTileset, 11*TILE_SIZE, 13*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		floorSprite = new Sprite(floorRegion);
		wallSprite = new Sprite(wallRegion);
		pcSprite = new Sprite(pcRegion);
		blankSprite = new Sprite(blank);
		
		this.camera = new OrthographicCamera(1280, 800);
		this.camera.position.set(0, 0, 0);
		this.camera.update();
	}
	
	public void render(Rectangle usableScreen) {
		update();
		
		spriteBatch.setProjectionMatrix(camera.combined);
		ScissorStack.pushScissors(usableScreen);

		spriteBatch.begin();
		
		for (int x = 0; x < level.cells.length; x++) {
			for (int y = 0; y < level.cells.length; y++) {
				float x1 = x * TILE_SIZE;
				float y1 = y * TILE_SIZE;
				if (level.cells[x][y].cellType == CellType.Wall) {
					spriteBatch.draw(wallSprite, x1, y1, TILE_SIZE, TILE_SIZE);
				}
				else {
					spriteBatch.draw(floorSprite, x1, y1, TILE_SIZE, TILE_SIZE);
				}
			}
		}
				
		drawPc();	
		spriteBatch.flush();
		ScissorStack.popScissors();
		spriteBatch.end();
		//renderDebug();
	}

	private void drawPc() {
		Color originalColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.BLACK);
		spriteBatch.draw(blankSprite, playerCharacter.position.x * TILE_SIZE, playerCharacter.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		spriteBatch.setColor(originalColor);
		spriteBatch.draw(pcSprite, playerCharacter.position.x * TILE_SIZE, playerCharacter.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
	public void renderDebug() {
		debugRenderer.setProjectionMatrix(this.camera.combined);
		debugRenderer.begin(ShapeType.Line);
		
		for (int x = 0; x < level.cells.length; x++) {
			for (int y = 0; y < level.cells.length; y++) {
				float x1 = x * TILE_SIZE;
				float y1 = y * TILE_SIZE;
				Color color = new Color(1,0,0,1);
				if (x == 0 && y == 0) {
					color = new Color(0, 0, 1, 1);
				}
				else if (level.cells[x][y].cellType == CellType.Floor) {
					color = new Color(0,1,0,1);
				}
				debugRenderer.setColor(color);
				debugRenderer.rect(x1, y1, TILE_SIZE, TILE_SIZE);
			}
		}
		
		debugRenderer.end();
	}
	
	public void update() {
		camera.position.x = this.playerCharacter.position.x * TILE_SIZE + 16;
		camera.position.y = this.playerCharacter.position.y * TILE_SIZE + 16;
		camera.update();
	}
}
