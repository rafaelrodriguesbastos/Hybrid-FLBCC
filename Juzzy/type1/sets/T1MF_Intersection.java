/*  
 * Created on 10 October 2012, 15:57
 *
 * Author: Christian Wagner
 * Copyright 2012 Christian Wagner All Rights Reserved.
 */
package type1.sets;

import javax.swing.JOptionPane;

import generic.BadParameterException;
import generic.Tuple;

/**
 *
 * @author Christian Wagner
 */
public class T1MF_Intersection extends T1MF_Prototype {
	private T1MF_Interface setA, setB;

// Construtor Padrão do Juzzy    
//    public T1MF_Intersection(String name, T1MF_Interface setA, T1MF_Interface setB)
//    {
//        super("Intersection: "+setA.getName()+"_"+setB.getName());
//        this.support = new Tuple(Math.max(setA.getSupport().getLeft(),setB.getSupport().getLeft()),
//                Math.min(setA.getSupport().getRight(),setB.getSupport().getRight()));        
//        this.setA = setA;
//        this.setB = setB;
//    }

	public T1MF_Intersection(String name, T1MF_Interface setA, T1MF_Interface setB, String typeIntersection) {
		super("Intersection: " + setA.getName() + "_" + setB.getName());

		// declara as variaveis para o intervalo [x,y]
		double x = 0;
		double y = 0;

		// obten os valores do intervalo de cada conjunto
		double xLeft = setA.getSupport().getLeft();
		double xRight = setA.getSupport().getRight();
		double yLeft = setB.getSupport().getLeft();
		double yRight = setB.getSupport().getRight();

		//JOptionPane.showMessageDialog(null, "T1MF_Intersection: Intersecção eh: "+typeIntersection);
		
		if (typeIntersection.toUpperCase().equals("O2")) { // Conjunctive IvOF (without neutral element)

			x = (Math.pow(xLeft, 2) * Math.pow(yLeft, 2));
			y = (Math.pow(xRight, 2) * Math.pow(yRight, 2));

		} else if (typeIntersection.toUpperCase().equals("OM")) { // Conjunctive IvOF (with neutral element)

			x = Math.min(xLeft, yLeft);
			y = Math.min(xRight, yRight);

		} else if (typeIntersection.toUpperCase().equals("ODB")) { // Averaging IvOF (without neutral element)

			x = ((2 * (xLeft * yLeft)) / (xLeft + yLeft));
			y = ((2 * (xRight * yRight)) / (xRight + yRight));

			// testa se a função gera valores indefinidos
			if (Double.isNaN(x))
				x = 0;
			if (Double.isNaN(y))
				y = 0;

		} else if (typeIntersection.toUpperCase().equals("OM3")) { // Averaging IvOF (with neutral element)

			x = Math.min(xLeft, yLeft);
			y = Math.pow(((xRight * Math.pow(yRight, 2)) + (Math.pow(xRight, 2) * yRight) / 2), 1 / 3);

		} else if (typeIntersection.toUpperCase().equals("O2DB")) { // Mixed IvOF

			x = Math.pow(xLeft, 2) * Math.pow(yLeft, 2);
			y = (2 * xRight * yRight) / (xRight + yRight);

		} else if (typeIntersection.toUpperCase().equals("OMIX")) {

			
			//Calcula o elemento inf (x)
			if (xLeft <= 0.4 && yLeft <= 0.4) {
				
				x = (Math.pow(xLeft, 2) * Math.pow(yLeft, 2) / Math.pow(0.4, 2));
				
			} else if (xLeft >= 0.6 && yLeft >= 0.6) {
				
				x = (0.6 + 2 * ((xLeft - 0.6) * (yLeft - 0.6)) / xLeft + yLeft - 1.2);

			} else {
				
				x = Math.min(xLeft, yLeft);
			}

			
			// Calcula o elemento sup(y)
			if (xRight <= 0.4 && yRight <= 0.4) {
				
				y = (Math.pow(xRight, 2) * Math.pow(yRight, 2) / Math.pow(0.4, 2));
				
			} else if (xRight >= 0.6 && yRight >= 0.6) {
				
				y = (0.6 + 2 * ((xRight - 0.6) * (yRight - 0.6)) / xRight + yRight - 1.2);

			} else {
				
				y = Math.min(xRight, yRight);
			}

		} else if (typeIntersection.toUpperCase().equals("A")) { // Conjunctive IvAF (without neutral element)

			x = (Math.pow(xLeft, 2) * yLeft);
			y = (Math.pow(xRight, 2) * yRight);

		} else if (typeIntersection.toUpperCase().equals("TL")) { // Conjunctive IvAF (with neutral element)
			
			x = Math.max(0, xLeft + yLeft - 1);
			y = Math.max(0, xRight + yRight - 1);

		} else if (typeIntersection.toUpperCase().equals("TLK")) { 
			
			x = Math.min(Math.max(xLeft + yRight -1 , 0), Math.max(xRight+yLeft -1, 0));
			y = Math.max(xRight+yRight, 0);
			
		} 
		else if (typeIntersection.toUpperCase().equals("MAXMIN")) { // default intersection
			
			

			x = Math.max(setA.getSupport().getLeft(), setB.getSupport().getLeft());
			y = Math.min(setA.getSupport().getRight(), setB.getSupport().getRight());

		} else {

			throw new BadParameterException(
					"Only o2, om, odb, om3, o2db, a, tl, tlk and maxmin intersection is currentlyt supported.");
		}

		// Normaliza as saídas

		double xTemp = 0;
		double yTemp = 0;
		double maxVal = 0;

		if (x > y)
			maxVal = x;
		else
			maxVal = y;

		xTemp = (x / maxVal) * 10;
		yTemp = (y / maxVal) * 10;

		x = xTemp;
		y = yTemp;

		this.support = new Tuple(x, y);

		this.setA = setA;
		this.setB = setB;
	}

	@Override
	public double getFS(double x) {
		return Math.min(setA.getFS(x), setB.getFS(x));
	}

	@Override
	public Tuple getAlphaCut(double alpha) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public double getPeak() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int compareTo(Object o) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
