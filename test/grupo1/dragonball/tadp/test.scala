package grupo1.dragonball.tadp
import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class test {
  @Test
  def prueba_test() = {
    var saiyajin = new Saiyajin
    var monstruo = new Monstruo
    saiyajin.addMovimiento(new CargarKi)
    saiyajin.ejecutar(new CargarKi, null)
    assertEquals(150, saiyajin.ki)
    saiyajin.ejecutar(new ConvertirseEnSuperSaiyajin, null)
    
  }
}