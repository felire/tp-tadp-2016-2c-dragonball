package grupo1.dragonball.tadp

import scala.util.Try


abstract class Resultado2

case class Peleando(atacante:Guerrero, atacado:Guerrero) extends Resultado2{
  def map(f:(Guerrero,Guerrero) => (Guerrero,Guerrero)) = {
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

case class Fallo(error:String) extends Resultado2{ //Si hubo un error que sentido tiene guardar el estado de los guerreros?
  def map(f:(Guerrero,Guerrero) => (Guerrero,Guerrero)) = this
}

case class Ganador(ganador: Guerrero) extends Resultado2{
  def map(f:(Guerrero,Guerrero) => (Guerrero,Guerrero)) = this
}