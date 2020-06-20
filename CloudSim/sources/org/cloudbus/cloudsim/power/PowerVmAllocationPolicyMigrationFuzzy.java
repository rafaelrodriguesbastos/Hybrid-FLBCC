/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.power;

import java.util.List;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.util.Type2FuzzyLogicEvaluation;
import org.cloudbus.cloudsim.util.MathUtil;

/**
 * The Fuzzy Type-2 VM allocation policy.
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
public class PowerVmAllocationPolicyMigrationFuzzy extends PowerVmAllocationPolicyMigrationAbstract {

	/** The safety parameter. */
	private double safetyParameter = 0;

	/** The fallback vm allocation policy. */
	private PowerVmAllocationPolicyMigrationAbstract fallbackVmAllocationPolicy;
	
	

	/**
	 * Instantiates a new power vm allocation policy migration mad.
	 * 
	 * @param hostList             the host list
	 * @param vmSelectionPolicy    the vm selection policy
	 * @param safetyParameter      the safety parameter
	 * @param utilizationThreshold the utilization threshold
	 */
	public PowerVmAllocationPolicyMigrationFuzzy(List<? extends Host> hostList,
			PowerVmSelectionPolicy vmSelectionPolicy, double safetyParameter,
			PowerVmAllocationPolicyMigrationAbstract fallbackVmAllocationPolicy, double utilizationThreshold,boolean admissibleOrders,
			String orderType, String typeIntersection, String typeUnion) {
		super(hostList, vmSelectionPolicy, admissibleOrders, orderType, typeIntersection, typeUnion);
		setSafetyParameter(safetyParameter);
		setFallbackVmAllocationPolicy(fallbackVmAllocationPolicy);
	}

	/**
	 * Instantiates a new power vm allocation policy migration mad.
	 * 
	 * @param hostList          the host list
	 * @param vmSelectionPolicy the vm selection policy
	 * @param safetyParameter   the safety parameter
	 */
	public PowerVmAllocationPolicyMigrationFuzzy(List<? extends Host> hostList,
			PowerVmSelectionPolicy vmSelectionPolicy, double safetyParameter,
			PowerVmAllocationPolicyMigrationAbstract fallbackVmAllocationPolicy, 
			boolean admissibleOrders, String orderType, String typeIntersection, String typeUnion) {
		super(hostList, vmSelectionPolicy, admissibleOrders, orderType, typeIntersection, typeUnion);
		setSafetyParameter(safetyParameter);
		setFallbackVmAllocationPolicy(fallbackVmAllocationPolicy);
	}

	/**
	 * Checks if is host over utilized.
	 * 
	 * @param _host the _host
	 * @return true, if is host over utilized
	 */
	/*
	 * @Override protected boolean isHostOverUtilized(PowerHost host) {
	 * PowerHostUtilizationHistory _host = (PowerHostUtilizationHistory) host;
	 * double upperThreshold = 0; try { upperThreshold = 1 - getSafetyParameter() *
	 * getHostUtilizationIqr(_host); } catch (IllegalArgumentException e) { return
	 * getFallbackVmAllocationPolicy().isHostOverUtilized(host); }
	 * addHistoryEntry(host, upperThreshold); double totalRequestedMips = 0; for (Vm
	 * vm : host.getVmList()) { totalRequestedMips +=
	 * vm.getCurrentRequestedTotalMips(); } double utilization = totalRequestedMips
	 * / host.getTotalMips(); return utilization > upperThreshold; }
	 */

	/**
	 * Checks if is host over utilized.
	 * 
	 * @param _host the _host
	 * @return true, if is host over utilized
	 */	
	@Override
	protected boolean isHostOverUtilized(PowerHost host) {
		
		/*
		PowerHostUtilizationHistory _host = (PowerHostUtilizationHistory) host;
		double upperThreshold = 0;
		try {
			upperThreshold = 1 - getSafetyParameter() * getHostUtilizationIqr(_host);
		} catch (IllegalArgumentException e) {
			return getFallbackVmAllocationPolicy().isHostOverUtilized(host);
		}
		addHistoryEntry(host, upperThreshold);
		double totalRequestedMips = 0;
		for (Vm vm : host.getVmList()) {
			totalRequestedMips += vm.getCurrentRequestedTotalMips();
		}
		double utilization = totalRequestedMips / host.getTotalMips();
		return utilization > upperThreshold;
		*/
		
		PowerHostUtilizationHistory _host = (PowerHostUtilizationHistory) host;
		
		//double cpAvailable = _host.getMaxAvailableMips();
		//double ccAvailable = _host.getBw() - _host.getUtilizationOfBw();
		//double ramAvailable = _host.getRam() - _host.getUtilizationOfRam();
		
		double maxCPHost =0;
		double maxCCHost=0;
		double maxRamHost=0;
		
		for(int i=0; i<getHostList().size(); i++) {
			
			if (maxCPHost < getHostList().get(i).getTotalMips()) {
				maxCPHost = getHostList().get(i).getTotalMips();
			}
			
			if (maxCCHost < getHostList().get(i).getBw()) {
				maxCCHost = getHostList().get(i).getBw();
			}
			
			if (maxRamHost < getHostList().get(i).getRam()) {
				maxRamHost = getHostList().get(i).getRam();
			}
			
		}
		
		double cpStandartScale = 0;
		double ccStandartScale = 0;
		double ramStandartScale=0;
		
		cpStandartScale = (_host.getMaxAvailableMips()/maxCPHost)*10;
		ccStandartScale = (_host.getUtilizationOfBw()/maxCCHost)*10;
		ramStandartScale = (_host.getUtilizationOfRam()/maxRamHost)*10;
		
		Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale,ccStandartScale,ramStandartScale, 
				false, 1, 1, false, "", "");
		
		// return utilization > getUtilizationThreshold();
		//double utililization = it2.getPriority();
		return true;
	}

	/**
	 * Gets the host utilization iqr.
	 * 
	 * @param host the host
	 * @return the host utilization iqr
	 */
	protected double getHostUtilizationIqr(PowerHostUtilizationHistory host) throws IllegalArgumentException {
		double[] data = host.getUtilizationHistory();
		if (MathUtil.countNonZeroBeginning(data) >= 12) { // 12 has been suggested as a safe value
			return MathUtil.iqr(data);
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Sets the safety parameter.
	 * 
	 * @param safetyParameter the new safety parameter
	 */
	protected void setSafetyParameter(double safetyParameter) {
		if (safetyParameter < 0) {
			Log.printLine("The safety parameter cannot be less than zero. The passed value is: " + safetyParameter);
			System.exit(0);
		}
		this.safetyParameter = safetyParameter;
	}

	/**
	 * Gets the safety parameter.
	 * 
	 * @return the safety parameter
	 */
	protected double getSafetyParameter() {
		return safetyParameter;
	}

	/**
	 * Sets the fallback vm allocation policy.
	 * 
	 * @param fallbackVmAllocationPolicy the new fallback vm allocation policy
	 */
	public void setFallbackVmAllocationPolicy(PowerVmAllocationPolicyMigrationAbstract fallbackVmAllocationPolicy) {
		this.fallbackVmAllocationPolicy = fallbackVmAllocationPolicy;
	}

	/**
	 * Gets the fallback vm allocation policy.
	 * 
	 * @return the fallback vm allocation policy
	 */
	public PowerVmAllocationPolicyMigrationAbstract getFallbackVmAllocationPolicy() {
		return fallbackVmAllocationPolicy;
	}

	
	

}
