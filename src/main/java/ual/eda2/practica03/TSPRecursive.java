package ual.eda2.practica03;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el algoritmo del TSP (Viajante de Comercio) utilizando programación
 * dinámica y memoización de forma recursiva. El algoritmo evalúa todos los posibles nodos de inicio
 * y regresa el camino óptimo y el costo mínimo.
 */
public class TSPRecursive {

    private static int N; // Número de nodos
    private static int FINISHED_STATE; // Estado final en el que todos los nodos han sido visitados

    private static double[][] distance; // Matriz de distancias entre nodos
    private static List<Vertex> caminoOptimo = new ArrayList<>(); // Lista que contendrá la ruta óptima
    private static List<Vertex> vertices; // Lista de vértices del grafo

    /**
     * Resuelve el problema del TSP para el grafo dado utilizando programación dinámica recursiva,
     * comenzando desde el vértice especificado.
     *
     * @param grafo El grafo en el cual se ejecuta el algoritmo.
     * @param startVertex El vértice de inicio para el recorrido.
     * @return Un objeto Solucion que contiene la ruta óptima y su costo total.
     */
    public static Solucion recursiveTSP(Grafo grafo, Vertex startVertex) {
        // Validar el grafo y el vértice inicial
        if (grafo == null || grafo.size() == 0 || startVertex == null || !grafo.getVertices().contains(startVertex)) {
            return null; // Grafo vacío o vértice inválido
        }

        // Caso especial: un solo nodo en el grafo
        if (grafo.size() == 1) {
            List<Vertex> ruta = new ArrayList<>(grafo.getVertices());
            ruta.add(ruta.get(0)); // Cerrar el circuito
            return new Solucion(ruta, 0.0);
        }

        vertices = new ArrayList<>(grafo.getVertices());
        N = grafo.size();

        distance = convertir(grafo);
        FINISHED_STATE = (1 << N) - 1;

        // Encontrar el índice del vértice de inicio
        int sourceIndex = vertices.indexOf(startVertex);
        if (sourceIndex == -1) {
            return null; // Vértice no encontrado en el grafo
        }

        // Inicializar estructuras para memoización y reconstrucción de camino
        Double[][] memo = new Double[N][1 << N];
        Integer[][] prev = new Integer[N][1 << N];

        // Calcular el camino óptimo desde el vértice de inicio especificado
        double caminoOptimalCost = tsp(sourceIndex, 1 << sourceIndex, sourceIndex, memo, prev);
        
        // Reconstruir el camino óptimo
        caminoOptimo.clear();
        reconstruirCamino(prev, sourceIndex);
        
        // Devolver la solución con el camino y su costo
        return new Solucion(new ArrayList<>(caminoOptimo), caminoOptimalCost);
    }

    /**
     * Método recursivo que calcula el costo mínimo para completar el circuito
     * desde el nodo actual usando programación dinámica y memoización.
     *
     * @param i     El índice del nodo actual.
     * @param state Bitmask que indica los nodos visitados.
     * @param start Índice del nodo de inicio.
     * @param memo  Tabla para memoización.
     * @param prev  Tabla para la reconstrucción del camino óptimo.
     * @return Costo mínimo acumulado desde el nodo actual hasta regresar al nodo de inicio.
     */
    private static double tsp(int i, int state, int start, Double[][] memo, Integer[][] prev) {
        if (state == FINISHED_STATE) {
            return distance[i][start]; // Caso base: retornar al nodo inicial.
        }

        if (memo[i][state] != null) {
            return memo[i][state];
        }

        double minCost = Double.POSITIVE_INFINITY;
        int index = -1;

        // Iterar por todos los nodos no visitados.
        for (int next = 0; next < N; next++) {
            if ((state & (1 << next)) != 0) continue; // Saltar si el nodo ya fue visitado.
            
            int nextState = state | (1 << next);
            double newCost = distance[i][next] + tsp(next, nextState, start, memo, prev);
            if (newCost < minCost) {
                minCost = newCost;
                index = next;
            }
        }

        prev[i][state] = index; // Almacenar el siguiente nodo óptimo para reconstruir el camino.
        return memo[i][state] = minCost;
    }

    /**
     * Reconstruye el camino óptimo a partir de la tabla 'prev' luego de calcular los costos.
     *
     * @param prev   Tabla con la información del siguiente nodo para cada subproblema.
     * @param source Índice del nodo de inicio.
     */
    private static void reconstruirCamino(Integer[][] prev, int source) {
        int state = 1 << source; // Estado inicial
        int index = source;

        // Limpiar la ruta y agregar el nodo de inicio.
        caminoOptimo.clear();
        caminoOptimo.add(vertices.get(index));

        // Reconstruir el camino usando la tabla 'prev'
        while (true) {
            Integer nextIndex = prev[index][state];
            if (nextIndex == null || nextIndex == -1) {
                break; // Terminar si no hay siguiente nodo válido.
            }

            caminoOptimo.add(vertices.get(nextIndex));
            state |= (1 << nextIndex); // Actualizar el estado de visitas.
            index = nextIndex; // Pasar al siguiente nodo.
        }

        // Asegurar que el camino cierra el circuito regresando al nodo de inicio.
        if (caminoOptimo.size() == 1 || !caminoOptimo.get(caminoOptimo.size() - 1).equals(vertices.get(source))) {
            caminoOptimo.add(vertices.get(source));
        }
    }

    /**
     * Convierte el grafo en una matriz de distancias.
     *
     * @param grafo El grafo a convertir.
     * @return Una matriz de distancias donde cada elemento representa el peso entre dos nodos.
     */
    private static double[][] convertir(Grafo grafo) {
        int n = grafo.size();
        double[][] matriz = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matriz[i][j] = 0; // La distancia de un nodo a sí mismo es 0.
                } else {
                    Vertex v1 = vertices.get(i);
                    Vertex v2 = vertices.get(j);
                    matriz[i][j] = grafo.adyacentes(v1, v2) ? grafo.peso(v1, v2) : Double.POSITIVE_INFINITY;
                }
            }
        }

        return matriz;
    }
}