package ual.eda2.practica02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

public class Graph {
    // Map to store vertices and their adjacent vertices with weights
    private Map<Vertex, Map<Vertex, Double>> adjacencyMap;
    
    /**
     * Constructor por defecto
     */
    public Graph() {
        this.adjacencyMap = new HashMap<>();
    }
    
    /**
     * Metodo que anade un vertice al grafo
     * @param v
     */
    public void addVertex(Vertex v) {
        if (!adjacencyMap.containsKey(v)) {
            adjacencyMap.put(v, new HashMap<>());
        }
    }
    
    /**
     * Metodo que anade una arista entre dos vertices
     * @param source
     * @param destination
     * @param weight
     */
    public void addEdge(Vertex source, Vertex destination, double weight) {
        // Add vertices if they don't exist
        addVertex(source);
        addVertex(destination);
        
        // Add the edge with weight
        adjacencyMap.get(source).put(destination, weight);
    }
    
    /**
     * Metodo que devuelve todos los vertices del grafo
     * @return Collection<Vertex>
     */
    public Collection<Vertex> getVertices() {
        return adjacencyMap.keySet();
    }
    
    /**
     * Metodo que verifica si dos vertices son adyacentes
     * @param Vertex v1
     * @param Vertex v2
     * @return boolean adyacentes
     */
    public boolean adyacentes(Vertex v1, Vertex v2) {
        if (!adjacencyMap.containsKey(v1)) {
            return false;
        }
        return adjacencyMap.get(v1).containsKey(v2);
    }
    
    /**
     * Metodo que devuelve el peso entre dos vertices
     * @param Vertex v1
     * @param Vertex v2
     * @return double peso
     */
    public double peso(Vertex v1, Vertex v2) {
        if (!adyacentes(v1, v2)) {
            return Double.POSITIVE_INFINITY;
        }
        return adjacencyMap.get(v1).get(v2);
    }
    
    /**
     * Metodo que devuelve el peso entre dos vertices
     * @param Vertex v1
     * @param Vertex v2
     * @return double
     */
    public double weight(Vertex v1, Vertex v2) {
        return peso(v1, v2);
    }
    
    /**
     * Metodo para imprimir el grafo.
     */
    public void printGraph() {
        for (Vertex v : adjacencyMap.keySet()) {
            System.out.print("Vertex " + v + " connects to: ");
            for (Map.Entry<Vertex, Double> neighbor : adjacencyMap.get(v).entrySet()) {
                System.out.print(neighbor.getKey() + " (weight: " + neighbor.getValue() + ") ");
            }
            System.out.println();
        }
    }
}