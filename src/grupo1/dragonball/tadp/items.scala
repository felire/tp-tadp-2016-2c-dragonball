package grupo1.dragonball.tadp

abstract class Item
{
   type f =(Guerrero, Guerrero)
    def apply(guerreros :f) : Resultado
}

abstract class Arma extends Item{
  
}

class Roma extends Arma
{
  def apply(guerreros: Guerreros)
  {
    atacado match
    {
      case atacado:GuerreroOrganico => 
        if(atacado.ki < 300)
        {
          atacado.conciente = false //estaba en true pero creo q va false
        }
    }
  }
}


class Filosa extends Arma
{
  def apply(guerreros: Guerreros)
  {
    var kiARestar = propietario.asInstanceOf[GuerreroOrganico].ki/100
    atacado match
    {
      case atacado:Saiyajin => atacado.estado.recibirAtaqueFilosa(atacado)
      case atacado:GuerreroOrganico => atacado.ki-=kiARestar
      case _ =>
    }
  }
}

class Fuego(var balas:Int) extends Arma
{
  def tieneBalas :Boolean=
  {
    balas>0
  }
  
  def apply(guerreros: Guerreros)
  {
    if(tieneBalas)
    {//si no, falta tirar excepcion o algo
      balas-=1
      atacado match
      {
        case atacado:Humano => atacado.ki -= 20
        case atacado:Namekusein if(!atacado.conciente) => atacado.ki -= 10   
        case _ =>
      }
    }
  }
}

class SemillaErmitanio extends Item
{
  def apply(guerreros: Guerreros)
  {
    propietario match
    {
      case propietario:GuerreroOrganico => propietario.ki = propietario.kiMaximo
      case _ =>
    }
  }
}

object FotoDeLaLuna extends Item
{
    def apply(guerreros: Guerreros){}
}

class EsferaDelDragon(val estrella: Int) extends Item{
  def apply(guerreros: Guerreros){}
}

