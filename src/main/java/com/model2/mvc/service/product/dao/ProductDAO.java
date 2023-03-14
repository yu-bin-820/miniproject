package com.model2.mvc.service.product.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductDAO {

	public ProductDAO() {
	}
	
	public ProductVO findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from Product where PROD_NO = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductVO productVO = null;
		while(rs.next()) {
			productVO = new ProductVO();
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getString("PRICE"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdNo(rs.getString("PROD_NO"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();
		rs.close();
		stmt.close();
		
		return productVO;		
	}
	
	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select pro.prod_no, pro.prod_name, pro.prod_detail, pro.manufacture_day, "
				+ " pro.price, pro.image_file, pro.reg_date, NVL(pur.tran_status_code,0) tran_status_code "
				+ " from product pro, transaction pur "
				+ " where pro.prod_no=pur.prod_no(+) ";
		if(searchVO.getSearchCondition() != null) {
			if(searchVO.getSearchCondition().equals("0")) {
				sql += " AND pro.PROD_NO='"+searchVO.getSearchKeyword()+"'";
			}else if (searchVO.getSearchCondition().equals("1")) {
				sql += " AND pro.PROD_NAME='" + searchVO.getSearchKeyword()+"'";
			}else if (searchVO.getSearchCondition().equals("2")) {
				sql += " AND pro.PRICE='"+searchVO.getSearchKeyword()+"'";
			}
		}
		sql += " order by pro.PROD_NO";
		
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
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if(total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setPrice(rs.getString("PRICE"));
				vo.setProdDetail(rs.getString("PROD_DETAIL"));
				vo.setProdName(rs.getString("PROD_NAME"));
				vo.setProdNo(rs.getString("PROD_NO"));
				vo.setRegDate(rs.getDate("REG_DATE"));
				vo.setProTranCode(rs.getString("tran_status_code").trim());
				
				list.add(vo);
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
	
	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		System.out.println(productVO+"productdao");
		
		String sql = "insert into product values (seq_product_prod_no.nextval, ?, ?, ?, ?, ?, sysdate)";
				
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setString(4,productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
		int i = stmt.executeUpdate();
		
		if(i==1) {
			System.out.println("등록완료");
		}else {
			System.out.println("등록실패");
		}
	}
	
	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "update Product set prod_name=?, prod_detail=?, manufacture_day=?,price=?, image_file=? where Prod_no=?";
			
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setString(4,productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setString(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
		stmt.close();
		
	}

}
