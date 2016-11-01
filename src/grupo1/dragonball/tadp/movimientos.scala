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
  def apply(guerreros: Guerreros)
  {
    atacante match
    {
      case atacante:Saiyajin if(atacante.tengoItem(FotoDeLaLuna) && atacante.tieneCola) => atacante.estado = new Mono()
      case _ =>
    }
  }
}

case object ConvertirseEnSuperSaiyajin extends Movimiento
{
  def apply(guerreros: Guerreros)
  {
    atacante match
    {
      case atacante:Saiyajin if(atacante.ki > atacante.kiMaximo/2) => atacante.pasarNivel()
      case _ =>
    }
  }
}

case class Fusion(companiero: GuerreroOrganico) extends Movimiento{
   def apply(guerreros: Guerreros){
    
  }
}

case class Magia(poderMistico: (Guerrero,Guerrero)=>(Guerrero,Guerrero)) extends Movimiento{
  def apply(guerreros: Guerreros){
    atacante match{
      case atacante:Magico => poderMistico(atacante, atacado)
      case atacante:Guerrero if(atacante.tieneEsferas) => poderMistico(atacante, atacado)
      case _ =>
    }
  }
}

abstract class Ataque extends Movimiento{

}

case object Explotar extends Ataque{
  def apply(guerreros: Guerreros){
    
  }
}

case object Onda extends Ataque{
  def apply(guerreros: Guerreros){
    
  }
}

case object Genkidama extends Ataque{
   def apply(guerreros: Guerreros){
    
  }
}



