<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Product</title>
</head>
<body>
<%
if(session.getAttribute("customer")==null)
{
	response.getWriter().print("<h1>Invalid Session</h1>");
	request.getRequestDispatcher("Home.jsp").include(request, response);
}else{
%>
	<h1>Add Product to Sell</h1>
	<form action="addproduct" method="post" enctype="multipart/form-data">
		Product Name:<input type="text" name="pname"><br> Product
		Price:<input type="text" name="pprice"><br> Product
		IMage:<input type="file" name="pimage"><br>
		<button type="reset">Cancel</button>
		<button>Put on Sale</button>
	</form>
	<br>
	<br>
	<a href="Home.jsp"><button>Home</button></a>
	<%} %>
</body>
</html>