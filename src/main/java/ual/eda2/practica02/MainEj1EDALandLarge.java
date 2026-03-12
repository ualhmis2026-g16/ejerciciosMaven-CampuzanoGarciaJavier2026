package ual.eda2.practica02;

import java.io.*;
import java.util.*;

public class MainEj1EDALandLarge {
	/**
	 * metodo main que se encarga de realizar la ejecucion de DijkstraEj1
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

        // Solicitar al usuario un vértice de origen
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del vértice de origen: ");
        String origenId = scanner.nextLine().trim();

        // Verificar si el vértice de origen existe
        Vertex source = vertexMap.get(origenId);

        if (source == null) {
            System.err.println("El vértice de origen no existe en el grafo.");
            return;
        }

        // Ejecutar el algoritmo DijkstraEj1 desde el vértice de origen
        System.out.println("\nCalculando distancias desde el vértice " + source.getId() + "...\n");
        Map<String, Object> results = DijkstraEj1.Dijkstra(graph, source);

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

        // Mostrar distancias a todos los vértices
        System.out.println("Distancias desde el vértice " + source.getId() + " a todos los demás vértices:");
        for (Map.Entry<Vertex, Double> entry : dist.entrySet()) {
            Vertex destination = entry.getKey();
            double distance = entry.getValue();

            if (distance == Double.POSITIVE_INFINITY) {
                System.out.println(" → " + destination.getId() + ": INFINITO");
            } else {
                System.out.printf(" → %s: %.2f\n", destination.getId(), distance);
            }
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