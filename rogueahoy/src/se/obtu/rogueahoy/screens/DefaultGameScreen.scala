package se.obtu.rogueahoy.screens

trait DefaultGameScreen {
	def resume(){}
	def pause(){}
	def show(){}
	def hide(){}
	def dispose(){}
	def resize(width: Int, height:Int) {}
}