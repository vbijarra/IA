import java.util.Arrays;

public class Main {
    // imagen "C"   los 1 son pixeles marcados y -1 blancos
    static int[] imagenC = {
        1, 1, 1, 1, 1,
        1,-1,-1,-1,-1,
        1,-1,-1,-1,-1,
        1,-1,-1,-1,-1,
        1, 1, 1, 1, 1
    };

    public static void main(String[] args) {
        int n = imagenC.length;    
        double[][] peso = new double[n][n];    //Se define matriz para los pesos 

        //Se carga la Matriz de pesos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) peso[i][j] = imagenC[i] * imagenC[j];
                else peso[i][j] = 0;
            }
        }

        // Se crea un clon de imagenC y se modifican algunos pixeles se simula el ruido o faltante de pixeles 
        int[] entRuidosa = imagenC.clone();   
        entRuidosa[0] = -1; // borra la esquina superior
        entRuidosa[24] = -1; // borra la esquina inferior

        System.out.println("Entrada con ruido (C dañada):");  //se imprime la imagen distorcionada
        imprime(entRuidosa);

        //se almacena la imagen distorcionada con el peso en resultado[]
        int[] resultado = recImagen(entRuidosa, peso);

        System.out.println("\nPatrón recuperado por Hopfield:");
        imprime(resultado);   //imprime el resultado 
    }

    static int[] recImagen(int[] entrada, double[][] peso) {    //recorre la entrada 
        int n = entrada.length;
        int[] actual = entrada.clone();
        boolean cambia = true;   //bandera booleana para controlar el bucle. Inicializa en true para asegurar que ingrese una vez al while
        
        while (cambia) {    
            cambia = false;     //cambia la bandera a false asume que no habrá cambios. Si no hay cambos entonces en el bucle siguiente la banderá serguirá siendo false y no ingresará más al while
            for (int i = 0; i < n; i++) {   //recorre cada pixel de la imagem desde  0 a n-1
                double sum = 0; 
                for (int j = 0; j < n; j++) {    //calcula la sumatoria de la neurona i.   
                    sum += peso[i][j] * actual[j];   //producto escalar del vector de pesos de la neurona i y el estado actual de la red
                }
                int nuevoVal = (sum >= 0) ? 1 : -1;   //si la suma es mayor o igual que cero el nuevo valor del pixel será 1 de lo contrario -1
                if (nuevoVal != actual[i]) {    //Compara el nuevo valor con el anterior. 
                    actual[i] = nuevoVal;    //Si son diferentes actualiza el pixel con el nuevo valor 
                    cambia = true;     //para que ingrese nuevamente al while
                }
            }
        }
        return actual;   //retorna actual una vez que no hay cambios en la última pasada
    }

    static void imprime(int[] p) {   //imprime en output con la letra 
        for (int i = 0; i < p.length; i++) {
            System.out.print((p[i] == 1 ? "█" : ".") + " ");  //si el vector tiene 1 entonces imprime █ para dibujar la C sino un punto "." simulando un espacio en blanco 
            if ((i + 1) % 5 == 0) System.out.println();
        }
    }
}
