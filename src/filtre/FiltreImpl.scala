package filtre
import database.DatabaseImpl
import language.LanguageImpl
object FiltreImpl extends Filtre {

  // Renvoie true si le mot est dans la base de donnée, false sinon
  def estDansBDD(str: String): Boolean = {
    DatabaseImpl.get(str) match {
      case ("", "") => false
      case (_, _)   => true
    }
  }

  /*
   * @return le nom exact du lieu cherché et son adresse
   */
  def stringToBDD(str: String): (String, String) = {
    DatabaseImpl.get(str)
  }
  
  def filterList(req: List[(String)]): (String,String) = {
    getList(req)
  }
  
  def getList(req: List[String]): (String, String) = {
    var res: List[(String, String)] = List()
    var answer: ((String,String),Int) = (("",""),0)
    var reponses_filtrees: List[(String, String, String)] = List()
    var baseXML = DatabaseImpl.getXML
    var syns = DatabaseImpl.getSyns
    
    for(word <- req) {
      if(word.length<4||LanguageImpl.rechercheAL.contains(word)||LanguageImpl.rechercheES.contains(word)||LanguageImpl.rechercheIT.contains(word)||LanguageImpl.rechercheEN.contains(word)||word=="trouver"||word=="hallo") {
        
      }
      else {
        var mot = word
        var motFinal= word
        for(syno <- syns) {
           if(levenshtein(mot.toLowerCase(), syno) == 1) {
             motFinal = syno
           }
        } 
      if(mot != motFinal) mot = motFinal
      if(syns.contains(mot)) {
        reponses_filtrees ++= baseXML.filter(i => levenshtein(mot.toLowerCase(), i._3) <= 1)
        
      }
      
     
    }
    res = reponses_filtrees.map(elem => (elem._1,elem._2))
    
     for(a <- res.groupBy(l => l).map(t => (t._1, t._2.length))) {
       if(a._2 > answer._2) answer = a
     }
      
      }
    answer._1
      
    
  }

  def filter(str: String): (String, String) = compare(str, DatabaseImpl.getBDD())

  //trouve si un mot est dans la base données ( prends en compte le filtre avec erreur
  // exemple si dans la base de données il y a "mairie", si l'utilisateur donne "marie" ou tout autre mot
  //comportant au maximum 1 erreur alors ça marchera et il reconnaitra le mot "mairie"
  private def compare(str: String, BDD: List[(String, String, String)]): (String, String) = {
    (str, BDD) match {
      case (str, (a, b, c) :: d) =>
        if (levenshtein(str, c) <= 1) (a, b)
        else compare(str, d)
      case (str, Nil) => ("", "") // Cas non trouvé
    }
  }
  

  //Fontion pour calculer la distance de levenshtein
  //https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Scala
  def levenshtein(str1: String, str2: String): Int = {
    val lenStr1 = str1.length
    val lenStr2 = str2.length

    val d: Array[Array[Int]] = Array.ofDim(lenStr1 + 1, lenStr2 + 1)

    for (i <- 0 to lenStr1) d(i)(0) = i
    for (j <- 0 to lenStr2) d(0)(j) = j

    for (i <- 1 to lenStr1; j <- 1 to lenStr2) {
      val cost = if (str1(i - 1) == str2(j - 1)) 0 else 1

      d(i)(j) = min(
        d(i - 1)(j) + 1, // supression
        d(i)(j - 1) + 1, // insertion
        d(i - 1)(j - 1) + cost // substitution
      )
    }

    d(lenStr1)(lenStr2)
  }

  def min(nums: Int*): Int = nums.min

}
