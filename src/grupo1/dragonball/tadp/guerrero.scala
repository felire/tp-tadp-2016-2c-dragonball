package grupo1.dragonball.tadp



case class Guerrero(items: List[item], movimientos: List[Movimiento], especie: Especie, estado: EstadoPelea)
{
  
  def ejecutar(movimiento: Movimiento,atacado:Option[Guerrero])
  {
    movimiento match
    {
      case DejarseFajar => 
      case CargarKi => this.cargarKi
      case UsarItem(item:Item) if(tengoItem(item)) => item.usar(this, atacado)
      case ConvertirseEnSuperSaiyajin => movimiento.ejecutar(this, atacado)
      case ComerseAlOponente => movimiento.ejecutar(this, atacado)
      case ConvertirseEnMono => movimiento.ejecutar(this,atacado)
      case Fusion(companiero:GuerreroOrganico) => this match{
        case original:GuerreroOrganico => original.fusion(companiero) //Devuelve un GuerreroFusion, abria que ver que hacer con el
        case _ =>
      }
      case _ =>
    }
  }
  
  def actualizarKi{
    copy(Especie = especie.kiNuevo)
  }
  def tengoItem(item:Item):Boolean=
  {
    items.contains(item)
  }
  
  def tieneEsferas: Boolean = {
    (1 to 7).forall(numero =>
      items.exists ( item => 
        item match {
          case item:EsferaDelDragon => item.estrella == numero
          case _ => false
        }
      )
   )
 }
  
}

