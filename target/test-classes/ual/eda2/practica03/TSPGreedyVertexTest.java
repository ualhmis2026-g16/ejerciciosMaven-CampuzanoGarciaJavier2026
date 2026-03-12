package ual.eda2.practica03;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TSPGreedyVertexTest {

    @Test
    public void testTspGreedyVertex_NullStartVertex() {
        // Crear un grafo simple
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        grafo.addVertex(A);

        // Ejecutar con vértice inicial nulo
        Solucion result = TSPGreedyVertex.tspGreedyVertex(grafo, null);

        // Comprobar que devuelve null
        assertNull(result, "Debería devolver null cuando el vértice inicial es nulo");
    }

    @Test
    public void testTspGreedyVertex_StartVertexNotInGraph() {
        // Crear un grafo simple
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        grafo.addVertex(A);
        
        // Vértice que no existe en el grafo
        Vertex B = new Vertex("B");

        // Ejecutar con vértice inicial que no está en el grafo
        Solucion result = TSPGreedyVertex.tspGreedyVertex(grafo, B);

        // Comprobar que devuelve null
        assertNull(result, "Debería devolver null cuando el vértice inicial no está en el grafo");
    }

    @Test
    public void testTspGreedyVertex_TwoVertices() {
        // Crear un grafo con dos vértices conectados
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        grafo.addVertex(A);
        grafo.addVertex(B);
        grafo.addEdge(A, B, 10.0);
        grafo.addEdge(B, A, 10.0);

        // Ejecutar TSP voraz
        Solucion result = TSPGreedyVertex.tspGreedyVertex(grafo, A);

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
    public void testTspGreedyVertex_SmallCompleteGraph() {
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
        Solucion result = TSPGreedyVertex.tspGreedyVertex(grafo, A);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null para un grafo completo");
        List<Vertex> path = result.getRecorrido();
        assertEquals(4, path.size(), "El camino debe tener 4 elementos");
        assertEquals(A, path.get(0), "El recorrido debe comenzar en A");
        assertEquals(B, path.get(1), "El segundo vértice debe ser B (el más cercano a A)");
        assertEquals(C, path.get(2), "El tercer vértice debe ser C (único restante)");
        assertEquals(A, path.get(3), "El recorrido debe terminar en A");
        assertEquals(45.0, result.getCoste(), "El coste debe ser 45.0 (10 + 20 + 15)");
    }

    @Test
    public void testTspGreedyVertex_LargerGraph() {
        // Crear un grafo con 4 nodos
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");
        Vertex D = new Vertex("D");

        grafo.addVertex(A);
        grafo.addVertex(B);
        grafo.addVertex(C);
        grafo.addVertex(D);

        // Definir las conexiones con pesos
        grafo.addEdge(A, B, 10.0);
        grafo.addEdge(A, C, 15.0);
        grafo.addEdge(A, D, 20.0);
        grafo.addEdge(B, A, 10.0);
        grafo.addEdge(B, C, 35.0);
        grafo.addEdge(B, D, 25.0);
        grafo.addEdge(C, A, 15.0);
        grafo.addEdge(C, B, 35.0);
        grafo.addEdge(C, D, 30.0);
        grafo.addEdge(D, A, 20.0);
        grafo.addEdge(D, B, 25.0);
        grafo.addEdge(D, C, 30.0);

        // Ejecutar TSP voraz desde el vértice A
        Solucion result = TSPGreedyVertex.tspGreedyVertex(grafo, A);

        // Comprobaciones - el algoritmo voraz seleccionará siempre el vecino más cercano
        // A→B→D→C→A
        assertNotNull(result, "El resultado no debe ser null para un grafo válido");
        List<Vertex> path = result.getRecorrido();
        assertEquals(5, path.size(), "El camino debe tener 5 elementos");
        assertEquals(A, path.get(0), "El recorrido debe comenzar en A");
        assertEquals(B, path.get(1), "El siguiente debe ser B (el más cercano a A)");
        assertEquals(D, path.get(2), "El siguiente debe ser D (el más cercano a B entre los no visitados)");
        assertEquals(C, path.get(3), "El siguiente debe ser C (único restante)");
        assertEquals(A, path.get(4), "El recorrido debe terminar en A");
        assertEquals(80.0, result.getCoste(), "El coste debe ser 80.0 (10 + 25 + 30 + 15)");
    }

    @Test
    public void testTspGreedyVertex_EqualWeights() {
        // Crear un grafo donde todos los pesos son iguales
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");

        grafo.addVertex(A);
        grafo.addVertex(B);
        grafo.addVertex(C);

        // Todas las aristas tienen el mismo peso
        grafo.addEdge(A, B, 5.0);
        grafo.addEdge(A, C, 5.0);
        grafo.addEdge(B, C, 5.0);
        grafo.addEdge(B, A, 5.0);
        grafo.addEdge(C, A, 5.0);
        grafo.addEdge(C, B, 5.0);

        // Ejecutar TSP voraz
        Solucion result = TSPGreedyVertex.tspGreedyVertex(grafo, A);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null para un grafo con pesos iguales");
        List<Vertex> path = result.getRecorrido();
        assertEquals(4, path.size(), "El camino debe tener 4 elementos");
        assertEquals(A, path.get(0), "El recorrido debe comenzar en A");
        assertEquals(A, path.get(path.size() - 1), "El recorrido debe terminar en A");
        assertEquals(15.0, result.getCoste(), "El coste debe ser 15.0 (5 * 3)");
    }

    @Test
    public void testTspGreedyVertex_DisconnectedGraph() {
        // Crear un grafo desconectado
        Grafo grafo = new Grafo();
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");

        grafo.addVertex(A);
        grafo.addVertex(B);
        grafo.addVertex(C);

        // Solo A y B están conectados
        grafo.addEdge(A, B, 10.0);
        grafo.addEdge(B, A, 10.0);
        // C no está conectado

        // Ejecutar TSP voraz
        try {
            TSPGreedyVertex.tspGreedyVertex(grafo, A);
            fail("Debería lanzar excepción para un grafo desconectado");
        } catch (RuntimeException e) {
            // Es correcto que lance una excepción al no encontrar vecino más cercano para C
            assertTrue(true, "Es esperado que lance excepción para un grafo desconectado");
        }
    }

    @Test
    public void testTspGreedyVertex_StartFromDifferentVertex() {
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

        // Ejecutar TSP voraz comenzando desde B
        Solucion result = TSPGreedyVertex.tspGreedyVertex(grafo, B);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null");
        List<Vertex> path = result.getRecorrido();
        assertEquals(4, path.size(), "El camino debe tener 4 elementos");
        assertEquals(B, path.get(0), "El recorrido debe comenzar en B");
        assertEquals(A, path.get(1), "El siguiente debe ser A (el más cercano a B)");
        assertEquals(C, path.get(2), "El siguiente debe ser C (único restante)");
        assertEquals(B, path.get(3), "El recorrido debe terminar en B");
        assertEquals(45.0, result.getCoste(), "El coste debe ser 45.0 (10 + 15 + 20)");
    }
}