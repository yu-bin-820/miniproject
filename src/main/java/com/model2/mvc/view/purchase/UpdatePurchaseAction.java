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

public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("UpdatePurchase시작");
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseVO vo = new PurchaseVO();
		vo.setTranNo(tranNo);
		vo.setPaymentOption(request.getParameter("paymentOption"));
		vo.setReceiverName(request.getParameter("receiverName"));
		vo.setReceiverPhone(request.getParameter("receiverPhone"));
		vo.setDivyAddr(request.getParameter("receiverAddr"));
		vo.setDivyRequest(request.getParameter("receiverRequest"));
		vo.setDivyDate(request.getParameter("divyDate"));
		
		System.out.println("담긴거"+vo);
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updatePurchase(vo);
		
		request.setAttribute("vo", vo);
		return "forward:/getPurchase.do?tranNo="+tranNo;
	}

}
