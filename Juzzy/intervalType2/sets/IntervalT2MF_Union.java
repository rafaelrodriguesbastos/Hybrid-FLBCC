/*
 * IntervalT2MF_Union.java
 *
 * Created on 21 May 2007, 18:56
 *
 * Author: Christian Wagner
 * Copyright 2006 Christian Wagner All Rights Reserved.
 */

package intervalType2.sets;

import generic.BadParameterException;
import generic.Tuple;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JOptionPane;

import type1.sets.T1MF_Union;

/**
 * Union operation for interval type 2
 * 
 * @author Christian Wagner
 */
public class IntervalT2MF_Union extends IntervalT2MF_Prototype {
	// private IntervalT2MF_Interface left, right;
	private HashSet<IntervalT2MF_Interface> sets;

	private final boolean DEBUG = false;
	private boolean isNull = false;

	/**
	 * THIS SHOULD BE REMOVED AND ALL UNION AMD INTERSECTION SHOULD BE DONE THROUGH
	 * TYPE-1 CASES Creates a new instance of IntervalT2MF_Union
	 */
//    public IntervalT2MF_Union(IntervalT2MF_Interface a, IntervalT2MF_Interface b,  String typeUnion)
//    {   
//        super("Union of ("+a.getName()+" and "+b.getName()+")");
//
//        //JOptionPane.showMessageDialog(null,"Left A: "+a.getSupport().getLeft()+" Right A: "+a.getSupport().getRight());
//        this.uMF = new T1MF_Union(a.getUMF(), b.getUMF(), typeUnion);
//        this.lMF = new T1MF_Union(a.getLMF(), b.getLMF(), typeUnion);
//              
//                support = new Tuple(Math.min(a.getSupport().getLeft(),b.getSupport().getLeft()),
//                    Math.max(a.getSupport().getRight(),b.getSupport().getRight()));    
//    }

	public IntervalT2MF_Union(IntervalT2MF_Interface a, IntervalT2MF_Interface b, String typeUnion) {
		super("Union of (" + a.getName() + " and " + b.getName() + ")");

		// JOptionPane.showMessageDialog(null,"Left A: "+a.getSupport().getLeft()+"
		// Right A: "+a.getSupport().getRight());
		
		//JOptionPane.showMessageDialog(null, "IntervalT2MF_Union: "+a.getSupport().getLeft()+" "+a.getSupport().getRight());
		
		this.uMF = new T1MF_Union(a.getUMF(), b.getUMF(), typeUnion);
		this.lMF = new T1MF_Union(a.getLMF(), b.getLMF(), typeUnion);

		double xLeft = a.getSupport().getLeft();
		double xRight = a.getSupport().getRight();
		double yLeft = b.getSupport().getLeft();
		double yRight = b.getSupport().getRight();

		double x = 0;
		double y = 0;

		if (typeUnion.toUpperCase().equals("O2N")) { // Conjunctive IvOF (without neutral element)
			x = 1 - Math.pow((1 - xLeft), 2) * Math.pow((1 - yLeft), 2);
			y = 1 - Math.pow((1 - xRight), 2) * Math.pow((1 - yRight), 2);

		} else if (typeUnion.toUpperCase().equals("OMN")) { // Conjunctive IvOF (with neutral element)

			x = Math.max(xLeft, yLeft);
			y = Math.max(xRight, yRight);

		} else if (typeUnion.toUpperCase().equals("ODBN")) { // Averaging IvOF (without neutral element)

			x = (xLeft + yLeft - 2 * xLeft * yLeft) / (2 - xLeft - yLeft);
			y = (xRight + yRight - 2 * xRight * yRight) / (2 - xRight - yRight);

		} else if (typeUnion.toUpperCase().equals("OM3N")) { // Averaging IvOF (without neutral element);

			x = 1 - Math.pow((1 - xLeft) * Math.pow((1 - yLeft), 2) + Math.pow((1 - xLeft), 2) * (1 - yLeft) / 2,
					1 / 3);
			y = Math.max(xRight, yRight);

		} else if (typeUnion.toUpperCase().equals("O2DBN")) { // Mixed IvOF

			//x = (2 * xRight * yRight) / (xLeft - yRight);
			x = (2 * xRight * yRight) / (xLeft + yRight);
			y = 1 - Math.pow((1 - xLeft), 2) * Math.pow((1 - yLeft), 2);

		} else if (typeUnion.toUpperCase().equals("OMIXN")) {

			// Calcula o elemento inf (x)
			if (xLeft >= 0.6 && yLeft >= 0.6) {

				x = (1 - Math.pow((1 - xLeft), 2) * Math.pow((1 - yLeft), 2) / Math.pow(0.4, 2));

			} else if (xLeft <= 0.4 && yLeft <= 0.4) {

				x = 0.6 + 2 * ((0.4 - xLeft) * (0.4 - yLeft) / (0.8 - xLeft - yLeft));

			} else {

				x = Math.max(xLeft, yLeft);

			}

			// Calcular o elemento sup (y)
			if (xRight >= 0.6 && yRight >= 0.6) {

				y = (1 - Math.pow((1 - xRight), 2) * Math.pow((1 - yRight), 2) / Math.pow(0.4, 2));

			} else if (xRight <= 0.4 && yRight <= 0.4) {

				y = 0.4 + 2 * ((0.4 - xRight) * (0.4 - yRight) / (0.8 - xRight - yRight));

			} else {

				y = Math.max(xRight, yRight);
			}

		} else if (typeUnion.toUpperCase().equals("AN")) { // Conjunctive IvAF which is not an IvOF (without neutral
															// element)

			x = 1 - Math.pow((1 - xLeft), 2) * (1 - yLeft);
			y = 1 - Math.pow((1 - xRight), 2) * (1 - yRight);

		} else if (typeUnion.toUpperCase().equals("TLN")) { // Conjunctive IvAF which is not an IvOF (with neutral
															// element)

			x = Math.min(1, xLeft + yLeft);
			y = Math.min(1, xRight + yRight);

		} else if (typeUnion.toUpperCase().equals("SLK")) {
			
			x = Math.min(1, xLeft + yLeft);
			y = Math.min(Math.min(1, xRight + yLeft), Math.min(1, xLeft + yRight));
			
			
		} else if (typeUnion.toUpperCase().equals("MINMAX")) { // default intersection the Juzzy min-max

			x = Math.min(a.getSupport().getLeft(), b.getSupport().getLeft());
			y = Math.max(a.getSupport().getRight(), b.getSupport().getRight());
		}

		else {
			throw new BadParameterException(
					"Only o2n, odbn, om3n, o2dbn, an, tln and minmax union is currentlyt supported.");
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

		support = new Tuple(x, y);
	}

	/*
	 * public IntervalT2MF_Union(IntervalT2MF_Interface a, IntervalT2MF_Interface b,
	 * String agregatorFunction) { super("Union of (" + a.getName() + " and " +
	 * b.getName() + ")"); // JOptionPane.showMessageDialog(null,a.toString());
	 * 
	 * // JOptionPane.showMessageDialog(null, "Left A: " + a.getSupport().getLeft()
	 * + " // Right A: " + a.getSupport().getRight());
	 * 
	 * this.uMF = new T1MF_Union(a.getUMF(), b.getUMF()); this.lMF = new
	 * T1MF_Union(a.getLMF(), b.getLMF());
	 * 
	 * double x = 0; double y = 0;
	 * 
	 * double xLeft = a.getSupport().getLeft(); double xRight =
	 * a.getSupport().getRight(); double yLeft = b.getSupport().getLeft(); double
	 * yRight = b.getSupport().getRight();
	 * 
	 * 
	 * // double x = Math.min(a.getSupport().getLeft(), b.getSupport().getLeft());
	 * // double y = Math.max(a.getSupport().getRight(), b.getSupport().getRight());
	 * // // support = new Tuple(x, // y); // //
	 * JOptionPane.showMessageDialog(null,"X: "+x+" Y "+y);
	 * 
	 * // JOptionPane.showMessageDialog(null, // "xLeft= " + xLeft + " xRight= " +
	 * xRight + " yLeft= " + yLeft + " yRight= " + // yRight);
	 * 
	 * if (agregatorFunction.equals("ODB")) {
	 * 
	 * x = ((2 * (xLeft * yLeft)) / (xLeft + yLeft)); y = ((2 * (xRight * yRight)) /
	 * (xRight + xLeft));
	 * 
	 * // JOptionPane.showMessageDialog(null, "X=" + x + " Y=" + yRight);
	 * 
	 * if (Double.isNaN(x)) x = 0; if (Double.isNaN(y)) y = 0;
	 * 
	 * } else if (agregatorFunction.equals("OM3")) {
	 * 
	 * x = Math.min(xLeft, yLeft); y = (xRight * (Math.pow(yRight, 2))) +
	 * Math.pow(((Math.pow(xRight, 2) * yRight) / 2), 1 / 3);
	 * 
	 * } else if (agregatorFunction.equals("O2")) {
	 * 
	 * // cpStandartScale = (_host.getMaxAvailableMips()/maxCPHost)*10; //
	 * ccStandartScale = (10*_host.getUtilizationOfBw())/minCCHost; //
	 * ramStandartScale = (_host.getUtilizationOfRam()/maxRamHost)*10;
	 * 
	 * x = Math.pow(xLeft, 2) * Math.pow(yLeft, 2); y = Math.pow(xRight, 2) *
	 * Math.pow(yRight, 2);
	 * 
	 * //x = (xLeft * yLeft) / 2; //y = (xRight * yRight) / 2;
	 * 
	 * // JOptionPane.showMessageDialog(null, "X = "+x+" Y = "+y);
	 * 
	 * 
	 * // if (Double.isNaN(x)) //JOptionPane.showMessageDialog(null,
	 * "is NaN X = "+x+"Y = "+y); x = 0; if (Double.isNaN(y)) y = 0;
	 * 
	 * 
	 * } else if (agregatorFunction.equals("OM")) {
	 * 
	 * x = Math.min(xLeft, yLeft); y = Math.min(xRight, yRight);
	 * 
	 * } else if (agregatorFunction.equals("A")) {
	 * 
	 * x = (Math.pow(xLeft, 2) * Math.pow(yLeft, 2)); y = xRight * (Math.pow(yRight,
	 * 2));
	 * 
	 * } else if (agregatorFunction.equals("TL")) {
	 * 
	 * x = Math.max(0, (xLeft + yLeft - 1)); y = Math.max(0, (xRight + yRight - 1));
	 * } else {
	 * 
	 * x = Math.min(a.getSupport().getLeft(), b.getSupport().getLeft()); y =
	 * Math.max(a.getSupport().getRight(), b.getSupport().getRight());
	 * 
	 * }
	 * 
	 * 
	 * //Normaliza as saídas
	 * 
	 * double xTemp = 0; double yTemp = 0; double maxVal = 0;
	 * 
	 * if (x > y) maxVal = x; else maxVal = y;
	 * 
	 * xTemp = (x/maxVal)*10; yTemp = (y/maxVal)*10;
	 * 
	 * x = xTemp; y=yTemp;
	 * 
	 * //JOptionPane.showMessageDialog(null, "agregatorFunction = " +
	 * agregatorFunction + " X ="+x+" Y= "+y);
	 * 
	 * support = new Tuple(x, y);
	 * 
	 * }
	 */

	public HashSet getSets() {
		return sets;
	}

	public boolean isNull() {
		return isNull;
	}

	@Override
	public double getPeak() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
