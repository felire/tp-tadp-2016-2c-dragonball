package grupo1.dragonball.tadp


trait Movimiento{
    type Guerreros = (Guerrero, Option[Guerrero])
    def apply(guerreros: Guerreros)
}

case object DejarseFajar extends Movimiento{
    def apply(guerreros: Guerreros){
    val (atacante, atacado) = guerreros
    atacante.estado match{
      case Luchando => (atacante.copy(estado = Fajadas(1)), atacado)
      case Fajadas(cantidad) => (atacante.copy(estado = Fajadas(cantidad + 1)), atacado)
      case _ => (atacante, atacado)
    }
  }
}

case object CargarKi extends Movimiento{
   def apply(guerreros: Guerreros){
      val (atacante, atacado) = guerreros
      atacante.especie match{
        case Androide(_) => (atacante, atacado)
        case _ => (atacante.actualizarKi, atacado) //La idea es que actualizarKi devuelva un nuevo Guerrero con la especie con el nuevo ki
      }
  }
}

case class UsarItem(item: Item) extends Movimiento{
   def apply(guerreros: Guerreros){
    
  }
}

case object ComerseAlOponente extends Movimiento
{
  def apply(guerreros: Guerreros)
  {
    atacante match
    {
      case atacante:Monstruo => atacado match
      {
        case atacado:GuerreroOrganico if(atacante.ki >= atacado.ki) => atacante.adquirirMovimientos(atacado.getMovimientos())
        case atacado:Androide => atacante.adquirirMovimientos(atacado.getMovimientos())
        case _ =>
      }
      case _ =>
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



