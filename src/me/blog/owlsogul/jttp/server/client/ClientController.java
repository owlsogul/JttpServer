package me.blog.owlsogul.jttp.server.client;

import java.net.Socket;
import java.util.LinkedList;

import me.blog.owlsogul.jttp.server.observer.ObserverList;
import me.blog.owlsogul.jttp.server.observer.client.ClientConnectObserver;
import me.blog.owlsogul.jttp.server.observer.client.ClientDisconnectObserver;
import me.blog.owlsogul.jttp.server.request.RequestController;
import me.blog.owlsogul.jttp.server.util.Log;

public class ClientController implements IClientController{

	private LinkedList<Client> clients;
	private RequestController requestController;
	private ObserverList<ClientConnectObserver> connectObservers;
	private ObserverList<ClientDisconnectObserver> disconnectObservers;
	
	public ClientController(RequestController requestController){
		this.requestController = requestController;
		clients = new LinkedList<>();
		connectObservers = new ObserverList<>();
		disconnectObservers = new ObserverList<>();
	}
	
	public void registerClient(Socket socket) {
		Client client = new Client(this, socket);
		clients.add(client);
		connectObservers.observe(client);
		client.start();
		Log.info("클라이언트 %s 가 등록되었습니다.", client.toString());
	}

	public void unregisterClient(Client client) {
		clients.remove(client);
		disconnectObservers.observe(client);
		Log.info("클라이언트 %s 가 등록 해제되었습니다.", client.toString());
	}

	public String parseRequest(Client client, String rawData) {
		return requestController.parseRequest(client, rawData);
	}
	
	// #########################################################
	//          implementation of IClientController
	// #########################################################
	
	@Override
	public void addConnectObserver(ClientConnectObserver observer) {
		connectObservers.add(observer);
	}

	@Override
	public void removeConnectObserver(ClientConnectObserver observer) {
		connectObservers.remove(observer);
	}

	
	@Override
	public void addDisconnectObserver(ClientDisconnectObserver observer) {
		disconnectObservers.add(observer);
	}
	

	@Override
	public void removeDisconnectObserver(ClientDisconnectObserver observer) {
		disconnectObservers.add(observer);
	}
	
}
