package com.huaweiar.ecs.resources;

public class Environment {

	private static String projectId = System.getenv("PROJECT_ID");

	private static String region = System.getenv("REGION");

	private static String accountName = System.getenv("ACCOUNT_NAME");

	private static String iamUser = System.getenv("IAM_USER");

	private static String password = System.getenv("IAM_PASSWORD");

	private static String function = System.getenv("FUNCTION");

	private static String tagKey = System.getenv("TAG_KEY");

	private static String tagValue = System.getenv("TAG_VALUE");

	public static String getTagKey() {
		return tagKey;
	}
	public static String getTagValue() {
		return tagValue;
	}
	public static String getProjectId() {
		return projectId;
	}
	public static String getRegion() {
		return region;
	}
	public static String getAccountName() {
		return accountName;
	}
	public static String getIamUser() {
		return iamUser;
	}
	public static String getPassword() {
		return password;
	}
	public static String getFunction() {
		return function;
	}

}
