package database
import org.junit.Test
import org.junit.Assert._

class TestAnalyse {

  // initialisation des objets sous test
  val d = DatabaseImpl

  // TESTS

  @Test
  def test_universel {
    assertEquals(true, true) // Si ce test ne réussi pas, le problème vient d'autre part
  }

  @Test
  def test_get_1 {
    assertEquals(("Mairie de Rennes", "Place de la Mairie"), d.get("Mairie de Rennes"))
  }

  @Test
  def test_get_2 {
    assertEquals(("Théâtre La Paillette", "2, Rue du Pré de Bris"), d.get("La Paillette"))
  }

  @Test
  def test_get_3 {
    assertEquals(("Gare SNCF", "19, Place de la Gare"), d.get("Gare"))
  }

  @Test
  def test_get_4 {
    assertEquals(("Mairie de Rennes", "Place de la Mairie"), d.get("Hotel"))
  }

}
