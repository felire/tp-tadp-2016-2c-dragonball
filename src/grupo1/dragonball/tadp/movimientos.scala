package grupo1.dragonball.tadp

import scala.util.Try

trait Movimiento{
    def apply(atacante :Guerrero,  atacado : Guerrero) : Resultado
    
}

case object DejarseFajar extends Movimiento{
    def apply(atacante :Guerrero,  atacado : Guerrero)={
      atacante.estado match{
        case Luchando => new Resultado(atacante.copy(estado = Fajadas(1)),atacado)
        case Fajadas(cantidad) => new Resultado(atacante.copy(estado = Fajadas(cantidad + 1)), atacado)
        case _ => new Resultado(atacante, atacado)
      }
    }  
}

case object CargarKi extends Movimiento{  
   def apply(atacante :Guerrero,  atacado : Guerrero)= {
       atacante.especie match{
        case Androide => new Resultado(Try(throw new Exception("Los androides no pueden cargar ki")),atacado)
        case Saiyajin(SuperSaiyajin(nivel),_) => new Resultado(atacante.modificarEnergia(150*nivel), atacado)
        case _ => new Resultado(atacante.modificarEnergia(100), atacado)
      }
   }
}

case class UsarItem(item: Item) extends Movimiento{
  def apply(atacante :Guerrero,  atacado : Guerrero) = {
    item.apply(atacante,atacado)
  }
}

case object ComerseAlOponente extends Movimiento{
  def apply(atacante :Guerrero,  atacado : Guerrero) = {
    atacante.especie match{
      case Monstruo(tipoDigestivo) => atacado.especie match{
        case Androide => new Resultado(atacante.adquirirMovimientos(tipoDigestivo,atacado.movimientos), atacado)
        case _ if(atacante.energia >= atacado.energia) => new Resultado(atacante.adquirirMovimientos(tipoDigestivo,atacado.movimientos),atacado)
        case _ => new Resultado(atacante,atacado)
      }
      case _ => new Resultado(Try(throw new Exception("Solo los monstruos pueden comer")),atacado)
    }
  }
}

case object ConvertirseEnMono extends Movimiento
{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match{
      case Saiyajin(Normal, true) if(atacante.tengoItem(FotoDeLaLuna)) => new Resultado(atacante.copy(especie = Saiyajin(Mono, true)),atacado)
      case Saiyajin(SuperSaiyajin(nivel),true) => new Resultado(atacante.copy(especie = Saiyajin(Mono,true), energiaMax = atacante.energiaMax/(5*nivel)),atacado)
      case _ => new Resultado(Try(throw new Exception("No puede convertirse en mono")),atacado)
    }
  }
}

case object ConvertirseEnSuperSaiyajin extends Movimiento{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match
    {
      case Saiyajin(Normal,cola) if(atacante.energia > atacante.energiaMax/2) => new Resultado(atacante.copy(especie=Saiyajin(SuperSaiyajin(1),cola), energiaMax=atacante.energiaMax*5),atacado)
      case Saiyajin(SuperSaiyajin(nivel),cola) if(atacante.energia > atacante.energiaMax/2)=> new Resultado(atacante.copy(especie=Saiyajin(SuperSaiyajin(nivel+1),cola),energiaMax=atacante.energiaMax*5*nivel),atacado)
      case _ => new Resultado(Try(throw new Exception("No es Saiyajin")),atacado)
    }
  }
}


case class Fusion(companiero: Guerrero) extends Movimiento{
   def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match{
      case Humano | Namekusein | Saiyajin(_,_) =>  companiero.especie match{
        case Humano | Namekusein | Saiyajin(_,_) => new Resultado(atacante.copy(especie = GuerreroFusionado(atacante),movimientos = atacante.getMovimientos ++ companiero.getMovimientos, energia = atacante.energia + companiero.energia, energiaMax = atacante.energiaMax + companiero.energiaMax),atacado)
        case _ => new Resultado(Try(throw new Exception("No se pudo fusionar")), atacado)
      }
      case _ => new Resultado(Try(throw new Exception("No se pudo fusionar")), atacado)
    }
  }
}

case class Magia(poderMistico: (Guerrero,Guerrero)=>(Guerrero,Guerrero)) extends Movimiento{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    val (atacanteMod, atacadoMod) = poderMistico(atacante, atacado)
    atacante.especie match{
      case Monstruo(_) | Namekusein => new Resultado(atacanteMod, atacadoMod)
      case _ if(atacante.tieneEsferas) => new Resultado(atacanteMod, atacadoMod)
      case _ => new Resultado(Try(throw new Exception("No se pudo tirar la magia")), atacado)
    }
  }
}

abstract class Ataque extends Movimiento

case object Explotar extends Ataque{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match{
      case Androide | Monstruo(_) =>
        var energiaNva = 0
        var mul = 0
        atacante.especie match{
          case Monstruo(_) => mul = 2
          case Androide => mul = 3
        }
        energiaNva = 0 max (atacado.energia - atacante.energia * mul)
        atacado.especie match{
          case Namekusein => energiaNva = energiaNva max 1
        }
        new Resultado(atacante.copy(energia = 0, estado = Muerto),atacado.copy(energia = energiaNva))
      case _ => new Resultado(Try(throw new Exception("No pudo explotar")), atacado)
    }
  }
}
/*
case object Onda extends Ataque{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    
  }
}
*/
case object MuchosGolpesNinja extends Ataque{
   def apply(atacante: Guerrero, atacado: Guerrero)={
     atacante.especie match{
       case Humano => atacado.especie match{
         case Androide => new Resultado(atacante.copy(energia=atacante.energia-10), atacado)
         case _ => verificarEnergias (atacante,atacado)
       }
       case _ => verificarEnergias (atacante,atacado)
     }
  }
   
  def verificarEnergias (atacante:Guerrero, atacado:Guerrero) :Resultado={
    if (atacado.energia > atacante.energia) return new Resultado(atacante.copy(energia=atacante.energia-20),atacado)
    else return new Resultado(atacante,atacado.copy(energia=atacado.energia-20))
  }
}
/*
case object Genkidama extends Ataque{
   def apply(atacante: Guerrero, atacado: Guerrero)={
    
  }
}*/



