package grupo1.dragonball.tadp
import scala.util.Try

case class Guerrero(energia : Int, energiaMax:Int, items: List[Item], movimientos: List[Movimiento], especie: Especie, estado: EstadoPelea)
{
  
  def getMovimientos : List[Movimiento]={
    this.movimientos ++ especie.getMovimientos
  }
  
  def addMovimiento (mov : Movimiento): Guerrero ={
    copy(movimientos = movimientos.+:(mov))
  }
  
  def addItem (item : Item) : Guerrero ={
    copy(items = items.+:(item))
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
    if((this.energia + energy) <= 0){
      copy(energia = 0, estado = Muerto)
    }
    else{
      copy(energia = this.energia + energy)
    }
  }
  
   def deleteMov(movimiento: Movimiento) ={
     copy(movimientos = this.movimientos.filter { mov => !mov.equals(movimiento) })
   }
   
   def movimientoMasEfectivoContra(atacado: Guerrero)(unCriterio: Criterio): Option[Movimiento] = {
     Try(movimientos.maxBy{ mov => unCriterio(this,atacado,mov)}).toOption
   }

   def pelearRound(mov:Movimiento, oponente: Guerrero): Resultado = {
     Peleando(this, oponente).flatMap(mov).flatMap(ContraAtacar)
   }
   
   def planDeAtaque(oponente: Guerrero, rounds: Int)(criterio: Criterio): List[Movimiento] = {
     val peleaInicial: Resultado = Peleando(this,oponente)
     val movsInicial = List(): List[Movimiento]
     val (resultado, movs) = (1 to rounds).foldLeft(peleaInicial,movsInicial){(peleaYMovs, round) =>
       val (pelea,movimientos) = peleaYMovs
       val movimiento = pelea.proximoMovimiento(criterio)
       movimiento.map(mov => (pelea.pelearRound(mov),movimientos.+:(mov))).getOrElse(pelea,movimientos)
     }
     movs/*enunciado : Si el guerrero no encuentra un movimiento satisfactorio para cada round pedido, 
                       NO DEBE retornarse un plan más corto.*/
   }
   
   def pelearContra(oponente: Guerrero)(planAtaque: List[Movimiento]): Resultado = {
     planAtaque.foldLeft(Peleando(this, oponente) : Resultado){(pelea, movimiento) =>
       pelea.pelearRound(movimiento)
     }
   }
   
}

