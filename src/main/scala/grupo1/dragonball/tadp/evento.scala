package grupo1.dragonball.tadp

import scala.util.Try

case class Resultado (atacante : Try[Guerrero], atacado : Try[Guerrero]) {
  def map(m:Movimiento ):Resultado = {
     atacante.map(atacanteObj => atacanteObj.estado match{
            case KO => this
            case Muerto => this 
            case _ => atacado.map(atacadoObj => atacadoObj.estado match{
              case KO => this
              case Muerto => this
              case _ => m.apply(atacanteObj, atacadoObj)
          }
        ).getOrElse(this)
      }
    ).getOrElse(this)
  }
   def this (atacante : Guerrero, atacado : Guerrero) = this (Try(atacante), Try(atacado))
   def this (atacante : Try[Guerrero], atacado : Guerrero) = this (atacante, Try(atacado)) 
}


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

case class Fallo(atacante:Guerrero, atacado:Guerrero, error:String) extends Resultado2{
  def map(f:(Guerrero,Guerrero) => (Guerrero,Guerrero)) = this
}

case class Ganador(ganador: Guerrero) extends Resultado2{
  def map(f:(Guerrero,Guerrero) => (Guerrero,Guerrero)) = this
}