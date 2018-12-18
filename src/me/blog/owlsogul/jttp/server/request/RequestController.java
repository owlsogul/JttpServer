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
import me.blog.owlsogul.jttp.server.request.page.RequestPage;
import me.blog.owlsogul.jttp.server.util.Level;
import me.blog.owlsogul.jttp.server.util.Log;

public class RequestController implements IRequestController{

	private HashMap<String, RequestPage> requestPages;
	private ObserverList<ClientRequestObserver> requestObservers;
	
	public RequestController() {
		requestPages = new HashMap<>();
		requestObservers = new ObserverList<>();
	}
	
	public String parseRequest(Client client, String rawData) {
		long currentTime = System.currentTimeMillis();
		Gson gson = new Gson();
		Response response = null;
		try {
			Request request = gson.fromJson(rawData, Request.class);
			RequestPage requestPage = getRequestPage(request.getCommand());
			response = requestPage.request(client, request);
		} catch (JsonSyntaxException | NullPointerException e) {
			Log.error(Level.Warning, e);
			response = new Response(Response.ErrorJsonParse, new JsonObject());
		} catch (NoRequestPageException e) {
			Log.error(Level.Warning, e);
			response = new Response(Response.ErrorNoRequestPage, new JsonObject());
		}
		if (response.getResponseData() == null) {
			response.setResponseData(new JsonObject());
		}
		Object[] observerData = {client, rawData, response};
		requestObservers.observe(observerData);
		Log.info("%s의 요청: %s, 응답: %s, 소요시간: %dms", client, rawData, response, System.currentTimeMillis() - currentTime);
		return gson.toJson(response);
	}
	
	public void addRequestPage(String command, RequestPage requestPage) throws DuplicatePageException {
		if (!requestPages.containsKey(command)) {
			requestPages.put(command, requestPage);
			requestPage.onLoad(command);
			Log.info("%s이 로드되었습니다.", command);
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
	
	public int getCountRequestPages() {
		return requestPages.size();
	}


	public void stop() {
		requestPages.clear();
		requestObservers.removeAll();
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
