package grupo1.dragonball.tadp

import scala.util.Try

case class Resultado (atacante : Try[Guerrero], atacado : Try[Guerrero]) {
  def map(f: (Guerrero, Guerrero) => Resultado ):Resultado = {
     atacante.map(atacanteObj => atacanteObj.estado match{
            case KO => this
            case Muerto => this 
            case _ => atacado.map(atacadoObj => atacadoObj.estado match{
              case KO => this
              case Muerto => this
              case _ => f.apply(atacanteObj, atacadoObj)
          }
        ).getOrElse(this)
      }
    ).getOrElse(this)
  }
}


