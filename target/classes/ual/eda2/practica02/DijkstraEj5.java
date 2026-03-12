package ual.eda2.practica02;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

public class DijkstraEj5 {

	/**
     * Clase interna para representar un nodo en el algoritmo A*
     * Almacena información adicional necesaria para A*
     */
    private static class AStarNode implements Comparable<AStarNode> {
        Vertex vertex;    // El vértice asociado a este nodo
        double g;         // Coste real desde el origen hasta este nodo
        double h;         // Valor heurístico (distancia estimada hasta el destino)
        double f;         // Valor f = g + h
        AStarNode parent; // Nodo padre para reconstruir la ruta
        
        public AStarNode(Vertex v) {
            this.vertex = v;
            this.g = Double.POSITIVE_INFINITY;
            this.h = 0;
            this.f = Double.POSITIVE_INFINITY;
            this.parent = null;
        }
        
        @Override
        public int compareTo(AStarNode other) {
            return Double.compare(this.f, other.f);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            AStarNode other = (AStarNode) obj;
            return vertex.equals(other.vertex);
        }
        
        @Override
        public int hashCode() {
            return vertex.hashCode();
        }
    }
    
    /**
     * Algoritmo A* para encontrar la ruta mínima entre dos vértices
     * @param G Grafo con los vértices y aristas
     * @param source Vértice origen
     * @param destination Vértice destino
     * @param positions Mapa con las coordenadas (x,y) de cada vértice para la heurística
     * @return Mapa con la ruta, distancia y estadísticas
     */
    public static TreeMap<String, Object> findPath(Graph G, Vertex source, Vertex destination, 
                                              Map<Vertex, double[]> positions) {
        // Variables para medir rendimiento
        long startTime = System.nanoTime();
        int nodesEvaluated = 0;
        int nodesGenerated = 0;
        
        // Mapeo de vértices a nodos A*
        TreeMap<Vertex, AStarNode> nodeMap = new TreeMap<>();
        
        // Inicializar listas abierta y cerrada
        PriorityQueue<AStarNode> openList = new PriorityQueue<>(); // Nodos por evaluar
        TreeSet<AStarNode> openSet = new TreeSet<>(); // Para búsqueda rápida en openList
        TreeSet<AStarNode> closedList = new TreeSet<>(); // Nodos ya evaluados
        
        // Inicializar nodo origen
        AStarNode sourceNode = new AStarNode(source);
        sourceNode.g = 0;
        sourceNode.h = heuristic(source, destination, positions);
        sourceNode.f = sourceNode.g + sourceNode.h;
        nodeMap.put(source, sourceNode);
        
        // Añadir nodo origen a la lista abierta
        openList.add(sourceNode);
        openSet.add(sourceNode);
        nodesGenerated++;
        
        AStarNode current = null;
        boolean pathFound = false;
        
        // Mientras la lista abierta no esté vacía
        while (!openList.isEmpty()) {
            // Obtener nodo con menor valor f
            current = openList.poll();
            openSet.remove(current);
            nodesEvaluated++;
            
            // Comprobar si hemos llegado al destino
            if (current.vertex.equals(destination)) {
                pathFound = true;
                break;
            }
            
            // Mover nodo actual de lista abierta a lista cerrada
            closedList.add(current);
            
            // Comprobar todos los nodos vecinos
            for (Vertex neighborVertex : G.getVertices()) {
                if (G.adyacentes(current.vertex, neighborVertex)) {
                    // Obtener o crear nodo vecino
                    AStarNode neighbor = nodeMap.get(neighborVertex);
                    if (neighbor == null) {
                        neighbor = new AStarNode(neighborVertex);
                        nodeMap.put(neighborVertex, neighbor);
                        nodesGenerated++;
                    }
                    
                    // Saltar nodos ya evaluados
                    if (closedList.contains(neighbor)) {
                        continue;
                    }
                    
                    // Calcular g tentativo
                    double tentativeG = current.g + G.weight(current.vertex, neighborVertex);
                    
                    boolean isNewNode = !openSet.contains(neighbor);
                    
                    if (isNewNode || tentativeG < neighbor.g) {
                        // Este camino es mejor
                        neighbor.parent = current;
                        neighbor.g = tentativeG;
                        neighbor.h = heuristic(neighborVertex, destination, positions);
                        neighbor.f = neighbor.g + neighbor.h;
                        
                        if (isNewNode) {
                            openList.add(neighbor);
                            openSet.add(neighbor);
                        } else {
                            // Actualizar la posición en la cola de prioridad
                            openList.remove(neighbor);
                            openList.add(neighbor);
                        }
                    }
                }
            }
        }
        
        // Calcular estadísticas
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        // Reconstruir ruta si se encontró un camino
        ArrayList<Vertex> path = new ArrayList<>();
        double distance = Double.POSITIVE_INFINITY;
        
        if (pathFound) {
            // Reconstruir camino
            for (AStarNode node = current; node != null; node = node.parent) {
                path.add(0, node.vertex);
            }
            distance = current.g;
        }
        
        // Preparar resultado
        TreeMap<String, Object> result = new TreeMap<>();
        result.put("path", path);
        result.put("distance", distance);
        result.put("pathFound", pathFound);
        result.put("tiempo_ejecucion_ns", duration);
        result.put("nodos_evaluados", nodesEvaluated);
        result.put("nodos_generados", nodesGenerated);
        
        return result;
    }
    
    /**
     * Función heurística - Distancia Euclídea
     * @param from Vértice origen
     * @param to Vértice destino
     * @param positions Mapa con las coordenadas de los vértices
     * @return Valor heurístico (distancia estimada)
     */
    private static double heuristic(Vertex from, Vertex to, Map<Vertex, double[]> positions) {
        // Si no hay posiciones disponibles, devolver 0 (sin heurística)
        if (positions == null || !positions.containsKey(from) || !positions.containsKey(to)) {
            return 0;
        }
        
        double[] posFrom = positions.get(from);
        double[] posTo = positions.get(to);
        
        // Calcular distancia euclídea
        double dx = posFrom[0] - posTo[0];
        double dy = posFrom[1] - posTo[1];
        
        return Math.sqrt(dx*dx + dy*dy);
    }
    
}
