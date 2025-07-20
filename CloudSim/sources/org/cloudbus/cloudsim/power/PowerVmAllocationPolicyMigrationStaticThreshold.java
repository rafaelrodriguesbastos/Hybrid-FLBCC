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
import java.util.Map;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.util.DaoMembershipDegreesOfType2FuzzySets;
import org.cloudbus.cloudsim.util.MembershipDegreesOfType2FuzzySets;
import org.cloudbus.cloudsim.util.Type2FuzzyLogicHybridEvaluation;

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
	private Map<String, String> vmPolicies;
	
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
	 * @param vmPolicies
	 */
	public PowerVmAllocationPolicyMigrationStaticThreshold(List<? extends Host> hostList,
														   PowerVmSelectionPolicy vmSelectionPolicy, double utilizationThreshold, boolean enableFuzzyT2Overload,
														   String typeIntersection, String typeUnion, int typeReductionType, int typeFuzzySystem, boolean admissibleOrders, String orderType, Map<String, String> vmPolicies) {
		//super(hostList, vmSelectionPolicy, admissibleOrders, orderType, typeIntersection, typeUnion);
		super(hostList, vmSelectionPolicy, admissibleOrders, orderType, typeIntersection, typeUnion, typeReductionType, typeFuzzySystem, vmPolicies);
		
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
		setVmPolicies(vmPolicies);
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

		double cpu = 0, ram = 0, bw = 0, mips = 0, storage = 0, energy = 0, previousUtilizationOfCpu = 0;

		double allVmRam = 0;
		double allVmBw = 0;
		double allVmMips = 0;
		double allVmStorage = 0;

		for (Vm vm : _host.getVmList()) {
			allVmRam += vm.getRam();
			if (allVmBw < vm.getBw()) {
				allVmBw = vm.getBw();
			};
			allVmMips += vm.getMips();
			allVmStorage += vm.getSize();
		}

		cpu = _host.getUtilizationOfCpu();
		ram = allVmRam / _host.getRam();
		bw = allVmBw / _host.getBw();
		mips = allVmMips / _host.getTotalMips();
		storage = allVmStorage / host.getStorage();

		cpu = _host.getUtilizationOfCpu();
		//cpu = _host.getUtilizationMips() / _host.getTotalMips(); ///Int-FLBCC _host.getMaxAvailableMips();
		storage = allVmStorage / _host.getStorage();

		previousUtilizationOfCpu = _host.getPreviousUtilizationOfCpu();
		energy = _host.getEnergyLinearInterpolation(
				previousUtilizationOfCpu,
				cpu,
				CloudSim.clock() - host.getDatacenter().getLastProcessTime());

		double saida=0;
		
		//JOptionPane.showMessageDialog(null, "typeFuzzySystem: "+typeFuzzySystem);
		
		if(typeFuzzySystem == 0) {

		Type2FuzzyLogicHybridEvaluation it2 = new Type2FuzzyLogicHybridEvaluation(cpu, ram, bw, mips, storage, energy,
				plotMF,	isOverOrUnder, isTypeObjective, true, getTypeIntersection(), getTypeUnion(), getTypeReductionType(), getVmPolicies());

		it2.getLevelRangeInUse(cpu, ram, bw, mips, storage, energy, getTypeReductionType());
		
		
       // este bloco estava usando para salvar no arquivo csv	
		//LowULower, LowUUpper, AverageULower, AverageUUpper, HighULower, HighUUpper
	    double xPontual = it2.getxPontual();
        this.degreesOfMembershipFunctions.setUnderUtilizationDegreeOfMembershipLowerBound(it2.getUnderUtilizationMF().getLowerBound(xPontual));
        this.degreesOfMembershipFunctions.setUnderUtilizationDegreeOfMembershipUpperBound(it2.getUnderUtilizationMF().getUpperBound(xPontual));
        
        this.degreesOfMembershipFunctions.setNormalUtilizationDegreeOfMembershipLowerBound(it2.getNormalUtilizationMF().getLowerBound(xPontual));
        this.degreesOfMembershipFunctions.setNormalUtilizationDegreeOfMembershipUpperBound(it2.getNormalUtilizationMF().getUpperBound(xPontual));
        
        this.degreesOfMembershipFunctions.setOverUtilizationDegreeOfMembershipLowerBound(it2.getOverUtilizationMF().getLowerBound(xPontual));  //highUtilizationUMF
        this.degreesOfMembershipFunctions.setOverUtilizationDegreeOfMembershipUpperBound(it2.getOverUtilizationMF().getUpperBound(xPontual));  //highUtilizationUMF

        daoMembershipDegreesOfType2FuzzySets.adicionar(this.degreesOfMembershipFunctions);

		saida = it2.getLevelOfUse();

		}else if(typeFuzzySystem ==1 ) {
			
			
			Type2FuzzyLogicHybridEvaluation it2 = new Type2FuzzyLogicHybridEvaluation(cpu, ram, bw, mips, storage, energy,
					plotMF,	isOverOrUnder, isTypeObjective, true, getTypeIntersection(), getTypeUnion(), getTypeReductionType(), getVmPolicies());
			//Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale, ccStandartScale, ramStandartScale);
			
			
			//JOptionPane.showMessageDialog(null, "typeReductionType é: "+getTypeReductionType());
			
			
			// System.out.println("maxCPHost: #"+maxCPHost+" minCCHost: #"+minCCHost+ "
			// maxRamHost: #"+maxRamHost + " Level of Use #"+it2.getLevelOfUse());
			// System.out.println("cpStandartScale: #"+cpStandartScale+" ccStandartScale:
			// #"+ccStandartScale+ " ramStandartScale: #"+ramStandartScale+ "
			// it2.getLevelOfUse() #"+(it2.getLevelOfUse()/10));

			// obtendo os graus de pertinecia para a saída utilização
			//it2.getLevelOfUse();
			it2.getLevelRangeInUse(cpu, ram, bw, mips, storage, energy, getTypeReductionType());
			
			
	       // este bloco estava usando para salvar no arquivo csv	
			//LowULower, LowUUpper, AverageULower, AverageUUpper, HighULower, HighUUpper
		    double xPontual = it2.getxPontual();
	        this.degreesOfMembershipFunctions.setUnderUtilizationDegreeOfMembershipLowerBound(it2.getUnderUtilizationMF().getLowerBound(xPontual));
	        this.degreesOfMembershipFunctions.setUnderUtilizationDegreeOfMembershipUpperBound(it2.getUnderUtilizationMF().getUpperBound(xPontual));
	        
	        this.degreesOfMembershipFunctions.setNormalUtilizationDegreeOfMembershipLowerBound(it2.getNormalUtilizationMF().getLowerBound(xPontual));
	        this.degreesOfMembershipFunctions.setNormalUtilizationDegreeOfMembershipUpperBound(it2.getNormalUtilizationMF().getUpperBound(xPontual));
	        
	        this.degreesOfMembershipFunctions.setOverUtilizationDegreeOfMembershipLowerBound(it2.getOverUtilizationMF().getLowerBound(xPontual));  //highUtilizationUMF
	        this.degreesOfMembershipFunctions.setOverUtilizationDegreeOfMembershipLowerBound(it2.getOverUtilizationMF().getUpperBound(xPontual));  //highUtilizationUMF

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


	public Map<String, String> getVmPolicies() {
		return vmPolicies;
	}

	public void setVmPolicies(Map<String, String> vmPolicies) {
		this.vmPolicies = vmPolicies;
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
