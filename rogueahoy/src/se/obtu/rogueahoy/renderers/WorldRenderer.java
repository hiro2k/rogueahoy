package se.obtu.rogueahoy.renderers;

import se.obtu.rogueahoy.dungeons.Cell;
import se.obtu.rogueahoy.dungeons.Floor$;
import se.obtu.rogueahoy.dungeons.Level;
import se.obtu.rogueahoy.dungeons.Wall$;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private static final int TILE_SIZE = 16;
	private static final float CAMERA_SPEED = 1;
	public Level level;
	public OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Texture defaultTileset;
	private Sprite floorSprite;
	private Sprite wallSprite;
	
	public WorldRenderer(Level level) {
		this.level = level;
	}
	
	public void create() {
		spriteBatch = new SpriteBatch();
		defaultTileset = new Texture(Gdx.files.internal("data/16_tileset_transparent.png"));
		TextureRegion floorRegion = new TextureRegion(defaultTileset, 14 * TILE_SIZE, 2 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		TextureRegion wallRegion = new TextureRegion(defaultTileset, 0 * TILE_SIZE, 11*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		floorSprite = new Sprite(floorRegion);
		wallSprite = new Sprite(wallRegion);
		
		this.camera = new OrthographicCamera(1280, 800);
		this.camera.zoom = 0.05f;
		this.camera.position.set(18, 18, 0);
		this.camera.update();
	}
	
	public void render(Rectangle usableScreen) {
		update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		
		for (int y = 0; y < level.map().length; y++) {
			for (int x = 0; x < level.map()[0].length; x++) {
				Cell c = level.map()[y][x];
				if (c instanceof Wall$) {
					spriteBatch.draw(wallSprite, x, y, 1, 1);
				}
				else if (c instanceof Floor$) {
					spriteBatch.draw(floorSprite, x, y, 1, 1);
				}
			}
		}
		spriteBatch.end();
	}
	
	public void update() {
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			camera.position.y += CAMERA_SPEED;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			camera.position.y -= CAMERA_SPEED;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			camera.position.x += CAMERA_SPEED;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			camera.position.x -= CAMERA_SPEED;
		}
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		camera.update();
	}
}
