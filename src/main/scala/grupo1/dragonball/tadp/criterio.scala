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

//TODO hacer el que no lo mate tome en cuenta el contra ataque y que gane el atacante
case object queNoLoMate extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Option[Double]   = {
    Peleando(atacante,atacado).flatMap(movimiento) match{
      case Peleando(atacanteDespues,_) if (atacanteDespues.energia == 0) => None
      case Peleando(_,_) => Some(1)
      case _ => None
    }
  }
}
