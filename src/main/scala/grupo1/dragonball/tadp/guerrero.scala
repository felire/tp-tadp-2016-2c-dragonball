package grupo1.dragonball.tadp

case class Guerrero(energia : Int, energiaMax:Int, items: List[Item], movimientos: List[Movimiento], especie: Especie, estado: EstadoPelea)
{
  
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
  
   def movimientoMasEfectivoContra(atacado: Guerrero)(unCriterio: Criterio): Movimiento = {
      movimientos.maxBy{ mov => unCriterio(this,atacado,mov)}
   }
    

   def pelearRound(mov:Movimiento, oponente: Guerrero): Resultado = {
     Peleando(this, oponente).flatmap(mov)
                             .invertir() match{
       case Peleando(atacante, atacado) => Peleando(atacante, atacado).flatmap(atacante.movimientoMasEfectivoContra(atacado)(oponentesDebiles))
                                                                      .invertir()
       case otro => otro
     }
   }
   
   def pelearRoundConContra(mov:Movimiento, oponente: Guerrero): Resultado = {
     Peleando(this, oponente).flatmap(mov).flatmap(ContraAtacar)
   }
}

