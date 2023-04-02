<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mvc_olx.dto.Product"%>
<%@page import="java.util.List"%>
<%@page import="mvc_olx.dao.CustomerDao"%>
<%@page import="mvc_olx.dto.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


	<table border="1">
		<tr>
			<th>Product Name</th>
			<th>Product Price</th>
			<th>Product Image</th>
			<th>Buy</th>
		</tr>
		<%
		for (Product product : (List<Product>) request.getAttribute("list")) {
		%>
		<tr>
			<th><%=product.getName()%></th>
			<th><%=product.getPrice()%></th>
			<th>
				<%
				String base64 = Base64.encodeBase64String(product.getImage());
				%> <img height="200" width="500" alt="unknown"
				src="data:image/jpeg;base64,<%=base64%>">
			</th>
			<th><button>Buy</button></th>
		</tr>
		<%
		}
		%>
	</table>
<br>
<br>

	<a href="Home.jsp"><button>Home</button></a>
</body>
</html>