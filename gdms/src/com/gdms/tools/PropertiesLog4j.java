package com.gdms.tools;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PropertiesLog4j {
	public int getGradeByFile(){
		Properties pps = new Properties();
		try {
			pps.load(getClass().getResourceAsStream("/com/gdms/resources/log4j.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String grade = pps.getProperty("grade");
		return Integer.parseInt(grade);
	}
	public Properties getProperties(){
		Properties pps = new Properties();
		try {
			pps.load(getClass().getResourceAsStream("/com/gdms/resources/log4j.properties"));
			//pps.load(getClass().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pps;
	}
	public void writeProperties(Properties pps){
		FileOutputStream fos=null;
		try {
//			fos=new FileOutputStream((getClass().getClassLoader().getResource("").toURI()).getPath()+"config.properties");
			fos=new FileOutputStream((getClass().getClassLoader().getResource("").toURI()).getPath()+"/com/gdms/resources/log4j.properties");
//			System.out.println(""+(getClass().getClassLoader().getResource("").toURI()).getPath()+"log4j.properties");
//			fos=new FileOutputStream((getClass().getClassLoader().getResource("").toURI()).getPath()+"log4j.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pps.store(fos, "test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Date getStartDate() throws ParseException{
		String dateStr = getProperties().getProperty("startDate");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		Date date = sdf.parse(dateStr);
		return date;
	}
	public static void setStartDate(Date date) throws ParseException{
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		PropertiesLog4j p4j = new PropertiesLog4j();
		Properties pps = p4j.getProperties();
		pps.setProperty("startDate", ""+sdf.format(date));
		p4j.writeProperties(pps);
	}
	public static void setGrade(int grade){
		PropertiesLog4j p4j = new PropertiesLog4j();
		Properties pps = p4j.getProperties();
//		System.out.println(""+pps.getProperty("grade"));
		pps.setProperty("grade", ""+grade);
		p4j.writeProperties(pps);
	}
	public static void setStartDate(String dateStr){
		PropertiesLog4j p4j = new PropertiesLog4j();
		Properties pps = p4j.getProperties();
//		System.out.println(""+pps.getProperty("grade"));
		pps.setProperty("startDate", dateStr);
		p4j.writeProperties(pps);
	}
	public static void main(String argv[]){
		PropertiesLog4j p4j = new PropertiesLog4j();
		Properties pps = p4j.getProperties();
		System.out.println(""+pps.getProperty("grade"));
		pps.setProperty("grade", "2016");
		p4j.writeProperties(pps);
	}
	public static int getGrade()
	{
		PropertiesLog4j p4j = new PropertiesLog4j();
		int grade = p4j.getGradeByFile();
		return grade;
	}
}
