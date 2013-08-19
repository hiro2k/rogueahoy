package se.obtu.rogueahoy.model.world;

public class Cell {
	public static enum CellType {
		Floor,
		Wall,
		Empty
	}
	
	public CellType cellType;
}
