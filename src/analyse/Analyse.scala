package analyse

trait Analyse {

  /*
   * La fonction analyse le contenu et renvoie une réponse adaptée en fonction
   * @return la réponse, une string
   */
  def request(request: String): List[String]

}
