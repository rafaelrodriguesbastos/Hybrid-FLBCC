package org.cloudbus.cloudsim;

import org.cloudbus.cloudsim.power.PowerHost;

public class HostUseLevel {
	
	private PowerHost powerHost;
	private double outputXValue;
	private double outputYValue;
	
	
	public HostUseLevel() {
		super();
	}



	public HostUseLevel(PowerHost powerHost, double outputXValue, double outputYValue) {
		super();
		this.powerHost = powerHost;
		this.outputXValue = outputXValue;
		this.outputYValue = outputYValue;
	}



	public PowerHost getPowerHost() {
		return powerHost;
	}


	public void setPowerHost(PowerHost powerHost) {
		this.powerHost = powerHost;
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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(outputXValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(outputYValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((powerHost == null) ? 0 : powerHost.hashCode());
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
		HostUseLevel other = (HostUseLevel) obj;
		if (Double.doubleToLongBits(outputXValue) != Double.doubleToLongBits(other.outputXValue))
			return false;
		if (Double.doubleToLongBits(outputYValue) != Double.doubleToLongBits(other.outputYValue))
			return false;
		if (powerHost == null) {
			if (other.powerHost != null)
				return false;
		} else if (!powerHost.equals(other.powerHost))
			return false;
		return true;
	}
	
	

	
}
