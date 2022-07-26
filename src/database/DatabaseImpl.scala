package database

import analyse.AnalyseImpl
import scala.io.Source
import scala.xml._

object DatabaseImpl extends Database {
  val dataList: List[(String, String, String)] = init()
  val dataListXML: List[(String, String, String)] = init():::initXML()
  val syns: Set[String] = initSyns()

  def getBDD(): List[(String, String, String)] = dataList
  def getXML(): List[(String, String, String)] = dataListXML
  def getSyns(): Set[String] = syns
  
  def initSyns(): Set[String] = {
    var res: Set[String] = Set()
    for(triplet <- dataListXML) {
      res ++= Set(triplet._3)
    }
    
    res
  }

  /*
   * Parcours la datalist afin de trouver le couple (nom, adresse) correspondant au synonyme
   * @return le couple en question, List[(String, String)]
   */
  def get(x: String): (String, String) = {
    dataList.filter(i => x.toLowerCase().equals(i._1.toLowerCase()) || x.toLowerCase().equals(i._3)).map(elem => (elem._1, elem._2)).head
  }

  /*
   * Initialise datalist
   * Pour la suite, on nommera le triplet ainsi : (keyword, addr, synonymous)
   * @return la liste de triplets correspondant au fichier DonneesInitiales.txt formaté
   */
  private def init(): List[(String, String, String)] = {
    var result: List[(String, String, String)] = List()
    val file: List[String] = Source.fromFile("doc/DonneesInitiales.txt").getLines().toList

    for (line <- file) {
      val currentL = line.split(";").toList
      for (synonymous <- currentL.slice(2, currentL.length)) {
        result = List((currentL(0), currentL(1), synonymous.toLowerCase())) ::: result
      }
    }
    result
  }
  
   private def initXML(): List[(String,String,String)] = {
     var result: List[(String,String,String)] = List()
     val x = XML.loadFile("Avatar2/vAr.xml")
     val stockOrg = (x \"answer"\"data"\"organization")
     for(org <- stockOrg) {
       val syn: List[String] = AnalyseImpl.skipAccents((org \"name").text.toLowerCase).split("[\\W]").toList
       for(addr <- syn) {
         if(addr.length >= 3 && addr != "des"&& addr != "trouvés") {
           result = result ::: List(((org \"name").text,(org \"addresses"\"address"\"street"\"number").text + " " + (org \"addresses"\"address"\"street"\"name").text, addr))
         }
       }       
     }
     result
  }

}
