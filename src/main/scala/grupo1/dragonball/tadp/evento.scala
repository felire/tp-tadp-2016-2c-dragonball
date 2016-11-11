package grupo1.dragonball.tadp

import scala.util.Try


abstract class Resultado

case class Peleando(atacante:Guerrero, atacado:Guerrero) extends Resultado{
  def flatmap(f:Movimiento) = {
    atacante.estado match {
      case KO => this
      case Muerto => Ganador(atacado)
      case _ => atacado.estado match{
        case KO => this
        case Muerto => Ganador(atacante)
        case _ => f(atacante, atacado)
      }
    }
  }
}

case class Fallo(error:String) extends Resultado{ //Si hubo un error que sentido tiene guardar el estado de los guerreros?
  def flatmap(f:Movimiento) = this
}

case class Ganador(ganador: Guerrero) extends Resultado{
  def flatmap(f:Movimiento) = this
}