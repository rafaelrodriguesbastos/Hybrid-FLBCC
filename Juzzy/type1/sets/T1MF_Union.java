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
public class T1MF_Union extends T1MF_Prototype
{
    private T1MF_Interface setA, setB;
    
    // Default Juzzy
    /*public T1MF_Union(T1MF_Interface setA, T1MF_Interface setB)
    {
        super("Union: "+setA.getName()+"_"+setB.getName());
        
        JOptionPane.showMessageDialog(null, "T1MF_Union: "+setA.getSupport().getLeft()+" "+setA.getSupport().getRight());
        
        this.support = new Tuple(Math.min(setA.getSupport().getLeft(),setB.getSupport().getLeft()),
                Math.max(setA.getSupport().getRight(),setB.getSupport().getRight()));
        this.setA = setA;
        this.setB = setB;
    }*/
    
    
    public T1MF_Union(T1MF_Interface setA, T1MF_Interface setB, String typeUnion)
    {
        super("Union: "+setA.getName()+"_"+setB.getName());
        
        //JOptionPane.showMessageDialog(null, "T1MF_Union: "+setA.getSupport().getLeft()+" "+setA.getSupport().getRight());
        
        //this.support = new Tuple(Math.min(setA.getSupport().getLeft(),setB.getSupport().getLeft()),
        //        Math.max(setA.getSupport().getRight(),setB.getSupport().getRight()));
        this.setA = setA;
        this.setB = setB;
        
        
        double xLeft = setA.getSupport().getLeft();
		double xRight = setA.getSupport().getRight();
		double yLeft = setB.getSupport().getLeft();
		double yRight = setB.getSupport().getRight();

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

			x = Math.min(setA.getSupport().getLeft(), setB.getSupport().getLeft());
			y = Math.max(setA.getSupport().getRight(), setB.getSupport().getRight());
		}

		else {
			throw new BadParameterException(
					"Only o2n, odbn, om3n, o2dbn, an, tln, slk and minmax union is currentlyt supported.");
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
		
		this.support = new Tuple(x,y);
		
    }


    @Override
    public double getFS(double x) {
        return Math.max(setA.getFS(x), setB.getFS(x));
    }

    @Override
    public Tuple getAlphaCut(double alpha) 
    {
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
