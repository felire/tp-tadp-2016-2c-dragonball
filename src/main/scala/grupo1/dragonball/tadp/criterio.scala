package grupo1.dragonball.tadp

trait Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero,movimiento:Movimiento): Double    
}

case object oponentesDebiles extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Double = {
    movimiento(atacante,atacado) match{
      case Peleando(_,atacadoDespues)=> atacado.energia - atacadoDespues.energia
      case _ => -1
    }
  }
}

case object oponentesFuertes extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Double = {
    movimiento(atacante,atacado) match{
      case Peleando(_, atacadoDespues) => atacadoDespues.energia
      case _ => -1
    }
  }
}

case object tacanio extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Double = {
    movimiento(atacante,atacado) match{
      case Peleando(atacanteDespues,_) => atacanteDespues.items.size
      case _ => -1
    }
  }
}

case object queNoLoMate extends Criterio{
  def apply(atacante:Guerrero,atacado:Guerrero, movimiento:Movimiento): Double = {
    movimiento(atacante,atacado) match{
      case Peleando(atacanteDespues,_) if (atacanteDespues.estaMuerto) => -1
      case Peleando(atacanteDespues,_) => atacanteDespues.energia
      case _ => -1
    }
  }
}
