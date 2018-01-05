<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="login.login" bundle="${lc}" var="login" />
<fmt:message key="login.password" bundle="${lc}" var="password" />
<fmt:message key="login.btn.cancel" bundle="${lc}" var="cancel" />
<fmt:message key="login.btn.signIn" bundle="${lc}" var="signIn" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>
	<form action="../Controller" method="post">
		<input type="hidden" name="command" value="SIGN_IN" />
		<div class="container">
			<label><c:out value="${login}" /><br><input type="text"
				name="login" required></label> <br> <label><c:out
						value="${password}" /><br><input type="password" name="password"
				required></label> 
		</div>
		<div class="buttons">
			<button type="button" class="cancelbtn"
				onclick="location.href='../index.jsp'">
				<c:out value="${cancel}" />
			</button>
			<button type="submit" class="signupbtn" value="LogIn">
				<c:out value="${signIn}" />
			</button>
		</div>
	</form>
	<c:import url="fragment/footer.jsp"></c:import>
</body>
</html>