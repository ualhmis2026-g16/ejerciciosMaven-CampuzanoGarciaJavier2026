package ual.eda2.practica02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Clase de prueba para la clase Graph.
 * Contiene varias pruebas unitarias para verificar el comportamiento de las operaciones 
 * de un grafo, incluyendo adición de aristas, verificación de adyacencia y cálculo de pesos.
 */
class GraphTest {

    /**
     * Prueba el método addEdge y getVertices.
     * Verifica que los vértices se añaden correctamente al grafo después de agregar aristas.
     */
    @Test
    void testAddEdgeAndGetVertices() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        // Añadir aristas al grafo
        graph.addEdge(a, b, 5.0);
        graph.addEdge(a, c, 10.0);

        // Verificar que los vértices se añaden correctamente
        Collection<Vertex> vertices = graph.getVertices();
        assertTrue(vertices.contains(a), "El vértice A debería estar en el grafo");
        assertTrue(vertices.contains(b), "El vértice B debería estar en el grafo");
        assertTrue(vertices.contains(c), "El vértice C debería estar en el grafo");
        assertEquals(3, vertices.size(), "El número de vértices debería ser 3");
    }

    /**
     * Prueba el método adyacentes.
     * Verifica que los vértices conectados mediante aristas se detectan como adyacentes.
     */
    @Test
    void testAdyacentes() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        // Añadir aristas al grafo
        graph.addEdge(a, b, 5.0);
        graph.addEdge(a, c, 10.0);

        // Verificar adyacencia
        assertTrue(graph.adyacentes(a, b), "El vértice A debería ser adyacente a B");
        assertTrue(graph.adyacentes(a, c), "El vértice A debería ser adyacente a C");
        assertFalse(graph.adyacentes(b, c), "El vértice B no debería ser adyacente a C");
    }

    /**
     * Prueba el método peso.
     * Verifica que los pesos de las aristas entre los vértices se calculan correctamente.
     */
    @Test
    void testPeso() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        // Añadir aristas al grafo
        graph.addEdge(a, b, 5.0);
        graph.addEdge(a, c, 10.0);

        // Verificar pesos
        assertEquals(5.0, graph.peso(a, b), "El peso de la arista entre A y B debería ser 5.0");
        assertEquals(10.0, graph.peso(a, c), "El peso de la arista entre A y C debería ser 10.0");
    }

    /**
     * Prueba la ausencia de adyacencia entre vértices no conectados.
     * Verifica que los vértices no conectados no se detectan como adyacentes.
     */
    @Test
    void testNoAdyacentes() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");

        // Añadir una arista al grafo
        graph.addEdge(a, b, 5.0);

        // Verificar que los vértices no conectados no son adyacentes
        Vertex c = new Vertex("C");
        assertFalse(graph.adyacentes(a, c), "El vértice A no debería ser adyacente a C");
        assertFalse(graph.adyacentes(b, c), "El vértice B no debería ser adyacente a C");
    }

    /**
     * Prueba el método peso para aristas inexistentes.
     * Verifica que el peso de aristas inexistentes retorne Double.POSITIVE_INFINITY.
     */
    @Test
    void testPesoInexistente() {
        Graph graph = new Graph();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        // Añadir una arista al grafo
        graph.addEdge(a, b, 5.0);

        // Verificar que el peso de aristas inexistentes retorne Double.POSITIVE_INFINITY
        assertEquals(Double.POSITIVE_INFINITY, graph.peso(a, c), "El peso de una arista inexistente debería ser Double.POSITIVE_INFINITY");
    }
}