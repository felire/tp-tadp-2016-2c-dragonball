package grupo1.dragonball.tadp

abstract class Criterio

case object favoreceKi extends Criterio {
    def apply(antes: Guerrero, despues: Guerrero): Int = {
      antes.energia - despues.energia 
    }
  }