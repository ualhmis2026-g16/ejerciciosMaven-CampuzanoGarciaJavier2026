package ual.eda2.practica02;

import java.util.TreeMap;
import java.util.TreeSet;

public class DijkstraEj2 {
	/**
     * Versión Naive (O(V²)) del algoritmo de Dijkstra
     * @param G Grafo con los vértices y aristas
     * @param fuente Vértice inicial
     * @return Mapa con distancias y predecesores
     */
    public static TreeMap<String, Object> Dijkstra(Graph G, Vertex fuente) {
        // Variables para medir rendimiento
        long startTime = System.nanoTime();
        int iteraciones = 0;
        int actualizaciones = 0;
        int verticesProcesados = 0;
        int aristasProcesadas = 0;
        
        // Estructuras de datos
        TreeMap<Vertex, Double> dist = new TreeMap<>();
        TreeMap<Vertex, Vertex> pred = new TreeMap<>();
        TreeSet<Vertex> visitados = new TreeSet<>();
        
        // Inicializar distancias
        for (Vertex v : G.getVertices()) {
            if (v.equals(fuente)) {
                dist.put(v, 0.0); // Distancia a la fuente es 0
            } else if (G.adyacentes(fuente, v)) {
                dist.put(v, G.peso(fuente, v));
                pred.put(v, fuente);
            } else {
                dist.put(v, Double.POSITIVE_INFINITY);
                pred.put(v, null);
            }
        }
        
        // Marcar la fuente como visitada
        visitados.add(fuente);
        
        // Mientras no se hayan visitado todos los vértices
        while (visitados.size() < G.getVertices().size()) {
            iteraciones++;
            
            // Encontrar el vértice no visitado con menor distancia (from)
            Vertex from = null;
            double minDist = Double.POSITIVE_INFINITY;
            
            for (Vertex v : G.getVertices()) {
                if (!visitados.contains(v) && dist.get(v) < minDist) {
                    minDist = dist.get(v);
                    from = v;
                }
            }
            
            // Si no hay camino a ningún vértice más, terminamos
            if (from == null) {
                break;
            }
            
            // Marcar 'from' como visitado
            visitados.add(from);
            verticesProcesados++;
            
            // Para cada vértice adyacente no visitado (to)
            for (Vertex to : G.getVertices()) {
                if (!visitados.contains(to) && G.adyacentes(from, to)) {
                	aristasProcesadas++;
                    // Verificar si hay un camino más corto
                    if (dist.get(from) + G.weight(from, to) < dist.get(to)) {
                        // Actualizar distancia y predecesor
                        dist.put(to, dist.get(from) + G.weight(from, to));
                        pred.put(to, from);
                        actualizaciones++;
                    }
                }
            }
        }
        
        // Calcular estadísticas
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        // Prepara resultado
        TreeMap<String, Object> result = new TreeMap<>();
        result.put("dist", dist);
        result.put("pred", pred);
        result.put("tiempo_ejecucion_ns", duration);
        result.put("iteraciones", iteraciones);
        result.put("actualizaciones", actualizaciones);
        result.put("aristas_procesadas", aristasProcesadas);
        result.put("vertices_procesados", verticesProcesados);
        
        return result;
    }
    
	
}
