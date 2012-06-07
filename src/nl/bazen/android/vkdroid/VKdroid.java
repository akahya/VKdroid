package nl.bazen.android.vkdroid;

import nl.bazen.vkcrawler.VKcrawler;

public class VKdroid {
	
	public static VKdroid INSTANCE;
	
	private VKdroidActivity activity;
	private VKdroidConfig config;
	private VKcrawler crawler;
	
	public VKdroid(VKdroidActivity activity){
		INSTANCE = this;
		this.activity = activity;
		this.config = new VKdroidConfig();
		this.crawler = new VKcrawler();
	}
	
	public VKdroidActivity getActivity(){
		return this.activity;
	}
	
	public VKcrawler getCrawler(){
		return this.crawler;
	}
	
	public VKdroidConfig getConfig(){
		return this.config;
	}
	
}
