import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HoughRectas {

    private int rhoMax;   //dimension del espacio
    private int[][] acumulador;   //matriz bidimensional las filas representan la distancia y las columnas el angulo theta
    private double[] sinCache;  //guarda valor de seno  
    private double[] cosCache;  //guarda valor de coseno
    private int thetaMax = 180;  //dimension del espacio . Angulo de inclinación 
    
    List<double[]> points = new ArrayList<>(); //

    public void TransfHoughRectas(List<double[]> points, int width, int height) {  //constructor que recibe los puntos, el ancho y el alto de la imágen
        this.points = points;

        rhoMax = (int) Math.hypot(width, height);   //calcula la diagonal maxima
        acumulador = new int[2 * rhoMax][thetaMax];  //define el tamaño de la matriz 

        sinCache = new double[thetaMax];   
        cosCache = new double[thetaMax];  

        for (int theta = 0; theta < thetaMax; theta++) {
            double thetaRad = Math.toRadians(theta);
            sinCache[theta] = Math.sin(thetaRad);   //completa arreglo con valores trigonometricos para seno
            cosCache[theta] = Math.cos(thetaRad);  //completa arreglo con valores trigonometricos para coseno
        }
    }

    // Realiza la acumulación de votos en el espacio de Hough
    public void performTransform() {   //itera sobre cada uno de los puntos x,y de la lista
        for (double[] p : points) {
            double x = p[0];      
            double y = p[1];
            for (int theta = 0; theta < thetaMax; theta++) {   //para cada punto recorre los angulos posibles
                int rho = (int) Math.round(x * cosCache[theta] + y * sinCache[theta]) + rhoMax;   //aplica la ecucación de de la recta 
                if (rho >= 0 && rho < 2 * rhoMax)
                    acumulador[rho][theta]++;  //incrementa el valor de acumulator 
            }
        }
    }

    // Devuelve el máximo de la acumulación (parámetros de la recta)
    public int[] getMaxAcumulador() {
        int max = 0;
        int rhoMaxFound = 0;
        int thetaMaxFound = 0;

        for (int rho = 0; rho < 2 * rhoMax; rho++) {   //recorre la matriz buscando el valor numercio más alto, que representa la recta con mayor cantidad de puntos alineados. 
            for (int theta = 0; theta < thetaMax; theta++) {
                if (acumulador[rho][theta] > max) {
                    max = acumulador[rho][theta];
                    rhoMaxFound = rho - rhoMax;
                    thetaMaxFound = theta;
                }
            }
        }

        return new int[]{rhoMaxFound, thetaMaxFound, max};   //retorna la linea ganadora con cantidad de puntos 
    }

 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Ingresa la resolución de la imagen (ancho y alto separados por espacio):");
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        List<double[]> testPoints = new ArrayList<>();
        System.out.println("Ingresa puntos (x y) para evaluar. Escribe '-1 -1' para terminar:");

        while (true) {
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();

            if (x == -1 && y == -1) {
                break;
            }
            testPoints.add(new double[]{x, y});
        }

        HoughRectas hough = new HoughRectas();
        hough.TransfHoughRectas(testPoints, width, height);   
        hough.performTransform();

        int[] result = hough.getMaxAcumulador();
        System.out.println("\n--- Resultados ---");
        System.out.println("Mejor Rho: " + result[0]);
        System.out.println("Mejor Theta: " + result[1] + " grados");
        System.out.println("Votos acumulados: " + result[2]);

        scanner.close();
    }
}