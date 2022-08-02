package com.huaweiar.ecs.api;

import java.util.ArrayList;

import org.json.JSONObject;

import com.huaweiar.ecs.resources.Environment;

public class ApiDataBuilder {
	
	final String tokenUrl = "https://iam.myhuaweicloud.com/v3/auth/tokens";

	final String tagsUrl = "https://ecs."+Environment.getRegion()+".myhuaweicloud.com/v1/"+Environment.getProjectId()+"/cloudservers/detail?tags="+Environment.getTagKey()+"="+Environment.getTagValue();

	final String ecsUrl = "https://ecs."+Environment.getRegion()+".myhuaweicloud.com/v1/"+Environment.getProjectId()+"/cloudservers/action";

	final String tokenJsonBody = "{\r\n"
			+ "    \"auth\": {\r\n"
			+ "        \"identity\": {\r\n"
			+ "            \"methods\": [\r\n"
			+ "                \"password\"\r\n"
			+ "            ],\r\n"
			+ "            \"password\": {\r\n"
			+ "                \"user\": {\r\n"
			+ "                    \"name\": \""+Environment.getIamUser()+"\",\r\n"
			+ "                    \"password\": \""+Environment.getPassword()+"\",\r\n"
			+ "                    \"domain\": {\r\n"
			+ "                        \"name\": \""+Environment.getAccountName()+"\"\r\n"
			+ "                    }\r\n"
			+ "                }\r\n"
			+ "            }\r\n"
			+ "        },\r\n"
			+ "        \"scope\": {\r\n"
			+ "            \"project\": {\r\n"
			+ "                \"name\": \""+Environment.getRegion()+"\"\r\n"
			+ "            }\r\n"
			+ "        }\r\n"
			+ "    }\r\n"
			+ "}";

	final String tagsJsonBody= "{\r\n"
			+ "    \"action\": \"filter\", \r\n"
			+ "    \"tags\": [\r\n"
			+ "    {\r\n"
			+ "        \"key\": \""+Environment.getTagKey()+"\", \r\n"
			+ "        \"values\": [\r\n"
			+ "            \""+Environment.getTagValue()+"\"\r\n"
			+ "        ]\r\n"
			+ "    }]\r\n"
			+ "}";

	final String startEcsJsonBody = "{\r\n"
			+ "    \"os-start\": {\r\n"
			+ "        \"servers\": [\r\n"
			+ "        ]\r\n"
			+ "    }\r\n"
			+ "}";

	final String stopEcsJsonBody = "{\r\n"
			+ "    \"os-stop\": {\r\n"
			+ "        \"type\":\"HARD\",\r\n"
			+ "        \"servers\": [\r\n"
			+ 			"\r\n"
			+ "        ]\r\n"
			+ "    }\r\n"
			+ "}";

	public ApiDataEntity buildApiData(Integer apisIndex) {
		ApiDataEntity data = null;
		switch(apisIndex) {
			case 0: data = new ApiDataEntity(this.tokenUrl,this.tokenJsonBody); break;
			case 1: data = new ApiDataEntity(this.tagsUrl,""); break;
		} 
		return data;	
	}

	public ApiDataEntity buildApiData(Integer apisIndex, ArrayList<String> ecsIdList) {
		ApiDataEntity data = null;
		if (Environment.getFunction().contentEquals("os-start")) {
			JSONObject jsonTemplate = new JSONObject(startEcsJsonBody);

			for (Integer i = 0 ; i <= ecsIdList.size() - 1 ; i++) {
				JSONObject tmp = (JSONObject) jsonTemplate.get("os-start");
				tmp.getJSONArray("servers")
				.put(i, new JSONObject().put("id",ecsIdList.get(i)));
			}
			data = new ApiDataEntity(this.ecsUrl, jsonTemplate.toString());	
		}	

		if (Environment.getFunction().contentEquals("os-stop")) {
			JSONObject jsonTemplate = new JSONObject(stopEcsJsonBody);

			for (Integer i = 0 ; i <= ecsIdList.size() -1 ; i++) {
				JSONObject tmp = (JSONObject) jsonTemplate.get("os-stop");
				tmp.getJSONArray("servers")
				.put(i, new JSONObject().put("id", ecsIdList.get(i)));
			}
			data = new ApiDataEntity(this.ecsUrl, jsonTemplate.toString());
		} 
		return data;
	}
	
}
