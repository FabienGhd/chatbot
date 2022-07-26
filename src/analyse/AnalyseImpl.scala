package analyse

import filtre.FiltreImpl
import language.LanguageImpl._
import machine.MachineImpl

object AnalyseImpl extends Analyse {
  var langC: Langue = FR
  var langT: Langue = FR
  var isConfirmation: Boolean = false

  /**
   * Application générale de l'analyse
   * @param request la requête de l'utilisateur
   * @param lastLangCour la langue courante du robot
   * @param lastLangTemp la langue temporaire du robot
   * @return la réponse adéquate à la requête, en fonction du contexte
   */
  def request(request: String, lastLangCour: Langue, lastLangTemp: Langue): List[String] = {
    // On sépare la requête en liste de mots + on supprime les majuscules et accents
    val reqs: List[String] = skipAccents(request.toLowerCase).split("[\\W]").toList // Voir explication en bas

    langC = lastLangCour;
    // Nouvelle langue temporaire de la requête
    if (langRecognition(reqs, langC) != NoneLang) langT = langRecognition(reqs, langC)
    else langT = langC
    val isLangueDiff: Boolean = langT != lastLangCour
    // Si l'utilisateur demande à parler dans une certaine langue

    if (isConfirmation || demandeLangue(langC).contains(reqs(0))) {
      isConfirmation = true
      confirmationMode(reqs, langT, lastLangTemp)
    } else if (isLangueDiff) {
      langueDiffMode(langT)
    } else {
      normalMode(reqs, langC)
    }
  }

  /**
   * Cette fonction gère le mode 'confirmation' du robot
   * @param req une requête formatée
   * @param langTemp la langue temporaire du robot
   * @param lastLangTemp la derniere langue temporaire du robot
   * @return une demande de reqûete dans la nouvelle langue si confirmation de l'utilisateur
   * 				 une demande de confirmation dans la prochaine langue sinon
   */
  def confirmationMode(req: List[String], langTemp: Langue, lastLangTemp: Langue): List[String] = {
    if (estConfirmation(req, lastLangTemp)) {
      // Demande requête dans nouvelle langue
      MachineImpl.langCour = lastLangTemp
      isConfirmation = false
      List(expression(lastLangTemp, 5))
    } else {
      // Si aucune langue reconnue, on passe à la langue suivante
      // Si une certaine langue est reconnue, le robot prend cette langue (temporaire)
      if (langRecognition(req, langC) == NoneLang) {
        MachineImpl.langTemp = nextLang(lastLangTemp)
        List(expression(nextLang(lastLangTemp), 4))
      } else {
        MachineImpl.langTemp = langTemp
        List(expression(langTemp, 4))
      }
    }
  }

  /**
   * Cette fonction gère le mode 'changement de langue' du robot.
   * Elle demande une confirmation et change la langue temporaire du robot
   * @param langTemp la langue courante du robot
   * @return une demande de confirmation dans la langue temporaire
   */
  def langueDiffMode(langTemp: Langue): List[String] = {
    MachineImpl.langTemp = langTemp
    isConfirmation = true
    List(expression(langTemp, 4)) // Demande confirmation
  }

  /**
   * Cette fonction gère le mode 'normal' du robot, quand il n'est ni en confirmation, ni en changement de langue
   * @param req une requête formatée
   * @param langCour la langue courante du robot
   * @return la liste des adresses qui correspondent à la recherche + une interaction si besoin
   */
  def normalMode(req: List[String], langCour: Langue): List[String] = {
    val answer: List[String] = interaction(req, langCour)
    val adress: List[(String, String)] = adressMatch(req)
    // Sépare le " XXX "
    val adress_string: List[String] = expression(langCour, 2).split("\\sXXX\\s").toList

    // Si adresse trouvee on renvoit interaction + premiere adresse
    if (!adress.isEmpty) {
      answer ++ List(adress_string(0) + " " + adress.head._1 + " " + adress_string(1) + " : " + adress.head._2)
    } // Si req est uniquement interaction : renvoit uniquement politesse
    else if (onlyInteraction(req, langCour)) answer
    // Sinon, renvoit answer + "je ne comprends pas.."
    else answer ++ List(expression(langCour, 3))
  }

  /**
   * @param req une requête formatée
   * @return La langue de la phrase
   * @note On part du principe que l'utilisateur écrit sa requête dans une unique langue
   * 			 (peut se changer facilement si besoin)
   */
  def langRecognition(req: List[String], langCour: Langue): Langue = {
    for (word <- req) {
      if (wordRecog(word, langCour) != NoneLang) return wordRecog(word, langCour)
    }
    NoneLang
  }

  /**
   * @param req une requête formatée
   * @return la liste des adresses qui correspondent à la recherche
   */
  def adressMatch(req: List[String]): List[(String, String)] = {
    var adress: List[(String, String)] = List()

    // On parcours les mots de la req (V1)
    for (word <- req) {
      // Si adresse trouvée
      if (FiltreImpl.filter(word) != ("", "")) {
        adress ::= FiltreImpl.filter(word)
      }
    }
    // V2 si rien trouvé
    if(adress.isEmpty) {
      if (FiltreImpl.getList(req) != ("", "")) 
        adress ::= FiltreImpl.getList(req)
    }
    
    adress
  }

  /*
   * @return true si req ne contient qu'un mot d'interaction, false sinon
   */
  def onlyInteraction(req: List[String], langCour: Langue): Boolean = {
    if (interaction(req, langCour) != Nil && req.length <= 1) true;
    else false
  }

  /**
   * @return Formule politesse dans langue donnée si interaction trouvée, Nil sinon
   */
  def interaction(req: List[String], langCour: Langue): List[String] = {
    // On reçoit la requete en minuscule
    val interactWords = getInteractWords(langCour)
    for (word <- interactWords) {
      for (w2 <- req) {
        if (req(0).length > 1 && FiltreImpl.levenshtein(word, w2) <= 1) {
          return List(poli(langCour).capitalize)
        }
      }
    }
    Nil
  }

  /*
   * Fonction utilitaire permettant de supprimer les accents
   * @return la même string mais sans les accents
   */
  def skipAccents(str: String): String = {
    str.replaceAll("[é|è|ê|ë]", "e").replaceAll("[â|à|ä]", "a").replaceAll("[û|ù|ü]", "u").replaceAll("[ô|ö|ó]", "o").replaceAll("ñ", "n").replaceAll("ç", "c")
  }

  def reinit = {
    langC = FR
    langT = FR
    isConfirmation = false
  }

  /*
   * Quelques parti pris :
   * Si la phrase contient plusieurs mots contenus dans la BDD, c'est le premier qui sortira
   */

  /* [W\\] Regex : match sur tous les mots (erreur sur ', ' -> a corriger?)
   * Source : https://www.tutorialspoint.com/scala/scala_regular_expressions.htm
   */

  // V1 (utile pour les tests)
  def request(request: String): List[String] = {
    List()
  }

}
