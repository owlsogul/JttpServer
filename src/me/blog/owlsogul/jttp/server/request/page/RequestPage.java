package me.blog.owlsogul.jttp.server.request.page;

import me.blog.owlsogul.jttp.server.client.Client;
import me.blog.owlsogul.jttp.server.request.Request;
import me.blog.owlsogul.jttp.server.request.Response;

public abstract class RequestPage {

	public abstract void onLoad(String pageName);
	public abstract Response request(Client client, Request request);

}
