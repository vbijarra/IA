import java.util.*;

public class BusquedaHeuristicaBrazo {
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
        
 
    }

    public static void main(String[] args) {
        // 1. Posición Inicial A
        Punto3D posicionActual = new Punto3D(75, 10, 20);
        Punto3D objetivo = new Punto3D(90, 20, 30);
        System.out.println("Posición Inicial A: " + posicionActual);
        double Desplazamiento = 5;
        
        ejecutarBusquedaHeuristica(posicionActual, objetivo, Desplazamiento);
         
    }
    

    
    public static void ejecutarBusquedaHeuristica(Punto3D actual, Punto3D objetivo, double Desplazamiento) {
        int intentos = 0;

        Punto3D newActual = new Punto3D(actual.x, actual.y, actual.z); 
        
        
        while (true) {  
           intentos++; 
            double DifActualX= calcularHeuristica(newActual.x, objetivo.x); 
            double DifActualY= calcularHeuristica(newActual.y, objetivo.y); 
            double DifActualZ= calcularHeuristica(newActual.z, objetivo.z); 
             
            System.out.printf("Posición actual: (%.2f, %.2f, %.2f) | Distancia al objetivo x: %.2f, y: %.2f,  z:%.2f mm%n", newActual.x, newActual.y, newActual.z, DifActualX, DifActualY, DifActualZ);
             
            if (DifActualX == 0 && DifActualY == 0 && DifActualZ == 0) {
                actual.x = newActual.x; 
                actual.y = newActual.y;
                actual.z = newActual.z;
                System.out.printf("Objetivo alcanzado. Total de desplazamientos : " +  intentos +  " (%.2f, %.2f, %.2f)%n", actual.x, actual.y, actual.z);
                break;   //si la función es  cero entonces sale del while 
            }
            
            if (DifActualX < 0) { 
                newActual.x=newActual.x-Desplazamiento; 
            } else {
                    if (DifActualX > 0) { 
                      newActual.x=newActual.x+Desplazamiento; 
                  } else { newActual.x=newActual.x; 
                          }}
            
            if (DifActualZ < 0) { 
                newActual.z=newActual.z-Desplazamiento; 
            } else {
                    if (DifActualZ > 0) { 
                      newActual.z=newActual.z+Desplazamiento; 
                  } else { newActual.z=newActual.z; 
                          }}
            
             if (DifActualY < 0) { 
                newActual.y=newActual.y-Desplazamiento; 
            } else {
                    if (DifActualY > 0) { 
                      newActual.y=newActual.y+Desplazamiento; 
                  } else { newActual.y=newActual.y; 
                          }}
                          
   
                          
          if (intentos > 150) break;  //para evitar bucles infinitos
        }   
    }

      public static double calcularHeuristica(double inicio, double obj) {
            return    Math.sqrt(Math.pow(obj - inicio, 2));
      }
      
    }



