<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>

	<form action="/PlanerWebJsp/LoginServlet" method="post">
		Email<input type="text" name="username" id="username"> <br> 
		Password<input type="password" name="password" id="password"> <br>
		<input type="submit" value="Login" name="button" /> <br>
	</form>
	You don't have a profile yet? Please register by clicking the <a href="/PlanerWebJsp/register.jsp">link</a>.
</body>
</html>