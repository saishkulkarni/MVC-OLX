<%@page import="mvc_olx.dto.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Money to Wallet</title>
</head>
<body>
<%
Customer customer=(Customer)session.getAttribute("customer");
if(customer==null)
{
	response.getWriter().print("<h1>Invalid Session</h1>");
	request.getRequestDispatcher("Home.jsp").include(request, response);
}else{
%>
Current Balance:<%=customer.getWallet()%><br>
<form action="addmoney">
<input type="number" name="money">
<button>Add</button>
</form>
<br><br>
	<a href="Home.jsp"><button>Home</button></a>
<%} %>

</body>
</html>