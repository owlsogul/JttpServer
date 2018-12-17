package me.blog.owlsogul.jttp.server.request.page;

import java.io.File;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import me.blog.owlsogul.jttp.server.util.Log;

public class RequestPageLoader {

	
	public File getRequestPagesFolder() {
		File requestFolder = new File("./RequestPages");
		if (!requestFolder.exists()) {
			requestFolder.mkdirs();
			Log.info("요청 페이지 폴더가 존재하지 않아 새로 만들었습니다.");
		}
		return requestFolder;
	}
	
	public HashMap<String, RequestPage> getRequestPageFromJar(File jarFile) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MalformedURLException {
		
		// init
		HashMap<String, RequestPage> pages = new HashMap<>();
		URL jarUrl = jarFile.toURI().toURL();
		String main = null;
		String pageName = null;
		Log.info("%s 파일 로드를 시작합니다", jarFile.getName());
		
		// json load
		@SuppressWarnings("resource")
		URLClassLoader classLoader = new URLClassLoader(new URL[] {jarUrl}, this.getClass().getClassLoader());
		JsonStreamParser parser = new JsonStreamParser(new InputStreamReader(classLoader.getResourceAsStream("page-info.json")));
		JsonArray pageInfoArr = (JsonArray) parser.next();
		
		// load
		for (JsonElement pageInfoEle : pageInfoArr) {
			JsonObject pageInfoObj = pageInfoEle.getAsJsonObject();
			main = pageInfoObj.get("main").getAsString();
			pageName = pageInfoObj.get("page_name").getAsString();
			RequestPage rp = (RequestPage) classLoader.loadClass(main).newInstance();
			pages.put(pageName, rp);
		}
		return pages;
	}
	
	
}
