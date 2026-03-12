package ual.eda2.practica03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TSPGreedyEdges {

	// TSP GREEDY EDGES
	/**
	 * Resuelve el problema del viajero (TSP) utilizando un enfoque voraz basado 
	 * en la selección de aristas.
	 * 
	 * @param g El grafo completo representado como una lista de adyacencia.
	 * @param startVertex El vértice de inicio desde el cual se inicia la búsqueda.
	 * @return La solución encontrada para el TSP utilizando el algoritmo voraz.
	 */
	public static Solucion tspGreedyEdges(Grafo g, Vertex startVertex) {
	    // Paso 1: Verifica si el vértice inicial es válido y está en el grafo
	    if (startVertex == null || !g.getVertices().contains(startVertex)) return null;

	    // Paso 2: Obtener y ordenar todas las aristas del grafo
	    List<Edge> edges = new ArrayList<>();
	    for (Vertex v : g.getVertices()) {
	        for (Vertex u : g.getVecinos(v)) {
	            if (v.compareTo(u) < 0) { // Evitar duplicar aristas
	                edges.add(new Edge(v, u, g.peso(v, u)));
	            }
	        }
	    }

	    // Ordenar las aristas por peso
	    edges.sort(null);

	    // Paso 3: Inicializar estructuras para los grados y componentes conectados
	    Map<Vertex, Integer> degree = new HashMap<>();
	    Map<Vertex, Integer> component = new HashMap<>();
	    int compId = 0;
	    for (Vertex v : g.getVertices()) {
	        component.put(v, compId++);
	        degree.put(v, 0);
	    }

	    Map<Vertex, List<Vertex>> selectedEdges = new HashMap<>();
	    int selectedEdgesCount = 0;

	    // Paso 4: Seleccionar las aristas válidas para el ciclo
	    for (Edge e : edges) {
	    	Vertex u = e.getSource();
	    	Vertex v = e.getDestination();

	        // Evitar aristas que generen vértices con más de 2 conexiones
	        if (degree.get(u) >= 2 || degree.get(v) >= 2) continue;

	        boolean isLastEdge = selectedEdgesCount == g.size() - 1;
	        
	        // Evitar formar ciclos prematuros
	        if (component.get(u).equals(component.get(v)) && !isLastEdge) continue;

	        // Añadir la arista al camino
	        // Así queda mas PRO .....pero mejor que no: selectedEdges.computeIfAbsent(u, _ -> new ArrayList<>()).add(v);
	        List<Vertex> list = selectedEdges.get(u);
	        if (list == null) selectedEdges.put(u, list = new ArrayList<>());
	        list.add(v);
	        //Igual que esta: selectedEdges.computeIfAbsent(v, _ -> new ArrayList<>()).add(u);
	        list = selectedEdges.get(v);
	        if (list == null) selectedEdges.put(v, list = new ArrayList<>());
	        list.add(u);

	        selectedEdgesCount++;
	        degree.put(u, degree.get(u) + 1);
	        degree.put(v, degree.get(v) + 1);

	        // Unir los componentes de los vértices
	        int oldComp = component.get(v);
	        int newComp = component.get(u);
	        for (Vertex w : component.keySet()) {
	            if (component.get(w) == oldComp) {
	                component.put(w, newComp);
	            }
	        }

	        // Si es la última arista, terminamos
	        if (isLastEdge) break;
	    }

	    // Verifica si se encontraron suficientes aristas para formar el ciclo
	    if (selectedEdgesCount != g.size()) throw new RuntimeException("No existe solución válida");

	    // Paso 6: Recorrer el camino desde el vértice inicial
	    List<Vertex> path = new ArrayList<>();
	    Set<Vertex> visited = new HashSet<>();
	    double totalWeight = 0.0;
	    Vertex current = startVertex;
	    path.add(current);
	    visited.add(current);

	    // Mientras el camino no esté completo, seguimos agregando vértices
	    while (path.size() < g.size()) {
	        for (Vertex neighbor : selectedEdges.get(current)) {
	            if (!visited.contains(neighbor)) {
	                path.add(neighbor);
	                totalWeight += g.peso(current, neighbor);
	                visited.add(neighbor);
	                current = neighbor;
	                break;
	            }
	        }
	    }

	    // Cierra el ciclo agregando el vértice inicial al final del camino
	    path.add(startVertex);
	    totalWeight += g.peso(current, startVertex);

	    // Devuelve la solución con el recorrido y el peso total
	    return new Solucion(path, totalWeight);
	}
}
