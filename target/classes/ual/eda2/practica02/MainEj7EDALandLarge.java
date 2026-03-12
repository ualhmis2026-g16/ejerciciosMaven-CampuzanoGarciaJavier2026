package ual.eda2.practica02;

import java.io.*;
import java.util.*;

public class MainEj7EDALandLarge {
	/**
	 * metodo main que se encarga de realizar la ejecucion de DijkstraEj7
	 * con el fichero graphEDAlandLarge
	 * @param Strin[] args
	 */
    public static void main(String[] args) {
        Graph graph = new Graph();
        Map<String, Vertex> vertexMap = new HashMap<>();

        // Directorio de entrada
        String directorioEntrada = System.getProperty("user.dir") + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "java" + File.separator +
                "ual" + File.separator +
                "eda2" + File.separator +
                "practica02" + File.separator;

        String filename = directorioEntrada + "graphEDAlandLarge.txt";

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

        // Ejecutar el algoritmo DijkstraEj7
        System.out.println("\nCalculando camino de máxima capacidad desde " + source.getId() + " hasta " + destination.getId() + "...\n");
        TreeMap<String, Object> results = DijkstraEj7.Dijkstra(graph, source);

        // Recuperar resultados
        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) results.get("dist");

        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Vertex> pred = (TreeMap<Vertex, Vertex>) results.get("pred");

        long tiempoEjecucion = (long) results.get("tiempo_ejecucion_ns");
        int iteraciones = (int) results.get("iteraciones");
        int actualizaciones = (int) results.get("actualizaciones");
        int verticesProcesados = (int) results.get("vertices_procesados");
        int aristasProcesadas = (int) results.get("aristas_procesadas");

        // Obtener la capacidad máxima al destino
        Double maxCapacity = dist.get(destination);

        if (maxCapacity == null || maxCapacity == Double.NEGATIVE_INFINITY) {
            System.out.println("No existe camino desde " + source.getId() + " hasta " + destination.getId());
            return;
        }

        // Reconstruir el camino
        List<Vertex> path = reconstructPath(pred, source, destination);

        // Mostrar resultados
        System.out.println("Camino de máxima capacidad encontrado:");
        System.out.printf("Capacidad máxima: %.2f\n", maxCapacity);
        System.out.print("Ruta: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getId());
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();

        // Mostrar estadísticas
        System.out.println("\nEstadísticas de ejecución:");
        System.out.printf("Tiempo de ejecución: %d ns\n", tiempoEjecucion);
        System.out.printf("Iteraciones: %d\n", iteraciones);
        System.out.printf("Actualizaciones de capacidad: %d\n", actualizaciones);
        System.out.println("Vértices procesados: " + verticesProcesados);
        System.out.println("Aristas procesadas: " + aristasProcesadas);

        scanner.close();
    }

    /**
     * Reconstruye el camino desde el origen hasta el destino
     * utilizando el mapa de predecesores.
     *
     * @param pred        Mapa de predecesores para cada vértice.
     * @param source      Vértice de origen.
     * @param destination Vértice de destino.
     * @return Lista de vértices que forman el camino.
     */
    private static List<Vertex> reconstructPath(TreeMap<Vertex, Vertex> pred, Vertex source, Vertex destination) {
        List<Vertex> path = new ArrayList<>();

        // Comenzar desde el destino y retroceder
        for (Vertex current = destination; current != null; current = pred.get(current)) {
            path.add(0, current); // Insertar al inicio
        }

        return path;
    }
}