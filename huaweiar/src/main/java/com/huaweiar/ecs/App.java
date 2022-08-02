package com.huaweiar.ecs;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.huawei.services.runtime.Context;
import com.huawei.services.runtime.entity.apig.APIGTriggerEvent;
import com.huawei.services.runtime.entity.apig.APIGTriggerResponse;
import com.huaweiar.ecs.api.ApiDataBuilder;
import com.huaweiar.ecs.api.ApiEnum;
import com.huaweiar.ecs.api.ApiService;

public class App {

	static ApiService apiSrv= new ApiService();
	
	static String token;
	
	static ApiDataBuilder apiBldr = new ApiDataBuilder();
	
	public static void main(String[] args) {
		token = apiSrv.postApiCall(
						apiBldr.buildApiData(ApiEnum.token.ordinal()))
				.headers().firstValue("X-Subject-Token").get();	
		System.out.println(token);
		HttpResponse<String> response = changeStateEcsList(getEcsListByTag());
		System.out.println(response.body());
	}

	public static ArrayList<String> getEcsListByTag() {
		ArrayList<String> resourceIdList = new ArrayList<String>();
		JSONObject jsonResponse = new JSONObject(
				apiSrv.getWithTokenAPICall(
						apiBldr.buildApiData(ApiEnum.tags.ordinal()), 
						token
				).body());	
		
		JSONArray resourcesByTagArray = jsonResponse.getJSONArray("servers");
		for (int i = 0 ; i < jsonResponse.length() -1; i++) {
			JSONObject resourceIdObject = (JSONObject) resourcesByTagArray.get(i);
			resourceIdList.add(resourceIdObject.get("id").toString());
		}

		return resourceIdList;
	}
	
	public static HttpResponse<String> changeStateEcsList(ArrayList<String> resourceIdList) {
		return apiSrv.postWithTokenAPICall(
				apiBldr.buildApiData(ApiEnum.ecs.ordinal(), resourceIdList), 
				token
		);
	}
	
	public APIGTriggerResponse apigTest(APIGTriggerEvent event, Context context){
        System.out.println(event);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        return new APIGTriggerResponse(200, headers, event.toString());
    }

    public void  ownEvent(MyEvent event, Context ctx){
        System.out.println(event.toString());
    }
	
}
