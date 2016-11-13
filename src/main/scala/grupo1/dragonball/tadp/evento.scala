package grupo1.dragonball.tadp

import scala.util.Try

abstract class Resultado{
  def flatmap(f:Movimiento) : Resultado
  def pelearRound(f:Movimiento): Resultado
}

case class Peleando(atacante:Guerrero, atacado:Guerrero) extends Resultado{
  def flatmap(f:Movimiento) = {
    atacado.estado match {
      case KO => this
      case Muerto => Ganador(atacante)
      case _ => atacante.estado match{
        case KO => this
        case Muerto => Ganador(atacado)
        case _ => f(atacante, atacado)
      }
    }
  }
  
  
  def pelearRound(f:Movimiento) = {
    atacante.pelearRound(f, atacado)
  }
}

case class Fallo(error:String) extends Resultado{ //Si hubo un error que sentido tiene guardar el estado de los guerreros?
  def flatmap(f:Movimiento) = this
  def pelearRound (f:Movimiento) = this
}

case class Ganador(ganador: Guerrero) extends Resultado{
  def flatmap(f:Movimiento) = this
  def pelearRound (f:Movimiento) = this
}