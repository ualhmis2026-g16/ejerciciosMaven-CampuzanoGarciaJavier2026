package ual.eda2.practica02;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Clase de pruebas unitarias para verificar el correcto funcionamiento del
 * algoritmo de Dijkstra implementado en {@link DijkstraEj1}.
 */
public class DijkstraEj1Test {

    // Ruta al directorio donde se encuentran los archivos de entrada de texto.
    String directorioEntrada = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "main" + File.separator +
            "java" + File.separator +
            "ual" + File.separator +
            "eda2" + File.separator +
            "practica02" + File.separator;

    /**
     * Prueba que se pueda cargar correctamente el archivo graphEDAland.txt.
     * Verifica que no se retorne null.
     */
    @Test
    public void loadGraphEdaLand() {
        try {
            Assertions.assertNotNull(DijkstraMainEj1.readGraphFromFile(directorioEntrada + "graphEDAland.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prueba que se pueda cargar correctamente el archivo graphEDAlandLarge.txt.
     * Verifica que no se retorne null.
     */
    @Test
    public void loadGraphEdaLandLarge() throws IOException {
        Assertions.assertNotNull(DijkstraMainEj1.readGraphFromFile(directorioEntrada + "graphEDAlandLarge.txt"));
    }

    /**
     * Prueba el algoritmo de Dijkstra sobre un grafo simple sin ciclos.
     * Verifica que se calculen correctamente las distancias mínimas desde el nodo Madrid.
     */
    @Test
    void testDijkstraSimpleGraph() {
        Graph graph = new Graph();
        Vertex madrid = new Vertex("Madrid");
        Vertex barcelona = new Vertex("Barcelona");
        Vertex sevilla = new Vertex("Sevilla");
        Vertex valencia = new Vertex("Valencia");

        graph.addEdge(madrid, barcelona, 5);
        graph.addEdge(madrid, sevilla, 10);
        graph.addEdge(barcelona, valencia, 3);
        graph.addEdge(sevilla, valencia, 7);

        Vertex source = madrid;
        Map<String, Object> result = DijkstraEj1.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Distancia a sí mismo debería ser null (o 0 según implementación)
        assertEquals(null, dist.get(madrid));
        assertEquals(5.0, dist.get(barcelona));
        assertEquals(10.0, dist.get(sevilla));
        assertEquals(8.0, dist.get(valencia)); // Camino más corto: Madrid -> Barcelona -> Valencia
    }

    /**
     * Prueba el algoritmo de Dijkstra sobre un grafo no conexo (dos componentes separados).
     * Verifica que los nodos no alcanzables tengan distancia infinita.
     */
    @Test
    void testDijkstraDisconnectedGraph() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");

        graph.addEdge(a, b, 3);
        graph.addEdge(c, d, 5);

        Vertex source = a;
        Map<String, Object> result = DijkstraEj1.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        assertEquals(null, dist.get(a)); // Nodo fuente
        assertEquals(3.0, dist.get(b));  // Nodo alcanzable directamente
        assertEquals(Double.POSITIVE_INFINITY, dist.get(c)); // No alcanzable
        assertEquals(Double.POSITIVE_INFINITY, dist.get(d)); // No alcanzable
    }

    /**
     * Prueba el algoritmo de Dijkstra sobre un grafo con ciclos.
     * Verifica que los ciclos no afecten el cálculo correcto de las distancias mínimas.
     */
    @Test
    void testDijkstraWithCycles() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");

        graph.addEdge(a, b, 2);
        graph.addEdge(b, c, 3);
        graph.addEdge(c, a, 4); // Ciclo: A -> B -> C -> A
        graph.addEdge(a, d, 1);
        graph.addEdge(d, c, 1);

        Vertex source = a;
        Map<String, Object> result = DijkstraEj1.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        assertEquals(null, dist.get(a)); // Nodo fuente
        assertEquals(2.0, dist.get(b));
        assertEquals(2.0, dist.get(c)); // Camino óptimo: A -> D -> C
        assertEquals(1.0, dist.get(d));
    }
}
