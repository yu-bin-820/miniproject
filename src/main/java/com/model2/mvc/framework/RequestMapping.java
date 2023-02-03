package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	private RequestMapping(String resources) {
		map = new HashMap<String, Action>(); //���� ���� action�����ϱ� ���� ��������
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources); 
			//in  = new FileINputStream(resources);   //�̰Ŷ� ���� �ǹ�
			properties = new Properties(); //Ű������ �����Ͱ����ϴ°� �߻�ȭ ĸ��ȭ
			properties.load(in); //�Ľ��ؼ� ������ ����������
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}	
	}
	
	//meta data�� ������ ������Ƽ���� ���� �ִ� ��
	
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		} //�ΰ� Ȯ�����ִ� ��
		return requestMapping;
	}
	
	//requestMapping�� ������Ƽ�� ����
	
	public Action getAction(String path){
		Action action = map.get(path);
		if(action == null){  //������ �ִ°� ���� ������ ����� ������ ���� ȿ��������
			String className = properties.getProperty(path); 
			System.out.println("prop : " + properties);   //{/logout.do=com.model2.mvc.view.user.LogoutA~~~~
			System.out.println("path : " + path);			//login.do
			System.out.println("className : " + className);	//com.model2.mvc.view.user.LoginAction
			className = className.trim(); //�����Ⱥ��̴� ��������� ���� ������ �ʵ��� ����
			try{
				Class c = Class.forName(className); //��Ʈ������ �Ǿ��ִ°� �ν��Ͻ��� ����(jdbc�� �ٸ��� ���� �����ؾߵ�)
				System.out.println(c); //class com.model2.mvc.view.user.LoginAction
				Object obj = c.newInstance();  //��������
				System.out.println(obj); //com.model2.mvc.view.user.LoginAction@6d6bfcd4
				System.out.println(obj instanceof Action); //true
				if(obj instanceof Action){  //��ȿ��üũ
					map.put(path, (Action)obj);  
					action = (Action)obj;
					System.out.println(action);
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}

//��Ʈ�ѷ����� �޶�� �� �ִ¾�