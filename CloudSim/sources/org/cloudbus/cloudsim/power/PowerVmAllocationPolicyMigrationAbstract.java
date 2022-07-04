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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.HostUseLevel;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.lists.PowerVmList;
import org.cloudbus.cloudsim.util.ExecutionTimeMeasurer;
import org.cloudbus.cloudsim.util.Type2FuzzyLogicEvaluation;



/**
 * The class of an abstract power-aware VM allocation policy that dynamically
 * optimizes the VM allocation using migration.
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
public abstract class PowerVmAllocationPolicyMigrationAbstract extends PowerVmAllocationPolicyAbstract {

	/** The vm selection policy. */
	private PowerVmSelectionPolicy vmSelectionPolicy;

	/** The saved allocation. */
	private final List<Map<String, Object>> savedAllocation = new ArrayList<Map<String, Object>>();

	/** The utilization history. */
	private final Map<Integer, List<Double>> utilizationHistory = new HashMap<Integer, List<Double>>();

	/** The metric history. */
	private final Map<Integer, List<Double>> metricHistory = new HashMap<Integer, List<Double>>();

	/** The time history. */
	private final Map<Integer, List<Double>> timeHistory = new HashMap<Integer, List<Double>>();

	/** The execution time history vm selection. */
	private final List<Double> executionTimeHistoryVmSelection = new LinkedList<Double>();

	/** The execution time history host selection. */
	private final List<Double> executionTimeHistoryHostSelection = new LinkedList<Double>();

	/** The execution time history vm reallocation. */
	private final List<Double> executionTimeHistoryVmReallocation = new LinkedList<Double>();

	/** The execution time history total. */
	private final List<Double> executionTimeHistoryTotal = new LinkedList<Double>();

	private double outputXValue;

	private double outputYValue;

	private double maxCPHostOfArchitecture = 2660;

	private double minCCHostOfArchitecture = 1000000;

	private double maxRamHostOfArchitecture = 393216;

	private ArrayList<HostUseLevel> hostUseLevel = new ArrayList<>();

	private boolean admissibleOrders = true;

	private String orderType = "XuAndYager";
	
	private String typeIntersection="";
	private String typeUnion="";
	private int typeReductionType; // CENTEROFSETS = 0; CENTROID = 1;
	private int typeFuzzySystem;  //  0 - Conventional Type-2 Fuzzy System, 1 - N Dimensional Type-2 Fuzzy Fuzzy System  

	/**
	 * Instantiates a new power vm allocation policy migration abstract.
	 * 
	 * @param hostList          the host list
	 * @param vmSelectionPolicy the vm selection policy
	 */
	public PowerVmAllocationPolicyMigrationAbstract(List<? extends Host> hostList,
			PowerVmSelectionPolicy vmSelectionPolicy, boolean admissibleOrders, String orderType,
			String typeIntersection, String typeUnion, int typeReductionType, int typeFuzzySystem) {
		super(hostList);
		setVmSelectionPolicy(vmSelectionPolicy);
		setAdmissibleOrders(admissibleOrders);
		setOrderType(orderType);
		setTypeIntersection(typeIntersection);
		setTypeUnion(typeUnion);
		setTypeReductionType(typeReductionType);
		setTypeFuzzySystem(typeFuzzySystem);
		
		// Carrega os melhores valores da aquitetura
		// getMaxValuesArchicture(getHostList());

	}

	/**
	 * Optimize allocation of the VMs according to current utilization.
	 * 
	 * @param vmList the vm list
	 * 
	 * @return the array list< hash map< string, object>>
	 */
	@Override
	public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
		ExecutionTimeMeasurer.start("optimizeAllocationTotal");

		ExecutionTimeMeasurer.start("optimizeAllocationHostSelection");
		List<PowerHostUtilizationHistory> overUtilizedHosts = getOverUtilizedHosts();
		getExecutionTimeHistoryHostSelection().add(ExecutionTimeMeasurer.end("optimizeAllocationHostSelection"));

		printOverUtilizedHosts(overUtilizedHosts);

		saveAllocation();

		ExecutionTimeMeasurer.start("optimizeAllocationVmSelection");
		List<? extends Vm> vmsToMigrate = getVmsToMigrateFromHosts(overUtilizedHosts);
		getExecutionTimeHistoryVmSelection().add(ExecutionTimeMeasurer.end("optimizeAllocationVmSelection"));

		Log.printLine("Reallocation of VMs from the over-utilized hosts:");
		ExecutionTimeMeasurer.start("optimizeAllocationVmReallocation");
		List<Map<String, Object>> migrationMap = getNewVmPlacement(vmsToMigrate, new HashSet<Host>(overUtilizedHosts));
		getExecutionTimeHistoryVmReallocation().add(ExecutionTimeMeasurer.end("optimizeAllocationVmReallocation"));
		Log.printLine();

		migrationMap.addAll(getMigrationMapFromUnderUtilizedHosts(overUtilizedHosts));

		restoreAllocation();

		getExecutionTimeHistoryTotal().add(ExecutionTimeMeasurer.end("optimizeAllocationTotal"));

		return migrationMap;
	}

	/**
	 * Gets the migration map from under utilized hosts.
	 * 
	 * @param overUtilizedHosts the over utilized hosts
	 * @return the migration map from under utilized hosts
	 */
	protected List<Map<String, Object>> getMigrationMapFromUnderUtilizedHosts(
			List<PowerHostUtilizationHistory> overUtilizedHosts) {
		List<Map<String, Object>> migrationMap = new LinkedList<Map<String, Object>>();
		List<PowerHost> switchedOffHosts = getSwitchedOffHosts();

		// over-utilized hosts + hosts that are selected to migrate VMs to from
		// over-utilized hosts
		Set<PowerHost> excludedHostsForFindingUnderUtilizedHost = new HashSet<PowerHost>();
		excludedHostsForFindingUnderUtilizedHost.addAll(overUtilizedHosts);
		excludedHostsForFindingUnderUtilizedHost.addAll(switchedOffHosts);
		excludedHostsForFindingUnderUtilizedHost.addAll(extractHostListFromMigrationMap(migrationMap));

		// over-utilized + under-utilized hosts
		Set<PowerHost> excludedHostsForFindingNewVmPlacement = new HashSet<PowerHost>();
		excludedHostsForFindingNewVmPlacement.addAll(overUtilizedHosts);
		excludedHostsForFindingNewVmPlacement.addAll(switchedOffHosts);

		int numberOfHosts = getHostList().size();

		while (true) {
			if (numberOfHosts == excludedHostsForFindingUnderUtilizedHost.size()) {
				break;
			}

			PowerHost underUtilizedHost = getUnderUtilizedHost(excludedHostsForFindingUnderUtilizedHost);
			if (underUtilizedHost == null) {
				break;
			}

			Log.printLine("Under-utilized host: host #" + underUtilizedHost.getId() + "\n");

			excludedHostsForFindingUnderUtilizedHost.add(underUtilizedHost);
			excludedHostsForFindingNewVmPlacement.add(underUtilizedHost);

			List<? extends Vm> vmsToMigrateFromUnderUtilizedHost = getVmsToMigrateFromUnderUtilizedHost(
					underUtilizedHost);
			if (vmsToMigrateFromUnderUtilizedHost.isEmpty()) {
				continue;
			}

			Log.print("Reallocation of VMs from the under-utilized host: ");
			if (!Log.isDisabled()) {
				for (Vm vm : vmsToMigrateFromUnderUtilizedHost) {
					Log.print(vm.getId() + " ");
				}
			}
			Log.printLine();

			List<Map<String, Object>> newVmPlacement = getNewVmPlacementFromUnderUtilizedHost(
					vmsToMigrateFromUnderUtilizedHost, excludedHostsForFindingNewVmPlacement);

			excludedHostsForFindingUnderUtilizedHost.addAll(extractHostListFromMigrationMap(newVmPlacement));

			migrationMap.addAll(newVmPlacement);
			Log.printLine();
		}

		return migrationMap;
	}

	/**
	 * Prints the over utilized hosts.
	 * 
	 * @param overUtilizedHosts the over utilized hosts
	 */
	protected void printOverUtilizedHosts(List<PowerHostUtilizationHistory> overUtilizedHosts) {
		if (!Log.isDisabled()) {
			Log.printLine("Over-utilized hosts:");
			for (PowerHostUtilizationHistory host : overUtilizedHosts) {
				Log.printLine("Host #" + host.getId());
			}
			Log.printLine();
		}
	}

	
	
	public PowerHost getPowerHostBestUseLevel(String strOrderType, ArrayList<HostUseLevel> listHostUseLevel) {

		double tempXValue = 0.0;
		double tempYValue = 0.0;
		PowerHost outputHost = null;

		for (int i = 0; i < (listHostUseLevel.size() -1); i++) {
			// tempHost = listHostUseLevel.get(i).getPowerHost();

			if (strOrderType.equalsIgnoreCase("Lex1")) {

				if ((tempXValue < listHostUseLevel.get(i).getOutputXValue())
						|| (tempXValue == listHostUseLevel.get(i).getOutputXValue()
								& tempYValue <= listHostUseLevel.get(i).getOutputYValue())) {

					tempXValue = listHostUseLevel.get(i).getOutputXValue();
					tempYValue = listHostUseLevel.get(i).getOutputYValue();
					outputHost = listHostUseLevel.get(i).getPowerHost();

				}

			} else if (strOrderType.equalsIgnoreCase("Lex2")) {

				if ((tempYValue < listHostUseLevel.get(i).getOutputYValue())
						|| (tempYValue == listHostUseLevel.get(i).getOutputYValue()
								& tempXValue <= listHostUseLevel.get(i).getOutputXValue())) {

					tempXValue = listHostUseLevel.get(i).getOutputXValue();
					tempYValue = listHostUseLevel.get(i).getOutputYValue();
					outputHost = listHostUseLevel.get(i).getPowerHost();

				}

			} else if (strOrderType.equalsIgnoreCase("XuAndYager")) {

				double sumTempXuAndYager = tempXValue + tempYValue;
				double sumListHostUseLevel = listHostUseLevel.get(i).getOutputXValue()
						+ listHostUseLevel.get(i).getOutputYValue();

				// System.out.println("sumTempXuAndYager é: "+sumTempXuAndYager+"
				// sumListHostUseLevel é: "+sumListHostUseLevel);

				double diffTempXuAndYager = tempYValue - tempXValue;
				double diffListHostUseLevel = listHostUseLevel.get(i).getOutputYValue()
						- listHostUseLevel.get(i).getOutputXValue();

				if ((sumTempXuAndYager < sumListHostUseLevel) || ((sumTempXuAndYager == sumListHostUseLevel)
						& (diffTempXuAndYager <= diffListHostUseLevel))) {

					tempXValue = listHostUseLevel.get(i).getOutputXValue();
					tempYValue = listHostUseLevel.get(i).getOutputYValue();
					outputHost = listHostUseLevel.get(i).getPowerHost();

				}

			}
			else if (strOrderType.equalsIgnoreCase("A")) {
				
				//double sumTempXuAndYager = tempXValue + tempYValue;
				
				//tempXValue = listHostUseLevel.get(i).getOutputXValue();
				//tempYValue = listHostUseLevel.get(i).getOutputYValue();
				//outputHost = listHostUseLevel.get(i).getPowerHost();
				
				//double xInf =0;
				//double xSup =0;
				
				double xInfIntA =0; double xSupIntA =0;
				double yInfIntB =0; double ySupIntB =0;
				
				String xValIntATemp = ""; //String xSupIntATemp = "";
				String yValIntBTemp = ""; //String ySupIntBTemp = "";
				
				Integer xInfPos1 =0; Integer xInfPos2 =0; Integer xInfPos3 =0; Integer xInfPos4 =0; 
				Integer xSupPos1 =0; Integer xSupPos2 =0; Integer xSupPos3 =0; Integer xSupPos4 =0;
				
				Integer yInfPos1 =0; Integer yInfPos2 =0; Integer yInfPos3 =0; Integer yInfPos4 =0;
				Integer ySupPos1 =0; Integer ySupPos2 =0; Integer ySupPos3 =0; Integer ySupPos4 =0;
				

				
				xInfIntA = listHostUseLevel.get(i).getOutputXValue()/10;
				xSupIntA = listHostUseLevel.get(i).getOutputYValue()/10;
				
				yInfIntB = listHostUseLevel.get(i+1).getOutputXValue()/10;
				ySupIntB = listHostUseLevel.get(i+1).getOutputYValue()/10;
				
				
				
				
				//xInf = listHostUseLevel.get(i).getOutputXValue();
				//yInf = listHostUseLevel.get(i).getOutputYValue();
				
				
				//String strInfPos1 =  Double.toString(xInf).substring(2,3);
				//String strInfPos2 =  Double.toString(xInf).substring(3,4);
				//String strInfPos3 =  Double.toString(xInf).substring(4,5);
				//String strInfPos4 =  Double.toString(xInf).substring(5,6);
				
				
				/*xInfPos1 = Double.parseDouble(Double.toString(xInfIntA).substring(2,3));
				xInfPos2 = Double.parseDouble(Double.toString(xInfIntA).substring(3,4));
				xInfPos3 = Double.parseDouble(Double.toString(xInfIntA).substring(4,5));
				xInfPos4 = Double.parseDouble(Double.toString(xInfIntA).substring(5,6)); 
				
				xSupPos1 = Double.parseDouble(Double.toString(xSupIntA).substring(2,3));
				xSupPos2 = Double.parseDouble(Double.toString(xSupIntA).substring(3,4));
				xSupPos3 = Double.parseDouble(Double.toString(xSupIntA).substring(4,5));
				xSupPos4 = Double.parseDouble(Double.toString(xSupIntA).substring(5,6));
				
				yInfPos1 = Double.parseDouble(Double.toString(yInfIntB).substring(2,3));
				yInfPos2 = Double.parseDouble(Double.toString(yInfIntB).substring(3,4));
				yInfPos3 = Double.parseDouble(Double.toString(yInfIntB).substring(4,5));
				yInfPos4 = Double.parseDouble(Double.toString(yInfIntB).substring(5,6));
				
				ySupPos1 = Double.parseDouble(Double.toString(ySupIntB).substring(2,3));
				ySupPos2 = Double.parseDouble(Double.toString(ySupIntB).substring(3,4));
				ySupPos3 = Double.parseDouble(Double.toString(ySupIntB).substring(4,5));
				ySupPos4 = Double.parseDouble(Double.toString(ySupIntB).substring(5,6));*/
				
				xInfPos1 = Integer.parseInt(Double.toString(xInfIntA).substring(2,3));
				xInfPos2 = Integer.parseInt(Double.toString(xInfIntA).substring(3,4));
				xInfPos3 = Integer.parseInt(Double.toString(xInfIntA).substring(4,5));
				xInfPos4 = Integer.parseInt(Double.toString(xInfIntA).substring(5,6)); 
				
				xSupPos1 = Integer.parseInt(Double.toString(xSupIntA).substring(2,3));
				xSupPos2 = Integer.parseInt(Double.toString(xSupIntA).substring(3,4));
				xSupPos3 = Integer.parseInt(Double.toString(xSupIntA).substring(4,5));
				xSupPos4 = Integer.parseInt(Double.toString(xSupIntA).substring(5,6));
				
				yInfPos1 = Integer.parseInt(Double.toString(yInfIntB).substring(2,3));
				yInfPos2 = Integer.parseInt(Double.toString(yInfIntB).substring(3,4));
				yInfPos3 = Integer.parseInt(Double.toString(yInfIntB).substring(4,5));
				yInfPos4 = Integer.parseInt(Double.toString(yInfIntB).substring(5,6));
				
				ySupPos1 = Integer.parseInt(Double.toString(ySupIntB).substring(2,3));
				ySupPos2 = Integer.parseInt(Double.toString(ySupIntB).substring(3,4));
				ySupPos3 = Integer.parseInt(Double.toString(ySupIntB).substring(4,5));
				ySupPos4 = Integer.parseInt(Double.toString(ySupIntB).substring(5,6));
				
				
				
				/*JOptionPane.showMessageDialog(null, " xInf = "+xInf+ " strInfPos1 = "+xInfPos1 + 
						" strInfPos2 = "+xInfPos2+ " strInfPos3 = "+xInfPos3 + " strInfPos4 = "+xInfPos4 + " \n "+
						"yInf = "+ yInf + " yInfPos1 = "+ yInfPos1 + " yInfPos2 = " + yInfPos2 + " yInfPos3 = " + yInfPos3 + " yInfPos4 = " + yInfPos4);*/
								
				
				/*String strXInfPos1 =  Double.toString(xInfPos1);
				String strXSupPos1 =  Double.toString(xSupPos1);
				String strXInfPos2 = Double.toString(xInfPos2);
				String strXSupPos2 = Double.toString(xSupPos2);
				String strXInfPos3 = Double.toString(xInfPos3);
				String strXSupPos3 = Double.toString(xSupPos3);
				String strXInfPos4 = Double.toString(xInfPos4);
				String strXSupPos4 = Double.toString(xSupPos4);
				
				String strYInfPos1 =  Double.toString(yInfPos1);
				String strYSupPos1 =  Double.toString(ySupPos1);
				String strYInfPos2 = Double.toString(yInfPos2);
				String strYSupPos2 = Double.toString(ySupPos2);
				String strYInfPos3 = Double.toString(yInfPos3);
				String strYSupPos3 = Double.toString(ySupPos3);
				String strYInfPos4 = Double.toString(yInfPos4);
				String strYSupPos4 = Double.toString(ySupPos4);
				*/
				
				String strXInfPos1 =  Integer.toString(xInfPos1);
				String strXSupPos1 =  Integer.toString(xSupPos1);
				String strXInfPos2 = Integer.toString(xInfPos2);
				String strXSupPos2 = Integer.toString(xSupPos2);
				String strXInfPos3 = Integer.toString(xInfPos3);
				String strXSupPos3 = Integer.toString(xSupPos3);
				String strXInfPos4 = Integer.toString(xInfPos4);
				String strXSupPos4 = Integer.toString(xSupPos4);
				
				String strYInfPos1 =  Integer.toString(yInfPos1);
				String strYSupPos1 =  Integer.toString(ySupPos1);
				String strYInfPos2 = Integer.toString(yInfPos2);
				String strYSupPos2 = Integer.toString(ySupPos2);
				String strYInfPos3 = Integer.toString(yInfPos3);
				String strYSupPos3 = Integer.toString(ySupPos3);
				String strYInfPos4 = Integer.toString(yInfPos4);
				String strYSupPos4 = Integer.toString(ySupPos4);
				
				//if( (tempXValue <= 0) && (tempXValue <= tempYValue) && (tempYValue < 10)) {
				if( (xInfIntA >= 0) && (xInfIntA <= xSupIntA) && (xSupIntA < 1)) {
					
					
					
					//double xInfIntATemp = 0; double xSupIntATemp = 0;
					//double yInfIntBTemp = 0; double ySupIntBTemp = 0;
					
					//double xInfIntA =0; double xSupIntA =0;
					//double yInfIntB =0; double ySupIntB =0;
					
					xValIntATemp =  "0."+strXInfPos1+strXSupPos1+strXInfPos2+strXSupPos2+strXInfPos3+strXSupPos3+strXInfPos4+strXSupPos4;
					
					yValIntBTemp =  "0."+strYInfPos1+strYSupPos1+strYInfPos2+strYSupPos2+strYInfPos3+strYSupPos3+strYInfPos4+strYSupPos4;
					
					/*JOptionPane.showMessageDialog(null,"A = [ "+ xInfIntA+","+xSupIntA+" ]"+ " B = ["+yInfIntB+","+yInfIntB+" ] \n"+
					" xValIntATemp = "+xValIntATemp+ " e yValIntBTemp = "+yValIntBTemp); */
					
					 if (Double.parseDouble(xValIntATemp) < Double.parseDouble(yValIntBTemp)) 
					
						outputHost = listHostUseLevel.get(i).getPowerHost();
					else
						outputHost = listHostUseLevel.get(i+1).getPowerHost();
					

				//}else if ((tempXValue >= 0) && (tempYValue >= tempXValue ) && (tempYValue == 10)) {
				}else if ((xInfIntA >= 0) && (xSupIntA >= xInfIntA ) && (xSupIntA == 1)) {
					
					xValIntATemp =  "0."+strXInfPos1+"9"+strXInfPos2+"9"+strXInfPos3+"9"+strXInfPos4;
					yValIntBTemp =  "0."+strYInfPos1+"9"+strYInfPos2+"9"+strYInfPos3+"9"+strYInfPos4;
					
					if (Double.parseDouble(xValIntATemp) < Double.parseDouble(yValIntBTemp)) 
						outputHost = listHostUseLevel.get(i).getPowerHost();
					else
						outputHost = listHostUseLevel.get(i+1).getPowerHost();
					
				}else {
					
					xValIntATemp = "1";
					yValIntBTemp = "1";
					outputHost = listHostUseLevel.get(i).getPowerHost();
					
				}
				
				
			}

		}

		return outputHost;
	}
	
	
	// boolean plotMF, int isOverOrUnder, int isTypeObjective
	public ArrayList<HostUseLevel> getLevelRangeInUse(PowerHost host, boolean plotMF, int isOverOrUnder,
			int isTypeObjective, String typeIntersection, String typeUnion, List<? extends Host> hostList, Vm vm) {

		double minPower = Double.MAX_VALUE;
		PowerHostUtilizationHistory _host = (PowerHostUtilizationHistory) host;
		// double cpAvailable = _host.getMaxAvailableMips();
		// double ccAvailable = _host.getBw() - _host.getUtilizationOfBw();
		// double ramAvailable = _host.getRam() - _host.getUtilizationOfRam();

		double maxCPHost = this.maxCPHostOfArchitecture;
		double minCCHost = this.minCCHostOfArchitecture;
		double maxRamHost = this.maxRamHostOfArchitecture;

		double cpStandartScale = 0;
		double ccStandartScale = 0;
		double ramStandartScale = 0;

		cpStandartScale = (_host.getMaxAvailableMips() / maxCPHost) * 10;
		ccStandartScale = (10 * _host.getUtilizationOfBw()) / minCCHost;
		ramStandartScale = (_host.getUtilizationOfRam() / maxRamHost) * 10;

		// inicio
		ArrayList<HostUseLevel> templistHostUseLevel = new ArrayList<>();
		for (int i = 0; i < hostList.size(); i++) {

			if (host.isSuitableForVm(vm)) {
				if (getUtilizationOfCpuMips(host) != 0 && isHostOverUtilizedAfterAllocation(host, vm)) {
					continue;
				}

				try {

					Type2FuzzyLogicEvaluation it2 = new Type2FuzzyLogicEvaluation(cpStandartScale, ccStandartScale,
							ramStandartScale, plotMF, isOverOrUnder, isTypeObjective, false, typeIntersection, typeUnion, typeReductionType);

					setOutputXValue(it2.getOutputXValue());
					setOutputYValue(it2.getOutputYValue());

					HostUseLevel h = new HostUseLevel();
					h.setPowerHost(host);
					h.setOutputXValue(it2.getOutputXValue());
					h.setOutputYValue(it2.getOutputYValue());
					templistHostUseLevel.add(h);
					// this.hostUseLevel.add(h);
					// hostList.add(h);


				} catch (Exception e) {
				}
			}
		}

		// fim

		ArrayList<HostUseLevel> outPuthostList = templistHostUseLevel;
		return outPuthostList;

	}

	/**
	 * Find host for vm by Bruno Moura.
	 * 
	 * @param vm            the vm
	 * @param excludedHosts the excluded hosts
	 * @return the power host
	 */
	public PowerHost findHostForVm(Vm vm, Set<? extends Host> excludedHosts) {
		double minPower = Double.MAX_VALUE;
		PowerHost allocatedHost = null;

		List<PowerHost> avalableHosts = new LinkedList<PowerHost>();

		ArrayList<HostUseLevel> hostList = null;

		for (PowerHost hs : this.<PowerHost>getHostList()) {

			if (excludedHosts.contains(hs)) {
				continue;
			}
			if (hs.isSuitableForVm(vm)) {
				if (getUtilizationOfCpuMips(hs) != 0 && isHostOverUtilizedAfterAllocation(hs, vm)) {
					continue;
				}

			}
			avalableHosts.add(hs);
		}

		
		
		for (PowerHost host : this.<PowerHost>getHostList()) {

			if (excludedHosts.contains(host)) {
				continue;
			}
			if (host.isSuitableForVm(vm)) {
				if (getUtilizationOfCpuMips(host) != 0 && isHostOverUtilizedAfterAllocation(host, vm)) {
					continue;
				}

				try {
					double powerAfterAllocation = getPowerAfterAllocation(host, vm);
					if (powerAfterAllocation != -1) {

						double powerDiff = powerAfterAllocation - host.getPower();
						if (powerDiff < minPower) {
							
							if (isAdmissibleOrders()) {

								minPower = powerDiff;
								hostList = getLevelRangeInUse(host, false, 1, 1, getTypeIntersection(),getTypeUnion(), avalableHosts, vm);
								allocatedHost = getPowerHostBestUseLevel(getOrderType(), hostList);

							} else {

								minPower = powerDiff;
								allocatedHost = host;

							}
						}
					}
				} catch (Exception e) {
				}
			}
		}


		return allocatedHost;
	}

	/**
	 * Find host for vm by Bruno Moura.
	 * 
	 * @param vm            the vm
	 * @param excludedHosts the excluded hosts
	 * @return the power host
	 */
	public PowerHost findHostForVmBackup(Vm vm, Set<? extends Host> excludedHosts) {
		double minPower = Double.MAX_VALUE;
		PowerHost allocatedHost = null;

		for (PowerHost host : this.<PowerHost>getHostList()) {

			if (excludedHosts.contains(host)) {
				continue;
			}
			if (host.isSuitableForVm(vm)) {
				if (getUtilizationOfCpuMips(host) != 0 && isHostOverUtilizedAfterAllocation(host, vm)) {
					continue;
				}

				try {
					double powerAfterAllocation = getPowerAfterAllocation(host, vm);
					if (powerAfterAllocation != -1) {
						double powerDiff = powerAfterAllocation - host.getPower();
						if (powerDiff < minPower) {
							minPower = powerDiff;
							allocatedHost = host;
						}
					}
				} catch (Exception e) {
				}
			}
		}
		return allocatedHost;
	}

	/**
	 * Checks if is host over utilized after allocation.
	 * 
	 * @param host the host
	 * @param vm   the vm
	 * @return true, if is host over utilized after allocation
	 */
	protected boolean isHostOverUtilizedAfterAllocation(PowerHost host, Vm vm) {
		boolean isHostOverUtilizedAfterAllocation = true;
		if (host.vmCreate(vm)) {
			isHostOverUtilizedAfterAllocation = isHostOverUtilized(host);
			host.vmDestroy(vm);
		}
		return isHostOverUtilizedAfterAllocation;
	}

	/**
	 * Find host for vm.
	 * 
	 * @param vm the vm
	 * @return the power host
	 */
	@Override
	public PowerHost findHostForVm(Vm vm) {
		Set<Host> excludedHosts = new HashSet<Host>();
		if (vm.getHost() != null) {
			excludedHosts.add(vm.getHost());
		}
		return findHostForVm(vm, excludedHosts);
	}

	/**
	 * Extract host list from migration map.
	 * 
	 * @param migrationMap the migration map
	 * @return the list
	 */
	protected List<PowerHost> extractHostListFromMigrationMap(List<Map<String, Object>> migrationMap) {
		List<PowerHost> hosts = new LinkedList<PowerHost>();
		for (Map<String, Object> map : migrationMap) {
			hosts.add((PowerHost) map.get("host"));
		}
		return hosts;
	}

	/**
	 * Gets the new vm placement.
	 * 
	 * @param vmsToMigrate  the vms to migrate
	 * @param excludedHosts the excluded hosts
	 * @return the new vm placement
	 */
	protected List<Map<String, Object>> getNewVmPlacement(List<? extends Vm> vmsToMigrate,
			Set<? extends Host> excludedHosts) {
		List<Map<String, Object>> migrationMap = new LinkedList<Map<String, Object>>();
		PowerVmList.sortByCpuUtilization(vmsToMigrate);
		for (Vm vm : vmsToMigrate) {
			PowerHost allocatedHost = findHostForVm(vm, excludedHosts);
			if (allocatedHost != null) {
				allocatedHost.vmCreate(vm);
				Log.printLine("VM #" + vm.getId() + " allocated to host #" + allocatedHost.getId());

				Map<String, Object> migrate = new HashMap<String, Object>();
				migrate.put("vm", vm);
				migrate.put("host", allocatedHost);
				migrationMap.add(migrate);
			}
		}
		return migrationMap;
	}

	/**
	 * Gets the new vm placement from under utilized host.
	 * 
	 * @param vmsToMigrate  the vms to migrate
	 * @param excludedHosts the excluded hosts
	 * @return the new vm placement from under utilized host
	 */
	protected List<Map<String, Object>> getNewVmPlacementFromUnderUtilizedHost(List<? extends Vm> vmsToMigrate,
			Set<? extends Host> excludedHosts) {
		List<Map<String, Object>> migrationMap = new LinkedList<Map<String, Object>>();
		PowerVmList.sortByCpuUtilization(vmsToMigrate);
		for (Vm vm : vmsToMigrate) {
			PowerHost allocatedHost = findHostForVm(vm, excludedHosts);
			if (allocatedHost != null) {
				allocatedHost.vmCreate(vm);
				Log.printLine("VM #" + vm.getId() + " allocated to host #" + allocatedHost.getId());

				Map<String, Object> migrate = new HashMap<String, Object>();
				migrate.put("vm", vm);
				migrate.put("host", allocatedHost);
				migrationMap.add(migrate);
			} else {
				Log.printLine("Not all VMs can be reallocated from the host, reallocation cancelled");
				for (Map<String, Object> map : migrationMap) {
					((Host) map.get("host")).vmDestroy((Vm) map.get("vm"));
				}
				migrationMap.clear();
				break;
			}
		}
		return migrationMap;
	}

	/**
	 * Gets the vms to migrate from hosts.
	 * 
	 * @param overUtilizedHosts the over utilized hosts
	 * @return the vms to migrate from hosts
	 */
	protected List<? extends Vm> getVmsToMigrateFromHosts(List<PowerHostUtilizationHistory> overUtilizedHosts) {
		List<Vm> vmsToMigrate = new LinkedList<Vm>();
		for (PowerHostUtilizationHistory host : overUtilizedHosts) {
			while (true) {
				Vm vm = getVmSelectionPolicy().getVmToMigrate(host);
				if (vm == null) {
					break;
				}
				vmsToMigrate.add(vm);
				host.vmDestroy(vm);
				if (!isHostOverUtilized(host)) {
					break;
				}
			}
		}
		return vmsToMigrate;
	}

	/**
	 * Gets the vms to migrate from under utilized host.
	 * 
	 * @param host the host
	 * @return the vms to migrate from under utilized host
	 */
	protected List<? extends Vm> getVmsToMigrateFromUnderUtilizedHost(PowerHost host) {
		List<Vm> vmsToMigrate = new LinkedList<Vm>();
		for (Vm vm : host.getVmList()) {
			if (!vm.isInMigration()) {
				vmsToMigrate.add(vm);
			}
		}
		return vmsToMigrate;
	}

	/**
	 * Gets the over utilized hosts.
	 * 
	 * @return the over utilized hosts
	 */
	protected List<PowerHostUtilizationHistory> getOverUtilizedHosts() {
		List<PowerHostUtilizationHistory> overUtilizedHosts = new LinkedList<PowerHostUtilizationHistory>();
		for (PowerHostUtilizationHistory host : this.<PowerHostUtilizationHistory>getHostList()) {
			if (isHostOverUtilized(host)) {
				overUtilizedHosts.add(host);
			}
		}
		return overUtilizedHosts;
	}

	/**
	 * Gets the switched off host.
	 * 
	 * @return the switched off host
	 */
	protected List<PowerHost> getSwitchedOffHosts() {
		List<PowerHost> switchedOffHosts = new LinkedList<PowerHost>();
		for (PowerHost host : this.<PowerHost>getHostList()) {
			if (host.getUtilizationOfCpu() == 0) {
				switchedOffHosts.add(host);
			}
		}
		return switchedOffHosts;
	}

	/**
	 * Gets the under utilized host.
	 * 
	 * @param excludedHosts the excluded hosts
	 * @return the under utilized host
	 */
	protected PowerHost getUnderUtilizedHost(Set<? extends Host> excludedHosts) {
		double minUtilization = 1;
		PowerHost underUtilizedHost = null;

		for (PowerHost host : this.<PowerHost>getHostList()) {
			if (excludedHosts.contains(host)) {
				continue;
			}
			double utilization = host.getUtilizationOfCpu();

			/*
			 * host.getUtilizationOfCpuMips(); host.getAvailableMips(); host.getBw()
			 */

			if (utilization > 0 && utilization < minUtilization && !areAllVmsMigratingOutOrAnyVmMigratingIn(host)) {
				minUtilization = utilization;
				underUtilizedHost = host;
			}
		}
		return underUtilizedHost;
	}

	/**
	 * Checks whether all vms are in migration.
	 * 
	 * @param host the host
	 * @return true, if successful
	 */
	protected boolean areAllVmsMigratingOutOrAnyVmMigratingIn(PowerHost host) {
		for (PowerVm vm : host.<PowerVm>getVmList()) {
			if (!vm.isInMigration()) {
				return false;
			}
			if (host.getVmsMigratingIn().contains(vm)) {
				return true;
			}
		}
		return true;
	}

	/**
	 * Checks if is host over utilized.
	 * 
	 * @param host the host
	 * @return true, if is host over utilized
	 * @throws IOException 
	 */
	protected abstract boolean isHostOverUtilized(PowerHost host);

	/**
	 * Adds the history value.
	 * 
	 * @param host   the host
	 * @param metric the metric
	 */
	protected void addHistoryEntry(HostDynamicWorkload host, double metric) {
		int hostId = host.getId();
		if (!getTimeHistory().containsKey(hostId)) {
			getTimeHistory().put(hostId, new LinkedList<Double>());
		}
		if (!getUtilizationHistory().containsKey(hostId)) {
			getUtilizationHistory().put(hostId, new LinkedList<Double>());
		}
		if (!getMetricHistory().containsKey(hostId)) {
			getMetricHistory().put(hostId, new LinkedList<Double>());
		}
		if (!getTimeHistory().get(hostId).contains(CloudSim.clock())) {
			getTimeHistory().get(hostId).add(CloudSim.clock());
			getUtilizationHistory().get(hostId).add(host.getUtilizationOfCpu());
			getMetricHistory().get(hostId).add(metric);
		}
	}

	/**
	 * Save allocation.
	 */
	protected void saveAllocation() {
		getSavedAllocation().clear();
		for (Host host : getHostList()) {
			for (Vm vm : host.getVmList()) {
				if (host.getVmsMigratingIn().contains(vm)) {
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("host", host);
				map.put("vm", vm);
				getSavedAllocation().add(map);
			}
		}
	}

	/**
	 * Restore allocation.
	 */
	protected void restoreAllocation() {
		for (Host host : getHostList()) {
			host.vmDestroyAll();
			host.reallocateMigratingInVms();
		}
		for (Map<String, Object> map : getSavedAllocation()) {
			Vm vm = (Vm) map.get("vm");
			PowerHost host = (PowerHost) map.get("host");
			if (!host.vmCreate(vm)) {
				Log.printLine("Couldn't restore VM #" + vm.getId() + " on host #" + host.getId());
				System.exit(0);
			}
			getVmTable().put(vm.getUid(), host);
		}
	}

	/**
	 * Gets the power after allocation.
	 * 
	 * @param host the host
	 * @param vm   the vm
	 * 
	 * @return the power after allocation
	 */
	protected double getPowerAfterAllocation(PowerHost host, Vm vm) {
		double power = 0;
		try {
			power = host.getPowerModel().getPower(getMaxUtilizationAfterAllocation(host, vm));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return power;
	}

	/**
	 * Gets the power after allocation. We assume that load is balanced between PEs.
	 * The only restriction is: VM's max MIPS < PE's MIPS
	 * 
	 * @param host the host
	 * @param vm   the vm
	 * 
	 * @return the power after allocation
	 */
	protected double getMaxUtilizationAfterAllocation(PowerHost host, Vm vm) {
		double requestedTotalMips = vm.getCurrentRequestedTotalMips();
		double hostUtilizationMips = getUtilizationOfCpuMips(host);
		double hostPotentialUtilizationMips = hostUtilizationMips + requestedTotalMips;
		double pePotentialUtilization = hostPotentialUtilizationMips / host.getTotalMips();
		return pePotentialUtilization;
	}

	/**
	 * Gets the utilization of the CPU in MIPS for the current potentially allocated
	 * VMs.
	 *
	 * @param host the host
	 *
	 * @return the utilization of the CPU in MIPS
	 */
	protected double getUtilizationOfCpuMips(PowerHost host) {
		double hostUtilizationMips = 0;
		for (Vm vm2 : host.getVmList()) {
			if (host.getVmsMigratingIn().contains(vm2)) {
				// calculate additional potential CPU usage of a migrating in VM
				hostUtilizationMips += host.getTotalAllocatedMipsForVm(vm2) * 0.9 / 0.1;
			}
			hostUtilizationMips += host.getTotalAllocatedMipsForVm(vm2);
		}
		return hostUtilizationMips;
	}

	/**
	 * Gets the saved allocation.
	 * 
	 * @return the saved allocation
	 */
	protected List<Map<String, Object>> getSavedAllocation() {
		return savedAllocation;
	}

	/**
	 * Sets the vm selection policy.
	 * 
	 * @param vmSelectionPolicy the new vm selection policy
	 */
	protected void setVmSelectionPolicy(PowerVmSelectionPolicy vmSelectionPolicy) {
		this.vmSelectionPolicy = vmSelectionPolicy;
	}

	/**
	 * Gets the vm selection policy.
	 * 
	 * @return the vm selection policy
	 */
	protected PowerVmSelectionPolicy getVmSelectionPolicy() {
		return vmSelectionPolicy;
	}

	/**
	 * Gets the utilization history.
	 * 
	 * @return the utilization history
	 */
	public Map<Integer, List<Double>> getUtilizationHistory() {
		return utilizationHistory;
	}

	/**
	 * Gets the metric history.
	 * 
	 * @return the metric history
	 */
	public Map<Integer, List<Double>> getMetricHistory() {
		return metricHistory;
	}

	/**
	 * Gets the time history.
	 * 
	 * @return the time history
	 */
	public Map<Integer, List<Double>> getTimeHistory() {
		return timeHistory;
	}

	/**
	 * Gets the execution time history vm selection.
	 * 
	 * @return the execution time history vm selection
	 */
	public List<Double> getExecutionTimeHistoryVmSelection() {
		return executionTimeHistoryVmSelection;
	}

	/**
	 * Gets the execution time history host selection.
	 * 
	 * @return the execution time history host selection
	 */
	public List<Double> getExecutionTimeHistoryHostSelection() {
		return executionTimeHistoryHostSelection;
	}

	/**
	 * Gets the execution time history vm reallocation.
	 * 
	 * @return the execution time history vm reallocation
	 */
	public List<Double> getExecutionTimeHistoryVmReallocation() {
		return executionTimeHistoryVmReallocation;
	}

	/**
	 * Gets the execution time history total.
	 * 
	 * @return the execution time history total
	 */
	public List<Double> getExecutionTimeHistoryTotal() {
		return executionTimeHistoryTotal;
	}

	public double getOutputXValue() {
		return outputXValue;
	}

	public void setOutputXValue(double outputXValue) {
		this.outputXValue = outputXValue;
	}

	public double getOutputYValue() {
		return outputYValue;
	}

	public void setOutputYValue(double outputYValue) {
		this.outputYValue = outputYValue;
	}

	public double getMaxCPHostOfArchitecture() {
		return maxCPHostOfArchitecture;
	}

	public void setMaxCPHostOfArchitecture(double maxCPHostOfArchitecture) {
		this.maxCPHostOfArchitecture = maxCPHostOfArchitecture;
	}

	public double getMinCCHostOfArchitecture() {
		return minCCHostOfArchitecture;
	}

	public void setMinCCHostOfArchitecture(double minCCHostOfArchitecture) {
		this.minCCHostOfArchitecture = minCCHostOfArchitecture;
	}

	public double getMaxRamHostOfArchitecture() {
		return maxRamHostOfArchitecture;
	}

	public void setMaxRamHostOfArchitecture(double maxRamHostOfArchitecture) {
		this.maxRamHostOfArchitecture = maxRamHostOfArchitecture;
	}

	public ArrayList<HostUseLevel> getHostUseLevel() {
		return hostUseLevel;
	}

	public void setHostUseLevel(ArrayList<HostUseLevel> hostUseLevel) {
		this.hostUseLevel = hostUseLevel;
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
	
	
	
	
	

}
