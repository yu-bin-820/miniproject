<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ page import="com.model2.mvc.service.user.vo.*" %>
<%@ page import="com.model2.mvc.service.product.vo.*" %>
<%@ page import="com.model2.mvc.service.purchase.vo.*" %>

<%
	PurchaseVO purchasevo = (PurchaseVO)request.getAttribute("purchasevo");
	//UserVO uvo = (UserVO)purchasevo.getBuyer();
	UserVO uvo = (UserVO)session.getAttribute("user");
	ProductVO pvo = (ProductVO)purchasevo.getPurchaseProd();
%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td><%= pvo.getProdNo() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td><%= uvo.getUserId() %></td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td><%= purchasevo.getPaymentOption() %>

		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td><%= purchasevo.getReceiverName() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td><%= purchasevo.getReceiverPhone() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td><%= purchasevo.getDivyAddr() %></td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td><%= purchasevo.getDivyRequest() %></td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td><%= purchasevo.getDivyDate() %></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>