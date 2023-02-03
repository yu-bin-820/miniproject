package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		mapper=RequestMapping.getInstance(resources);//요구사항 파싱해서 mapper에 세팅
	System.out.println("준비끝");
	} 

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		System.out.println("request받음");
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		System.out.println(path);  //req로 path경로 설정
		
		try{
			Action action = mapper.getAction(path);
			action.setServletContext(getServletContext()); //pathAction
			
			String resultPage=action.execute(request, response);  //일해일하고 가는곳 알려줘(URI) =>navigation
			//redirect:/index.jsp
			System.out.println(response);
			String result=resultPage.substring(resultPage.indexOf(":")+1); //파싱
			//index.jsp
			
			if(resultPage.startsWith("forward:")) //false
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}