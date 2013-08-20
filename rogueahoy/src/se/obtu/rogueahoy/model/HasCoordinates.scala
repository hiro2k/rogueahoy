package se.obtu.rogueahoy.model

trait HasCoordinates {

	def coordinates: (Int, Int);
	
	def x = coordinates._1;
	def y = coordinates._2;
}