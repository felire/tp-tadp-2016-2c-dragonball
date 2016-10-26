package grupo1.dragonball.tadp

abstract class Guerrero(var items: List[Item], private var movimientos: List[Movimiento]) {

  def ejecutar(movimiento: Movimiento,atacado:Guerrero){
    movimiento match{
      case DejarseFajar() => 
      case CargarKi() => this.cargarKi
      case UsarItem(item) if(tengoItem(item)) => item.usar(this, atacado)
    }
  }
  def tengoItem(item:Item):Boolean={
    items.contains(item)
  }
  
  def cargarKi: Unit = ???
  def getMovimientos():List[Movimiento]={
    movimientos
  }
}


abstract class GuerreroOrganico( var ki: Int, items: List[Item], movimientos: List[Movimiento], kiMaximoR:Int) extends Guerrero(items, movimientos){
  
  var conciente:Boolean = true
  var kiMaximo:Int = kiMaximoR
  override def cargarKi{
    ki = ki + 100
  }
}

case class Androide(var bateria: Int, itemsR: List[Item], movimientosR: List[Movimiento]) extends Guerrero(itemsR, movimientosR){
  override def cargarKi{}  
}

case class Humano( kiR: Int, itemsR: List[Item], movimientosR: List[Movimiento], kiMaximoR:Int) extends GuerreroOrganico( kiR, itemsR, movimientosR, kiMaximoR){
  
}

case class Saiyajin(kiR: Int, itemsR: List[Item], movimientosR: List[Movimiento], kiMaximoR:Int, var estado: Estado) extends GuerreroOrganico( kiR, itemsR, movimientosR, kiMaximoR){
  var tieneCola: Boolean = true  
  override def cargarKi{
      estado.cargarKi(this)
    }
  def perderCola{
    tieneCola = false
  }
  def pasarNivel(){
    estado.aumentar(this)
  }
}

abstract class Estado{
  def cargarKi(saiyajin: Saiyajin){
    saiyajin.ki += 100
  }
  def recibirAtaqueFilosa(saiyajin: Saiyajin){
    if(saiyajin.tieneCola) saiyajin.ki =1
  }
  def aumentar(x:Saiyajin){}
}

case class Mono() extends Estado{
   override def recibirAtaqueFilosa(saiyajin: Saiyajin){
     super.recibirAtaqueFilosa(saiyajin)
     saiyajin.perderCola
     saiyajin.conciente = false
     saiyajin.estado = new Normal()
   }
}

case class SuperSaiyajin(var nivel: Int) extends Estado{
  override def cargarKi(saiyajin: Saiyajin){
    saiyajin.ki = saiyajin.ki + nivel*150
  }
  override def aumentar(sayian:Saiyajin){
    nivel+= 1
    sayian.kiMaximo = sayian.kiMaximo * 5
  }
}

case class Normal() extends Estado{
   override def recibirAtaqueFilosa(saiyajin: Saiyajin){
     super.recibirAtaqueFilosa(saiyajin)
     saiyajin.perderCola
   }
     override def aumentar(sayian:Saiyajin){
       sayian.estado = new SuperSaiyajin(1)
       sayian.kiMaximo = sayian.kiMaximo * 5
  }
}

case class Namekusein(kiR: Int, itemsR: List[Item], movimientosR: List[Movimiento], kiMaximoR:Int) extends GuerreroOrganico( kiR, itemsR, movimientosR, kiMaximoR){
  
}

abstract class MetodoDeDigerir(){
  var movimientos:List[Movimiento] = List()
  def getMovimientos():List[Movimiento]={
    movimientos
  }
  def agregarMovimientos(movimientos:List[Movimiento])
}

class SoloDigieroUltimo() extends MetodoDeDigerir{
  def agregarMovimientos(movimientosNuevos:List[Movimiento]){
    movimientos = movimientosNuevos
  }
}

class DigieroTodos() extends MetodoDeDigerir{
  def agregarMovimientos(movimientosNuevos:List[Movimiento]){
    movimientos = movimientos++movimientosNuevos
  }
}

//los movimientos se dividen en los propios y los que digirio.
case class Monstruo(kiR: Int, itemsR: List[Item], movimientosR: List[Movimiento], kiMaximoR:Int) extends GuerreroOrganico( kiR, itemsR, movimientosR, kiMaximoR){
  var metodoDeDigerir:MetodoDeDigerir = new DigieroTodos() //por defecto
  def adquirirMovimientos(movimientos:List[Movimiento]) {
    metodoDeDigerir.agregarMovimientos(movimientos)
  }
  override def getMovimientos():List[Movimiento]={
    super.getMovimientos()++metodoDeDigerir.getMovimientos()
  }
}


