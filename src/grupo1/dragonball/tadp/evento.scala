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
