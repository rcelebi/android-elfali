package org.tensorflow.demo;



import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


public class YorumView extends Activity {
	protected static final String TAG = YorumView.class.getSimpleName();                     
	private TextView textview;
	public String comments;
	public String thumbnailPath;
	private ImageView thumbnail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scrollview_yorum);
		textview= (TextView) findViewById(R.id.textLine);
		thumbnail = (ImageView)findViewById(R.id.thumbnailImage);
		Intent intent = getIntent();
		comments=intent.getStringExtra(Properties.INTENT_EXTRA_YORUM);
		thumbnailPath=intent.getStringExtra(Properties.INTENT_EXTRA_THUMBNAIL);
		setText(comments);
		//setThumbnailPath(thumbnailPath);
	}
	
	private void setThumbnailPath(String thumbnail_path) {
		Log.d(TAG, "Thumbnail path :"+thumbnail_path);
		try {
			Drawable d = Drawable.createFromStream(getAssets().open(thumbnail_path), null);
			thumbnail.setImageDrawable(d);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//			thumbnail.setLayoutParams(lp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setText(String text){
		if(text!=null){
			textview.setText(text);
		}
	}

}
