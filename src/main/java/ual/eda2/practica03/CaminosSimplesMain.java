package ual.eda2.practica03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class CaminosSimplesMain {

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
	                    "practica03" + File.separator;
	            
	            // Cargar el grafo
	            Graph cityGraph = readGraphFromFile(directorioEntrada + "graphTSP01.txt");
	            System.out.println("Grafo cargado exitosamente.");
	            
	            
	            // Test Dijkstra's algorithm on the loaded graph
	            Vertex source = new Vertex("Almeria");
	            
	            if (source != null) {
	                System.out.println("\nAlgoritmo de caminos simples desde " + source + ": ");
	                Map<ArrayList<Vertex>, Double> result = new HashMap<>();
	                long tiempoTotal =0;
					try {
						long timeInicio = System.currentTimeMillis();
						result = CaminosSimples.buscarCaminosSimples(cityGraph, source);
						long tiempoFinal = System.currentTimeMillis();
						tiempoTotal = tiempoFinal - timeInicio;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //Aqui va el algoritmo de caminos simples
					int totalCircuitos =0;
					for(Entry<ArrayList<Vertex>, Double> arr : result.entrySet()) {
		                System.out.println("Camino " + totalCircuitos + ": " + arr.getKey().toString());
		                System.out.println("Peso: " + arr.getValue());
		                System.out.println();
		                totalCircuitos++;
					}
					System.out.println("Tiempo total de ejecución: " + tiempoTotal);
					
					System.out.println("Total de circuitos encontrados: " + totalCircuitos);

	            }
	            
	        } catch (IOException e) {
	            System.err.println("Error reading graph file: " + e.getMessage());
	        }
	    }

}
