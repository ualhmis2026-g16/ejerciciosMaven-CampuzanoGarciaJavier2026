
	package ual.eda2.practica02;

	import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
	import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
public class DijkstraMainEj1 {

    /**
     * Reads a graph from a text file.
     * 
     * @param filePath The path to the text file
     * @return A Graph object constructed from the file data
     * @throws IOException If an I/O error occurs
     */
	  public static Graph readGraphFromFile(String filePath) throws IOException {
	        Graph graph = new Graph();
	        Map<String, Vertex> verticesMap = new HashMap<>();
	        
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                line = line.trim();
	                if (line.isEmpty()) continue;
	                
	                String[] parts = line.split("\\s+");
	                if (parts.length == 3) {
	                    String sourceId = parts[0];
	                    String destId = parts[1];
	                    double distance = Double.parseDouble(parts[2]);
	                    
	                    // Get or create vertices
	                    Vertex source = getOrCreateVertex(verticesMap, graph, sourceId);
	                    Vertex dest = getOrCreateVertex(verticesMap, graph, destId);
	                    
	                    // Add the edge to the graph (assuming undirected graph)
	                    graph.addEdge(source, dest, distance);
	                    graph.addEdge(dest, source, distance); // Remove this line if it's a directed graph
	                }
	            }
	        }
	        
	        return graph;
	    }
	    
	    /**
	     * Helper method to get an existing vertex or create a new one if needed
	     * @param Map<String, Vertex> verticesMap
	     * @param Graph graph
	     * @param String id
	     * @return Vertex
	     */
	    private static Vertex getOrCreateVertex(Map<String, Vertex> verticesMap, Graph graph, String id) {
	        Vertex vertex = verticesMap.get(id);
	        if (vertex == null) {
	            vertex = new Vertex(id);
	            verticesMap.put(id, vertex);
	            graph.addVertex(vertex);
	        }
	        return vertex;
	    }
	   
	    /**
	     * Reconstructs the shortest path from source to destination using the predecessor map
	     * 
	     * @param pred The predecessor map from Dijkstra's algorithm
	     * @param source The source vertex
	     * @param destination The destination vertex
	     * @return A list of vertices representing the path from source to destination
	     */
	    private static List<Vertex> reconstructPath(TreeMap<Vertex, Vertex> pred, Vertex source, Vertex destination) {
	        List<Vertex> path = new ArrayList<>();
	        
	        // Start from the destination and work backwards
	        for (Vertex current = destination; current != null; current = pred.get(current)) {
	            path.add(current);
	            if (current.equals(source)) {
	                break; // We've reached the source
	            }
	        }
	        
	        // Reverse the path to get source to destination
	        Collections.reverse(path);
	        return path;
	    }
	    
	    /**
	     * Método principal para probar el algoritmo
	     */
	    public static void main(String[] args) {
	        try {
	        	String directorioEntrada = System.getProperty("user.dir") + File.separator +
	                    "src" + File.separator +
	                    "main" + File.separator +
	                    "java" + File.separator +
	                    "ual" + File.separator +
	                    "eda2" + File.separator +
	                    "practica02" + File.separator;
	            
	            // Cargar el grafo
	            Graph cityGraph = readGraphFromFile(directorioEntrada + "graphEDAland.txt");
	            System.out.println("Grafo cargado exitosamente.");
	            
	            
	            // Test Dijkstra's algorithm on the loaded graph
	            Vertex source = null;
	            Scanner scanner = new Scanner(System.in);
	            System.out.print("Introduce vértice origen (ej: Madrid): ");
	            String sourceId = scanner.nextLine().trim();
	            for (Vertex v : cityGraph.getVertices()) {
	                if (v.getId().equals(sourceId)) {
	                    source = v;
	                    break;
	                }
	            }
	            
	            if (source != null) {
	                System.out.println("\nRunning Dijkstra's algorithm from " + source + ": ");
	                Map<String, Object> result = DijkstraEj1.Dijkstra(cityGraph, source);
	                
	                @SuppressWarnings("unchecked")
	                TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");
	                
	                for (Map.Entry<Vertex, Double> entry : dist.entrySet()) {
	                    System.out.println("Distance to " + entry.getKey().getId() + 
	                                      ": " + entry.getValue());
	                }
	                long tiempoEjecucion = (long) result.get("tiempo_ejecucion_ns");
	                int iteraciones = (int) result.get("iteraciones");
	                int actualizaciones = (int) result.get("actualizaciones");
	                int verticesProcesados = (int) result.get("vertices_procesados");
	                int aristasProcesadas = (int) result.get("aristas_procesadas");
	                
	                // Mostrar estadísticas
	                System.out.println("\nEstadísticas de ejecución:");
	                System.out.println("Tiempo de ejecución: " + tiempoEjecucion + " nanosegundos");
	                System.out.println("Iteraciones: " + iteraciones);
	                System.out.println("Actualizaciones de distancia: " + actualizaciones);
	                System.out.println("Vértices procesados: " + verticesProcesados);
	                System.out.println("Aristas procesadas: " + aristasProcesadas);

	            }
	            
	        } catch (IOException e) {
	            System.err.println("Error reading graph file: " + e.getMessage());
	        }
	    }

}
