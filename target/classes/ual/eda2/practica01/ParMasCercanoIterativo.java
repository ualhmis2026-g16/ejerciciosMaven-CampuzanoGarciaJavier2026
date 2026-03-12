package ual.eda2.practica01;

import java.util.ArrayList;

/**
 * La clase {@code ParMasCercanoIterativo} contiene métodos para resolver el problema del 
 * "par más cercano de puntos" de manera iterativa.
 * Esta implementación recorre todos los pares de puntos en el conjunto dado {@code P} y calcula
 * la distancia mínima entre ellos.
 * 
 * <p>El método principal, {@link #iterativo(ArrayList)}, encuentra el par de puntos con la menor 
 * distancia mediante una comparación directa de todos los puntos en la lista.</p>
 * 
 * <p>El método {@link #distancia(Poin, Poin)} se utiliza para calcular la distancia euclidiana entre 
 * dos puntos dados.</p>
 */
public class ParMasCercanoIterativo {
	
	/**
     * Calcula la distancia mínima entre todos los pares de puntos en el conjunto dado {@code P}.
     * 
     * <p>Este método recorre todos los pares de puntos en la lista {@code P}, calcula la distancia 
     * entre cada par y actualiza el valor mínimo de distancia encontrado.</p>
     * 
     * @param P La lista de puntos a evaluar.
     * @return La distancia mínima entre los puntos en la lista {@code P}.
     */
	public static double iterativo(ArrayList<Poin> P) {
		int size = P.size();
		double min = Double.MAX_VALUE;  //Se pone el mayor valor, ya que vamos a ir iterando y comparando.
		double currMin = 0;

		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if(i!=j) {
					currMin = distancia(P.get(i), P.get(j));
					if(currMin<min) {
						min = currMin;
					}
				}
			}
		}
		return min;
	}


	 /**
     * Calcula la distancia euclidiana entre dos puntos {@code Poin}.
     * 
     * <p>Este método utiliza la fórmula de distancia euclidiana en el plano 2D para calcular la distancia 
     * entre los puntos {@code p1} y {@code p2}.</p>
     * 
     * @param p1 El primer punto.
     * @param p2 El segundo punto.
     * @return La distancia entre los puntos {@code p1} y {@code p2}.
     */
	public static double distancia(Poin p1, Poin p2) {
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) +
				(p1.y - p2.y) * (p1.y - p2.y));
	}
}
