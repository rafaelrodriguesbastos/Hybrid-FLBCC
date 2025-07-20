package org.cloudbus.cloudsim.util;

import java.io.Serializable;

public class MembershipDegreesOfType2FuzzySets implements Serializable {

	private static final long serialVersionUID = 1L;

	// Degree Of Membership Functions cpu low
	private double lowCpuDegreeOfMembershipUpperBound;
	private double lowCpuDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions cpu medium
	private double mediumCpuDegreeOfMembershipUpperBound;
	private double mediumCpuDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions cpu high
	private double highCpuDegreeOfMembershipUpperBound;
	private double highCpuDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions ram low
	private double lowRamDegreeOfMembershipUpperBound;
	private double lowRamDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions ram medium
	private double mediumRamDegreeOfMembershipUpperBound;
	private double mediumRamDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions ram high
	private double highRamDegreeOfMembershipUpperBound;
	private double highRamDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions bw low
	private double lowBwDegreeOfMembershipUpperBound;
	private double lowBwDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions bw medium
	private double mediumBwDegreeOfMembershipUpperBound;
	private double mediumBwDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions bw high
	private double highBwDegreeOfMembershipUpperBound;
	private double highBwDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions mips low
	private double lowMipsDegreeOfMembershipUpperBound;
	private double lowMipsDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions mips medium
	private double mediumMipsDegreeOfMembershipUpperBound;
	private double mediumMipsDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions mips high
	private double highMipsDegreeOfMembershipUpperBound;
	private double highMipsDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions storage low
	private double lowStorageDegreeOfMembershipUpperBound;
	private double lowStorageDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions storage medium
	private double mediumStorageDegreeOfMembershipUpperBound;
	private double mediumStorageDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions storage high
	private double highStorageDegreeOfMembershipUpperBound;
	private double highStorageDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions energy low
	private double lowEnergyDegreeOfMembershipUpperBound;
	private double lowEnergyDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions energy medium
	private double mediumEnergyDegreeOfMembershipUpperBound;
	private double mediumEnergyDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions energy high
	private double highEnergyDegreeOfMembershipUpperBound;
	private double highEnergyDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions utilization under
	private double underUtilizationDegreeOfMembershipUpperBound;
	private double underUtilizationDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions utilization normal
	private double normalUtilizationDegreeOfMembershipUpperBound;
	private double normalUtilizationDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions utilization over
	private double overUtilizationDegreeOfMembershipUpperBound;
	private double overUtilizationDegreeOfMembershipLowerBound;

	public MembershipDegreesOfType2FuzzySets() {
		super();
	}

	public MembershipDegreesOfType2FuzzySets(double lowCpuDegreeOfMembershipUpperBound, double lowCpuDegreeOfMembershipLowerBound, double mediumCpuDegreeOfMembershipUpperBound, double mediumCpuDegreeOfMembershipLowerBound, double highCpuDegreeOfMembershipUpperBound, double highCpuDegreeOfMembershipLowerBound, double lowStorageDegreeOfMembershipUpperBound, double lowStorageDegreeOfMembershipLowerBound, double mediumStorageDegreeOfMembershipUpperBound, double mediumStorageDegreeOfMembershipLowerBound, double highStorageDegreeOfMembershipUpperBound, double highStorageDegreeOfMembershipLowerBound, double lowEnergyDegreeOfMembershipUpperBound, double lowEnergyDegreeOfMembershipLowerBound, double mediumEnergyDegreeOfMembershipUpperBound, double mediumEnergyDegreeOfMembershipLowerBound, double highEnergyDegreeOfMembershipUpperBound, double highEnergyDegreeOfMembershipLowerBound, double underUtilizationDegreeOfMembershipUpperBound, double underUtilizationDegreeOfMembershipLowerBound, double normalUtilizationDegreeOfMembershipUpperBound, double normalUtilizationDegreeOfMembershipLowerBound, double overUtilizationDegreeOfMembershipUpperBound, double overUtilizationDegreeOfMembershipLowerBound) {
		super();
		this.lowCpuDegreeOfMembershipUpperBound = lowCpuDegreeOfMembershipUpperBound;
		this.lowCpuDegreeOfMembershipLowerBound =lowCpuDegreeOfMembershipLowerBound;
		this.mediumCpuDegreeOfMembershipUpperBound=mediumCpuDegreeOfMembershipUpperBound;
		this.mediumCpuDegreeOfMembershipLowerBound=mediumCpuDegreeOfMembershipLowerBound;
		this.highCpuDegreeOfMembershipUpperBound=highCpuDegreeOfMembershipUpperBound;
		this.highCpuDegreeOfMembershipLowerBound=highCpuDegreeOfMembershipLowerBound;
		this.lowRamDegreeOfMembershipUpperBound = lowRamDegreeOfMembershipUpperBound;
		this.lowRamDegreeOfMembershipLowerBound =lowRamDegreeOfMembershipLowerBound;
		this.mediumRamDegreeOfMembershipUpperBound=mediumRamDegreeOfMembershipUpperBound;
		this.mediumRamDegreeOfMembershipLowerBound=mediumRamDegreeOfMembershipLowerBound;
		this.highRamDegreeOfMembershipUpperBound=highRamDegreeOfMembershipUpperBound;
		this.highRamDegreeOfMembershipLowerBound=highRamDegreeOfMembershipLowerBound;
		this.lowBwDegreeOfMembershipUpperBound = lowBwDegreeOfMembershipUpperBound;
		this.lowBwDegreeOfMembershipLowerBound =lowBwDegreeOfMembershipLowerBound;
		this.mediumBwDegreeOfMembershipUpperBound=mediumBwDegreeOfMembershipUpperBound;
		this.mediumBwDegreeOfMembershipLowerBound=mediumBwDegreeOfMembershipLowerBound;
		this.highBwDegreeOfMembershipUpperBound=highBwDegreeOfMembershipUpperBound;
		this.highBwDegreeOfMembershipLowerBound=highBwDegreeOfMembershipLowerBound;
		this.lowMipsDegreeOfMembershipUpperBound = lowMipsDegreeOfMembershipUpperBound;
		this.lowMipsDegreeOfMembershipLowerBound =lowMipsDegreeOfMembershipLowerBound;
		this.mediumMipsDegreeOfMembershipUpperBound=mediumMipsDegreeOfMembershipUpperBound;
		this.mediumMipsDegreeOfMembershipLowerBound=mediumMipsDegreeOfMembershipLowerBound;
		this.highMipsDegreeOfMembershipUpperBound=highMipsDegreeOfMembershipUpperBound;
		this.highMipsDegreeOfMembershipLowerBound=highMipsDegreeOfMembershipLowerBound;
		this.lowStorageDegreeOfMembershipUpperBound=lowStorageDegreeOfMembershipUpperBound;
		this.lowStorageDegreeOfMembershipLowerBound=lowStorageDegreeOfMembershipLowerBound;
		this.mediumStorageDegreeOfMembershipUpperBound=mediumStorageDegreeOfMembershipUpperBound;
		this.mediumStorageDegreeOfMembershipLowerBound=mediumStorageDegreeOfMembershipLowerBound;
		this.highStorageDegreeOfMembershipUpperBound=highStorageDegreeOfMembershipUpperBound;
		this.highStorageDegreeOfMembershipLowerBound=highStorageDegreeOfMembershipLowerBound;
		this.lowEnergyDegreeOfMembershipUpperBound=lowEnergyDegreeOfMembershipUpperBound;
		this.lowEnergyDegreeOfMembershipLowerBound=lowEnergyDegreeOfMembershipLowerBound;
		this.mediumEnergyDegreeOfMembershipUpperBound=mediumEnergyDegreeOfMembershipUpperBound;
		this.mediumEnergyDegreeOfMembershipLowerBound=mediumEnergyDegreeOfMembershipLowerBound;
		this.highEnergyDegreeOfMembershipUpperBound=mediumEnergyDegreeOfMembershipLowerBound;
		this.highEnergyDegreeOfMembershipLowerBound=highEnergyDegreeOfMembershipLowerBound;
		this.underUtilizationDegreeOfMembershipUpperBound=underUtilizationDegreeOfMembershipUpperBound;
		this.underUtilizationDegreeOfMembershipLowerBound=underUtilizationDegreeOfMembershipLowerBound;
		this.normalUtilizationDegreeOfMembershipUpperBound=normalUtilizationDegreeOfMembershipUpperBound;
		this.normalUtilizationDegreeOfMembershipLowerBound=normalUtilizationDegreeOfMembershipLowerBound;
		this.overUtilizationDegreeOfMembershipUpperBound=overUtilizationDegreeOfMembershipUpperBound;
		this.overUtilizationDegreeOfMembershipLowerBound=overUtilizationDegreeOfMembershipLowerBound;
	}

	public double getLowCpuDegreeOfMembershipUpperBound() {
		return lowCpuDegreeOfMembershipUpperBound;
	}

	public void setLowCpuDegreeOfMembershipUpperBound(double lowCpuDegreeOfMembershipUpperBound) {
		this.lowCpuDegreeOfMembershipUpperBound = lowCpuDegreeOfMembershipUpperBound;
	}

	public double getLowCpuDegreeOfMembershipLowerBound() {
		return lowCpuDegreeOfMembershipLowerBound;
	}

	public void setLowCpuDegreeOfMembershipLowerBound(double lowCpuDegreeOfMembershipLowerBound) {
		this.lowCpuDegreeOfMembershipLowerBound = lowCpuDegreeOfMembershipLowerBound;
	}

	public double getMediumCpuDegreeOfMembershipUpperBound() {
		return mediumCpuDegreeOfMembershipUpperBound;
	}

	public void setMediumCpuDegreeOfMembershipUpperBound(double mediumCpuDegreeOfMembershipUpperBound) {
		this.mediumCpuDegreeOfMembershipUpperBound = mediumCpuDegreeOfMembershipUpperBound;
	}

	public double getMediumCpuDegreeOfMembershipLowerBound() {
		return mediumCpuDegreeOfMembershipLowerBound;
	}

	public void setMediumCpuDegreeOfMembershipLowerBound(double mediumCpuDegreeOfMembershipLowerBound) {
		this.mediumCpuDegreeOfMembershipLowerBound = mediumCpuDegreeOfMembershipLowerBound;
	}

	public double getHighCpuDegreeOfMembershipUpperBound() {
		return highCpuDegreeOfMembershipUpperBound;
	}

	public void setHighCpuDegreeOfMembershipUpperBound(double highCpuDegreeOfMembershipUpperBound) {
		this.highCpuDegreeOfMembershipUpperBound = highCpuDegreeOfMembershipUpperBound;
	}

	public double getHighCpuDegreeOfMembershipLowerBound() {
		return highCpuDegreeOfMembershipLowerBound;
	}

	public void setHighCpuDegreeOfMembershipLowerBound(double highCpuDegreeOfMembershipLowerBound) {
		this.highCpuDegreeOfMembershipLowerBound = highCpuDegreeOfMembershipLowerBound;
	}

	public double getLowStorageDegreeOfMembershipUpperBound() {
		return lowStorageDegreeOfMembershipUpperBound;
	}

	public void setLowStorageDegreeOfMembershipUpperBound(double lowStorageDegreeOfMembershipUpperBound) {
		this.lowStorageDegreeOfMembershipUpperBound = lowStorageDegreeOfMembershipUpperBound;
	}

	public double getLowStorageDegreeOfMembershipLowerBound() {
		return lowStorageDegreeOfMembershipLowerBound;
	}

	public void setLowStorageDegreeOfMembershipLowerBound(double lowStorageDegreeOfMembershipLowerBound) {
		this.lowStorageDegreeOfMembershipLowerBound = lowStorageDegreeOfMembershipLowerBound;
	}

	public double getMediumStorageDegreeOfMembershipUpperBound() {
		return mediumStorageDegreeOfMembershipUpperBound;
	}

	public void setMediumStorageDegreeOfMembershipUpperBound(double mediumStorageDegreeOfMembershipUpperBound) {
		this.mediumStorageDegreeOfMembershipUpperBound = mediumStorageDegreeOfMembershipUpperBound;
	}

	public double getMediumStorageDegreeOfMembershipLowerBound() {
		return mediumStorageDegreeOfMembershipLowerBound;
	}

	public void setMediumStorageDegreeOfMembershipLowerBound(double mediumStorageDegreeOfMembershipLowerBound) {
		this.mediumStorageDegreeOfMembershipLowerBound = mediumStorageDegreeOfMembershipLowerBound;
	}

	public double getHighStorageDegreeOfMembershipUpperBound() {
		return highStorageDegreeOfMembershipUpperBound;
	}

	public void setHighStorageDegreeOfMembershipUpperBound(double highStorageDegreeOfMembershipUpperBound) {
		this.highStorageDegreeOfMembershipUpperBound = highStorageDegreeOfMembershipUpperBound;
	}

	public double getHighStorageDegreeOfMembershipLowerBound() {
		return highStorageDegreeOfMembershipLowerBound;
	}

	public void setHighStorageDegreeOfMembershipLowerBound(double highStorageDegreeOfMembershipLowerBound) {
		this.highStorageDegreeOfMembershipLowerBound = highStorageDegreeOfMembershipLowerBound;
	}

	public double getLowEnergyDegreeOfMembershipUpperBound() {
		return lowEnergyDegreeOfMembershipUpperBound;
	}

	public void setLowEnergyDegreeOfMembershipUpperBound(double lowEnergyDegreeOfMembershipUpperBound) {
		this.lowEnergyDegreeOfMembershipUpperBound = lowEnergyDegreeOfMembershipUpperBound;
	}

	public double getLowEnergyDegreeOfMembershipLowerBound() {
		return lowEnergyDegreeOfMembershipLowerBound;
	}

	public void setLowEnergyDegreeOfMembershipLowerBound(double lowEnergyDegreeOfMembershipLowerBound) {
		this.lowEnergyDegreeOfMembershipLowerBound = lowEnergyDegreeOfMembershipLowerBound;
	}

	public double getMediumEnergyDegreeOfMembershipUpperBound() {
		return mediumEnergyDegreeOfMembershipUpperBound;
	}

	public void setMediumEnergyDegreeOfMembershipUpperBound(double mediumEnergyDegreeOfMembershipUpperBound) {
		this.mediumEnergyDegreeOfMembershipUpperBound = mediumEnergyDegreeOfMembershipUpperBound;
	}

	public double getMediumEnergyDegreeOfMembershipLowerBound() {
		return mediumEnergyDegreeOfMembershipLowerBound;
	}

	public void setMediumEnergyDegreeOfMembershipLowerBound(double mediumEnergyDegreeOfMembershipLowerBound) {
		this.mediumEnergyDegreeOfMembershipLowerBound = mediumEnergyDegreeOfMembershipLowerBound;
	}

	public double getHighEnergyDegreeOfMembershipUpperBound() {
		return highEnergyDegreeOfMembershipUpperBound;
	}

	public void setHighEnergyDegreeOfMembershipUpperBound(double highEnergyDegreeOfMembershipUpperBound) {
		this.highEnergyDegreeOfMembershipUpperBound = highEnergyDegreeOfMembershipUpperBound;
	}

	public double getHighEnergyDegreeOfMembershipLowerBound() {
		return highEnergyDegreeOfMembershipLowerBound;
	}

	public void setHighEnergyDegreeOfMembershipLowerBound(double highEnergyDegreeOfMembershipLowerBound) {
		this.highEnergyDegreeOfMembershipLowerBound = highEnergyDegreeOfMembershipLowerBound;
	}

	public double getUnderUtilizationDegreeOfMembershipUpperBound() {
		return underUtilizationDegreeOfMembershipUpperBound;
	}

	public void setUnderUtilizationDegreeOfMembershipUpperBound(double underUtilizationDegreeOfMembershipUpperBound) {
		this.underUtilizationDegreeOfMembershipUpperBound = underUtilizationDegreeOfMembershipUpperBound;
	}

	public double getUnderUtilizationDegreeOfMembershipLowerBound() {
		return underUtilizationDegreeOfMembershipLowerBound;
	}

	public void setUnderUtilizationDegreeOfMembershipLowerBound(double underUtilizationDegreeOfMembershipLowerBound) {
		this.underUtilizationDegreeOfMembershipLowerBound = underUtilizationDegreeOfMembershipLowerBound;
	}

	public double getNormalUtilizationDegreeOfMembershipUpperBound() {
		return normalUtilizationDegreeOfMembershipUpperBound;
	}

	public void setNormalUtilizationDegreeOfMembershipUpperBound(double normalUtilizationDegreeOfMembershipUpperBound) {
		this.normalUtilizationDegreeOfMembershipUpperBound = normalUtilizationDegreeOfMembershipUpperBound;
	}

	public double getNormalUtilizationDegreeOfMembershipLowerBound() {
		return normalUtilizationDegreeOfMembershipLowerBound;
	}

	public void setNormalUtilizationDegreeOfMembershipLowerBound(double normalUtilizationDegreeOfMembershipLowerBound) {
		this.normalUtilizationDegreeOfMembershipLowerBound = normalUtilizationDegreeOfMembershipLowerBound;
	}

	public double getOverUtilizationDegreeOfMembershipUpperBound() {
		return overUtilizationDegreeOfMembershipUpperBound;
	}

	public void setOverUtilizationDegreeOfMembershipUpperBound(double overUtilizationDegreeOfMembershipUpperBound) {
		this.overUtilizationDegreeOfMembershipUpperBound = overUtilizationDegreeOfMembershipUpperBound;
	}

	public double getOverUtilizationDegreeOfMembershipLowerBound() {
		return overUtilizationDegreeOfMembershipLowerBound;
	}

	public void setOverUtilizationDegreeOfMembershipLowerBound(double overUtilizationDegreeOfMembershipLowerBound) {
		this.overUtilizationDegreeOfMembershipLowerBound = overUtilizationDegreeOfMembershipLowerBound;
	}

	public double getLowRamDegreeOfMembershipUpperBound() {
		return lowRamDegreeOfMembershipUpperBound;
	}

	public void setLowRamDegreeOfMembershipUpperBound(double lowRamDegreeOfMembershipUpperBound) {
		this.lowRamDegreeOfMembershipUpperBound = lowRamDegreeOfMembershipUpperBound;
	}

	public double getLowRamDegreeOfMembershipLowerBound() {
		return lowRamDegreeOfMembershipLowerBound;
	}

	public void setLowRamDegreeOfMembershipLowerBound(double lowRamDegreeOfMembershipLowerBound) {
		this.lowRamDegreeOfMembershipLowerBound = lowRamDegreeOfMembershipLowerBound;
	}

	public double getMediumRamDegreeOfMembershipUpperBound() {
		return mediumRamDegreeOfMembershipUpperBound;
	}

	public void setMediumRamDegreeOfMembershipUpperBound(double mediumRamDegreeOfMembershipUpperBound) {
		this.mediumRamDegreeOfMembershipUpperBound = mediumRamDegreeOfMembershipUpperBound;
	}

	public double getMediumRamDegreeOfMembershipLowerBound() {
		return mediumRamDegreeOfMembershipLowerBound;
	}

	public void setMediumRamDegreeOfMembershipLowerBound(double mediumRamDegreeOfMembershipLowerBound) {
		this.mediumRamDegreeOfMembershipLowerBound = mediumRamDegreeOfMembershipLowerBound;
	}

	public double getHighRamDegreeOfMembershipUpperBound() {
		return highRamDegreeOfMembershipUpperBound;
	}

	public void setHighRamDegreeOfMembershipUpperBound(double highRamDegreeOfMembershipUpperBound) {
		this.highRamDegreeOfMembershipUpperBound = highRamDegreeOfMembershipUpperBound;
	}

	public double getHighRamDegreeOfMembershipLowerBound() {
		return highRamDegreeOfMembershipLowerBound;
	}

	public void setHighRamDegreeOfMembershipLowerBound(double highRamDegreeOfMembershipLowerBound) {
		this.highRamDegreeOfMembershipLowerBound = highRamDegreeOfMembershipLowerBound;
	}

	public double getLowBwDegreeOfMembershipUpperBound() {
		return lowBwDegreeOfMembershipUpperBound;
	}

	public void setLowBwDegreeOfMembershipUpperBound(double lowBwDegreeOfMembershipUpperBound) {
		this.lowBwDegreeOfMembershipUpperBound = lowBwDegreeOfMembershipUpperBound;
	}

	public double getLowBwDegreeOfMembershipLowerBound() {
		return lowBwDegreeOfMembershipLowerBound;
	}

	public void setLowBwDegreeOfMembershipLowerBound(double lowBwDegreeOfMembershipLowerBound) {
		this.lowBwDegreeOfMembershipLowerBound = lowBwDegreeOfMembershipLowerBound;
	}

	public double getMediumBwDegreeOfMembershipUpperBound() {
		return mediumBwDegreeOfMembershipUpperBound;
	}

	public void setMediumBwDegreeOfMembershipUpperBound(double mediumBwDegreeOfMembershipUpperBound) {
		this.mediumBwDegreeOfMembershipUpperBound = mediumBwDegreeOfMembershipUpperBound;
	}

	public double getMediumBwDegreeOfMembershipLowerBound() {
		return mediumBwDegreeOfMembershipLowerBound;
	}

	public void setMediumBwDegreeOfMembershipLowerBound(double mediumBwDegreeOfMembershipLowerBound) {
		this.mediumBwDegreeOfMembershipLowerBound = mediumBwDegreeOfMembershipLowerBound;
	}

	public double getHighBwDegreeOfMembershipUpperBound() {
		return highBwDegreeOfMembershipUpperBound;
	}

	public void setHighBwDegreeOfMembershipUpperBound(double highBwDegreeOfMembershipUpperBound) {
		this.highBwDegreeOfMembershipUpperBound = highBwDegreeOfMembershipUpperBound;
	}

	public double getHighBwDegreeOfMembershipLowerBound() {
		return highBwDegreeOfMembershipLowerBound;
	}

	public void setHighBwDegreeOfMembershipLowerBound(double highBwDegreeOfMembershipLowerBound) {
		this.highBwDegreeOfMembershipLowerBound = highBwDegreeOfMembershipLowerBound;
	}

	public double getLowMipsDegreeOfMembershipUpperBound() {
		return lowMipsDegreeOfMembershipUpperBound;
	}

	public void setLowMipsDegreeOfMembershipUpperBound(double lowMipsDegreeOfMembershipUpperBound) {
		this.lowMipsDegreeOfMembershipUpperBound = lowMipsDegreeOfMembershipUpperBound;
	}

	public double getLowMipsDegreeOfMembershipLowerBound() {
		return lowMipsDegreeOfMembershipLowerBound;
	}

	public void setLowMipsDegreeOfMembershipLowerBound(double lowMipsDegreeOfMembershipLowerBound) {
		this.lowMipsDegreeOfMembershipLowerBound = lowMipsDegreeOfMembershipLowerBound;
	}

	public double getMediumMipsDegreeOfMembershipUpperBound() {
		return mediumMipsDegreeOfMembershipUpperBound;
	}

	public void setMediumMipsDegreeOfMembershipUpperBound(double mediumMipsDegreeOfMembershipUpperBound) {
		this.mediumMipsDegreeOfMembershipUpperBound = mediumMipsDegreeOfMembershipUpperBound;
	}

	public double getMediumMipsDegreeOfMembershipLowerBound() {
		return mediumMipsDegreeOfMembershipLowerBound;
	}

	public void setMediumMipsDegreeOfMembershipLowerBound(double mediumMipsDegreeOfMembershipLowerBound) {
		this.mediumMipsDegreeOfMembershipLowerBound = mediumMipsDegreeOfMembershipLowerBound;
	}

	public double getHighMipsDegreeOfMembershipUpperBound() {
		return highMipsDegreeOfMembershipUpperBound;
	}

	public void setHighMipsDegreeOfMembershipUpperBound(double highMipsDegreeOfMembershipUpperBound) {
		this.highMipsDegreeOfMembershipUpperBound = highMipsDegreeOfMembershipUpperBound;
	}

	public double getHighMipsDegreeOfMembershipLowerBound() {
		return highMipsDegreeOfMembershipLowerBound;
	}

	public void setHighMipsDegreeOfMembershipLowerBound(double highMipsDegreeOfMembershipLowerBound) {
		this.highMipsDegreeOfMembershipLowerBound = highMipsDegreeOfMembershipLowerBound;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(mediumStorageDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mediumStorageDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mediumEnergyDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mediumEnergyDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(normalUtilizationDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(normalUtilizationDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highStorageDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highStorageDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highCpuDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highCpuDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highEnergyDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highEnergyDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(overUtilizationDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(overUtilizationDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowCpuDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowCpuDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(underUtilizationDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(underUtilizationDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mediumCpuDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mediumCpuDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowStorageDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowStorageDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowEnergyDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowEnergyDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MembershipDegreesOfType2FuzzySets other = (MembershipDegreesOfType2FuzzySets) obj;
		if (Double.doubleToLongBits(mediumStorageDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.mediumStorageDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(mediumStorageDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.mediumStorageDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(mediumEnergyDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.mediumEnergyDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(mediumEnergyDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.mediumEnergyDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(normalUtilizationDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.normalUtilizationDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(normalUtilizationDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.normalUtilizationDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(highStorageDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.highStorageDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(highStorageDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.highStorageDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(highCpuDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.highCpuDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(highCpuDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.highCpuDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(highEnergyDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.highEnergyDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(highEnergyDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.highEnergyDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(overUtilizationDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.overUtilizationDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(overUtilizationDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.overUtilizationDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(lowCpuDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.lowCpuDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(lowCpuDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.lowCpuDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(underUtilizationDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.underUtilizationDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(underUtilizationDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.underUtilizationDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(mediumCpuDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.mediumCpuDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(mediumCpuDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.mediumCpuDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(lowStorageDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.lowStorageDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(lowStorageDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.lowStorageDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(lowEnergyDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.lowEnergyDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(lowEnergyDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.lowEnergyDegreeOfMembershipUpperBound))
			return false;
		return true;
	}


}
