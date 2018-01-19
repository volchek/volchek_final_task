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
<fmt:message key="answer.get_answer" bundle="${lc}" var="send" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/pell.css">
<body>
	<c:import url="fragment/header.jsp" />
	<c:import url="menu.jsp" />
	<c:out value="${requestScope.question.id}" />
	<c:out value="${requestScope.question.title}" />
	<c:out value="${requestScope.question.text}" />
	<c:out value="${requestScope.question.languages}" />
	<c:out value="${requestScope.question.tags}" />

	<div class="all-content">
		<div>
			<div class="question-container clearfix">
				<div class="mark clearfix">
					<div class="number">5</div>
					<form action="#" method="post" class="clearfix">
						<input type="hidden" name="command" value="SET_MARK" /> <select
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
					<h1>Title</h1>
					<div>
					<span class="lang">Lang1</span><span class="lang">Lang2</span><span class="lang">Lang3</span>
					</div>
					<div>
					<span class="tag">Tag1</span><span class="tag">Tag2</span><span class="tag">Tag3</span>				
					</div>
					<p>Text... Text... Text...</p>
					<p class="date">Вопрос задан: Time</p>
					<p class="author">Author</p>
				</div>
			</div>
			<div class="question-container">
				<div class="mark clearfix">
					<div class="number">4</div>
					<form action="#" method="post" class="clearfix">
						<input type="hidden" name="command" value="SET_MARK" /> <select
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
					<p>LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM
						LOREM IPSUMLOREM IPSUM LOREM IPSUM LOREM IPSUMLOREM IPSUM LOREM
						IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM LOREM IPSUM v v LOREM
						IPSUM</p>
					<p class="date">Time</p>
					<p class="author">Author</p>
				</div>
			</div>
			<div class="answer">
				<p>Ваш ответ</p>
				<form action="${pageContext.request.contextPath}/Controller"
					method="post">
					<input type="hidden" name="command" value="GET_ANSWER" />
					<c:if test="${empty current_user.login}">
						<div>
							<div class="content">
								<div id="pell" class="pell"></div>
								<div id="text-output" style="display: none"
									onKeyUp="addCodeTag(replacePreTags())"></div>
								<textarea name="answer_text" id="answer" style="display: none"></textarea>
							</div>

							<button type="submit" class="signupbtn"
								onclick="location.href='afterLogIn.jsp'">
								<c:out value="${send}" />
							</button>
						</div>
					</c:if>
				</form>
			</div>
		</div>
	</div>
	<div class="links clearfix">
		<p>info</p>
	</div>
	<c:import url="fragment/footer.jsp"></c:import>
	<script src="${pageContext.request.contextPath}/js/pell.js"></script>
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