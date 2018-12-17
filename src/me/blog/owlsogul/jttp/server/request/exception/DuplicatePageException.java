package me.blog.owlsogul.jttp.server.request.exception;

public class DuplicatePageException extends Exception{

	private static final long serialVersionUID = 1L;

	public DuplicatePageException(String requestPageName) {
		super(requestPageName + "는 이미 존재하는 페이지입니다.");
	}
	
}
