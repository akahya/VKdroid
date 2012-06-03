package nl.bazen.android.vkdroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;


public class VKcrawler {
	
	public static enum Status{
		OFFLINE, BUSY, READY
	}
	
	private Status status;
	private String sessionId;
	
	public static final String LOGINURL = "http://login.vk.com/";
	public static final String COOKIE = "remixchk=5; l=5337115; p=7378d9d80df7781b3849bec8db9bf5cb61a4";
	
	public VKcrawler(){
		this.status = Status.OFFLINE;
	}
	
	public void login(){
		if(this.status == Status.OFFLINE){
			this.status = Status.BUSY;
			
			Runnable runLogin = new Runnable() {
				public void run() {
					try {
						URL loginURL = new URL(VKcrawler.LOGINURL);
						HttpURLConnection conn = (HttpURLConnection) loginURL.openConnection();
						conn.setUseCaches(false);
						conn.setRequestMethod("GET");
						conn.addRequestProperty("Cookie", VKcrawler.COOKIE);
						conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5");
						conn.connect();
						Log.d("vk", String.valueOf(conn.getResponseCode()));
						String line;
						BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						while((line = r.readLine())!=null){
							Log.d("vk", line);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NullPointerException e){
						Log.d("vk", "NULL");
					}
				}
			};
			
			new Thread(runLogin).start();
			
		}
	}
	
}