package org.cloudbus.cloudsim.examples.power.planetlab;

import java.io.IOException;

/**
 * A simulation of a heterogeneous power aware data center that only applied DVFS, but no dynamic
 * optimization of the VM allocation. The adjustment of the hosts' power consumption according to
 * their CPU utilization is happening in the PowerDatacenter class.
 * 
 * This example uses a real PlanetLab workload: 20110303.
 * 
 * The remaining configuration parameters are in the Constants and PlanetLabConstants classes.
 * 
 * If you are using any algorithms, policies or workload included in the power package please cite
 * the following paper:
 * 
 * Anton Beloglazov, and Rajkumar Buyya, "Optimal Online Deterministic Algorithms and Adaptive
 * Heuristics for Energy and Performance Efficient Dynamic Consolidation of Virtual Machines in
 * Cloud Data Centers", Concurrency and Computation: Practice and Experience (CCPE), Volume 24,
 * Issue 13, Pages: 1397-1420, John Wiley & Sons, Ltd, New York, USA, 2012
 * 
 * @author Anton Beloglazov
 * @since Jan 5, 2012
 */
public class Dvfs {

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		boolean enableOutput = true;
		boolean outputToFile = false;
		String inputFolder = Dvfs.class.getClassLoader().getResource("workload/planetlab").getPath();
		String outputFolder = "output";
		String workload = "20110303"; // PlanetLab workload
		String vmAllocationPolicy = "dvfs"; // DVFS policy without VM migrations
		String vmSelectionPolicy = "";
		String parameter = "";
		boolean outputAbstractInCsv = false;  // enable summary recording in csv 
		boolean enableFuzzyT2Overload = true; // enable overload fuzzy type 2 detection
		String typeIntersection = "TL"; //default value empty max(xInf, yInf), min(xSup, ySup)
		String typeUnion = ""; // default value empty min(xInf, yInf), max(xSup, ySup)
		boolean admissibleOrders = true; // enable selection host for admissible orders
		String orderType = "xuandyager"; // set admissible order type
		int typeReductionType = 0; // CENTEROFSETS = 0; CENTROID = 1;
		int typeFuzzySystem = 0;  //  0 - Conventional Type-2 Fuzzy System, 1 - N Dimensional Type-2 Fuzzy Fuzzy System  
		
		new PlanetLabRunner(
				enableOutput,
				outputToFile,
				inputFolder,
				outputFolder,
				workload,
				vmAllocationPolicy,
				vmSelectionPolicy,
				parameter,
				outputAbstractInCsv,
				enableFuzzyT2Overload,
				typeIntersection,
				typeUnion,
				admissibleOrders,
				orderType,
				typeReductionType,
				typeFuzzySystem);
	}

}
