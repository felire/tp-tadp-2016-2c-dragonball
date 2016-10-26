package grupo1.dragonball.tadp

abstract class Item{
  def usar(propietario: Guerrero, atacado:Guerrero)
}

abstract class Arma extends Item{
  
}

class Roma extends Arma{
  override def usar(propietario:Guerrero, atacado: Guerrero){
    atacado match{
      case Androide(_, _, _) =>
      case _ => 
        var guerrero = atacado.asInstanceOf[GuerreroOrganico]
        if(guerrero.ki < 300){
          guerrero.conciente = true
        }
    }
  }
}


class Filosa extends Arma{
  override def usar(propietario:Guerrero, atacado: Guerrero){
    var kiARestar = propietario.asInstanceOf[GuerreroOrganico].ki/100
    atacado match{
      case atacado:Saiyajin => atacado.estado.recibirAtaqueFilosa(atacado)
      case atacado:Androide => 
      case atacado:GuerreroOrganico => atacado.ki-=kiARestar
    }
  }
}

class Fuego extends Arma{
  override def usar(propietario:Guerrero, atacado:Guerrero){
    
  }
}

class SemillaErmitanio extends Item{
  override def usar(propietario:Guerrero, nada: Guerrero){
    
  }
}
