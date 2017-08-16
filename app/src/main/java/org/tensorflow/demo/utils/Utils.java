package org.tensorflow.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.tensorflow.demo.BuildConfig;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Utils {
	
	private static final String TAG = Utils.class.getSimpleName();
	public static boolean debug= true;

	
	public static Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
	        int quality) {
	      ByteArrayOutputStream os = new ByteArrayOutputStream();
	      src.compress(format, quality, os);

	      byte[] array = os.toByteArray();
	      return BitmapFactory.decodeByteArray(array, 0, array.length);
	    }

	public static void copyFromAssetToSdcard(AssetManager assetManager,String from, String to) {
	        FileOutputStream outputStream= null;
	        InputStream instream = null;
	    	
	    try{
	    	
	    	outputStream = new FileOutputStream(new File(to));
	    	instream = assetManager.open(from);
	    	byte[] bytes = new byte[255];
			while((instream.read(bytes)) != -1){
				outputStream.write(bytes);
			}
	    }
	    catch(FileNotFoundException ex){
	    	Log.e(TAG,"FileNotFoundException "+ ex.getMessage());
	    	Log.w(TAG, ex.fillInStackTrace());
	    } catch (IOException ex) {
	    	Log.e(TAG, "IOException "+ ex.getMessage());
		}
	    finally{
	    	if(outputStream!= null){
	    		try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	if(instream!= null){
	    		try {
					instream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
			
		}


	public static void copyImageDataTooSdcard(Bitmap bmp, String to) {
		FileOutputStream outputStream = null;
		try {

			outputStream = new FileOutputStream(new File(to));
		    bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
		} catch (FileNotFoundException ex) {
			Log.e(TAG, "FileNotFoundException " + ex.getMessage());
			Log.w(TAG, ex.fillInStackTrace());
		} finally {
			if (outputStream != null) {
				try {
				    outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	
	public static void copyDataTooSdcard(byte[] data, String to) {
		FileOutputStream outputStream = null;
		try {

			outputStream = new FileOutputStream(new File(to));
			outputStream.write(data);
		} catch (FileNotFoundException ex) {
			Log.e(TAG, "FileNotFoundException " + ex.getMessage());
			Log.w(TAG, ex.fillInStackTrace());
		} catch (IOException ex) {
			Log.e(TAG, "IOException " + ex.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	
	
	@SuppressWarnings("unused")
	private Bitmap yuv2rgba(final byte[] data, final int width, final int height) {
		 int frameSize = width *height;
		 if(BuildConfig.DEBUG){
	        Log.d(TAG, "Frame size :"+frameSize);
		 }

	        int[] rgba = new int[frameSize];
	    
	        
	      for (int i = 0; i < height; i++){
		      for (int j = 0; j < width; j++) {
		          int y = (0xff & ((int) data[i * width + j]));
		          int u = (0xff & ((int) data[frameSize + (i >> 1) * width + (j & ~1) + 0]));
		          int v = (0xff & ((int) data[frameSize + (i >> 1) * width + (j & ~1) + 1]));
		          y = y < 16 ? 16 : y;
	
		          int r = Math.round(1.164f * (y - 16) + 1.596f * (v - 128));
		          int g = Math.round(1.164f * (y - 16) - 0.813f * (v - 128) - 0.391f * (u - 128));
		          int b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128));
	
		          r = r < 0 ? 0 : (r > 255 ? 255 : r);
		          g = g < 0 ? 0 : (g > 255 ? 255 : g);
		          b = b < 0 ? 0 : (b > 255 ? 255 : b);
	
		          rgba[i * width + j] = 0xff000000 + (b << 16) + (g << 8) + r;
		      }
	      }
	      Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	      bmp.setPixels(rgba, 0/* offset */, width /* stride */, 0, 0, width, height);
	      return bmp;
	}
	
	// decode Y, U, and V values on the YUV 420 buffer described as YCbCr_422_SP
	// by Android
	// David Manpearl 081201
	public Bitmap decodeYUV(byte[] fg, int width, int height)
			throws NullPointerException, IllegalArgumentException {
		int sz = width * height;

		if (fg == null)
			throw new NullPointerException("buffer 'fg' is null");
		if (fg.length < sz)
			throw new IllegalArgumentException("buffer fg size " + fg.length
					+ " < minimum " + sz * 3 / 2);
		int[] out = new int[sz];
		int i, j;
		int Y, Cr = 0, Cb = 0;
		for (j = 0; j < height; j++) {
			int pixPtr = j * width;
			final int jDiv2 = j >> 1;
			for (i = 0; i < width; i++) {
				Y = fg[pixPtr];
				if (Y < 0)
					Y += 255;
				if ((i & 0x1) != 1) {
					final int cOff = sz + jDiv2 * width + (i >> 1) * 2;
					Cb = fg[cOff];
					if (Cb < 0)
						Cb += 127;
					else
						Cb -= 128;
					Cr = fg[cOff + 1];
					if (Cr < 0)
						Cr += 127;
					else
						Cr -= 128;
				}
				int R = Y + Cr + (Cr >> 2) + (Cr >> 3) + (Cr >> 5);
				if (R < 0)
					R = 0;
				else if (R > 255)
					R = 255;
				int G = Y - (Cb >> 2) + (Cb >> 4) + (Cb >> 5) - (Cr >> 1)
						+ (Cr >> 3) + (Cr >> 4) + (Cr >> 5);
				if (G < 0)
					G = 0;
				else if (G > 255)
					G = 255;
				int B = Y + Cb + (Cb >> 1) + (Cb >> 2) + (Cb >> 6);
				if (B < 0)
					B = 0;
				else if (B > 255)
					B = 255;
				out[pixPtr++] = 0xff000000 + (B << 16) + (G << 8) + R;
			}
		}
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    bmp.setPixels(out, 0/* offset */, width /* stride */, 0, 0, width, height);
	     return bmp;

	}
	


	int RGB565_to_ARGB8(short rgb565)
	{
	    int a = 0xff;
	    int r = (rgb565 & 0xf800) >> 11;
	    int g = (rgb565 & 0x07e0) >> 5;
	    int b = (rgb565 & 0x001f);
	 
	    r  = r << 3;
	    g  = g << 2;
	    b  = b << 3;
	 
	    return (a << 24) | (r << 16) | (g << 8) | (b);
	}



}
