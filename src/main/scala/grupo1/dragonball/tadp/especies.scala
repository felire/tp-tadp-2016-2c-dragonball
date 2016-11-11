package grupo1.dragonball.tadp


trait Especie{
  def getMovimientos():List[Movimiento] = List()
}
case object Androide extends Especie
case object Humano extends Especie

case object Namekusein extends Especie

case class Monstruo(metodo:MetodoDeDigerir) extends Especie{
  override def getMovimientos :List[Movimiento] = {
    metodo.getMovimientos()
  }
}

case class Saiyajin(estado:Estado = Normal, tieneCola:Boolean = true) extends Especie

abstract class Estado

case object Mono extends Estado

case class GuerreroFusionado(invocador :Guerrero) extends Especie

case class SuperSaiyajin(nivel: Int) extends Estado

case object Normal extends Estado

trait MetodoDeDigerir
{
  def getMovimientos() : List[Movimiento]
  def agregarMovimientos(movimientos:List[Movimiento]) : MetodoDeDigerir
}

case class SoloDigieroUltimo(movimientos :List[Movimiento] ) extends MetodoDeDigerir
{
  def getMovimientos() = movimientos
  def agregarMovimientos(movimientosNuevos:List[Movimiento])=
  {
    copy(movimientosNuevos)
  }
}

case class DigieroTodos(movimientos :List[Movimiento]) extends MetodoDeDigerir
{
  def getMovimientos() = movimientos
  def agregarMovimientos(movimientosNuevos:List[Movimiento])=
  {
    copy(movimientos++movimientosNuevos)
  }
}