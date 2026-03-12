package ual.eda2.practica02;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainAuxEj5 {

    /**
     * Método principal para probar el algoritmo
     * @param String[] args
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
            Graph cityGraph = DijkstraMainEj1.readGraphFromFile(directorioEntrada + "graphEDAland.txt");
            System.out.println("Grafo cargado exitosamente.");

            // Cargar posiciones para la heurística
            Map<Vertex, double[]> positions = loadPositions(directorioEntrada + "graphEDALandpositions.txt", cityGraph);

            // Mostrar vértices disponibles
            System.out.println("\nVértices disponibles en el grafo:");
            for (Vertex v : cityGraph.getVertices()) {
                System.out.print(v.getId() + " ");
            }
            System.out.println("\n");

            // Crear un mapa para buscar vértices rápidamente por ID
            Map<String, Vertex> vertexMap = new HashMap<>();
            for (Vertex v : cityGraph.getVertices()) {
                vertexMap.put(v.getId(), v);
            }

            // Obtener origen y destino por entrada de usuario
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce vértice origen (ej: Madrid): ");
            String sourceId = scanner.nextLine().trim();

            System.out.print("Introduce vértice destino: ");
            String destId = scanner.nextLine().trim();

            // Buscar vértices origen y destino
            Vertex source = vertexMap.get(sourceId);
            Vertex destination = vertexMap.get(destId);

            // Comprobar si se encontraron ambos vértices
            if (source == null) {
                System.out.println("Vértice origen no encontrado: " + sourceId);
                return;
            }
            if (destination == null) {
                System.out.println("Vértice destino no encontrado: " + destId);
                return;
            }

            // Ejecutar el algoritmo A*
            System.out.println("\nEjecutando algoritmo A* desde " + sourceId + " hasta " + destId + ":");
            Map<String, Object> result = DijkstraEj5.findPath(cityGraph, source, destination, positions);

            // Obtener resultados
            @SuppressWarnings("unchecked")
            List<Vertex> path = (List<Vertex>) result.get("path");
            double distance = (double) result.get("distance");
            boolean pathFound = (boolean) result.get("pathFound");

            // Obtener estadísticas
            long tiempoEjecucion = (long) result.get("tiempo_ejecucion_ns");
            int nodosEvaluados = (int) result.get("nodos_evaluados");
            int nodosGenerados = (int) result.get("nodos_generados");

            // Mostrar estadísticas
            System.out.println("\nEstadísticas de ejecución de A*:");
            System.out.println("Tiempo de ejecución: " + tiempoEjecucion + " nanosegundos");
            System.out.println("Nodos evaluados: " + nodosEvaluados);
            System.out.println("Nodos generados: " + nodosGenerados);

            // Mostrar resultados
            if (pathFound) {
                System.out.println("\nCamino más corto encontrado de " + sourceId + " a " + destId + ":");
                System.out.printf("Distancia: %.2f\n", distance);

                System.out.print("Ruta: ");
                for (int i = 0; i < path.size(); i++) {
                    System.out.print(path.get(i).getId());
                    if (i < path.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();
            } else {
                System.out.println("\nNo se encontró camino de " + sourceId + " a " + destId);
            }

            scanner.close();

        } catch (IOException e) {
            System.err.println("Error al leer el archivo del grafo: " + e.getMessage());
        }
    }

    /**
     * Carga las posiciones de los vértices desde un archivo
     * para usar en la función heurística.
     * Formato del archivo:
     * - Líneas iniciales con número de vértices y aristas (ignoradas)
     * - Lista de posiciones: id_vertice x y
     * - Lista de aristas al final (ignoradas)
     * @param String filePath
     * @param Graph graph
     * @return Map<Vertex, double[]>
     */
    private static Map<Vertex, double[]> loadPositions(String filePath, Graph graph) {
        Map<Vertex, double[]> positions = new HashMap<>();

        try {
            Scanner scanner = new Scanner(new File(filePath));
            
            // Ignorar las dos primeras líneas (número de vértices y aristas)
            if (scanner.hasNextLine()) scanner.nextLine(); // Ignorar línea 1
            if (scanner.hasNextLine()) scanner.nextLine(); // Ignorar línea 2

            // Leer posiciones de los vértices
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Detener la lectura al encontrar la lista de aristas
                if (line.matches("^[A-Za-z]+\\s+[A-Za-z]+\\s+\\d+$")) {
                    break; // Línea coincide con el formato de una arista
                }

                // Dividir la línea en partes
                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Línea inválida en el archivo de posiciones: " + line);
                    continue; // Saltar líneas mal formateadas
                }

                String id = parts[0];
                try {
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);

                    // Buscar el vértice correspondiente en el grafo
                    Vertex vertex = null;
                    for (Vertex v : graph.getVertices()) {
                        if (v.getId().equals(id)) {
                            vertex = v;
                            break;
                        }
                    }

                    if (vertex == null) {
                        System.out.println("Vértice desconocido en el archivo de posiciones: " + id);
                        continue;
                    }

                    // Asignar las coordenadas al vértice
                    positions.put(vertex, new double[]{x, y});
                } catch (NumberFormatException e) {
                    System.out.println("Error al analizar coordenadas en la línea: " + line);
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.err.println("Error al cargar posiciones: " + e.getMessage());
            generateRandomPositions(graph, positions);
        }

        return positions;
    }

    /**
     * Genera posiciones aleatorias para los vértices en caso de que el archivo de posiciones no exista o sea inválido.
     * @param Graph graph
     * @param Map<Vertex, double[]> positions
     */
    private static void generateRandomPositions(Graph graph, Map<Vertex, double[]> positions) {
        Random random = new Random(42); // Semilla fija para reproducibilidad
        for (Vertex v : graph.getVertices()) {
            double x = random.nextDouble() * 1000;
            double y = random.nextDouble() * 1000;
            positions.put(v, new double[]{x, y});
        }
    }
}