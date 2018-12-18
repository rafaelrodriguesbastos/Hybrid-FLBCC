package org.fis.juzzy.flb;

import java.util.Calendar;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;

public class IntFLBCCPlanetLabRunner extends IntFLBCCRunnerAbstract{

	public IntFLBCCPlanetLabRunner(
			boolean enableOutput,
			boolean outputToFile,
			String inputFolder,
			String outputFolder,
			String workload,
			String vmAllocationPolicy,
			String vmSelectionPolicy,
			String parameter) {
		super(
				enableOutput,
				outputToFile,
				inputFolder,
				outputFolder,
				workload,
				vmAllocationPolicy,
				vmSelectionPolicy,
				parameter);
	}

	@Override
	protected void init(String inputFolder) {
		try {
			CloudSim.init(1, Calendar.getInstance(), false);

			broker = IntFLBCCHelper.createBroker();
			int brokerId = broker.getId();

			cloudletList = IntFLBCCPlanetLabHelper.createCloudletListPlanetLab(brokerId, inputFolder);
			vmList = IntFLBCCHelper.createVmList(brokerId, cloudletList.size());
			hostList = IntFLBCCHelper.createHostList(IntFLBCCPlanetLabConstants.NUMBER_OF_HOSTS);
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
			System.exit(0);
		}
	}
	
}
