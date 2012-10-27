//*****************************************************************//
//2011-09-19 kuangyi#create SysConfig.java
//
//
//*****************************************************************//

package util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 	系统配置�? * @author libl@jctlive.com 2011-9-27 下午12:03:43
 *
 */

public class SysConfig {
	 public static String URL = "url";
	 public static String DOMAINNAME = "domainName";
	 public static String USERNAME = "userName";
	 public static String TYPE = "type";
	 public static String DEBUG = "1";
	 public static String LEVEL = "level";
	 public static String POINT = "point";
	 public static String LOGTYPE="logtype";
	 //public static Logger logger = Logger.getLogger(SysConfig.class);
	 public void loadconfig() throws FileNotFoundException{
		 readconfigfile();
		 
	 }

	 /**
	  *读取config.properties配置文件
	  * @author libl@jctlive.com 2011-9-27 下午12:22:43
	  *
	  */
	 
	public void readconfigfile() throws FileNotFoundException{
		// File f = new File("D:\\lizhe_projects\\cpic\\trunk\\src\\jct\\db\\config.properties");
		 
		//InputStream inputStream = this.getClass().getResourceAsStream("config.properties");
		String filePath = System.getProperty("user.dir") + "/config/config.properties";  
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
  
		Properties p = new Properties();   
		  try {   
		  p.load(inputStream);
		  //p.load(f);
		  } catch (IOException e1) {   
		   e1.printStackTrace();   
		  }
		
			URL = p.getProperty("url");
			USERNAME = p.getProperty("userName");
			DOMAINNAME=p.getProperty("domainName");
			TYPE=p.getProperty("type");
			LEVEL = p.getProperty("level");
			POINT=p.getProperty("point");
	
	 }
	
	/**
	 *获取生成日志文件路径
	 * @author libl@jctlive.com 2011-9-27 下午12:22:43
	 *
	 */
		
	public String getPath(String path,String folderName,String fileName) {
			StringBuffer str=new StringBuffer();
			str.append(path);
			if(!folderName.equals("")){
			  str.append("/").append(folderName);
			}
			str.append("/").append(fileName);
			return str.toString();
			
	}
	
	public static void main(String[] args){
		String a  = "wangjinlong";
		String b  = "fuck";
		System.out.println(a+b);
	}

}
