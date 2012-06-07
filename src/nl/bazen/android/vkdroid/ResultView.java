package nl.bazen.android.vkdroid;

import nl.bazen.vkcrawler.VKresult;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultView extends LinearLayout {

	public ResultView(Context context, VKresult r) {
		super(context);
		LayoutInflater lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		lif.inflate(R.layout.result_content, this);
		TextView titleView = (TextView) this.findViewById(R.id.result_title);
		titleView.setText(r.getArtist()+" - "+r.getTitle());
	}

}
