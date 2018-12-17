package me.blog.owlsogul.jttp.server.request;

import me.blog.owlsogul.jttp.server.client.Client;

public abstract class RequestPage {

	public abstract void onLoad(String pageName);
	public abstract Response request(Client client, Request request);

}
