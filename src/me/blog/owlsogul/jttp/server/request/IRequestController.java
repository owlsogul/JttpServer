package me.blog.owlsogul.jttp.server.request;

import me.blog.owlsogul.jttp.server.observer.client.ClientRequestObserver;
import me.blog.owlsogul.jttp.server.request.exception.DuplicatePageException;
import me.blog.owlsogul.jttp.server.request.page.RequestPage;

public interface IRequestController {

	public void addRequestObserver(ClientRequestObserver observer);
	public void removeRequestObserver(ClientRequestObserver observer);
	public void addRequestPage(String command, RequestPage requestPage) throws DuplicatePageException;
	
}
