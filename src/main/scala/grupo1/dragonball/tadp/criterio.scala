package grupo1.dragonball.tadp

trait Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero,movimiento:Movimiento): Double    
}

case object oponentesDebiles extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Double = {
    movimiento(atacante,atacado) match{
      case Peleando(_,atacadoDespues)=>atacado.energia-atacadoDespues.energia
      case _ =>0
    }    
  }
}

case object oponentesFuertes extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Double = {
    movimiento(atacante,atacado) match{
      case Peleando(_,atacadoDespues)=>(atacadoDespues.energia-atacado.energia)
      case _ =>0
    }    
  }
}

case object tacanio extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Double = {
    movimiento(atacante,atacado) match{
      case Peleando(atacanteDespues,_)=>(atacante.items.size - atacanteDespues.items.size)
      case _ =>0
    }    
  }
}


case object queNoLoMate extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Double = {
    movimiento(atacante,atacado) match{
      case Peleando(_,_)=>1
      case Ganador(_) =>0
      case _ =>(-1)
    }    
  }
}
