package me.blog.owlsogul.jttp.server;

import me.blog.owlsogul.jttp.server.client.IClientController;
import me.blog.owlsogul.jttp.server.request.IRequestController;

public interface IJttpServer {

	public boolean open(int port);
	public void listen();
	public void stop();
	public IClientController getClientController();
	public IRequestController getRequestController();
	
}
