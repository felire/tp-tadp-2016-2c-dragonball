package grupo1.dragonball.tadp

import scala.util.Try

abstract class Resultado{
  def flatmap(f:Movimiento) = this
  def mejorMov(criterio : Criterio) : Option[Movimiento] = None
  def pelearRound (f:Movimiento) : Resultado  = this
}

case class Peleando(atacante:Guerrero, atacado:Guerrero) extends Resultado{
  override def flatmap(f:Movimiento) = {
    atacado.estado match {
      case KO => this
      case Muerto => Ganador(atacante)
      case _ => atacante.estado match{
        case KO => this
        case Muerto => Ganador(atacado)
        case _ => f(atacante.deleteMov(f), atacado)
      }
    }
  }
       
  override def mejorMov(criterio : Criterio):Option[Movimiento] ={
    Option(atacante.movimientoMasEfectivoContra(atacado)(criterio))
  }
   
  override def pelearRound(f:Movimiento) : Resultado = {
    atacante.pelearRound(f, atacado)
  }
}

case class Fallo(error:String) extends Resultado{}

case class Ganador(ganador: Guerrero) extends Resultado{}

