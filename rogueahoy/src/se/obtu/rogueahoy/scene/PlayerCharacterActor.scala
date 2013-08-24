package se.obtu.rogueahoy.scene

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import se.obtu.rogueahoy.model.world.GameState
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import se.obtu.rogueahoy.GameResources

object PlayerCharacterActor {
	val TILE_SIZE = 16;
	
	def apply(camera: OrthographicCamera, gameState: GameState): PlayerCharacterActor = {
		var assetManager = GameResources.assetManager.get;
		var texture = assetManager.get[Texture]("data/16_tileset_transparent.png");
		var pcRegion = new TextureRegion(texture, 0, 4*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		var blank = new TextureRegion(texture, 11*TILE_SIZE, 13*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		var sprite = new Sprite(pcRegion);
		var blankSprite = new Sprite(blank);
		new PlayerCharacterActor(sprite, blankSprite, camera, gameState);
	}
}

class PlayerCharacterActor(
		var sprite: Sprite,
		var blankSprite: Sprite,
		var camera: OrthographicCamera,
		gameState: GameState) extends GameActor(gameState)  {
		
	val player = gameState.playerCharacter;
	var timeSinceLastUpdate = 0f;
	
	override def act(delta: Float) {
				timeSinceLastUpdate += Gdx.graphics.getDeltaTime();
		var potentialX = this.player.x; 
		var potentialY = this.player.y;
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			potentialY = this.player.y + 1;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			potentialY = this.player.y - 1;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			potentialX = this.player.x + 1;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			potentialX = this.player.x - 1;
		}
		
		if (!gameState.level.isObstacle(potentialX, potentialY) && timeSinceLastUpdate > 0.05f) {
			timeSinceLastUpdate = 0;
			this.player.coordinates(potentialX, potentialY);
			this.camera.position.x = potentialX;
			this.camera.position.y = potentialY;
			this.camera.update();
		}
		
		camera.update();
	}
	
	override def draw(spriteBatch: SpriteBatch, parentAlpha: Float) {
		super.draw(spriteBatch, parentAlpha);
		var originalColor = spriteBatch.getColor();
		var originalProjection = spriteBatch.getProjectionMatrix();

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.setColor(Color.BLACK);
		spriteBatch.draw(blankSprite, player.x,
				player.y, 1, 1);
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(sprite, player.x,
				player.y, 1, 1);
		
		spriteBatch.setColor(originalColor);
		spriteBatch.setProjectionMatrix(originalProjection);
	}
}