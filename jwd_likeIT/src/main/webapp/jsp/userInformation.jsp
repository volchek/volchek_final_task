<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>LikeIT</title>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<h1>User information</h1>
	
	<table border="1" class="userInfo">
		<thead>
		<tr>
			<td>Name</td>
			<td>Surname</td>
			<td>Login</td>
			<td>Status</td>
			</tr>
		</thead>
   		<tr>
			<td><c:out value="${requestScope.user.name}"></c:out></td>
			<td><c:out value="${requestScope.user.surname}"></c:out></td>
			<td><c:out value="${requestScope.user.login}"></c:out></td>
			<td><c:out value="${requestScope.user.status}"></c:out></td>
		</tr>
	</table>
	<br>
	<a href="jsp/findUser.jsp" class="link">Найти другого пользователя</a>
</body>
</html>