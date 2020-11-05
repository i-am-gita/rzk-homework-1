<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
<form action="/PlanerWebJsp/RegisterServlet" method="post">
		Firstname<input type="text" name="firstname" id="firstname"> <br> 
		Lastname<input type="text" name="lastname" id="lastname"> <br>
	    Email<input type="text" name="email" id="email"> <br>  
		Password<input type="password" name="password" id="password"> <br>
		<input type="submit" value="Register" name="button" /> <br>
	</form>
</body>
</html>