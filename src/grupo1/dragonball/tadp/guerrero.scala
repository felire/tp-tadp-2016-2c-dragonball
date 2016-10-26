package grupo1.dragonball.tadp

abstract class Guerrero(items: List[Item], movimientos: List[Movimiento]) {
  //var items: List[Item] = List()
  //var movimientos: List[Movimiento] = List()
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
}


abstract class GuerreroOrganico( kiR: Int, itemsR: List[Item], movimientosR: List[Movimiento]) extends Guerrero(itemsR, movimientosR){
  var ki=kiR
  var items=itemsR
  var movimientos=movimientosR
  var conciente:Boolean = true
  override def cargarKi{
    ki = ki + 100
  }
}

/*
object GuerreroOrganico {
  def unapply(p: GuerreroOrganico):Option[Int] = {
    p.ki
  }
}*/

case class Androide(var bateria: Int, items: List[Item], movimientos: List[Movimiento]) extends Guerrero(items, movimientos){
  override def cargarKi{}
  
}

case class Humano( kiR: Int, itemsR: List[Item], movimientosR: List[Movimiento]) extends GuerreroOrganico( kiR, itemsR, movimientosR){
  
}

case class Saiyajin(kiR: Int, itemsR: List[Item], movimientosR: List[Movimiento], var estado: Estado) extends GuerreroOrganico( kiR, itemsR, movimientosR){
  var tieneCola: Boolean = true  
  override def cargarKi{
      estado.cargarKi(this)
    }
  def perderCola{
    tieneCola = false
  }
}

abstract class Estado{
  def cargarKi(saiyajin: Saiyajin){
    saiyajin.ki += 100
  }
  def recibirAtaqueFilosa(saiyajin: Saiyajin){
    if(saiyajin.tieneCola) saiyajin.ki =1
  }
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
}

case class Normal() extends Estado{
   override def recibirAtaqueFilosa(saiyajin: Saiyajin){
     super.recibirAtaqueFilosa(saiyajin)
     saiyajin.perderCola
   }
}

case class Namekusein(kiR: Int, itemsR: List[Item], movimientosR: List[Movimiento]) extends GuerreroOrganico( kiR, itemsR, movimientosR){
  
}

case class Monstruo(kiR: Int, itemsR: List[Item], movimientosR: List[Movimiento]) extends GuerreroOrganico( kiR, itemsR, movimientosR){
  
}



/*
case object NOP extends Instruccion
case object ADD extends Instruccion
case object MUL extends Instruccion
case object DIV extends Instruccion
case object SWAP extends Instruccion
case object HALT extends Instruccion

case class LODV(valor: Short) extends Instruccion(2)
case class LOD(direccion: Int) extends Instruccion(3)
case class STR(direccion: Int) extends Instruccion(3)
case class IFNZ(instrucciones: Instruccion*) extends Instruccion
 */




