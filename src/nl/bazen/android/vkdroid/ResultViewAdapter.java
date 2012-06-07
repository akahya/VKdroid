package nl.bazen.android.vkdroid;

import java.util.ArrayList;

import nl.bazen.vkcrawler.VKresult;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ResultViewAdapter extends BaseAdapter {

	private Context ctx;
	private ArrayList<VKresult> results;
	
	public ResultViewAdapter(Context ctx){
		this.ctx = ctx;
		this.results = new ArrayList<VKresult>();
	}
	
	public void clear() {
		this.results.clear();
	}
	
	public void addResult(VKresult r){
		Log.d("vk", "adding result...");
		this.results.add(r);
		this.notifyDataSetChanged();
	}
	
	public int getCount() {
		return this.results.size();
	}

	public Object getItem(int position) {
		return this.results.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return new ResultView(this.ctx, (VKresult) this.getItem(position));
	}

}
