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
import com.model2.mvc.service.user.vo.UserVO;

public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("UpdateTrancodeActionΩ√¿€");
		
		ProductService proservice = new ProductServiceImpl();
		ProductVO provo = proservice.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		
		PurchaseVO vo = new PurchaseVO();
		vo.setPurchaseProd(provo);
		vo.setTranCode(request.getParameter("tranCode"));
		System.out.println(vo);
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(vo);
		
		
		return "redirect:/listProduct.do?menu=manage";
	}

}
