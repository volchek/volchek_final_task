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
<fmt:message key="question.text" bundle="${lc}" var="text" />
<fmt:message key="question.author_info" bundle="${lc}"
	var="question_info" />
<fmt:message key="answer.get_answer" bundle="${lc}" var="send" />
<fmt:message key="question.answer" bundle="${lc}" var="get_answer" />
<fmt:message key="language.label" bundle="${lc}" var="label" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/pell.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
</head>
<body>
	<c:import url="fragment/header.jsp" />
	<c:import url="menu.jsp" />

	<div class="all-content clearfix">
		<div class="clearfix">
			<div class="question-container clearfix">
				<div class="mark clearfix">
					<div class="number">
						<ct:mark averageMark="${requestScope.question.averageMark}" />
					</div>
					<c:if
						test="${(not empty sessionScope.current_user) and (sessionScope.current_user.login != requestScope.question.authorLogin) }">
						<c:set value="true" var="display_mark" />
					</c:if>
					<c:out value="${test}" />
					<form action="${pageContext.request.contextPath}/Controller"
						method="get" class="clearfix">
						<input type="hidden" name="command" value="EVALUATE_QUESTION" />
						<input type="hidden" name="question"
							value="${requestScope.question.id}" /> <select name="mark"
							class="${display_mark ? 'styled-select pink rounded clearfix' : 'nonvisible'}">
							<option>5</option>
							<option>4</option>
							<option>3</option>
							<option>2</option>
							<option>1</option>
							<option>0</option>
						</select>
						<button type="submit"
							class="${display_mark ? 'clearfix' : 'nonvisible'}">OK</button>
					</form>
				</div>
				<div class="question clearfix">
					<h1>
						<c:out value="${ requestScope.question.title }" />
					</h1>
					<div>
						<ct:keyword cssClass="tag"
							keywordList="${requestScope.question.tags}" />
					</div>
					<div>
						<ct:keyword cssClass="lang"
							keywordList="${requestScope.question.languages}" />
					</div>
					<p id="text">${ requestScope.question.text }</p>
					<p class="date">
						<ct:date date="${requestScope.question.creationDate}"
							text="${question_info}" format="dd-MM-yyyy" />
					</p>
					<p class="author">
						<a
							href="${pageContext.request.contextPath}/users/${requestScope.question.authorLogin }">
							<c:out value="${ requestScope.question.authorLogin }" />
						</a>
					</p>
				</div>
			</div>

			<c:forEach var="answer" items="${requestScope.question.answers}">
				<div class="question-container clearfix">
					<div class="mark clearfix">
						<ct:mark averageMark="${answer.averageMark}" />
						<c:set value="false" var="display_answer" />
						<c:if
							test="${(not empty sessionScope.current_user) 
							and (sessionScope.current_user.login != answer.authorLogin)}">
							<c:set value="true" var="display_answer" />
						</c:if>
						<form action="${pageContext.request.contextPath}/Controller"
							method="get" class="clearfix">
							<input type="hidden" name="command" value="EVALUATE_ANSWER" /> <input
								type="hidden" name="answer" value="${answer.id}" /> <select
								name="mark"
								class="${display_answer ? 'styled-select pink rounded clearfix' : 'nonvisible'}">
								<option>5</option>
								<option>4</option>
								<option>3</option>
								<option>2</option>
								<option>1</option>
								<option>0</option>
							</select>
							<button type="submit"
								class="${display_answer ? 'clearfix' : 'nonvisible'}">OK</button>
						</form>
					</div>
					<div class="question clearfix">
						<p>${answer.text}</p>
						<p class="date">
							<ct:date date="${answer.creationDate}" format="dd-MM-yyyy" />
						</p>
						<p class="author">
							<a
								href="${pageContext.request.contextPath}/users/${answer.authorLogin}">
								${answer.authorLogin} </a>
						</p>
					</div>
				</div>
			</c:forEach>

			<c:if
				test="${(not empty sessionScope.current_user) and (sessionScope.current_user.login != requestScope.question.authorLogin)}">
				<div class="answer">
					<p>
						<c:out value="${get_answer}" />
					</p>
					<form action="${pageContext.request.contextPath}/Controller"
						method="post">
						<input type="hidden" name="command" value="GET_ANSWER" /> <input
							type="hidden" name="question_id"
							value="${requestScope.question.id}" />
						<div>
							<div class="content">
								<div id="pell" class="pell"></div>
								<div id="text-output" style="display: none"
									onKeyUp="addCodeTag(replacePreTags())"></div>
								<textarea name="answer_text" id="question" style="display: none"></textarea>
							</div>

							<button type="submit" class="signupbtn"
								onclick="location.href='afterLogIn.jsp'">
								<c:out value="${send}" />
							</button>
						</div>
					</form>
				</div>
			</c:if>
		</div>
	</div>
	<div class="links question-container clearfix">
		<c:if test="${not empty requestScope.languages}">
			<p>
				<c:out value="${label}" />
			</p>
			<c:forEach var="index" begin="1" end="5">
				<div class="lang">
					<c:set var="language" value="${requestScope.languages[index - 1]}" />

					<c:choose>
						<c:when test="${fn:contains(language, '++')}">
							<c:set var="correct_lang"
								value="${fn:replace(language, '+', '%2B')}" />
							<a
								href="${pageContext.request.contextPath}/Controller?command=FIND_QUESTION_BY_LANGUAGE&language=${correct_lang}">
								<c:out value="${language}" />
							</a>
						</c:when>
						<c:when test="${fn:contains(language, '#')}">
							<c:set var="correct_lang"
								value="${fn:replace(language, '#', '%23')}" />
							<a
								href="${pageContext.request.contextPath}/languages/${correct_lang}">
								<c:out value="${language}" />
							</a>
						</c:when>
						<c:otherwise>
							<a
								href="${pageContext.request.contextPath}/languages/${language}">
								<c:out value="${language}" />
							</a>
						</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
		</c:if>
	</div>

	<c:import url="fragment/footer.jsp"></c:import>

	<script src="${pageContext.request.contextPath}/js/pell.js"></script>
	<script src="${pageContext.request.contextPath}/js/add_question.js"></script>
	<script src="${pageContext.request.contextPath}/js/highlight.pack.js"></script>
	<script>
		hljs.configure({
		  tabReplace: '    '
		})
		hljs.initHighlightingOnLoad();
	</script>
	<script>
      function ensureHTTP (str) {
        return /^https?:\/\//.test(str) && str || `http://${str}`
      }
      var editor = window.pell.init({
        element: document.getElementById('pell'),
        styleWithCSS: false,
        actions: [
          'bold',
          'underline',
          'italic',
          {
            name: 'zitalic',
            icon: 'Z',
            title: 'Zitalic',
            result: () => window.pell.exec('italic')
          },
          'heading1',
          'heading2',
          'paragraph',
          {
        	  name: 'quote',
        	  icon: '<<>>'
          },
          {
        	  name: 'olist',
        	  icon: '(1)'
          },
          {
        	  name: 'ulist',
        	  icon: '(*)'
          },
          'code',
          {
            name: 'image',
            result: () => {
              const url = window.prompt('Enter the image URL')
              if (url) window.pell.exec('insertImage', ensureHTTP(url))
            }
          },
          {
            name: 'link',
            result: () => {
              const url = window.prompt('Enter the link URL')
              if (url) window.pell.exec('createLink', ensureHTTP(url))
            }
          }
        ],
        classes: {
            actionbar: 'pell-actionbar',
            button: 'pell-button',
            content: 'pell-content'
          },
        onChange: function (html) {
        	onChange: html => console.log(html);
         document.getElementById('text-output').innerHTML = html;
         var evt = document.createEvent("KeyboardEvent");
         evt.initEvent('keyup', true, true);
         document.getElementById('text-output').dispatchEvent(evt);
        }
      })
    </script>
</body>
</html>