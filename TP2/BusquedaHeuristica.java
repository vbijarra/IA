import java.util.*;

// Clase para representar un nodo en el grafo
class Nodo implements Comparable<Nodo> {
    String nombre;
    int h; // Valor heurístico (distancia estimada a la meta)
    List<Arista> adyacentes;

    public Nodo(String nombre, int h) {   //constructor de la clase nodo
        this.nombre = nombre;  //nombre
        this.h = h;     //heuristica
        this.adyacentes = new ArrayList<>();    //lista vacia para las conexiones adyacentes
    }

    public void agregarAdyacente(Nodo destino) {   //crea lista adyacente
        this.adyacentes.add(new Arista(destino));
    }

    @Override
    public int compareTo(Nodo otro) {
        // Prioridad al nodo con la heurística más pequeña 
        return Integer.compare(this.h, otro.h);
    }
}

// Clase para representar las conexiones  conecta a un nodo con otro 
class Arista {
    Nodo destino;
    public Arista(Nodo destino) { this.destino = destino; }
}

public class Main {   //clase principal

    public static void main(String[] args) { 
        // Definir los nodos con su heurística (h) al objetivo (Meta)
        Nodo a = new Nodo("A", 10);
        Nodo b = new Nodo("B", 8);
        Nodo c = new Nodo("C", 5);
        Nodo d = new Nodo("D", 7);
        Nodo e = new Nodo("E", 3);
        Nodo f = new Nodo("F", 2);
        Nodo meta = new Nodo("Meta", 0);

        // agregar conexiones (grafo)
        a.agregarAdyacente(b);
        a.agregarAdyacente(c);
        b.agregarAdyacente(d);
        c.agregarAdyacente(e);
        c.agregarAdyacente(f);
        f.agregarAdyacente(meta);

        // Ejecutar la búsqueda
        buscar(a, meta);
    }

    public static void buscar(Nodo inicio, Nodo meta) {
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();  //organiza los nodos para explorar primero el de menor heuristica
        Set<String> visitados = new HashSet<>();   //controla los nodos ya explorados

        colaPrioridad.add(inicio);   //agrega  nodo inicial
        visitados.add(inicio.nombre);   //agrega nodos visitados

        System.out.print("Orden de exploración: ");

        while (!colaPrioridad.isEmpty()) {   //mientras hay nodos para explorar 
            Nodo actual = colaPrioridad.poll();   //saca el nodo con menor heuristica de la cola - el mejor nodo actual
            System.out.print(actual.nombre + " ");

            if (actual.nombre.equals(meta.nombre)) {   //verifica si el nodo actual es la meta
                System.out.println("\n¡Meta alcanzada!");
                return;
            }

            for (Arista arista : actual.adyacentes) {   //busca entre los nodos adyacentes del nodo actual
                if (!visitados.contains(arista.destino.nombre)) {   //sino ha sido visitado lo agrega en la lista de visitados 
                    visitados.add(arista.destino.nombre);
                    colaPrioridad.add(arista.destino);  //lo agrega a colaPrioridad para ser evaluado luego 
                }
            }
        }
        System.out.println("\nNo se encontró la meta.");  //no encontró la meta 
    }
}