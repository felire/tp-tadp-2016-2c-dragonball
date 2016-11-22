package grupo1.dragonball.tadp

import scala.util.Try

abstract class Resultado{
  def flatMap(f:Movimiento) = this
  def map(f:(Guerrero, Guerrero)=>(Guerrero, Guerrero)): Resultado = this
  def filter(f:(Guerrero, Guerrero)=>Boolean): Resultado = this
  def foreach(f:(Guerrero, Guerrero)=>Unit): Unit = Unit
  def proximoMovimiento(criterio : Criterio):Option[Movimiento] = None
  def pelearRound (f:Movimiento) : Resultado  = this
  def invertir : Resultado = this
  def checkear : Resultado = this
}

case class Peleando(atacante:Guerrero, atacado:Guerrero) extends Resultado{
  
  override def checkear : Resultado = {
    if(atacado.estaMuerto){
      return Ganador(atacante)
    }
    if(atacante.estaMuerto){
      return Ganador(atacado)
    }
    return this
  }
  
  def noEstanKO : Boolean = {
    atacado.estado != KO && atacante.estado != KO
  }
  
  override def flatMap(f:Movimiento) = {
    if(noEstanKO){
      f(atacante.deleteMov(f), atacado).checkear
    }
    else{
      this
    }
  }
  
  override def map(f:(Guerrero, Guerrero)=>(Guerrero, Guerrero)) = {
    if(noEstanKO) {
      val (atacante2 ,atacado2 ) = f.apply(atacante, atacado)
      Peleando(atacante2,atacado2).checkear
    } 
    else {
      this
    }
  }
  
  override def filter(f:(Guerrero, Guerrero)=>Boolean): Resultado = {
    if (f.apply(atacante,atacado)){
      this
    }
    else {
      Fallo("Filter error")
    }
  }
  
  override def foreach(f:(Guerrero, Guerrero)=>Unit): Unit = {
    f.apply(atacante,atacado)
  }
  
  override def proximoMovimiento(criterio : Criterio):Option[Movimiento] = {
    atacante.movimientoMasEfectivoContra(atacado)(criterio)
  }
   
  override def pelearRound(f:Movimiento) : Resultado = {
    atacante.pelearRound(f, atacado)
  }
  
  override def invertir : Resultado = {
    Peleando(atacado, atacante)
  }
}

case class Fallo(error:String) extends Resultado

case class Ganador(ganador: Guerrero) extends Resultado

