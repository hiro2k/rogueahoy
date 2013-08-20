package se.obtu.rogueahoy.renderers;

import rlforj.los.IFovAlgorithm;
import rlforj.los.ShadowCasting;
import se.obtu.rogueahoy.dungeons.Cell;
import se.obtu.rogueahoy.dungeons.Floor;
import se.obtu.rogueahoy.dungeons.Level;
import se.obtu.rogueahoy.dungeons.Wall;
import se.obtu.rogueahoy.model.entities.PlayerCharacter;
import se.obtu.rogueahoy.model.world.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class WorldRenderer extends Actor {

	private static final int TILE_SIZE = 16;
	private static final float CAMERA_SPEED = 1;
	public Level level;
	public OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Texture defaultTileset;
	private Sprite floorSprite;
	private Sprite wallSprite;
	private IFovAlgorithm fovAlgorithm;
	private GameState state;
	private Sprite pcSprite;
	private Sprite blankSprite;
	private PlayerCharacter playerCharacter;
	private float timeSinceLastUpdate = 301;
	
	public WorldRenderer(GameState state) {
		this.level = state.level();
		this.state = state;
		this.playerCharacter = state.playerCharacter();
		fovAlgorithm = new ShadowCasting();
		this.create();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		batch.end();
		render(new Rectangle(this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight()));
		batch.begin();
	}
	
	public void create() {
		spriteBatch = new SpriteBatch();
		defaultTileset = new Texture(Gdx.files.internal("data/16_tileset_transparent.png"));
		TextureRegion floorRegion = new TextureRegion(defaultTileset, 14 * TILE_SIZE, 2 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		TextureRegion wallRegion = new TextureRegion(defaultTileset, 3 * TILE_SIZE, 2*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		TextureRegion pcRegion = new TextureRegion(defaultTileset, 0, 4*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		TextureRegion blank = new TextureRegion(defaultTileset, 11*TILE_SIZE, 13*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		floorSprite = new Sprite(floorRegion);
		wallSprite = new Sprite(wallRegion);
		pcSprite = new Sprite(pcRegion);
		blankSprite = new Sprite(blank);
		
		this.camera = new OrthographicCamera(1280, 800);
		this.camera.zoom = 1/28f;
		this.camera.position.set(this.state.currentFocus().x(), this.state.currentFocus().y(), 0);
		this.camera.update();
	}
	
	public void render(Rectangle usableScreen) {
		update();
		this.clipBegin();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		
		for (int y = 0; y < level.map().length; y++) {
			for (int x = 0; x < level.map()[y].length; x++) {
				Cell c = level.map()[y][x];
				if (c.visited() && !level.currentFov()[y][x]) {
					spriteBatch.setColor(0.10f, 0.10f, 0.10f, 1);
					if (c instanceof Wall) {
						spriteBatch.draw(wallSprite, x, y, 1, 1);
					}
					else if (c instanceof Floor) {
						spriteBatch.draw(floorSprite, x, y, 1, 1);
					}
				}
				else if (level.currentFov()[y][x]) {
					spriteBatch.setColor(Color.WHITE);
					if (c instanceof Wall) {
						spriteBatch.draw(wallSprite, x, y, 1, 1);
					}
					else if (c instanceof Floor) {
						spriteBatch.draw(floorSprite, x, y, 1, 1);
					}
					
				}
			}
		}
		
		drawPc();
		
		spriteBatch.end();
		this.clipEnd();
	}
	
	private void drawPc() {
		Color originalColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.BLACK);
		spriteBatch.draw(blankSprite, playerCharacter.x(),
				playerCharacter.y(), 1, 1);
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(pcSprite, playerCharacter.x(),
				playerCharacter.y(), 1, 1);
		spriteBatch.setColor(originalColor);
	}
	
	public void update() {
		
		
		timeSinceLastUpdate += Gdx.graphics.getDeltaTime();
		int potentialX = this.state.playerCharacter().x(), potentialY = this.state.playerCharacter().y();
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			potentialY = state.playerCharacter().y() + 1;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			potentialY = state.playerCharacter().y() - 1;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			potentialX = state.playerCharacter().x() + 1;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			potentialX = state.playerCharacter().x() - 1;
		}
		
		if (!level.isObstacle(potentialX, potentialY) && timeSinceLastUpdate > 0.05f) {
			timeSinceLastUpdate = 0;
			this.state.playerCharacter().coordinates(potentialX, potentialY);
			this.camera.position.x = potentialX;
			this.camera.position.y = potentialY;
			this.camera.update();
		}
		
		level.resetFov();
		fovAlgorithm.visitFieldOfView(level, (int)camera.position.x, (int)camera.position.y, 10);
		
		camera.update();
	}
}
