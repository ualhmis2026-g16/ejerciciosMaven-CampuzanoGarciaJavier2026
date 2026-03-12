package ual.eda2.practica02;

import java.io.*;
import java.util.*;

public class MainEj6EDALandLarge {
	/**
	 * metodo main que se encarga de realizar la ejecucion de DijkstraEj6
	 * con el fichero graphEDAlandLarge
	 * @param Strin[] args
	 */
    public static void main(String[] args) {
        Graph graph = new Graph();
        Map<String, Vertex> vertexMap = new HashMap<>();
        Map<Vertex, double[]> positions = new HashMap<>(); // Mapa de coordenadas para la heurística

        // Directorio de entrada
        String directorioEntrada = System.getProperty("user.dir") + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "java" + File.separator +
                "ual" + File.separator +
                "eda2" + File.separator +
                "practica02" + File.separator;

        String filename = directorioEntrada + "graphEDAlandLarge.txt";
        String positionsFile = directorioEntrada + "graphEDAlandLargePositions.txt"; // Archivo de coordenadas

        // Leer archivo de grafo y construirlo
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;

                String[] parts = line.trim().split("\\s+");
                if (parts.length < 3) continue;

                String fromId = parts[0];
                String toId = parts[1];
                double weight = Double.parseDouble(parts[2]);

                Vertex from = vertexMap.computeIfAbsent(fromId, Vertex::new);
                Vertex to = vertexMap.computeIfAbsent(toId, Vertex::new);

                graph.addEdge(from, to, weight);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo del grafo: " + e.getMessage());
            return;
        }

        // Leer archivo de posiciones para la heurística
        try (BufferedReader br = new BufferedReader(new FileReader(positionsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;

                String[] parts = line.trim().split("\\s+");
                if (parts.length < 3) continue;

                String vertexId = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);

                Vertex vertex = vertexMap.computeIfAbsent(vertexId, Vertex::new);
                positions.put(vertex, new double[]{x, y});
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo de posiciones: " + e.getMessage());
            return;
        }

        // Solicitar al usuario un vértice de origen y destino
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del vértice de origen: ");
        String origenId = scanner.nextLine().trim();

        System.out.print("Introduce el ID del vértice de destino: ");
        String destinoId = scanner.nextLine().trim();

        // Verificar si el vértice de origen y destino existen
        Vertex source = vertexMap.get(origenId);
        Vertex destination = vertexMap.get(destinoId);

        if (source == null) {
            System.err.println("El vértice de origen no existe en el grafo.");
            return;
        }
        if (destination == null) {
            System.err.println("El vértice de destino no existe en el grafo.");
            return;
        }

        // Ejecutar el algoritmo Dijkstra con heurística (DijkstraEj6)
        System.out.println("\nCalculando camino mínimo desde " + source.getId() + " hasta " + destination.getId() + "...\n");
        TreeMap<String, Object> results = DijkstraEj6.findPath(graph, source, destination, positions);

        // Recuperar resultados
        @SuppressWarnings("unchecked")
        List<Vertex> path = (List<Vertex>) results.get("path");
        double distance = (double) results.get("distance");
        boolean pathFound = (boolean) results.get("pathFound");
        long tiempoEjecucion = (long) results.get("tiempo_ejecucion_ns");
        int iteraciones = (int) results.get("iteraciones");
        int actualizaciones = (int) results.get("actualizaciones");
        int verticesProcesados = (int) results.get("vertices_procesados");
        int aristasProcesadas = (int) results.get("aristas_procesadas");

        // Mostrar resultados
        if (!pathFound) {
            System.out.println("No existe camino desde " + source.getId() + " hasta " + destination.getId());
        } else {
            System.out.println("Camino mínimo encontrado:");
            System.out.printf("Distancia total: %.2f\n", distance);
            System.out.print("Ruta: ");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i).getId());
                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }

        // Mostrar estadísticas
        System.out.println("\nEstadísticas de ejecución:");
        System.out.printf("Tiempo de ejecución: %d ns\n", tiempoEjecucion);
        System.out.printf("Iteraciones: %d\n", iteraciones);
        System.out.printf("Actualizaciones de distancia: %d\n", actualizaciones);
        System.out.println("Vértices procesados: " + verticesProcesados);
        System.out.println("Aristas procesadas: " + aristasProcesadas);

        scanner.close();
    }
}