package nl.bazen.vkcrawler;

import nl.bazen.utils.StatusObject;

public abstract class VKsidProvider extends StatusObject {
	
	public static final int STATUS_BUSY = 0;
	public static final int STATUS_READY = 1;
	public static final int STATUS_ERROR = 2;
	
	private boolean ready;
	
	private boolean initTakesAWhile;
	private String initMessage;
	
	public VKsidProvider(boolean initTakesAWhile){
		this.ready = false;
		this.initTakesAWhile = initTakesAWhile;
		this.initMessage = this.onInit();
	}
	
	protected void onError(String errorMsg){
		this.change(STATUS_ERROR, errorMsg);
	}
	
	protected void onReady(){
		this.ready = true;
		this.changeCode(STATUS_READY);
	}
	
	public VKsid get(){
		if(this.ready)
			return this.getSID();
		else
			return null;
	}
	
	public boolean initTakesAWhile(){
		return this.initTakesAWhile;
	}
	
	public String getInitMessage(){
		return this.initMessage;
	}
	
	protected abstract VKsid getSID();
	
	protected abstract String onInit();
	
}
