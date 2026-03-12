package ual.eda2.practica03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTSPBruteForce {
    public static void main(String[] args) {
        // Definir el directorio de entrada para el archivo del grafo
    	String directorioEntrada = System.getProperty("user.dir") + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "java" + File.separator +
                "ual" + File.separator +
                "eda2" + File.separator +
                "practica03" + File.separator;

        // Archivo de entrada del grafo
        String archivoGrafo = directorioEntrada + "graphEDAlandTSP01.txt";

        try {
            // Cargar el grafo desde el archivo
            Grafo cityGraph = readGraphFromFile(archivoGrafo);
            System.out.println("Grafo cargado exitosamente.");
            List<Vertex> lista = (List<Vertex>) cityGraph.getVertices();
            Vertex startIndex = lista.get(1); //Cargamos Almería
            System.out.println("Número de vértices del grafo: " + lista.size());
            System.out.println("Se ha cargado el vértice " + startIndex.toString());
            long timeInicio = System.currentTimeMillis();
            Solucion tsp = TSPBruteForce.TSPFuerzaBruta(cityGraph, startIndex);
            long timeFinal = System.currentTimeMillis();
            long time = timeFinal - timeInicio;
            
        	System.out.println("Camino seguido: " + tsp.imprimirResultados());
        	System.out.println("Distancia total: " + tsp.getCoste());
            System.out.println("Tiempo de ejecución del algoritmo: " + time + "ms");
           
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo del grafo: " + e.getMessage());
        }
    }

    /**
     * Lee un grafo desde un archivo de texto.
     * 
     * @param filePath Ruta del archivo de texto
     * @return Un objeto Graph construido a partir de los datos del archivo
     * @throws IOException Si ocurre un error de entrada/salida
     */
    public static Grafo readGraphFromFile(String filePath) throws IOException {
        Grafo graph = new Grafo();
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

                    // Obtener o crear vértices
                    Vertex source = getOrCreateVertex(verticesMap, graph, sourceId);
                    Vertex dest = getOrCreateVertex(verticesMap, graph, destId);

                    // Añadir las aristas al grafo (suponiendo grafo no dirigido)
                    graph.addEdge(source, dest, distance);
                    graph.addEdge(dest, source, distance); // Elimina esta línea si el grafo es dirigido
                }
            }
        }

        return graph;
    }


    /**
     * Método auxiliar para obtener un vértice existente o crear uno nuevo si es necesario.
     * 
     * @param verticesMap Mapa de identificadores de vértices a objetos Vertex
     * @param graph Grafo donde se añaden los vértices
     * @param id Identificador del vértice
     * @return Un objeto Vertex correspondiente al identificador
     */
    private static Vertex getOrCreateVertex(Map<String, Vertex> verticesMap, Grafo graph, String id) {
        Vertex vertex = verticesMap.get(id);
        if (vertex == null) {
            vertex = new Vertex(id);
            verticesMap.put(id, vertex);
            graph.addVertex(vertex);
        }
        return vertex;
    }
}