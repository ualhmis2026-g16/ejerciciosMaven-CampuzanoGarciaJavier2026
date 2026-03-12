package ual.eda2.practica02;

import java.io.*;
import java.util.*;

public class MainEj8EDALandLarge {
	/**
	 * metodo main que se encarga de realizar la ejecucion de DijkstraEj8
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

        // Solicitar al usuario un vértice de origen, destino y opcionalmente un intermedio
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del vértice de origen: ");
        String origenId = scanner.nextLine().trim();

        System.out.print("Introduce el ID del vértice de destino: ");
        String destinoId = scanner.nextLine().trim();

        System.out.print("Introduce el ID del vértice intermedio (opcional, presiona Enter para omitir): ");
        String intermedioId = scanner.nextLine().trim();

        // Verificar si el vértice de origen y destino existen
        Vertex source = vertexMap.get(origenId);
        Vertex destination = vertexMap.get(destinoId);
        Vertex intermedio = intermedioId.isEmpty() ? null : vertexMap.get(intermedioId);

        if (source == null) {
            System.err.println("El vértice de origen no existe en el grafo.");
            return;
        }
        if (destination == null) {
            System.err.println("El vértice de destino no existe en el grafo.");
            return;
        }
        if (intermedioId != null && intermedio == null) {
            System.err.println("El vértice intermedio no existe en el grafo.");
            return;
        }

        // Ejecutar el algoritmo de DijkstraEj8
        TreeMap<String, Object> results;
        double distanciaTotal = 0.0;
        if (intermedio != null) {
            System.out.println("\nCalculando camino mínimo desde " + source.getId() + " pasando por " + intermedio.getId() + " hasta " + destination.getId() + "...\n");
            results = DijkstraEj8.DijkstraIntermedio(graph, source, intermedio, destination);

            @SuppressWarnings("unchecked")
            TreeMap<Vertex, Double> distFuente = (TreeMap<Vertex, Double>) results.get("distFuente");
            @SuppressWarnings("unchecked")
            TreeMap<Vertex, Double> distIntermedio = (TreeMap<Vertex, Double>) results.get("distIntermedio");

            // Verificar que ambos tramos tienen un camino válido
            if (distFuente.get(intermedio) == Double.POSITIVE_INFINITY || distIntermedio.get(destination) == Double.POSITIVE_INFINITY) {
                System.out.println("No existe un camino desde " + source.getId() + " hasta " + destination.getId() + " pasando por " + intermedio.getId());
                return;
            }

            // Calcular la distancia total
            distanciaTotal = distFuente.get(intermedio) + distIntermedio.get(destination);
        } else {
            System.out.println("\nCalculando camino mínimo desde " + source.getId() + " hasta " + destination.getId() + "...\n");
            results = DijkstraEj8.Dijkstra(graph, source);

            @SuppressWarnings("unchecked")
            TreeMap<Vertex, Double> dist = (TreeMap<Vertex, Double>) results.get("dist");

            // Obtener la distancia al destino
            distanciaTotal = dist.get(destination);
            if (distanciaTotal == Double.POSITIVE_INFINITY) {
                System.out.println("No existe un camino desde " + source.getId() + " hasta " + destination.getId());
                return;
            }
        }

        // Reconstruir el camino completo
        List<Vertex> path = new ArrayList<>();
        if (intermedio != null) {
            @SuppressWarnings("unchecked")
            TreeMap<Vertex, Vertex> predFuente = (TreeMap<Vertex, Vertex>) results.get("predFuente");
            @SuppressWarnings("unchecked")
            TreeMap<Vertex, Vertex> predIntermedio = (TreeMap<Vertex, Vertex>) results.get("predIntermedio");

            path.addAll(reconstructPath(predFuente, source, intermedio));
            path.remove(path.size() - 1); // Evitar duplicar el nodo intermedio
            path.addAll(reconstructPath(predIntermedio, intermedio, destination));
        } else {
            @SuppressWarnings("unchecked")
            TreeMap<Vertex, Vertex> pred = (TreeMap<Vertex, Vertex>) results.get("pred");
            path = reconstructPath(pred, source, destination);
        }

        // Mostrar resultados
        System.out.println("Camino mínimo encontrado:");
        System.out.printf("Distancia total: %.2f\n", distanciaTotal);
        System.out.print("Ruta: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getId());
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();

        // Mostrar estadísticas
        long tiempoEjecucion = (long) results.get("tiempo_ejecucion_ns");
        int iteraciones = (int) results.get("iteraciones");
        int actualizaciones = (int) results.get("actualizaciones");
        int verticesProcesados = (int) results.get("vertices_procesados");
        int aristasProcesadas = (int) results.get("aristas_procesadas");

        System.out.println("\nEstadísticas de ejecución:");
        System.out.printf("Tiempo de ejecución: %d ns\n", tiempoEjecucion);
        System.out.printf("Iteraciones: %d\n", iteraciones);
        System.out.printf("Actualizaciones de distancia: %d\n", actualizaciones);
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
        Vertex current = destination;

        // Verificar si el destino es alcanzable
        while (current != null && current != source) {
            path.add(0, current);
            current = pred.get(current);
        }

        if (current == source) {
            path.add(0, source);
        }

        return path;
    }
}