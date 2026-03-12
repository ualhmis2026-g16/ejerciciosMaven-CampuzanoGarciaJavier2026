package ual.eda2.practica03;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TSPGreedyEdgesTest {

    @Test
    public void testTspGreedyEdges_NullStartVertex() {
        // Crear un grafo simple
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        grafo.addVertex(A);

        // Ejecutar con vértice inicial nulo
        Solucion result = TSPGreedyEdges.tspGreedyEdges(grafo, null);

        // Comprobar que devuelve null
        assertNull(result, "Debería devolver null cuando el vértice inicial es nulo");
    }

    @Test
    public void testTspGreedyEdges_StartVertexNotInGraph() {
        // Crear un grafo simple
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        grafo.addVertex(A);
        
        // Vértice que no existe en el grafo
        Vertex B = new Vertex("B");

        // Ejecutar con vértice inicial que no está en el grafo
        Solucion result = TSPGreedyEdges.tspGreedyEdges(grafo, B);

        // Comprobar que devuelve null
        assertNull(result, "Debería devolver null cuando el vértice inicial no está en el grafo");
    }

    @Test
    public void testTspGreedyEdges_SingleVertex() {
        // Crear un grafo con un único vértice
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        grafo.addVertex(A);

        // Ejecutar con el único vértice
        try {
            TSPGreedyEdges.tspGreedyEdges(grafo, A);
            fail("Debería lanzar excepción para un grafo con un solo vértice");
        } catch (RuntimeException e) {
            // Esperamos que lance excepción ya que no puede formar un ciclo con solo un vértice
            assertTrue(e.getMessage().contains("No existe solución válida"), 
                       "Mensaje de error incorrecto: " + e.getMessage());
        }
    }


    @Test
    public void testTspGreedyEdges_SmallCompleteGraph() {
        // Crear un grafo completamente conectado con 3 nodos
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");

        grafo.addVertex(A);
        grafo.addVertex(B);
        grafo.addVertex(C);

        grafo.addEdge(A, B, 10.0);
        grafo.addEdge(A, C, 15.0);
        grafo.addEdge(B, C, 20.0);
        grafo.addEdge(B, A, 10.0);
        grafo.addEdge(C, A, 15.0);
        grafo.addEdge(C, B, 20.0);

        // Ejecutar TSP voraz
        Solucion result = TSPGreedyEdges.tspGreedyEdges(grafo, A);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null para un grafo completo");
        List<Vertex> path = result.getRecorrido();
        assertEquals(4, path.size(), "El camino debe tener 4 elementos (A, B, C, A)");
        assertEquals(A, path.get(0), "El recorrido debe comenzar en A");
        assertEquals(A, path.get(path.size() - 1), "El recorrido debe terminar en A");
        
        // Verificar que el camino es válido (todos los vértices están conectados en el grafo)
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex current = path.get(i);
            Vertex next = path.get(i + 1);
            assertTrue(grafo.getVecinos(current).contains(next), 
                       "Vértices adyacentes en el camino deben estar conectados en el grafo");
        }
        
        // Comprobar el coste (debería ser 10 + 20 + 15 = 45.0 para el camino A-B-C-A)
        assertEquals(45.0, result.getCoste(), "El coste debe ser 45.0");
    }

    @Test
    public void testTspGreedyEdges_DisconnectedGraph() {
        // Crear un grafo desconectado
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");

        grafo.addVertex(A);
        grafo.addVertex(B);
        grafo.addVertex(C);

        grafo.addEdge(A, B, 10.0);
        grafo.addEdge(B, A, 10.0);
        // C no está conectado a ningún otro vértice

        // Ejecutar TSP voraz
        try {
            TSPGreedyEdges.tspGreedyEdges(grafo, A);
            fail("Debería lanzar excepción para un grafo desconectado");
        } catch (RuntimeException e) {
            // Esperamos que lance excepción ya que no puede formar un ciclo con un grafo desconectado
            assertTrue(e.getMessage().contains("No existe solución válida"), 
                       "Mensaje de error incorrecto: " + e.getMessage());
        }
    }

    @Test
    public void testTspGreedyEdges_VariedWeightGraph() {
        // Crear un grafo con pesos variados
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");
        Vertex D = new Vertex("D");

        grafo.addVertex(A);
        grafo.addVertex(B);
        grafo.addVertex(C);
        grafo.addVertex(D);

        // Las aristas con menor peso forman un camino específico
        grafo.addEdge(A, B, 1.0);
        grafo.addEdge(B, A, 1.0);
        grafo.addEdge(B, D, 2.0);
        grafo.addEdge(D, B, 2.0);
        grafo.addEdge(D, C, 3.0);
        grafo.addEdge(C, D, 3.0);
        grafo.addEdge(C, A, 4.0);
        grafo.addEdge(A, C, 4.0);
        
        // Agregar aristas de mayor peso
        grafo.addEdge(A, D, 10.0);
        grafo.addEdge(D, A, 10.0);
        grafo.addEdge(B, C, 10.0);
        grafo.addEdge(C, B, 10.0);

        // Ejecutar TSP voraz
        Solucion result = TSPGreedyEdges.tspGreedyEdges(grafo, A);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null para un grafo válido");
        
        // El algoritmo voraz debería seleccionar las aristas con menor peso (A-B-D-C-A)
        assertEquals(10.0, result.getCoste(), "El coste debe ser 10.0 (1.0 + 2.0 + 3.0 + 4.0)");
        
        List<Vertex> path = result.getRecorrido();
        assertEquals(5, path.size(), "El camino debe tener 5 elementos");
        assertEquals(A, path.get(0), "El recorrido debe comenzar en A");
        assertEquals(A, path.get(path.size() - 1), "El recorrido debe terminar en A");
    }
}