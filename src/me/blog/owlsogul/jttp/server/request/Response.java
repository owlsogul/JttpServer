package me.blog.owlsogul.jttp.server.request;

import com.google.gson.JsonObject;

public class Response {

	public static final int Success = 200;
	public static final int ErrorJsonParse = 400;
	public static final int ErrorNoRequestPage = 404;
	
	private int responseCode;
	private JsonObject responseData;
	
	public Response(int responseCode, JsonObject responseData) {
		this.setResponseCode(responseCode);
		this.setResponseData(responseData);
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public JsonObject getResponseData() {
		return responseData;
	}

	public void setResponseData(JsonObject responseData) {
		this.responseData = responseData;
	}
	
}
