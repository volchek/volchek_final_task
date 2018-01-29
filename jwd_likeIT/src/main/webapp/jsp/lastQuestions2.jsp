<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="likeitTagLib" prefix="ct"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="answer.score" bundle="${lc}" var="score" />
<fmt:message key="answer.count" bundle="${lc}" var="count" />


<div class="feed">
<table>
	<c:forEach var="item" items="${ requestScope.question_list}">
		<tr>
			<td><div class="mark"><ct:mark
					averageMark="${item.averageMark}" /></div>
			</td>
			<td>
				<div class="question-container">
					<a
						href="${pageContext.request.contextPath}/Controller?command=FIND_QUESTION_BY_ID&question_id=${item.id}">${item.title}
					</a>
				</div>
				<div class="question-container clearfix">
					<ct:keyword cssClass="lang" keywordList="${item.languages}" />
					<ct:keyword cssClass="lang" keywordList="${item.tags}" />
				</div>
			</td>
			<td>
				<div class="author">
				<a
						href="${pageContext.request.contextPath}/Controller?command=FIND_USER_BY_LOGIN&login=${item.authorLogin}">
				<c:out value="${item.authorLogin}" />
					</a>
				
				</div>
			</td>
			<td>
				<div class="date">
					<ct:date date="${item.creationDate}" text="${question_info}"
						format="dd-MM-yyyy" />
				</div>
			</td>
			<td>
				<div class="text">
					<c:out value="${count}" />${item.countAnswers}</div>
			</td>
		</tr>
	</c:forEach>
</table>
</div>