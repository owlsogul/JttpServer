package me.blog.owlsogul.jttp.server.request.exception;

public class NoRequestPageException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoRequestPageException(String requestPageName) {
		super(requestPageName + "가 존재하지 않습니다.");
	}
	
}
