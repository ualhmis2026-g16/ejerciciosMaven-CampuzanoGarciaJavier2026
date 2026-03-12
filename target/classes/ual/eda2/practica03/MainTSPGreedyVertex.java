package ual.eda2.practica03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTSPGreedyVertex {
	public static void main(String[] args) {
        // Definir el directorio de entrada para el archivo del grafo
        String directorioEntrada = System.getProperty("user.dir") + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "java" + File.separator +
                "ual" + File.separator +
                "eda2" + File.separator +
                "practica03" + File.separator;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for(int j = 1; j<=13; j++) {
        	// Archivo de entrada del grafo
        	String añadido = j<10 ? "graphTSP0" : "graphTSP";
            String archivoGrafo = directorioEntrada + añadido + j + ".txt";

            try {
                // Cargar el grafo desde el archivo
                Grafo cityGraph = readGraphFromFile(archivoGrafo);
                System.out.println("*********************************");
                //System.out.println("Grafo cargado exitosamente (" + archivoGrafo.split("practica03")[1] + ").");
                List<Vertex> lista = (List<Vertex>) cityGraph.getVertices();
                Vertex startIndex = lista.get(0);
            	System.out.println("Para el archivo: " + archivoGrafo.split("practica03")[1]);
                System.out.println("Número de vértices del grafo: " + lista.size());
                System.out.println("Número de aristas (carreteras) del grafo: " + cityGraph.getNumAristas());
                //System.out.println("Se ha cargado el vértice " + startIndex.toString());
                List<Double> tiempos = new ArrayList<>();
                Solucion tsp = new Solucion(null, 0);
                System.out.println("\nComienza la ejecución...\n");
                for(int i=0; i<10; i++) {
                	System.out.print(1+i + "/10...");
                	long timeInicio = System.nanoTime();
                    tsp = TSPGreedyVertex.tspGreedyVertex(cityGraph, startIndex);
                    long timeFinal = System.nanoTime();
                    long time = timeFinal - timeInicio;
                    tiempos.add((double) time);
                }
                double time = 0;
                for(double a : tiempos)
                	time+=a;
                time/=10;
                System.out.println("\n///////////////////////////////////////////");
                //System.out.println("Camino seguido: " + tsp.imprimirResultados());
            	//System.out.println("Distancia total: " + tsp.getCoste());
                System.out.println("Tiempo de ejecución medio del algoritmo: " + time + "ns");
                
               
            } catch (IOException e) {
                System.err.println("Error leyendo el archivo del grafo: " + e.getMessage());
            }
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
