package grupo1.dragonball.tadp

import scala.util.Try

abstract class Resultado{
  def flatMap(f:Movimiento) = this
  def map(f:(Guerrero, Guerrero)=>(Guerrero, Guerrero)): Resultado = this
  def filter(f:(Guerrero, Guerrero)=>Boolean): Resultado = this
  def foreach(f:(Guerrero, Guerrero)=>Unit): Unit = Unit
  def proximoMovimiento(criterio : Criterio):Option[Movimiento] = None
  def pelearRound (f:Movimiento) : Resultado  = this
  def invertir : Resultado  = this
}

case class Peleando(atacante:Guerrero, atacado:Guerrero) extends Resultado{
  override def flatMap(f:Movimiento) = {
    atacado.estado match {
      case KO => this
      case _ => atacante.estado match{
        case KO => this
        case _ => 
          f(atacante.deleteMov(f), atacado) match{
            case Peleando(atacante, Guerrero(_, _, _, _, _, Muerto)) => Ganador(atacante)
            case Peleando(Guerrero(_, _, _, _, _, Muerto), atacado) => Ganador(atacado)
            case _ => f(atacante.deleteMov(f), atacado)
          }
      }
    }
  }
  override def map(f:(Guerrero, Guerrero)=>(Guerrero, Guerrero)): Resultado =
  {
     val (atacante2 :Guerrero ,atacado2 : Guerrero) = f.apply(atacante, atacado)
    Peleando(atacante2,atacado2)    
  }
  override def filter(f:(Guerrero, Guerrero)=>Boolean): Resultado = 
  {
    if (f.apply(atacante,atacado)) {
      this
    } else {
      Fallo("Filter error")
    }
  }
  override def foreach(f:(Guerrero, Guerrero)=>Unit): Unit = 
  {
    f.apply(atacante,atacado)
  }
  override def proximoMovimiento(criterio : Criterio):Option[Movimiento] ={
    atacante.movimientoMasEfectivoContra(atacado)(criterio)
  }
   
  override def pelearRound(f:Movimiento) : Resultado = {
    atacante.pelearRound(f, atacado)
  }
  
  override def invertir : Resultado ={
    Peleando(atacado, atacante)
  }
}

case class Fallo(error:String) extends Resultado

case class Ganador(ganador: Guerrero) extends Resultado

