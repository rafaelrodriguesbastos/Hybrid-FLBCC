package org.cloudbus.cloudsim.examples.power.planetlab;

import java.io.IOException;

/**
 * A simulation of a heterogeneous power aware data center that applies the Median Absolute
 * Deviation (MAD) VM allocation policy and Minimum Migration Time (MMT) VM selection policy.
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
public class MadMmt {

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		boolean enableOutput = true;
		boolean outputToFile = false;
		String inputFolder = MadMmt.class.getClassLoader().getResource("workload/planetlab").getPath();
		String outputFolder = "output";
		String workload = "20110322"; // PlanetLab workload - 20110303, 20110306, 20110309, 20110322, 20110325, 20110403, 20110409, 20110411, 20110412 
		String vmAllocationPolicy = "mad"; // Median Absolute Deviation (MAD) VM allocation policy
		String vmSelectionPolicy = "mmt"; // Minimum Migration Time (MMT) VM selection policy
		String parameter = "2.5"; //2.5 the safety parameter of the MAD policy
		boolean outputAbstractInCsv = true;  // enable summary recording in csv
		boolean enableFuzzyT2Overload = false; // enable overload fuzzy type 2 detection
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
