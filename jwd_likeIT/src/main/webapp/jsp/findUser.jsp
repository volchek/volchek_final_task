<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="find.login" bundle="${lc}" var="login" />
<fmt:message key="find.btn.cancel" bundle="${lc}" var="cancel" />
<fmt:message key="find.btn.find" bundle="${lc}" var="find" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>

	<form action="../Controller" method="get">
		<input type="hidden" name="command" value="FIND_BY_LOGIN" />
		<div class="container">
			<label><c:out value="${login}" /><br> <input
				type="text" name="login" required> </label>
		</div>
		<div class="buttons">
			<c:choose>
				<c:when test="${not empty current_user.login}">
					<button type="button" class="cancelbtn"
						onclick="location.href='afterLogIn.jsp'">
						<c:out value="${cancel}" />
					</button>
				</c:when>
				<c:otherwise>
					<button type="button" class="cancelbtn"
						onclick="location.href='../index.jsp'">
						<c:out value="${cancel}" />
					</button>
				</c:otherwise>
			</c:choose>
			<button type="submit" class="signupbtn">
				<c:out value="${find}" />
			</button>
		</div>
	</form>
	<c:import url="fragment/footer.jsp"></c:import>
</body>
</html>