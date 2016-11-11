package grupo1.dragonball.tadp

trait Criterio{
  def apply(antes :Guerrero,  despues : Guerrero) : Int    
}

case object favoreceKi extends Criterio{
  def apply(antes: Guerrero, despues: Guerrero): Int = {
    antes.energia - despues.energia 
  }
}

case object noFavoreceKi extends Criterio{
  def apply(antes: Guerrero, despues: Guerrero) : Int ={
    favoreceKi(antes, despues)*(-1)    
  }
}

case object tacanio extends Criterio{
  def apply(antes: Guerrero, despues: Guerrero) :Int ={
    despues.items.size - antes.items.size
  }
}


// ATENCIONNNNN, ATENCIONNNNNNNNNNNN
// Fijense si lo de abajo funca...
//porque me parece que con el match de mejorCriterio el despues nunca puede quedar muerto, entonces nunca entra
case object queNoLoMate extends Criterio{ //el de Krillin... No se si hacia falta
  def apply(antes: Guerrero, despues: Guerrero) :Int ={
    despues.estado match{
      case Muerto => -5646548
      case _ => 1000000
    }
  }
}