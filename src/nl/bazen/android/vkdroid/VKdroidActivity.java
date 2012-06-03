package nl.bazen.android.vkdroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class VKdroidActivity extends Activity {
    /** Called when the activity is first created. */
    
	private VKcrawler vk;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        EditText et = (EditText) this.findViewById(R.id.editText1);
        et.setOnEditorActionListener(new OnEditorActionListener() {
			
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_SEARCH){
					Log.d("vk", "SEARCH!");
				}
				return false;
			}
		});
        
        //ProgressDialog dialog = ProgressDialog.show(this, "", "Connecting to VK.com...", true);
        
        vk = new VKcrawler();
        vk.login();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	MenuInflater inflater = this.getMenuInflater();
    	inflater.inflate(R.menu.general_menu, menu);
    	
    	return super.onCreateOptionsMenu(menu);
    	
    }
}