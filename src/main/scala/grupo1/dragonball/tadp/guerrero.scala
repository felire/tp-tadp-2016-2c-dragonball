package grupo1.dragonball.tadp
import scala.util.Try

case class Guerrero(energia : Int, energiaMax:Int, items: List[Item], movimientos: List[Movimiento], especie: Especie, estado: EstadoPelea)
{
  
  def getMovimientos : List[Movimiento]={
    this.movimientos ++ especie.getMovimientos
  }

 
  def adquirirMovimientos(tipo: MetodoDeDigerir, movimientos :List[Movimiento])={
    copy(especie = Monstruo(tipo.agregarMovimientos(movimientos)))
  }
  
  def cambiarEstado(estadoR: EstadoPelea) = {
    copy(estado = estadoR)
  }
  
  def tengoItem(item:Item):Boolean=
  {
    items.contains(item)
  }
  
  def tieneEsferas: Boolean = {
    (1 to 7).forall(numero =>
        items.exists ( item => 
          item match {
            case item:EsferaDelDragon => item.estrella == numero
            case _ => false
          }
        )
     )
   }

  def modificarEnergia (energy: Int)={
    copy(energia = this.energia + energy)
  }
  
   def movimientoMasEfectivoContra(atacado: Guerrero)(unCriterio: Criterio): Movimiento = {
      movimientos.maxBy{ mov => unCriterio(this,atacado,mov)}
   }
    

   def pelearRound(mov:Movimiento, oponente: Guerrero): Resultado = {
     Peleando(this, oponente).flatmap(mov)
                             .invertir() match{
       case Peleando(atacante, atacado) => Peleando(atacante, atacado).flatmap(atacante.movimientoMasEfectivoContra(atacado)(oponentesDebiles))
                                                                      .invertir()
       case otro => otro
     }
   }
   
   def pelearRoundConContra(mov:Movimiento, oponente: Guerrero): Resultado = {
     Peleando(this, oponente).flatmap(mov).flatmap(ContraAtacar)
   }
   
   def planDeAtaque(oponente: Guerrero, rounds: Int)(criterio: Criterio): Try[List[Movimiento]] = {
     //val (movimientos,pelea) = (List(): List[Movimiento],Peleando(this, oponente))
     val pelea: Resultado = Peleando(this,oponente)
     val movimientos = List(): List[Movimiento]
     val (resultado, movs) = (1 to rounds).foldLeft(pelea,movimientos){(peleasYMovs, round) =>
       val (peleaa,movimientoss) = peleasYMovs
       peleaa match{
         case Peleando(atacante, atacado) => 
           val mov = atacante.movimientoMasEfectivoContra(atacado)(criterio)
           (peleaa.flatmap(mov), movimientos.+:(mov))
         case otro => (peleaa, movimientos)
       }
     }
     resultado match{
       case Fallo(_) => Try(throw new Exception("La pelea a fallado"))
       case _ => Try(movs)
     }
   }
}

