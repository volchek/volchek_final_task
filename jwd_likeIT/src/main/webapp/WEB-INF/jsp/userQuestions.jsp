<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="likeitTagLib" prefix="ct"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="question.no_questions" bundle="${lc}" var="warning" />
<fmt:message key="question.author_info" bundle="${lc}"
	var="question_info" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
</head>
<body>
	<c:import url="jsp/fragment/header.jsp"></c:import>
	<c:import url="jsp/menu.jsp"></c:import>

	<div class="${sessionScope.user ? 'warning' : 'hide'}">
		<c:out value="${warning}" />
	</div>

	<div class="question-container myquestions clearfix">
		<c:forEach var="item" items="${ requestScope.question_list}">
			<form action="Controller" method="get" class="clearfix"
				id='user_question${item.id}'>
				<input type="hidden" name="command" value="FIND_QUESTION_BY_ID" />
				<input type="hidden" name="question_id" value="${item.id}" />
				<div class="mark clearfix">
					<span class="tag"><ct:mark averageMark="${item.averageMark}" /></span>
				</div>
				<div class="question clearfix">
					<h2>
						<c:set value='user_question${item.id}' var="elem_id" />
						<a
							href='javascript:document.getElementById("${elem_id}").submit()'>${item.title}</a>
					</h2>
					<div>
						<ct:keyword cssClass="tag" keywordList="${item.languages}" />
					</div>
					<div>
						<ct:keyword cssClass="tag" keywordList="${item.tags}" />
					</div>
					<p>${item.text}</p>
					<p class="date">
						<ct:date date="${item.creationDate}" text="${question_info}"
							format="dd-MM-yyyy" />
					</p>
				</div>
			</form>
		</c:forEach>
	</div>

	<c:import url="jsp/fragment/footer.jsp"></c:import>

	<script src="${pageContext.request.contextPath}/js/highlight.pack.js"></script>
	<script>
		hljs.configure({
			tabReplace : '    '
		})
		hljs.initHighlightingOnLoad();
	</script>
</body>
</html>