package ual.eda2.practica03;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TSPBruteForceTest {

    @Test
    public void testTspRecursive_NullGraph() {
        // Ejecutar TSP recursivo con grafo nulo
        Solucion result = TSPBruteForce.TSPFuerzaBruta(null, new Vertex("A"));

        // Comprobaciones
        assertNull(result, "El resultado debe ser null para un grafo nulo");
    }
    @Test
    public void testTspRecursive_EmptyGraph() {
        // Crear un grafo vacío
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");

        // Ejecutar TSP recursivo
        Solucion result = TSPBruteForce.TSPFuerzaBruta(grafo, A);

        // Comprobaciones
        assertNull(result, "El resultado debe ser null para un grafo vacío");
    }

    @Test
    public void testTspRecursive_InvalidStartVertex() {
        // Crear un grafo simple
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        grafo.addVertex(A);
        
        // Vértice que no existe en el grafo
        Vertex B = new Vertex("B");

        // Ejecutar TSP recursivo
        Solucion result = TSPBruteForce.TSPFuerzaBruta(grafo, B);

        // Comprobaciones
        assertNull(result, "El resultado debe ser null cuando el vértice inicial no está en el grafo");
    }

    @Test
    public void testTspRecursive_SingleVertex() {
        // Crear un grafo con un único vértice
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        grafo.addVertex(A);

        // Ejecutar TSP recursivo
        Solucion result = TSPBruteForce.TSPFuerzaBruta(grafo, A);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null para un grafo con un solo vértice");
        List<Vertex> path = result.getRecorrido();
        assertEquals(2, path.size(), "El camino debe incluir el nodo inicial y el regreso al mismo");
        assertEquals(A, path.get(0), "El primer vértice debe ser A");
        assertEquals(A, path.get(1), "El segundo vértice debe ser A (regreso)");
        assertEquals(0.0, result.getCoste(), "La distancia debe ser 0 para un solo vértice");
    }

    @Test
    public void testTspRecursive_TwoVertices() {
        // Crear un grafo con dos vértices conectados
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        grafo.addVertex(A);
        grafo.addVertex(B);
        grafo.addEdge(A, B, 10.0);
        grafo.addEdge(B, A, 10.0);

        // Ejecutar TSP recursivo
        Solucion result = TSPBruteForce.TSPFuerzaBruta(grafo, A);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null para un grafo válido");
        List<Vertex> path = result.getRecorrido();
        assertEquals(3, path.size(), "El camino debe tener 3 elementos (A, B, A)");
        assertEquals(A, path.get(0), "El recorrido debe comenzar en A");
        assertEquals(B, path.get(1), "El segundo vértice debe ser B");
        assertEquals(A, path.get(2), "El recorrido debe terminar en A");
        assertEquals(20.0, result.getCoste(), "El coste debe ser 20.0 (10.0 * 2)");
    }

    @Test
    public void testTspRecursive_CompleteGraph() {
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

        // Ejecutar TSP recursivo
        Solucion result = TSPBruteForce.TSPFuerzaBruta(grafo, A);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null para un grafo completamente conectado");
        assertEquals(45.0, result.getCoste(), "La distancia más corta debe ser 45.0 (10 + 20 + 15)");
        List<Vertex> path = result.getRecorrido();
        assertEquals(4, path.size(), "El camino debe tener 4 elementos");
        assertEquals(A, path.get(0), "El recorrido debe comenzar en A");
        assertEquals(A, path.get(path.size() - 1), "El recorrido debe terminar en A");
    }

    @Test
    public void testTspRecursive_LargeGraph() {
        // Crear un grafo completamente conectado con 4 nodos
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");
        Vertex D = new Vertex("D");

        grafo.addVertex(A);
        grafo.addVertex(B);
        grafo.addVertex(C);
        grafo.addVertex(D);

        grafo.addEdge(A, B, 10.0);
        grafo.addEdge(A, C, 15.0);
        grafo.addEdge(A, D, 20.0);
        grafo.addEdge(B, C, 35.0);
        grafo.addEdge(B, D, 25.0);
        grafo.addEdge(C, D, 30.0);

        grafo.addEdge(B, A, 10.0);
        grafo.addEdge(C, A, 15.0);
        grafo.addEdge(D, A, 20.0);
        grafo.addEdge(C, B, 35.0);
        grafo.addEdge(D, B, 25.0);
        grafo.addEdge(D, C, 30.0);

        // Ejecutar TSP recursivo
        Solucion result = TSPBruteForce.TSPFuerzaBruta(grafo, A);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null para un grafo válido");
        assertEquals(80.0, result.getCoste(), "La distancia más corta debe ser 80.0");
        List<Vertex> path = result.getRecorrido();
        assertEquals(5, path.size(), "El camino debe tener 5 elementos");
        assertEquals(A, path.get(0), "El recorrido debe comenzar en A");
        assertEquals(A, path.get(path.size() - 1), "El recorrido debe terminar en A");
    }

    @Test
    public void testTspRecursive_DifferentStartingPoints() {
        // Crear un grafo con 3 nodos
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

        // Ejecutar TSP recursivo con diferentes vértices iniciales
        Solucion resultA = TSPBruteForce.TSPFuerzaBruta(grafo, A);
        Solucion resultB = TSPBruteForce.TSPFuerzaBruta(grafo, B);
        Solucion resultC = TSPBruteForce.TSPFuerzaBruta(grafo, C);

        // Comprobaciones
        assertEquals(45.0, resultA.getCoste(), "El coste debe ser el mismo independientemente del vértice inicial");
        assertEquals(45.0, resultB.getCoste(), "El coste debe ser el mismo independientemente del vértice inicial");
        assertEquals(45.0, resultC.getCoste(), "El coste debe ser el mismo independientemente del vértice inicial");
        
        assertEquals(A, resultA.getRecorrido().get(0), "El recorrido A debe comenzar en A");
        assertEquals(A, resultA.getRecorrido().get(resultA.getRecorrido().size() - 1), "El recorrido A debe terminar en A");
        
        assertEquals(B, resultB.getRecorrido().get(0), "El recorrido B debe comenzar en B");
        assertEquals(B, resultB.getRecorrido().get(resultB.getRecorrido().size() - 1), "El recorrido B debe terminar en B");
        
        assertEquals(C, resultC.getRecorrido().get(0), "El recorrido C debe comenzar en C");
        assertEquals(C, resultC.getRecorrido().get(resultC.getRecorrido().size() - 1), "El recorrido C debe terminar en C");
    }
}