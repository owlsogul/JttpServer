package me.blog.owlsogul.jttp;

import me.blog.owlsogul.jttp.server.JttpServer;

public class JttpServerMain {
	
	public static void main(String[] args) {
		
		JttpServer server = new JttpServer();
		server.open(30000);
		server.listen();
		
	}
	
}
