package rogueahoy;

import se.obtu.rogueahoy.GameResources;
import se.obtu.rogueahoy.dungeons.DungeonGeneration;
import se.obtu.rogueahoy.model.world.GameState;
import se.obtu.rogueahoy.scene.PlayerCharacterActor;
import se.obtu.rogueahoy.scene.WorldRenderer;

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
		GameState startState = DungeonGeneration.randomStartState();
		WorldRenderer renderer = new WorldRenderer(startState);
		renderer.setBounds(0, 0, 980, 800);
		stage.addActor(renderer);
		
		GameResources.initializeAssetManager(true);
		PlayerCharacterActor pcActor = PlayerCharacterActor.apply(renderer.getCamera(), startState);
		stage.addActor(pcActor);
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
