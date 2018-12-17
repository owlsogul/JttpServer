package me.blog.owlsogul.jttp.server.util;

public enum Level {

	Info("정보"), Warning("경고"), Severe("심각");
	
	String value;
	Level(String value) {
		this.value = value;
	}
	
	public String toString() {
		return value;
	}
	
}
