package database

import scala.io.Source

trait Database {

  def get(x: String): (String, String)
  
  def getBDD(): List[(String, String, String)]
  
  def getXML(): List[(String, String, String)]

}
