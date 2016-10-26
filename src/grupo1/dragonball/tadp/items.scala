package grupo1.dragonball.tadp

abstract class Item{
  def usar(propietario: Guerrero, atacado:Guerrero)
}

abstract class Arma extends Item{
  
}

class Roma extends Arma{
  override def usar(propietario:Guerrero, atacado: Guerrero){
    atacado match{
      //case Androide(_, _, _) =>
      case atacado:GuerreroOrganico => 
        if(atacado.ki < 300){
          atacado.conciente = true
        }
    }
  }
}


class Filosa extends Arma{
  override def usar(propietario:Guerrero, atacado: Guerrero){
    var kiARestar = propietario.asInstanceOf[GuerreroOrganico].ki/100
    atacado match{
      case atacado:Saiyajin => atacado.estado.recibirAtaqueFilosa(atacado)
      //case atacado:Androide => 
      case atacado:GuerreroOrganico => atacado.ki-=kiARestar
    }
  }
}

class Fuego(var balas:Int) extends Arma{
  
  def tieneBalas :Boolean={
    balas>0
  }
  
  override def usar(propietario:Guerrero, atacado:Guerrero){
    if(tieneBalas){ //si no, falta tirar excepcion o algo
      balas-=1
      atacado match{
      case atacado:Humano => atacado.ki -= 20
      case atacado:Namekusein if(!atacado.conciente) => atacado.ki -= 10      
      }
    }
  }
}

class SemillaErmitanio extends Item{
  override def usar(propietario:Guerrero, nada: Guerrero){
    propietario match{
      case propietario:GuerreroOrganico => propietario.ki = propietario.kiMaximo
    }
  }
}

class FotoDeLaLuna extends Item{
    override def usar(propietario:Guerrero, nada: Guerrero){
    }
}


