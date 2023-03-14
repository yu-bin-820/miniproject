package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseViewAction extends Action {

	public AddPurchaseViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("AddPurchaseView로 넘어온 request:"+request);
		System.out.println(request.getParameter("prod_no"));
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));
		
		ProductService pservice = new ProductServiceImpl();
		ProductVO prodvo = (pservice.getProduct(prodNo));
		
		request.setAttribute("prodvo", prodvo);
		
		HttpSession session=request.getSession(); 

		UserVO uservo = (UserVO)session.getAttribute("user");
		System.out.println(uservo.toString());
		
		request.setAttribute("uservo", uservo);
		
		System.out.println("addPurchaseviewaction에 들어간 VO"+prodvo+uservo);
		
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
