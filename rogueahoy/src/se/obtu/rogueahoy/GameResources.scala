package se.obtu.rogueahoy

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import scala.beans.BeanProperty
import com.badlogic.gdx.scenes.scene2d.ui.Skin

object GameResources {
	var assetManager: Option[AssetManager] = None;
	
	def initializeAssetManager(block: Boolean) {
		var am = new AssetManager();
		am.load("data/16_tileset_transparent.png", classOf[Texture]);
		am.load("data/uiskin.json", classOf[Skin]);
		
		if (block) {
			am.finishLoading();
		}
		
		assetManager = Some(am);
	}
	
	def getAssetManager() = assetManager.get;
	
	def asset[T](name: String): T = {
		assetManager.get.get[T](name);
	}
}