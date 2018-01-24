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
<fmt:message key="answer.no_answers" bundle="${lc}" var="warning" />
<fmt:message key="answer.author_info" bundle="${lc}" var="signed" />
<fmt:message key="answer.score" bundle="${lc}" var="score" />
<fmt:message key="answer.vote" bundle="${lc}" var="vote" />
<fmt:message key="answer.answer" bundle="${lc}" var="answer" />

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

	<div class="question-container myanswers clearfix">

		<c:forEach var="item" items="${ requestScope.question_list}">

			<form action="Controller" method="get" class="clearfix"
				id='user_question${item.id}'>
				<input type="hidden" name="command" value="FIND_QUESTION_BY_ID" />
				<input type="hidden" name="question_id" value="${item.id}" />

				<div class="question clearfix">
					<div>
						<ct:keyword cssClass="tag" keywordList="${item.languages}" />
						<ct:keyword cssClass="tag" keywordList="${item.tags}" />
					</div>
					<h2>
						<c:set value='user_question${item.id}' var="elem_id" />
						<a
							href='javascript:document.getElementById("${elem_id}").submit()'>${item.title}</a>
					</h2>
					<div>
						<span class="date"> <ct:date date="${item.creationDate}"
								text="${signed}" format="dd-MM-yyyy" /></span> <span class="author">${item.authorLogin}</span>
					</div>
				</div>

				<c:forEach var="user_answer" items="${item.answers}">
					<div class="answer">
						<div>
							<c:out value="${answer}" />
						</div>
						<div class="text">${user_answer.text}</div>
						<div>
							<c:out value="${score}" />
							<span class="mark average"> <ct:mark
									averageMark="${user_answer.averageMark }" />
							</span>
							<c:out value="${vote}" />
							<span>${user_answer.markCount}</span>
						</div>
					</div>
				</c:forEach>
				<br>
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