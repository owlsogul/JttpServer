package me.blog.owlsogul.jttp;

import me.blog.owlsogul.jttp.server.JttpServer;
import me.blog.owlsogul.jttp.server.util.Log;

public class JttpServerMain {
	
	public static void main(String[] args) {

		int port = JttpServer.DefaultPort;
		try {
			port = Integer.valueOf(args[0]);
		}catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
			e.printStackTrace();
		}
		Log.info("포트 %d번이 확인 되었습니다.", port);
		JttpServer server = new JttpServer();
		server.open(port);
		server.listen();
		
	}
	
}
