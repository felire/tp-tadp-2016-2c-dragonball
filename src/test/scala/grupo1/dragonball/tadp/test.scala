package grupo1.dragonball.tadp

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class test {
   @Test
  def planDeAtaque_test() = {
    var vegeta = new Guerrero(10, 10, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(10, 10, List[Item](),List[Movimiento](), Saiyajin(SuperSaiyajin(4), false), Luchando)
    vegeta = vegeta.addMovimiento(DejarseFajar)
    //vegeta = vegeta.addMovimiento(ContraAtacar)
    //vegeta = vegeta.addMovimiento(Onda(1))
    //vegeta = vegeta.addMovimiento(MuchosGolpesNinja)
    goku = goku.addMovimiento(MuchosGolpesNinja)
    goku = goku.addMovimiento(ConvertirseEnSuperSaiyajin)
    //goku = goku.addMovimiento(ConvertirseEnMono)
    val plan = goku.planDeAtaque(vegeta, 10)(queNoLoMate)
    val resultado = goku.pelearContra(vegeta, plan)
    resultado match{
      case Ganador(a) => assertEquals(true, true)
    }
    assertEquals(true, plan.length > 0)
  }
  /*
   @Test
  def transformarseEnSaiyan_test() = {
    var saiyajin = new Saiyajin
    saiyajin.addMovimiento(new CargarKi)
    saiyajin.ejecutar(new CargarKi, null)
    assertEquals(150, saiyajin.ki)
    saiyajin.ejecutar(new ConvertirseEnSuperSaiyajin, null)
    saiyajin.ejecutar(new CargarKi, null)
    assertEquals(300, saiyajin.ki)
    saiyajin.ejecutar(new ConvertirseEnSuperSaiyajin, null)
    saiyajin.ejecutar(new CargarKi, null)
    assertEquals(600, saiyajin.ki) //nivel 3
  }
     @Test
  def transformarseEnMono_test() = {
    var saiyajin = new Saiyajin
    saiyajin.addItem(FotoDeLaLuna)
    saiyajin.ejecutar(new ConvertirseEnMono, null)
    assertEquals(true, saiyajin.estado match{
      case estado:Mono => true
      case _ => false
    })
  }
  @Test
  def usarRoma_test() = {
    var saiyajin = new Saiyajin
    var atacado = new Saiyajin
    var roma = new Roma
    saiyajin.addItem(roma)
    saiyajin.ejecutar(new UsarItem(roma), atacado)
    assertEquals(false, atacado.conciente)
  }
  @Test
  def usarFilosaContraSaiyanNormalConCola_test() = {
    var saiyajin = new Saiyajin
    var atacado = new Saiyajin
    var filosa = new Filosa
    saiyajin.addItem(filosa)
    saiyajin.ejecutar(new UsarItem(filosa), atacado)
    assertEquals(false, atacado.tieneCola)
    assertEquals(1, atacado.ki)
  }
  
   @Test
  def usarFilosaContraSaiyanMono_test() = {
    var saiyajin = new Saiyajin
    var atacado = new Saiyajin
    atacado.estado = new Mono
    var filosa = new Filosa
    saiyajin.addItem(filosa)
    saiyajin.ejecutar(new UsarItem(filosa), atacado)
    assertEquals(false, atacado.tieneCola)
    assertEquals(1, atacado.ki)
    assertEquals(true, atacado.estado match{
      case estado:Normal => true
      case _ => false
    })
    assertEquals(false, atacado.conciente)
  } 
    @Test
  def usarFuego_test() = {
    var saiyajin = new Saiyajin
    var atacado = new Humano
    var atacado2 = new Namekusein
    atacado2.conciente = false
    var fuego = new Fuego(10)
    saiyajin.addItem(fuego)
    saiyajin.ejecutar(new UsarItem(fuego), atacado)
    assertEquals(30, atacado.ki)
    assertEquals(9,fuego.balas)
    saiyajin.ejecutar(new UsarItem(fuego), atacado2)
    assertEquals(40, atacado2.ki)
    assertEquals(8,fuego.balas)
  } 
   
   
  @Test
  def usarSemilla_test() = {
    var saiyajin = new Saiyajin
    var atacado = new Saiyajin
    atacado.estado = new Mono
    var semilla = new SemillaErmitanio
    saiyajin.addItem(semilla)
    saiyajin.ejecutar(new UsarItem(semilla), atacado)
    assertEquals(100,saiyajin.ki)
  } 
  
  @Test
  def comerseAlOponenteAdquieroTodos_test() = {
    var monstruo = new Monstruo
    var atacado = new Saiyajin
    var atacado2 = new Saiyajin
    atacado2.addMovimiento(new ConvertirseEnSuperSaiyajin)
    atacado.addMovimiento(new CargarKi)
    monstruo.ejecutar(new ComerseAlOponente, atacado)
     monstruo.ejecutar(new ComerseAlOponente, atacado2)
    assertEquals(2,monstruo.getMovimientos().length)
  }
  
   @Test
  def comerseAlOponenteAdquieroUltimo_test() = {
    var monstruo = new Monstruo
    monstruo.metodoDeDigerir = new SoloDigieroUltimo
    var atacado = new Saiyajin
    var atacado2 = new Saiyajin
    atacado2.addMovimiento(new ConvertirseEnSuperSaiyajin)
    atacado.addMovimiento(new CargarKi)
    monstruo.ejecutar(new ComerseAlOponente, atacado)
     assertEquals(1,monstruo.getMovimientos().length)
     monstruo.ejecutar(new ComerseAlOponente, atacado2)
    assertEquals(1,monstruo.getMovimientos().length)
  }  
   
  @Test
  def testt() = {
    var r = 1 max 2
    assertEquals(1,r)
  }  */
    @Test
  def testtt() = {
    var list = List(): List[Movimiento]
    list = list.+:(null)
    assertEquals(1,list.length)
  } 
   
}

