/*
 * SimpleT1FLS.java
 *
 * Created on May 21st 2012
 *
 * Copyright 2012 Christian Wagner All Rights Reserved.
 */
package org.cloudbus.cloudsim.util;

import generic.Input;
import generic.Output;
import generic.Tuple;
import intervalType2.sets.IntervalT2MF_Interface;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.power.PowerHostUtilizationHistory;
import tools.JMathPlotter;
import type1.sets.T1MF_Trapezoidal;


/**
 * A simple example of an interval Type-2 FLS based on the "How much to tip the waiter"
 *  scenario.
 * The example is an extension of the Type-1 FLS example where we extend the MFs
 * and use the Interval Type-2 System classes. Note that in contrast to the type-1
 * case, here only two sets are used to model the service quality.
 * We have two inputs: food quality and service level and as an output we would
 * like to generate the applicable tip.
 * @author Christian Wagner
 */
public class Type2FuzzyLogicEvaluationBackup14092021 
{
    Input CP, CC, RAM, EC;    //the inputs to the FLS
    Output U;             //the output of the FLS
    IT2_Rulebase rulebase;   //the rulebase captures the entire FLS
    
    private double inCP;
    private double inCC;
    private double inRam;
    private double levelOfuse;
    
    private double OutputXValue;
    private double OutputYValue;
    private double xPontual;
    
    private String typeIntersection="";
    private String typeUnion="";
    private int typeReductionType; // CENTEROFSETS = 0; CENTROID = 1;
    
    private MembershipDegreesOfType2FuzzySets degreesOfMembershipFunctions = new MembershipDegreesOfType2FuzzySets();
    private LibFile libFile = new LibFile();
    private boolean existsHeader;
    
    private DaoMembershipDegreesOfType2FuzzySets daoMembershipDegreesOfType2FuzzySets = new DaoMembershipDegreesOfType2FuzzySets();
    
    // MF for Computational Power
    //MF1='limited':'itrapatype2',[-2.269 -0.9 0.45 2.4 -1.329 -0.01882 1.5 3.5 0.9]
    T1MF_Trapezoidal limitedCPUMF = new  T1MF_Trapezoidal("Upper MF for Limited CP", new double[] {-2.27, -0.9, 1.5, 3.5});   
    T1MF_Trapezoidal limitedCPLMF = new  T1MF_Trapezoidal("Lower MF for Limited CP", new double[] {-1.3, -0.2, 0.45, 2.4});
    IntervalT2MF_Trapezoidal limitedCPMF = new IntervalT2MF_Trapezoidal("IT2MF for limited CP",limitedCPUMF,limitedCPLMF);

    //MF2='reasonable':'itrapatype2',[1.6 3.8 5.4 7.4 2.6 4.6 6.3 8.4 0.9]
    T1MF_Trapezoidal reasonableCPUMF = new  T1MF_Trapezoidal("Upper MF for Reasonable CP", new double[] {1.6, 3.8, 6.3, 8.4});  
    T1MF_Trapezoidal reasonableCPLMF = new  T1MF_Trapezoidal("Lower MF for Reasonable CP", new double[] {2.6, 4.6, 5.4, 7.4});
    IntervalT2MF_Trapezoidal reasonableCPMF = new IntervalT2MF_Trapezoidal("IT2MF for limited CP",reasonableCPUMF,reasonableCPLMF);
    
    //MF3='high':'itrapatype2',[6.65 7.65 10.19 12.34 7.65 8.5 11.19 13.34 0.9]
    T1MF_Trapezoidal highCPUMF = new  T1MF_Trapezoidal("Upper MF for High CP", new double[] {6.65, 7.65, 11.19, 13.34});  
    T1MF_Trapezoidal highCPLMF = new  T1MF_Trapezoidal("Lower MF for High CP", new double[] {7.65, 8.5, 10.19, 12.34});
    IntervalT2MF_Trapezoidal highCPMF = new IntervalT2MF_Trapezoidal("IT2MF for limited CP",highCPUMF,highCPLMF);

    
    //plotMFs("Computational Power Membership Functions", new IntervalT2MF_Interface[]{limitedCPMF,reasonableCPMF, highCPMF}, 10);

    
    // até a l189
    // MF for Comunication Cost
    //MF1='small':'itrapatype2',[-2.27 -0.8 0.5 2.4 -1.327 -0.01682 1.35 3.4 0.9]
    T1MF_Trapezoidal smallCCUMF = new  T1MF_Trapezoidal("Upper MF for Small CC", new double[] {-2.27, -0.8, 1.35, 3.4});   
    T1MF_Trapezoidal smallCCLMF = new  T1MF_Trapezoidal("Lower MF for Small CC", new double[] {-1.3, -0.02, 0.5, 2.4});
    IntervalT2MF_Trapezoidal smallCCMF = new IntervalT2MF_Trapezoidal("IT2MF for limited CP",smallCCUMF,smallCCLMF);
    
    //MF3='average':'itrapatype2',[1.5 4 5.2 7.5 2.5 4.8 6 8.5 0.9]
    T1MF_Trapezoidal averageCCUMF = new  T1MF_Trapezoidal("Upper MF for Average CC", new double[] {1.5, 4, 6, 8.5});
    T1MF_Trapezoidal averageCCLMF = new  T1MF_Trapezoidal("Lower MF for Average CC", new double[] {2.5, 4.8, 5.2, 7.5});
    IntervalT2MF_Trapezoidal averageCCMF = new IntervalT2MF_Trapezoidal("IT2MF for Average CC",averageCCUMF,averageCCLMF);
    
    //MF2='big':'itrapatype2',[6.5 8.5 10 12 7.5 9.2 11.19 13.34 0.9]
    T1MF_Trapezoidal bigCCUMF = new  T1MF_Trapezoidal("Upper MF for Big CC", new double[] {6.5, 8.5, 11.19, 13.34}); 
    T1MF_Trapezoidal bigCCLMF = new  T1MF_Trapezoidal("Lower MF for Big CC", new double[] {7.5, 9.2, 10, 12});
    IntervalT2MF_Trapezoidal bigCCMF = new IntervalT2MF_Trapezoidal("IT2MF for Big CC",bigCCUMF,bigCCLMF);
    
    
    //plotMFs("Comunication Cost Membership Functions", new IntervalT2MF_Interface[]{smallCCMF, averageCCMF, bigCCMF},10);
    
    // MF for RAM
    //MF1='small':'itrapatype2',[-2.27 -0.8 0.5 2.4 -1.327 -0.01682 1.35 3.4 0.9]
    T1MF_Trapezoidal smallRAMUMF = new  T1MF_Trapezoidal("Upper MF for Small RAM", new double[] {-2.27, -0.8, 1.35, 3.4}); 
    T1MF_Trapezoidal smallRAMLMF = new  T1MF_Trapezoidal("Lower MF for Small RAM", new double[] {-1.327, -0.01682, 0.5, 2.4});
    IntervalT2MF_Trapezoidal smallRAMMF = new IntervalT2MF_Trapezoidal("IT2MF for Small RAM",smallRAMUMF,smallRAMLMF);
    
    //MF2='average':'itrapatype2',[1.5 4 5.2 7.5 2.5 4.8 6 8.5 0.9]
    T1MF_Trapezoidal averageRAMUMF = new  T1MF_Trapezoidal("Upper MF for Average RAM", new double[] {1.5, 4, 6, 8.5});
    T1MF_Trapezoidal averageRAMLMF = new  T1MF_Trapezoidal("Lower MF for Average RAM", new double[] {2.5, 4.8, 5.2, 7.5});
    IntervalT2MF_Trapezoidal averageRAMMF = new IntervalT2MF_Trapezoidal("IT2MF Average RAM",averageRAMUMF,averageRAMLMF);
    
    //MF3='high':'itrapatype2',[6.5 8.5 10 12 7.5 9.2 11.19 13.34 0.9]
    T1MF_Trapezoidal highRAMUMF = new  T1MF_Trapezoidal("Upper MF for High RAM CC", new double[] {6.5, 8.5, 11.19, 13.34});
    T1MF_Trapezoidal highRAMLMF = new  T1MF_Trapezoidal("Lower MF for High RAM", new double[] {7.5, 9.2, 10, 12});
    IntervalT2MF_Trapezoidal highRAMMF = new IntervalT2MF_Trapezoidal("IT2MF for High RAM",highRAMUMF,highRAMLMF);
    
    //plotMFs("RAM Membership Functions", new IntervalT2MF_Interface[]{smallRAMMF, averageRAMMF, highRAMMF}, 10);
    
    // MF for Priority (P)
    //MF1='low':'itrapatype2',[-1.74698039215686 -0.590980392156862 -0.569980392156862 3.99901960784314 -1.24678039215686 0.079019607843138 0.409019607843138 4.99901960784314 0.9]
    T1MF_Trapezoidal lowUtilizationUMF = new  T1MF_Trapezoidal("Upper MF for Low Priority", new double[] {-1.75, -0.6, 0.41, 5});  
    T1MF_Trapezoidal lowUtilizationLMF = new  T1MF_Trapezoidal("Lower MF for Low Priority", new double[] {-1.2, 0.08, -0.57, 4.0}); //
    IntervalT2MF_Trapezoidal lowUtilizationMF = new IntervalT2MF_Trapezoidal("IT2MF for Low Priority ",lowUtilizationUMF,lowUtilizationLMF);
    
    //MF2='average':'itrapatype2',[0.5 4.5 4.83 8.5 1.5 5.16 5.5 9.5 0.9]
    T1MF_Trapezoidal averageUtilizationUMF = new  T1MF_Trapezoidal("Upper MF for Average Priority", new double[] {0.5, 4.5, 5.5, 9.5}); 
    T1MF_Trapezoidal averageUtilizationLMF = new  T1MF_Trapezoidal("Lower MF for Average Priority", new double[] {1.5, 5.16, 4.83, 8.5});
    IntervalT2MF_Trapezoidal averageUtilizationMF = new IntervalT2MF_Trapezoidal("IT2MF for Average Priority ",averageUtilizationUMF,averageUtilizationLMF);
    
    //MF3='high':'itrapatype2',[5 9.61 9.94 11.3 6 10.3 10.7 11.8 0.9]
    T1MF_Trapezoidal highUtilizationUMF = new  T1MF_Trapezoidal("Upper MF for High Priority", new double[] {5, 9.6, 10.7, 11.8});  
    T1MF_Trapezoidal highUtilizationLMF = new  T1MF_Trapezoidal("Lower MF for High Priority", new double[] {6, 10.3, 9.94, 11.3});
    IntervalT2MF_Trapezoidal highUtilizationMF = new IntervalT2MF_Trapezoidal("IT2MF for High Priority ", highUtilizationUMF, highUtilizationLMF);        
    //plotMFs("Priority Membership Functions", new IntervalT2MF_Interface[]{lowPriorityMF, averagePriorityMF, highPriorityMF}, 10);

       
           
    public Type2FuzzyLogicEvaluationBackup14092021()
    {
		
        //Define the inputs
        //food = new Input("Food Quality", new Tuple(0,10));
    	CP = new Input("Compatational Power", new Tuple(0,10));
        CC = new Input("Communication Cost", new Tuple(0,10));
        RAM = new Input("Available RAM", new Tuple(0,10));
        //EC = new Input("Energy Cost", new Tuple(0,10));
        
        U = new Output("Utilization", new Tuple(0,10));    //a percentage for the tip

        // MF for Computational Power
        
        //MF1='limited':'itrapatype2',[-2.269 -0.9 0.45 2.4 -1.329 -0.01882 1.5 3.5 0.9]
        T1MF_Trapezoidal limitedCPUMF = new  T1MF_Trapezoidal("Upper MF for Limited CP", new double[] {-2.27, -0.9, 1.5, 3.5});   
        T1MF_Trapezoidal limitedCPLMF = new  T1MF_Trapezoidal("Lower MF for Limited CP", new double[] {-1.3, -0.2, 0.45, 2.4});
        IntervalT2MF_Trapezoidal limitedCPMF = new IntervalT2MF_Trapezoidal("IT2MF for limited CP",limitedCPUMF,limitedCPLMF);
        
        //MF2='reasonable':'itrapatype2',[1.6 3.8 5.4 7.4 2.6 4.6 6.3 8.4 0.9]
        T1MF_Trapezoidal reasonableCPUMF = new  T1MF_Trapezoidal("Upper MF for Reasonable CP", new double[] {1.6, 3.8, 6.3, 8.4});  
        T1MF_Trapezoidal reasonableCPLMF = new  T1MF_Trapezoidal("Lower MF for Reasonable CP", new double[] {2.6, 4.6, 5.4, 7.4});
        IntervalT2MF_Trapezoidal reasonableCPMF = new IntervalT2MF_Trapezoidal("IT2MF for limited CP",reasonableCPUMF,reasonableCPLMF);
        
        //MF3='high':'itrapatype2',[6.65 7.65 10.19 12.34 7.65 8.5 11.19 13.34 0.9]
        T1MF_Trapezoidal highCPUMF = new  T1MF_Trapezoidal("Upper MF for High CP", new double[] {6.65, 7.65, 11.19, 13.34});  
        T1MF_Trapezoidal highCPLMF = new  T1MF_Trapezoidal("Lower MF for High CP", new double[] {7.65, 8.5, 10.19, 12.34});
        IntervalT2MF_Trapezoidal highCPMF = new IntervalT2MF_Trapezoidal("IT2MF for limited CP",highCPUMF,highCPLMF);

        
        //plotMFs("Computational Power Membership Functions", new IntervalT2MF_Interface[]{limitedCPMF,reasonableCPMF, highCPMF}, 10);

        
        // até a l189
        // MF for Comunication Cost
        //MF1='small':'itrapatype2',[-2.27 -0.8 0.5 2.4 -1.327 -0.01682 1.35 3.4 0.9]
        T1MF_Trapezoidal smallCCUMF = new  T1MF_Trapezoidal("Upper MF for Small CC", new double[] {-2.27, -0.8, 1.35, 3.4});   
        T1MF_Trapezoidal smallCCLMF = new  T1MF_Trapezoidal("Lower MF for Small CC", new double[] {-1.3, -0.02, 0.5, 2.4});
        IntervalT2MF_Trapezoidal smallCCMF = new IntervalT2MF_Trapezoidal("IT2MF for limited CP",smallCCUMF,smallCCLMF);
        
        //MF3='average':'itrapatype2',[1.5 4 5.2 7.5 2.5 4.8 6 8.5 0.9]
        T1MF_Trapezoidal averageCCUMF = new  T1MF_Trapezoidal("Upper MF for Average CC", new double[] {1.5, 4, 6, 8.5});
        T1MF_Trapezoidal averageCCLMF = new  T1MF_Trapezoidal("Lower MF for Average CC", new double[] {2.5, 4.8, 5.2, 7.5});
        IntervalT2MF_Trapezoidal averageCCMF = new IntervalT2MF_Trapezoidal("IT2MF for Average CC",averageCCUMF,averageCCLMF);
        
        //MF2='big':'itrapatype2',[6.5 8.5 10 12 7.5 9.2 11.19 13.34 0.9]
        T1MF_Trapezoidal bigCCUMF = new  T1MF_Trapezoidal("Upper MF for Big CC", new double[] {6.5, 8.5, 11.19, 13.34}); 
        T1MF_Trapezoidal bigCCLMF = new  T1MF_Trapezoidal("Lower MF for Big CC", new double[] {7.5, 9.2, 10, 12});
        IntervalT2MF_Trapezoidal bigCCMF = new IntervalT2MF_Trapezoidal("IT2MF for Big CC",bigCCUMF,bigCCLMF);
        
        
        //plotMFs("Comunication Cost Membership Functions", new IntervalT2MF_Interface[]{smallCCMF, averageCCMF, bigCCMF},10);
        
        // MF for RAM
        //MF1='small':'itrapatype2',[-2.27 -0.8 0.5 2.4 -1.327 -0.01682 1.35 3.4 0.9]
        T1MF_Trapezoidal smallRAMUMF = new  T1MF_Trapezoidal("Upper MF for Small RAM", new double[] {-2.27, -0.8, 1.35, 3.4}); 
        T1MF_Trapezoidal smallRAMLMF = new  T1MF_Trapezoidal("Lower MF for Small RAM", new double[] {-1.327, -0.01682, 0.5, 2.4});
        IntervalT2MF_Trapezoidal smallRAMMF = new IntervalT2MF_Trapezoidal("IT2MF for Small RAM",smallRAMUMF,smallRAMLMF);
        
        //MF2='average':'itrapatype2',[1.5 4 5.2 7.5 2.5 4.8 6 8.5 0.9]
        T1MF_Trapezoidal averageRAMUMF = new  T1MF_Trapezoidal("Upper MF for Average RAM", new double[] {1.5, 4, 6, 8.5});
        T1MF_Trapezoidal averageRAMLMF = new  T1MF_Trapezoidal("Lower MF for Average RAM", new double[] {2.5, 4.8, 5.2, 7.5});
        IntervalT2MF_Trapezoidal averageRAMMF = new IntervalT2MF_Trapezoidal("IT2MF Average RAM",averageRAMUMF,averageRAMLMF);
        
        //MF3='high':'itrapatype2',[6.5 8.5 10 12 7.5 9.2 11.19 13.34 0.9]
        T1MF_Trapezoidal highRAMUMF = new  T1MF_Trapezoidal("Upper MF for High RAM CC", new double[] {6.5, 8.5, 11.19, 13.34});
        T1MF_Trapezoidal highRAMLMF = new  T1MF_Trapezoidal("Lower MF for High RAM", new double[] {7.5, 9.2, 10, 12});
        IntervalT2MF_Trapezoidal highRAMMF = new IntervalT2MF_Trapezoidal("IT2MF for High RAM",highRAMUMF,highRAMLMF);
        
        //plotMFs("RAM Membership Functions", new IntervalT2MF_Interface[]{smallRAMMF, averageRAMMF, highRAMMF}, 10);
        
        // MF for Priority (P)
        //MF1='low':'itrapatype2',[-1.74698039215686 -0.590980392156862 -0.569980392156862 3.99901960784314 -1.24678039215686 0.079019607843138 0.409019607843138 4.99901960784314 0.9]
        T1MF_Trapezoidal lowUtilizationUMF = new  T1MF_Trapezoidal("Upper MF for Low Priority", new double[] {-1.75, -0.6, 0.41, 5});  
        T1MF_Trapezoidal lowUtilizationLMF = new  T1MF_Trapezoidal("Lower MF for Low Priority", new double[] {-1.2, 0.08, -0.57, 4.0}); //
        IntervalT2MF_Trapezoidal lowUtilizationMF = new IntervalT2MF_Trapezoidal("IT2MF for Low Priority ",lowUtilizationUMF,lowUtilizationLMF);
        
        //MF2='average':'itrapatype2',[0.5 4.5 4.83 8.5 1.5 5.16 5.5 9.5 0.9]
        T1MF_Trapezoidal averageUtilizationUMF = new  T1MF_Trapezoidal("Upper MF for Average Priority", new double[] {0.5, 4.5, 5.5, 9.5}); 
        T1MF_Trapezoidal averageUtilizationLMF = new  T1MF_Trapezoidal("Lower MF for Average Priority", new double[] {1.5, 5.16, 4.83, 8.5});
        IntervalT2MF_Trapezoidal averageUtilizationMF = new IntervalT2MF_Trapezoidal("IT2MF for Average Priority ",averageUtilizationUMF,averageUtilizationLMF);
        
        //MF3='high':'itrapatype2',[5 9.61 9.94 11.3 6 10.3 10.7 11.8 0.9]
        T1MF_Trapezoidal highUtilizationUMF = new  T1MF_Trapezoidal("Upper MF for High Priority", new double[] {5, 9.6, 10.7, 11.8});  
        T1MF_Trapezoidal highUtilizationLMF = new  T1MF_Trapezoidal("Lower MF for High Priority", new double[] {6, 10.3, 9.94, 11.3});
        IntervalT2MF_Trapezoidal highUtilizationMF = new IntervalT2MF_Trapezoidal("IT2MF for High Priority ", highUtilizationUMF, highUtilizationLMF);
        
        //plotMFs("Priority Membership Functions", new IntervalT2MF_Interface[]{lowPriorityMF, averagePriorityMF, highPriorityMF}, 10);

        //Set up the antecedents and consequents - note how the inputs are associated...
        IT2_Antecedent limitedCP = new IT2_Antecedent("LimitedCP", limitedCPMF, CP);
        IT2_Antecedent reasonableCP = new IT2_Antecedent("ReasonableCP", reasonableCPMF, CP);
        IT2_Antecedent highCP = new IT2_Antecedent("HighCP", highCPMF, CP);
        
        IT2_Antecedent smallCC = new IT2_Antecedent("SmallCC", smallCCMF,CC);
        IT2_Antecedent averageCC = new IT2_Antecedent("AverageCC", averageCCMF,CC);
        IT2_Antecedent bigCC = new IT2_Antecedent("BigCC", bigCCMF,CC);


        IT2_Antecedent smallRAM = new IT2_Antecedent("SmallRAM", smallRAMMF,RAM);
        IT2_Antecedent averageRAM = new IT2_Antecedent("AverageRAM", averageRAMMF,RAM);
        IT2_Antecedent highRAM = new IT2_Antecedent("HighRAM", highRAMMF,RAM);
        
        IT2_Consequent lowUtilization = new IT2_Consequent("LowPriority", lowUtilizationMF, U);
        IT2_Consequent averageUtilization = new IT2_Consequent("AveragePriority", averageUtilizationMF, U);
        IT2_Consequent highUtilization = new IT2_Consequent("AveragePriority", highUtilizationMF, U);
        
        
        // Set up the rulebase and add rules
        rulebase = new IT2_Rulebase(27);
        
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, smallRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, averageRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, highRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, bigCC, smallRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, bigCC, averageRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, bigCC, highRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, smallRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, averageRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, highRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, smallRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, averageRAM}, averageUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, highRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, smallRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, averageRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, highRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, averageCC, smallRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, averageCC, averageRAM}, averageUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, averageCC, highRAM}, lowUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, smallRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, averageRAM}, averageUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, highRAM}, lowUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, smallRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, averageRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, highRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, averageCC, smallRAM}, highUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, averageCC, averageRAM}, averageUtilization));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, averageCC, highRAM}, lowUtilization));
        
        
        
        
        //get some outputs
        // getPriority(inCP, inCC, inRAM);
        getPriority(8.0, 2.0, 9.0);
        getPriority(1.0, 8.0, 3.0);
        
      
                
        //plot some sets, discretizing each input into 100 steps.
        plotMFs("Computational Power Membership Functions", new IntervalT2MF_Interface[]{limitedCPMF,reasonableCPMF, highCPMF}, 10);
        plotMFs("Comunication Cost Membership Functions", new IntervalT2MF_Interface[]{smallCCMF, averageCCMF, bigCCMF},10);
        plotMFs("RAM Membership Functions", new IntervalT2MF_Interface[]{smallRAMMF, averageRAMMF, highRAMMF}, 10);
        plotMFs("Priority Membership Functions", new IntervalT2MF_Interface[]{lowUtilizationMF, averageUtilizationMF, highUtilizationMF}, 10);
        
        //plot control surface
        //do either height defuzzification (false) or centroid d. (true)
       // plotControlSurface(false, 100, 100);
        
        //print out the rules
        System.out.println("\n"+rulebase);
        
    }
    
    
    
    // isOverOrUnder: 1 = Overload; 2 = Underload
    // isTypeObjective: 1 = Performance; 2 = Energy; 3 = Hibridy
    public Type2FuzzyLogicEvaluationBackup14092021(double inCP, double inCC, double inRam, boolean plotMF, int isOverOrUnder, 
    		int isTypeObjective, boolean IntervalOutputType, String typeIntersection, String typeUnion) {
		super();
		this.inCP = inCP;
		this.inCC = inCC;
		this.inRam = inRam;
		this.typeIntersection = typeIntersection;
		this.typeUnion = typeUnion;
		 
		
	       //Define the inputs
        //food = new Input("Food Quality", new Tuple(0,10));
    	CP = new Input("Compatational Power", new Tuple(0,10));
        CC = new Input("Communication Cost", new Tuple(0,10));
        RAM = new Input("Available RAM", new Tuple(0,10));
        //EC = new Input("Energy Cost", new Tuple(0,10));
        
        U = new Output("Priority", new Tuple(0,10));    //a percentage for the tip

        // as funções de pertinência eram aqui

        //Set up the antecedents and consequents - note how the inputs are associated...
        IT2_Antecedent limitedCP = new IT2_Antecedent("LimitedCP", this.limitedCPMF, CP);
        IT2_Antecedent reasonableCP = new IT2_Antecedent("ReasonableCP", this.reasonableCPMF, CP);
        IT2_Antecedent highCP = new IT2_Antecedent("HighCP", this.highCPMF, CP);
        
        IT2_Antecedent smallCC = new IT2_Antecedent("SmallCC", this.smallCCMF,CC);
        IT2_Antecedent averageCC = new IT2_Antecedent("AverageCC", this.averageCCMF,CC);
        IT2_Antecedent bigCC = new IT2_Antecedent("BigCC", this.bigCCMF,CC);


        IT2_Antecedent smallRAM = new IT2_Antecedent("SmallRAM", this.smallRAMMF,RAM);
        IT2_Antecedent averageRAM = new IT2_Antecedent("AverageRAM", this.averageRAMMF,RAM);
        IT2_Antecedent highRAM = new IT2_Antecedent("HighRAM", this.highRAMMF,RAM);
        
        IT2_Consequent lowUtilization = new IT2_Consequent("LowPriority", this.lowUtilizationMF, U);
        IT2_Consequent averageUtilization = new IT2_Consequent("AveragePriority", this.averageUtilizationMF, U);
        IT2_Consequent highUtilization = new IT2_Consequent("AveragePriority", this.highUtilizationMF, U);
        
        
        // Set up the rulebase and add rules
        rulebase = new IT2_Rulebase(27);
        
        // isOverOrUnder: 1 = Overload; 2 = Underload
        // isTypeObjective: 1 = Performance; 2 = Energy; 3 = Hibridy
        
        // Overload e Performance
        if (isOverOrUnder == 1 && isTypeObjective ==1) {
        	
        	// Regras que estavam com problema verificar se estão corretas 
            /*rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, smallRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, averageRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, bigCC, averageRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, smallRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, highRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, highRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, averageRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, highRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, averageCC, smallRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, smallRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, averageRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, highRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, smallRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, averageRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, highRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, smallRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, averageRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, highRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, smallRAM}, highUtilization));*/
        	
        	 rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, smallRAM}, highUtilization));
        	 rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, averageRAM}, averageUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, highRAM}, highUtilization)); // nao tinha esta regra
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, bigCC, averageRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, bigCC, smallRAM}, highUtilization)); // não tinha esta regra
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, bigCC, highRAM}, highUtilization)); // não tinha esta regra 
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, smallRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, averageRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, highRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, smallRAM}, averageUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, averageRAM}, averageUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, highRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, smallRAM}, averageUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, averageRAM}, averageUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, highRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, averageCC, smallRAM}, averageUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, averageCC, averageRAM}, averageUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, averageCC, highRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, smallRAM}, lowUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, averageRAM}, lowUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, highRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, smallRAM}, averageUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, averageRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, highRAM}, highUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, averageCC, smallRAM}, lowUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, averageCC, averageRAM}, lowUtilization));
             rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, averageCC, highRAM}, averageUtilization));
        	
         // // Underload e Performance   
        }else if(isOverOrUnder == 2 && isTypeObjective ==1) {
        	
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, smallRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, averageRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, bigCC, averageRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, smallRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, averageCC, highRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, highRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, averageRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, bigCC, highRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, averageCC, smallRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, smallRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, averageRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, highRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, smallRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, averageRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, bigCC, highRAM}, lowUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, smallRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{limitedCP, smallCC, averageRAM}, highUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{reasonableCP, smallCC, highRAM}, averageUtilization));
            rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highCP, smallCC, smallRAM}, highUtilization));
            
        }
       
        //rulebase.setAgregatorFunction("");
        //rulebase.setTypeIntersection("TL");
        //rulebase.setTypeUnion("");
        //JOptionPane.showMessageDialog(null,"Rulebase Intersecção é: "+getTypeIntersection()+" e "+"União é "+getTypeUnion());
       
        rulebase.setImplicationMethod((byte) 1);
        rulebase.setTypeIntersection(getTypeIntersection());
        rulebase.setTypeUnion(getTypeUnion());
        
        //obtem os graus de pertinencia para da entrada CP
        //getDegreesOfMembershipFunctions().setLimitedCPDegreeOfMembershipLowerBound(limitedCPMF.getLowerBound(this.inCP));
        //getDegreesOfMembershipFunctions().setLimitedCPDegreeOfMembershipUpperBound(limitedCPMF.getUpperBound(this.inCP));
        
        //getDegreesOfMembershipFunctions().setReasonableCPDegreeOfMembershipLowerBound(reasonableCPMF.getLowerBound(this.inCP));  
        //getDegreesOfMembershipFunctions().setReasonableCPDegreeOfMembershipUpperBound(reasonableCPMF.getUpperBound(this.inCP));
        
        //getDegreesOfMembershipFunctions().setHighCPDegreeOfMembershipLowerBound(highCPMF.getLowerBound(this.inCP));
        //getDegreesOfMembershipFunctions().setHighCPDegreeOfMembershipUpperBound(highCPMF.getUpperBound(this.inCP));
        
        // obtem os graus de pertinencia da entrada CC
        //getDegreesOfMembershipFunctions().setSmallCCDegreeOfMembershipLowerBound(smallCCMF.getLowerBound(this.inCC));
        //getDegreesOfMembershipFunctions().setSmallCCDegreeOfMembershipUpperBound(smallCCMF.getUpperBound(this.inCC));
        
        //getDegreesOfMembershipFunctions().setAverageCCDegreeOfMembershipLowerBound(averageCCMF.getLowerBound(this.inCC));
        //getDegreesOfMembershipFunctions().setAverageCCDegreeOfMembershipUpperBound(averageCCMF.getUpperBound(this.inCC));
        
        //getDegreesOfMembershipFunctions().setBigCCDegreeOfMembershipLowerBound(bigCCMF.getLowerBound(this.inCC));
        //getDegreesOfMembershipFunctions().setBigCCDegreeOfMembershipUpperBound(bigCCMF.getUpperBound(this.inCC));
        
        
        //obter os graus de pertinencia para entrada RAM
        //getDegreesOfMembershipFunctions().setSmallRAMDegreeOfMembershipLowerBound(smallRAMMF.getLowerBound(this.inRam));
        //getDegreesOfMembershipFunctions().setSmallRAMDegreeOfMembershipUpperBound(smallRAMMF.getUpperBound(this.inRam));
        
        //getDegreesOfMembershipFunctions().setAverageRAMDegreeOfMembershipLowerBound(averageRAMMF.getLowerBound(this.inRam));
        //getDegreesOfMembershipFunctions().setAverageRAMDegreeOfMembershipUpperBound(averageRAMMF.getUpperBound(this.inRam));
        
        //getDegreesOfMembershipFunctions().setHighRAMDegreeOfMembershipLowerBound(averageRAMMF.getLowerBound(this.inRam));
        //getDegreesOfMembershipFunctions().setHighRAMDegreeOfMembershipUpperBound(averageRAMMF.getUpperBound(this.inRam));
        
        
        //get some outputs
        // getPriority(inCP, inCC, inRAM);
        //getPriority(8.0, 2.0, 9.0);
        //getPriority(1.0, 8.0, 3.0);
        
        // OutputType = 1 saída em valor único
        // se não saída em intervalo
        if(!IntervalOutputType) {
        	setLevelOfUsePriority(getLevelOfUse(getInCP(), getInCC(), getInRam(),0));	
        }else {
        	getLevelRangeInUse(getInCP(), getInCC(), getInRam(),0);
        }
        
        
        // obtendo os graus de pertinecia para a saída utilização
        //getDegreesOfMembershipFunctions().setLowUtilizationDegreeOfMembershipLowerBound(lowUtilizationMF.getLowerBound(this.xPontual));
        //getDegreesOfMembershipFunctions().setLowUtilizationDegreeOfMembershipUpperBound(lowUtilizationMF.getUpperBound(this.xPontual));
        
        //getDegreesOfMembershipFunctions().setAverageUtilizationDegreeOfMembershipLowerBound(averageUtilizationMF.getLowerBound(this.xPontual));
        //getDegreesOfMembershipFunctions().setAverageUtilizationDegreeOfMembershipUpperBound(averageUtilizationMF.getUpperBound(this.xPontual));
        
        //getDegreesOfMembershipFunctions().setHighUtilizationDegreeOfMembershipLowerBound(highUtilizationMF.getLowerBound(this.xPontual));  //highUtilizationUMF
        //getDegreesOfMembershipFunctions().setHighUtilizationDegreeOfMembershipLowerBound(highUtilizationMF.getUpperBound(this.xPontual));  //highUtilizationUMF
        
        // para salvar em arquivos
        /*String pathFile = "/home/bruno/tmp/";
        String fileName = "correlacao_alex";
        String fileHeader = "LowUtilizationLower, LowUtilizationUpper";
        
        
        String linha = getDegreesOfMembershipFunctions().getLowUtilizationDegreeOfMembershipLowerBound()+","+
        		getDegreesOfMembershipFunctions().getLowUtilizationDegreeOfMembershipUpperBound()+","+
                getDegreesOfMembershipFunctions().getAverageUtilizationDegreeOfMembershipLowerBound()+","+
                getDegreesOfMembershipFunctions().getAverageUtilizationDegreeOfMembershipUpperBound()+","+
                getDegreesOfMembershipFunctions().getHighUtilizationDegreeOfMembershipLowerBound()+","+
                getDegreesOfMembershipFunctions().getHighUtilizationDegreeOfMembershipUpperBound();
        
        //linhaAnterior = linha;
        
        libFile.adicionarLinhaTxt(linha, pathFile, fileName);*/
        
        // estava usando este metodo para adicionar linhas ao arquivo
        //daoMembershipDegreesOfType2FuzzySets.adicionar(getDegreesOfMembershipFunctions());
        
        
        
        
                

        if (plotMF) {
          //plot some sets, discretizing each input into 100 steps.
          plotMFs("Computational Power Membership Functions", new IntervalT2MF_Interface[]{limitedCPMF,reasonableCPMF, highCPMF}, 10);
          plotMFs("Comunication Cost Membership Functions", new IntervalT2MF_Interface[]{smallCCMF, averageCCMF, bigCCMF},10);
          plotMFs("RAM Membership Functions", new IntervalT2MF_Interface[]{smallRAMMF, averageRAMMF, highRAMMF}, 10);
          plotMFs("Priority Membership Functions", new IntervalT2MF_Interface[]{lowUtilizationMF, averageUtilizationMF, highUtilizationMF}, 10);

          //plot control surface
          //do either height defuzzification (false) or centroid d. (true)
          // plotControlSurface(false, 100, 100);
        
          //print out the rules
          System.out.println("\n"+rulebase);
        }
	}

	public double getInCP() {
		return inCP;
	}




	public void setInCP(double inCP) {
		this.inCP = inCP;
	}




	public double getInCC() {
		return inCC;
	}




	public void setInCC(double inCC) {
		this.inCC = inCC;
	}




	public double getInRam() {
		return inRam;
	}




	public void setInRam(double inRam) {
		this.inRam = inRam;
	}




	public double getLevelOfUse() {
		return levelOfuse;
	}




	public void setLevelOfUsePriority(double levelOfuse) {
		this.levelOfuse = levelOfuse;
	}
	
	




	public int getTypeReductionType() {
		return typeReductionType;
	}



	public void setTypeReductionType(int typeReductionType) {
		this.typeReductionType = typeReductionType;
	}



	/**
     * Basic method that prints the output for a given set of inputs.
     * @param inCP
     * @param inCC
     * @param inRAM 
     */
    
    public void getPriority(double inCP, double inCC, double inRAM)
    {
        //first, set the inputs
    	
    	CP.setInput(inCP);
    	CC.setInput(inCC);
    	RAM.setInput(inRAM);
    	
        
        //now execute the FLS and print output
        System.out.println("The CP was: "+CP.getInput());
        System.out.println("The CC was: "+CC.getInput());
        System.out.println("The RAM was: "+RAM.getInput());
        System.out.println("Using center of sets type reduction, the IT2 FLS recommends a "
                + "priority of: "+rulebase.evaluate(0).get(U));  
        System.out.println("Using centroid type reduction, the IT2 FLS recommends a "
                + "priority of: "+rulebase.evaluate(1).get(U));
        
        
        //show the output of the raw centroids
        System.out.println("Centroid of the output for Priority (based on centroid type reduction):");
        TreeMap<Output, Object[]> centroid = rulebase.evaluateGetCentroid(1);
        Object[] centroidTip = centroid.get(U);
        Tuple centroidTipXValues = (Tuple)centroidTip[0];
        double centroidTipYValues = ((Double)centroidTip[1]);
            System.out.println(centroidTipXValues+" at y= "+centroidTipYValues);        
    }
    
    
    public double getLevelOfUse(double inCP, double inCC, double inRAM, int tipoDefuzzificacao)
    {
        //first, set the inputs
    	
    	CP.setInput(inCP);
    	CC.setInput(inCC);
    	RAM.setInput(inRAM);
    	
    	
  		
    	
        /*
        //now execute the FLS and print output
        System.out.println("The CP was: "+CP.getInput());
        System.out.println("The CC was: "+CC.getInput());
        System.out.println("The RAM was: "+RAM.getInput());
        System.out.println("Using center of sets type reduction, the IT2 FLS recommends a "
                + "priority of: "+rulebase.evaluate(0).get(P));  
        System.out.println("Using centroid type reduction, the IT2 FLS recommends a "
                + "priority of: "+rulebase.evaluate(1).get(P));
        
        
        //show the output of the raw centroids
        System.out.println("Centroid of the output for Priority (based on centroid type reduction):");
        TreeMap<Output, Object[]> centroid = rulebase.evaluateGetCentroid(1);
        Object[] centroidTip = centroid.get(P);
        Tuple centroidTipXValues = (Tuple)centroidTip[0];
        double centroidTipYValues = ((Double)centroidTip[1]);
            System.out.println(centroidTipXValues+" at y= "+centroidTipYValues);     
         */   
    	// System.out.println("Prioridade: "+rulebase.evaluate(tipoDefuzzificacao).get(U));
    	
    	    //JOptionPane.showMessageDialog(null, "rulebase.evaluate(tipoDefuzzificacao).get(U): "+rulebase.evaluate(tipoDefuzzificacao).get(U));
    	
            return rulebase.evaluate(tipoDefuzzificacao).get(U);
    }
    
    public void getLevelRangeInUse(double inCP, double inCC, double inRAM, int tipoDefuzzificacao)
    {
        //first, set the inputs
    	
    	CP.setInput(inCP);
    	CC.setInput(inCC);
    	RAM.setInput(inRAM);
    	
   	
        /*
        //now execute the FLS and print output
        System.out.println("The CP was: "+CP.getInput());
        System.out.println("The CC was: "+CC.getInput());
        System.out.println("The RAM was: "+RAM.getInput());
        System.out.println("Using center of sets type reduction, the IT2 FLS recommends a "
                + "priority of: "+rulebase.evaluate(0).get(P));  
        System.out.println("Using centroid type reduction, the IT2 FLS recommends a "
                + "priority of: "+rulebase.evaluate(1).get(P));
        
        //show the output of the raw centroids
        System.out.println("Centroid of the output for Priority (based on centroid type reduction):");
        TreeMap<Output, Object[]> centroid = rulebase.evaluateGetCentroid(1);
        Object[] centroidTip = centroid.get(P);
        Tuple centroidTipXValues = (Tuple)centroidTip[0];
        double centroidTipYValues = ((Double)centroidTip[1]);
            System.out.println(centroidTipXValues+" at y= "+centroidTipYValues);     
         */   
    	// System.out.println("Prioridade: "+rulebase.evaluate(tipoDefuzzificacao).get(U));
    	
    	TreeMap<Output, Object[]> centroid = rulebase.evaluateGetCentroid(tipoDefuzzificacao);
        Object[] centroidTip = centroid.get(U);
        Tuple centroidTipXValues = (Tuple)centroidTip[0];
        double centroidTipYValues = ((Double) centroidTip[1]);

        //System.out.println("inCP: "+inCP + " inCC= " + inCC+" inRAM "+inRAM);
        //System.out.println(centroidTipXValues + " at y= " + centroidTipYValues);
        
        //this.OutputXValue = Double.parseDouble(centroidTipXValues.toString());
        this.OutputXValue = centroidTipXValues.getLeft();

        //double centroidTipYValues = ((Double)centroidTip[1]);
        this.OutputYValue = centroidTipXValues.getRight();
        
       // System.out.println("XValue: "+this.OutputXValue+" YValue: "+this.OutputYValue);
        
        // verificar
        this.xPontual = rulebase.evaluate(0).get(U);  //centroidTipXValues.getAverage(); 
        
        //JOptionPane.showMessageDialog(null, "get(u): "+ rulebase.evaluate(0).get(U)+"\n"+"centroidTipXValues.getAverage(): "+centroidTipXValues.getAverage());
    }
    
    
    
    
    private void plotMFs(String name, IntervalT2MF_Interface[] sets, int discretizationLevel)
    {
        JMathPlotter plotter = new JMathPlotter();
        plotter.plotMF(sets[0].getName(), sets[0], discretizationLevel, null, false);
       
        for (int i=1;i<sets.length;i++)
        {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, null, false);
        }
        plotter.show(name);
    }

    /*
    private void plotControlSurface(boolean useCentroidDefuzzification, int input1Discs, int input2Discs, int input3Discs)
    {
        double output;
        double[] x = new double[input1Discs];
        double[] y = new double[input2Discs];
        double[] j = new double[input3Discs];
        
        double[][][] z = new double[y.length][x.length][j.length];
        double incrX, incrY, incrJ;
        incrX = CP.getDomain().getSize()/(input1Discs-1.0);
        incrY = CC.getDomain().getSize()/(input2Discs-1.0);
        incrJ = RAM.getDomain().getSize()/(input3Discs-1.0);

        //first, get the values
        for(int currentX=0; currentX<input1Discs; currentX++)
        {
            x[currentX] = currentX * incrX;        
        }
        for(int currentY=0; currentY<input2Discs; currentY++)
        {
            y[currentY] = currentY * incrY;
        }
        
        for(int currentJ=0; currentJ<input3Discs; currentJ++)
        {
            j[currentJ] = currentJ * incrJ;
        }

        
        for(int currentX=0; currentX<input1Discs; currentX++)
        {
            CP.setInput(x[currentX]);
            for(int currentY=0; currentY<input2Discs; currentY++)
            {//System.out.println("Current x = "+currentX+"  current y = "+currentY);
                CC.setInput(y[currentY]);
                RAM.setInput(j[currentJ]);
                if(useCentroidDefuzzification)
                    output = rulebase.evaluate(1).get(tip);
                else
                    output = rulebase.evaluate(0).get(tip);
                z[currentY][currentX] = output;
            }    
        }
        
        //now do the plotting
        JMathPlotter plotter = new JMathPlotter();
        plotter.plotControlSurface("Control Surface",
                new String[]{food.getName(), service.getName(), "Tip"}, x, y, z, new Tuple(0.0,30.0), true); 
        plotter.show("Interval Type-2 Fuzzy Logic System Control Surface for Tipping Example");
    }
    */
    
    
    
   /* 
    public static void main(String args[])
    {
        new HostIT2FLSIntFLBCC();
    }
    */
    
	//aqui ini
    
    public double getLevelOfUse(Host host, boolean plotMF, int isOverOrUnder, int isTypeObjective, List<? extends Host> hostList,
    		boolean IntervalOutputType) throws IOException {
		PowerHostUtilizationHistory _host = (PowerHostUtilizationHistory) host;
		
		//double cpAvailable = _host.getMaxAvailableMips();
		//double ccAvailable = _host.getBw() - _host.getUtilizationOfBw();
		//double ramAvailable = _host.getRam() - _host.getUtilizationOfRam();
		
		double maxCPHost =0;
		double minCCHost=0;
		double maxRamHost=0;
		
		for(int i=0; i<hostList.size(); i++) {
			
			if (maxCPHost < hostList.get(i).getTotalMips()) {
				maxCPHost = hostList.get(i).getTotalMips();
			}
			
			if (minCCHost < hostList.get(i).getBw()) {
				minCCHost = hostList.get(i).getBw();
			}
			
			if (maxRamHost < hostList.get(i).getRam()) {
				maxRamHost = hostList.get(i).getRam();
			}
			
			//System.out.println("maxCPHost: #"+maxCPHost+" minCCHost: #"+minCCHost+ " maxRamHost: #"+maxRamHost);
			
		}
		
		double cpStandartScale = 0;
		double ccStandartScale = 0;
		double ramStandartScale=0;
		
		cpStandartScale = (_host.getMaxAvailableMips()/maxCPHost)*10;
		//ccStandartScale = (_host.getUtilizationOfBw()/maxCCHost)*10;
		ccStandartScale = (10*_host.getUtilizationOfBw())/minCCHost;
		ramStandartScale = (_host.getUtilizationOfRam()/maxRamHost)*10;
		
		Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale,ccStandartScale,ramStandartScale, plotMF, isOverOrUnder, isTypeObjective,
				IntervalOutputType, typeIntersection, typeUnion, typeReductionType);		
		
		// System.out.println("maxCPHost: #"+maxCPHost+" minCCHost: #"+minCCHost+ " maxRamHost: #"+maxRamHost + " Level of Use #"+it2.getLevelOfUse());
		//System.out.println("cpStandartScale: #"+cpStandartScale+" ccStandartScale: #"+ccStandartScale+ " ramStandartScale: #"+ramStandartScale+ " it2.getLevelOfUse() #"+(it2.getLevelOfUse()/10));
		
		return it2.getLevelOfUse();
		
	}



	public double getOutputXValue() {
		return OutputXValue;
	}



	public void setOutputXValue(double outputXValue) {
		OutputXValue = outputXValue;
	}



	public double getOutputYValue() {
		return OutputYValue;
	}



	public void setOutputYValue(double outputYValue) {
		OutputYValue = outputYValue;
	}



	public String getTypeIntersection() {
		return typeIntersection;
	}



	public void setTypeIntersection(String typeIntersection) {
		this.typeIntersection = typeIntersection;
	}



	public String getTypeUnion() {
		return typeUnion;
	}



	public void setTypeUnion(String typeUnion) {
		this.typeUnion = typeUnion;
	}



	public MembershipDegreesOfType2FuzzySets getDegreesOfMembershipFunctions() {
		return degreesOfMembershipFunctions;
	}



	public void setDegreesOfMembershipFunctions(MembershipDegreesOfType2FuzzySets degreesOfMembershipFunctions) {
		this.degreesOfMembershipFunctions = degreesOfMembershipFunctions;
	}



	public double getxPontual() {
		return xPontual;
	}



	public void setxPontual(double xPontual) {
		this.xPontual = xPontual;
	}



	public LibFile getLibFile() {
		return libFile;
	}



	public void setLibFile(LibFile libFile) {
		this.libFile = libFile;
	}



	public boolean isExistsHeader() {
		return existsHeader;
	}



	public void setExistsHeader(boolean existsHeader) {
		this.existsHeader = existsHeader;
	}



	public DaoMembershipDegreesOfType2FuzzySets getDaoMembershipDegreesOfType2FuzzySets() {
		return daoMembershipDegreesOfType2FuzzySets;
	}



	public void setDaoMembershipDegreesOfType2FuzzySets(
			DaoMembershipDegreesOfType2FuzzySets daoMembershipDegreesOfType2FuzzySets) {
		this.daoMembershipDegreesOfType2FuzzySets = daoMembershipDegreesOfType2FuzzySets;
	}



	public IT2_Rulebase getRulebase() {
		return rulebase;
	}



	public void setRulebase(IT2_Rulebase rulebase) {
		this.rulebase = rulebase;
	}



	public T1MF_Trapezoidal getLimitedCPUMF() {
		return limitedCPUMF;
	}



	public void setLimitedCPUMF(T1MF_Trapezoidal limitedCPUMF) {
		this.limitedCPUMF = limitedCPUMF;
	}



	public T1MF_Trapezoidal getLimitedCPLMF() {
		return limitedCPLMF;
	}



	public void setLimitedCPLMF(T1MF_Trapezoidal limitedCPLMF) {
		this.limitedCPLMF = limitedCPLMF;
	}



	public IntervalT2MF_Trapezoidal getLimitedCPMF() {
		return limitedCPMF;
	}



	public void setLimitedCPMF(IntervalT2MF_Trapezoidal limitedCPMF) {
		this.limitedCPMF = limitedCPMF;
	}



	public T1MF_Trapezoidal getReasonableCPUMF() {
		return reasonableCPUMF;
	}



	public void setReasonableCPUMF(T1MF_Trapezoidal reasonableCPUMF) {
		this.reasonableCPUMF = reasonableCPUMF;
	}



	public T1MF_Trapezoidal getReasonableCPLMF() {
		return reasonableCPLMF;
	}



	public void setReasonableCPLMF(T1MF_Trapezoidal reasonableCPLMF) {
		this.reasonableCPLMF = reasonableCPLMF;
	}



	public IntervalT2MF_Trapezoidal getReasonableCPMF() {
		return reasonableCPMF;
	}



	public void setReasonableCPMF(IntervalT2MF_Trapezoidal reasonableCPMF) {
		this.reasonableCPMF = reasonableCPMF;
	}



	public T1MF_Trapezoidal getHighCPUMF() {
		return highCPUMF;
	}



	public void setHighCPUMF(T1MF_Trapezoidal highCPUMF) {
		this.highCPUMF = highCPUMF;
	}



	public T1MF_Trapezoidal getHighCPLMF() {
		return highCPLMF;
	}



	public void setHighCPLMF(T1MF_Trapezoidal highCPLMF) {
		this.highCPLMF = highCPLMF;
	}



	public IntervalT2MF_Trapezoidal getHighCPMF() {
		return highCPMF;
	}



	public void setHighCPMF(IntervalT2MF_Trapezoidal highCPMF) {
		this.highCPMF = highCPMF;
	}



	public T1MF_Trapezoidal getSmallCCUMF() {
		return smallCCUMF;
	}



	public void setSmallCCUMF(T1MF_Trapezoidal smallCCUMF) {
		this.smallCCUMF = smallCCUMF;
	}



	public T1MF_Trapezoidal getSmallCCLMF() {
		return smallCCLMF;
	}



	public void setSmallCCLMF(T1MF_Trapezoidal smallCCLMF) {
		this.smallCCLMF = smallCCLMF;
	}



	public IntervalT2MF_Trapezoidal getSmallCCMF() {
		return smallCCMF;
	}



	public void setSmallCCMF(IntervalT2MF_Trapezoidal smallCCMF) {
		this.smallCCMF = smallCCMF;
	}



	public T1MF_Trapezoidal getAverageCCUMF() {
		return averageCCUMF;
	}



	public void setAverageCCUMF(T1MF_Trapezoidal averageCCUMF) {
		this.averageCCUMF = averageCCUMF;
	}



	public T1MF_Trapezoidal getAverageCCLMF() {
		return averageCCLMF;
	}



	public void setAverageCCLMF(T1MF_Trapezoidal averageCCLMF) {
		this.averageCCLMF = averageCCLMF;
	}



	public IntervalT2MF_Trapezoidal getAverageCCMF() {
		return averageCCMF;
	}



	public void setAverageCCMF(IntervalT2MF_Trapezoidal averageCCMF) {
		this.averageCCMF = averageCCMF;
	}



	public T1MF_Trapezoidal getBigCCUMF() {
		return bigCCUMF;
	}



	public void setBigCCUMF(T1MF_Trapezoidal bigCCUMF) {
		this.bigCCUMF = bigCCUMF;
	}



	public T1MF_Trapezoidal getBigCCLMF() {
		return bigCCLMF;
	}



	public void setBigCCLMF(T1MF_Trapezoidal bigCCLMF) {
		this.bigCCLMF = bigCCLMF;
	}



	public IntervalT2MF_Trapezoidal getBigCCMF() {
		return bigCCMF;
	}



	public void setBigCCMF(IntervalT2MF_Trapezoidal bigCCMF) {
		this.bigCCMF = bigCCMF;
	}



	public T1MF_Trapezoidal getSmallRAMUMF() {
		return smallRAMUMF;
	}



	public void setSmallRAMUMF(T1MF_Trapezoidal smallRAMUMF) {
		this.smallRAMUMF = smallRAMUMF;
	}



	public T1MF_Trapezoidal getSmallRAMLMF() {
		return smallRAMLMF;
	}



	public void setSmallRAMLMF(T1MF_Trapezoidal smallRAMLMF) {
		this.smallRAMLMF = smallRAMLMF;
	}



	public IntervalT2MF_Trapezoidal getSmallRAMMF() {
		return smallRAMMF;
	}



	public void setSmallRAMMF(IntervalT2MF_Trapezoidal smallRAMMF) {
		this.smallRAMMF = smallRAMMF;
	}



	public T1MF_Trapezoidal getAverageRAMUMF() {
		return averageRAMUMF;
	}



	public void setAverageRAMUMF(T1MF_Trapezoidal averageRAMUMF) {
		this.averageRAMUMF = averageRAMUMF;
	}



	public T1MF_Trapezoidal getAverageRAMLMF() {
		return averageRAMLMF;
	}



	public void setAverageRAMLMF(T1MF_Trapezoidal averageRAMLMF) {
		this.averageRAMLMF = averageRAMLMF;
	}



	public IntervalT2MF_Trapezoidal getAverageRAMMF() {
		return averageRAMMF;
	}



	public void setAverageRAMMF(IntervalT2MF_Trapezoidal averageRAMMF) {
		this.averageRAMMF = averageRAMMF;
	}



	public T1MF_Trapezoidal getHighRAMUMF() {
		return highRAMUMF;
	}



	public void setHighRAMUMF(T1MF_Trapezoidal highRAMUMF) {
		this.highRAMUMF = highRAMUMF;
	}



	public T1MF_Trapezoidal getHighRAMLMF() {
		return highRAMLMF;
	}



	public void setHighRAMLMF(T1MF_Trapezoidal highRAMLMF) {
		this.highRAMLMF = highRAMLMF;
	}



	public IntervalT2MF_Trapezoidal getHighRAMMF() {
		return highRAMMF;
	}



	public void setHighRAMMF(IntervalT2MF_Trapezoidal highRAMMF) {
		this.highRAMMF = highRAMMF;
	}



	public T1MF_Trapezoidal getLowUtilizationUMF() {
		return lowUtilizationUMF;
	}



	public void setLowUtilizationUMF(T1MF_Trapezoidal lowUtilizationUMF) {
		this.lowUtilizationUMF = lowUtilizationUMF;
	}



	public T1MF_Trapezoidal getLowUtilizationLMF() {
		return lowUtilizationLMF;
	}



	public void setLowUtilizationLMF(T1MF_Trapezoidal lowUtilizationLMF) {
		this.lowUtilizationLMF = lowUtilizationLMF;
	}



	public IntervalT2MF_Trapezoidal getLowUtilizationMF() {
		return lowUtilizationMF;
	}



	public void setLowUtilizationMF(IntervalT2MF_Trapezoidal lowUtilizationMF) {
		this.lowUtilizationMF = lowUtilizationMF;
	}



	public T1MF_Trapezoidal getAverageUtilizationUMF() {
		return averageUtilizationUMF;
	}



	public void setAverageUtilizationUMF(T1MF_Trapezoidal averageUtilizationUMF) {
		this.averageUtilizationUMF = averageUtilizationUMF;
	}



	public T1MF_Trapezoidal getAverageUtilizationLMF() {
		return averageUtilizationLMF;
	}



	public void setAverageUtilizationLMF(T1MF_Trapezoidal averageUtilizationLMF) {
		this.averageUtilizationLMF = averageUtilizationLMF;
	}



	public IntervalT2MF_Trapezoidal getAverageUtilizationMF() {
		return averageUtilizationMF;
	}



	public void setAverageUtilizationMF(IntervalT2MF_Trapezoidal averageUtilizationMF) {
		this.averageUtilizationMF = averageUtilizationMF;
	}



	public T1MF_Trapezoidal getHighUtilizationUMF() {
		return highUtilizationUMF;
	}



	public void setHighUtilizationUMF(T1MF_Trapezoidal highUtilizationUMF) {
		this.highUtilizationUMF = highUtilizationUMF;
	}



	public T1MF_Trapezoidal getHighUtilizationLMF() {
		return highUtilizationLMF;
	}



	public void setHighUtilizationLMF(T1MF_Trapezoidal highUtilizationLMF) {
		this.highUtilizationLMF = highUtilizationLMF;
	}



	public IntervalT2MF_Trapezoidal getHighUtilizationMF() {
		return highUtilizationMF;
	}



	public void setHighUtilizationMF(IntervalT2MF_Trapezoidal highUtilizationMF) {
		this.highUtilizationMF = highUtilizationMF;
	}

	
    
   
    
    
}
