<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<fmt:message key="warning" bundle="${lc}" var="reg_warning" />

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

	<c:if test="${empty sessionScope.current_user}">
		<h3>
			<c:out value="${reg_warning}" />
		</h3>
	</c:if>

	<div class="question-container myanswers clearfix">
		<c:choose>
			<c:when test="${ fn:length(requestScope.question_list)==0 }">
				<c:out value="${warning}" />
			</c:when>
			<c:otherwise>
				<c:forEach var="item" items="${ requestScope.question_list}">
					<div class="question clearfix">
						<div>
							<ct:keyword cssClass="tag" keywordList="${item.languages}" />
							<ct:keyword cssClass="tag" keywordList="${item.tags}" />
						</div>
						<h2>
							<a
								href="${pageContext.request.contextPath}/Controller?command=FIND_QUESTION_BY_ID&question_id=${item.id}">
								<c:out value="${item.title}" />
							</a>
						</h2>
						<div>
							<span class="date"> <ct:date date="${item.creationDate}"
									text="${signed}" format="dd-MM-yyyy" />
							</span> <span class="author"> <a
								href="${pageContext.request.contextPath}/Controller?command=FIND_USER_BY_LOGIN&login=${item.authorLogin}">
									<c:out value="${item.authorLogin}" />
							</a>
							</span>
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
				</c:forEach>
			</c:otherwise>
		</c:choose>
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