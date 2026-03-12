package ual.eda2.practica02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

/**
 * Clase de prueba para la implementación del algoritmo de Dijkstra (DijkstraEj4).
 * Este conjunto de pruebas verifica el comportamiento del algoritmo en distintos escenarios,
 * incluyendo grafos simples, desconectados y con ciclos, así como también evalúa las métricas de rendimiento.
 */
public class DijkstraEj4Test {

    /**
     * Prueba el algoritmo de Dijkstra en un grafo simple y conectado.
     * Verifica que las distancias calculadas sean correctas y que las métricas
     * de rendimiento sean válidas.
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

        // Ejecutar Dijkstra desde el vértice A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj4.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(0.0, dist.get(a)); // Distancia al vértice fuente
        assertEquals(5.0, dist.get(b)); // Camino directo
        assertEquals(7.0, dist.get(c)); // Camino más corto: A -> B -> C

        // Verificar estadísticas
        long tiempoEjecucion = (long) result.get("tiempo_ejecucion_ns");
        int iteraciones = (int) result.get("iteraciones");
        int actualizaciones = (int) result.get("actualizaciones");

        assertTrue(tiempoEjecucion > 0, "El tiempo de ejecución debe ser positivo");
        assertTrue(iteraciones > 0, "Debe haber al menos una iteración");
        assertTrue(actualizaciones > 0, "Debe haber al menos una actualización");
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

        // Ejecutar Dijkstra desde el vértice A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj4.Dijkstra(graph, source);

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");

        // Verificar distancias
        assertEquals(0.0, dist.get(a)); // Distancia al vértice fuente
        assertEquals(5.0, dist.get(b)); // Camino directo
        assertEquals(null, dist.get(c), "Vértice no alcanzable debe tener distancia infinita");
    }

    /**
     * Prueba el algoritmo de Dijkstra en un grafo con ciclos.
     * Verifica que las distancias calculadas sean correctas incluso en presencia de ciclos.
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

        // Ejecutar Dijkstra desde el vértice A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj4.Dijkstra(graph, source);

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
     * Verifica que las estadísticas como tiempo de ejecución, iteraciones y actualizaciones sean válidas.
     */
    @Test
    void testPerformanceMetrics() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");

        // Crear un grafo con más aristas y vértices
        graph.addEdge(a, b, 1.0);
        graph.addEdge(b, c, 2.0);
        graph.addEdge(c, d, 3.0);
        graph.addEdge(a, d, 10.0);

        // Ejecutar Dijkstra desde el vértice A
        Vertex source = a;
        Map<String, Object> result = DijkstraEj4.Dijkstra(graph, source);

        // Verificar estadísticas
        long tiempoEjecucion = (long) result.get("tiempo_ejecucion_ns");
        int iteraciones = (int) result.get("iteraciones");
        int actualizaciones = (int) result.get("actualizaciones");

        assertTrue(tiempoEjecucion > 0, "El tiempo de ejecución debe ser mayor que 0");
        assertTrue(iteraciones > 0, "El número de iteraciones debe ser mayor que 0");
        assertTrue(actualizaciones > 0, "El número de actualizaciones debe ser mayor que 0");
    }
}