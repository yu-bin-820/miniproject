package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddProductAction extends Action {
	
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ProductVO productVO = new ProductVO();
		productVO.setFileName(request.getParameter("fileName"));
		productVO.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));
		productVO.setPrice(request.getParameter("price"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setProdName(request.getParameter("prodName"));
		
		System.out.println(productVO);
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(productVO);
		
		return "redirect:/product/addProductView.jsp";
	}

}
