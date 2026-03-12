package ual.eda2.practica01;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class ParMasCercanoDyVEj3 {
	
	/*
	 * Comparador de puntos según la coordenada X
	 */
	static Comparator<Poin> compareX = new Comparator<Poin>() {
		@Override
		public int compare(Poin p1, Poin p2) {
			return Double.compare(p1.x, p2.x);
		}
	};
	
	/*
	 * Comparador de puntos según la coordenada Y
	 */
	static Comparator<Poin> compareY = new Comparator<Poin>() {
		@Override
		public int compare(Poin p1, Poin p2) {
			return Double.compare(p1.y, p2.y);
		}
	};

	/**
     * Calcula la distancia euclídea entre un par de puntos dado
     * 
     * @param p1 Primer punto
     * @param p2 Segundo punto
     * @return Distancia euclídea entre los dos puntos
     */
	public static double distancia(Poin p1, Poin p2) {
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) +
						(p1.y - p2.y) * (p1.y - p2.y));
	}
	
	/**
     * Método para encontrar la distancia entre los puntos más cercanos 
     * de una lista haciendo uso del algoritmo de Divide y Vencerás sin mejorar
     * 
     * @param listaPuntos Los puntos a comparar
     * @return Distancia mínima entre los puntos más cercanos de la lista
     */
	public static double parMasCercanoDivideandConquer(ArrayList<Poin> listaPuntos) {
		Collections.sort(listaPuntos, compareX);
		return parMasCercanoDivideandConquer(listaPuntos, 0, listaPuntos.size()-1);
	}
	
	
	/**
     * Método privado recursivo para encontrar el par más cercano, en este caso, sin ninguna mejora.
     * 
     * @param puntos   Lista de puntos ordenados por coordenada X.
     * @param comienzo Índice inicial del array.
     * @param fin      Índice final del array.
     * @return Distancia mínima entre los puntos más cercanos de la lista
     */
	private static double parMasCercanoDivideandConquer(ArrayList<Poin> puntos, int comienzo, int fin) {
		
		//Caso base, fuerza bruta cuando el array de puntos es de tamaño 3 o inferior.
		
		if(fin-comienzo <=3) {
			double minDist = Double.MAX_VALUE;
			double aux;
			for(int i = comienzo; i<=fin; i++) {
				for(int j=i+1; j<=fin; j++) {
					aux = distancia(puntos.get(i), puntos.get(j));
					if(aux<minDist) {
						minDist = aux;
					}
				}
			}
			return minDist;
		}
		else {
			
			//Dividir
			
			int mitad = comienzo + (fin-comienzo)/2;
			double izquierda = parMasCercanoDivideandConquer(puntos, comienzo, mitad);
			double derecha = parMasCercanoDivideandConquer(puntos, mitad+1, fin);
			
			//Combinar
			
			double delta = Math.min(izquierda, derecha);
			double aux;
			for (int i = comienzo; i <= mitad; i++) {
				for (int j = mitad+1; j <= fin; j++) {
	                aux = distancia(puntos.get(i), puntos.get(j));
	                if (aux< delta) {
	                    delta = aux;
	                }
		        }
			}
			return delta;
		}
	}
}
