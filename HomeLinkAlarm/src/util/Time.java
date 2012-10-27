package util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Time {
	public static String getDate() {
		Date now=new Date();
		SimpleDateFormat sa =new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat sa =new SimpleDateFormat("yyyy/M/dd");

		return sa.format(now);
	}
	
	public static int getHour(){
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
		//SimpleDateFormat b = new SimpleDateFormat("");
		int hour = c.get(Calendar.HOUR_OF_DAY);
		//String retHour = "";
		//if (hour<10) {
			//retHour= "0"+hour;
		//}else{
			//retHour = String.valueOf(hour);
		//}
		return hour;
		
	}
	
	
	public static int getMinute(){
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
		int minute = c.get(Calendar.MINUTE);
		return minute;
		
	}
	
	public static void main(String[] args){	
		//String a = getHour();
		//System.out.println(a);
	}
}