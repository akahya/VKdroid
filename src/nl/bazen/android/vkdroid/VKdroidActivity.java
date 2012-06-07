package nl.bazen.android.vkdroid;

import nl.bazen.utils.StatusListener;
import nl.bazen.utils.StatusObject;
import nl.bazen.vkcrawler.VKcrawler;
import nl.bazen.vkcrawler.VKresult;
import nl.bazen.vkcrawler.VKsidProvider;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class VKdroidActivity extends Activity implements StatusListener {
    /** Called when the activity is first created. */
    
	private VKcrawler vk;
	private EditText searchBox;
	private ProgressDialog dialog;
	private ListView resultview;
	private ResultViewAdapter resultviewadapter;
	private boolean busy = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.searchBox = (EditText) this.findViewById(R.id.editText1);
        this.resultview = (ListView) this.findViewById(R.id.listView1);
        this.resultviewadapter = new ResultViewAdapter(this);
        this.resultview.setAdapter(resultviewadapter);
        this.resultviewadapter.addResult(new VKresult(null, "Froxic", "The Arising"));
        
        this.vk = new VKcrawler(this);
        
        if(vk.getSidProvider().initTakesAWhile()){
        	this.dialog = ProgressDialog.show(this, "", vk.getSidProvider().getInitMessage(), true);
        	vk.getSidProvider().addStatusListener(this);
        	this.busy = true;
        }
        vk.addStatusListener(this);
        
        final VKcrawler vk = this.vk;
        this.searchBox.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(!busy){
					//if(actionId == EditorInfo.IME_ACTION_SEARCH){   //this cannot be tested on an emulator.
						vk.search(v.getText().toString());
					//}
				}
				return false;
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	MenuInflater inflater = this.getMenuInflater();
    	inflater.inflate(R.menu.general_menu, menu);
    	
    	return super.onCreateOptionsMenu(menu);
    	
    }

	public void onStatusChange(StatusObject o, int code, String message,
			int oldCode, String oldMessage, boolean codeChanged,
			boolean messageChanged) {
		if(o == this.vk.getSidProvider() && oldCode == VKsidProvider.STATUS_BUSY && code == VKsidProvider.STATUS_READY && this.dialog != null){
			this.dialog.dismiss();
			this.dialog = null;
			this.busy = false;
		}
		if(o == this.vk){
			if(this.dialog !=null)
				this.dialog.dismiss();
			if(code == VKcrawler.STATUS_BUSY){
				this.dialog = ProgressDialog.show(this, "", message, true);
				this.busy = true;
			}
			else{
				this.dialog = null;
				this.busy = false;
			}
		}
	}
	
	public ListView getResultView(){
		return this.resultview;
	}
	
	public ResultViewAdapter getResultViewAdapter(){
		return this.resultviewadapter;
	}
	
}