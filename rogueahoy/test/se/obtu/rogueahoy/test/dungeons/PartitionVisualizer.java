package se.obtu.rogueahoy.test.dungeons;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import se.obtu.rogueahoy.dungeons.BspDungeonGeneration;
import se.obtu.rogueahoy.dungeons.Cell;
import se.obtu.rogueahoy.dungeons.DungeonPartition;
import se.obtu.rogueahoy.dungeons.DungeonPrinter;
import se.obtu.rogueahoy.dungeons.DungeonTransformer;
import se.obtu.rogueahoy.dungeons.Level;
import se.obtu.rogueahoy.dungeons.PartitionJoiner;
import se.obtu.rogueahoy.dungeons.Partitions;
import se.obtu.rogueahoy.dungeons.Room;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PartitionVisualizer extends Game implements InputProcessor {
	
	private static final int CAMERA_SPEED = 2;
	List<DungeonPartition> dungeonPartitions;
	DungeonPartition rootPartition;
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	private BspDungeonGeneration generator;
	private BitmapFont font;
	private SpriteBatch batch;
	Random rand = new Random(1l);
	private boolean doStep = false;
	Deque<List<DungeonPartition>> steps;

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
		generator.setHeight(35);
		generator.setWidth(35);
		generator.setMinHeight(7);
		generator.setMinWidth(7);
		generator.setMaxHeight(17);
		generator.setMaxWidth(17);
		generator.setMinRoomHeight(6);
		generator.setMinRoomWidth(6);
		generator.setMinPartitionSize(7*7);
		generator.setMaxPartitionSize(17*17);
		rootPartition = generator.generate(1376728261342l);
		Cell[][] map = DungeonTransformer.transformDungeonToLevel(rootPartition);
		Level level = new Level(map);
		PartitionJoiner partitionJoiner = new PartitionJoiner(level);
		partitionJoiner.joinDungeon(rootPartition);
		
		DungeonPrinter.printLevel(new File("test.txt"), level);
		
		steps = new ArrayDeque<>();
		regenerateDungeon();
		
		camera = new OrthographicCamera(1280, 800);
		camera.zoom = 0.05f;
		camera.position.set(rootPartition.width()/2, rootPartition.height()/2, 0);
		camera.update();
		
		font = new BitmapFont(Gdx.files.internal("data/default.fnt"), false);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
	}

	private void regenerateDungeon() {
		List<DungeonPartition> currentStep = new ArrayList<>();
		currentStep.add(rootPartition);
		steps.add(currentStep);
	}
	
	@Override
	public void render() {
		super.render();
		this.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		for (List<DungeonPartition> step : this.steps) {
			for (DungeonPartition d : step) {
				renderDungeonPartition(d);
			}
		}
	}

	private void renderDungeonPartition(DungeonPartition partition) {
			
		if (!(partition.contents() instanceof Room)) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(partition.color());
			shapeRenderer.rect(partition.getStartX(), partition.getStartY(), partition.width(), partition.height());
			shapeRenderer.end();
		}
		else {
			Room room = (Room) partition.contents();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(room.startX(), room.startY(), room.getWidth(), room.getHeight());
			shapeRenderer.end();
			
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(partition.getColor());
			shapeRenderer.rect(partition.startX(), partition.startY(), partition.width(), partition.height());
			shapeRenderer.end();

			batch.setProjectionMatrix(camera.combined);
			font.setScale(camera.zoom*2, camera.zoom);
			batch.begin();
			font.draw(batch, Integer.toString(partition.getId()), partition.getStartX(), partition.getStartY() + 1);
			batch.end();
			
			camera.update();
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
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		camera.update();
		
		if (doStep) {
			List<DungeonPartition> cs = steps.getLast();
			List<DungeonPartition> nextStep = new ArrayList<>();
			for (DungeonPartition d : cs) {
				if (d.hasPartitions()) {
					Partitions p = (Partitions) d.contents();
					nextStep.add(p.leftChild());
					nextStep.add(p.rightChild());
				}
			}
			
			if (!nextStep.isEmpty()) {
				steps.addLast(nextStep);
			}
			
			doStep = false;
		}
	}

	@Override
	public boolean keyDown(int keycode) {
//		if (Keys.R == keycode) {
//			long seed = System.currentTimeMillis();
//			System.out.println("Regenerating with seed: " + seed);
//			this.rootPartition = generator.generate(seed);
//			
//			DungeonPrinter.printLevel(new File("test.txt"), level);
//			steps = new ArrayDeque<>();
//			regenerateDungeon();
//		}
//		
//		if (Keys.S == keycode) {
//			doStep = true;
//		}
//		
//		if (Keys.P == keycode) {
//			if (steps.size() > 1) {
//				steps.removeLast();
//			}
//		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
