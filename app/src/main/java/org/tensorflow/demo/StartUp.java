package org.tensorflow.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;



import org.tensorflow.demo.utils.DatabaseHelper;
import org.tensorflow.demo.utils.InitializationTask;

import java.io.File;

/**
 * Created by prometheus on 8/12/17.
 */

public class StartUp extends Activity{

    private static final String TAG = StartUp.class.getName();

    public static DatabaseHelper dbhelper;
    private String root;
    private static boolean initiazed = false;

    public static  void initDB(Context context) {
        dbhelper = new DatabaseHelper(context);
        dbhelper.createDataBase();
    }

    public void initiliaze(android.content.Context context) {

        root = Environment.getExternalStorageDirectory().toString();
        new File(Properties.FOLDER_TO_BE_SAVED).mkdirs();
        // new File("/data/anr/").mkdirs()

        InitializationTask initTask = new InitializationTask();
        initTask.start(context);
        initTask.cancel(true);
        // dbhelper = new DatabaseHelper(context, initTask);

        //SQLiteDatabase.loadLibs(context); // first init the db libraries with
        // the context
        initDB(context);

        initTask.onPostExecute("");
        if (!dbhelper.checkDataBase()) {
            // show a progress dialog
            // initialize the database
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " initTask.execute");
            }
            initTask.execute();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactions);
       if (!initiazed) {
            initiliaze(this);
            initiazed = true;
        }

        Button b = (Button) findViewById(R.id.buttonTakePhoto);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(),
                        CameraActivity.class);
                startActivity(myIntent);

            }
        });

        Button b2 = (Button) findViewById(R.id.ButtonTakeAutomatic);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(),
                        CameraActivity.class);
                startActivity(myIntent);

            }
        });

        Button b3 = (Button) findViewById(R.id.ButtonListItems);
        b3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(),
                        RecordListView.class);
                startActivity(myIntent);

            }
        });

        Button b4 = (Button) findViewById(R.id.buttonHelp);
        b4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), HelpView.class);
                startActivity(myIntent);

            }
        });

    }
}
