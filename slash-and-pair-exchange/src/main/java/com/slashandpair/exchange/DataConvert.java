package com.slashandpair.exchange;

import org.json.JSONObject;

import com.slashandpair.datastructures.ClickData;
import com.slashandpair.datastructures.GyroscopeData;
import com.slashandpair.datastructures.ObjectData;

public class DataConvert {
		
	public static String mappingUserAndJson(String userId, String json){
		JSONObject jsonObj = new JSONObject(json);
		
		return jsonObj.put("userId", userId).toString();
	}
	
	public static Object mappingFromJson(String json){
		JSONObject jsonObj = new JSONObject(json);
		String userId = (String) jsonObj.get("userId");
		String type = (String) jsonObj.get("typeData");
		
		switch(type) {
			case "ClickData":
				return new ObjectData (userId, 
						new JSONObject().put("positionX", ((String) jsonObj.get("positionX")))
						.put("positionY", ((String) jsonObj.get("positionY"))));
			
			case "GyroscopeData":
				return new ObjectData (userId, new JSONObject().put("alpha", (String) jsonObj.get("alpha"))
						.put("beta", (String) jsonObj.get("beta")).put("gamma", (String) jsonObj.get("gamma")));
		}
		
		return null;
	}
}
