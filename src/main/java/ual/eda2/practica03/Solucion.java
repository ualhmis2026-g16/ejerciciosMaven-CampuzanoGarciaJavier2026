package ual.eda2.practica03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Solucion {
	

	private List<Vertex> camino = new ArrayList<>();
	private double minCost;

	public Solucion(List<Vertex> vertexes, double minTourCost) {
		camino = vertexes;
		minCost = minTourCost;
	}
	
	public List<Vertex> getRecorrido() {
	    return camino;
	}

	
	public String imprimirResultados() {
		String result = "";
		for(Vertex v : camino) {
			result += v.getId();
			if(camino.indexOf(v)!=camino.size()-1) result += " → ";
		}
		return result;
	}
	
	public double getCoste() {
		return minCost;
	}

}
