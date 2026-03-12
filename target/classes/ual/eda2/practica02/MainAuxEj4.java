package ual.eda2.practica02;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class MainAuxEj4 {
    /**
     * Método principal para probar el algoritmo DijkstraEj4
     *@param String[] args 
     */
    @SuppressWarnings("resource")
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
            Graph cityGraph = DijkstraMainEj1.readGraphFromFile(directorioEntrada + "graphEDAland.txt");
            System.out.println("Grafo cargado exitosamente.");
            System.out.println("Fecha: 2025-04-04 08:11:44");
            System.out.println("Usuario: ualacg019");
            
            // Mostrar vértices disponibles
            System.out.println("\nVértices disponibles en el grafo:");
            for (Vertex v : cityGraph.getVertices()) {
                System.out.print(v.getId() + " ");
            }
            System.out.println("\n");
            
            // Obtener origen y destino por entrada de usuario
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce vértice origen (ej: Madrid): ");
            String sourceId = scanner.nextLine().trim();
            
            System.out.print("Introduce vértice destino: ");
            String destId = scanner.nextLine().trim();
            
            // Buscar vértices origen y destino
            Vertex source = null;
            Vertex destination = null;
            
            for (Vertex v : cityGraph.getVertices()) {
                if (v.getId().equals(sourceId)) {
                    source = v;
                }
                if (v.getId().equals(destId)) {
                    destination = v;
                }
            }
            
            // Comprobar si se encontraron ambos vértices
            if (source == null) {
                System.out.println("Vértice origen no encontrado: " + sourceId);
                return;
            }
            if (destination == null) {
                System.out.println("Vértice destino no encontrado: " + destId);
                return;
            }
            
            // Ejecutar el algoritmo de Dijkstra
            System.out.println("\nEjecutando algoritmo DijkstraBestFirst desde " + sourceId + ":");
            TreeMap<String, Object> result = DijkstraEj4.Dijkstra(cityGraph, source);
            
            // Extraer resultados
            @SuppressWarnings("unchecked")
            TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) result.get("dist");
            
            @SuppressWarnings("unchecked")
            TreeMap<Vertex, Vertex> pred = (TreeMap<Vertex, Vertex>) result.get("pred");
            
            // Obtener estadísticas
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
            
            // Obtener la distancia al destino
            Double distance = dist.get(destination);
            
            if (distance == null || distance == Double.POSITIVE_INFINITY) {
                System.out.println("No existe camino desde " + sourceId + " hasta " + destId);
                return;
            }
            
            // Mostrar el camino más corto
            System.out.println("\nCamino más corto desde " + sourceId + " hasta " + destId + ":");
            System.out.println("Distancia: " + distance);
            
            System.out.print("Ruta: ");
            List<Vertex> path = reconstructPath(pred, source, destination);
            
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i).getId());
                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
            
            scanner.close();
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo del grafo: " + e.getMessage());
        }
    }
    
    /**
     * Reconstruye el camino más corto desde el origen hasta el destino
     * utilizando el mapa de predecesores
     * @param TreeMap<Vertex, Vertex> pred
     * @param Vertex source
     * @param Vertex destination
     * @return List<Vertex>
     */
    private static List<Vertex> reconstructPath(TreeMap<Vertex, Vertex> pred, Vertex source, Vertex destination) {
        List<Vertex> path = new ArrayList<>();
        
        // Comenzar desde el destino y retroceder
        for (Vertex current = destination; current != null; current = pred.get(current)) {
            path.add(current);
            if (current.equals(source)) {
                break; // Hemos llegado al origen
            }
        }
        
        // Invertir el camino para obtener origen -> destino
        Collections.reverse(path);
        return path;
    }
}