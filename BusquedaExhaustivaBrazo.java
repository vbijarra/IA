import java.util.*;

public class BusquedaExhaustivaBrazo {
    // Clase interna para representar coordenadas
    static class Punto3D {
        double x, y, z;

        Punto3D(double x, double y, double z) {
            this.x= x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return String.format("(X: %.1f, Y: %.1f, Z: %.1f)", x, y, z);
        }
        
        // Verifica si este punto es igual al objetivo
        boolean esIgual(Punto3D otro) {
            return this.x == otro.x && this.y == otro.y && this.z == otro.z;
        }
         
    }

    public static void main(String[] args) {
        // 1. Posición Inicial A
        Punto3D posicionActual = new Punto3D(75, 20, 30);
        Punto3D objetivo = new Punto3D(90, 20, 30);
        System.out.println("Posición Inicial A: " + posicionActual);
        double Desplazamiento = 5;
        
         ejecutarBusqueda(posicionActual, objetivo, Desplazamiento);
    }
    
    
    
     public static void ejecutarBusqueda(Punto3D actual, Punto3D objetivo, double Desplazamiento) {
        int intentos = 0;
        int increment_despl = 1; 
        boolean encontrado = false;
        
        Punto3D newActual = new Punto3D(actual.x, actual.y, actual.z); 

        // El bucle continúa hasta que la posición X alcance el objetivo
        while (!encontrado ) {
            intentos++;
            if (!(intentos % 2 == 0))  //el intento es impar  va por la derecha 
            {    //es par entonces suma el desplazamiento va a la derecha 
                System.out.println(" -> Moviendo 5mm a la derecha...");
                newActual.x = actual.x + (Desplazamiento*increment_despl);
                
            } else 
            {
                // Lógica de movimiento:  intenta a la izquierda (-X)
                System.out.println(" -> Moviendo 5mm a la izquierda...");
                newActual.x = actual.x - (Desplazamiento*(increment_despl));
                increment_despl++; 
            }
            
            System.out.println("Intento #" + intentos + ": Posición actual " + newActual);
                if (newActual.esIgual(objetivo)) {
                    encontrado = true;
                    System.out.println("\n¡Objetivo alcanzado con éxito!");
                    break;
                }
                

            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }

        System.out.println("Total de desplazamientos realizados: " + intentos);
    }

}