package se.obtu.rogueahoy

import org.apache.logging.log4j.LogManager
import scala.beans.BeanProperty

object Log {
	@BeanProperty
	lazy val logger = LogManager.getLogger();
}