package ual.eda2.practica02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Clase de prueba para la implementación del algoritmo de Dijkstra Ej7 (DijkstraEj7).
 * Este conjunto de pruebas verifica la funcionalidad del algoritmo en diferentes escenarios,
 * incluyendo grafos simples, desconectados, con ciclos y casos donde no hay caminos posibles.
 */
class DijkstraEj7Test {

    /**
     * Prueba el algoritmo de Dijkstra en un grafo simple y conectado.
     * Verifica que las distancias calculadas sean correctas y que los caminos se encuentren adecuadamente.
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

        Vertex source = a;

        // Ejecutar el algoritmo
        Map<String, Object> result = DijkstraEj7.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(Double.POSITIVE_INFINITY, dist.get(a), "La distancia a sí mismo debe ser infinita");
        assertEquals(5.0, dist.get(b), "La distancia a B debe ser 5.0");
        assertEquals(10.0, dist.get(c), "La distancia más corta a C debe ser 10.0");
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo desconectado.
     * Verifica que las distancias sean correctas y que los vértices no alcanzables
     * tengan un valor apropiado.
     */
    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        // Crear un grafo desconectado
        graph.addEdge(a, b, 5.0);

        Vertex source = a;

        // Ejecutar el algoritmo
        Map<String, Object> result = DijkstraEj7.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(Double.POSITIVE_INFINITY, dist.get(a), "La distancia a sí mismo debe ser infinita");
        assertEquals(5.0, dist.get(b), "La distancia a B debe ser 5.0");
        assertNull(dist.get(c), "C no es alcanzable y su distancia debe ser null");
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo con ciclos.
     * Verifica que el algoritmo maneje correctamente los ciclos y encuentre el camino más corto.
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

        Vertex source = a;

        // Ejecutar el algoritmo
        Map<String, Object> result = DijkstraEj7.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(Double.POSITIVE_INFINITY, dist.get(a), "La distancia a sí mismo debe ser infinita");
        assertEquals(2.0, dist.get(b), "La distancia a B debe ser 2.0");
        assertEquals(2.0, dist.get(c), "La distancia más corta a C debe ser 2.0 (A -> D -> C)");
        assertEquals(1.0, dist.get(d), "La distancia a D debe ser 1.0");
    }

    /**
     * Prueba las métricas de rendimiento del algoritmo de Dijkstra.
     * Verifica que las estadísticas como tiempo de ejecución, iteraciones, actualizaciones,
     * vértices procesados y aristas procesadas sean válidas.
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

        Vertex source = a;

        // Ejecutar el algoritmo
        Map<String, Object> result = DijkstraEj7.Dijkstra(graph, source);

        long tiempoEjecucion = (long) result.get("tiempo_ejecucion_ns");
        int iteraciones = (int) result.get("iteraciones");
        int actualizaciones = (int) result.get("actualizaciones");
        int verticesProcesados = (int) result.get("vertices_procesados");
        int aristasProcesadas = (int) result.get("aristas_procesadas");

        // Verificar estadísticas de rendimiento
        assertTrue(tiempoEjecucion > 0, "El tiempo de ejecución debe ser positivo");
        assertTrue(iteraciones > 0, "Debe haber iteraciones realizadas");
        assertTrue(actualizaciones > 0, "Debe haber actualizaciones realizadas");
        assertTrue(verticesProcesados > 0, "Debe haber vértices procesados");
        assertTrue(aristasProcesadas > 0, "Debe haber aristas procesadas");
    }

    /**
     * Prueba el algoritmo de Dijkstra en un caso donde no hay camino posible entre los vértices.
     * Verifica que los vértices no alcanzables tengan un valor apropiado.
     */
    @Test
    void testNoPath() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");

        graph.addVertex(a);
        graph.addVertex(b);

        Vertex source = a;

        // Ejecutar el algoritmo
        Map<String, Object> result = DijkstraEj7.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(Double.POSITIVE_INFINITY, dist.get(a), "La distancia a sí mismo debe ser infinita");
        assertEquals(Double.NEGATIVE_INFINITY, dist.get(b), "B no es alcanzable y su distancia debe ser negativa infinita");
    }
}