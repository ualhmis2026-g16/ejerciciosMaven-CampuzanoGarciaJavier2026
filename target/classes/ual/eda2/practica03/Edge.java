package ual.eda2.practica03;

public class Edge implements Comparable {
	Vertex v1, v2;
	double weight;

	public Edge(Vertex v, Vertex u, double peso) {
		v1 = v;
		v2 = u;
		weight = peso;
		// TODO Auto-generated constructor stub
	}

	public Vertex getSource() {
		return v1;
	}

	public Vertex getDestination() {
		return v2;
	}
	
	public double getPeso() {
		return weight;
	}

	@Override
	public int compareTo(Object o) {
		Edge other = (Edge)o;
		if(this.weight>other.getPeso()) return 1;
		return this.weight== other.getPeso() ? 0 : 1;
	}

}
