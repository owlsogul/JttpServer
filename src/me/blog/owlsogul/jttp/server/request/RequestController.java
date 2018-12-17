package me.blog.owlsogul.jttp.server.request;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import me.blog.owlsogul.jttp.server.client.Client;
import me.blog.owlsogul.jttp.server.observer.ObserverList;
import me.blog.owlsogul.jttp.server.observer.client.ClientRequestObserver;
import me.blog.owlsogul.jttp.server.request.exception.DuplicatePageException;
import me.blog.owlsogul.jttp.server.request.exception.NoRequestPageException;

public class RequestController implements IRequestController{

	private HashMap<String, RequestPage> requestPages;
	private ObserverList<ClientRequestObserver> requestObservers;
	
	public RequestController() {
		requestPages = new HashMap<>();
		requestObservers = new ObserverList<>();
	}
	
	public String parseRequest(Client client, String rawData) {
		Gson gson = new Gson();
		Response response = null;
		try {
			Request request = gson.fromJson(rawData, Request.class);
			RequestPage requestPage = getRequestPage(request.getCommand());
			response = requestPage.request(client, request);
		} catch (JsonSyntaxException | NullPointerException e) {
			e.printStackTrace();
			response = new Response(Response.ErrorJsonParse, new JsonObject());
		} catch (NoRequestPageException e) {
			e.printStackTrace();
			response = new Response(Response.ErrorNoRequestPage, new JsonObject());
		}
		if (response.getResponseData() == null) {
			response.setResponseData(new JsonObject());
		}
		Object[] observerData = {client, rawData, response};
		requestObservers.observe(observerData);
		return gson.toJson(response);
	}
	
	public void addRequestPage(String command, RequestPage requestPage) throws DuplicatePageException {
		if (!requestPages.containsKey(command)) {
			requestPages.put(command, requestPage);
		} 
		else {
			throw new DuplicatePageException(command);
		}
	}
	
	public RequestPage getRequestPage(String requestPageName) throws NoRequestPageException {
		if (requestPageName != null && requestPages.containsKey(requestPageName)) {
			return requestPages.get(requestPageName);
		}
		else {
			throw new NoRequestPageException(requestPageName);
		}
	}


	
	@Override
	public void addRequestObserver(ClientRequestObserver observer) {
		requestObservers.add(observer);
	}
	

	@Override
	public void removeRequestObserver(ClientRequestObserver observer) {
		requestObservers.remove(observer);
	}
	
}
