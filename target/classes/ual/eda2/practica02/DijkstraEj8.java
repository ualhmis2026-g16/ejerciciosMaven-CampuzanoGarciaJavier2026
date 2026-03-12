package ual.eda2.practica02;

import java.util.Map.Entry;


import java.util.PriorityQueue;
import java.util.TreeMap;

public class DijkstraEj8 {

    /**
     * Clase interna para representar un elemento en la cola de prioridad
     * Cada elemento contiene el vértice destino (to), la distancia y el vértice origen (from)
     */
    private static class VertexTriple implements Comparable<VertexTriple> {
        Vertex to;       // Vértice destino
        double distance; // Distancia acumulada hasta este vértice
        Vertex from;     // Vértice origen (predecesor)
        
        public VertexTriple(Vertex to, double distance, Vertex from) {
            this.to = to;
            this.distance = distance;
            this.from = from;
        }
        
        @Override
        public int compareTo(VertexTriple other) {
            return Double.compare(this.distance, other.distance);
        }
    }
    
    /**
     * Versión del algoritmo de Dijkstra usando Best-First Search con un solo vértice origen
     * @param G Grafo con los vértices y aristas
     * @param fuente Vértice inicial
     * @return Mapa con distancias y predecesores
     */
    public static TreeMap<String, Object> Dijkstra(Graph G, Vertex fuente) {
        return DijkstraAux(G, fuente);
    }
    
    /**
     * Versión del algoritmo de Dijkstra usando Best-First Search
     * No utiliza una estructura para almacenar vértices visitados
     * @param G Grafo con los vértices y aristas
     * @param fuente Vértice inicial
     * @param intermedio Vértice intermedio
     * @param destino Vértice destino
     * @return Mapa con distancias y predecesores
     */
    public static TreeMap<String, Object> DijkstraIntermedio(Graph G, Vertex fuente, Vertex intermedio, Vertex destino){
    	TreeMap<String, Object> result = new TreeMap<>();
    	TreeMap<String, Object> dijkstraFuente = DijkstraAux(G, fuente);
    	TreeMap<String, Object> dijkstraIntermedio = DijkstraAux(G, intermedio);
    	TreeMap<Vertex, Double> distFuente = (TreeMap<Vertex, Double>) dijkstraFuente.get("dist");
        TreeMap<Vertex, Double> distIntermedio = (TreeMap<Vertex, Double>) dijkstraIntermedio.get("dist");
        TreeMap<Vertex, Vertex> predFuente = (TreeMap<Vertex, Vertex>) dijkstraFuente.get("pred");
        TreeMap<Vertex, Vertex> predIntermedio = (TreeMap<Vertex, Vertex>) dijkstraIntermedio.get("pred");
        long duracion = (long)dijkstraFuente.get("tiempo_ejecucion_ns") + (long)dijkstraIntermedio.get("tiempo_ejecucion_ns");
        int iteraciones = (int)dijkstraFuente.get("iteraciones") + (int)dijkstraIntermedio.get("iteraciones");
        int actualizaciones = (int)dijkstraFuente.get("actualizaciones") + (int)dijkstraIntermedio.get("actualizaciones");
        int verticesProcesados = (int)dijkstraFuente.get("vertices_procesados") + (int)dijkstraIntermedio.get("vertices_procesados");
        int aristasProcesadas = (int)dijkstraFuente.get("aristas_procesadas") + (int)dijkstraIntermedio.get("aristas_procesadas");
        result.put("distFuente", distFuente);
        result.put("distIntermedio", distIntermedio);
        result.put("predFuente", predFuente);
        result.put("predIntermedio", predIntermedio);
        result.put("tiempo_ejecucion_ns", duracion);
        result.put("iteraciones", iteraciones);
        result.put("actualizaciones", actualizaciones);
        result.put("vertices_procesados", verticesProcesados);
        result.put("aristas_procesadas", aristasProcesadas);
        
        // Añadir el dist para compatibilidad con los tests
        result.put("dist", distFuente);
        
        return result;
    }
    
    public static TreeMap<String, Object> DijkstraAux(Graph G, Vertex fuente) {
        // Variables para medir rendimiento
        long startTime = System.nanoTime();
        int iteraciones = 0;
        int actualizaciones = 0;
        int verticesProcesados = 0;
        int aristasProcesadas = 0;
        
        // Estructuras de datos
        TreeMap<Vertex, Double> dist = new TreeMap<>();
        TreeMap<Vertex, Vertex> pred = new TreeMap<>();
        
        // Cola de prioridad organizada por distancia mínima con tripletas <to, distance, from>
        PriorityQueue<VertexTriple> pq = new PriorityQueue<>();
        
        /// Inicializar distancias
        for (Vertex v : G.getVertices()) {
           if (v.equals(fuente)) {
               dist.put(v, 0.0); // Distancia a la fuente debería ser 0
           } else {
               dist.put(v, Double.POSITIVE_INFINITY); // Los demás vértices a INFINITY
           }
           pred.put(v, null);
       }
        
        // Añadir el vértice fuente a la cola
        pq.add(new VertexTriple(fuente, Double.POSITIVE_INFINITY, null));
        
        // Mientras la cola de prioridad no esté vacía
        while (!pq.isEmpty()) {
            iteraciones++;
            
            // Extraer la tripleta con menor distancia
            VertexTriple triple = pq.poll();
            Vertex to = triple.to;
            double ancho = triple.distance;
            Vertex from = triple.from;
            
            // Si la distancia en la cola es mayor que la registrada, ignorar este camino
            if (ancho < dist.get(to)) {
                continue;
            }
            
            if(from!=null) {
            	pred.put(to, from);
            }
            
            verticesProcesados++;
            
            // Para cada vértice adyacente
            for (Vertex neighbor : G.getVertices()) {
                if (G.adyacentes(to, neighbor)) {
                   if (G.adyacentes(to, neighbor)) {
                      aristasProcesadas++;
                      
                      // Calcular nueva distancia
                      double weight = G.weight(to, neighbor);
                      double newDist = dist.get(to) + weight;
                      
                      // Verificar si hay un camino más corto
                      if (newDist < dist.get(neighbor)) {
                          // Actualizar distancia
                          dist.put(neighbor, newDist);
                          
                          // Añadir a la cola con la nueva distancia y el vértice origen
                          pq.add(new VertexTriple(neighbor, newDist, to));
                          
                          actualizaciones++;
                      }
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