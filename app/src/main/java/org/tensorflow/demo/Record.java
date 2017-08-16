package org.tensorflow.demo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.location.Location;

public class Record {

	private String id;
	private String fotoURL;
	private Date date;
	private int result1;
	private int result2;
	private int result3;
	private int result4;

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setFotoURL(String fotoURL) {
		this.fotoURL = fotoURL;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setResult1(int result1) {
		this.result1 = result1;
	}
	
	public void setResult2(int result2) {
		this.result2 = result2;
	}
	
	public void setResult3(int result3) {
		this.result3 = result3;
	}
	public void setResult4(int result4) {
		this.result4 = result4;
	}
	
	public Date getDate() {
		return date;
	}
	public String getFotoURL() {
		return fotoURL;
	}
	public String getId() {
		return id;
	}
	public int getResult1() {
		return result1;
	}
	public int getResult2() {
		return result2;
	}
	public int getResult3() {
		return result3;
	}
	public int getResult4() {
		return result4;
	}
	
	public static List<Record> convertPOJOList(List<String[]> list, Location location) {
		List<Record> pojoList = new ArrayList<Record>();

		for (String[] row : list) {
			Record tp = new Record();
			tp.setId(row[0]);
			tp.setDate(Date.valueOf(row[1]));
			tp.setFotoURL(row[2]);
			tp.setResult1(Integer.valueOf(row[3]));
			tp.setResult2(Integer.valueOf(row[4]));
			tp.setResult3(Integer.valueOf(row[5]));
			tp.setResult4(Integer.valueOf(row[6]));
			pojoList.add(tp);

		}
		return pojoList;
	}
	

}
