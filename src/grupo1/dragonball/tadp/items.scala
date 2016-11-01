package grupo1.dragonball.tadp

import scala.util.Try

abstract class Item
{
   type f =(Guerrero, Guerrero)
   def apply(guerreros :f) : Resultado
}

abstract class Arma extends Item

class Roma extends Arma
{
  def apply(atacante :Guerrero,  atacado : Guerrero) = {
    atacado.especie match
    {
      case Androide => Resultado(Try(atacante),Try(atacado))
      case _ if(atacado.energia < 300) => Resultado(Try(atacante),Try(atacado.cambiarEstado(KO)))
      case _ => Resultado(Try(atacante),Try(atacado))
    }
  }
}


class Filosa extends Arma
{
  
    def apply(atacante :Guerrero,  atacado : Guerrero) = {
    var kiARestar = atacante.energia/100
    atacado.especie match
    {
      case Saiyajin(Mono,true) => Resultado(Try(atacante),Try(atacado.copy(energia = 1, especie = Saiyajin(Normal, false), estado = KO)))
      case Saiyajin(estado,true) => Resultado(Try(atacante),Try(atacado.copy(energia = 1, especie = Saiyajin(estado, false))))
      case _ => Resultado(Try(atacante),Try(atacado.copy(energia = energia - kiARestar)))
    }
  }
    
}

class Fuego(var balas:Int) extends Arma
{
  def tieneBalas :Boolean=
  {
    balas>0
  }
  
  
    def apply(atacante :Guerrero,  atacado : Guerrero) = {
    if(tieneBalas)
    {//si no, falta tirar excepcion o algo
      balas-=1
      atacado.especie match
      {
        case Humano => Resultado(Try(atacante),Try(atacado.copy(energia = energia - 20)))
        case Namekusein => atacado.estado match{
          case KO => Resultado(Try(atacante),Try(atacado.copy(energia = energia - 10)))
          case _ => Resultado(Try(atacante),Try(atacado))
        }
        case _ => Resultado(Try(atacante),Try(atacado))
      }
    }
    else{
      Resultado(Try(throw new Exception("El arma no tiene balas")),Try(atacado))
    }
  }
}

class SemillaErmitanio extends Item
{
    def apply(atacante :Guerrero,  atacado : Guerrero) = {
      atacante.especie match{
        case Androide => Resultado(Try(throw new Exception("Un androide no debe comer semillas")),Try(atacado))
        case _ => Resultado(Try(atacante.copy(energia = atacante.energiaMax)),Try(atacado))
      }
  }
}

object FotoDeLaLuna extends Item
{
    def apply(atacante :Guerrero,  atacado : Guerrero) = ???
}

class EsferaDelDragon(val estrella: Int) extends Item{
    def apply(atacante :Guerrero,  atacado : Guerrero) = ???
}

