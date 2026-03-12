package ual.eda2.practica01;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class ParMasCercanoDyVEj6 {
	
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
     * de una lista haciendo uso del algoritmo de Divide y Vencerás, con la última mejora:
     * Se crean 2 copias del array de puntos: Px, ordenado por la coordenada X, y Py, ordenado por la coordenada Y.
     * Tras esto, cada array se divide en 2: Plx, Prx, Ply, Pry. Estos 4 arrays, básicamente dividen a los arrays anteriores
     * según su posición con respecto a la línea vertical que divide a todos los puntos por la mitad.
     * De esta manera, se van dividiendo en 4 los arrays de puntos que entran, para luego seguir el mismo procedimiento con la franja.
     * 
     * @param listaPuntos Los puntos a comparar
     * @return Distancia mínima entre los puntos más cercanos de la lista
     */
	public static double parMasCercanoDivideandConquer(ArrayList<Poin> listaPuntos) {
		ArrayList<Poin> puntosejey = new ArrayList<>(listaPuntos);
		Collections.sort(puntosejey, compareY);
	    Collections.sort(listaPuntos, compareX);
	    return parMasCercanoDivideandConquer(listaPuntos, puntosejey, 0, listaPuntos.size() - 1);
	}

	/**
     * Parte privada del recursivo.
     * 
     * @param Px       Lista de puntos ordenados por la coordenada X.
     * @param Py       Lista de puntos ordenados por la coordenada Y.
     * @param comienzo Índice inicial del subarray a considerar.
     * @param fin      Índice final del subarray a considerar.
     * @return Distancia mínima entre dos puntos dentro del subarray.
     */
	private static double parMasCercanoDivideandConquer(ArrayList<Poin> Px, ArrayList<Poin> Py, int comienzo, int fin) {
		
		//Caso base, fuerza bruta cuando el array de puntos es de tamaño 3 o inferior.
		
	    if (fin - comienzo <= 3) {
	    	double minDist = Double.MAX_VALUE;
			double aux;
			for(int i = comienzo; i<=fin; i++) {
				for(int j=i+1; j<=fin; j++) {
					aux = distancia(Px.get(i), Px.get(j));
					if(aux<minDist) {
						minDist = aux;
					}
				}
			}
			return minDist;
	    } else {
	    	
	    	//Dividir
	    	
	        int mitad = comienzo + (fin - comienzo) / 2;
	        double lineaX = Px.get(mitad).x;
	        
	        //Combinar
	        
	        ArrayList<Poin> plx = new ArrayList<>();
	        ArrayList<Poin> prx = new ArrayList<>();
	        ArrayList<Poin> ply = new ArrayList<>();
	        ArrayList<Poin> pry = new ArrayList<>();
	        
	        for (int i = comienzo; i <= fin; i++) {
	            if (i <= mitad) {
	                plx.add(Px.get(i));
	            } else {
	                prx.add(Px.get(i));
	            }
	        }
	        
	        for (Poin p : Py) {
	            if (p.x <= lineaX) {
	                ply.add(p);
	            } else {
	                pry.add(p);
	            }
	        }
	        
	        double dl = parMasCercanoDivideandConquer(plx, ply, 0, plx.size() - 1);
	        double dr = parMasCercanoDivideandConquer(prx, pry, 0, prx.size() - 1);

	        double delta = Math.min(dl, dr);
	        
	        ArrayList<Poin> franja = new ArrayList<>();
	        for (Poin p : Py) {
	            if (Math.abs(p.x - lineaX) < delta) {
	                franja.add(p);
	            }
	        }
	        
	        double aux;
	        for (int i = 0; i < franja.size(); i++) {
	            for (int j = i + 1; j < franja.size() && j<=i+7; j++) {
	                aux = distancia(franja.get(i), franja.get(j));
	                if (aux < delta) {
	                    delta = aux;
	                }
	            }
	        }
	        
	        return delta;
	    }
	}
}