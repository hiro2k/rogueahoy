package se.obtu.rogueahoy

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import scala.beans.BeanProperty

object GameResources {
	var assetManager: Option[AssetManager] = None;
	
	def initializeAssetManager(block: Boolean) {
		var am = new AssetManager();
		am.load("data/16_tileset_transparent.png", classOf[Texture]);
		
		if (block) {
			am.finishLoading();
		}
		
		assetManager = Some(am);
	}
	
	def getAssetManager() = assetManager.get;
}