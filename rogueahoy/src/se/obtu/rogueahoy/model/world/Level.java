package se.obtu.rogueahoy.model.world;


public class Level {
	public Cell[][] cells;
	
	public Level(int cellWidth, int cellHeight) {
		cells = new Cell[cellWidth][cellHeight];
	}
}
