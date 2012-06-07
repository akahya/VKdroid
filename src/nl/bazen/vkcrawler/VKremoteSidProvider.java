package nl.bazen.vkcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.util.Log;

public class VKremoteSidProvider extends VKsidProvider {
	
	public VKremoteSidProvider() {
		super(true);
	}

	private ArrayList<String> sids;
	
	public static final String ADDRESS = "http://freetexthost.com/jn1ixwtleg";
	
	@Override
	protected String onInit() {
		this.sids = new ArrayList<String>();
		final VKremoteSidProvider p = this;
		Runnable retrieveSids = new Runnable() {
			public void run() {
				try {
					Document doc = Jsoup.connect(ADDRESS).get();
					Element contentInner = doc.select("#contentsinner").first();
					String textContent = contentInner.ownText();
					int addCount = 0;
					for(String sid : textContent.split("\\s+")){
						p.addSid(sid);
						addCount++;
					}
					Log.d("vk","remote sid provider: found "+addCount);
					p.onReady();
				} catch (IOException e) {
					p.onError("IOException occurred");
				}
			}
		};
		new Thread(retrieveSids).start();
		return "Retrieving SID...";
	}
	
	private void addSid(String sid){
		if(!this.sids.contains(sid)) this.sids.add(sid);
	}

	@Override
	protected VKsid getSID() {
		int index = (new Random()).nextInt(this.sids.size());
		return new VKsid(this.sids.remove(index));
	}

}
