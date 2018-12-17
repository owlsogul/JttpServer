package me.blog.owlsogul.jttp.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import me.blog.owlsogul.jttp.server.util.Log;

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
		Log.info("%s으로 데이터를 보냈습니다: %s", toString(), msg);
	}
	
	@Override
	public void run() {

		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			
			String request = br.readLine();
			Log.info("%s에서 데이터를 보냈습니다: %s", toString(), request);
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
