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
      case Peleando(_,atacadoDespues)=>(-1)/(atacadoDespues.energia-atacado.energia) //hago una hiperbola, para que siempre sea mayor a cero
      case _ =>0
    }    
  }
}

case object tacanio extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Double = {
    movimiento(atacante,atacado) match{
      case Peleando(atacanteDespues,_)=>(-1)/(atacanteDespues.items.size-atacante.items.size)//mismo aca
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
