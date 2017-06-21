package com.slashandpair.datastructures;

import org.json.JSONObject;

import lombok.Data;

/**
 * Pojo for ObjectData
 * @author Carlos
 * @author Victor
 * @author Guillermo
 */
@Data
public class ObjectData {
	private String userId;
	private JSONObject json;
	
	public ObjectData(String userId, JSONObject json){
		this.userId = userId;
		this.json = json;
	}
}
