package se.obtu.rogueahoy.model;

public enum Direction {
	NORTH("north"),
	SOUTH("south"),
	WEST("west"),
	EAST("east"),
	NORTH_WEST("north-west"),
	NORTH_EAST("north-east"),
	SOUTH_WEST("south-west"),
	SOUTH_EAST("south-east")
	;
	
	private String directionString;
	
	private Direction(String directionString) {
		this.directionString = directionString;
	}

	public String getDirectionString() {
		return directionString;
	}
}
