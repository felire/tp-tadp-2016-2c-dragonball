package grupo1.dragonball.tadp
trait Especie{
  def adquirirMovimientos(movimientos:List[Movimiento]) = List()
  def getMovimientos():List[Movimiento] = List()
}
case object Androide extends Especie
case object Humano extends Especie
trait Magico
case object Namekusein extends Especie with Magico
//los movimientos se dividen en los propios y los que digirio.
case class Monstruo(metodo:MetodoDeDigerir) extends Especie with Magico{
  def getMovimientos :List[Movimiento] = {
    metodo.getMovimientos
  }
}

//case class GuerreroFusion(guerreroOriginal: GuerreroOrganico,ki: Int,kiMaximo: Int) extends Especie

case class Saiyajin(estado:Estado = Normal, tieneCola:Boolean = true) extends Especie

abstract class Estado
{
  def cargarKi(ki: Int) : Int =
  {
     ki + 100
  }
  def recibirAtaqueFilosa(saiyajin: Saiyajin)
  {
    if(saiyajin.tieneCola) saiyajin.ki =1
  }
  def aumentar(x:Saiyajin){}
}


case object Mono extends Estado
{
   override def recibirAtaqueFilosa(saiyajin: Saiyajin)
   {
     super.recibirAtaqueFilosa(saiyajin)
     saiyajin.perderCola
     saiyajin.conciente = false
     saiyajin.estado = new Normal()
   }
}

case class GuerreroFusinado(invocador :Guerrero) extends Estado

case class SuperSaiyajin(nivel: Int) extends Estado
{
  def cargarKi(ki: Int): Int =
  {
    ki + nivel*150
  }
  override def aumentar(sayian:Saiyajin)
  {
    nivel+= 1
    sayian.kiMaximo = sayian.kiMaximo * 5
  }
}

case object Normal extends Estado
{
   override def recibirAtaqueFilosa(saiyajin: Saiyajin)
   {
     super.recibirAtaqueFilosa(saiyajin)
     saiyajin.perderCola
   }
     override def aumentar(sayian:Saiyajin)
     {
       sayian.estado = new SuperSaiyajin(1)
       sayian.kiMaximo = sayian.kiMaximo * 5
     }
}

//metodos digestion
trait MetodoDeDigerir
{
  def getMovimientos : List[Movimiento]
  def agregarMovimientos(movimientos:List[Movimiento])
}

case class SoloDigieroUltimo(val movimientos :List[Movimiento]) extends MetodoDeDigerir
{
  def getMovimientos() = movimientos
  def agregarMovimientos(movimientosNuevos:List[Movimiento])=
  {
    copy(movimientosNuevos)
  }
}

case class DigieroTodos(val movimientos :List[Movimiento]) extends MetodoDeDigerir
{
  def getMovimientos() = movimientos
  def agregarMovimientos(movimientosNuevos:List[Movimiento])
  {
    copy(movimientos++movimientosNuevos)
  }
}