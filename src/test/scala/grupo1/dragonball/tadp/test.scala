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
    val plan = goku.planDeAtaque(vegeta, 10)(queNoLoMate)
    val resultado = goku.pelearContra(vegeta)(plan)
    resultado match{
      case Ganador(a) => assertEquals(1, 1)
    }
  }
   
  @Test
  def seConvierteEnMono_test() = {
    var vegeta = new Guerrero(10, 10, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(10, 10, List[Item](),List[Movimiento](), Saiyajin(Normal, true), Luchando)
    vegeta = vegeta.addMovimiento(DejarseFajar)
    goku = goku.addItem(FotoDeLaLuna)
    goku = goku.addMovimiento(ConvertirseEnMono)
    val plan = goku.planDeAtaque(vegeta, 7)(queNoLoMate)
    val resultado = goku.pelearContra(vegeta)(plan)
    resultado match{
      case Peleando(Guerrero(_,_,_,_,Saiyajin(Mono,_),_),otro) => assertEquals(true, true)
    }
  }   
  /*santi: creo que el criterio queNoLoMate es el unico que anda bien*/
     
  @Test
  def Henkidama_test() = {
    var vegeta = new Guerrero(1000, 10, List[Item](),List[Movimiento](), new Saiyajin, Luchando)
    var goku = new Guerrero(10, 10, List[Item](),List[Movimiento](), Saiyajin(SuperSaiyajin(1), true), Fajadas(3))
    vegeta = vegeta.addMovimiento(DejarseFajar)
    goku = goku.addMovimiento(Genkidama)
    val plan = goku.planDeAtaque(vegeta, 7)(oponentesFuertes) /*nunca hace la Genkidama porque nunca se deja fajar primero*/
    val resultado = goku.pelearContra(vegeta)(plan)
    resultado match{
      case Ganador(gokusito) => assertEquals(true, true)
    }
  }
  
}

