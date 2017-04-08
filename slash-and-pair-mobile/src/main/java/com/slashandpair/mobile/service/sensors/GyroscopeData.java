package com.slashandpair.mobile.service.sensors;

public class GyroscopeData {
	private String alpha;
	private String beta;
	private String gamma;

    public GyroscopeData() {
    }

	public GyroscopeData(String alpha, String beta, String gamma) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
	}



	public String getAlpha() {
		return alpha;
	}

	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	public String getBeta() {
		return beta;
	}

	public void setBeta(String beta) {
		this.beta = beta;
	}

	public String getGamma() {
		return gamma;
	}

	public void setGamma(String gamma) {
		this.gamma = gamma;
	}

    
}
