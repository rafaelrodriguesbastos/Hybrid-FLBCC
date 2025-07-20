package org.cloudbus.cloudsim.examples.power.random;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simulation of a heterogeneous power aware data center that applies the Local Regression Robust
 * (LRR) VM allocation policy and Random Selection (RS) VM selection policy.
 * 
 * The remaining configuration parameters are in the Constants and RandomConstants classes.
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
public class LrrRs {

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		boolean enableOutput = true;
		boolean outputToFile = false;
		String inputFolder = "";
		String outputFolder = "";
		String workload = "random"; // Random workload
		String vmAllocationPolicy = "lrr"; // Local Regression Robust (LRR) VM allocation policy
		String vmSelectionPolicy = "rs"; // Random Selection (RS) VM selection policy
		String parameter = "1.2"; // the safety parameter of the LRR policy
		boolean outputAbstractInCsv = true;  // enable summary recording in csv
		boolean enableFuzzyT2Overload = true; // enable overload fuzzy type 2 detection
		String typeIntersection = "TL"; //default value empty max(xInf, yInf), min(xSup, ySup)
		String typeUnion = ""; // default value empty min(xInf, yInf), max(xSup, ySup)
		boolean admissibleOrders = true; // enable selection host for admissible orders
		String orderType = "xuandyager"; // set adimissible order type
		int typeReductionType = 0; // CENTEROFSETS = 0; CENTROID = 1;
		int typeFuzzySystem = 0;  //  0 - Conventional Type-2 Fuzzy System, 1 - N Dimensional Type-2 Fuzzy Fuzzy System  

		Map<String, String> vmPolicies = new HashMap<>();
		vmPolicies.put("ap", "lrr");
		vmPolicies.put("sp", "rs");

		new RandomRunner(
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
				typeFuzzySystem,
				vmPolicies);
	}

}
