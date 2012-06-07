package nl.bazen.utils;

public interface StatusListener{
	
	public void onStatusChange(StatusObject o, int code, String message, int oldCode, String oldMessage, boolean codeChanged, boolean messageChanged);
	
}
