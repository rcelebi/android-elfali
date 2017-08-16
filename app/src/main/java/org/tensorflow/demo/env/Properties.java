package org.tensorflow.demo.env;

import android.os.Environment;

public class Properties {
	public static final String FOLDER_TO_BE_SAVED =Environment.getExternalStorageDirectory()+ "/elfali/";
	public static final String FILE_EXT = ".jpg";
	public static final String HAND_PALM_IMAGE_EXT = "_palm";
	public static final String INTENT_EXTRA_YORUM = "yorum";
	public static final String INTENT_EXTRA_YORUM_RESULTS = "result";
	public static final String INTENT_EXTRA_PALM_URL = "url";
	public static final String INTENT_EXTRA_YORUM_THUMBNAILS = "thumbnails";
	public static final String INTENT_EXTRA_THUMBNAIL="thumbnail";
	/**
	 * 
	 */
	public static final String REF_SEQ_DIR="sequences/";
	public static final String LIFE_DIR = "life";
	public static final String HEAD_DIR ="head";
	public static final String HEART_DIR = "heart";
	public static final String MARRIAGE_DIR = "marriage";
	public static final String INTENT_EXTRA_MATCH_RESULTS = "match";
	public static final int LIFE_LINE_NUM = 11;
	public static final int HEAD_LINE_NUM = 10;
	public static final int HEART_LINE_NUM = 7;
	public static final int MARRIAGE_LINE_NUM = 5;
	


}
