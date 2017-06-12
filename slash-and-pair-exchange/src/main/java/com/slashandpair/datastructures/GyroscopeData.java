package com.slashandpair.datastructures;

import org.json.JSONObject;

import lombok.Data;


@Data
public class GyroscopeData {
	private String alpha;
	private String beta;
	private String gamma;
	
	public GyroscopeData(String alpha, String beta, String gamma) {
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
	}
	
	public GyroscopeData(String json){
    	JSONObject jsonObj = new JSONObject(json);
    	this.alpha = String.valueOf(jsonObj.get("alpha"));
    	this.beta = String.valueOf(jsonObj.get("beta"));
    	this.gamma = String.valueOf(jsonObj.get("gamma"));
    }
    
    public JSONObject convertDataInJson() {
        return new JSONObject()
                .put("typeData", "GyroscopeData")
                .put("alpha", this.alpha)
                .put("beta", this.beta)
                .put("gamma", this.gamma);
    }
	
	@Override
    public String toString(){
    	return "GyroscopeData: a: " + this.alpha + " b: " + this.beta + " c: " +this.gamma ;
    }
    
    
}
