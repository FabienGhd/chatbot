package filtre
import org.junit.Test
import org.junit.Assert._


class TestAnalyse {

  // initialisation des objets sous test
  val f = FiltreImpl

  // TESTS

  @Test
  def test_universel {
    assertEquals(true, true) // Si ce test ne réussi pas, le problème vient d'autre part
  }

  //Test 
  @Test
  def testLevenshtein_1 {
    assertEquals(1, f.levenshtein("mairie", "Mairie"))
  }
  
  @Test
  def testFilter_1 {
    assertEquals(("Mairie de Rennes", "Place de la Mairie"), f.filter("marie"))
  }
  
  @Test
  def testFilter_2 {
    assertEquals(("Gare SNCF", "19, Place de la Gare"), f.filter("gzre"))
  }
  
  @Test
  def testFilter_3 {
    assertEquals(("", ""), f.filter("gzreee"))
  }
  
//  @Test 
//  def testGetList1 {
//    assertEquals(("", ""), f.getList(List("piscine")))
//  }
  
//  @Test 
//  def testGetList2 {
//    assertEquals(("Piscine Villejean","1 SQUARE D'ALSACE"), f.getList(List("piscine", "villejean")))
//  }
////la piscine des gayeulles
//  @Test 
//  def testGetList3 {
//    assertEquals(("Piscine Gayeulles","16 AVENUE DES GAYEULLES,AVENUE DES GAYEULLES"), f.getList(List("la", "piscine", "des", "gayeulles")))
//  }
//  //40mcube
//  @Test 
//  def testGetList4 {
//    assertEquals(("Piscine Villejean","1 SQUARE D'ALSACE"), f.getList(List("40mcube")))
//  }
//  //je cherche le complexe sportif du moulin du comte
//  @Test 
//  def testGetList5 {
//    assertEquals(("Piscine Villejean","1 SQUARE D'ALSACE"), f.getList(List("complexe", "sportif", "du", "moulin", "du" ,"comte")))
//  }
  //la pisicine saint georges
   @Test 
  def testGetList6 {
    assertEquals(("Piscine Villejean","1 SQUARE D'ALSACE"), f.getList(List("la", "pisicine","saint" ,"georges")))
  }
   //fret sndf
    @Test 
  def testGetList7 {
    assertEquals(("Piscine Villejean","1 SQUARE D'ALSACE"), f.getList(List("fret", "sndf")))
  }
}
