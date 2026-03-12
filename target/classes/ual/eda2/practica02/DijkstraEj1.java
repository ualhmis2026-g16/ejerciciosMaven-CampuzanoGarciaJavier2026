package ual.eda2.practica02;
import java.util.*;

/**
 * Clase que implementa el algoritmo de Dijkstra para calcular las distancias más cortas desde un vértice fuente 
 * a todos los demás vértices en un grafo ponderado.
 * 
 * El algoritmo utiliza un enfoque voraz (greedy) y es válido únicamente para grafos con pesos no negativos.
 * Además, el método calcula estadísticas sobre la ejecución, como el tiempo de ejecución y el número de iteraciones.
 */
public class DijkstraEj1 {

    /**
     * Aplica el algoritmo de Dijkstra para encontrar las distancias más cortas desde un vértice fuente 
     * a todos los demás vértices de un grafo.
     * 
     * @param G El grafo sobre el que se ejecutará el algoritmo.
     * @param fuente El vértice desde el cual se calculan las distancias más cortas.
     * @return Un mapa que contiene:
     *         - "dist": Un TreeMap que asocia cada vértice con su distancia mínima desde el vértice fuente.
     *         - "pred": Un TreeMap que asocia cada vértice con su predecesor en el camino más corto.
     *         - Información estadística como el tiempo de ejecución, iteraciones, actualizaciones, etc.
     */
    public static Map<String, Object> Dijkstra(Graph G, Vertex fuente) {
        // Medir el tiempo de inicio
        long startTime = System.nanoTime();

        // Variables para estadísticas
        int iteraciones = 0;
        int actualizaciones = 0;
        int verticesProcesados = 0;
        int aristasProcesadas = 0;

        // Inicialización de estructuras de datos
        TreeMap<Vertex, Double> dist = new TreeMap<>(); // Distancias mínimas desde el vértice fuente
        TreeMap<Vertex, Vertex> pred = new TreeMap<>(); // Predecesores en el camino más corto
        TreeSet<Vertex> V_menos_S = new TreeSet<>();    // Conjunto de vértices no procesados
        TreeMap<String, Object> result = new TreeMap<>(); // Resultado final

        // Inicializar V_menos_S con todos los vértices menos la fuente
        for (Vertex v : G.getVertices()) {
            if (!v.equals(fuente)) {
                V_menos_S.add(v);
            }
        }

        int adyacentes = 0; // Contador de vértices adyacentes

        // Inicializar distancias y predecesores para cada vértice en V_menos_S
        for (Vertex v : V_menos_S) {
            if (v == fuente) {
                dist.put(v, 0.0); // La distancia al vértice fuente es 0
                pred.put(v, null); // No tiene predecesor
            } else if (G.adyacentes(fuente, v)) {
                adyacentes++;
                dist.put(v, G.peso(fuente, v)); // Peso de la arista desde la fuente
                pred.put(v, fuente); // Fuente como predecesor
            } else {
                dist.put(v, Double.POSITIVE_INFINITY); // Distancia inicial infinita
                pred.put(v, null); // Sin predecesor
            }
        }

        // Mientras queden vértices por procesar
        while (!V_menos_S.isEmpty()) {
            iteraciones++; // Contador de iteraciones

            // Encontrar el vértice u con la distancia mínima en V_menos_S
            Vertex u = null;
            double minDist = Double.POSITIVE_INFINITY;

            for (Vertex v : V_menos_S) {
                if (dist.get(v) < minDist) {
                    minDist = dist.get(v);
                    u = v;
                }
            }

            // Eliminar u de V_menos_S
            if(u==null) break; 
            V_menos_S.remove(u);
            verticesProcesados++; // Incrementar contador de vértices procesados

            // Actualizar las distancias de los vértices adyacentes
            for (Vertex v : V_menos_S) {
                if (G.adyacentes(u, v)) { // Si v es adyacente a u
                    aristasProcesadas++; // Incrementar contador de aristas procesadas

                    // Relajación: actualizar dist[v] si encontramos un camino más corto
                    if (dist.get(u) + G.weight(u, v) < dist.get(v)) {
                        dist.put(v, dist.get(u) + G.weight(u, v)); // Nueva distancia mínima
                        pred.put(v, u); // Actualizar predecesor
                        actualizaciones++; // Incrementar contador de actualizaciones
                    }
                }
            }
        }

        // Calcular estadísticas de ejecución
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // Guardar resultados en el mapa
        result.put("dist", dist);
        result.put("pred", pred);
        result.put("tiempo_ejecucion_ns", duration);
        result.put("iteraciones", iteraciones);
        result.put("actualizaciones", actualizaciones);
        result.put("aristas_procesadas", aristasProcesadas);
        result.put("vertices_procesados", verticesProcesados);

        // Retornar resultados
        return result;
    }
}