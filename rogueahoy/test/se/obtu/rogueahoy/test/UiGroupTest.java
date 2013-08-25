package se.obtu.rogueahoy.test;

import se.obtu.rogueahoy.GameResources;
import se.obtu.rogueahoy.dungeons.DungeonGeneration;
import se.obtu.rogueahoy.ui.UiGroup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class UiGroupTest extends Game {
	
	private UiGroup uiGroup;
	private Stage stage;
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "rogueahoy";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 800;
		cfg.resizable = false;
		
		new LwjglApplication(new UiGroupTest(), cfg);
	}
	
	@Override
	public void render() {
		if (Gdx.graphics.getDeltaTime() > 1/30) {
			super.render();
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			stage.act();
			stage.draw();
			
			Table.drawDebug(stage);
		}
	}

	@Override
	public void create() {
		GameResources.initializeAssetManager(true);
		this.uiGroup = new UiGroup(DungeonGeneration.randomStartStateWithoutLevel(), 0f, 0f, 1280f, 800f);
		uiGroup.sidebarGroup().table().debug();
		this.stage = new Stage();
		stage.addActor(uiGroup);
	}

}
