package grupo1.dragonball.tadp

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class test {
   @Test
  def hayGanador_test() = {
    var vegeta = new Guerrero(10, 10, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(10, 10, List[Item](),List[Movimiento](), Saiyajin(Normal, true), Luchando)
    vegeta = vegeta.addMovimiento(DejarseFajar)
    goku = goku.addMovimiento(MuchosGolpesNinja)
    goku = goku.addMovimiento(ConvertirseEnSuperSaiyajin)
    goku = goku.addItem(FotoDeLaLuna)
    goku = goku.addMovimiento(ConvertirseEnMono)
    val plann = goku.planDeAtaque(vegeta, 10)(queNoLoMate)
    val ganador = plann.map(plan => goku.pelearContra(vegeta)(plan)).getOrElse(null)
    ganador match{
      case Ganador(a) => assertEquals(true, true)
      case _ => assertEquals(true, 42)
    }
  }
   
  @Test
  def seConvierteEnMono_test() = { 
    var vegeta = new Guerrero(10, 10, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(10, 10, List[Item](),List[Movimiento](), Saiyajin(Normal, true), Luchando)
    vegeta = vegeta.addMovimiento(DejarseFajar)
    goku = goku.addItem(FotoDeLaLuna)
    goku = goku.addMovimiento(ConvertirseEnMono)
    val plann = goku.planDeAtaque(vegeta, 1)(queNoLoMate)
    val result = plann.map(plan => goku.pelearContra(vegeta)(plan) ).getOrElse(null)
    result match{
      case Peleando(Guerrero(_,_,_,_,Saiyajin(Mono,_),_),otro) => assertEquals(true, true)
    }

  }   
  
     
  @Test
  def Henkidama_test() = {
    var vegeta = new Guerrero(1000, 10, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(10, 10, List[Item](),List[Movimiento](), Saiyajin(SuperSaiyajin(1), true), Fajadas(3))
    vegeta = vegeta.addMovimiento(DejarseFajar)
    goku = goku.addMovimiento(Genkidama)
    val plann = goku.planDeAtaque(vegeta, 7)(oponentesFuertes) /*nunca hace la Genkidama porque nunca se deja fajar primero*/
    val result = plann.map(plan =>goku.pelearContra(vegeta)(plan)).getOrElse(null)
    result match{
      case Ganador(gokusito) => assertEquals(true, true)
    } 
  }
  
  @Test
  def noHayMovsSuficientes_test() = {
    var vegeta = new Guerrero(1000, 10, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(10, 10, List[Item](),List[Movimiento](), Saiyajin(SuperSaiyajin(1), true), Fajadas(3))
    vegeta = vegeta.addMovimiento(DejarseFajar)
    goku = goku.addMovimiento(DejarseFajar)
    val plann = goku.planDeAtaque(vegeta, 1000)(oponentesFuertes) 
    val result = plann.map(plan =>goku.pelearContra(vegeta)(plan)).getOrElse(null)
    assertEquals(result, null)
  }
  
  @Test
  def hayMovsSuficientes_test() = {
    var vegeta = new Guerrero(1000, 10, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(10, 10, List[Item](),List[Movimiento](), Saiyajin(SuperSaiyajin(1), true), Fajadas(3))
    vegeta = vegeta.addMovimiento(DejarseFajar)
    goku = goku.addMovimiento(Genkidama)
    val plann = goku.planDeAtaque(vegeta, 1000)(oponentesFuertes) 
    val result = plann.map(plan =>goku.pelearContra(vegeta)(plan)).getOrElse(null)
    result match{
      case Ganador(_) => assertEquals(true, true) // lo mata de una y devuelve la lista "incompleta"
    }
    
  }
  
  @Test
  def oponentesDebiles_test() = {
    var vegeta = new Guerrero(1000, 1000, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(100, 100, List[Item](),List[Movimiento](), Saiyajin(SuperSaiyajin(1), true), Fajadas(3))
    vegeta = vegeta.addMovimiento(DejarseFajar)
    vegeta = vegeta.addMovimiento(Onda(21))
    vegeta = vegeta.addMovimiento(MuchosGolpesNinja)
    
    val resultado = vegeta.movimientoMasEfectivoContra(goku)(oponentesDebiles)
    val mov = resultado.map(x => x).getOrElse(null)
    mov match {
      case Onda(_) => assertEquals(1, 1)
    }
  }
  
  @Test
  def oponentesFuertes_test() = {
    var vegeta = new Guerrero(1000, 1000, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(100, 100, List[Item](),List[Movimiento](), Saiyajin(SuperSaiyajin(1), true), Fajadas(3))
    vegeta = vegeta.addMovimiento(DejarseFajar)
    vegeta = vegeta.addMovimiento(Onda(21))
    vegeta = vegeta.addMovimiento(MuchosGolpesNinja)
    
    val resultado = vegeta.movimientoMasEfectivoContra(goku)(oponentesFuertes)
    val mov = resultado.map(x => x).getOrElse(null)
    mov match {
      case DejarseFajar => assertEquals(1, 1)
    }
  }
  
  @Test
  def tacanio_test() = {
    var vegeta = new Guerrero(1000, 1000, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(100, 100, List[Item](),List[Movimiento](), Saiyajin(SuperSaiyajin(1), true), Fajadas(3))
    vegeta = vegeta.addItem(new SemillaErmitanio)
    vegeta = vegeta.addItem(new SemillaErmitanio)
    vegeta = vegeta.addMovimiento(UsarItem(new SemillaErmitanio))
    vegeta = vegeta.addMovimiento(DejarseFajar)
    
    val resultado = vegeta.movimientoMasEfectivoContra(goku)(tacanio)
    val mov = resultado.map(x => x).getOrElse(null)
    mov match {
      case DejarseFajar => assertEquals(1, 1)
    }
  }

  
}

