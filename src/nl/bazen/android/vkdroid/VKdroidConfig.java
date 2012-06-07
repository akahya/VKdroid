package nl.bazen.android.vkdroid;

import java.util.HashMap;

import nl.bazen.vkcrawler.VKloginprivate;

public class VKdroidConfig {

	private HashMap<String, String> values;
	
	public static final String K_USERNAME = "username";
	public static final String K_PASSWORD = "password";
	
	public VKdroidConfig(){
		this.values = new HashMap<String, String>();
		this.setValue(K_USERNAME, VKloginprivate.USERNAME);
		this.setValue(K_PASSWORD, VKloginprivate.PASSWORD);
	}
	
	public void setValue(String key, String value){
		this.values.put(key, value);
	}
	
	public String getValue(String key){
		return this.values.get(key);
	}
	
}
