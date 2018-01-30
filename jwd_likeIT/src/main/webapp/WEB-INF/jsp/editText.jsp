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
	<c:import url="jsp/fragment/header.jsp" />
	<c:import url="jsp/menu.jsp" />

	<div class="all-content clearfix">
		<div class="clearfix">
			<div class="question-container clearfix">
				<div class="mark clearfix">
					<div class="number">
						<ct:mark averageMark="${requestScope.question.averageMark}" />
					</div>
				</div>
				<div class="question clearfix">
					<h1>
						<c:out value="${requestScope.question.title}" />
					</h1>
					<div>
						<ct:keyword cssClass="tag"
							keywordList="${requestScope.question.tags}" />
					</div>
					<div>
						<ct:keyword cssClass="lang"
							keywordList="${requestScope.question.languages}" />
					</div>
					<p id="text">${requestScope.question.text}</p>
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
				<c:choose>
					<c:when
						test="${not empty sessionScope.current_user and sessionScope.current_user.login == answer.authorLogin and answer.id == requestScope.answer_id }">
						<c:set var="user_text" value="${answer.text}" />
						<div class="answer">
							<form action="${pageContext.request.contextPath}/Controller"
								method="post">
								<input type="hidden" name="command" value="FINISH_TO_EDIT_ANSWER" />
								<input type="hidden" name="answer_id" value="${answer.id}" />

								<div>
									<div class="content">
										<div id="pell" class="pell"></div>
										<div id="text-output" style="display: none"
											onKeyUp="addCodeTag(replacePreTags())"></div>
										<textarea name="answer_text" id="question"
											style="display: none"></textarea>
									</div>

									<button type="submit" class="signupbtn"
										onclick="location.href='afterLogIn.jsp'">
										<c:out value="${send}" />
									</button>
								</div>
							</form>
						</div>
				</c:when>
					<c:otherwise>
						<div class="question-container clearfix">
							<div class="mark clearfix">
								<ct:mark averageMark="${answer.averageMark}" />
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
					</c:otherwise>
				</c:choose>
			</c:forEach>

		</div>
	</div>

	<c:import url="jsp/fragment/footer.jsp"></c:import>

	<script src="${pageContext.request.contextPath}/js/pell.js"></script>
	<script src="${pageContext.request.contextPath}/js/add_question.js"></script>
	<script src="${pageContext.request.contextPath}/js/highlight.pack.js"></script>
	<script>
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
    <script>
    function fillPell(text){
    	document.getElementsByClassName("pell-content")[0].innerHTML = text;
    };
    window.onload = fillPell("${user_text}");
    </script>
</body>
</html>