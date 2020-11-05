<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Planner</title>
</head>
<body>
<h3>Hello there and welcome to the best event planing app you have ever used before!</h3>

<form action="/PlanerWebJsp/CreateEventServlet" method="post">
Create new Event<br><br>

Description<input type="text" name="description">
<br>
From date<input type="date" name="fromDate"> and time <input type="text" name="fromTime"> 
<br>
ToDate<input type="date" name="toDate"> and time <input type="text" name="toTime"> 
<br>
Event type <select name = "eventType">
				<c:forEach var = "eventType" items = "${eventTypes}">
					<option value = "${eventType.id }">${eventType.name}</option>
				</c:forEach>
			</select>
<br>
<input type="submit" value="Create">
</form>

<br>
<br>

<form action="/PlanerWebJsp/ShowEventServlet" method="get">
Show events
	Date<input type="date" name="eventsDate"> <br>
	<input type="submit" value="Show">
</form>

<c:if test="${empty eventResults }">
	${searchStatus}
</c:if>

<c:if test="${!empty eventResults }">
	<c:forEach var = "event" items = "${eventResults}">
		${event.description}
	</c:forEach>
</c:if>
</body>
</html>