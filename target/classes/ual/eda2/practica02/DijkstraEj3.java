package ual.eda2.practica02;

import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

public class DijkstraEj3 {

	/**
     * Clase interna para representar un elemento en la cola de prioridad
     * Cada elemento contiene un vértice y su distancia actual
     */
    private static class VertexDistancePair implements Comparable<VertexDistancePair> {
        Vertex vertex;
        double distance;
        
        public VertexDistancePair(Vertex v, double d) {
            this.vertex = v;
            this.distance = d;
        }
        
        @Override
        public int compareTo(VertexDistancePair other) {
            return Double.compare(this.distance, other.distance);
        }
    }
    
    /**
     * Versión del algoritmo de Dijkstra usando cola de prioridad
     * Complejidad: O((E+V) log V)
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
        
        // Cola de prioridad organizada por distancia mínima
        PriorityQueue<VertexDistancePair> pq = new PriorityQueue<>();
        
        // Inicializar distancias
        for (Vertex v : G.getVertices()) {
            if (v.equals(fuente)) {
                dist.put(v, 0.0); // Distancia a la fuente es 0
                pq.add(new VertexDistancePair(v, 0.0));
            } else {
                dist.put(v, Double.POSITIVE_INFINITY);
                pred.put(v, null);
                pq.add(new VertexDistancePair(v, Double.POSITIVE_INFINITY));
            }
        }
        
        // Mientras la cola de prioridad no esté vacía
        while (!pq.isEmpty()) {
            iteraciones++;
            
            // Extraer el vértice con menor distancia (from)
            VertexDistancePair fromPair = pq.poll();
            Vertex from = fromPair.vertex;
            double fromDist = fromPair.distance;
            
            // Si el vértice ya fue visitado o la distancia en la cola no es actual, ignorarlo
            if (visitados.contains(from) || fromDist > dist.get(from)) {
                continue;
            }
            
            // Marcar 'from' como visitado
            visitados.add(from);
            verticesProcesados++;
            
            // Para cada vértice adyacente (to)
            for (Vertex to : G.getVertices()) {
                if (G.adyacentes(from, to) && !visitados.contains(to)) {
                    // Calcular nueva distancia
                    double newDist = dist.get(from) + G.weight(from, to);
                    aristasProcesadas++;
                    
                    // Verificar si hay un camino más corto
                    if (newDist < dist.get(to)) {
                        // Actualizar distancia y predecesor
                        dist.put(to, newDist);
                        pred.put(to, from);
                        
                        // Añadir a la cola con la nueva distancia
                        pq.add(new VertexDistancePair(to, newDist));
                        
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
        result.put("vertices_procesados", verticesProcesados);
        result.put("aristas_procesadas", aristasProcesadas);
        
        return result;
    }
}
