package grupo1.dragonball.tadp

trait Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero,movimiento:Movimiento): Option[Double]    
}

case object oponentesDebiles extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Option[Double]   = {
    movimiento(atacante,atacado) match{
      case Peleando(_,atacadoDespues)=> Some(atacado.energia - atacadoDespues.energia)
      case _ => None
    }    
  }
}

case object oponentesFuertes extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Option[Double]   = {
    movimiento(atacante,atacado) match{
      case Peleando(_, atacadoDespues) => Some(atacadoDespues.energia - atacado.energia)
      case _ => None
    }    
  }
}

case object tacanio extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Option[Double]   = {
    movimiento(atacante,atacado) match{
      case Peleando(atacanteDespues,_) => Some(atacante.items.size - atacanteDespues.items.size)
      case _ => None
    }    
  }
}


case object queNoLoMate extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Option[Double]   = {
    movimiento(atacante,atacado) match{
      case Peleando(_,atacadoDespues)=> Some(atacadoDespues.energia) //mientras mas energia tiene mejor
      case _ => None
    }    
  }
}
