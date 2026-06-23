import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que implementa la Transformada de Hough para detectar circunferencias
 * de un radio conocido en un espacio de puntos bidimensional.
 */
public class TransfHoughCirc {

    private final int[][] accumulator;    //matriz bidimensional donde cada celda representa una coordenada del plano y acumula votos. 
    private final int width;    //dimension de la matriz bidimensional
    private final int height;   //dimension de la matriz bidimensional
    private final int radius;   //radio de la circunsferencia que se desea detectar
    private final List<double[]> points;   //almacena los puntos bidimensionales 

     
    private static final double[] COS_TABLE = new double[360];   //arreglo para almacenar valores de coseno
    private static final double[] SIN_TABLE = new double[360];  //arreglo para almacenar valores de seno

    static {      //precalcula los valores trigonometricos
        for (int theta = 0; theta < 360; theta++) { 
            double radians = Math.toRadians(theta);
            COS_TABLE[theta] = Math.cos(radians);
            SIN_TABLE[theta] = Math.sin(radians);
        }
    }

    public TransfHoughCirc(List<double[]> points, int width, int height, int radius) {   //inicializa los atributos de la clase 
        this.points = points;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.accumulator = new int[width][height];
    }

    /**
     * Ejecuta la transformada de Hough utilizando las tablas trigonométricas precalculadas.
     */
    public void performTransform() {
        for (double[] p : points) {   //recorre cada punto x,y ingresado
            double x = p[0];
            double y = p[1];

            for (int theta = 0; theta < 360; theta++) {   //para cada punto dibuja una circunsferencia imaginaria de 360° a su alrededor 
                // Ecuación paramétrica de la circunferencia para hallar el centro (a, b)
                int a = (int) Math.round(x - radius * COS_TABLE[theta]);
                int b = (int) Math.round(y - radius * SIN_TABLE[theta]);

                // Verificación de límites de la matriz del acumulador
                if (a >= 0 && a < width && b >= 0 && b < height) {  //verifica que las coordenadas calculadas del posible centro a, b se encuentren dentro de los limites de la matriz 
                    accumulator[a][b]++;    //suma el voto a la celda-. El lugar donde se crucen más circunsferencias será el centro real. 
                }
            }
        }
    }


    public int[] getMaxAccumulator() {   //Busca la coordenada (a, b) que obtuvo la mayor cantidad de votos en el acumulador.
        int max = 0;
        int aMaxFound = 0;
        int bMaxFound = 0;

        for (int a = 0; a < width; a++) {
            for (int b = 0; b < height; b++) {
                if (accumulator[a][b] > max) {
                    max = accumulator[a][b];
                    aMaxFound = a;
                    bMaxFound = b;
                }
            }
        }
        return new int[]{aMaxFound, bMaxFound, max};
    }

    public static void main(String[] args) {    //metodo principal. Solicita entrada de parámetros
        Scanner scanner = new Scanner(System.in);
        List<double[]> points = new ArrayList<>();

        System.out.print("\nIngrese el radio conocido para la circunferencia: ");
        int radius = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Ingrese pares de coordenadas (x y) separados por espacio.");
        System.out.println("Puede finalizar la introducción escribiendo la letra 'q'.");

        int contador = 1;
        while (true) {
            System.out.print("(x[" + contador + "] ; y[" + contador + "]) = ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                break;
            }

            // Soporta múltiples espacios o tabulaciones como separadores
            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                System.out.println("¡Error! Deben ingresarse dos valores numéricos separados por espacio.");
                continue;
            }

            try {
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                points.add(new double[]{x, y});
                contador++;
            } catch (NumberFormatException e) {
                System.out.println("¡Error! Ingrese números decimales o enteros válidos.");
            }
        }

        if (points.isEmpty()) {
            System.out.println("No se ingresaron coordenadas.");
            scanner.close();
            return;
        }

        System.out.println("\nValores ingresados:");
        for (int i = 0; i < points.size(); i++) {
            double[] point = points.get(i);
            System.out.printf("[%d] (x ; y) = (%.1f ; %.1f)\n", i + 1, point[0], point[1]);
        }
        System.out.println();

    
        int width = (int) points.stream().mapToDouble(p -> p[0]).max().orElse(0) + radius + 10;
        int height = (int) points.stream().mapToDouble(p -> p[1]).max().orElse(0) + radius + 10;

        // Instancia y ejecución del algoritmo
        TransfHoughCirc hough = new TransfHoughCirc(points, width, height, radius);
        hough.performTransform();

        int[] circleParams = hough.getMaxAccumulator();   //obtiene los resultados de máxima votación 


        if (circleParams[2] > 1) {           // Impresión de resultados en consola
            System.out.println("Centro detectado de la circunferencia:");
            System.out.println("(a ; b) = (" + circleParams[0] + " ; " + circleParams[1] + ")");
            System.out.println("Votos (puntos que coinciden): " + circleParams[2]);
        } else {   //si el voto es 1 o menor a 1 no se detectó coincidencia
            System.out.println("No se detectó claramente una circunferencia con los puntos provistos.");
        }

        scanner.close();
    }
}