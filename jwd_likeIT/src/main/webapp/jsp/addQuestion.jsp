<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="question.text" bundle="${lc}" var="text" />
<fmt:message key="question.send" bundle="${lc}" var="send" />
<fmt:message key="question.cancel" bundle="${lc}" var="cancel" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>
	<form action="${pageContext.request.contextPath}/Controller"
		method="post">
		<input type="hidden" name="command" value="ASK" />
		<div class="container">
			<label><b><c:out value="${text}" /></b></label> <input type="text"
				name="text">
		</div>
		<div class="clearfix">
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
				<c:out value="${send}" />
			</button>
		</div>
	</form>
	<c:import url="fragment/footer.jsp"></c:import>
</body>
</html>