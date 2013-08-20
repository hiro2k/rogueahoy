package rogueahoy;

import scala.Tuple2;
import se.obtu.rogueahoy.dungeons.DungeonGeneration;
import se.obtu.rogueahoy.dungeons.DungeonPartition;
import se.obtu.rogueahoy.dungeons.Level;
import se.obtu.rogueahoy.model.entities.PlayerCharacter;
import se.obtu.rogueahoy.model.world.GameState;
import se.obtu.rogueahoy.renderers.WorldRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class WorldAsActorTest extends Game {
	
	private Stage stage;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "rogueahoy";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 800;
		cfg.resizable = false;
		
		new LwjglApplication(new WorldAsActorTest(), cfg);
	}

	@Override
	public void create() {
		stage = new Stage();
		
		Tuple2<Level, DungeonPartition> world = DungeonGeneration.randomLevel();
		Level level = world._1;
		PlayerCharacter pc = new PlayerCharacter(level.entryPoint());
		
		GameState state = new GameState(pc, level, pc);
		
		WorldRenderer renderer = new WorldRenderer(state);
		renderer.setBounds(0, 0, 980, 800);
		stage.addActor(renderer);
	}
	
	@Override
	public void render() {
		super.render();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}
	
	

}
