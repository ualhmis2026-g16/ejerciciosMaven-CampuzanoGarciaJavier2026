package ual.eda2.practica01;

import java.io.File;
/**
* La clase {@code Poin} representa un punto en el plano 2D con una coordenada {@code x} y {@code y},
* así como un identificador único {@code id}.
* Esta clase es útil para almacenar información sobre puntos en un plano cartesiano y asociarles un identificador.
* 
* <p>La clase también sobrescribe el método {@link #toString()} para proporcionar una representación
* legible del punto.</p>
*/
public class Poin {
	 /**
     * Coordenada en el eje X del punto.
     */
	public Double x;
	
	 /**
     * Identificador único del punto.
     */
	public int id;
	
	 /**
     * Coordenada en el eje Y del punto.
     */
	public Double y;
 
	 /**
     * Constructor que inicializa un nuevo punto con un identificador {@code id}, 
     * una coordenada {@code x} y una coordenada {@code y}.
     *
     * @param id El identificador único del punto.
     * @param x La coordenada X del punto.
     * @param y La coordenada Y del punto.
     */
	Poin(int id, Double x, Double y) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	 /**
     * Devuelve una representación en cadena del objeto {@code Poin}.
     * La representación incluye el valor de las coordenadas {@code x} y {@code y}.
     *
     * @return Una cadena que representa al punto con las coordenadas {@code x} y {@code y}.
     */
	@Override
	public String toString() {
		return "punto: x=" + x + ", y=" + y;
		
	}
}