package language
import org.junit.Test
import org.junit.Assert._

class TestLanguage {

  // initialisation des objets sous test
  val l = LanguageImpl

  // TESTS

  @Test
  def test_universel {
    assertEquals(true, true) // Si ce test ne réussi pas, le problème vient d'autre part
  }
  
  @Test
  def wordRecog1 {
    assertEquals(LanguageImpl.EN, l.wordRecog("hello", LanguageImpl.FR))
  }
  
  @Test
  def wordRecog2 {
    assertEquals(LanguageImpl.ES, l.wordRecog("buenos", LanguageImpl.FR))
  }
  
  @Test
  def wordRecog3 {
    assertEquals(LanguageImpl.IT, l.wordRecog("ciao", LanguageImpl.FR))
  }
  
  @Test
  def wordRecog4 {
    assertEquals(LanguageImpl.NoneLang, l.wordRecog("qzerkyzg", LanguageImpl.FR))
  }
  
  @Test
  def wordRecog5 {
    assertEquals(LanguageImpl.FR, l.wordRecog("francais", LanguageImpl.FR))
  }
  
  @Test
  def wordRecog6 {
    assertEquals(LanguageImpl.ES, l.wordRecog("espanol", LanguageImpl.FR))
  }
  
  @Test
  def estConfirmation1 {
    assertEquals(true, l.estConfirmation(List("yes"), LanguageImpl.EN))
  }
  
  @Test
  def estConfirmation2 {
    assertEquals(true, l.estConfirmation(List("oui"), LanguageImpl.FR))
  }
  
  @Test
  def estConfirmation3 {
    assertEquals(false, l.estConfirmation(List("oui"), LanguageImpl.AL))
  }
  
  @Test
  def expression1 {
    assertEquals("Está bien, cuál es tu petición?", l.expression(LanguageImpl.ES, 5))
  }
  
}
