package org.tensorflow.demo.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class InitializationTask  extends AsyncTask<String, String, String> {
	
		private ProgressDialog progressDialog;
		


         /**  
          * The log tag.
          */
         private final String TAG = InitializationTask.class.getSimpleName();

         /**
          * {@inheritDoc}
          */
         @Override
         protected String doInBackground(String... arg0) {
                 Log.v(TAG, "doInBackground()");
                //new DatabaseHelper(MainScreen.this, this);
                 return null;
         }

         /**
          * Initialized the progress bar with max value.
          * @param maxValue the max value
          */
         public void initProgressBar(int maxValue) {
                 Log.v(TAG, "initProgressBar ("+ maxValue+")");
                 this.progressDialog.setIndeterminate(false);
                 this.progressDialog.setMax(maxValue);
         }

         /**
          * Set the progress bar progress to the new progress.
          * @param value the new progress
          */
         public void incrementProgressBar(int value) {
                 this.progressDialog.setProgress(value);
         }

         /**
          * {@inheritDoc}
          */
         @Override
		public void onPostExecute(String result) {
                 Log.v(TAG, "onPostExecute()" +result);
                 super.onPostExecute(result);
                 this.progressDialog.dismiss();
         }

		public void start(Context context) {
			this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.setIndeterminate(true);
     
            //this.progressDialog.setTitle(getString(R.string.init_dialog_title));
//            this.progressDialog.setMessage(getString(R.string.init_dialog_message));
            this.progressDialog.show();
			
		}
         
 }

