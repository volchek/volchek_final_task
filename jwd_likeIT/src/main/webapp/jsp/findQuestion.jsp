<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="find.btn.find" bundle="${lc}" var="find" />
<fmt:message key="find.id_question" bundle="${lc}" var="id" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
<body>
	<c:import url="fragment/header.jsp"></c:import>
	<c:import url="menu.jsp"></c:import>

	<div class="finder">
		<form action="${pageContext.request.contextPath}/Controller"
			method="get">
			<input type="hidden" name="command" value="FIND_QUESTION_BY_ID" />
			<div class="container">
				<label><c:out value="${id}" /><input type="text" name="question_id"
					pattern="[0-9]+" required></label>
				<button type="submit" class="signupbtn">
					<c:out value="${find}" />
				</button>
			</div>
		</form>
	</div>
</body>
</html>