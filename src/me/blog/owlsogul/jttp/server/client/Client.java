package me.blog.owlsogul.jttp.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonStreamParser;

import me.blog.owlsogul.jttp.server.util.Log;

public class Client implements Runnable{

	private Socket socket;
	private ClientController controller;
	private Thread thread;
	
	private JsonStreamParser jsp;
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
		try {
			pw.println(msg);
			pw.flush();
			Log.info("%s으로 데이터를 보냈습니다: %s", toString(), msg);
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {

		try {
			jsp = new JsonStreamParser(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			JsonArray requestArray = new JsonArray();
			while (jsp.hasNext()) {
				JsonElement request = jsp.next();
				requestArray.add(request);
				Log.info("%s에서 데이터를 보냈습니다: %s", toString(), request);
				String response = controller.parseRequest(this, request.toString());
				sendString(response);
			}
			pw.close();
			socket.close();
		} catch(JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller.unregisterClient(this);
		
	}

	@Override
	public String toString() {
		return socket.getInetAddress().toString();
	}
	
	
	public void stop() {
		try {
			pw.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
