package com.slashandpair.datastructures;

import org.json.JSONObject;

public class GyroscopeData {
	private String alpha;
	private String beta;
	private String gamma;
	private String user;
	
    public GyroscopeData() {
    }

	public GyroscopeData(String user, String alpha, String beta, String gamma) {
		super();
		this.user = user;
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
	}



	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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
	
	
    public String toString1(){
    	return "GyroscopeData: userId - "+ this.user + " a: " + this.alpha + " b: " + this.beta + " c: " +this.gamma ;
    }
    
    @Override
    public String toString() {
        String jsonString = new JSONObject()
                .put("userId", this.user)
                .put("alpha", this.alpha)
                .put("betta", this.beta)
                .put("gamma", this.gamma).toString();
        return jsonString;
    }
    
    public void createGyroscopeDataFromJSON(String json){
    	JSONObject jsonObj = new JSONObject(json);
    	this.user = (String) jsonObj.get("userId");
    	this.alpha = (String) jsonObj.get("alpha");
    	this.beta = (String) jsonObj.get("betta");
    	this.gamma = (String) jsonObj.get("gamma");
    }
    
}
