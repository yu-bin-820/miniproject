package com.model2.mvc.service.purchase.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {

	///field
	UserService uservice=new UserServiceImpl();
	
	ProductService pservice= new ProductServiceImpl();
	
	public PurchaseDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from transaction where tran_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		PurchaseVO purchaseVO = null;
		
		while(rs.next()) {
			purchaseVO = new PurchaseVO();
			purchaseVO.setTranNo(Integer.parseInt(rs.getString("tran_No")));
			purchaseVO.setPurchaseProd((ProductVO)(pservice.getProduct(rs.getInt("PROD_NO"))));
			purchaseVO.setBuyer((UserVO)(uservice.getUser(rs.getString("buyer_ID"))));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));
			purchaseVO.setOrderDate(rs.getDate("ORDER_DATA"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			
		}
		
		con.close();
		rs.close();
		stmt.close();
		
		return purchaseVO;
		
	}
	
	
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String userId) throws Exception {
		
		System.out.println("PurchaseDAO에서 받은 userId"+userId);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from transaction ";
		
		sql += " where buyer_ID='" + userId+"' ";
		
		sql += " order by ORDER_DATA ";
		
		System.out.println(sql);
		
		PreparedStatement stmt = con.prepareStatement(sql,
																ResultSet.TYPE_SCROLL_INSENSITIVE,
																ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:"+total);
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage() :"+searchVO.getPage());
		System.out.println("searchVO.getPageUnit():"+searchVO.getPageUnit());
		
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if(total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				PurchaseVO purchasevo = new PurchaseVO();
				purchasevo.setTranNo(Integer.parseInt(rs.getString("tran_No")));
				purchasevo.setPurchaseProd((ProductVO)(pservice.getProduct(rs.getInt("PROD_NO"))));
				purchasevo.setBuyer((UserVO)(uservice.getUser(rs.getString("buyer_ID"))));
				purchasevo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				purchasevo.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchasevo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchasevo.setDivyAddr(rs.getString("DEMAILADDR"));
				purchasevo.setDivyRequest(rs.getString("DLVY_REQUEST"));
				purchasevo.setDivyDate(rs.getString("DLVY_DATE"));
				purchasevo.setOrderDate(rs.getDate("ORDER_DATA"));
				purchasevo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				
				
				list.add(purchasevo);
				if(!rs.next())
					break;
				
			}
		}
		System.out.println("list.size() :"+list.size());
		map.put("list", list);
		System.out.println("map().size() :"+map.size());
		
		con.close();
		rs.close();
		stmt.close();
		
		return map;
	}
	
	
	public HashMap<String, Object> getSaleList(SearchVO searchVO) throws Exception {
		
		
		
		return null;
		
	}

	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		System.out.println("PurchaseDAO로 넘어옴"+purchaseVO);
		
		String sql = "insert into transaction values (seq_product_prod_no.nextval, ?, ?, ?, ?, ?, ?, ?, ? , sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDivyDate());
		
		int i = stmt.executeUpdate();
		
		if(i==1) {
			System.out.println("판매등록완료");
		}else {
			System.out.println("판매등록실패");
		}
		
		con.close();
		stmt.close();
	}
	
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		
		System.out.println("DAO에서 update시작");
		System.out.println(purchaseVO);
		Connection con = DBUtil.getConnection();
		
		String sql = "update transaction set PAYMENT_OPTION=?, RECEIVER_NAME=?, RECEIVER_PHONE=?, DEMAILADDR=?, DLVY_REQUEST=?, DLVY_DATE=? where Tran_no=?";
			
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.setInt(7, purchaseVO.getTranNo());
		stmt.executeUpdate();

		System.out.println(sql);
		
		
		con.close();
		stmt.close();
		
	}

	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		
		System.out.println("DAO에서 updatetrancode시작");
		System.out.println(purchaseVO);
		Connection con = DBUtil.getConnection();
		String tranCode = purchaseVO.getTranCode().trim();
		System.out.println(purchaseVO.getTranCode());
		if(tranCode.equals("2")) {
		
		String sql = "update transaction set TRAN_STATUS_CODE=? where PROD_NO=?";
			
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, tranCode);
		stmt.setString(2, purchaseVO.getPurchaseProd().getProdNo());
		
		stmt.executeUpdate();

		System.out.println(sql);
		
		con.close();
		stmt.close();
		
		} else if(tranCode.equals("3")) {
			
		System.out.println("tranCode=3");
		String sql = "UPDATE TRANSACTION SET tran_status_code=? WHERE tran_no=?";
		System.out.println(purchaseVO.getTranNo());	
		PreparedStatement stmt = con.prepareStatement(sql);
			
		stmt.setString(1, tranCode);
		stmt.setInt(2, purchaseVO.getTranNo());

		//stmt.executeUpdate();
		System.out.println("update성공시 1 : "+stmt.executeUpdate());
		System.out.println(sql);
		
		con.close();
		stmt.close();
		
		}

		
	}
}
