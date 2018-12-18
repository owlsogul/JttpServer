package me.blog.owlsogul.jttp.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;

import me.blog.owlsogul.jttp.server.client.ClientController;
import me.blog.owlsogul.jttp.server.client.IClientController;
import me.blog.owlsogul.jttp.server.request.IRequestController;
import me.blog.owlsogul.jttp.server.request.RequestController;
import me.blog.owlsogul.jttp.server.request.page.RequestPage;
import me.blog.owlsogul.jttp.server.request.page.RequestPageLoader;
import me.blog.owlsogul.jttp.server.util.Log;
import me.blog.owlsogul.jttp.server.util.Level;

public class JttpServer implements Runnable, IJttpServer{

	private int port;
	private Thread serverThread;
	private ServerSocket serverSocket;
	private volatile boolean isRunning;
	
	private ClientController clientController;
	private RequestPageLoader requestPageLoader;
	private RequestController requestController;
	
	public static final int DefaultPort = 30000;
	
	public JttpServer() {
		serverThread = new Thread(this);
		isRunning = false;
		requestController = new RequestController();
		clientController = new ClientController(requestController);
		requestPageLoader = new RequestPageLoader();
	}
	
	public boolean open(int port) {
		try {
			serverSocket = new ServerSocket(port);
			isRunning = true;
			this.port = port;
			Log.info("%d번 포트가 열렸습니다.", port);
		} catch (IOException e) {
			Log.log(Level.Severe, "서버 열기를 실패했습니다.");
			Log.error(Level.Severe, e);
			return false;
		}
		
		Log.info("요청 페이지 로드를 시작합니다.");
		loadRequestPages();
		Log.info("요청 페이지 로드가 완료되었습니다. 로드된 페이지 개수 : %d", requestController.getCountRequestPages());
		return true;
	}
	
	public void listen() {
		if (isRunning == false) {
			Log.log(Level.Warning, "JttpServer.open() 메소드가 실행되지 않았습니다. 기본 포트로 서버 오픈을 시도합니다.");
			if (!open(DefaultPort)) {
				return;
			}
		}
		serverThread.start();
		Log.info("%d번 포트로 리스닝 중 입니다.", port);
	}
	
	public IClientController getClientController() {
		return this.clientController;
	}
	
	public IRequestController getRequestController() {
		return requestController;
	}
	
	private void loadRequestPages() {
		File requestFolder = requestPageLoader.getRequestPagesFolder();
		for (File jarFile : requestFolder.listFiles()) {
			if (!jarFile.getName().endsWith(".jar")) continue;
			try {
				HashMap<String, RequestPage> pages = requestPageLoader.getRequestPageFromJar(jarFile);
				for (Entry<String, RequestPage> entry : pages.entrySet()) {
					String pageName = entry.getKey();
					RequestPage requestPage = entry.getValue();
					requestController.addRequestPage(pageName, requestPage);
				}
			} catch (Exception e) {
				Log.log(Level.Warning, "%s 파일 로딩 중 오류가 발생하였습니다. 스킵합니다.", jarFile.getName());
				Log.error(Level.Warning, e);
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
				Log.info("소켓을 등록했습니다.");
			} catch (IOException e) {
				e.printStackTrace();
				if (serverSocket == null) {
					break;	
				}
			}
		}
	}

	@Override
	public void stop() {
		
		clientController.stop();
		requestController.stop();
		try {
			serverSocket.close();
			serverSocket = null;
			isRunning = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
