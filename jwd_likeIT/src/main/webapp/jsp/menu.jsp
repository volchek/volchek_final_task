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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<div class="menu clearfix">
	<div class="nav">
		<c:choose>
			<c:when test="${not empty sessionScope.current_user}">
				<form action="${pageContext.request.contextPath}/PersonalData"
					method="post" id="form_edit">
				</form>
				<form action="${pageContext.request.contextPath}/Controller"
					method="post" id="form_logout">
					<input type="hidden" name="command" value="LOGOUT" />
				</form>

				<span class="user"><c:out value="${current_user.login}"></c:out></span>
				<a href='javascript:document.getElementById("form_edit").submit()'><c:out
						value="${edit}" /></a>
				<a href='javascript:document.getElementById("form_logout").submit()'><c:out
						value="${logout}" /></a>

			</c:when>
			<c:otherwise>
				<a href="${pageContext.request.contextPath}/jsp/login.jsp"> <c:out
						value="${login}" /></a>
				<a href="${pageContext.request.contextPath}/jsp/register.jsp"> <c:out
						value="${registration}" /></a>
			</c:otherwise>
		</c:choose>

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
