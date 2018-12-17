package me.blog.owlsogul.jttp.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import me.blog.owlsogul.jttp.server.client.ClientController;
import me.blog.owlsogul.jttp.server.client.IClientController;
import me.blog.owlsogul.jttp.server.request.IRequestController;
import me.blog.owlsogul.jttp.server.request.RequestController;
import me.blog.owlsogul.jttp.server.request.RequestPage;

public class JttpServer implements Runnable, IJttpServer{

	private Thread serverThread;
	private ServerSocket serverSocket;
	private volatile boolean isRunning;
	private ClientController clientController;
	private RequestController requestController;
	
	public static final int DefaultPort = 30000;
	
	public JttpServer() {
		serverThread = new Thread(this);
		isRunning = false;
		requestController = new RequestController();
		clientController = new ClientController(requestController);
	}
	
	public boolean open(int port) {
		try {
			serverSocket = new ServerSocket(port);
			isRunning = true;
			System.out.printf("%d번 포트가 열렸습니다.\n", port);
			
			loadRequestPages();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void listen() {
		if (isRunning == false) {
			open(DefaultPort);
		}
		serverThread.start();
		System.out.println("소켓 서버로 Listen 하는 중입니다.");
	}
	
	public IClientController getClientController() {
		return this.clientController;
	}
	
	public IRequestController getRequestController() {
		return requestController;
	}
	
	private void loadRequestPages() {
		// TODO: 코드 분리
		File requestFolder = new File("./RequestPages");
		if (!requestFolder.exists()) {
			requestFolder.mkdirs();
			System.out.println("요청 페이지 폴더가 존재하지 않아 새로 만들었습니다.");
		}
		for (File jarFile : requestFolder.listFiles()) {
			if (!jarFile.getName().endsWith(".jar")) continue;
			try {
				// initialize
				URL jarUrl = jarFile.toURI().toURL();
				String main = null;
				String pageName = null;
				
				// json load
				@SuppressWarnings("resource")
				URLClassLoader classLoader = new URLClassLoader(new URL[] {jarUrl}, this.getClass().getClassLoader());
				JsonStreamParser parser = new JsonStreamParser(new InputStreamReader(classLoader.getResourceAsStream("page-info.json")));
				JsonObject pageInfoObj = (JsonObject) parser.next();
				
				// load
				main = pageInfoObj.get("main").getAsString();
				pageName = pageInfoObj.get("page_name").getAsString();
				RequestPage rp = (RequestPage) classLoader.loadClass(main).newInstance();
				requestController.addRequestPage(pageName, rp);
				System.out.println(pageName + "이 로드되었습니다.");
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(jarFile.getName() + " 파일 로딩 중 오류가 발생하였습니다. 스킵합니다.");
				continue;
			} 
		}
	}
	
	
	@Override
	public void run() {
		while (isRunning) {
			try {
				Socket socket = serverSocket.accept();
				clientController.registerClient(socket);
				System.out.println("소켓을 등록했습니다.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
