package ual.eda2.practica02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Clase de prueba para la implementación del algoritmo DijkstraEj8.
 * Este conjunto de pruebas valida el comportamiento del algoritmo en diferentes escenarios,
 * incluyendo grafos simples, desconectados, con ciclos y casos donde no hay caminos posibles.
 */
class DijkstraEj8Test {

    /**
     * Prueba el algoritmo de Dijkstra en un grafo simple y conectado.
     * Verifica que las distancias calculadas sean correctas y que el camino más corto se determine adecuadamente.
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

        Vertex fuente = a;
        Vertex destino = c;
        Vertex intermedio = b;

        // Ejecutar el algoritmo
        Map<String, Object> result = DijkstraEj8.DijkstraIntermedio(graph, fuente, intermedio, destino);

        // Extraer resultados
        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> distFuente = (TreeMap<Vertex, Double>) result.get("distFuente");
        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> distIntermedio = (TreeMap<Vertex, Double>) result.get("distIntermedio");

        // Verificar distancias calculadas
        assertEquals(0.0, distFuente.get(fuente), "La distancia de A a sí mismo debe ser 0");
        assertEquals(5.0, distFuente.get(intermedio), "La distancia de A a B debe ser 5.0");
        assertEquals(7.0, distFuente.get(destino), "La distancia de A a C debe ser 7.0 (A -> B -> C)");
        assertEquals(0.0, distIntermedio.get(intermedio), "La distancia de B a sí mismo debe ser 0");
        assertEquals(2.0, distIntermedio.get(destino), "La distancia de B a C debe ser 2.0");
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo desconectado.
     * Verifica que los vértices no alcanzables tengan distancias apropiadas (infinito).
     */
    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        // Crear un grafo desconectado
        graph.addEdge(a, b, 5.0);
        graph.addVertex(c); // Añadir vértice c sin conexiones

        Vertex source = a;

        // Ejecutar el algoritmo
        Map<String, Object> result = DijkstraEj8.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias calculadas
        assertEquals(0.0, dist.get(a), "La distancia al origen debe ser 0");
        assertEquals(5.0, dist.get(b), "La distancia al vértice B debe ser 5.0");
        assertEquals(Double.POSITIVE_INFINITY, dist.get(c), "La distancia al vértice C debe ser infinita");
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo con ciclos.
     * Verifica que el algoritmo maneje correctamente los ciclos y calcule el camino más corto.
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
        graph.addEdge(d, c, 2.0);

        Vertex source = a;

        // Ejecutar el algoritmo
        Map<String, Object> result = DijkstraEj8.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias calculadas
        assertEquals(0.0, dist.get(a), "La distancia al origen debe ser 0");
        assertEquals(2.0, dist.get(b), "La distancia a B debe ser 2.0");
        assertEquals(3.0, dist.get(c), "La distancia más corta a C debe ser 3.0 (A -> D -> C)");
        assertEquals(1.0, dist.get(d), "La distancia a D debe ser 1.0");
    }

    /**
     * Prueba las métricas de rendimiento del algoritmo de Dijkstra.
     * Verifica que las estadísticas de rendimiento sean válidas, incluyendo tiempo de ejecución,
     * iteraciones, actualizaciones, vértices procesados y aristas procesadas.
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
        Map<String, Object> result = DijkstraEj8.Dijkstra(graph, source);

        long tiempoEjecucion = (long) result.get("tiempo_ejecucion_ns");
        int iteraciones = (int) result.get("iteraciones");
        int actualizaciones = (int) result.get("actualizaciones");
        int verticesProcesados = (int) result.get("vertices_procesados");
        int aristasProcesadas = (int) result.get("aristas_procesadas");

        // Verificar estadísticas de rendimiento
        assertTrue(tiempoEjecucion > 0, "El tiempo de ejecución debe ser positivo");
        assertTrue(iteraciones > 0, "Debe haber iteraciones realizadas");
        assertTrue(actualizaciones >= 0, "Las actualizaciones deben ser >= 0");
        assertTrue(verticesProcesados > 0, "Debe haber vértices procesados");
        assertTrue(aristasProcesadas >= 0, "Debe haber aristas procesadas");
    }

    /**
     * Prueba el algoritmo de Dijkstra en un caso donde no hay camino posible entre los vértices.
     * Verifica que los vértices no conectados tengan una distancia infinita.
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
        Map<String, Object> result = DijkstraEj8.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias calculadas
        assertEquals(0.0, dist.get(a), "La distancia al origen debe ser 0");
        assertEquals(Double.POSITIVE_INFINITY, dist.get(b), "La distancia al vértice B debe ser infinita");
    }
}