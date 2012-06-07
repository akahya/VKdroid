package nl.bazen.vkcrawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VKresult {
	
	private String artist;
	private String title;
	
	public VKresult(Element e, String artist, String title){
		this.artist = artist;
		this.title = title;
	}
	
	public String getArtist(){
		return this.artist;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public static VKresult fromElement(Element e){
		Elements audiotitle = e.select(".audioTitle *");
		String artist = audiotitle.get(0).text();
		String title = audiotitle.get(2).text();
		return new VKresult(e, artist, title);
	}
	
}
