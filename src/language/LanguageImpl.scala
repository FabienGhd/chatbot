package language

object LanguageImpl {

  sealed trait Langue
  case object FR extends Langue
  case object EN extends Langue
  case object AL extends Langue
  case object ES extends Langue
  case object IT extends Langue
  case object NoneLang extends Langue
  
  val langs: List[Langue] = List(FR, EN, AL, ES, IT)
  
  var poliFR: List[String] = List("bonjour", "salut", "bonsoir")
  var poliEN: List[String] = List("hi", "hello", "morning", "evening", "afternoon", "hey")
  var poliAL: List[String] = List("hallo", "guten", "morgen", "tag", "abend")
  var poliES: List[String] = List("hola", "buenos", "dias")
  var poliIT: List[String] = List("buongiorno", "ciao", "salve", "buon", "pomeriggio", "buonasera", "incantato")

  var rechercheFR: List[String] = List("recherche", "cherche", "ou", "est", "donc", "trouve", "trouver")
  var rechercheEN: List[String] = List("seek", "seeking", "search", "searching", "look", "looking", "where", "find")
  var rechercheES: List[String] = List("donde", "esta", "busco", "buscando")
  var rechercheAL: List[String] = List("wo", "ist", "suche", "suchen")
  var rechercheIT: List[String] = List("dove", "trova", "cerco", "cercando")

  var nomFR: List[String] = List("Francais", "francais")
  var nomEN: List[String] = List("English", "english")
  var nomES: List[String] = List("espanol", "Espanol")
  var nomIT: List[String] = List("italiano", "Italiano")
  var nomAL: List[String] = List("deutsch", "Deutsch", "deutch")

  var ExpressionFR: List[String] = List("oui", "non", "L'adresse de XXX est", "Je ne comprends pas votre demande",
    "Parlez-vous français?", "D'accord, quelle est votre demande?", "J'ai XXX réponses possibles", "Quel est votre choix?",
    "restaurant", "creperie", "pizzeria")

  var ExpressionEN: List[String] = List("yes", "no", "The address of XXX is", "I do not understand", "Do you speak english?",
    "OK, what is your query?", "I found XXX answers", "What is your choice?", "restaurant", "creperie", "pizzeria ")

  var ExpressionES: List[String] = List("si", "no", "La dirección de XXX es", "No comprendo", "Hablas español?",
    "Está bien, cuál es tu petición?", "Tengo XXX opciones", "Cuál es su elección?", "restaurante, creperie, pizzeria")

  var ExpressionAL: List[String] = List("ja", "nein", "Die adresse von XXX ist", "Ich verstehe nicht",
    "Sprechen Sie Deutsch?", "Okay, was ist Ihr Wunsch?", "Ich habe XXX Antworten", "Was ist Ihre Wahl?",
    "restaurant", "creperie", "pizzeria")

  var ExpressionIT: List[String] = List("si", "no", "Indirizzo di XXX è", "No capisco", "Parli italiano?",
    "Va bene, qual è la tua richiesta?", "qual è la tua richiesta?", "Ho XXX risposte", "Qual è la vostra scelta?", "ristorante", "creperie", "pizzeria")



  val data: Map[(Langue, String), List[String]] = Map(
    (FR, "poli") -> poliFR,
    (FR, "rech") -> rechercheFR,
    (FR, "nom") -> nomFR,
    (FR, "expr") -> ExpressionFR,
    
    (EN, "poli") -> poliEN,
    (EN, "rech") -> rechercheEN,
    (EN, "nom") -> nomEN,
    (EN, "expr") -> ExpressionEN,
    
    (ES, "poli") -> poliES,
    (ES, "rech") -> rechercheES,
    (ES, "nom") -> nomES,
    (ES, "expr") -> ExpressionES,
    
    (AL, "poli") -> poliAL,
    (AL, "rech") -> rechercheAL,
    (AL, "nom") -> nomAL,
    (AL, "expr") -> ExpressionAL,
    
    (IT, "poli") -> poliIT,
    (IT, "rech") -> rechercheIT,
    (IT, "nom") -> nomIT,
    (IT, "expr") -> ExpressionIT,
    )

  /**
   * @param word le mot à analyser
   * @param langCour la langue courante du robot
   * @return la langue du mot analysé, par défaut renvoie la langue courante
   */
  def wordRecog(word: String, langCour: Langue): Langue = {
    // Hardcode avant de trouver meilleure solution
    if(word == "si" || word == "Si") return langCour
    // On parcours data, une map
    for(text <- data) {
      if(text._2.contains(word)) return toLang(text)
    }
    NoneLang
  }

  /**
   * @param lang la langue choisie
   * @return l'expression de politesse dans la langue choisie (par défaut la première)
   */
  def poli(lang: Langue): String = data((lang, "poli"))(0)

  /**
   * @param lang la langue choisie
   * @return l'expression de recherche dans la langue choisie
   */
  def recherche(lang: Langue): String = ???

  /**
   * @param lang la langue choisie
   * @param index l'indice de l'expression dans la liste
   * @return l'expression de recherche dans la langue choisie
   */
  def expression(lang: Langue, index: Integer): String = data((lang, "expr"))(index)
  
  /*
   * @param lang la langue choisie
   * @return la nom de la langue
   */
  def demandeLangue(lang: Langue): List[String] = data(lang, "nom")

  /**
   * @param req la requête en cours
   * @param langTemp la langue temporaire du robot
   * @return vrai ssi req correspond à une confirmation
   */
  def estConfirmation(req: List[String], langTemp: Langue): Boolean = {
    req == List(expression(langTemp, 0))
  }

  /**
   * @param lang la langue choisie
   * @return la prochaine langue
   */
  def nextLang(lang: Langue): Langue = {
    lang match {
      case FR => EN
      case EN => ES
      case ES => AL
      case AL => IT
      case IT => FR
      case _ => NoneLang
    }
  }

  /*
   * @return la liste des mots d'interaction (politesse)
   */
  def getInteractWords(langCour: Langue): List[String] = {
    data(langCour, "poli")
  }
  
  /*
   * Fonction purement esthétique
   * @param data un n-uplet représentant une langue, le type d'expression et une liste d'expressions
   * @return la langue du n-uplet
   */
  def toLang(d: ((Langue, String), List[String])): Langue = {
    d._1._1
  }
  
  /*
   * Fonction purement esthétique
   * @param data un n-uplet représentant une langue, le type d'expression et une liste d'expressions
   * @return la liste d'expressions du n-uplet
   */
  def toListExpr(data: ((Langue, String), List[String])): List[String] = {
    data._2
  }
  
  /*
   * @return la liste de toutes les expressions dans toutes les langues (hors phrases du robot)
   */
  def listSentences(): List[String] = {
    var res: List[String] = List()
    val listType: List[String] = List("poli", "rech", "nom")
    for(lang <- langs) {
      for(typ <- listType) {
        res ++= data(lang, typ)
      }
    }
    res
  }

  
  
}
