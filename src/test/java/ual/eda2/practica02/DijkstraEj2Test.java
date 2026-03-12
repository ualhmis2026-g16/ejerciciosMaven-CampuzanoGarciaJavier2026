package ual.eda2.practica02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Clase de prueba para la implementación del algoritmo de Dijkstra (DijkstraEj2).
 * Contiene pruebas unitarias para verificar el correcto funcionamiento del algoritmo
 * en diferentes escenarios, como grafos desconectados, grafos con ciclos, y métricas de rendimiento.
 */
public class DijkstraEj2Test {

    // Ruta del directorio de entrada que contiene los archivos de prueba
    String directorioEntrada = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "main" + File.separator +
            "java" + File.separator +
            "ual" + File.separator +
            "eda2" + File.separator +
            "practica02" + File.separator;

    /**
     * Prueba que verifica la carga del archivo "graphEDAland.txt".
     * Se asegura de que el grafo sea cargado correctamente.
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
     * Prueba que verifica la carga del archivo "graphEDAlandLarge.txt".
     * Se asegura de que el grafo sea cargado correctamente.
     * 
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void loadGraphEdaLandLarge() throws IOException {
        Assertions.assertNotNull(DijkstraMainEj1.readGraphFromFile(directorioEntrada + "graphEDAlandLarge.txt"));
    }
    
    /**
     * Prueba el algoritmo de Dijkstra en un grafo simple y conectado.
     * Verifica que las distancias calculadas sean correctas.
     */
    @Test
    void testDijkstraSimpleGraph() {
        Graph graph = new Graph();
        Vertex madrid = new Vertex("Madrid");
        Vertex barcelona = new Vertex("Barcelona");
        Vertex sevilla = new Vertex("Sevilla");
        Vertex valencia = new Vertex("Valencia");

        // Crear aristas
        graph.addEdge(madrid, barcelona, 5);
        graph.addEdge(madrid, sevilla, 10);
        graph.addEdge(barcelona, valencia, 3);
        graph.addEdge(sevilla, valencia, 7);

        // Ejecutar Dijkstra desde Madrid
        Vertex source = madrid;
        Map<String, Object> result = DijkstraEj2.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(0.0, dist.get(madrid)); // Distancia a sí mismo
        assertEquals(5.0, dist.get(barcelona)); // Camino directo
        assertEquals(10.0, dist.get(sevilla)); // Camino directo
        assertEquals(8.0, dist.get(valencia)); // Camino más corto: Madrid -> Barcelona -> Valencia
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo desconectado.
     * Verifica que los vértices no alcanzables tengan una distancia infinita.
     */
    @Test
    void testDijkstraDisconnectedGraph() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");

        // Crear aristas
        graph.addEdge(a, b, 3);
        graph.addEdge(c, d, 5);

        // Ejecutar Dijkstra desde A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj2.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(0.0, dist.get(a)); // Fuente
        assertEquals(3.0, dist.get(b)); // Camino directo
        assertEquals(Double.POSITIVE_INFINITY, dist.get(c)); // No alcanzable
        assertEquals(Double.POSITIVE_INFINITY, dist.get(d)); // No alcanzable
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo con ciclos.
     * Verifica que el algoritmo calcule correctamente las distancias más cortas
     * incluso en la presencia de ciclos.
     */
    @Test
    void testDijkstraWithCycles() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");

        // Crear aristas con ciclos
        graph.addEdge(a, b, 2);
        graph.addEdge(b, c, 3);
        graph.addEdge(c, a, 4);
        graph.addEdge(a, d, 1);
        graph.addEdge(d, c, 1);

        // Ejecutar Dijkstra desde A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj2.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(0.0, dist.get(a)); // Fuente
        assertEquals(2.0, dist.get(b)); // Camino directo
        assertEquals(2.0, dist.get(c)); // Camino más corto: A -> D -> C
        assertEquals(1.0, dist.get(d)); // Camino directo
    }
    
    /**
     * Prueba el algoritmo de Dijkstra en un grafo simple.
     * Verifica las distancias calculadas y las estadísticas de rendimiento.
     */
    @Test
    void testSimpleGraph() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        // Crear un grafo simple
        graph.addEdge(a, b, 5.0);
        graph.addEdge(b, c, 2.0);
        graph.addEdge(a, c, 10.0);

        // Ejecutar Dijkstra desde A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj2.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(0.0, dist.get(a)); // Distancia a sí mismo
        assertEquals(5.0, dist.get(b)); // Camino directo
        assertEquals(7.0, dist.get(c)); // Camino más corto: A -> B -> C

        // Verificar estadísticas
        long tiempoEjecucion = (long) result.get("tiempo_ejecucion_ns");
        int iteraciones = (int) result.get("iteraciones");
        int actualizaciones = (int) result.get("actualizaciones");

        Assertions.assertTrue(tiempoEjecucion > 0);
        Assertions.assertTrue(iteraciones > 0);
        Assertions.assertTrue(actualizaciones > 0);
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo desconectado.
     * Verifica que los vértices no alcanzables tengan una distancia infinita.
     */
    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        // Crear un grafo desconectado
        graph.addEdge(a, b, 5.0);

        // Ejecutar Dijkstra desde A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj2.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(0.0, dist.get(a)); // Distancia a sí mismo
        assertEquals(5.0, dist.get(b)); // Camino directo
        assertEquals(null, dist.get(c)); // No alcanzable
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo con ciclos.
     * Verifica las distancias más cortas y el correcto manejo de ciclos.
     */
    @Test
    void testGraphWithCycles() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");

        // Crear un grafo con ciclos
        graph.addEdge(a, b, 2.0);
        graph.addEdge(b, c, 3.0);
        graph.addEdge(c, a, 4.0);
        graph.addEdge(a, d, 1.0);
        graph.addEdge(d, c, 1.0);

        // Ejecutar Dijkstra desde A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj2.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(0.0, dist.get(a)); // Fuente
        assertEquals(2.0, dist.get(b)); // Camino directo
        assertEquals(2.0, dist.get(c)); // Camino más corto: A -> D -> C
        assertEquals(1.0, dist.get(d)); // Camino directo
    }

    /**
     * Prueba las métricas de rendimiento del algoritmo de Dijkstra.
     * Verifica que las estadísticas como tiempo de ejecución, iteraciones y actualizaciones sean positivas.
     */
    @Test
    void testPerformanceMetrics() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");

        // Crear un grafo más grande
        graph.addEdge(a, b, 1.0);
        graph.addEdge(b, c, 2.0);
        graph.addEdge(c, d, 3.0);
        graph.addEdge(a, d, 10.0);

        // Ejecutar Dijkstra desde A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj2.Dijkstra(graph, source);

        // Verificar estadísticas
        long tiempoEjecucion = (long) result.get("tiempo_ejecucion_ns");
        int iteraciones = (int) result.get("iteraciones");
        int actualizaciones = (int) result.get("actualizaciones");

        Assertions.assertTrue(tiempoEjecucion > 0);
        Assertions.assertTrue(iteraciones > 0);
        Assertions.assertTrue(actualizaciones > 0);
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo sin aristas.
     * Verifica que los vértices no alcanzables tengan una distancia infinita.
     */
    @Test
    void testNoPath() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");

        // Grafo sin aristas
        Vertex source = a;
        Map<String, Object> result = DijkstraEj2.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(null, dist.get(a)); // Fuente
        assertEquals(null, dist.get(b)); // No alcanzable
    }
}