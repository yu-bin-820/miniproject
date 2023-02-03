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
		mapper=RequestMapping.getInstance(resources);//�䱸���� �Ľ��ؼ� mapper�� ����
	System.out.println("�غ�");
	} 

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		System.out.println("request����");
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		System.out.println(path);  //req�� path��� ����
		
		try{
			Action action = mapper.getAction(path);
			action.setServletContext(getServletContext()); //pathAction
			
			String resultPage=action.execute(request, response);  //�������ϰ� ���°� �˷���(URI) =>navigation
			//redirect:/index.jsp
			System.out.println(response);
			String result=resultPage.substring(resultPage.indexOf(":")+1); //�Ľ�
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