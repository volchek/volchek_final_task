<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="menu.edit" bundle="${lc}" var="edit" />
<fmt:message key="menu.logout" bundle="${lc}" var="logout" />
<fmt:message key="main_menu.login" bundle="${lc}" var="login" />
<fmt:message key="main_menu.registration" bundle="${lc}"
	var="registration" />
<fmt:message key="main_menu.index" bundle="${lc}" var="index" />

<div class="menu clearfix">
	<div class="nav">
		<a href="login.jsp"><c:out value="${login}" /></a> <a
			href="../index.jsp"><c:out value="${index}" /></a>

		<div class="menu-lang">
			<form action="${pageContext.request.contextPath}/Controller"
				method="post">
				<input type="hidden" name="command" value="CHANGE_LANG" /> <input
					type="hidden" name="address"
					value="${pageContext.request.requestURL}" /> <input type="hidden"
					name="query" value="${pageContext.request.queryString}" />
				<button type="submit" name="local" value="ru_RU">RU</button>
			</form>
			<form action="${pageContext.request.contextPath}/Controller"
				method="post">
				<input type="hidden" name="command" value="CHANGE_LANG" /> <input
					type="hidden" name="address"
					value="${pageContext.request.requestURL}" /> <input type="hidden"
					name="query" value="${pageContext.request.queryString}" />
				<button type="submit" name="local" value="en_US">EN</button>
			</form>
		</div>
	</div>
</div>
