package ual.eda2.practica02;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Clase de prueba para la implementación del algoritmo de Dijkstra Ej6 (DijkstraEj6).
 * Este conjunto de pruebas verifica la funcionalidad del algoritmo en diferentes escenarios,
 * incluyendo grafos simples, desconectados, con ciclos y casos donde no hay caminos posibles.
 */
class DijkstraEj6Test {

    /**
     * Prueba el algoritmo de Dijkstra en un grafo simple y conectado.
     * Verifica que el camino encontrado y la distancia calculada sean correctos.
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

        // Coordenadas para la heurística
        Map<Vertex, double[]> positions = new HashMap<>();
        positions.put(a, new double[]{0, 0});
        positions.put(b, new double[]{1, 0});
        positions.put(c, new double[]{2, 0});

        Vertex source = a;
        Vertex destination = c;

        Map<String, Object> result = DijkstraEj6.findPath(graph, source, destination, positions);

        @SuppressWarnings("unchecked")
        List<Vertex> path = (List<Vertex>) result.get("path");
        double distance = (double) result.get("distance");
        boolean pathFound = (boolean) result.get("pathFound");

        // Verificar el camino encontrado
        Assertions.assertTrue(pathFound, "Se debe encontrar un camino");
        Assertions.assertEquals(Arrays.asList(a, b, c), path, "El camino más corto debe ser A -> B -> C");
        Assertions.assertEquals(7.0, distance, "La distancia más corta debe ser 7.0");
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo desconectado.
     * Verifica que no se encuentra un camino si los vértices no están conectados.
     */
    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        // Crear un grafo desconectado
        graph.addEdge(a, b, 5.0);

        // Coordenadas para la heurística
        Map<Vertex, double[]> positions = new HashMap<>();
        positions.put(a, new double[]{0, 0});
        positions.put(b, new double[]{1, 0});
        positions.put(c, new double[]{2, 0});

        Vertex source = a;
        Vertex destination = c;

        Map<String, Object> result = DijkstraEj6.findPath(graph, source, destination, positions);

        @SuppressWarnings("unchecked")
        List<Vertex> path = (List<Vertex>) result.get("path");
        Object obj = result.get("distance");
        double distance;
        if (obj == null) {
            distance = Double.POSITIVE_INFINITY;
        } else {
            distance = (double) obj;
        }
        boolean pathFound = (boolean) result.get("pathFound");

        // Verificar que no se encuentra un camino
        Assertions.assertFalse(pathFound, "No debe encontrarse un camino");
        Assertions.assertTrue(path.isEmpty(), "El camino debe estar vacío");
        Assertions.assertEquals(Double.POSITIVE_INFINITY, distance, "La distancia debe ser infinita");
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

        // Coordenadas para la heurística
        Map<Vertex, double[]> positions = new HashMap<>();
        positions.put(a, new double[]{0, 0});
        positions.put(b, new double[]{1, 0});
        positions.put(c, new double[]{2, 0});
        positions.put(d, new double[]{1, 1});

        Vertex source = a;
        Vertex destination = c;

        Map<String, Object> result = DijkstraEj6.findPath(graph, source, destination, positions);

        @SuppressWarnings("unchecked")
        List<Vertex> path = (List<Vertex>) result.get("path");
        double distance = (double) result.get("distance");
        boolean pathFound = (boolean) result.get("pathFound");

        // Verificar el camino encontrado
        Assertions.assertTrue(pathFound, "Se debe encontrar un camino");
        Assertions.assertEquals(Arrays.asList(a, d, c), path, "El camino más corto debe ser A -> D -> C");
        Assertions.assertEquals(2.0, distance, "La distancia más corta debe ser 2.0");
    }

    /**
     * Prueba las métricas de rendimiento del algoritmo de Dijkstra.
     * Verifica que las estadísticas como tiempo de ejecución, iteraciones y actualizaciones sean válidas.
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

        // Coordenadas para la heurística
        Map<Vertex, double[]> positions = new HashMap<>();
        positions.put(a, new double[]{0, 0});
        positions.put(b, new double[]{1, 0});
        positions.put(c, new double[]{2, 0});
        positions.put(d, new double[]{3, 0});

        Vertex source = a;
        Vertex destination = d;

        Map<String, Object> result = DijkstraEj6.findPath(graph, source, destination, positions);

        long tiempoEjecucion = (long) result.get("tiempo_ejecucion_ns");
        int iteraciones = (int) result.get("iteraciones");
        int actualizaciones = (int) result.get("actualizaciones");

        // Verificar estadísticas de rendimiento
        Assertions.assertTrue(tiempoEjecucion > 0, "El tiempo de ejecución debe ser positivo");
        Assertions.assertTrue(iteraciones > 0, "Debe haber iteraciones realizadas");
        Assertions.assertTrue(actualizaciones > 0, "Debe haber actualizaciones realizadas");
    }

    /**
     * Prueba el algoritmo de Dijkstra en un caso donde no hay camino posible entre los vértices.
     * Verifica que el resultado indique que no se encuentra un camino.
     */
    @Test
    void testNoPath() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");

        // Coordenadas para la heurística
        Map<Vertex, double[]> positions = new HashMap<>();
        positions.put(a, new double[]{0, 0});
        positions.put(b, new double[]{1, 0});
        graph.addVertex(a);
        graph.addVertex(b);

        Vertex source = a;
        Vertex destination = b;

        Map<String, Object> result = DijkstraEj6.findPath(graph, source, destination, positions);

        @SuppressWarnings("unchecked")
        List<Vertex> path = (List<Vertex>) result.get("path");
        double distance = (double) result.get("distance");
        boolean pathFound = (boolean) result.get("pathFound");

        // Verificar que no se encuentra un camino
        Assertions.assertFalse(pathFound, "No debe encontrarse un camino");
        Assertions.assertTrue(path.isEmpty(), "El camino debe estar vacío");
        Assertions.assertEquals(Double.POSITIVE_INFINITY, distance, "La distancia debe ser infinita");
    }
}