<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="actions.questions" bundle="${lc}" var="questions" />
<fmt:message key="actions.answers" bundle="${lc}" var="answers" />
<fmt:message key="actions.find" bundle="${lc}" var="find" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<div class="actions">
		<div class="question">
			<a href="#"><c:out value="${questions}" /></a>
		</div>
		<div class="answers">
			<a href="#"><c:out value="${answers}" /></a>
		</div>
		<div class="find">
			<a href="../jsp/findUser.jsp"><c:out value="${find}" /></a>
		</div>
	</div>
</body>
</html>