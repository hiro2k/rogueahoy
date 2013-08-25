package se.obtu.rogueahoy.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import se.obtu.rogueahoy.model.world.GameState
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.Gdx
import se.obtu.rogueahoy.GameResources

class SidebarGroup(var gameState: GameState) extends Group {

	var playerCharacter = gameState.playerCharacter;
	var table = new Table();
	table.setBounds(981, 0, 299, 800);
	table.setClip(true);
	table.top().left();
	var skin = GameResources.asset[Skin]("data/uiskin.json");
	var label = new Label(gameState.playerCharacter.name, skin);
	table.add(label).expandX().left().colspan(2);
	table.row();
	var hpLabel = new Label(s"HP: ${playerCharacter.currentHp}/${playerCharacter.maxHp}", skin);
	var mpLabel = new Label(s"MP: ${playerCharacter.currentMp}/${playerCharacter.maxMp}", skin);
	var strLabel = new Label(s"Str: ${playerCharacter.strength}", skin);
	var dexLabel = new Label(s"Dex: ${playerCharacter.dexterity}", skin);
	var intLabel = new Label(s"Int: ${playerCharacter.intelligence}", skin);
	table.add(hpLabel).left().padRight(16f);
	table.add(mpLabel).left().expandX();
	table.row();
	table.add(strLabel).left();
	table.row();
	table.add(dexLabel).left();
	table.row();
	table.add(intLabel).left();
	this.addActor(table);
	
}