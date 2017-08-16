package org.tensorflow.demo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import org.tensorflow.demo.BuildConfig;
import org.tensorflow.demo.Record;


public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DBNAME = "elfaliv2.2.db";
	private static final String YORUM_TABLE = "ELFALI_YORUM";
	private static final String YORUM_ID = "YORUM_ID";
	private static final String YORUM_ACIKLAMA = "YORUM_ACIKLAMA";
	private static final String YORUM_CIZGI_TURU = "ELCIZGISI_TURU";

	private static final String KARSILASMA_TABLE = "ELFALI_KARSILASMA";
	private static final String KARSILASMA_ID = "KARSILASMA_ID";
	private static final String KARSILASMA_ACIKLAMA = "KARSILASMA_ACIKLAMA";
	private static final String KARSILASMA_CIZGI_TURU = "ELCIZGISI_TURU";

	private static final String INSERT = "insert into " + YORUM_TABLE
			+ " values (?)";
	private static final int DATABASE_VERSION = 33;
	private static final String TAG = DatabaseHelper.class.getName();
//	private static final int MAX_NUMBER_OF_LINE = 99;
	private static final String RECORD_ID = "KAYIT_ID";
	private static final String RECORD_DATE = "KAYIT_DATE";
	private static final String RECORD_URL = "KAYIT_FOTO_URL";
	private static final String RECORD_TABLE = "ELFALI_KAYIT";
	private static final String RECORD_RESULT2 = "KAYIT_RESULT2";
	private static final String RECORD_RESULT1 = "KAYIT_RESULT1";
	private static final String RECORD_RESULT3 = "KAYIT_RESULT3";
	private static final String RECORD_RESULT4 = "KAYIT_RESULT4";
	private static final String SECRET_KEY = "elfali_remzi_celebi";

	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private final Context myContext;

	// private InitializationTask task;

	public enum CizgiTuru {
		CIZGITURU_HAYAT(1), CIZGITURU_AKIL(2), CIZGITURU_KALP(3), CIZGITURU_EVLILIK(
				4);

		int code;

		private CizgiTuru(int c) {
			code = c;
		}

		public int getCode() {
			return code;
		}
	}

	public DatabaseHelper(Context context) {
		super(context, DBNAME, null, DATABASE_VERSION);
		this.myContext = context;
		// this.task = task;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		if (org.tensorflow.demo.BuildConfig.DEBUG) {
			Log.d(TAG, "onCreate myContext:" + myContext);
		}
	}

	/**
	 * Copies the database file at the specified location over the current
	 * internal application database.
	 * */
	public boolean importDatabase(String string) throws IOException {

		// Close the SQLiteOpenHelper so it will commit the created empty
		// database to internal storage.
		File databaseFile = myContext.getDatabasePath(DBNAME);
		databaseFile.mkdirs();
		databaseFile.delete();
		Utils.copyFromAssetToSdcard(myContext.getAssets(), string,
				databaseFile.getAbsolutePath());

        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, null);

        if(database != null){
            Log.d(TAG, "database works!!");
            database.close();
        }
		return false;
	}

    public static String singleValueFromQuery(SQLiteDatabase database, String query){
        Cursor cursor = database.rawQuery(query, new String[]{});
        String value = "";
        if(cursor != null){
            cursor.moveToFirst();
            value = cursor.getString(0);
            cursor.close();
        }
        return value;
    }

	// exporting database
	public void exportDB(String backupDBPath) {
		// TODO Auto-generated method stub

		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				data.mkdirs();
				File currentDB = myContext.getDatabasePath(DBNAME);
				File backupDB = new File(backupDBPath);

				FileUtils.copyFile(new FileInputStream(currentDB),
						new FileOutputStream(backupDB));

			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

	}

	public boolean checkDataBase() {
		if (BuildConfig.DEBUG) {
			Log.d(TAG, "checkDataBase()");
		}
		List<String> list = Arrays.asList(myContext.databaseList());
		for (String name : list) {
			if (BuildConfig.DEBUG) {
				Log.v(TAG, "checkDataBase(): DB " + name);
			}
		}
		return Arrays.asList(myContext.databaseList()).contains(DBNAME);
	}

	public void createDataBase() {

		boolean dbExist = checkDataBase();

		if (!dbExist) {
			try {
				if (BuildConfig.DEBUG) {
					Log.d(TAG, "Initilization of Database...");
				}
				//importDatabase(DBNAME);
                initDatabase();
				Log.v(TAG, "Initilization of Database... DONE");
			} catch (IOException e) {

				throw new Error("Error initiliazing database");

			}
		}

	}

	
	public void initDatabase() throws IOException {
		if (BuildConfig.DEBUG) {
			Log.v(TAG, "initDatabase()");
		}
		BufferedReader br = null;
		SQLiteDatabase database = null;
		// task.initProgressBar(MAX_NUMBER_OF_LINE);
		try {

            database = this.getWritableDatabase();
            database.beginTransaction();
			br = new BufferedReader(new InputStreamReader(myContext.getAssets()
					.open(DBNAME)));
			String line;
//			int lineNumber = 0;
			while ((line = br.readLine()) != null) {
				if (BuildConfig.DEBUG) {
					Log.d(TAG, "Executing line : " + line);
				}
				database.execSQL(line);
				if (BuildConfig.DEBUG) {
					Log.d(TAG, "Executed line : " + line);
				}
				// if (task != null)
				// task.incrementProgressBar(++lineNumber);
			}
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getMessage());
		} finally {
			try {
				if (database != null) {
					// end the transaction
					database.endTransaction();
					database.close();
				}
			} catch (Exception e) {
				Log.v(TAG, e.getMessage());
			}

			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				Log.w(TAG, e.getMessage());
			}
		}

	}

	public boolean insertRecord(String recordURL, int result1, int result2,
			int result3, int result4) {
        final String root =
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "elfali";
		this.db = this.getReadableDatabase();
		//this.db = this.getReadableDatabase();
        recordURL = root+ File.separator +recordURL;

        ContentValues cv = new ContentValues();
		cv.put(RECORD_URL, recordURL);
		cv.put(RECORD_RESULT1, result1);
		cv.put(RECORD_RESULT2, result2);
		cv.put(RECORD_RESULT3, result3);
		cv.put(RECORD_RESULT4, result4);
		db.insert(RECORD_TABLE, null, cv);

		if (this.db != null)
			this.db.close();

		return true;
	}

	public boolean insertA(String name) {
		this.db = this.getReadableDatabase();
		//this.db = this.getReadableDatabase();
		this.insertStmt = this.db.compileStatement(INSERT);
		insert(name);
		// ContentValues cv=new ContentValues();
		// cv.put("id", 1);
		// cv.put(taxName, "Taksi A");
		// db.insert(taxiTable, colDeptID, cv);

		// cv.put(colDeptID, 2);
		// cv.put(colDeptName, "IT");
		// db.insert(deptTable, colDeptID, cv
		return true;
	}

	public long insert(String name) {
		try {
			this.insertStmt.bindString(1, name);
			return this.insertStmt.executeInsert();
		} catch (Exception e) {
			Log.e(TAG, "Error in insertion into db");
		}
		return 0;

	}

	public List<String[]> selectMatching(int karsilasmaID, CizgiTuru cizgiTuru) {

		List<String[]> list = new ArrayList<String[]>();
		this.db = this.getReadableDatabase();
		//this.db = this.getReadableDatabase();
		Cursor cursor = this.db.rawQuery("SELECT " + KARSILASMA_TABLE + "."
				+ KARSILASMA_ACIKLAMA + " FROM " + KARSILASMA_TABLE + " WHERE "
				+ KARSILASMA_ID + " = " + karsilasmaID + " and "
				+ KARSILASMA_CIZGI_TURU + " = " + cizgiTuru.code, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(new String[] { cursor.getString(0) });
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		if (this.db != null)
			this.db.close();
		return list;
	}

	public List<String[]> selectYorum(int yorumID, CizgiTuru cizgiTuru) {
		yorumID = cizgiTuru.code * 100 + yorumID;

		List<String[]> list = new ArrayList<String[]>();
		this.db = this.getReadableDatabase();
		//this.db = this.getReadableDatabase();
		Cursor cursor = this.db.rawQuery("SELECT " + YORUM_TABLE + "."
				+ YORUM_ACIKLAMA + " FROM " + YORUM_TABLE + " WHERE "
				+ YORUM_ID + " = " + yorumID + " and " + YORUM_CIZGI_TURU
				+ " = " + cizgiTuru.code, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(new String[] { cursor.getString(0) });
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		if (this.db != null)
			this.db.close();
		return list;
	}

	public List<String[]> selectAllYorum() {
		List<String[]> list = new ArrayList<String[]>();
		this.db = this.getReadableDatabase();
		//this.db = this.getReadableDatabase();
		Cursor cursor = this.db.query(YORUM_TABLE, new String[] {
				YORUM_ACIKLAMA, YORUM_CIZGI_TURU }, null, null, null, null,
				YORUM_ACIKLAMA + " desc");
		if (cursor.moveToFirst()) {
			do {
				list.add(new String[] { cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3) });
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		if (this.db != null)
			this.db.close();
		return list;
	}

	public List<Record> selectAllRecords() {
		List<String[]> list = new ArrayList<String[]>();
		this.db = this.getReadableDatabase();
		//this.db = this.getReadableDatabase();
		Cursor cursor = this.db.query(RECORD_TABLE, new String[] { RECORD_ID,
				RECORD_URL, RECORD_RESULT1, RECORD_RESULT2, RECORD_RESULT3,
				RECORD_RESULT4, RECORD_DATE }, null, null, null, null,
				RECORD_URL + " desc");
		if (cursor.moveToFirst()) {
			do {
				list.add(new String[] { cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4),
						cursor.getString(5), cursor.getString(6) });
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		if (this.db != null)
			this.db.close();
		return convertToRecordPOJO(list);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private List<Record> convertToRecordPOJO(List<String[]> list) {
		List<Record> pojoList = new ArrayList<Record>();

		for (String[] row : list) {
			if (BuildConfig.DEBUG) {
				Log.d(TAG, "Row : " + row[0] + " " + row[1] + " " + row[2]
						+ " " + row[3] + " " + row[4] + " " + row[5] + " "
						+ row[6]);
			}
			Record record = new Record();
			record.setId(row[0]);
			record.setFotoURL(row[1]);
			record.setResult1(Integer.valueOf(row[2]));
			record.setResult2(Integer.valueOf(row[3]));
			record.setResult3(Integer.valueOf(row[4]));
			record.setResult4(Integer.valueOf(row[5]));
			java.util.Date result;
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String datetimeString = row[6];
			try {
				result = formatter.parse(datetimeString);
				record.setDate(new java.sql.Date(result.getTime()));
			} catch (ParseException e) {
				if (BuildConfig.DEBUG) {
					Log.w(TAG, e.getLocalizedMessage());
				}
				record.setDate(new java.sql.Date(Calendar.getInstance()
						.getTime().getTime()));
			}

			pojoList.add(record);

		}
		return pojoList;
	}

}
