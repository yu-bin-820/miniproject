package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class UpdateProductViewAction extends Action {

	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("************");
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		System.out.println("22222222222222222");
		ProductService service = new ProductServiceImpl();
		ProductVO VO = service.getProduct(prodNo);
		
		System.out.println("333333333333333333333333");
		request.setAttribute("vo", VO);
		
		return "forward:/product/updateProduct.jsp";
		
	}

}
