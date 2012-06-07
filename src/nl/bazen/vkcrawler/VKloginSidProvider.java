package nl.bazen.vkcrawler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.util.Log;

public class VKloginSidProvider extends VKsidProvider {
	
	private String username, password, sid;
	
	public VKloginSidProvider(String username, String password) {
		super(true);
		this.username = username;
		this.password = password;
	}

	@Override
	protected VKsid getSID() {
		return new VKsid(this.sid);
	}

	@Override
	protected String onInit() {
		
		final VKloginSidProvider p = this;
		
		Runnable runLogin = new Runnable() {
			
			public void run() {
				
				try {	
					
					Connection c = Jsoup.connect("http://login.vk.com/?act=login");
					
					c.header("Content-Type", "application/x-www-form-urlencoded");
					
					c.data("act", "login",
							"q", "1",
							"al_frame", "1",
							"from_host", "vk.com",
							"from_protocol", "http",
							"email", p.username,
							"pass", p.password);
					
					Response r = c.execute();
					
					Document d = c.get();
					
					if(r.hasCookie("remixsid")){
						p.sid = r.cookie("remixsid");
						p.onReady();
					}else{
						p.onError("Could not retrieve SID. Try again later, or wait for update.");
					}
					
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		new Thread(runLogin).start();
		
		return "Logging you in...";
	}

}
