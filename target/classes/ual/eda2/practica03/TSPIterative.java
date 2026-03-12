package ual.eda2.practica03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSPIterative {

	// TSP ITERATIVE
		/**
		 * Resuelve el problema del viajero (TSP) utilizando un enfoque iterativo 
		 * con programación dinámica.
		 * 
		 * @param g El Grafo completo representado como una matriz de adyacencia.
		 * @param startVertex El vértice de inicio desde el cual se inicia la búsqueda.
		 * @return La solución óptima encontrada para el TSP.
		 */
		public static Solucion tspIterative(Grafo g, Vertex startVertex) {
			Solucion sol = null;
			
			//Contempla el caso de 1 solo vértice para devolver una lista con ese elemento.
			if (g.getVertices().size() == 1) {
			    Vertex v = g.getVertices().iterator().next();
			    List<Vertex> singlePath = new ArrayList<>();
			    singlePath.add(v);
			    singlePath.add(v);
			    return new Solucion(singlePath, 0.0);
			}
			
			// Inicializa el coste mínimo de la ruta
			double minTourCost = Double.POSITIVE_INFINITY;
			
			// Lista que almacenará el recorrido final
			List<Integer> tour = new ArrayList<>();
			
			// Obtiene el índice del nodo inicial en el Grafo
			int start = g.getVertexIndex(startVertex);

			// Convierte el Grafo a una matriz de distancias
			double[][] distance = g.mapaAMatriz();
			
			// Número de nodos en el Grafo
			int N = distance.length;
			
			// Estado final, cuando todos los nodos han sido visitados
			final int END_STATE = (1 << N) - 1;
			
			// Tabla para memorización de subproblemas
			Double[][] memo = new Double[N][1 << N];

			// Inicializa la tabla memo con las distancias directas desde el nodo inicial
			for (int end = 0; end < N; end++) {
				if (end == start) continue;
				memo[end][(1 << start) | (1 << end)] = distance[start][end];
			}

			// Iteración sobre todos los subconjuntos de nodos de tamaño r, desde 3 hasta N
			for (int r = 3; r <= N; r++) {
				// Itera sobre todas las combinaciones de tamaño r de nodos
				for (int subset : combinations(r, N)) {
					// Asegúrate de que el nodo inicial esté en el subconjunto
					if (notIn(start, subset)) continue;

					// Itera sobre todos los nodos posibles como siguiente nodo
					for (int next = 0; next < N; next++) {
						// Si el nodo es el nodo inicial o no está en el subconjunto, saltamos
						if (next == start || notIn(next, subset)) continue;

						// Calcula el nuevo subconjunto sin el nodo 'next'
						// usa el operador XOR (^) para "remover" el bit correspondiente al nodo next dentro del subconjunto (subset).
						int subsetWithoutNext = subset ^ (1 << next);
						
						// Variable para almacenar el coste mínimo encontrado
						double minDist = Double.POSITIVE_INFINITY;
						
						// Busca el nodo anterior en el subconjunto que minimiza la distancia
						for (int end = 0; end < N; end++) {
							// Si el nodo 'end' es inválido, lo saltamos
							if (end == start || end == next || notIn(end, subset)) continue;

							// Calcula la distancia entre el nodo 'end' y 'next'
							double newDistance = memo[end][subsetWithoutNext] + distance[end][next];
							// Actualiza el coste mínimo si se encuentra una mejor ruta
							if (newDistance < minDist) {
								minDist = newDistance;
							}
						}

						// Guarda el coste mínimo en la tabla de memoización
						memo[next][subset] = minDist;
					}
				}
			}

			// Conecta la ruta de vuelta al nodo inicial y minimiza el coste
			for (int i = 0; i < N; i++) {
				if (i == start) continue;
				double tourCost = memo[i][END_STATE] + distance[i][start];
				if (tourCost < minTourCost) {
					minTourCost = tourCost;
				}
			}

			// Reconstrucción del recorrido mínimo desde la tabla de memoización
			int lastIndex = start;
			int state = END_STATE;
			tour.add(start);

			// Se reconstruye el recorrido nodo por nodo
			for (int i = 1; i < N; i++) {
				int index = -1;
				// Busca el siguiente nodo que minimiza el coste
				for (int j = 0; j < N; j++) {
					if (j == start || notIn(j, state)) continue;
					if (index == -1) index = j;
					double prevDist = memo[index][state] + distance[index][lastIndex];
					double newDist  = memo[j][state] + distance[j][lastIndex];
					if (newDist < prevDist) {
						index = j;
					}
				}

				// Agrega el siguiente nodo al recorrido
				tour.add(index);
				// Actualiza el estado al quitar el nodo de la lista de visitados
				state = state ^ (1 << index);
				// Actualiza el índice del último nodo visitado
				lastIndex = index;
			}

			// Agrega el nodo inicial al final del recorrido
			tour.add(start);

			// Invierte el recorrido para obtenerlo en el orden correcto (de inicio a fin)
			//Collections.reverse(tour);
			
			// Convierte los índices de los nodos a vértices del Grafo
			List<Vertex> vertexes = g.obtenerListaVertices(tour.toArray(new Integer[tour.size()]));
			
			double tourCost = 0;
			for(int i=0; i<vertexes.size()-1; i++) {
				tourCost+= g.peso(vertexes.get(i), vertexes.get(i+1));
			}
			// Crea la solución con el recorrido y el coste mínimo
			sol = new Solucion(vertexes, tourCost);
			return sol;
		}
		

		/**
		 * Verifica si un elemento no está en un subconjunto representado por un entero.
		 * 
		 * @param elem El elemento a verificar.
		 * @param subset El subconjunto representado como un entero.
		 * @return true si el elemento no está en el subconjunto, false en caso contrario.
		 */
		private static boolean notIn(int elem, int subset) {
			// Verifica si el elemento 'elem' no está en el subconjunto (usando bits)
			return ((1 << elem) & subset) == 0;
		}

		/**
		 * Genera todas las combinaciones de tamaño r de un conjunto de n elementos.
		 * 
		 * @param r El tamaño de las combinaciones a generar.
		 * @param n El número total de elementos en el conjunto.
		 * @return Una lista de enteros representando las combinaciones generadas.
		 */
		private static List<Integer> combinations(int r, int n) {
			List<Integer> subsets = new ArrayList<>();
			combinations(0, 0, r, n, subsets);
			return subsets;
		}

		/**
		 * Genera combinaciones de tamaño r a partir de un conjunto de n elementos.
		 * 
		 * @param set El conjunto actual representado como un entero.
		 * @param at El índice actual en el conjunto.
		 * @param r El tamaño de las combinaciones a generar.
		 * @param n El número total de elementos en el conjunto.
		 * @param subsets La lista donde se almacenan las combinaciones generadas.
		 */
		private static void combinations(int set, int at, int r, int n, List<Integer> subsets) {
			// Verifica si hay suficientes elementos restantes para elegir
			int elementsLeftToPick = n - at;
			if (elementsLeftToPick < r) return;

			// Si se han seleccionado 'r' elementos, guarda el subconjunto
			if (r == 0) {
				subsets.add(set);
			} else {
				for (int i = at; i < n; i++) {
					// Incluye el elemento 'i' en el subconjunto
					set |= 1 << i;
					// Se llama recursivamente para incluir más elementos
					combinations(set, i + 1, r - 1, n, subsets);
					// Se deshace la inclusión para explorar el caso sin él
					set &= ~(1 << i);
				}
			}
		}
		
		
}
