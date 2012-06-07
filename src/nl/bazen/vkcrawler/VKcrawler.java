package nl.bazen.vkcrawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;
import android.widget.ListView;
import nl.bazen.android.vkdroid.VKdroid;
import nl.bazen.android.vkdroid.VKdroidActivity;
import nl.bazen.android.vkdroid.VKdroidConfig;
import nl.bazen.utils.StatusListener;
import nl.bazen.utils.StatusObject;

public class VKcrawler extends StatusObject implements StatusListener {
	
	public final static int STATUS_OK = 0;
	public final static int STATUS_BUSY = 1;

	private VKsid sid;

	private VKsidProvider p;
	
	private ArrayList<VKresult> results;
	private VKdroidActivity vkda;
	
	public static final String URL_SEARCH = "http://vkontakte.ru/gsearch.php?section=audio&q=%s&offset=0";

	public VKcrawler() {
		this.vkda = VKdroid.INSTANCE.getActivity();
		this.p = new VKloginSidProvider(VKdroid.INSTANCE.getConfig().getValue(VKdroidConfig.K_USERNAME), VKdroid.INSTANCE.getConfig().getValue(VKdroidConfig.K_PASSWORD));
		this.p.addStatusListener(this);
	}

	public void onStatusChange(StatusObject o, int code, String message,
			int oldCode, String oldMessage, boolean codeChanged,
			boolean messageChanged) {
		if(o == this.p && oldCode == VKsidProvider.STATUS_BUSY && code == VKsidProvider.STATUS_READY){
			this.sid = this.p.get();
			Log.d("vk", "crawler: using sid: "+this.sid.getSid());
		}
	}
	
	public VKsidProvider getSidProvider(){
		return this.p;
	}
	
	public void search(final String query){
		this.change(STATUS_BUSY, "Hold on...");
		final VKcrawler c = this;
		Runnable doSearch = new Runnable() {
			public void run() {
				try {
					String urlsafe_query = URLEncoder.encode(query, "UTF-8");
					String url = String.format(URL_SEARCH, urlsafe_query);
					Log.d("vk", "using url: "+url);
					Connection scon = Jsoup.connect(url);
					scon.cookie("remixsid", c.sid.getSid());
					Document sdoc = scon.post();
					Log.d("vk", sdoc.html());
					Elements audioRows = sdoc.select(".audioRow");
					
					c.results = new ArrayList<VKresult>();
					
					for(Element e : audioRows){
						Log.d("vk", "result found");
						VKresult result = VKresult.fromElement(e);
						c.results.add(result);
						c.vkda.getResultViewAdapter().addResult(result);
					}
					c.change(STATUS_OK, "");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(doSearch).start();
	}

}