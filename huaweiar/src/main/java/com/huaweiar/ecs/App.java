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
import com.huaweiar.ecs.resources.Environment;

public class App {

	static ApiService apiSrv = new ApiService();

	static String token;

	static ApiDataBuilder apiBldr = new ApiDataBuilder();

	public static void main(String[] args) {
		System.out.println(Environment.getAccountName());
		token = apiSrv.postApiCall(apiBldr.buildApiData(ApiEnum.token.ordinal())).headers()
				.firstValue("X-Subject-Token").get();
		System.out.println(token);
		HttpResponse<String> response = changeStateEcsList(getEcsListByTag());
		System.out.println(response.body());
	}

	public static ArrayList<String> getEcsListByTag() {
		ArrayList<String> resourceIdList = new ArrayList<String>();
		Integer i = 0;
		
		JSONObject jsonResponse = new JSONObject(
				apiSrv.getWithTokenAPICall(
						apiBldr.buildApiData(
								ApiEnum.tags.ordinal()), token).body());
		JSONArray resourcesByTagArray = jsonResponse.getJSONArray("servers");
		Integer arrayLength = jsonResponse.getInt("count");

		while ( i <= arrayLength -1 && arrayLength != 1) {
			JSONObject resourceIdObject = (JSONObject) resourcesByTagArray.get(i);
			resourceIdList.add(resourceIdObject.getString("id"));
			i++;
		}
		if (arrayLength == 1) {
			JSONObject resourceIdObject = (JSONObject) resourcesByTagArray.get(0);
			resourceIdList.add(resourceIdObject.getString("id"));
			i++;
		}
		return resourceIdList;
	}

	public static HttpResponse<String> changeStateEcsList(ArrayList<String> resourceIdList) {
		return apiSrv.postWithTokenAPICall(apiBldr.buildApiData(ApiEnum.ecs.ordinal(), resourceIdList), token);
	}

	public APIGTriggerResponse apigTest(APIGTriggerEvent event, Context context) {
		System.out.println(event);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		System.out.println(Environment.getAccountName());
		token = apiSrv.postApiCall(apiBldr.buildApiData(ApiEnum.token.ordinal())).headers()
				.firstValue("X-Subject-Token").get();
		System.out.println(token);
		HttpResponse<String> response = changeStateEcsList(getEcsListByTag());
		System.out.println(response.body());
		return new APIGTriggerResponse(200, headers, event.toString());
	}

	public void ownEvent(MyEvent event, Context ctx) {
		System.out.println(event.toString());
	}

}
