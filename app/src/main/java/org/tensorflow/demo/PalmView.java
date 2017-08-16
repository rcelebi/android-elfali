package org.tensorflow.demo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.tensorflow.demo.utils.DatabaseHelper;
import org.tensorflow.demo.utils.DatabaseHelper.CizgiTuru;


public class PalmView extends Activity {
  
	protected static final String TAG = PalmView.class.getSimpleName();
	Button buttonHayat;
	Button buttonAkil;
	Button buttonKalp;
	Button buttonEvlilik;
	private int[] results=null;
	private String[] comments=null;
	private int[] secondResults=null;
	private String[] secondComments=null;
	//private String[] thumbnails=null;
	private Button buttonMainMenu;
	private Button buttonComparePartners; 
	
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commnets_org);
//		palmView=(ImageView) findViewById(R.id.palmImage);
		String url =getIntent().getStringExtra(Properties.INTENT_EXTRA_PALM_URL);
		results=getIntent().getIntArrayExtra(Properties.INTENT_EXTRA_YORUM_RESULTS);
		//secondResults =getIntent().getIntArrayExtra(Properties.INTENT_EXTRA_MATCH_RESULTS);

		if(results != null){
			comments= convertToComments(results, StartUp.dbhelper);

		}
		
		if(StartUp.dbhelper == null){
			StartUp.initDB(this);
		}

		setImageFromURL(url);
		addListenerOnButton();
 
	}

	public static String getComments(int result,CizgiTuru cizgiTuru, DatabaseHelper dbhelper){
		List<String[]> yorum = dbhelper.selectYorum(result, cizgiTuru);
		String allYorum="";
		for(String[] s:yorum){
			allYorum=allYorum+s[0];
		}
		return allYorum;
	}

	public  String[] convertToComments(int[] results,DatabaseHelper dbhelper) {
		String comment1=getComments(results[0], CizgiTuru.CIZGITURU_HAYAT, dbhelper);
		String comment2=getComments(results[1], CizgiTuru.CIZGITURU_AKIL, dbhelper);
		String comment3=getComments(results[2], CizgiTuru.CIZGITURU_KALP, dbhelper);
		String comment4=getComments(results[3], CizgiTuru.CIZGITURU_EVLILIK, dbhelper);
		return new String[]{comment1,comment2,comment3,comment4};
	}


	public void addListenerOnButton() {
 
 
		buttonHayat = (Button) findViewById(R.id.btnCommentHayat);
		buttonHayat.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				showCommentForPalm(CizgiTuru.CIZGITURU_HAYAT);

			}

		});

		buttonAkil = (Button) findViewById(R.id.btnCommentAkil);
		buttonAkil.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				showCommentForPalm(CizgiTuru.CIZGITURU_AKIL);
			}

		});
		
		buttonKalp = (Button) findViewById(R.id.btnCommentKalp);
		buttonKalp.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				showCommentForPalm(CizgiTuru.CIZGITURU_KALP);
			}

		});
		
		buttonEvlilik = (Button) findViewById(R.id.btnCommentEvlilik);
		buttonEvlilik.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				showCommentForPalm(CizgiTuru.CIZGITURU_EVLILIK);
			}

		});
		
		buttonMainMenu = (Button) findViewById(R.id.ButtonMainMenu);
		buttonMainMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Log.d(TAG, "Clicked ");
				Intent myIntent = new Intent(getBaseContext(), StartUp.class);
				startActivity(myIntent);
			}

		});
		
		buttonComparePartners = (Button) findViewById(R.id.ButtonComparePartners);
		buttonComparePartners.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				showMacthComments(CizgiTuru.CIZGITURU_EVLILIK);
			}

		});
 
	}
	
	private void showMacthComments(CizgiTuru cizgiTuru) {
		Toast.makeText(getBaseContext(), "Her hangibir kar��la�t�rma yok", Toast.LENGTH_SHORT).show();

	}

	private String getThumbnailPath(CizgiTuru cizgituru, int index) {
		String path="template/";
		if (cizgituru == CizgiTuru.CIZGITURU_HAYAT) {
			path=path+Properties.LIFE_DIR;
		}
		else if (cizgituru == CizgiTuru.CIZGITURU_AKIL) {
			path=path+Properties.HEAD_DIR;
		}
		else if (cizgituru == CizgiTuru.CIZGITURU_KALP) {
			path=path+Properties.HEART_DIR;
		}
		else if (cizgituru == CizgiTuru.CIZGITURU_EVLILIK) {
			path=path+Properties.MARRIAGE_DIR;
		}
		path=path+"/"+index+".JPG";
		return path;
	}

	private void showCommentForPalm(CizgiTuru cizgiTuru) {
		int index=cizgiTuru.getCode()-1;
		if(comments != null){
			Intent intent = new Intent(getBaseContext(), YorumView.class);
			intent.putExtra(Properties.INTENT_EXTRA_YORUM, comments[index]);
			intent.putExtra(Properties.INTENT_EXTRA_THUMBNAIL, getThumbnailPath(cizgiTuru, index));
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getBaseContext().startActivity(intent);
		}
		else{
			Toast.makeText(getBaseContext(), "Bir hata olu�tu", Toast.LENGTH_SHORT).show();
		}
		
	}

	private void setImageFromURL(String url) {
		if(url != null && url != ""){
//			palmView.setImageDrawable( Drawable.createFromPath(url));
		}
		
	}
 
}