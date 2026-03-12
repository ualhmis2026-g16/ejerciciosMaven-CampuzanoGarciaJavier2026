package ual.eda2.practica02;

import java.io.*;
import java.util.*;

public class MainEj3EDALandLarge {
	/**
	 * metodo main que se encarga de realizar la ejecucion de DijkstraEj3
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

        // Leer archivo y construir grafo
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
            System.err.println("Error leyendo el archivo: " + e.getMessage());
            return;
        }

        // Solicitar al usuario un vértice de origen
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del vértice de origen: ");
        String origenId = scanner.nextLine();

        // Verificar si el vértice de origen existe
        Vertex source = vertexMap.get(origenId);
        if (source == null) {
            System.err.println("El vértice de origen no existe en el grafo.");
            return;
        }

        System.out.println("\nCalculando caminos mínimos desde el vértice: " + source.getId() + "\n");

        // Llamar al algoritmo de Dijkstra
        Map<String, Object> results = DijkstraEj3.Dijkstra(graph, source);

        // Recuperar distancias, estadísticas y otros datos
        @SuppressWarnings("unchecked")
        TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) results.get("dist");
        long tiempoEjecucion = (long) results.get("tiempo_ejecucion_ns");
        int iteraciones = (int) results.get("iteraciones");
        int actualizaciones = (int) results.get("actualizaciones");
        int verticesProcesados = (int) results.get("vertices_procesados");
        int aristasProcesadas = (int) results.get("aristas_procesadas");

        // Ordenar distancias por valor (de menor a mayor)
        List<Map.Entry<Vertex, Double>> sortedDistances = new ArrayList<>(dist.entrySet());
        sortedDistances.sort(Map.Entry.comparingByValue());

        // Mostrar resultados ordenados
        System.out.printf("Tiempo de ejecución: %d ns\n", tiempoEjecucion);
        System.out.printf("Iteraciones: %d\n", iteraciones);
        System.out.printf("Actualizaciones: %d\n", actualizaciones);
        System.out.println("Vértices procesados: " + verticesProcesados);
        System.out.println("Aristas procesadas: " + aristasProcesadas);
        System.out.println("Distancias desde el vértice de origen:");

        for (Map.Entry<Vertex, Double> entry : sortedDistances) {
            Vertex destination = entry.getKey();
            double distance = entry.getValue();

            if (distance == Double.POSITIVE_INFINITY) {
                System.out.println(" → " + destination.getId() + ": INFINITO");
            } else {
                System.out.printf(" → %s: %.2f\n", destination.getId(), distance);
            }
        }

        scanner.close();
    }
}