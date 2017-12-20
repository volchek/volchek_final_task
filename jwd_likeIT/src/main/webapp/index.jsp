<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="main_menu.login" bundle="${lc}" var="login" />
<fmt:message key="main_menu.registration" bundle="${lc}"
	var="registration" />
<fmt:message key="actions.questions" bundle="${lc}" var="questions" />
<fmt:message key="actions.answers" bundle="${lc}" var="answers" />
<fmt:message key="actions.find" bundle="${lc}" var="find" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<div class="header">LikeIT</div>
	<ul id="menu">
		<li><a href="jsp/login.jsp"><c:out value="${login}" /></a></li>
		<li><a href="jsp/register.jsp"><c:out value="${registration}" /></a></li>
		<li></li>
	</ul>
	<ul id="lang">
		<li>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="CHANGE_LANG" /> <input
					type="hidden" name="address"
					value="${pageContext.request.requestURL}" /> <input type="hidden"
					name="query" value="${pageContext.request.queryString}" />
				<button type="submit" name="local" value="ru_RU">RU</button>
			</form>
		</li>
		<li>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="CHANGE_LANG" /> <input
					type="hidden" name="address"
					value="${pageContext.request.requestURL}" /> <input type="hidden"
					name="query" value="${pageContext.request.queryString}" />
				<button type="submit" name="local" value="en_US">EN</button>
			</form>
		</li>
	</ul>

	<div class="actions">
		<div class="question">
			<a href="#"><c:out value="${questions}" /></a>
		</div>
		<div class="answers">
			<a href="#"><c:out value="${answers}" /></a>
		</div>
		<div class="find">
			<a href="jsp/findUser.jsp"><c:out value="${find}" /></a>
		</div>
	</div>
</body>
</html>