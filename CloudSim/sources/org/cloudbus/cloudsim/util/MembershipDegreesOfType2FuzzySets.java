package org.cloudbus.cloudsim.util;

import java.io.Serializable;

public class MembershipDegreesOfType2FuzzySets implements Serializable {

	private static final long serialVersionUID = 1L;

	// Degree Of Membership Functions CP Limited
	private double limitedCPDegreeOfMembershipUpperBound;
	private double limitedCPDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions CP Reasonable
	private double reasonableCPDegreeOfMembershipUpperBound;
	private double reasonableCPDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions CP High
	private double highCPDegreeOfMembershipUpperBound;
	private double highCPDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions CC Small
	private double smallCCDegreeOfMembershipUpperBound;
	private double smallCCDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions CC Average
	private double averageCCDegreeOfMembershipUpperBound;
	private double averageCCDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions CC Big
	private double bigCCDegreeOfMembershipUpperBound;
	private double bigCCDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions RAM Small
	private double smallRAMDegreeOfMembershipUpperBound;
	private double smallRAMDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions RAM Average
	private double averageRAMDegreeOfMembershipUpperBound;
	private double averageRAMDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions RAM High
	private double highRAMDegreeOfMembershipUpperBound;
	private double highRAMDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions Utilization Low
	private double lowUtilizationDegreeOfMembershipUpperBound;
	private double lowUtilizationDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions Utilization Average
	private double averageUtilizationDegreeOfMembershipUpperBound;
	private double averageUtilizationDegreeOfMembershipLowerBound;

	// Degree Of Membership Functions Utilization High
	private double highUtilizationDegreeOfMembershipUpperBound;
	private double highUtilizationDegreeOfMembershipLowerBound;

	public MembershipDegreesOfType2FuzzySets() {
		super();
	}

	public MembershipDegreesOfType2FuzzySets(double limitedCPDegreeOfMembershipUpperBound,
			double limitedCPDegreeOfMembershipLowerBound, double reasonableCPDegreeOfMembershipUpperBound,
			double reasonableCPDegreeOfMembershipLowerBound, double highCPDegreeOfMembershipUpperBound,
			double highCPDegreeOfMembershipLowerBound, double smallCCDegreeOfMembershipUpperBound,
			double smallCCDegreeOfMembershipLowerBound, double averageCCDegreeOfMembershipUpperBound,
			double averageCCDegreeOfMembershipLowerBound, double bigCCDegreeOfMembershipUpperBound,
			double bigCCDegreeOfMembershipLowerBound, double smallRAMDegreeOfMembershipUpperBound,
			double smallRAMDegreeOfMembershipLowerBound, double averageRAMDegreeOfMembershipUpperBound,
			double averageRAMDegreeOfMembershipLowerBound, double highRAMDegreeOfMembershipUpperBound,
			double highRAMDegreeOfMembershipLowerBound, double lowUtilizationDegreeOfMembershipUpperBound,
			double lowUtilizationDegreeOfMembershipLowerBound, double averageUtilizationDegreeOfMembershipUpperBound,
			double averageUtilizationDegreeOfMembershipLowerBound, double highUtilizationDegreeOfMembershipUpperBound,
			double highUtilizationDegreeOfMembershipLowerBound) {
		super();
		this.limitedCPDegreeOfMembershipUpperBound = limitedCPDegreeOfMembershipUpperBound;
		this.limitedCPDegreeOfMembershipLowerBound = limitedCPDegreeOfMembershipLowerBound;
		this.reasonableCPDegreeOfMembershipUpperBound = reasonableCPDegreeOfMembershipUpperBound;
		this.reasonableCPDegreeOfMembershipLowerBound = reasonableCPDegreeOfMembershipLowerBound;
		this.highCPDegreeOfMembershipUpperBound = highCPDegreeOfMembershipUpperBound;
		this.highCPDegreeOfMembershipLowerBound = highCPDegreeOfMembershipLowerBound;
		this.smallCCDegreeOfMembershipUpperBound = smallCCDegreeOfMembershipUpperBound;
		this.smallCCDegreeOfMembershipLowerBound = smallCCDegreeOfMembershipLowerBound;
		this.averageCCDegreeOfMembershipUpperBound = averageCCDegreeOfMembershipUpperBound;
		this.averageCCDegreeOfMembershipLowerBound = averageCCDegreeOfMembershipLowerBound;
		this.bigCCDegreeOfMembershipUpperBound = bigCCDegreeOfMembershipUpperBound;
		this.bigCCDegreeOfMembershipLowerBound = bigCCDegreeOfMembershipLowerBound;
		this.smallRAMDegreeOfMembershipUpperBound = smallRAMDegreeOfMembershipUpperBound;
		this.smallRAMDegreeOfMembershipLowerBound = smallRAMDegreeOfMembershipLowerBound;
		this.averageRAMDegreeOfMembershipUpperBound = averageRAMDegreeOfMembershipUpperBound;
		this.averageRAMDegreeOfMembershipLowerBound = averageRAMDegreeOfMembershipLowerBound;
		this.highRAMDegreeOfMembershipUpperBound = highRAMDegreeOfMembershipUpperBound;
		this.highRAMDegreeOfMembershipLowerBound = highRAMDegreeOfMembershipLowerBound;
		this.lowUtilizationDegreeOfMembershipUpperBound = lowUtilizationDegreeOfMembershipUpperBound;
		this.lowUtilizationDegreeOfMembershipLowerBound = lowUtilizationDegreeOfMembershipLowerBound;
		this.averageUtilizationDegreeOfMembershipUpperBound = averageUtilizationDegreeOfMembershipUpperBound;
		this.averageUtilizationDegreeOfMembershipLowerBound = averageUtilizationDegreeOfMembershipLowerBound;
		this.highUtilizationDegreeOfMembershipUpperBound = highUtilizationDegreeOfMembershipUpperBound;
		this.highUtilizationDegreeOfMembershipLowerBound = highUtilizationDegreeOfMembershipLowerBound;
	}

	public double getLimitedCPDegreeOfMembershipUpperBound() {
		return limitedCPDegreeOfMembershipUpperBound;
	}

	public void setLimitedCPDegreeOfMembershipUpperBound(double limitedCPDegreeOfMembershipUpperBound) {
		this.limitedCPDegreeOfMembershipUpperBound = limitedCPDegreeOfMembershipUpperBound;
	}

	public double getLimitedCPDegreeOfMembershipLowerBound() {
		return limitedCPDegreeOfMembershipLowerBound;
	}

	public void setLimitedCPDegreeOfMembershipLowerBound(double limitedCPDegreeOfMembershipLowerBound) {
		this.limitedCPDegreeOfMembershipLowerBound = limitedCPDegreeOfMembershipLowerBound;
	}

	public double getReasonableCPDegreeOfMembershipUpperBound() {
		return reasonableCPDegreeOfMembershipUpperBound;
	}

	public void setReasonableCPDegreeOfMembershipUpperBound(double reasonableCPDegreeOfMembershipUpperBound) {
		this.reasonableCPDegreeOfMembershipUpperBound = reasonableCPDegreeOfMembershipUpperBound;
	}

	public double getReasonableCPDegreeOfMembershipLowerBound() {
		return reasonableCPDegreeOfMembershipLowerBound;
	}

	public void setReasonableCPDegreeOfMembershipLowerBound(double reasonableCPDegreeOfMembershipLowerBound) {
		this.reasonableCPDegreeOfMembershipLowerBound = reasonableCPDegreeOfMembershipLowerBound;
	}

	public double getHighCPDegreeOfMembershipUpperBound() {
		return highCPDegreeOfMembershipUpperBound;
	}

	public void setHighCPDegreeOfMembershipUpperBound(double highCPDegreeOfMembershipUpperBound) {
		this.highCPDegreeOfMembershipUpperBound = highCPDegreeOfMembershipUpperBound;
	}

	public double getHighCPDegreeOfMembershipLowerBound() {
		return highCPDegreeOfMembershipLowerBound;
	}

	public void setHighCPDegreeOfMembershipLowerBound(double highCPDegreeOfMembershipLowerBound) {
		this.highCPDegreeOfMembershipLowerBound = highCPDegreeOfMembershipLowerBound;
	}

	public double getSmallCCDegreeOfMembershipUpperBound() {
		return smallCCDegreeOfMembershipUpperBound;
	}

	public void setSmallCCDegreeOfMembershipUpperBound(double smallCCDegreeOfMembershipUpperBound) {
		this.smallCCDegreeOfMembershipUpperBound = smallCCDegreeOfMembershipUpperBound;
	}

	public double getSmallCCDegreeOfMembershipLowerBound() {
		return smallCCDegreeOfMembershipLowerBound;
	}

	public void setSmallCCDegreeOfMembershipLowerBound(double smallCCDegreeOfMembershipLowerBound) {
		this.smallCCDegreeOfMembershipLowerBound = smallCCDegreeOfMembershipLowerBound;
	}

	public double getAverageCCDegreeOfMembershipUpperBound() {
		return averageCCDegreeOfMembershipUpperBound;
	}

	public void setAverageCCDegreeOfMembershipUpperBound(double averageCCDegreeOfMembershipUpperBound) {
		this.averageCCDegreeOfMembershipUpperBound = averageCCDegreeOfMembershipUpperBound;
	}

	public double getAverageCCDegreeOfMembershipLowerBound() {
		return averageCCDegreeOfMembershipLowerBound;
	}

	public void setAverageCCDegreeOfMembershipLowerBound(double averageCCDegreeOfMembershipLowerBound) {
		this.averageCCDegreeOfMembershipLowerBound = averageCCDegreeOfMembershipLowerBound;
	}

	public double getBigCCDegreeOfMembershipUpperBound() {
		return bigCCDegreeOfMembershipUpperBound;
	}

	public void setBigCCDegreeOfMembershipUpperBound(double bigCCDegreeOfMembershipUpperBound) {
		this.bigCCDegreeOfMembershipUpperBound = bigCCDegreeOfMembershipUpperBound;
	}

	public double getBigCCDegreeOfMembershipLowerBound() {
		return bigCCDegreeOfMembershipLowerBound;
	}

	public void setBigCCDegreeOfMembershipLowerBound(double bigCCDegreeOfMembershipLowerBound) {
		this.bigCCDegreeOfMembershipLowerBound = bigCCDegreeOfMembershipLowerBound;
	}

	public double getSmallRAMDegreeOfMembershipUpperBound() {
		return smallRAMDegreeOfMembershipUpperBound;
	}

	public void setSmallRAMDegreeOfMembershipUpperBound(double smallRAMDegreeOfMembershipUpperBound) {
		this.smallRAMDegreeOfMembershipUpperBound = smallRAMDegreeOfMembershipUpperBound;
	}

	public double getSmallRAMDegreeOfMembershipLowerBound() {
		return smallRAMDegreeOfMembershipLowerBound;
	}

	public void setSmallRAMDegreeOfMembershipLowerBound(double smallRAMDegreeOfMembershipLowerBound) {
		this.smallRAMDegreeOfMembershipLowerBound = smallRAMDegreeOfMembershipLowerBound;
	}

	public double getAverageRAMDegreeOfMembershipUpperBound() {
		return averageRAMDegreeOfMembershipUpperBound;
	}

	public void setAverageRAMDegreeOfMembershipUpperBound(double averageRAMDegreeOfMembershipUpperBound) {
		this.averageRAMDegreeOfMembershipUpperBound = averageRAMDegreeOfMembershipUpperBound;
	}

	public double getAverageRAMDegreeOfMembershipLowerBound() {
		return averageRAMDegreeOfMembershipLowerBound;
	}

	public void setAverageRAMDegreeOfMembershipLowerBound(double averageRAMDegreeOfMembershipLowerBound) {
		this.averageRAMDegreeOfMembershipLowerBound = averageRAMDegreeOfMembershipLowerBound;
	}

	public double getHighRAMDegreeOfMembershipUpperBound() {
		return highRAMDegreeOfMembershipUpperBound;
	}

	public void setHighRAMDegreeOfMembershipUpperBound(double highRAMDegreeOfMembershipUpperBound) {
		this.highRAMDegreeOfMembershipUpperBound = highRAMDegreeOfMembershipUpperBound;
	}

	public double getHighRAMDegreeOfMembershipLowerBound() {
		return highRAMDegreeOfMembershipLowerBound;
	}

	public void setHighRAMDegreeOfMembershipLowerBound(double highRAMDegreeOfMembershipLowerBound) {
		this.highRAMDegreeOfMembershipLowerBound = highRAMDegreeOfMembershipLowerBound;
	}

	public double getLowUtilizationDegreeOfMembershipUpperBound() {
		return lowUtilizationDegreeOfMembershipUpperBound;
	}

	public void setLowUtilizationDegreeOfMembershipUpperBound(double lowUtilizationDegreeOfMembershipUpperBound) {
		this.lowUtilizationDegreeOfMembershipUpperBound = lowUtilizationDegreeOfMembershipUpperBound;
	}

	public double getLowUtilizationDegreeOfMembershipLowerBound() {
		return lowUtilizationDegreeOfMembershipLowerBound;
	}

	public void setLowUtilizationDegreeOfMembershipLowerBound(double lowUtilizationDegreeOfMembershipLowerBound) {
		this.lowUtilizationDegreeOfMembershipLowerBound = lowUtilizationDegreeOfMembershipLowerBound;
	}

	public double getAverageUtilizationDegreeOfMembershipUpperBound() {
		return averageUtilizationDegreeOfMembershipUpperBound;
	}

	public void setAverageUtilizationDegreeOfMembershipUpperBound(
			double averageUtilizationDegreeOfMembershipUpperBound) {
		this.averageUtilizationDegreeOfMembershipUpperBound = averageUtilizationDegreeOfMembershipUpperBound;
	}

	public double getAverageUtilizationDegreeOfMembershipLowerBound() {
		return averageUtilizationDegreeOfMembershipLowerBound;
	}

	public void setAverageUtilizationDegreeOfMembershipLowerBound(
			double averageUtilizationDegreeOfMembershipLowerBound) {
		this.averageUtilizationDegreeOfMembershipLowerBound = averageUtilizationDegreeOfMembershipLowerBound;
	}

	public double getHighUtilizationDegreeOfMembershipUpperBound() {
		return highUtilizationDegreeOfMembershipUpperBound;
	}

	public void setHighUtilizationDegreeOfMembershipUpperBound(double highUtilizationDegreeOfMembershipUpperBound) {
		this.highUtilizationDegreeOfMembershipUpperBound = highUtilizationDegreeOfMembershipUpperBound;
	}

	public double getHighUtilizationDegreeOfMembershipLowerBound() {
		return highUtilizationDegreeOfMembershipLowerBound;
	}

	public void setHighUtilizationDegreeOfMembershipLowerBound(double highUtilizationDegreeOfMembershipLowerBound) {
		this.highUtilizationDegreeOfMembershipLowerBound = highUtilizationDegreeOfMembershipLowerBound;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(averageCCDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(averageCCDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(averageRAMDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(averageRAMDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(averageUtilizationDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(averageUtilizationDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(bigCCDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(bigCCDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highCPDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highCPDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highRAMDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highRAMDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highUtilizationDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(highUtilizationDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(limitedCPDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(limitedCPDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowUtilizationDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowUtilizationDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(reasonableCPDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(reasonableCPDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(smallCCDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(smallCCDegreeOfMembershipUpperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(smallRAMDegreeOfMembershipLowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(smallRAMDegreeOfMembershipUpperBound);
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
		if (Double.doubleToLongBits(averageCCDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.averageCCDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(averageCCDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.averageCCDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(averageRAMDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.averageRAMDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(averageRAMDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.averageRAMDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(averageUtilizationDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.averageUtilizationDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(averageUtilizationDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.averageUtilizationDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(bigCCDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.bigCCDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(bigCCDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.bigCCDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(highCPDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.highCPDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(highCPDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.highCPDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(highRAMDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.highRAMDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(highRAMDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.highRAMDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(highUtilizationDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.highUtilizationDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(highUtilizationDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.highUtilizationDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(limitedCPDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.limitedCPDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(limitedCPDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.limitedCPDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(lowUtilizationDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.lowUtilizationDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(lowUtilizationDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.lowUtilizationDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(reasonableCPDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.reasonableCPDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(reasonableCPDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.reasonableCPDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(smallCCDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.smallCCDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(smallCCDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.smallCCDegreeOfMembershipUpperBound))
			return false;
		if (Double.doubleToLongBits(smallRAMDegreeOfMembershipLowerBound) != Double
				.doubleToLongBits(other.smallRAMDegreeOfMembershipLowerBound))
			return false;
		if (Double.doubleToLongBits(smallRAMDegreeOfMembershipUpperBound) != Double
				.doubleToLongBits(other.smallRAMDegreeOfMembershipUpperBound))
			return false;
		return true;
	}
	
	
	

}
