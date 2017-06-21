package com.slashandpair.datastructures;

import org.json.JSONObject;

/**
 * Abstract of the objects of the sensors.
 * @author Carlos
 * @author Victor
 * @author Guillermo
 */
public interface AbstractSensor {
	
	/**
	 * This function convert data from object in JSON
	 * @return
	 */
	public JSONObject convertDataInJson();
}
