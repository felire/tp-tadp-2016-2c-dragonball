package grupo1.dragonball.tadp
import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class test {
  @Test
  def prueba_test() = {
    var prueba = new Prueba()
    assertEquals(1,prueba.hola)
  }
}