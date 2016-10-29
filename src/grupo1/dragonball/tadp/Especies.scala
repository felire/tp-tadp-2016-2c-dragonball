package grupo1.dragonball.tadp
trait Especie{
  def kiNuevo : Especie
}
case class Androide(bateria: Int) extends Especie{
  def kiNuevo{
    copy(bateria = bateria)
  }
}
case class Humano(ki: Int, kiMax:Int) extends Especie{
    def kiNuevo{
    copy(ki = ki+100, kiMax = kiMax )
  }
}
trait Magico
case class Namekusein(ki: Int, kiMax:Int) extends Especie with Magico{
    def kiNuevo{
     copy(ki = ki+100, kiMax = kiMax )
  }
}
//los movimientos se dividen en los propios y los que digirio.
case class Monstruo(ki: Int, kiMax:Int, metodo:MetodoDeDigerir) extends Especie with Magico
{
  def kiNuevo{
     copy(ki = ki+100, kiMax = kiMax, metodo = metodo )
  }
  //var metodoDeDigerir:MetodoDeDigerir = new DigieroTodos() //por defecto
  
  def adquirirMovimientos(movimientos:List[Movimiento])
  {
    metodo.agregarMovimientos(movimientos)
  }
  def getMovimientos():List[Movimiento]=
  {
    //super.getMovimientos()++metodoDeDigerir.getMovimientos() --> Re pensar inmutable
  }
}

//case class GuerreroFusion(guerreroOriginal: GuerreroOrganico,ki: Int,kiMaximo: Int) extends Especie

case class Saiyajin(ki: Int, kiMax:Int, estado:Estado = Normal, tieneCola:Boolean = true) extends Especie
{
  
  def cargarKi
  {
      copy(ki = estado.cargarKi(ki), kiMax = kiMax, estado = estado, tieneCola = tieneCola)
  }
  def perderCola
  {
    tieneCola = false
  }
  def pasarNivel()
  {
    estadoPoder.aumentar(this)
  }
}



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
abstract class MetodoDeDigerir()
{
  var movimientos:List[Movimiento] = List()
  def getMovimientos():List[Movimiento]=
  {
    movimientos
  }
  def agregarMovimientos(movimientos:List[Movimiento])
}

class SoloDigieroUltimo() extends MetodoDeDigerir
{
  def agregarMovimientos(movimientosNuevos:List[Movimiento])
  {
    movimientos = movimientosNuevos
  }
}

class DigieroTodos() extends MetodoDeDigerir
{
  def agregarMovimientos(movimientosNuevos:List[Movimiento])
  {
    movimientos = movimientos++movimientosNuevos
  }
}