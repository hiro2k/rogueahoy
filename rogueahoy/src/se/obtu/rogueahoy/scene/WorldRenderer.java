package se.obtu.rogueahoy.scene;

import se.obtu.rogueahoy.dungeons.Cell;
import se.obtu.rogueahoy.dungeons.Floor;
import se.obtu.rogueahoy.dungeons.Level;
import se.obtu.rogueahoy.dungeons.Wall;
import se.obtu.rogueahoy.model.entities.PlayerCharacter;
import se.obtu.rogueahoy.model.world.GameState;
import squidpony.squidgrid.fov.FOVSolver;
import squidpony.squidgrid.fov.ShadowFOV;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class WorldRenderer extends Actor {

	private static final int TILE_SIZE = 16;
	public Level level;
	public OrthographicCamera camera;
	private Texture defaultTileset;
	private Sprite floorSprite;
	private Sprite wallSprite;
	private GameState state;

	private PlayerCharacter playerCharacter;
	private float[][] levelResistanceMap;
	private float[][] lightMap;
	private FOVSolver solver;
	
	public WorldRenderer(GameState state) {
		this.level = state.level();
		this.state = state;
		this.playerCharacter = state.playerCharacter();
		this.levelResistanceMap = this.level.asResistanceMap();
		solver = new ShadowFOV();
		this.lightMap = solver.calculateFOV(levelResistanceMap, this.playerCharacter.x(), this.playerCharacter.y(), 10);
		this.create();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		this.lightMap = this.solver.calculateFOV(
				this.levelResistanceMap, 
				(int)camera.position.x, 
				(int)camera.position.y, 10);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float parentAlpha) {
		super.draw(spriteBatch, parentAlpha);
		Matrix4 originalProjection = spriteBatch.getProjectionMatrix();
		ScissorStack.pushScissors(new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		spriteBatch.setProjectionMatrix(camera.combined);
		
		for (int y = 0; y < level.map().length; y++) {
			for (int x = 0; x < level.map()[y].length; x++) {
				Cell c = level.map()[y][x];
				if (this.lightMap[x][y] > 0) {
					c.visited_$eq(true);
					spriteBatch.setColor(Color.WHITE);
					if (c instanceof Wall) {
						spriteBatch.draw(wallSprite, x, y, 1, 1);
					}
					else if (c instanceof Floor) {
						spriteBatch.draw(floorSprite, x, y, 1, 1);
					}
					
				}
				else if (c.visited() && this.lightMap[x][y] <= 0) {
					spriteBatch.setColor(0.10f, 0.10f, 0.10f, 1);
					if (c instanceof Wall) {
						spriteBatch.draw(wallSprite, x, y, 1, 1);
					}
					else if (c instanceof Floor) {
						spriteBatch.draw(floorSprite, x, y, 1, 1);
					}
				}
			}
		}
		spriteBatch.setProjectionMatrix(originalProjection);
		ScissorStack.popScissors();
	}
	
	public void create() {
		defaultTileset = new Texture(Gdx.files.internal("data/16_tileset_transparent.png"));
		TextureRegion floorRegion = new TextureRegion(defaultTileset, 14 * TILE_SIZE, 2 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		TextureRegion wallRegion = new TextureRegion(defaultTileset, 3 * TILE_SIZE, 2*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		floorSprite = new Sprite(floorRegion);
		wallSprite = new Sprite(wallRegion);
		
		this.camera = new OrthographicCamera(1280f, 800f);
		this.camera.zoom = 1/28f;
		this.camera.position.set(this.state.currentFocus().x(), this.state.currentFocus().y(), 0);
		this.camera.update();
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}
}
