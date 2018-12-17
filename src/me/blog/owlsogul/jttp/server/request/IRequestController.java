package me.blog.owlsogul.jttp.server.request;

import me.blog.owlsogul.jttp.server.observer.client.ClientRequestObserver;

public interface IRequestController {

	public void addRequestObserver(ClientRequestObserver observer);
	public void removeRequestObserver(ClientRequestObserver observer);
	
}
