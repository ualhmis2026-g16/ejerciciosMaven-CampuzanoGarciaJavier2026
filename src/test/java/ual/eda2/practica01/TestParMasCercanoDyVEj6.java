package ual.eda2.practica01;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestParMasCercanoDyVEj6 {
	
	public static boolean load(String filename, ArrayList<Poin> poinList){
		Scanner scan = null;
		if(poinList == null) return false;
		try{
			scan = new Scanner(new File(filename));
		}catch(Exception e){
			return false;
		}
		while(scan.hasNextLine()){
			String punto[] = scan.nextLine().split("\t");
			poinList.add(new Poin(Integer.parseInt(punto[0]), Double.parseDouble(punto[1].replace(",", ".")), Double.parseDouble(punto[2].replace(",", "."))));
			
		}
		return true;
	}

	@Test
	@Order(0)
	public void barras() {
		System.out.println("/////////////////////////////////////////////");
	}
	
	String directorioEntrada = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "main" + File.separator +
            "java" + File.separator +
	         "ual" + File.separator +
      		 "eda2" + File.separator +
      		 "practica01" + File.separator;
	
	@Test
	public void parMasCercanoDyVSpain() {
		ArrayList<Long> arr = new ArrayList<>();
		for(int i=0; i<7; i++) {
			ArrayList<Poin> points = new ArrayList<>();
			load(directorioEntrada + "Spain.txt", points);
			long timeInicio = System.currentTimeMillis();
			Assertions.assertEquals(ParMasCercanoDyVEj6.parMasCercanoDivideandConquer(points), 0.001208304597360537);
			long timeFinal = System.currentTimeMillis();
			long time = timeFinal-timeInicio;
			arr.add(time);
		}
		arr.sort(null);
		System.out.println("Tiempo del algoritmo DyV Ejercicio 6 con Spain: " + arr.getFirst());
	}
	
	@Test
	public void parMasCercanoDyVEdaLand() {
		ArrayList<Long> arr = new ArrayList<>();
		for(int i=0; i<7; i++) {
			ArrayList<Poin> points = new ArrayList<>();
			load(directorioEntrada + "EDALand.txt", points);
			long timeInicio = System.currentTimeMillis();
			Assertions.assertEquals(ParMasCercanoDyVEj6.parMasCercanoDivideandConquer(points), 0.6197661483915995);
			long timeFinal = System.currentTimeMillis();
			long time = timeFinal-timeInicio;
			arr.add(time);
		}
		arr.sort(null);
		System.out.println("Tiempo del algoritmo DyV Ejercicio 6 con EdaLand: " + arr.getFirst());
	}
	
	@Test
	public void parMasCercanoDyVNAcl() {
		ArrayList<Long> arr = new ArrayList<>();
		for(int i=0; i<7; i++) {
			ArrayList<Poin> points = new ArrayList<>();
			load(directorioEntrada + "NAclpoint.txt", points);
			long timeInicio = System.currentTimeMillis();
			Assertions.assertEquals(ParMasCercanoDyVEj6.parMasCercanoDivideandConquer(points), 3.482814953449257E-4);
			long timeFinal = System.currentTimeMillis();
			long time = timeFinal-timeInicio;
			arr.add(time);
		}
		arr.sort(null);
		System.out.println("Tiempo del algoritmo DyV Ejercicio 6 con NAcl: " + arr.getFirst());
	}
	
	@Test
	public void parMasCercanoDyVNApp() {
		ArrayList<Long> arr = new ArrayList<>();
		for(int i=0; i<7; i++) {
			ArrayList<Poin> points = new ArrayList<>();
			load(directorioEntrada + "NApppoint.txt", points);
			long timeInicio = System.currentTimeMillis();
			Assertions.assertEquals(ParMasCercanoDyVEj6.parMasCercanoDivideandConquer(points), 3.3015148037532826E-4);
			long timeFinal = System.currentTimeMillis();
			long time = timeFinal-timeInicio;
			arr.add(time);
		}
		arr.sort(null);
		System.out.println("Tiempo del algoritmo DyV Ejercicio 6 NApp: " + arr.get(0));
		
	}
	
	@Test
	public void parMasCercanoDyVNArd() {
		ArrayList<Long> arr = new ArrayList<>();
		for(int i=0; i<7; i++) {
			ArrayList<Poin> points = new ArrayList<>();
			load(directorioEntrada + "NArdpoint.txt", points);
			long timeInicio = System.currentTimeMillis();
			Assertions.assertEquals(ParMasCercanoDyVEj6.parMasCercanoDivideandConquer(points), 1.600000000010482E-4);
			long timeFinal = System.currentTimeMillis();
			long time = timeFinal-timeInicio;
			arr.add(time);
		}
		arr.sort(null);
		System.out.println("Tiempo del algoritmo DyV Ejercicio 6 NArd: " + arr.getFirst());
	}
	
	@Test
	public void parMasCercanoDyVNArr() {
		ArrayList<Long> arr = new ArrayList<>();
		for(int i=0; i<7; i++) {
			ArrayList<Poin> points = new ArrayList<>();
			boolean bool = load(directorioEntrada + "NArrpoint.txt", points);
			long timeInicio = System.currentTimeMillis();
			//System.out.println("DyV NArr: " + ParMasCercanoDyVEj6.parMasCercanoDivideandConquer(points));
			Assertions.assertEquals(ParMasCercanoDyVEj6.parMasCercanoDivideandConquer(points), 1.0000000003174137E-5);
			long timeFinal = System.currentTimeMillis();
			long time = timeFinal-timeInicio;
			arr.add(time);
		}
		arr.sort(null);
		System.out.println("Tiempo del algoritmo DyV Ejercicio 6 NArr: " + arr.getFirst());
	}
}