package com.huaweiar.ecs.api;

public class ApiDataEntity {

	private String url;

	private String jsonBody;

	public ApiDataEntity(String url, String jsonBody) {
		super();
		this.url = url;
		this.jsonBody = jsonBody;
	}

	public String getUrl() {
		return url;
	}

	public String getJsonBody() {
		return jsonBody;
	}
}
