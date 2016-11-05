package grupo1.dragonball.tadp
trait EstadoPelea
case object KO extends EstadoPelea
case class Fajadas(cantidadSeguidas: Int) extends EstadoPelea
case object Muerto extends EstadoPelea
case object Luchando extends EstadoPelea