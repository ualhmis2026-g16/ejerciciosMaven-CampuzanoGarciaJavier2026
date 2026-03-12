package ual.eda2.practica03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NuevaRed {
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
            Grafo edaland = readGraphFromFile(archivoGrafo);
            System.out.println("Grafo cargado exitosamente.");
            
            System.out.println("/*/*/*/*/*/*/*CASO BASE*\\*\\*\\*\\*\\");
            List<Vertex> lista = (List<Vertex>) edaland.getVertices();
            Vertex startIndex = lista.get(1);
            System.out.println("Número de vértices del grafo: " + lista.size());
            System.out.println("Número de aristas (carreteras) del grafo: " + edaland.getNumAristas());
            System.out.println("Se ha cargado el vértice " + startIndex.toString());
            long timeInicio = System.currentTimeMillis();
            Solucion tsp = TSPIterative.tspIterative(edaland, startIndex);
            long timeFinal = System.currentTimeMillis();
            long time = timeFinal - timeInicio;
            
        	System.out.println("Camino seguido: " + tsp.imprimirResultados());
        	System.out.println("Distancia total: " + tsp.getCoste());
            System.out.println("Tiempo de ejecución del algoritmo: " + time + "ms");
            
            //Añadir vértices y aristas al grafo:
            // Caso 1
            edaland.addVertex(new Vertex("Malaga"));
            edaland.addEdge(new Vertex("Almeria"), new Vertex("Malaga"), 202);
            edaland.addEdge(new Vertex("Granada"), new Vertex("Malaga"), 129);
            edaland.addEdge(new Vertex("Malaga"), new Vertex("Cadiz"), 233);
            
            System.out.println("/*/*/*/*/*/*/*+ MALAGA*\\*\\*\\*\\*\\");
            lista = (List<Vertex>) edaland.getVertices();
            startIndex = lista.get(1);
            System.out.println("Número de vértices del grafo: " + lista.size());
            System.out.println("Número de aristas (carreteras) del grafo: " + edaland.getNumAristas());
            System.out.println("Se ha cargado el vértice " + startIndex.toString());
            timeInicio = System.currentTimeMillis();
            tsp = TSPIterative.tspIterative(edaland, startIndex);
            //Solucion tsp = TSPIterative.tspIterative(edaland, startIndex);
            timeFinal = System.currentTimeMillis();
            time = timeFinal - timeInicio;
        	System.out.println("Camino seguido: " + tsp.imprimirResultados());
        	System.out.println("Distancia total: " + tsp.getCoste());
            System.out.println("Tiempo de ejecución del algoritmo: " + time + "ms");
            
            
            
            // Caso 2
            edaland.addVertex(new Vertex("Salamanca"));
            edaland.addEdge(new Vertex("Salamanca"), new Vertex("Caceres"), 201);
            edaland.addEdge(new Vertex("Salamanca"), new Vertex("Valladolid"), 121);
            edaland.addEdge(new Vertex("Salamanca"), new Vertex("Vigo"), 415);

            System.out.println("/*/*/*/*/*/*/*+ SALAMANCA*\\*\\*\\*\\*\\");
            lista = (List<Vertex>) edaland.getVertices();
            startIndex = lista.get(1);
            System.out.println("Número de vértices del grafo: " + lista.size());
            System.out.println("Número de aristas (carreteras) del grafo: " + edaland.getNumAristas());
            System.out.println("Se ha cargado el vértice " + startIndex.toString());
            timeInicio = System.currentTimeMillis();
            tsp = TSPIterative.tspIterative(edaland, startIndex);
            //Solucion tsp = TSPIterative.tspIterative(edaland, startIndex);
            timeFinal = System.currentTimeMillis();
            time = timeFinal - timeInicio;
        	System.out.println("Camino seguido: " + tsp.imprimirResultados());
        	System.out.println("Distancia total: " + tsp.getCoste());
            System.out.println("Tiempo de ejecución del algoritmo: " + time + "ms");
            
            // Caso 3
            edaland.addVertex(new Vertex("Palencia"));
            edaland.addEdge(new Vertex("Valladolid"), new Vertex("Palencia"), 58);
            edaland.addEdge(new Vertex("Palencia"), new Vertex("Bilbao"), 244);
            edaland.addEdge(new Vertex("Palencia"), new Vertex("Oviedo"), 253);

            System.out.println("/*/*/*/*/*/*/*+ PALENCIA*\\*\\*\\*\\*\\");
            lista = (List<Vertex>) edaland.getVertices();
            startIndex = lista.get(1);
            System.out.println("Número de vértices del grafo: " + lista.size());
            System.out.println("Número de aristas (carreteras) del grafo: " + edaland.getNumAristas());
            System.out.println("Se ha cargado el vértice " + startIndex.toString());
            timeInicio = System.currentTimeMillis();
            tsp = TSPIterative.tspIterative(edaland, startIndex);
            //Solucion tsp = TSPIterative.tspIterative(edaland, startIndex);
            timeFinal = System.currentTimeMillis();
            time = timeFinal - timeInicio;
        	System.out.println("Camino seguido: " + tsp.imprimirResultados());
        	System.out.println("Distancia total: " + tsp.getCoste());
            System.out.println("Tiempo de ejecución del algoritmo: " + time + "ms");
            
            // Caso 4
            edaland.addVertex(new Vertex("Soria"));
            edaland.addEdge(new Vertex("Madrid"), new Vertex("Soria"), 236);
            edaland.addEdge(new Vertex("Soria"), new Vertex("Zaragoza"), 159);
            edaland.addEdge(new Vertex("Soria"), new Vertex("Bilbao"), 229);
            
            System.out.println("/*/*/*/*/*/*/*+ SORIA*\\*\\*\\*\\*\\");
            lista = (List<Vertex>) edaland.getVertices();
            startIndex = lista.get(1);
            System.out.println("Número de vértices del grafo: " + lista.size());
            System.out.println("Número de aristas (carreteras) del grafo: " + edaland.getNumAristas());
            System.out.println("Se ha cargado el vértice " + startIndex.toString());
            timeInicio = System.currentTimeMillis();
            tsp = TSPIterative.tspIterative(edaland, startIndex);
            //Solucion tsp = TSPIterative.tspIterative(edaland, startIndex);
            timeFinal = System.currentTimeMillis();
            time = timeFinal - timeInicio;
        	System.out.println("Camino seguido: " + tsp.imprimirResultados());
        	System.out.println("Distancia total: " + tsp.getCoste());
            System.out.println("Tiempo de ejecución del algoritmo: " + time + "ms");
            
            // Caso 5
            edaland.addVertex(new Vertex("Teruel"));
            edaland.addEdge(new Vertex("Madrid"), new Vertex("Teruel"), 310);
            edaland.addEdge(new Vertex("Teruel"), new Vertex("Zaragoza"), 171);
            edaland.addEdge(new Vertex("Teruel"), new Vertex("Valencia"), 144);

            System.out.println("/*/*/*/*/*/*/*+ TERUEL*\\*\\*\\*\\*\\");
            lista = (List<Vertex>) edaland.getVertices();
            startIndex = lista.get(1);
            System.out.println("Número de vértices del grafo: " + lista.size());
            System.out.println("Número de aristas (carreteras) del grafo: " + edaland.getNumAristas());
            System.out.println("Se ha cargado el vértice " + startIndex.toString());
            timeInicio = System.currentTimeMillis();
            tsp = TSPIterative.tspIterative(edaland, startIndex);
            //Solucion tsp = TSPIterative.tspIterative(edaland, startIndex);
            timeFinal = System.currentTimeMillis();
            time = timeFinal - timeInicio;
        	System.out.println("Camino seguido: " + tsp.imprimirResultados());
        	System.out.println("Distancia total: " + tsp.getCoste());
            System.out.println("Tiempo de ejecución del algoritmo: " + time + "ms");
            
            // Caso 6
            edaland.addVertex(new Vertex("Cordoba"));
            edaland.addEdge(new Vertex("Sevilla"), new Vertex("Cordoba"), 145);
            edaland.addEdge(new Vertex("Jaen"), new Vertex("Cordoba"), 108);
            edaland.addEdge(new Vertex("Huelva"), new Vertex("Cordoba"), 243);
            edaland.addEdge(new Vertex("Cordoba"), new Vertex("Badajoz"), 264);
            
            System.out.println("/*/*/*/*/*/*/*+ CORDOBA*\\*\\*\\*\\*\\");
            lista = (List<Vertex>) edaland.getVertices();
            startIndex = lista.get(1);
            System.out.println("Número de vértices del grafo: " + lista.size());
            System.out.println("Número de aristas (carreteras) del grafo: " + edaland.getNumAristas());
            System.out.println("Se ha cargado el vértice " + startIndex.toString());
            timeInicio = System.currentTimeMillis();
            tsp = TSPIterative.tspIterative(edaland, startIndex);
            //Solucion tsp = TSPIterative.tspIterative(edaland, startIndex);
            timeFinal = System.currentTimeMillis();
            time = timeFinal - timeInicio;
        	System.out.println("Camino seguido: " + tsp.imprimirResultados());
        	System.out.println("Distancia total: " + tsp.getCoste());
            System.out.println("Tiempo de ejecución del algoritmo: " + time + "ms");
            
            // Caso 7
            edaland.addVertex(new Vertex("Lugo"));
            edaland.addEdge(new Vertex("Lugo"), new Vertex("Corunya"), 101);
            edaland.addEdge(new Vertex("Lugo"), new Vertex("Valladolid"), 353);
            edaland.addEdge(new Vertex("Lugo"), new Vertex("Oviedo"), 252);
            
            System.out.println("/*/*/*/*/*/*/*+ LUGO (FINAL)*\\*\\*\\*\\*\\");
            lista = (List<Vertex>) edaland.getVertices();
            startIndex = lista.get(1);
            System.out.println("Número de vértices del grafo: " + lista.size());
            System.out.println("Número de aristas (carreteras) del grafo: " + edaland.getNumAristas());
            System.out.println("Se ha cargado el vértice " + startIndex.toString());
            timeInicio = System.currentTimeMillis();
            tsp = TSPIterative.tspIterative(edaland, startIndex);
            //Solucion tsp = TSPIterative.tspIterative(edaland, startIndex);
            timeFinal = System.currentTimeMillis();
            time = timeFinal - timeInicio;
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
