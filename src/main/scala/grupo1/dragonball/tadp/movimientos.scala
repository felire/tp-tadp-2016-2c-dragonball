package grupo1.dragonball.tadp

import scala.util.Try

trait Movimiento{
  def apply(atacante :Guerrero,  atacado : Guerrero) : Resultado    
}

case object DejarseFajar extends Movimiento{
  def apply(atacante :Guerrero,  atacado : Guerrero)={
    atacante.estado match{
      case Luchando => Peleando(atacante.copy(estado = Fajadas(1)),atacado)
      case Fajadas(cantidad) => Peleando(atacante.copy(estado = Fajadas(cantidad + 1)), atacado)
      case _ => Peleando(atacante, atacado)
    }
  }  
}

case object CargarKi extends Movimiento{  
  def apply(atacante :Guerrero,  atacado : Guerrero)= {
       atacante.especie match{
        case Androide => Fallo("Los androides no pueden cargar ki")
        case Saiyajin(SuperSaiyajin(nivel),_) => Peleando(atacante.modificarEnergia(150*nivel), atacado)
        case _ => Peleando(atacante.modificarEnergia(100), atacado)
      }
   }
}

case object ContraAtacar extends Movimiento{
  def apply(atacante :Guerrero,  atacado : Guerrero)= {
       val mov = atacado.movimientoMasEfectivoContra(atacante)(oponentesDebiles)
       mov.map(m => m.apply(atacado.deleteMov(m), atacante)).getOrElse(Peleando(atacado, atacante))
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
        case Androide => Peleando(atacante.adquirirMovimientos(tipoDigestivo,atacado.movimientos), atacado)
        case _ if(atacante.energia >= atacado.energia) =>Peleando(atacante.adquirirMovimientos(tipoDigestivo,atacado.movimientos),atacado)
        case _ => Peleando(atacante,atacado)
      }
      case _ => Fallo("Solo los monstruos pueden comer")
    }
  }
}

case object ConvertirseEnMono extends Movimiento{

  def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match{
      case Saiyajin(Normal, true) if(atacante.tengoItem(FotoDeLaLuna)) => Peleando(atacante.copy(especie = Saiyajin(Mono, true)),atacado)
      case Saiyajin(SuperSaiyajin(nivel),true) => Peleando(atacante.copy(especie = Saiyajin(Mono,true), energiaMax = atacante.energiaMax/(5*nivel)),atacado)
      case _ => Fallo("No puede convertirse en mono")
    }
  }
}

case object ConvertirseEnSuperSaiyajin extends Movimiento{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match
    {
      case Saiyajin(Normal,cola) if(atacante.energia > atacante.energiaMax/2) => Peleando(atacante.copy(especie=Saiyajin(SuperSaiyajin(1),cola), energiaMax=atacante.energiaMax*5),atacado)
      case Saiyajin(SuperSaiyajin(nivel),cola) if(atacante.energia > atacante.energiaMax/2)=> Peleando(atacante.copy(especie=Saiyajin(SuperSaiyajin(nivel+1),cola),energiaMax=atacante.energiaMax*5*nivel),atacado)
      case _ => Fallo("No es Saiyajin")
    }
  }
}


case class Fusion(companiero: Guerrero) extends Movimiento{
   def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match{
      case Humano | Namekusein | Saiyajin(_,_) =>  companiero.especie match{
        case Humano | Namekusein | Saiyajin(_,_) => Peleando(atacante.copy(especie = GuerreroFusionado(atacante),movimientos = atacante.getMovimientos ++ companiero.getMovimientos, energia = atacante.energia + companiero.energia, energiaMax = atacante.energiaMax + companiero.energiaMax),atacado)
        case _ => Fallo("No se pudo fusionar")
      }
      case _ => Fallo("No se pudo fusionar")
    }
  }
}

case class Magia(poderMistico: (Guerrero,Guerrero)=>(Guerrero,Guerrero)) extends Movimiento{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    val (atacanteMod, atacadoMod) = poderMistico(atacante, atacado)
    atacante.especie match{
      case Monstruo(_) | Namekusein => Peleando(atacanteMod, atacadoMod)
      case _ if(atacante.tieneEsferas) => Peleando(atacanteMod, atacadoMod)
      case _ => Fallo("No se pudo tirar la magia")
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
        Peleando(atacante.copy(energia = 0, estado = Muerto),atacado.copy(energia = energiaNva))
      case _ => Fallo("No pudo explotar")
    }
  }
}

case object MuchosGolpesNinja extends Ataque{
  def apply(atacante: Guerrero, atacado: Guerrero)={
     atacante.especie match{
       case Humano => atacado.especie match{
         case Androide => Peleando(atacante.copy(energia=atacante.energia-10), atacado)
         case _ => verificarEnergias(atacante,atacado)
       }
       case _ => verificarEnergias (atacante,atacado)
     }
  }
 
  def verificarEnergias(atacante:Guerrero, atacado:Guerrero) :Resultado={
    atacante.energia max atacado.energia match{
           case atacante.energia => Peleando(atacante,atacado.copy(energia=atacado.energia-20))
           case atacado.energia => Peleando(atacante.copy(energia=atacante.energia-20),atacado)
           case _ => Fallo("No se pudo golpear")
    }
  }
}


case class Onda(kiNecesario : Int) extends Ataque{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.energia max this.kiNecesario match{
      case this.kiNecesario => Fallo("El atacante no tiene la energia necesaria")
      case atacante.energia => atacado.especie match{
        case Monstruo(_) => Peleando(atacante.copy(energia=atacante.energia-this.kiNecesario), atacado.copy(energia=atacado.energia-(this.kiNecesario/2)))
        case _ => Peleando(atacante.copy(energia=atacante.energia-this.kiNecesario), atacado.copy(energia=atacado.energia-(this.kiNecesario*2)))
      }
    }
  }
}

case object Genkidama extends Ataque{
  def apply(atacante: Guerrero, atacado: Guerrero)={
     atacante.estado match {
       case Fajadas(cantidad) => Peleando(atacante.copy(estado=Luchando),atacado.copy(energia=atacado.energia-(10^cantidad)))
       case _ => Fallo("El atacante no junto energia de su entorno")
     }
  }
}



