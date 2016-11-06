package grupo1.dragonball.tadp

case class Guerrero(energia : Int, energiaMax:Int, items: List[Item], movimientos: List[Movimiento], especie: Especie, estado: EstadoPelea)
{
  
  /*def ejecutar(movimiento: Movimiento, atacado:Option[Guerrero])r
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
  }*/
  
  def getMovimientos : List[Movimiento]={
    this.movimientos ++ especie.getMovimientos
  }

 
  def adquirirMovimientos(tipo: MetodoDeDigerir, movimientos :List[Movimiento])={
    copy(especie = Monstruo(tipo.agregarMovimientos(movimientos)))
  }
  
  def cambiarEstado(estadoR: EstadoPelea) = {
    copy(estado = estadoR)
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

  def modificarEnergia (energy: Int)={
    copy(energia = this.energia + energy)
  }
  
   def movimientoMasEfectivoContra(atacado: Guerrero)(unCriterio: (Guerrero,Guerrero) => Int): Movimiento = {
     movimientos.maxBy { mov => unCriterio(atacado, 
         mov.apply(this, atacado) match{
           case Peleando(_,atacad) => atacad
           case _ => atacado
         }
     )}
   }
    
   
   def pelearRound(mov:Movimiento, oponente: Guerrero): Resultado2 = {
     Peleando(this, oponente).flatmap(mov) match{
       case Peleando(atacante, atacado) => 
         Peleando(atacado, atacante).flatmap(atacado.movimientoMasEfectivoContra(atacante)(favoreceKi.apply)) match{
           case Peleando(atacado, atacante) => Peleando(atacante, atacado)
           case Fallo(desc) => Fallo(desc)
           case Ganador(gan) => Ganador(gan)
       }
       case Fallo(desc) => Fallo(desc)
       case Ganador(gan) => Ganador(gan)
     }
   }
}

