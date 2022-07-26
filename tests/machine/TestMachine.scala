package machine
import org.junit.Test
import org.junit.Assert._

class TestIntegration {

  // initialisation des objets sous test
  val m= MachineImpl
  m.reinit
  
  // tests
  @Test
  def test1_1{    
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("Où est donc la Mairie de Rennes?")))
  }
  
  @Test
  def test1_2{    
    assertEquals(List("L'adresse de Gare SNCF est : 19, Place de la Gare"),m.test(List("Où est donc la Gare SNCF?")))
  }
  
  @Test
  def test1_3{    
    assertEquals(List("L'adresse de Gare SNCF est : 19, Place de la Gare"),m.test(List("Où est la Gare")))
  }
  
  @Test
  def test1_4{    
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Où est la gur")))
  }
  
  @Test
  def test4_1{
    m.test(List("hola"));
    val answer: List[String] =  m.test(List("si"));
    assertEquals(List("Está bien, cuál es tu petición?"), answer)
  }
  
}