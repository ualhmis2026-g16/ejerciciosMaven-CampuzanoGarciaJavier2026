package ual.eda2.practica03;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el algoritmo TSP mediante fuerza bruta usando programación dinámica.
 * Se utiliza memoización para evitar cálculos redundantes y se reconstruye el camino
 * óptimo basado en los nodos visitados.
 */
public class TSPBruteForce {

    /**
     * Resuelve el problema del TSP para el grafo dado utilizando una aproximación de fuerza bruta
     * con programación dinámica, partiendo desde el vértice especificado.
     *
     * @param g El grafo sobre el cual se ejecuta el algoritmo.
     * @param start El vértice desde el cual se inicia el recorrido.
     * @return Un objeto Solucion que contiene la ruta óptima y su costo total.
     */
    public static Solucion TSPFuerzaBruta(Grafo g, Vertex start) {
        // Validar el grafo y el vértice inicial
        if (g == null || g.size() == 0 || start == null || !g.getVertices().contains(start)) {
            return null; // Grafo vacío o vértice inicial inválido
        }

        // Caso especial: un solo nodo en el grafo
        if (g.size() == 1) {
            List<Vertex> ruta = new ArrayList<>(g.getVertices());
            ruta.add(ruta.get(0)); // Cerrar el circuito
            return new Solucion(ruta, 0.0);
        }

        // Convertir el grafo a una matriz de distancias
        double[][] matrizDistancias = convertir(g);
        int n = g.size();
        int FINISHED_STATE = (1 << n) - 1; // Estado donde todos los nodos han sido visitados
        ArrayList<Vertex> listaVertices = new ArrayList<>(g.getVertices());
        
        // Encontrar el índice del vértice inicial
        int nodoInicio = listaVertices.indexOf(start);
        if (nodoInicio == -1) {
            return null; // Vértice inicial no encontrado en el grafo
        }

        // Tablas para memoización y reconstrucción de ruta
        Double[][] memo = new Double[n][1 << n];
        Integer[][] prev = new Integer[n][1 << n];

        // Resolver el problema TSP desde el nodo de inicio especificado
        double costo = tsp(nodoInicio, 1 << nodoInicio, matrizDistancias, memo, prev, n, FINISHED_STATE, nodoInicio);

        // Reconstruir la ruta óptima
        ArrayList<Vertex> mejorRuta = reconstruirRuta(prev, listaVertices, n, nodoInicio);

        // Devolver el resultado con la ruta y su costo total
        return new Solucion(mejorRuta, costo);
    }

    /**
     * Método recursivo que resuelve el TSP para el nodo actual.
     *
     * @param nodoActual         El índice del nodo actual.
     * @param estadoVisitado     Bitmask que indica los nodos ya visitados.
     * @param matrizDistancias   Matriz de distancias del grafo.
     * @param memo               Tabla para memoización de subproblemas.
     * @param prev               Tabla para la reconstrucción del camino.
     * @param n                  Número total de nodos.
     * @param FINISHED_STATE     Estado que indica que todos los nodos han sido visitados.
     * @param start              Índice del nodo de inicio.
     * @return El costo mínimo acumulado desde el nodo actual hasta completar el circuito.
     */
    private static double tsp(int nodoActual, int estadoVisitado, double[][] matrizDistancias, 
                              Double[][] memo, Integer[][] prev, int n, int FINISHED_STATE, int start) {
        // Caso base: si todos los nodos han sido visitados, regresar al nodo de inicio ('start')
        if (estadoVisitado == FINISHED_STATE) {
            return matrizDistancias[nodoActual][start];
        }

        // Si el subproblema ya fue calculado, devolver el valor memoizado
        if (memo[nodoActual][estadoVisitado] != null) {
            return memo[nodoActual][estadoVisitado];
        }

        double mejorCostoLocal = Double.POSITIVE_INFINITY;
        int siguienteNodoOptimo = -1;

        // Explorar todos los nodos posibles que no hayan sido visitados
        for (int siguienteNodo = 0; siguienteNodo < n; siguienteNodo++) {
            // Si el nodo ya fue visitado, continuar
            if ((estadoVisitado & (1 << siguienteNodo)) != 0) continue;

            int nuevoEstado = estadoVisitado | (1 << siguienteNodo);
            double costo = matrizDistancias[nodoActual][siguienteNodo] + 
                           tsp(siguienteNodo, nuevoEstado, matrizDistancias, memo, prev, n, FINISHED_STATE, start);

            if (costo < mejorCostoLocal) {
                mejorCostoLocal = costo;
                siguienteNodoOptimo = siguienteNodo;
            }
        }

        // Almacenar el siguiente nodo óptimo para la reconstrucción del camino
        prev[nodoActual][estadoVisitado] = siguienteNodoOptimo;
        // Memoizar el costo mínimo obtenido para este estado
        memo[nodoActual][estadoVisitado] = mejorCostoLocal;
        return mejorCostoLocal;
    }

    /**
     * Reconstruye el camino óptimo a partir de la tabla 'prev'.
     *
     * @param prev          Tabla con los índices de nodos siguientes en cada subproblema.
     * @param listaVertices Lista de todos los vértices del grafo.
     * @param n             Número de nodos.
     * @param nodoInicio    Índice del nodo de inicio.
     * @return Un ArrayList que representa el camino óptimo, cerrando el circuito.
     */
    private static ArrayList<Vertex> reconstruirRuta(Integer[][] prev, ArrayList<Vertex> listaVertices, int n, int nodoInicio) {
        ArrayList<Vertex> ruta = new ArrayList<>();
        int estado = 1 << nodoInicio; // Estado inicial
        int nodoActual = nodoInicio;

        ruta.add(listaVertices.get(nodoActual));

        while (true) {
            Integer siguienteNodo = prev[nodoActual][estado];
            if (siguienteNodo == null || siguienteNodo == -1) {
                break;
            }
            ruta.add(listaVertices.get(siguienteNodo));
            estado |= (1 << siguienteNodo);
            nodoActual = siguienteNodo;
        }

        // Cerrar circuito si es necesario
        if (!ruta.get(ruta.size() - 1).equals(listaVertices.get(nodoInicio))) {
            ruta.add(listaVertices.get(nodoInicio));
        }

        return ruta;
    }

    /**
     * Convierte el grafo en una matriz de distancias.
     *
     * @param grafo El grafo a convertir.
     * @return Una matriz de distancias donde cada elemento representa el peso entre dos nodos.
     */
    public static double[][] convertir(Grafo grafo) {
        int n = grafo.size();
        double[][] matrizDistancias = new double[n][n];
        ArrayList<Vertex> listaVertices = new ArrayList<>(grafo.getVertices());

        // Inicializar la matriz con valores infinitos y diagonales en 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrizDistancias[i][j] = 0; // Misma ciudad
                } else {
                    matrizDistancias[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        // Rellenar la matriz con los pesos de las aristas
        for (int i = 0; i < n; i++) {
            Vertex origen = listaVertices.get(i);
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    Vertex destino = listaVertices.get(j);
                    if (grafo.adyacentes(origen, destino)) {
                        matrizDistancias[i][j] = grafo.peso(origen, destino);
                    }
                }
            }
        }
        
        return matrizDistancias;
    }
}