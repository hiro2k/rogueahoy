package se.obtu.rogueahoy.test.dungeons;

import java.util.List;

import se.obtu.rogueahoy.dungeons.BspDungeonGeneration;
import se.obtu.rogueahoy.dungeons.DungeonPartition;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PartitionVisualizer extends Game {
	
	private static final int CAMERA_SPEED = 2;
	private static final float ZOOM_CHUNK = 0.05f;
	private static final float ZOOM_MIN = 0;
	private static final float ZOOM_MAX = 1f;
	List<DungeonPartition> dungeonPartitions;
	DungeonPartition rootPartition;
	ShapeRenderer shapeRenderer;
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	OrthographicCamera camera;
	int step = 0;
	private BspDungeonGeneration generator;

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "rogueahoy";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 800;
		cfg.resizable = false;
		
		new LwjglApplication(new PartitionVisualizer(), cfg);
	}

	@Override
	public void create() {
		generator = new BspDungeonGeneration();
		generator.setHeight(25);
		generator.setWidth(25);
		generator.setMinHeight(4);
		generator.setMinWidth(4);
		generator.setMaxHeight(10);
		generator.setMaxWidth(10);
		generator.setMinPartitionSize(16);
		generator.setMaxPartitionSize(25*25);
		rootPartition = generator.generate(1376447623589l);
		
		this.dungeonPartitions = generator.getLeafPartitions();
		camera = new OrthographicCamera(1280, 800);
		camera.position.set(15, 15, 0);
		camera.zoom = 0.05f;
		camera.update();
		
		shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void render() {
		super.render();
		this.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		renderDungeonPartition(rootPartition);
		
		shapeRenderer.end();
	}

	private void renderDungeonPartition(DungeonPartition partition) {
		if (partition.getLeftChild() != null) {
			renderDungeonPartition(partition.getLeftChild());
			renderDungeonPartition(partition.getRightChild());
		}
		else {
			shapeRenderer.rect(partition.startX, partition.startY, partition.getWidth(), partition.getHeight());
		}
	}

	private void update() {
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
		
		if (Gdx.input.isKeyPressed(Keys.S)) {
			step = (step + 1) % this.dungeonPartitions.size();
		}
		
		if (Gdx.input.isKeyPressed(Keys.PLUS)) {
			camera.zoom = Math.min(ZOOM_MIN, camera.zoom - ZOOM_CHUNK);
		}
		
		if (Gdx.input.isKeyPressed(Keys.R)) {
			long seed = System.currentTimeMillis();
			System.out.println("Regenerating with seed: " + seed);
			this.rootPartition = generator.generate(seed);
		}
		
		camera.update();
	}
}
