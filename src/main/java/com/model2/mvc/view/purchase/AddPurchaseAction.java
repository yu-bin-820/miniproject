package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class AddPurchaseAction extends Action{

	public AddPurchaseAction() {
		// TODO Auto-generated constructor stub
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("AddPurchase로 넘어온 request:"+request);
		
		UserService uservice=new UserServiceImpl();
		UserVO uvo=uservice.getUser(request.getParameter("buyerId"));
		
		ProductService pservice= new ProductServiceImpl();
		ProductVO pvo=pservice.getProduct(Integer.parseInt(request.getParameter("purchaseProd")));
		
		PurchaseVO purchaseVO=new PurchaseVO();
		purchaseVO.setBuyer(uvo);
		purchaseVO.setPurchaseProd(pvo);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receicerPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		
		purchaseVO.setTranCode("1");
		
		System.out.println("addPurchaseviewaction에 들어간 VO"+purchaseVO);
		
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchaseVO);
		
		request.setAttribute("purchasevo",purchaseVO);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
