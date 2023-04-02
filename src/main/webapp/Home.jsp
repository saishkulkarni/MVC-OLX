<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>
	<h1>${msg}</h1>
	<h1>Welcome to JSP OLX Home</h1>
	<br>
	<%
	if (session.getAttribute("customer") == null) {
	%>
	<a href="Login.jsp"><button>Login</button></a>
	<br>
	<br>
	<a href="load"><button>Signup</button></a>
	<br>
	<br>
	<%
	}
	%>
	<a href="displayproducts"><button>Buy</button></a>
	<br>
	<br>
	<%
	if (session.getAttribute("customer") != null) {
	%>
	<a href="AddProduct.jsp"><button>Sell</button></a>
	<br>
	<br>
	<a href="AddMoney.jsp"><button>Add Money to wallet</button></a>
	<br>
	<br>
	<a href="logout"><button>Logout</button></a>
	<br>
	<br>
	<%
	}
	%>
</body>
</html>