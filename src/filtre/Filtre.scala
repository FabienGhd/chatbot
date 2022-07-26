package filtre
import database.DatabaseImpl
trait Filtre {

  /*
   * @return true si le mot est dans la base de donnée, false sinon
   * elle implementera F1 dans un premier temps puis F3
   */
  def estDansBDD(str: String): Boolean

  /*
   * @return le nom exact du lieu cherché et son adresse
   */
  def stringToBDD(str: String): (String, String)

  def filter(str: String): (String, String)
  def levenshtein(str1: String, str2: String): Int
  def min(i: Int*): Int
}
