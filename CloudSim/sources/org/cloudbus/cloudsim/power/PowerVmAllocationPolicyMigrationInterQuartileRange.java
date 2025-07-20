/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.power;

import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.util.MathUtil;
import org.cloudbus.cloudsim.util.Type2FuzzyLogicHybridEvaluation;

/**
 * The Inter Quartile Range (IQR) VM allocation policy.
 * 
 * If you are using any algorithms, policies or workload included in the power package, please cite
 * the following paper:
 * 
 * Anton Beloglazov, and Rajkumar Buyya, "Optimal Online Deterministic Algorithms and Adaptive
 * Heuristics for Energy and Performance Efficient Dynamic Consolidation of Virtual Machines in
 * Cloud Data Centers", Concurrency and Computation: Practice and Experience (CCPE), Volume 24,
 * Issue 13, Pages: 1397-1420, John Wiley & Sons, Ltd, New York, USA, 2012
 * 
 * @author Anton Beloglazov
 * @since CloudSim Toolkit 3.0
 */
public class PowerVmAllocationPolicyMigrationInterQuartileRange extends
		PowerVmAllocationPolicyMigrationAbstract {

	/** The safety parameter. */
	private double safetyParameter = 0;

	private Map<String, String> vmPolicies;

	/** The fallback vm allocation policy. */
	private PowerVmAllocationPolicyMigrationAbstract fallbackVmAllocationPolicy;
	
	
	
	/**
	 * Instantiates a new power vm allocation policy migration mad.
	 *
	 * @param hostList             the host list
	 * @param vmSelectionPolicy    the vm selection policy
	 * @param safetyParameter      the safety parameter
	 * @param utilizationThreshold the utilization threshold
	 * @param vmPolicies
	 */
	public PowerVmAllocationPolicyMigrationInterQuartileRange(
			List<? extends Host> hostList,
			PowerVmSelectionPolicy vmSelectionPolicy,
			double safetyParameter,
			PowerVmAllocationPolicyMigrationAbstract fallbackVmAllocationPolicy,
			double utilizationThreshold,
			boolean enableFuzzyT2Overload,
			boolean admissibleOrders,
			String orderType,
			String typeIntersection,
			String typeUnion,
			int typeReductionType,
			int typeFuzzySystem,
			Map<String, String> vmPolicies) {
		super(hostList, vmSelectionPolicy, admissibleOrders, orderType, typeIntersection, typeUnion, typeReductionType, typeFuzzySystem, vmPolicies);
		setSafetyParameter(safetyParameter);
		setFallbackVmAllocationPolicy(fallbackVmAllocationPolicy);
		setVmPolicies(vmPolicies);

		
	}

	/**
	 * Instantiates a new power vm allocation policy migration mad.
	 * 
	 * @param hostList the host list
	 * @param vmSelectionPolicy the vm selection policy
	 * @param safetyParameter the safety parameter
	 */
	public PowerVmAllocationPolicyMigrationInterQuartileRange(
			List<? extends Host> hostList,
			PowerVmSelectionPolicy vmSelectionPolicy,
			double safetyParameter,
			PowerVmAllocationPolicyMigrationAbstract fallbackVmAllocationPolicy,
			boolean enableFuzzyT2Overload,
			boolean admissibleOrders, 
			String orderType,
			String typeIntersection,
			String typeUnion,
			int typeReductionType,
			int typeFuzzySystem,
			Map<String, String> vmPolicies) {
		super(hostList, vmSelectionPolicy, admissibleOrders, orderType, typeIntersection, typeUnion, typeReductionType, typeFuzzySystem, vmPolicies);
		setSafetyParameter(safetyParameter);
		setFallbackVmAllocationPolicy(fallbackVmAllocationPolicy);
		setVmPolicies(vmPolicies);

	}

	/**
	 * Checks if is host over utilized.
	 * 
	 * @param _host the _host
	 * @return true, if is host over utilized
	 */
	@Override
	protected boolean isHostOverUtilized(PowerHost host) {
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

		//Alterado por Bastos
		//double utilization = totalRequestedMips / host.getTotalMips();
		double utilization = getLevelOfUse(host, false, 1, 1, getTypeFuzzySystem()) / 10;

		return utilization > upperThreshold;
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
			Log.printLine("The safety parameter cannot be less than zero. The passed value is: "
					+ safetyParameter);
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
	public void setFallbackVmAllocationPolicy(
			PowerVmAllocationPolicyMigrationAbstract fallbackVmAllocationPolicy) {
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

	public Map<String, String> getVmPolicies() {
		return vmPolicies;
	}

	public void setVmPolicies(Map<String, String> vmPolicies) {
		this.vmPolicies = vmPolicies;
	}

	/**
	 * Adicionado por Bastos
	 * @param host
	 * @param plotMF
	 * @param isOverOrUnder
	 * @param isTypeObjective
	 * @param typeFuzzySystem
	 * @return
	 */

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

		//cpu = _host.getUtilizationMips() / _host.getTotalMips(); ///Int-FLBCC _host.getMaxAvailableMips();
		cpu = _host.getUtilizationOfCpu();
		ram = allVmRam / _host.getRam();
		bw = allVmBw / _host.getBw();
		mips = allVmMips / _host.getTotalMips();
		storage = allVmStorage / host.getStorage();

		previousUtilizationOfCpu = _host.getPreviousUtilizationOfCpu();
		energy = _host.getEnergyLinearInterpolation(
				previousUtilizationOfCpu,
				cpu,
				CloudSim.clock() - host.getDatacenter().getLastProcessTime());

		double saida=0;

		if(typeFuzzySystem == 0) {

			Type2FuzzyLogicHybridEvaluation it2 = new Type2FuzzyLogicHybridEvaluation(cpu, ram, bw, mips, storage, energy,
					plotMF,	isOverOrUnder, isTypeObjective, true, getTypeIntersection(), getTypeUnion(), getTypeReductionType(), getVmPolicies());

			it2.getLevelRangeInUse(cpu, ram, bw, mips, storage, energy, getTypeReductionType());

			saida = it2.getxPontual();

		}else if(typeFuzzySystem ==1 ) {

			Type2FuzzyLogicHybridEvaluation it2 = new Type2FuzzyLogicHybridEvaluation(cpu, ram, bw, mips, storage, energy,
					plotMF,	isOverOrUnder, isTypeObjective, true, getTypeIntersection(), getTypeUnion(), getTypeReductionType(), getVmPolicies());

			it2.getLevelRangeInUse(cpu, ram, bw, mips, storage, energy, getTypeReductionType());

			saida = it2.getxPontual();
		}

		return saida;

	}



}
