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
      getMovimientos.maxBy{ mov => unCriterio(this,atacado,mov)}
   }

   def deleteMov(movimiento: Movimiento) ={
     copy(movimientos = this.movimientos.filter { mov => !mov.equals(movimiento) })
   }
   
   def movimientoMasEfectivoContraOption(atacado: Guerrero)(unCriterio: Criterio): Option[Movimiento] = {
     Try(movimientos.maxBy{ mov => unCriterio(this,atacado,mov)}).toOption
   }

   def pelearRound(mov:Movimiento, oponente: Guerrero): Resultado = {
     Peleando(this, oponente).flatmap(mov).flatmap(ContraAtacar)
   }
   
   def planDeAtaque(oponente: Guerrero, rounds: Int)(criterio: Criterio): Try[List[Movimiento]] = {
     val peleaInicial: Resultado = Peleando(this,oponente)
     val movsInicial = List(): List[Movimiento]
     val (resultado, movs) = (1 to rounds).foldLeft(peleaInicial,movsInicial){(peleaYMovs, round) =>
       val (pelea,movimientos) = peleaYMovs
       val mov = pelea.mejorMov(criterio)
       (pelea.pelearRound(mov),movimientos.+:(mov))
     }
     resultado match{
       case Fallo(_) => Try(throw new Exception("La pelea a fallado"))
       case _ => Try(movs)
     }
   }
   
   def pelearContra(oponente: Guerrero, planAtaque: List[Movimiento]): Resultado = {
     planAtaque.foldLeft(Peleando(this, oponente) : Resultado){(pelea, movimiento) =>
       pelea.pelearRound(movimiento)
     }
   }
}

