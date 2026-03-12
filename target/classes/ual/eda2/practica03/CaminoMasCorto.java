package ual.eda2.practica03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * La clase CaminoMasCorto proporciona métodos para encontrar el camino 
 * más corto (en términos de peso total) en un grafo. Utiliza 
 * la clase CaminosSimples para obtener todos los caminos simples (circuitos) 
 * a partir de un vértice de origen y luego selecciona aquel de menor peso.
 */
public class CaminoMasCorto {

    /**
     * Obtiene el camino simple de menor peso a partir del vértice de origen en el grafo dado.
     * Se recorre el conjunto de caminos simples obtenidos y se selecciona el camino con 
     * la menor suma de pesos.
     * 
     * @param grafo  Grafo en el cual se busca el camino.
     * @param source Vértice de origen desde el que se inician los caminos.
     * @return El ArrayList de vértices que representa el camino más corto.
     */
    public static ArrayList<Vertex> shortestPath(Graph grafo, Vertex source) {
        ArrayList<Vertex> caminoElegido = new ArrayList<>();
        double peso = Double.POSITIVE_INFINITY;
        Map<ArrayList<Vertex>, Double> conjuntoCaminos = CaminosSimples.buscarCaminosSimples(grafo, source);
        
        // Iterar sobre cada camino simple y su peso para seleccionar el de menor peso
        for (Entry<ArrayList<Vertex>, Double> entry : conjuntoCaminos.entrySet()) {
            if (entry.getValue() < peso) {
                caminoElegido = entry.getKey();
                peso = entry.getValue();
            }
        }
        
        System.out.println("El camino elegido es: " + caminoElegido.toString());
        System.out.println();
        System.out.println("El peso del camino elegido es: " + peso);
        return caminoElegido;
    }

    /**
     * Método principal para ejecutar el programa.
     * Se carga un grafo desde un archivo de texto, se imprime un mensaje si el grafo se carga correctamente 
     * y se llama al método shortestPath para obtener e imprimir el camino más corto iniciando desde un vértice específico.
     * 
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        String directorioEntrada = System.getProperty("user.dir") + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "java" + File.separator +
                "ual" + File.separator +
                "eda2" + File.separator +
                "practica03" + File.separator;

        // Cargar el grafo
        try {
            Graph cityGraph = readGraphFromFile(directorioEntrada + "graphEDALandTSP01.txt");
            System.out.println("Grafo cargado exitosamente.");
            
            // Obtener el camino más corto iniciando en el vértice "Almeria"
            shortestPath(cityGraph, new Vertex("Almeria"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee un grafo desde un archivo de texto.
     * El archivo debe tener una línea por cada arista con tres elementos separados por espacios:
     * el identificador del vértice origen, el identificador del vértice destino y el peso (distancia) de la arista.
     * 
     * @param filePath Ruta del archivo de texto.
     * @return Un objeto Graph construido a partir de los datos del archivo.
     * @throws IOException Si ocurre un error de entrada/salida.
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

                    // Obtener o crear vértices
                    Vertex source = getOrCreateVertex(verticesMap, graph, sourceId);
                    Vertex dest = getOrCreateVertex(verticesMap, graph, destId);

                    // Añadir la arista al grafo (suponiendo grafo no dirigido)
                    graph.addEdge(source, dest, distance);
                    graph.addEdge(dest, source, distance); // Eliminar esta línea si el grafo es dirigido
                }
            }
        }

        return graph;
    }

    /**
     * Método auxiliar que obtiene un vértice existente o crea uno nuevo si es necesario.
     * 
     * @param verticesMap Mapa de identificadores de vértices a objetos Vertex.
     * @param graph       Grafo en el cual se añaden los vértices.
     * @param id          Identificador del vértice.
     * @return Un objeto Vertex correspondiente al identificador dado.
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
}