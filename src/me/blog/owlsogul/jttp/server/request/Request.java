package me.blog.owlsogul.jttp.server.request;

import com.google.gson.JsonObject;

public class Request {
	
	private String command;
	private JsonObject data;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public JsonObject getData() {
		return data;
	}
	public void setData(JsonObject data) {
		this.data = data;
	}
	
	
	
}
