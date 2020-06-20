/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.power;

import java.util.List;

import javax.swing.JOptionPane;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.util.Type2FuzzyLogicEvaluation;

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
	
	private boolean admissibleOrders;
	private String orderType;
	
	

	/**
	 * Instantiates a new power vm allocation policy migration mad.
	 * 
	 * @param hostList             the host list
	 * @param vmSelectionPolicy    the vm selection policy
	 * @param utilizationThreshold the utilization threshold
	 */
	public PowerVmAllocationPolicyMigrationStaticThreshold(List<? extends Host> hostList,
			PowerVmSelectionPolicy vmSelectionPolicy, double utilizationThreshold, boolean enableFuzzyT2Overload, String typeIntersection, String typeUnion,
			boolean admissibleOrders, String orderType) {
		super(hostList, vmSelectionPolicy, admissibleOrders, orderType, typeIntersection, typeUnion);
		setUtilizationThreshold(utilizationThreshold);
		setEnableFuzzyT2Overload(enableFuzzyT2Overload);
		setTypeIntersection(typeIntersection);
		setTypeUnion(typeUnion);
		setAdmissibleOrders(admissibleOrders);
		setOrderType(orderType);
		setTypeIntersection(typeIntersection);
		setTypeUnion(typeUnion);
	}

	/**
	 * Checks if is host over utilized.
	 * 
	 * @param _host the _host
	 * @return true, if is host over utilized
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
	protected boolean isHostOverUtilized(PowerHost host) {
		double utilization = 0;
		
		if (enableFuzzyT2Overload) {
			utilization = getLevelOfUse(host, false, 1, 1) / 10;

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
	public double getLevelOfUse(Host host, boolean plotMF, int isOverOrUnder, int isTypeObjective) {
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

		Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale, ccStandartScale, ramStandartScale, 
				plotMF,	isOverOrUnder, isTypeObjective, false, getTypeIntersection(), getTypeUnion());
		
		//JOptionPane.showMessageDialog(null, "Interseção é"+getTypeIntersection());
		
		
		// System.out.println("maxCPHost: #"+maxCPHost+" minCCHost: #"+minCCHost+ "
		// maxRamHost: #"+maxRamHost + " Level of Use #"+it2.getLevelOfUse());
		// System.out.println("cpStandartScale: #"+cpStandartScale+" ccStandartScale:
		// #"+ccStandartScale+ " ramStandartScale: #"+ramStandartScale+ "
		// it2.getLevelOfUse() #"+(it2.getLevelOfUse()/10));

		return it2.getLevelOfUse();

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
	
	
	
	

}
