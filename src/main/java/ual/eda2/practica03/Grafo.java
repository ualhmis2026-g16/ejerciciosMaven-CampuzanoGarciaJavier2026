package ual.eda2.practica03;

import java.util.Map.Entry;


import java.util.*;

public class Grafo {
	 private TreeMap<Vertex, TreeMap<Vertex, Double>> adjacencyMap;
	 private ArrayList<Vertex> vertexList;
	 private int numAristas;
	    
	    /**
	     * Constructor por defecto
	     */
	    public Grafo() {
	        this.adjacencyMap = new TreeMap<>();
	        vertexList = new ArrayList<>();
	    }
	    
	    /**
	     * Metodo que anade un vertice al grafo
	     * @param v
	     */
	    public void addVertex(Vertex v) {
	        if (!adjacencyMap.containsKey(v))
	            adjacencyMap.put(v, new TreeMap<>());
	        
	        if(!vertexList.contains(v)) {
	        	vertexList.add(v);
	        	vertexList.sort(null);
	        }
	        
	    }
	    
	    
	    public double[][] mapaAMatriz() {
	        int n = vertexList.size();
	        double[][] result = new double[n][n];
	        
	        // Inicializar la matriz con valores infinitos
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                result[i][j] = Double.POSITIVE_INFINITY;
	            }
	        }
	        
	        // Rellenar la matriz usando vertexList para mantener el orden consistente
	        for (int i = 0; i < n; i++) {
	            Vertex origen = vertexList.get(i);
	            for (int j = 0; j < n; j++) {
	                Vertex destino = vertexList.get(j);
	                TreeMap<Vertex, Double> aux = adjacencyMap.get(origen);
	                if (aux !=null) {
		                Double aux2 = aux.get(destino);
		                if(aux2!=null)
		                	result[i][j] = adjacencyMap.get(origen).get(destino);
	                }
	            }
	        }
	        
	        return result;
	    }
	    
	    /**
	     * Metodo que anade una arista entre dos vertices
	     * @param source
	     * @param destination
	     * @param weight
	     */
	    public void addEdge(Vertex source, Vertex destination, double weight) {
	        // Add vertices if they don't exist
	        addVertex(source);
	        addVertex(destination);
	        if(!adjacencyMap.get(destination).containsKey(source) && !adjacencyMap.get(source).containsKey(destination))
	        	numAristas++;
	        // Add the edge with weight
	        adjacencyMap.get(source).put(destination, weight);
	        adjacencyMap.get(destination).put(source, weight);
	        
	    }
	    
	    /**
	     * Metodo que devuelve todos los vertices del grafo
	     * @return Collection<Vertex>
	     */
	    public Collection<Vertex> getVertices() {
	        return vertexList;
	    }
	    
	    /**
	     * Metodo que verifica si dos vertices son adyacentes
	     * @param Vertex v1
	     * @param Vertex v2
	     * @return boolean adyacentes
	     */
	    public boolean adyacentes(Vertex v1, Vertex v2) {
	    	TreeMap<Vertex, Double> aux = adjacencyMap.get(v1);
	        if (aux==null) {
	            return false;
	        }
	        Double aux2 = aux.get(v2);
	        return aux2!=null;
	    }
	    
	    /**
	     * Metodo que devuelve el peso entre dos vertices
	     * @param Vertex v1
	     * @param Vertex v2
	     * @return double peso
	     */
	    public double peso(Vertex v1, Vertex v2) {
	        if (!adyacentes(v1, v2)) {
	            return Double.POSITIVE_INFINITY;
	        }
	        return adjacencyMap.get(v1).get(v2);
	    }
	    
	    /**
	     * Metodo que devuelve el peso entre dos vertices
	     * @param Vertex v1
	     * @param Vertex v2
	     * @return double
	     */
	    public double weight(Vertex v1, Vertex v2) {
	        return peso(v1, v2);
	    }
	    
	    /**
	     * Metodo para imprimir el grafo.
	     */
	    public void printGraph() {
	        for (Vertex v : adjacencyMap.keySet()) {
	            System.out.print("Vertex " + v + " connects to: ");
	            for (Map.Entry<Vertex, Double> neighbor : adjacencyMap.get(v).entrySet()) {
	                System.out.print(neighbor.getKey() + " (weight: " + neighbor.getValue() + ") ");
	            }
	            System.out.println();
	        }
	    }

		public int getVertexIndex(Vertex v) {
			return vertexList.indexOf(v);
		}
		
		/**
	     * Metodo que devuelve el número de vértices del grafo
	     * @return int tamaño
	     */
	    public int size() {
	    	return vertexList.size();
	    }
		
	    /**
	     * Metodo que devuelve una lista de vértices, basándose en un array de índices pasados por parámetro
	     * @param array el array de índices
	     * @return List<Vertex>
	     */
		public List<Vertex> obtenerListaVertices(Integer[] array) {
			ArrayList<Vertex> result = new ArrayList<>();
			for(Integer i : array) {
				result.add(vertexList.get(i));
			}
			return result;
		}
		
		public List<Vertex> getVecinos(Vertex v1){
			List<Vertex> result = new ArrayList<>();
			for(Vertex v2 : vertexList)
				if(v1!=v2 && adyacentes(v1, v2))
					result.add(v2);
			return result;
		}

		public int getNumAristas() {
			return numAristas;
		}
}