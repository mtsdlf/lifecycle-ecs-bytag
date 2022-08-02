package com.huaweiar.ecs.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
	private final HttpClient client = HttpClient.newHttpClient();

	public  HttpResponse<String> postWithTokenAPICall(ApiDataEntity apiData, String tokenValue) {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(apiData.getUrl()))
				.header("X-Auth-Token", tokenValue)
				.POST(HttpRequest.BodyPublishers.ofString(apiData.getJsonBody()))
				.build();
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return response;
	}

	public  HttpResponse<String> getWithTokenAPICall(ApiDataEntity apiData, String tokenValue) {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(apiData.getUrl()))
				.header("X-Auth-Token", tokenValue)
				.GET()
				.build();
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println(response.body());
		return response;
	}

	public  HttpResponse<String> postApiCall(ApiDataEntity apiData) {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(apiData.getUrl()))
				.POST(HttpRequest.BodyPublishers.ofString(apiData.getJsonBody()))
				.build();
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
