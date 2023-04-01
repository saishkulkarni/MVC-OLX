<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="x" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Signup</title>
</head>
<body>

<h1>${msg}</h1>

	<h1>Signup Here</h1>

	<x:form action="signup" method="post" modelAttribute="customer">

Name:<input type="text" name="name" required="required">
		<br>
Mobile:<input type="tel" name="mobile" pattern="[0-9]{10}"
			required="required">
		<br>
Password:<input type="password" name="password" required="required">
		<br>
Gender:<input type="radio" name="gender" value="male"
			required="required">Male<br>
		<input type="radio" name="gender" value="female">Female<br>
DOB:<input type="date" required="required" name="bdate">
		<br>
Address:<textarea rows="5" cols="30" name="address" required="required"></textarea>
		<br>
		<button type="reset">Cancel</button>
		<button>Signup</button>
	</x:form>
	<br>
	<br>
	<a href="Home.jsp"><button>Home</button></a>
</body>
</html>