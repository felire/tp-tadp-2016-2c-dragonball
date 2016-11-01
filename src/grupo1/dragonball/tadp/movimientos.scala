package grupo1.dragonball.tadp

import scala.util.Try

trait Movimiento{
    
    type f =(Guerrero, Guerrero)
    def apply(guerreros :f) : Resultado
    
}

case object DejarseFajar extends Movimiento{
    def apply(atacante :Guerrero,  atacado : Guerrero)={
      atacante.estado match{
        case Luchando => Resultado(Try(atacante.copy(estado = Fajadas(1))),Try( atacado))
        case Fajadas(cantidad) => Resultado(Try(atacante.copy(estado = Fajadas(cantidad + 1))),Try( atacado))
        case _ => Resultado(Try(atacante), Try(atacado))
      }
    }  
}

case object CargarKi extends Movimiento{  
   def apply(atacante :Guerrero,  atacado : Guerrero)= {
       atacante.especie match{
        case Androide => Resultado(Try(throw new Exception("Los androides no pueden cargar ki")),Try(atacado))
        case Saiyajin(SuperSaiyajin(nivel),_) => Resultado(Try(atacante.modificarEnergia(150*nivel)), Try( atacado))
        case _ => Resultado(Try(atacante.modificarEnergia(100)), Try( atacado))
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
        case Androide => Resultado(Try(atacante.adquirirMovimientos(tipoDigestivo,atacado.movimientos)), Try(atacado))
        case _ if(atacante.energia >= atacado.energia) => Resultado(Try(atacante.adquirirMovimientos(tipoDigestivo,atacado.movimientos)),Try(atacado))
        case _ => Resultado(Try(atacante),Try(atacado))
      }
      case _ => Resultado(Try(throw new Exception("Solo los monstruos pueden comer")),Try(atacado))
    }
  }
}

case object ConvertirseEnMono extends Movimiento
{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match{
      case Saiyajin(Normal, true) if(atacante.tengoItem(FotoDeLaLuna)) => Resultado(Try(atacante.copy(especie = Mono)),Try(atacado))
      case Saiyajin(SuperSaiyajin(nivel),true) => Resultado(Try(atacante.copy(especie = Mono, energiaMax = atacante.energiaMax/(5*nivel))),Try(atacado))
      case _ => Resultado(Try(throw new Exception("No puede convertirse en mono")), Try(atacado))
    }
  }
}

case object ConvertirseEnSuperSaiyajin extends Movimiento{
  def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match
    {
      case Saiyajin(Normal,cola) if(atacante.energia > atacante.energiaMax/2) => Resultado(Try(atacante.copy(especie=Saiyajin(SuperSaiyajin(1),cola), energiaMax=atacante.energiaMax*5)),Try(atacado))
      case Saiyajin(SuperSaiyajin(nivel),cola) if(atacante.energia > atacante.energiaMax/2)=>Resultado(Try(atacante.copy(especie=Saiyajin(SuperSaiyajin(nivel+1),cola),energiaMax=atacante.energiaMax*5*nivel)),Try(atacado))
      case _ =>Resultado(Try(throw new Exception("No es Saiyajin")),Try(atacado))
    }
  }
}

case class Fusion(companiero: Guerrero) extends Movimiento{
   def apply(atacante: Guerrero, atacado: Guerrero)={
    atacante.especie match{
      case Humano | Namekusein | Saiyajin(_,_) =>  companiero.especie match{
        case Humano | Namekusein | Saiyajin(_,_) => Resultado(Try(atacante.copy(especie = GuerreroFusionado(atacante),movimientos = atacante.getMovimientos ++ companiero.getMovimientos, energia = atacante.energia + companiero.energia, energiaMax = atacante.energiaMax + companiero.energiaMax)),Try(atacado))
        case _ => Resultado(Try(throw new Exception("No se pudo fusionar")), Try(atacado))
      }
      case _ => Resultado(Try(throw new Exception("No se pudo fusionar")), Try(atacado))
    }
  }
}

case class Magia(poderMistico: (Guerrero,Guerrero)=>(Guerrero,Guerrero)) extends Movimiento{
  def apply(atacante: Guerrero, atacado: Guerrero){
    val (atacanteMod, atacadoMod) = poderMistico(atacante, atacado)
    atacante.especie match{
      case Monstruo(_) | Namekusein => Resultado(Try(atacanteMod), Try(atacadoMod))
      case _ if(atacante.tieneEsferas) => Resultado(Try(atacanteMod), Try(atacadoMod))
      case _ => Resultado(Try(throw new Exception("No se pudo tirar la magia")), Try(atacado))
    }
  }
}

abstract class Ataque extends Movimiento

case object Explotar extends Ataque{
  def apply(atacante: Guerrero, atacado: Guerrero){
    atacante.especie match{
      case Androide => 
        var energiaNva = 0 max (atacado.energia - atacante.energia *3) 
        atacado.especie match{
          case Namekusein => energiaNva = energiaNva max 1
        }
        val atacadoNvo = atacado.copy(energia =)
        val atacanteNvo = atacante.copy(energia = 0, estado = Muerto)
        return Resultado()
        
    }
  }
}

case object Onda extends Ataque{
  def apply(atacante: Guerrero, atacado: Guerrero){
    
  }
}

case object Genkidama extends Ataque{
   def apply(atacante: Guerrero, atacado: Guerrero){
    
  }
}

case object MuchosGolpes extends Ataque{
   def apply(atacante: Guerrero, atacado: Guerrero){
    
  }
}



