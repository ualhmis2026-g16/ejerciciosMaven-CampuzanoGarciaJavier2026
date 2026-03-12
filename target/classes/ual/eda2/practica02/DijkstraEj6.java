package ual.eda2.practica02;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class DijkstraEj6 {


    /**
     * Clase interna para representar un elemento en la cola de prioridad para A*
     * Cada elemento contiene el vértice destino (to), los valores g, h, f y el vértice origen (from)
     */
    private static class VertexAStar implements Comparable<VertexAStar> {
        Vertex to;       // Vértice destino
        double g;        // Coste real desde el origen (distancia acumulada)
        double h;        // Valor heurístico (distancia estimada al destino)
        double f;        // Valor f = g + h (coste total estimado)
        Vertex from;     // Vértice origen (predecesor)
        
        public VertexAStar(Vertex to, double g, double h, Vertex from) {
            this.to = to;
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.from = from;
        }
        
        @Override
        public int compareTo(VertexAStar other) {
            return Double.compare(this.f, other.f);
        }
    }
    
    /**
     * Función heurística - Distancia Euclídea
     * @param from Vértice actual
     * @param destination Vértice destino final
     * @param positions Mapa con las coordenadas de los vértices
     * @return Valor heurístico (distancia estimada)
     */
    private static double heuristic(Vertex from, Vertex destination, Map<Vertex, double[]> positions) {
        // Si no hay posiciones disponibles, devolver 0 (sin heurística)
        if (positions == null || !positions.containsKey(from) || !positions.containsKey(destination)) {
            return 0;
        }
        
        double[] posFrom = positions.get(from);
        double[] posTo = positions.get(destination);
        
        // Calcular distancia euclídea
        double dx = posFrom[0] - posTo[0];
        double dy = posFrom[1] - posTo[1];
        
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    /**
     * Versión del algoritmo A* basado en Best-First Search de Dijkstra
     * No utiliza listas abierta y cerrada explícitas
     * @param G Grafo con los vértices y aristas
     * @param source Vértice inicial
     * @param destination Vértice destino
     * @param positions Mapa con las coordenadas de los vértices
     * @return Mapa con resultados y estadísticas
     */
    public static TreeMap<String, Object> findPath(Graph G, Vertex source, Vertex destination, 
                                                 Map<Vertex, double[]> positions) {
        // Variables para medir rendimiento
        long startTime = System.nanoTime();
        int iteraciones = 0;
        int actualizaciones = 0;
        int verticesProcesados = 0;
        int aristasProcesadas = 0;
        
        // Estructuras de datos
        TreeMap<Vertex, Double> gValues = new TreeMap<>(); // Costes reales (g)
        TreeMap<Vertex, Vertex> pred = new TreeMap<>();    // Predecesores
        
        // Cola de prioridad organizada por valor f mínimo
        PriorityQueue<VertexAStar> pq = new PriorityQueue<>();
        
        // Inicializar valores g
        for (Vertex v : G.getVertices()) {
            if (v.equals(source)) {
                gValues.put(v, 0.0); // Distancia al origen es 0
            } else {
                gValues.put(v, Double.POSITIVE_INFINITY);
                pred.put(v, null);
            }
        }
        
        // Añadir el vértice origen a la cola
        double h0 = heuristic(source, destination, positions);
        pq.add(new VertexAStar(source, 0.0, h0, null));
        
        boolean pathFound = false;
        
        // Mientras la cola de prioridad no esté vacía
        while (!pq.isEmpty()) {
            iteraciones++;
            
            // Extraer el vértice con menor valor f
            VertexAStar current = pq.poll();
            Vertex to = current.to;
            
            
            
            // Si la g en la cola es mayor que la registrada, ignorar este camino
            // Esta es la clave para descartar caminos no óptimos sin necesidad de lista cerrada
            if (current.g > gValues.get(to)) {
                continue;
            }
            
            // Si no es el vértice origen, actualizar el predecesor
            if (!to.equals(source)) {
                pred.put(to, current.from);
            }
        
            
            verticesProcesados++;
            
            // Para cada vértice adyacente
            for (Vertex neighbor : G.getVertices()) {
                if (G.adyacentes(to, neighbor)) {
                    aristasProcesadas++;
                    
                    // Calcular nuevo valor g
                    double weight = G.weight(to, neighbor);
                    double newG = gValues.get(to) + weight;
                    
                    // Verificar si hay un camino con menor g (no f)
                    if (newG < gValues.get(neighbor)) {
                        // Actualizar valor g
                        gValues.put(neighbor, newG);
                        
                        // Calcular h para este vecino
                        double h = heuristic(neighbor, destination, positions);
                        
                        // Añadir a la cola con los nuevos valores g, h, f
                        pq.add(new VertexAStar(neighbor, newG, h, to));
                        
                        actualizaciones++;
                    }
                }
            }
         // Si hemos llegado al destino
            if (to.equals(destination)) {
                pathFound = true;
            }
        }
        
        // Reconstruir camino
        ArrayList<Vertex> path = new ArrayList<>();
        if (pathFound) {
            Vertex current = destination;
            while (current != null) {
                path.add(0, current);
                current = pred.get(current);
            }
        }
        
        // Calcular estadísticas
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        // Preparar resultado
        TreeMap<String, Object> result = new TreeMap<>();
        result.put("path", path);
        result.put("distance", gValues.get(destination));
        result.put("pathFound", pathFound);
        result.put("tiempo_ejecucion_ns", duration);
        result.put("iteraciones", iteraciones);
        result.put("actualizaciones", actualizaciones);
        result.put("vertices_procesados", verticesProcesados);
        result.put("aristas_procesadas", aristasProcesadas);
        
        return result;
    }
}
