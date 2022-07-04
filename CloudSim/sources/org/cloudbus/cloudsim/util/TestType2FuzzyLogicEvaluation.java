package org.cloudbus.cloudsim.util;



public class TestType2FuzzyLogicEvaluation {
	
	 public static void main(String args[])
	    {
	        //new SimpleIT2FLSIntFLBCC();
		 
		 //Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale, ccStandartScale, ramStandartScale, 
		 //			plotMF,	isOverOrUnder, isTypeObjective, true, getTypeIntersection(), getTypeUnion());
		 
		 double cpStandartScale = 6.613651768693075;
		 double ccStandartScale = 1.0;
		 double ramStandartScale = 0.022125244140625;
		 boolean plotMF = false;
		 int isOverOrUnder = 1; // 1 Overload, 2 Underload
		 int isTypeObjective =  1; // 1 Performance  
		 String typeIntersection = "";
		 String typeUnion = "";
		 int typeReductionType = 0; // CENTEROFSETS = 0; CENTROID = 1;
		 
		 Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale, ccStandartScale, ramStandartScale, 
		 			plotMF,	isOverOrUnder, isTypeObjective, true, typeIntersection, typeUnion, typeReductionType);
		 
		 it2.getLevelOfUse();

		 
	    }

}
