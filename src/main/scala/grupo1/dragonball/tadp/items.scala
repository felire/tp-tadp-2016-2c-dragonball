package grupo1.dragonball.tadp

import scala.util.Try

abstract class Item
{
   def apply(atacante :Guerrero,  atacado : Guerrero): Resultado2
}

abstract class Arma extends Item{

}

class Roma extends Arma
{
  def apply(atacante :Guerrero,  atacado : Guerrero) = {
    atacado.especie match
    {
      case Androide => Peleando(atacante,atacado)
      case _ if(atacado.energia < 300) => Peleando(atacante,atacado.cambiarEstado(KO))
      case _ => Peleando(atacante,atacado)
    }
  }
}


class Filosa extends Arma
{
  
    def apply(atacante :Guerrero,  atacado : Guerrero) = {
    var kiARestar = atacante.energia/100
    atacado.especie match
    {
      case Saiyajin(Mono,true) => Peleando(atacante,atacado.copy(energia = 1, especie = Saiyajin(Normal, false), estado = KO))
      case Saiyajin(estado,true) => Peleando(atacante,atacado.copy(energia = 1, especie = Saiyajin(estado, false)))
      case _ => Peleando(atacante,atacado.copy(energia = atacado.energia - kiARestar))
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
        case Humano => Peleando(atacante,atacado.copy(energia = atacado.energia - 20))
        case Namekusein => atacado.estado match{
          case KO => Peleando(atacante,atacado.copy(energia = atacado.energia - 10))
          case _ => Peleando(atacante,atacado)
        }
        case _ => Peleando(atacante,atacado)
      }
    }
    else{
      Fallo("El arma no tiene balas")
    }
  }
}

class SemillaErmitanio extends Item
{
    def apply(atacante :Guerrero,  atacado : Guerrero) = {
      atacante.especie match{
        case Androide => Fallo("Un androide no debe comer semillas")
        case _ => Peleando(atacante.copy(energia = atacante.energiaMax),atacado)
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

