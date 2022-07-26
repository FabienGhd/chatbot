package machine
import analyse.AnalyseImpl
import language.LanguageImpl

object MachineImpl extends MachineDialogue {
  var analyse = AnalyseImpl
  var langCour: LanguageImpl.Langue = LanguageImpl.FR
  var langTemp: LanguageImpl.Langue = LanguageImpl.FR

  /**
   * envoi d'une requête à la machine et réccupération de sa réponse
   *  @param s la requête
   *  @result la liste de réponses
   */
  def ask(s: String): List[String] = {
    println(s)
    val res = analyse.request(s, langCour, langTemp)
    println("\n" + res + "\n")
    res
  }

  // Pour la partie test par le client
  def reinit = {
    val newAnalyse = AnalyseImpl
    analyse = newAnalyse
    analyse.reinit
    langCour = LanguageImpl.FR
    langTemp = LanguageImpl.FR
  }
  def test(l: List[String]): List[String] = {
    var answer: List[String] = List()
    for (request <- l) {
      answer = answer ::: ask(request)
    }
    answer
  }
}
