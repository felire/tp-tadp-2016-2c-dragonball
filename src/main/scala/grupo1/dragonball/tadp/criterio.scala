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
      case Peleando(_, atacadoDespues) => Some(atacadoDespues.energia)
      case _ => None
    }
  }
}

case object tacanio extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Option[Double]   = {
    movimiento(atacante,atacado) match{
      case Peleando(atacanteDespues,_) => Some(atacanteDespues.items.size)
      case _ => None
    }
  }
}

case object queNoLoMate extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Option[Double]   = {
    movimiento(atacante,atacado) match{
      case Peleando(atacanteDespues,_) if (atacanteDespues.estaMuerto) => None
      case Peleando(atacanteDespues,_) => Some(atacanteDespues.energia)
      case _ => None
    }
  }
}
