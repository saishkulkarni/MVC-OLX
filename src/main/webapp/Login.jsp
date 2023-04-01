<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
<h1>${msg }</h1>
	<h1>Login Here</h1>
	<br>
	<form action="login" method="post">
		Customer Id:<input type="number" name="cid"
			placeholder="Enter Customer Id"><br> Password:<input
			type="password" name="password"><br>
		<button type="reset">Cancel</button>
		<button>Login</button>
	</form>
	<br>
	<br>
	<a href="Home.jsp"><button>Home</button></a>
</body>
</html>