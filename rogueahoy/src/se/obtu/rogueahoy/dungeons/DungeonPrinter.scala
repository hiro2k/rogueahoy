package se.obtu.rogueahoy.dungeons

import java.io.File
import java.io.FileWriter
import se.obtu.rogueahoy.Log

object DungeonPrinter {

	def printLevel(file: File, level: Level): Unit = {
		var fileWriter = new FileWriter(file);
		var newLine = System.getProperty("line.separator");
		
		for (x <- Range(0, level.map.size).reverse) {
			for (y <- Range(0, level.map(x).size)) {
				val cell = level.map(x)(y);
				val char = cell match {
					case EmptyCell() => " "
					case Wall() => "#"
					case Floor() => "."
				}
				fileWriter.write(char);
			}
			fileWriter.write(newLine);
		}
		
		fileWriter.close();
	}
}