/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.power;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.util.DaoMembershipDegreesOfType2FuzzySets;
import org.cloudbus.cloudsim.util.MembershipDegreesOfType2FuzzySets;
import org.cloudbus.cloudsim.util.Type2FuzzyLogicEvaluation;
import org.cloudbus.cloudsim.util.Type2FuzzyLogicNDimensionalEvaluation;

/**
 * The Static Threshold (THR) VM allocation policy.
 * 
 * If you are using any algorithms, policies or workload included in the power
 * package, please cite the following paper:
 * 
 * Anton Beloglazov, and Rajkumar Buyya, "Optimal Online Deterministic
 * Algorithms and Adaptive Heuristics for Energy and Performance Efficient
 * Dynamic Consolidation of Virtual Machines in Cloud Data Centers", Concurrency
 * and Computation: Practice and Experience (CCPE), Volume 24, Issue 13, Pages:
 * 1397-1420, John Wiley & Sons, Ltd, New York, USA, 2012
 * 
 * @author Anton Beloglazov
 * @since CloudSim Toolkit 3.0
 */
public class PowerVmAllocationPolicyMigrationStaticThreshold extends PowerVmAllocationPolicyMigrationAbstract {

	/** The utilization threshold. */
	private double utilizationThreshold = 0.9;

	private boolean enableFuzzyT2Overload;
	private String typeIntersection;
	private String typeUnion;
	private int typeReductionType; // CENTEROFSETS = 0; CENTROID = 1; 
	private int typeFuzzySystem;  //  0 - Conventional Type-2 Fuzzy System, 1 - N Dimensional Type-2 Fuzzy Fuzzy System  
	
	private boolean admissibleOrders;
	private String orderType;
	
	private MembershipDegreesOfType2FuzzySets degreesOfMembershipFunctions = new MembershipDegreesOfType2FuzzySets();
	private ArrayList<MembershipDegreesOfType2FuzzySets> listDegrees = new ArrayList<>();
	private DaoMembershipDegreesOfType2FuzzySets daoMembershipDegreesOfType2FuzzySets = new DaoMembershipDegreesOfType2FuzzySets();
	

	/**
	 * Instantiates a new power vm allocation policy migration mad.
	 * 
	 * @param hostList             the host list
	 * @param vmSelectionPolicy    the vm selection policy
	 * @param utilizationThreshold the utilization threshold
	 */
	public PowerVmAllocationPolicyMigrationStaticThreshold(List<? extends Host> hostList,
			PowerVmSelectionPolicy vmSelectionPolicy, double utilizationThreshold, boolean enableFuzzyT2Overload, 
			String typeIntersection, String typeUnion, int typeReductionType, int typeFuzzySystem, boolean admissibleOrders, String orderType) {
		//super(hostList, vmSelectionPolicy, admissibleOrders, orderType, typeIntersection, typeUnion);
		super(hostList, vmSelectionPolicy, admissibleOrders, orderType, typeIntersection, typeUnion, typeReductionType, typeFuzzySystem);
		
		setUtilizationThreshold(utilizationThreshold);
		setEnableFuzzyT2Overload(enableFuzzyT2Overload);
		setTypeIntersection(typeIntersection);
		setTypeUnion(typeUnion);
		setAdmissibleOrders(admissibleOrders);
		setOrderType(orderType);
		setTypeIntersection(typeIntersection);
		setTypeUnion(typeUnion);
		setTypeReductionType(typeReductionType);
		setTypeFuzzySystem(typeFuzzySystem);
	}

	/**
	 * Checks if is host over utilized.
	 * 
	 * @param _host the _host
	 * @return true, if is host over utilized
	 * @throws IOException 
	 */

// Convencional 	
//	@Override
//	protected boolean isHostOverUtilized(PowerHost host) {
//		addHistoryEntry(host, getUtilizationThreshold());
//		double totalRequestedMips = 0;
//		for (Vm vm : host.getVmList()) {
//			totalRequestedMips += vm.getCurrentRequestedTotalMips();
//		}
//		double utilization = totalRequestedMips / host.getTotalMips();
//		return utilization > getUtilizationThreshold();
//	}

// Fuzzy Type-2
//	@Override
//	protected boolean isHostOverUtilized(PowerHost host) {
//		double utilization = getLevelOfUse(host, false, 1, 1) / 10;
//		//n System.out.println("Nível de utilização é: "+utilization);
//		return utilization > getUtilizationThreshold(); 
//	}

	@Override
	protected boolean isHostOverUtilized(PowerHost host)  {
		double utilization = 0;
		
		if (enableFuzzyT2Overload) {
			utilization = getLevelOfUse(host, false, 1, 1, getTypeFuzzySystem()) / 10;

		} else {

			addHistoryEntry(host, getUtilizationThreshold());
			double totalRequestedMips = 0;
			for (Vm vm : host.getVmList()) {
				totalRequestedMips += vm.getCurrentRequestedTotalMips();
			}
			utilization = totalRequestedMips / host.getTotalMips();

		}

		return utilization > getUtilizationThreshold();
	}

	public double getLevelOfUseNew(Host host, boolean plotMF, int isOverOrUnder, int isTypeObjective,
			List<? extends Host> hostList) {

		return 0;
	}

	// boolean plotMF, int isOverOrUnder, int isTypeObjective
	// typeFuzzySystem   (0 - Conventional Type-2 Fuzzy System, 1 - N Dimensional Type-2 Fuzzy Fuzzy System)  
	public double getLevelOfUse(Host host, boolean plotMF, int isOverOrUnder, int isTypeObjective, int typeFuzzySystem)  {
		PowerHostUtilizationHistory _host = (PowerHostUtilizationHistory) host;

		// double cpAvailable = _host.getMaxAvailableMips();
		// double ccAvailable = _host.getBw() - _host.getUtilizationOfBw();
		// double ramAvailable = _host.getRam() - _host.getUtilizationOfRam();

		double maxCPHost = 0;
		double minCCHost = 0;
		double maxRamHost = 0;

		for (int i = 0; i < getHostList().size(); i++) {

			if (maxCPHost < getHostList().get(i).getTotalMips()) {
				maxCPHost = getHostList().get(i).getTotalMips();
			}

			if (minCCHost < getHostList().get(i).getBw()) {
				minCCHost = getHostList().get(i).getBw();
			}

			if (maxRamHost < getHostList().get(i).getRam()) {
				maxRamHost = getHostList().get(i).getRam();
			}

			// System.out.println("maxCPHost: #"+maxCPHost+" minCCHost: #"+minCCHost+ "
			// maxRamHost: #"+maxRamHost);

		}

		double cpStandartScale = 0;
		double ccStandartScale = 0;
		double ramStandartScale = 0;

		cpStandartScale = (_host.getMaxAvailableMips() / maxCPHost) * 10;
		// ccStandartScale = (_host.getUtilizationOfBw()/maxCCHost)*10;
		ccStandartScale = (10 * _host.getUtilizationOfBw()) / minCCHost;
		ramStandartScale = (_host.getUtilizationOfRam() / maxRamHost) * 10;
		
		double saida=0;
		
		//JOptionPane.showMessageDialog(null, "typeFuzzySystem: "+typeFuzzySystem);
		
		if(typeFuzzySystem == 0) {

		Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale, ccStandartScale, ramStandartScale, 
				plotMF,	isOverOrUnder, isTypeObjective, true, getTypeIntersection(), getTypeUnion(), getTypeReductionType());
		//Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale, ccStandartScale, ramStandartScale);
		
		
		//JOptionPane.showMessageDialog(null, "typeReductionType é: "+getTypeReductionType());
		
		
		// System.out.println("maxCPHost: #"+maxCPHost+" minCCHost: #"+minCCHost+ "
		// maxRamHost: #"+maxRamHost + " Level of Use #"+it2.getLevelOfUse());
		// System.out.println("cpStandartScale: #"+cpStandartScale+" ccStandartScale:
		// #"+ccStandartScale+ " ramStandartScale: #"+ramStandartScale+ "
		// it2.getLevelOfUse() #"+(it2.getLevelOfUse()/10));

		// obtendo os graus de pertinecia para a saída utilização
		//it2.getLevelOfUse();
		it2.getLevelRangeInUse(cpStandartScale, ccStandartScale, ramStandartScale, getTypeReductionType());
		
		
       // este bloco estava usando para salvar no arquivo csv	
		//LowULower, LowUUpper, AverageULower, AverageUUpper, HighULower, HighUUpper
	    double xPontual = it2.getxPontual();
        this.degreesOfMembershipFunctions.setLowUtilizationDegreeOfMembershipLowerBound(it2.getLowUtilizationMF().getLowerBound(xPontual));
        this.degreesOfMembershipFunctions.setLowUtilizationDegreeOfMembershipUpperBound(it2.getLowUtilizationMF().getUpperBound(xPontual));
        
        this.degreesOfMembershipFunctions.setAverageUtilizationDegreeOfMembershipLowerBound(it2.getAverageUtilizationMF().getLowerBound(xPontual));
        this.degreesOfMembershipFunctions.setAverageUtilizationDegreeOfMembershipUpperBound(it2.getAverageUtilizationMF().getUpperBound(xPontual));
        
        this.degreesOfMembershipFunctions.setHighUtilizationDegreeOfMembershipLowerBound(it2.getHighUtilizationMF().getLowerBound(xPontual));  //highUtilizationUMF
        this.degreesOfMembershipFunctions.setHighUtilizationDegreeOfMembershipUpperBound(it2.getHighUtilizationMF().getUpperBound(xPontual));  //highUtilizationUMF

        daoMembershipDegreesOfType2FuzzySets.adicionar(this.degreesOfMembershipFunctions);

		saida = it2.getLevelOfUse();

		}else if(typeFuzzySystem ==1 ) {
			
			
			Type2FuzzyLogicNDimensionalEvaluation it2 = new Type2FuzzyLogicNDimensionalEvaluation(cpStandartScale, ccStandartScale, ramStandartScale, 
					plotMF,	isOverOrUnder, isTypeObjective, true, getTypeIntersection(), getTypeUnion(), getTypeReductionType());
			//Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale, ccStandartScale, ramStandartScale);
			
			
			//JOptionPane.showMessageDialog(null, "typeReductionType é: "+getTypeReductionType());
			
			
			// System.out.println("maxCPHost: #"+maxCPHost+" minCCHost: #"+minCCHost+ "
			// maxRamHost: #"+maxRamHost + " Level of Use #"+it2.getLevelOfUse());
			// System.out.println("cpStandartScale: #"+cpStandartScale+" ccStandartScale:
			// #"+ccStandartScale+ " ramStandartScale: #"+ramStandartScale+ "
			// it2.getLevelOfUse() #"+(it2.getLevelOfUse()/10));

			// obtendo os graus de pertinecia para a saída utilização
			//it2.getLevelOfUse();
			it2.getLevelRangeInUse(cpStandartScale, ccStandartScale, ramStandartScale, getTypeReductionType());
			
			
	       // este bloco estava usando para salvar no arquivo csv	
			//LowULower, LowUUpper, AverageULower, AverageUUpper, HighULower, HighUUpper
		    double xPontual = it2.getxPontual();
	        this.degreesOfMembershipFunctions.setLowUtilizationDegreeOfMembershipLowerBound(it2.getLowUtilizationMF().getLowerBound(xPontual));
	        this.degreesOfMembershipFunctions.setLowUtilizationDegreeOfMembershipUpperBound(it2.getLowUtilizationMF().getUpperBound(xPontual));
	        
	        this.degreesOfMembershipFunctions.setAverageUtilizationDegreeOfMembershipLowerBound(it2.getAverageUtilizationMF().getLowerBound(xPontual));
	        this.degreesOfMembershipFunctions.setAverageUtilizationDegreeOfMembershipUpperBound(it2.getAverageUtilizationMF().getUpperBound(xPontual));
	        
	        this.degreesOfMembershipFunctions.setHighUtilizationDegreeOfMembershipLowerBound(it2.getHighUtilizationMF().getLowerBound(xPontual));  //highUtilizationUMF
	        this.degreesOfMembershipFunctions.setHighUtilizationDegreeOfMembershipLowerBound(it2.getHighUtilizationMF().getUpperBound(xPontual));  //highUtilizationUMF

	        daoMembershipDegreesOfType2FuzzySets.adicionar(this.degreesOfMembershipFunctions);

			saida = it2.getLevelOfUse();
		}
		

		


 
		return saida;

	}

	/**
	 * Sets the utilization threshold.
	 * 
	 * @param utilizationThreshold the new utilization threshold
	 */
	protected void setUtilizationThreshold(double utilizationThreshold) {
		this.utilizationThreshold = utilizationThreshold;
	}

	/**
	 * Gets the utilization threshold.
	 * 
	 * @return the utilization threshold
	 */
	protected double getUtilizationThreshold() {
		return utilizationThreshold;
	}

	public boolean isEnableFuzzyT2Overload() {
		return enableFuzzyT2Overload;
	}

	public void setEnableFuzzyT2Overload(boolean enableFuzzyT2Overload) {
		this.enableFuzzyT2Overload = enableFuzzyT2Overload;
	}

	public boolean isAdmissibleOrders() {
		return admissibleOrders;
	}

	public void setAdmissibleOrders(boolean admissibleOrders) {
		this.admissibleOrders = admissibleOrders;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	
	

	public int getTypeReductionType() {
		return typeReductionType;
	}

	public void setTypeReductionType(int typeReductionType) {
		this.typeReductionType = typeReductionType;
	}
	
	

	public int getTypeFuzzySystem() {
		return typeFuzzySystem;
	}

	public void setTypeFuzzySystem(int typeFuzzySystem) {
		this.typeFuzzySystem = typeFuzzySystem;
	}

	public MembershipDegreesOfType2FuzzySets getDegreesOfMembershipFunctions() {
		return degreesOfMembershipFunctions;
	}

	public void setDegreesOfMembershipFunctions(MembershipDegreesOfType2FuzzySets degreesOfMembershipFunctions) {
		this.degreesOfMembershipFunctions = degreesOfMembershipFunctions;
	}

	public ArrayList<MembershipDegreesOfType2FuzzySets> getListDegrees() {
		return listDegrees;
	}

	public void setListDegrees(ArrayList<MembershipDegreesOfType2FuzzySets> listDegrees) {
		this.listDegrees = listDegrees;
	}
	
	
	
	

}
