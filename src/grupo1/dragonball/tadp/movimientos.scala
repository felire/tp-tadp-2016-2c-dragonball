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

case class ComerseAlOponente() extends Movimiento{
  /*    def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
  }*/
}

case class ConvertirseEnMono() extends Movimiento{
   /*   def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
  }*/
}

case class ConvertirseEnSuperSaiyajin() extends Movimiento{
  /*    def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
  }*/
}

case class Fusion() extends Movimiento{
    /*  def ejecutar(atacante: Guerrero, atacado: Guerrero){
    
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



