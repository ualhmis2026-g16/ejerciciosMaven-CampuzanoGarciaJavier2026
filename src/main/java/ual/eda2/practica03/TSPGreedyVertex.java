package ual.eda2.practica03;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TSPGreedyVertex{
	/**
	 * Resuelve el problema del viajero (TSP) utilizando un enfoque voraz basado 
	 * en la selección del vecino más cercano.
	 * 
	 * @param g El grafo completo representado como una lista de adyacencia.
	 * @param startVertex El vértice de inicio desde el cual se inicia la búsqueda.
	 * @return La solución encontrada para el TSP utilizando el algoritmo voraz.
	 */
	public static Solucion tspGreedyVertex(Grafo g, Vertex startVertex) {
		// Verifica que el vértice inicial sea válido y esté presente en el grafo
		if(startVertex == null || !g.getVertices().contains(startVertex)) return null;

		// Conjunto para almacenar los vértices visitados
		Set<Vertex> visitados = new HashSet<>();
		
		// Lista para almacenar el camino (recorrido)
		List<Vertex> path = new LinkedList<>();
		
		// Variable para almacenar el peso total del recorrido
		Double totalWeight = 0.0;
		
		// El vértice actual es el vértice inicial
		Vertex actual = startVertex;
		visitados.add(actual);
		path.add(actual);
		Double w = null;

		// Mientras haya vértices por visitar
		while (visitados.size() < g.size()) {
			// Encuentra el vecino más cercano que aún no ha sido visitado
			Vertex siguiente = vecinoMasCercano(g, visitados, actual);
			
			// Añade el vecino más cercano al recorrido
			path.add(siguiente);
			
			// Obtiene el peso de la arista entre el vértice actual y el siguiente
			w = g.peso(actual, siguiente);
			totalWeight += w;
			
			// Marca el siguiente vértice como visitado
			visitados.add(siguiente);
			
			// El siguiente vértice pasa a ser el actual
			actual = siguiente;
		}
		
		// Añade el vértice inicial al final para formar un ciclo
		path.add(startVertex);
		
		// Obtiene el peso de la arista que conecta el último vértice con el inicial
		w = g.peso(actual, startVertex);
		if(w == null) throw new RuntimeException("No existe solucion");
		
		// Suma el peso de la arista final al total
		totalWeight += w;
		
		// Devuelve la solución con el recorrido y el peso total
		return new Solucion(path, totalWeight);
	}

	/**
	 * Busca el vecino más cercano a un vértice actual que no ha sido visitado.
	 * 
	 * @param g El grafo en el que se busca el vecino más cercano.
	 * @param visited Conjunto de vértices ya visitados.
	 * @param actual El vértice actual desde el cual se busca el vecino más cercano.
	 * @return El vecino más cercano al vértice actual que no ha sido visitado.
	 */
	private static Vertex vecinoMasCercano(Grafo g, Set<Vertex> visited, Vertex actual) {
		Double min = Double.MAX_VALUE;  // Inicializa el peso mínimo con un valor muy grande
		Vertex mejorVecino = null;  // Inicializa el mejor vecino como nulo

		// Recorre los vecinos del vértice actual
		for (Vertex v : g.getVecinos(actual)) {
			// Si el vecino aún no ha sido visitado
			if (!visited.contains(v)) {
				// Obtiene el peso de la arista entre el vértice actual y el vecino
				Double w = g.peso(actual, v);
				// Si el peso es menor al mínimo actual, actualiza el vecino más cercano
				if(w < min) {
					min = w;
					mejorVecino = v;
				}
			}
		}

		// Retorna el vecino más cercano
		return mejorVecino;
	}
}
