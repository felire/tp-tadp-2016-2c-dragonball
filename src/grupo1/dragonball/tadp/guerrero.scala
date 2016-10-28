package grupo1.dragonball.tadp

abstract class Guerrero
{
  var items = List[Item]()
  var movimientos = List[Movimiento]()
  
  def ejecutar(movimiento: Movimiento,atacado:Guerrero)
  {
    movimiento match
    {
      case DejarseFajar() => 
      case CargarKi() => this.cargarKi
      case UsarItem(item:Item) if(tengoItem(item)) => item.usar(this, atacado)
      case movimiento:ConvertirseEnSuperSaiyajin => movimiento.ejecutar(this, atacado)
      case movimiento:ComerseAlOponente => movimiento.ejecutar(this, atacado)
      case movimiento:ConvertirseEnMono => movimiento.ejecutar(this,atacado)
      case Fusion(companiero:GuerreroOrganico) => this match{
        case original:GuerreroOrganico => original.fusion(companiero) //Devuelve un GuerreroFusion, abria que ver que hacer con el
        case _ =>
      }
      case _ =>
    }
  }
  def tengoItem(item:Item):Boolean=
  {
    items.contains(item)
  }
  def cargarKi: Unit = ???
  def getMovimientos():List[Movimiento]=
  {
    movimientos
  }
  def addMovimiento(movimiento: Movimiento){
    movimientos = movimientos.+:(movimiento) //añadir de scala.. muy expresivo
  }
  def addItem(item: Item){
    items = items.+:(item)
  }
}

abstract class GuerreroOrganico extends Guerrero
{
  var conciente:Boolean = true
  var kiMaximo:Int = 100
  var ki:Int = 50
  
  override def cargarKi
  {
    ki = ki + 100
  }
  
  def fusion(guerrero: GuerreroOrganico){
    new GuerreroFusion(this, this.ki + guerrero.ki, this.kiMaximo+guerrero.kiMaximo, this.getMovimientos().++(guerrero.getMovimientos()))
  }
}

class GuerreroFusion(guerreroOriginal: GuerreroOrganico,var ki: Int,var kiMaximo: Int, movimientos: List[Movimiento]) extends Guerrero{
  override def cargarKi(){
    ki = ki + 100
  }
}

class Androide extends Guerrero
{
  override def cargarKi{}  
}

class Humano extends GuerreroOrganico
{
  
}

class Namekusein extends GuerreroOrganico
{
  
}



//los movimientos se dividen en los propios y los que digirio.
class Monstruo extends GuerreroOrganico
{
  var metodoDeDigerir:MetodoDeDigerir = new DigieroTodos() //por defecto
  
  def adquirirMovimientos(movimientos:List[Movimiento])
  {
    metodoDeDigerir.agregarMovimientos(movimientos)
  }
  override def getMovimientos():List[Movimiento]=
  {
    super.getMovimientos()++metodoDeDigerir.getMovimientos()
  }
}

class Saiyajin extends GuerreroOrganico
{
  var tieneCola: Boolean = true
  var estado:Estado = new Normal()
  
  override def cargarKi
  {
      estado.cargarKi(this)
  }
  def perderCola
  {
    tieneCola = false
  }
  def pasarNivel()
  {
    estado.aumentar(this)
  }
}

abstract class Estado
{
  def cargarKi(saiyajin: Saiyajin)
  {
    saiyajin.ki += 100
  }
  def recibirAtaqueFilosa(saiyajin: Saiyajin)
  {
    if(saiyajin.tieneCola) saiyajin.ki =1
  }
  def aumentar(x:Saiyajin){}
}

case class Mono() extends Estado
{
   override def recibirAtaqueFilosa(saiyajin: Saiyajin)
   {
     super.recibirAtaqueFilosa(saiyajin)
     saiyajin.perderCola
     saiyajin.conciente = false
     saiyajin.estado = new Normal()
   }
}

case class SuperSaiyajin(var nivel: Int) extends Estado
{
  override def cargarKi(saiyajin: Saiyajin)
  {
    saiyajin.ki = saiyajin.ki + nivel*150
  }
  override def aumentar(sayian:Saiyajin)
  {
    nivel+= 1
    sayian.kiMaximo = sayian.kiMaximo * 5
  }
}

case class Normal() extends Estado
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
