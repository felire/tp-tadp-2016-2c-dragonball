package grupo1.dragonball.tadp

abstract class Movimiento{
   // def ejecutar(atacante: Guerrero, atacado: Guerrero)
}

case class DejarseFajar() extends Movimiento{
  /*  def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
  }*/
}

case class CargarKi() extends Movimiento{
 /*     def ejecutar(atacante: Guerrero, atacado: Guerrero){
      atacante.cargarKi
  }*/
}

case class UsarItem(item: Item) extends Movimiento{
/*      def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
  }*/
}

case class ComerseAlOponente() extends Movimiento
{
  def ejecutar(atacante: Guerrero, atacado: Guerrero)
  {
    atacante match
    {
      case atacante:Monstruo => atacado match
      {
        case atacado:GuerreroOrganico if(atacante.ki >= atacado.ki) => atacante.adquirirMovimientos(atacado.getMovimientos())
        case atacado:Androide => atacante.adquirirMovimientos(atacado.getMovimientos())
        case _ =>
      }
      case _ =>
    }
  }
}

case class ConvertirseEnMono() extends Movimiento
{
  def ejecutar(atacante: Guerrero, atacado: Guerrero)
  {
    atacante match
    {
      case atacante:Saiyajin if(atacante.tengoItem(FotoDeLaLuna) && atacante.tieneCola) => atacante.estado = new Mono()
      case _ =>
    }
  }
}

case class ConvertirseEnSuperSaiyajin() extends Movimiento
{
  def ejecutar(atacante: Guerrero, atacado: Guerrero)
  {
    atacante match
    {
      case atacante:Saiyajin if(atacante.ki > atacante.kiMaximo/2) => atacante.pasarNivel()
      case _ =>
    }
  }
}

case class Fusion(companiero: GuerreroOrganico) extends Movimiento{
   /*def ejecutar(atacante: Guerrero, atacado: Guerrero){
    atacante match{
      case atacante:GuerreroOrganico => 
        
      case _ =>
    }
  }*/
}

case class Magia() extends Movimiento{
   /*   def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
  }*/
}

abstract class Ataque extends Movimiento{

}

case class Explotar() extends Ataque{
 /* def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
  }*/
}

case class Onda() extends Ataque{
  /*def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
  }*/
}

case class Genkidama() extends Ataque{
   /*   override def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
  }*/
}



