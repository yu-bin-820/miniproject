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
		map = new HashMap<String, Action>(); //값에 따른 action저장하기 위한 공간생성
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources); 
			//in  = new FileINputStream(resources);   //이거랑 같은 의미
			properties = new Properties(); //키벨류로 데이터관리하는것 추상화 캡슐화
			properties.load(in); //파싱해서 데이터 가지고있음
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}	
	}
	
	//meta data의 정보를 프로포티스에 정보 주는 애
	
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		} //널값 확인해주는 거
		return requestMapping;
	}
	
	//requestMapping에 프로포티스 세팅
	
	public Action getAction(String path){
		Action action = map.get(path);
		if(action == null){  //있으면 있는거 쓰고 없으면 만들어 데이터 관리 효율적으로
			String className = properties.getProperty(path); 
			System.out.println("prop : " + properties);   //{/logout.do=com.model2.mvc.view.user.LogoutA~~~~
			System.out.println("path : " + path);			//login.do
			System.out.println("className : " + className);	//com.model2.mvc.view.user.LoginAction
			className = className.trim(); //눈에안보이는 빈공간으로 오류 생기지 않도록 제거
			try{
				Class c = Class.forName(className); //스트링으로 되어있는거 인스턴스로 생성(jdbc랑 다르게 내가 관리해야됨)
				System.out.println(c); //class com.model2.mvc.view.user.LoginAction
				Object obj = c.newInstance();  //생성해줌
				System.out.println(obj); //com.model2.mvc.view.user.LoginAction@6d6bfcd4
				System.out.println(obj instanceof Action); //true
				if(obj instanceof Action){  //유효성체크
					map.put(path, (Action)obj);  
					action = (Action)obj;
					System.out.println(action);
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}
}

//컨트롤러에서 달라는 거 주는애