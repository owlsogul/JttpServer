package me.blog.owlsogul.jttp.server.client;

import me.blog.owlsogul.jttp.server.observer.client.ClientConnectObserver;
import me.blog.owlsogul.jttp.server.observer.client.ClientDisconnectObserver;

public interface IClientController {

	public void addConnectObserver(ClientConnectObserver observer);
	public void removeConnectObserver(ClientConnectObserver observer);
	public void addDisconnectObserver(ClientDisconnectObserver observer);
	public void removeDisconnectObserver(ClientDisconnectObserver observer);
	
}
