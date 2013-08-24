package se.obtu.rogueahoy.ui

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class UiGroup extends Group {
	var table = new Table();
	table.setBounds(981, 0, 299, 800);
	table.setClip(true);
	table.top().left();
	
	var skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	var label = new Label("Hrongar the Barbarian", skin);
	table.add(label).expandX().left().colspan(2);
	table.row(); 	
	table.add(new Label("HP: 20/25", skin)).left();
	table.add(new Label("MP: 7/10", skin)).expandX();
	table.debug();
	
	this.addActor(table);
	
	override def draw(batch: SpriteBatch, parentAlpha: Float) {
		super.draw(batch, parentAlpha);
	}
}