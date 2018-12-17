package me.blog.owlsogul.jttp.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{

	private Socket socket;
	private ClientController controller;
	private Thread thread;
	
	private BufferedReader br;
	private PrintWriter pw;
	
	public Client(ClientController controller, Socket socket) {
		this.controller = controller;
		this.socket = socket;
		this.thread = new Thread(this);
	}
	
	public Socket getSocket() {
		return this.socket;
	}
	
	public void start() {
		thread.start();
	}
	
	public void sendString(String msg) {
		pw.println(msg);
		pw.flush();
	}
	
	@Override
	public void run() {

		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			
			String request = br.readLine();
			String response = controller.parseRequest(this, request);
			sendString(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller.unregisterClient(this);
		
	}

	@Override
	public String toString() {
		return socket.getInetAddress().toString();
	}
	
	
}
