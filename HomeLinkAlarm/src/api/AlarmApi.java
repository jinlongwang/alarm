package api;

import java.net.MalformedURLException;
import java.util.ArrayList;

import net.extsoft.webservice.WebServiceException;
import net.extsoft.webservice.service.Generic;

import com.caucho.hessian.client.HessianProxyFactory;

public class AlarmApi {
	//url服务器地址
	private String url;
	
	public AlarmApi(String url){
		this.url = url;
		
	}
	
	/**
	 * 获取服务
	 * @return service
	 */
	public Generic getService(){
		HessianProxyFactory factory = new HessianProxyFactory();

		Generic service = null;

		try {
			// 获取服务
			service = (Generic) factory.create(Generic.class, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return service;
		
	}

	
	/**
	 * 创建域
	 */
	public void creatDomainName(String domainName){
		Generic service = getService();
		if (service != null) {
			// 第一步，创建域
			try {
				System.out.println("添加域：" + domainName);
				service.addDomain(domainName);
				System.out.println("添加域：" + domainName + " 成功");
			} catch (WebServiceException e) {
				// 如果调用不成功，会抛出异常，程序需要对异常做处理
				e.printStackTrace();
			} 
		
		}
	}
	
	
	/**
	 * 创建部门
	 * @param domainName 域名
	 * @param departID 	   部门ID
	 * @param departName 部门名
	 */
	public void createApart(String domainName, String departID,
			String departName) {
		Generic service = getService();
		if (service != null) {
			try {
				System.out.println("添加部门：" + departName + "{ID:" + departID
						+ ")");
				service.addDepartment(domainName, departID, departName, null,
						"0");
				System.out.println("添加部门：" + departName + "{ID:" + departID
						+ ") 成功");
			} catch (WebServiceException e) {
				// 失败
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 创建人员
	 * @param domainName
	 * @param userID
	 * @param userName
	 * @param realName
	 * @param sex
	 * @param password
	 */
	public void creatUser(String domainName,String userID,String userName, String realName,String sex,String password){
		Generic service = getService();
		if (service != null) {
			try {
				System.out.println("添加用户：" + realName + "(userName：" + userName
						+ ")");
				service.addEmployee(domainName, userName, realName, sex,
						password);
				System.out.println("添加用户：" + realName + "(userName：" + userName
						+ ") 成功");
			} catch (WebServiceException e) {
				// 失败
				e.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * 向用户发送系统消息
	 * @param domainName
	 * @param userName 用户名 多个用户名之间用英文半角","分隔
	 * @param title 标题
	 * @param content 内容
	 * @param type 
	 * @param level 消息重要程度，有效值:普通:null、""、"NULL"、"0"，中等:"1"，紧急:"2" 

	 */
	public void sendSystemMessage(String domainName,String userName,String title,String content,String type,String level){
		Generic service = getService();
		if(service != null){
			try {
				System.out.println("向人员" + userName + "发送消息：" + content);
				service.sendSystemMessage(domainName, userName, title, content,
						type, level);
				System.out.println("向人员" + userName + "发送消息：成功");
			} catch (WebServiceException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public static void main(String[] args) {
		// 服务地址
		String url = "http://192.168.0.133:9090/hessian/generic";

		// 创建hession工厂
		HessianProxyFactory factory = new HessianProxyFactory();

		Generic service = null;

		try {
			// 获取服务
			service = (Generic) factory.create(Generic.class, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (service != null) {
			// 第一步，创建域
			String domainName = "test";
			try {
				System.out.println("添加域：" + domainName);
				service.addDomain(domainName);
				System.out.println("添加域：" + domainName + " 成功");
			} catch (WebServiceException e) {
				// 如果调用不成功，会抛出异常，程序需要对异常做处理
				e.printStackTrace();
			}

			try {
				// 抛出异常例子：再次调用，抛出异常
				service.addDomain(domainName);
			} catch (WebServiceException e) {
				System.out
						.println("再次添加域：" + domainName + " " + e.getMessage());
				// 打印异常信息
				e.printStackTrace();
			}

			// //////////////////////////////////

			// 第二步，创建部门
			String departID = "1";
			String departName = "测试部门1";

			try {
				System.out.println("添加部门：" + departName + "{ID:" + departID
						+ ")");
				service.addDepartment(domainName, departID, departName, null,
						"0");
				System.out.println("添加部门：" + departName + "{ID:" + departID
						+ ") 成功");
			} catch (WebServiceException e) {
				// 失败
				e.printStackTrace();
			}

			// //////////////////////////////////

			// 第三步，创建人员
			String userID = "1";
			String userName = "user1";
			String realName = "用户1";
			String sex = "男";
			String password = "123456";

			try {
				System.out.println("添加用户：" + realName + "(userName：" + userName
						+ ")");
				service.addEmployee(domainName, userName, realName, sex,
						password);
				System.out.println("添加用户：" + realName + "(userName：" + userName
						+ ") 成功");
			} catch (WebServiceException e) {
				// 失败
				e.printStackTrace();
			}

			// //////////////////////////////////

			// 第五步，将人员追加到部门1
			String duty = "部门经理";// 职务
			String sort = "0";// 在部门中排序

			try {
				System.out.println("将人员追加到部门：" + departID);
				service.appendEmployee(domainName, userName, departID, duty,
						sort);
				System.out.println("将人员追加到部门：" + departID + " 成功");
			} catch (WebServiceException e) {
				// 失败
				e.printStackTrace();
			}

			// //////////////////////////////////

			// 第六步，向人员发送消息
			String title = "测试消息";
			String content = "<a href='#' onclick=\"alert('支持执行脚本')\">内容支持html格式，支持脚本</a>";
			String type = null;// 消息类型，具体说明见api
			String level = "0";// 消息级别，0=普通

			try {
				System.out.println("向人员" + userName + "发送消息：" + content);
				service.sendSystemMessage(domainName, userName, title, content,
						type, level);
				System.out.println("向人员" + userName + "发送消息：成功");
			} catch (WebServiceException e) {
				e.printStackTrace();
			}
		}

	}
}
