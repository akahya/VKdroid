package nl.bazen.utils;

import java.util.ArrayList;

public class StatusObject {
	
	private ArrayList<StatusListener> listeners = new ArrayList<StatusListener>();
	
	private String statusMessage = "";
	
	private int statusCode = 0;
	
	public void addStatusListener(StatusListener l){
		this.listeners.add(l);
	}
	
	public void removeStatusListener(StatusListener l){
		this.listeners.remove(l);
	}
	
	public String getStatusMessage(){
		return this.statusMessage;
	}
	
	public int getStatusCode(){
		return this.statusCode;
	}
	
	protected void change(int statusCode, String statusMessage){
		boolean codeHasChanged = this.statusCode!=statusCode;
		boolean messageHasChanged = this.statusMessage!=statusMessage;
		if(codeHasChanged || messageHasChanged){
			int oldCode = this.statusCode;
			String oldMessage = this.statusMessage;
			this.statusCode = statusCode;
			this.statusMessage = statusMessage;
			for(StatusListener l : this.listeners)
				l.onStatusChange(this, this.statusCode, this.statusMessage, oldCode, oldMessage, codeHasChanged, messageHasChanged);
		}
	}
	
	protected void changeCode(int statusCode){
		this.change(statusCode, this.statusMessage);
	}
	
}
