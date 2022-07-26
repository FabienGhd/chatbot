package analyse
import org.junit.Test
import org.junit.Assert._
import language.LanguageImpl._

class TestAnalyse {

  // initialisation des objets sous test
  val a = AnalyseImpl

  // TESTS

  @Test
  def test_universel {
    assertEquals(true, true) // Si ce test ne réussi pas, le problème vient d'autre part
  }

  //Test F1
  @Test
  def test1_1 {
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), a.request("Je cherche la Mairie", FR, FR))
  }

  //Test F1
  @Test
  def test1_2 {
    assertEquals(List("L'adresse de Gare SNCF est : 19, Place de la Gare"), a.request("et la Gare?", FR, FR))
  }

  //Test F1
  @Test
  def test1_3 {
    assertEquals(List("Je ne comprends pas votre demande"), a.request("je cherche", FR, FR))
  }
  
  @Test
  def test1_4 {
    assertEquals(List("L'adresse de Théâtre La Paillette est : 2, Rue du Pré de Bris"), a.request("je cherche le Théâtre La Paillette", FR, FR))
  }
  
  @Test
  def test1_5 {
    assertEquals(List("L'adresse de Théâtre National de Bretagne est : 1, Rue Saint-Hélier"), a.request("Théâtre National de Bretagne", FR, FR))
  }
  
  //Test F2
  @Test
  def test2_1 {
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), a.request("Je cherche la Marie", FR, FR))
  }

  //Test F2
  @Test
  def test2_2 {
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), a.request("Je cherche la mairie", FR, FR))
  }

  //Test F2
  @Test
  def test2_3 {
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), a.request("marie", FR, FR))
  }

  //Test F3
  @Test
  def test3_1 {
    assertEquals(List("Bonjour", "L'adresse de Mairie de Rennes est : Place de la Mairie"), a.request("Bonjour je cherche la Mairie", FR, FR))
  }

  //Test F3
  @Test
  def test3_2 {
    assertEquals(List("Bonjour", "L'adresse de Théâtre National de Bretagne est : 1, Rue Saint-Hélier"), a.request("salut tnb", FR, FR))
  }
  
  @Test
  def test_bonjour1 {
    a.reinit
    assertEquals(List("Bonjour"), a.request("bonjour", FR, FR))
  }
  
  @Test
  def test_bonjour2 {
    a.reinit
    assertEquals(List("Bonjour"), a.request("Bonjour", FR, FR))
  }
  
  @Test
  def test_bonjour3 {
    a.reinit
    assertEquals(List("Bonjour"), a.request("bonjou", FR, FR))
  }
  
  @Test
  def test_bonjour4 {
    a.reinit
    assertEquals(List("Bonjour", "Je ne comprends pas votre demande"), a.request("salut, qzigziurg", FR, FR))
  }
  
  // TEST UNITAIRES POUR F4
  @Test
  def test_langRecognition1 {
    assertEquals(ES, a.langRecognition(List("buenos", "dias"), FR))
  }
  
  @Test
  def test_langRecognition2 {
    assertEquals(EN, a.langRecognition(List("seek", "playa"), FR))
  }
  
  @Test
  def test_langRecognition3 {
    assertEquals(EN, a.langRecognition(List("afakuygfaygf", "seek"), FR))
  }
  
  @Test
  def test_langRecognition4 {
    assertEquals(FR, a.langRecognition(List("francais"), FR))
  }
  
  @Test
  def test_langRecognition5 {
    assertEquals(EN, a.langRecognition(List("english"), FR))
  }
  
  @Test
  def test_langRecognition6 {
    assertEquals(ES, a.langRecognition(List("espanol"), FR))
  }
  
  // TESTS F4
  @Test
  def test4_1 {
    assertEquals(List("Hablas español?"), a.request("hola", FR, FR))
  }
  
  @Test
  def test4_2 {
    assertEquals(List("Sprechen Sie Deutsch?"), a.request("guten", FR, FR))
  }
  
  @Test
  def test4_3 {
    assertEquals(List("Parli italiano?"), a.request("ciao", EN, EN))
  }
  
  @Test
  def test4_4 {
    assertEquals(List("Do you speak english?"), a.request("seek", AL, AL))
  }
  
  @Test
  def test4_5 {
    assertEquals(List("Parlez-vous français?"), a.request("oui", AL, IT))
  }
  
  @Test
  def test4_6 {
    val answer: List[String] = a.request("si", FR, ES); // VERIF CONFIRMATION
    assertEquals(List("Está bien, cuál es tu petición?"), answer)
  }
  
  @Test
  def test4_7 {
    a.request("ciao", FR, FR);
    val answer: List[String] = a.request("zgzeghikhzeg", FR, IT);
    assertEquals(List("Parlez-vous français?"), answer)
  }
  
  @Test
  def test4_8 {
    a.request("hola", FR, FR);
    val answer: List[String] = a.request("si", FR, ES);
    assertEquals(List("Está bien, cuál es tu petición?"), answer)
  }
  
  @Test
  def test4_9 {
    a.request("I look for gare", EN, EN);
    val answer: List[String] = a.request("je cherche le tnb", EN, FR);
    assertEquals(List("Parlez-vous français?"), answer)
  }
  
  @Test
  def test4_10 {
    assertEquals(List("Parlez-vous français?"), a.request("français", FR, FR))
  }
  
  @Test
  def test4_11 {
    a.request("Bonjour", FR, FR);
    a.request("hola", FR, FR);
    a.request("si", FR, ES);
    a.request("busco gare", ES, ES);
    a.request("español?", ES, ES);
    //a.request("hola", ES, ES);
    val answer: List[String] = a.request("hola", ES, ES);;
    assertEquals(List("Hablas español?"), answer)
  }
  

}
