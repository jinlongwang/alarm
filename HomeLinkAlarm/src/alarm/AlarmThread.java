package alarm;


import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import org.omg.CORBA.OBJ_ADAPTER;

import util.DBUtil;
import util.Property;
import util.SysConfig;
import util.Time;

import api.AlarmApi;


public class AlarmThread extends Thread{
	public AlarmThread(String name){
		super(name);
	}
	
	public void run(){
		while(true){
			int time_hour = Time.getHour();
			int time_min = Time.getMinute();
			System.out.println("["+time_hour+":"+time_min+"]");
			if(time_hour >= 9 && time_hour <= 22 && time_min == 0 ){
				try {
					System.out.println("send message");
					String selectTime_one = "";
					String selectTime_two = "";
					SysConfig config = new SysConfig();
					config.loadconfig();
					String title = "提示";
					String url = SysConfig.URL;
					String domainName = SysConfig.DOMAINNAME;
					String userName = SysConfig.USERNAME;
					String type = SysConfig.TYPE;
					String level = SysConfig.LEVEL;
					System.out.println(domainName + userName+"===================");
					int hour = Time.getHour();
					System.out.println(hour);
					String shour = hour-2<10?"0"+(hour-2):String.valueOf(hour-2);
					selectTime_one = Time.getDate()+" "+shour+":"+"00";
					StringBuffer alarmContent = new StringBuffer();
					StringBuffer alarmContent2 = new StringBuffer();
					ArrayList<ArrayList<HashMap<String, String>>> badList  = selectDateByIbatis(5,25,75,selectTime_one);
					if(badList.size() != 0){
						for (HashMap<String, String> hashMap : badList.get(0)) {
							   String detailmsg = hashMap.get("msg");
							   String name = hashMap.get("name");
							   String houseName = hashMap.get("house_name");
							   String content = "["+selectTime_one+"]:"+name+" "+houseName+" "+detailmsg;
							   alarmContent.append(content+"</br>");
						}
						AlarmApi api = new AlarmApi(url);
						api.sendSystemMessage(domainName, userName, "不足提示",alarmContent.toString(),type, level);
						
						for (HashMap<String, String> hashMap : badList.get(1)) {
							   String detailmsg = hashMap.get("msg");
							   String name = hashMap.get("name");
							   String houseName = hashMap.get("house_name");
							   String content = "["+selectTime_one+"]:"+name+" "+houseName+" "+detailmsg;
							   alarmContent2.append(content+"</br>");
						}
						AlarmApi api2 = new AlarmApi(url);
						api2.sendSystemMessage(domainName, userName, "超标提示",alarmContent2.toString(),type, level);
					}
					
					//selectTime_two = Time.getDate()+" "+(hour-2)+":"+"00"; 
					
					//前五条数据不用加上限
					//selectData(5, selectTime_one,selectTime_two, domainName, userName, title, type, level, url);
					//前25条
					//selectData2(25, selectTime_one,selectTime_two, domainName, userName, title, type, level, url);
					//前75条
					//selectData3(75, selectTime_one,selectTime_two, domainName, userName, title, type, level, url);
					Thread.sleep(60000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			}else{
				System.out.println("It's not the time");
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
		}
		
	}

	private ArrayList<ArrayList<HashMap<String, String>>> selectDateByIbatis(int num5, int num25, int num75, String selectTime_one) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String , Object> param = new HashMap<String, Object>();
		param.put("mynum5", num5);
		param.put("mynum25", num25);
		param.put("mynum75", num75);
		param.put("mycrop", "链家地产");
		param.put("mydate", selectTime_one);
		List<HashMap<String, String>> retList = (List<HashMap<String,String>>)DBUtil.queryForList("sql_for_max",param);
		System.out.println(retList);
		ArrayList<HashMap<String, String>> retArraylt = new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String, String>> retArraygt = new ArrayList<HashMap<String,String>>();
		ArrayList<ArrayList<HashMap<String, String>>> retArray = new ArrayList<ArrayList<HashMap<String,String>>>();
		HashMap<String, String> chargerNamedic = new HashMap<String, String>();
		if (retList != null && retList.size() != 0) {
			for (HashMap<String, String> map : retList) {
				String ltmsg = "";
				String gtmsg = "";
				HashMap<String, String> badDatelt = new HashMap<String, String>();
				HashMap<String, String> badDategt = new HashMap<String, String>();
				Boolean checkDataFlaglt = false;
				Boolean checkDataFlaggt = false;
				//StringBuffer ltmsg = new StringBuffer();
				String house_name = map.get("HNAME1");
				String num_5 = String.valueOf(map.get("count"));
				String target_5 = String.valueOf(map.get("NUMBER_FIVE_MIN"));
				String target5Max = String.valueOf(map.get("NUMBER_FIVE_MAX"));
				String num_25 = String.valueOf(map.get("count25"));
				String target_25 = String.valueOf(map.get("NUMBER_TWENTYFIVE_MIN"));
				String target25Max = String.valueOf(map.get("NUMBER_TWENTYFIVE_MAX"));
				String num_75 = String.valueOf(map.get("count75"));
				String target_75 = String.valueOf(map.get("NUMBER_SEVENTYFIVE_MIN"));
				String target75Max = String.valueOf(map.get("NUMBER_SEVENTYFIVE_MAX"));
				String chargerName = map.get("CHARGER");
				if(chargerName == null){
					continue;
				}
				if("null".equals(target_5) || "null".equals(target_25) || "null".equals(target_5)){
					continue;
				}
				if (Integer.parseInt(num_5) < Integer.parseInt(target_5)) {
					ltmsg = ltmsg + String.valueOf(num_5)+"/5;";
					checkDataFlaglt = true;
				} 
				if (Integer.parseInt(num_25) < Integer.parseInt(target_25)) {
					ltmsg = ltmsg + String.valueOf(num_25)+"/25;";
					checkDataFlaglt = true;
				}
				if (Integer.parseInt(target_75) != 0 &&Integer.parseInt(num_75)< Integer.parseInt(target_75)) {
					ltmsg = ltmsg + String.valueOf(num_75)+"/75;";
					checkDataFlaglt = true;
				}
				if (Integer.parseInt(num_5) > Integer.parseInt(target5Max)) {
					gtmsg = gtmsg + String.valueOf(num_5)+"/5;";
					checkDataFlaggt = true;
				}
				if (Integer.parseInt(num_5) > Integer.parseInt(target25Max)) {
					gtmsg = gtmsg + String.valueOf(num_25)+"/25;";
					checkDataFlaggt = true;
				}
				if (Integer.parseInt(target_75) != 0 && Integer.parseInt(num_5) > Integer.parseInt(target75Max)) {
					gtmsg = gtmsg + String.valueOf(num_75)+"/75;";
					checkDataFlaggt = true;
				}
				if(checkDataFlaglt){
					badDatelt.put("name", chargerName);
					badDatelt.put("house_name", house_name);
					badDatelt.put("msg", ltmsg);
				}
				if(checkDataFlaggt){
					badDategt.put("name", chargerName);
					badDategt.put("house_name", house_name);
					badDategt.put("msg", gtmsg);
				}
				if(badDatelt.size() != 0){
					retArraylt.add(badDatelt);
				}
				if(badDategt.size() != 0){
					retArraygt.add(badDategt);
				}
			}
		}
		retArray.add(retArraylt);
		retArray.add(retArraygt);
		return retArray;
	}
	
	
	/*private void selectData(int mynum,String mSelectTime_one,String mSelectTime_two,String domainName,String userName,String title,String type,String level,String url) throws Exception{
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("mynumber", mynum);
		param.put("mydate1", mSelectTime_one);
		param.put("mydate2",mSelectTime_two);
		List<HashMap<String, String>> retListFive = List<HashMap<String, String>>) DBUtil.queryForList("sql1",param);
		System.out.println(retListFive); 
		if(retListFive != null && retListFive.size() != 0){
			for (HashMap<String, String> map : retListFive) {
			String total = String.valueOf(map.get("num"));
			String percent = String.valueOf(map.get("rate"));
			String number =String.valueOf(map.get("count"));
			String house_name = map.get("HOUSE_NAME");
			String target = map.get("NUMBER_FIVE");
			System.out.println(number+","+house_name+","+target);
			if (target != null) {
				if (Integer.parseInt(number)< Integer.parseInt(target)) 
				{
					String content = "[时间:"+mSelectTime_one+"] "+house_name + " 前5条不达标，" +"只有"+number+"条";
					System.out.println(content);
					AlarmApi api = new AlarmApi(url);
					api.sendSystemMessage(domainName, userName, title,content,type, level);
		
				}
			}
			//String content = "条数："+mynum+","+"楼盘名："+house_name+","+"占比:"+percent+","+"链家数量："+number+","+"总数："+total;
			}
		}
	}
	
	private void selectData2(int mynum,String mSelectTime_one,String mSelectTime_two,String domainName,String userName,String title,String type,String level,String url) throws Exception{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("mynumber", mynum);
		param.put("mydate1", mSelectTime_one);
		param.put("mydate2", mSelectTime_two);
		List<HashMap<String, String>> retListFive = (List<HashMap<String, String>>) DBUtil.queryForList("sql2",param);
		System.out.println(retListFive); 
		if(retListFive != null && retListFive.size() != 0){
			for (HashMap<String, String> map : retListFive) {
				String total = String.valueOf(map.get("num"));
				String percent = String.valueOf(map.get("rate"));
				String number =String.valueOf(map.get("count"));
				String house_name = map.get("HOUSE_NAME");
				String target = map.get("NUMBER_TWENTYFIVE");
				if (target != null) {
					if (Integer.parseInt(number)< Integer.parseInt(target)) 
					{
						String content = "[时间:"+mSelectTime_one+"] "+house_name +" 前25条不达标，" +"只有"+number+"条";
						System.out.println(content);
						AlarmApi api = new AlarmApi(url);
						api.sendSystemMessage(domainName, userName, title,content,type, level);
					}
				}
			}
		}
	}
	
	
	private void selectData3(int mynum,String mSelectTime_one,String mSelectTime_two,String domainName,String userName,String title,String type,String level,String url) throws Exception{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("mynumber", mynum);
		param.put("mydate1", mSelectTime_one);
		param.put("mydate2", mSelectTime_two);
		List<HashMap<String, String>> retListFive = (List<HashMap<String, String>>) DBUtil.queryForList("sql3",param);
		System.out.println(retListFive); 
		if(retListFive != null && retListFive.size() != 0){
			for (HashMap<String, String> map : retListFive) {
				String total = String.valueOf(map.get("num"));
				String percent = String.valueOf(map.get("rate"));
				String number =String.valueOf(map.get("count"));
				String house_name = map.get("HOUSE_NAME");
				String target = map.get("NUMBER_SEVENTYFIVE");
				if (target != null) {
					if (Integer.parseInt(target) != 0) {
						if (Integer.parseInt(number)< Integer.parseInt(target)) 
						{
							String content = "[时间:"+mSelectTime_one+"] "+house_name + " 前75条不达标，" +"只有"+number+"条";
							System.out.println(content);
							AlarmApi api = new AlarmApi(url);
							api.sendSystemMessage(domainName, userName, title,content,type, level);
						}
					}
					
				}
			}
		}
	
	}*/

}
