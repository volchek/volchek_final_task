<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="question.text" bundle="${lc}" var="text" />
<fmt:message key="question.author_info" bundle="${lc}" var="question_info" />
<fmt:message key="answer.get_answer" bundle="${lc}" var="send" />
<fmt:message key="question.answer" bundle="${lc}" var="get_answer" />

<fmt:formatDate value = "${requestScope.question.creationDate}" pattern = "dd-MM-yyyy" var="date" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/pell.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
	
<body>
	<c:import url="fragment/header.jsp" />
	<c:import url="menu.jsp" />

	<div class="all-content">
		<div>
			<div class="question-container clearfix">
				<div class="mark clearfix">
					<div class="number">
					<c:choose>
					<c:when test="${empty requestScope.question.averageMark}}">
					&mdash;
					</c:when>
					<c:otherwise>
					<c:out value="${requestScope.question.averageMark}" />
					</c:otherwise>
					</c:choose>
					</div>
					<form action="${pageContext.request.contextPath}/Controller" method="post" class="clearfix">
						<input type="hidden" name="command" value="EVALUATE_QUESTION" />
						<input type="hidden" name="question" value="${requestScope.question.id}" />
						 <select
							name="mark" class="styled-select pink rounded clearfix">
							<option>5</option>
							<option>4</option>
							<option>3</option>
							<option>2</option>
							<option>1</option>
							<option>0</option>
						</select>
						<button type="submit" class="clearfix">OK</button>
					</form>
				</div>
				<div class="question clearfix">
					<h1><c:out value="${ requestScope.question.title }" /></h1>
					<div>
					<c:forEach var="tag" items="${requestScope.question.tags}">
						<span class="lang"><c:out value="${tag}" /></span>
					</c:forEach>
					</div>
					<div>
					<c:forEach var="lang" items="${requestScope.question.languages}">
						<span class="lang"><c:out value="${lang}" /></span>
					</c:forEach>
					</div>
					<p id="text" >${ requestScope.question.text }</p>
					<p class="date"><c:out value="${question_info}" /> <c:out value="${date}" /></p>
					<p class="author"><c:out value="${ requestScope.question.authorLogin }" /></p>
				</div>
			</div>
			
			<c:forEach var="answer" items="${requestScope.question.answers}">				
			<div class="question-container">
				<div class="mark clearfix">
					<c:out value="${not empty answer.averageMark ? answer.averageMark : '—'}"/>

					<form action="#" method="post" class="clearfix">
						<input type="hidden" name="command" value="EVALUATE_ANSWER" />
						<input type="hidden" name="answer" value="${answer.id}" />
						
						<select
							name="mark" class="styled-select pink rounded clearfix">
							<option>5</option>
							<option>4</option>
							<option>3</option>
							<option>2</option>
							<option>1</option>
							<option>0</option>
						</select>
						<button type="submit" class="clearfix">OK</button>
					</form>
				</div>
				<div class="question">
					<p>${answer.text}</p>
					<fmt:formatDate value = "${answer.creationDate}" pattern = "dd-MM-yyyy" var="date" />
					<p class="date">${date}</p>
					<p class="author">${answer.authorLogin}</p>
				</div>
			</div>
			</c:forEach>

			<c:if test="${not empty current_user.login}">
			<div class="answer">
				<p><c:out value="${get_answer}" /></p>
				<form action="${pageContext.request.contextPath}/Controller"
					method="post">
					<input type="hidden" name="command" value="GET_ANSWER" />
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
	<div class="links clearfix">
		<p>C++</p>
		<p>Java</p>
		<p>Python</p>
		<p>JavaScript</p>
	</div>
	<c:import url="fragment/footer.jsp"></c:import>
	<script src="${pageContext.request.contextPath}/js/pell.js"></script>
	<script src="${pageContext.request.contextPath}/js/add_question.js"></script>
	<script src="${pageContext.request.contextPath}/js/highlight.pack.js"></script>
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
	<script>hljs.initHighlightingOnLoad();</script>
	<script>
	   initHighlighting();
	</script>
</body>
</html>