<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="lc" />
<fmt:message key="question.title" bundle="${lc}" var="title" />
<fmt:message key="question.text" bundle="${lc}" var="text" />
<fmt:message key="question.languages" bundle="${lc}" var="languages" />
<fmt:message key="question.tags" bundle="${lc}" var="tags" />
<fmt:message key="question.clear" bundle="${lc}" var="clear" />
<fmt:message key="question.send" bundle="${lc}" var="send" />
<fmt:message key="question.tooltip1" bundle="${lc}" var="tooltip1" />
<fmt:message key="question.tooltip2" bundle="${lc}" var="tooltip2" />
<fmt:message key="question.tooltip3" bundle="${lc}" var="tooltip3" />
<fmt:message key="question.tooltip4" bundle="${lc}" var="tooltip4" />

<c:set var="lang_list" value="${sessionScope.languages}" />
<c:set var="tag_list" value="${sessionScope.tags}" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LikeIT</title>
<link rel="stylesheet" href=“css/normalize.css“>
<link href="https://fonts.googleapis.com/css?family=Work+Sans"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Raleway"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/pell.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/awesomplete.css">
</head>

<body>
	<c:import url="jsp/fragment/header.jsp"></c:import>
	<c:import url="jsp/menu.jsp"></c:import>

	<div class="tooltip">
		<p>
			<c:out value="${tooltip1}" />
		</p>
		<p>
			<c:out value="${tooltip2}" />
		</p>
		<p>
			<c:out value="${tooltip3}" />
		</p>
		<p>
			<c:out value="${tooltip4}" />
		</p>
	</div>

	<form action="${pageContext.request.contextPath}/Controller"
		method="post" class="question">
		<input type="hidden" name="command" value="ADD_QUESTION" /> 

		<div>
			<label><c:out value="${title}" /> <br> <input
				type="text" name="title" id="title" required /> </label>
			<br>
			<label ><c:out value="${languages}" />
			<br>
			<input type="text" name="language" class="tag" required id="lang1" style="display: inline" />
			<input type="text" name="language" class="tag" id="lang2" style="display: none" />
			<input type="text" name="language" class="tag" id="lang3" style="display: none" />
			<a href="#" id="add-language" class="add-tag" onclick="addFields('add-language', 'remove-language', 'lang2', 'lang3')">
				+
			</a>				
			<a href="#" id="remove-language" class="add-tag" style="display:none" onclick="removeFields('add-language', 'remove-language', 'lang2', 'lang3')">
				-
			</a>				
			</label>
			<br>
			<label><c:out value="${tags}" />
				<br>
			<input type="text" name="tag" id="tag1" class="tag" style="display: inline" required />
			<input type="text" name="tag" id="tag2" class="tag" style="display: none" />
			<input type="text" name="tag" id="tag3" class="tag" style="display: none" />						
			<a href="#" id="add-tag" class="add-tag"
					onclick="addFields('add-tag', 'remove-tag', 'tag2', 'tag3')">
					+
			</a>
			<a href="#" id="remove-tag" class="add-tag" style="display:none" 
					onclick="removeFields('add-tag', 'remove-tag', 'tag2', 'tag3')">
					-
			</a>
			
			</label>
			<br>
			<label><c:out value="${text}" /></label> <br>
		</div>
		<div class="content" >
			<div id="pell" class="pell" ></div>
			<div id="text-output" style="display: none" onKeyUp="addCodeTag(replacePreTags())"></div>
			<textarea name="question" id="question" style="display: none" ></textarea>
		</div>


		<div class="buttons">
			<button type="submit" class="cancelbtn" onclick="resetFields()">
				<c:out value="${clear}" />
			</button>
			<button type="submit" class="signupbtn">
				<c:out value="${send}" />
			</button>
		</div>
	</form>
	<c:import url="jsp/fragment/footer.jsp"></c:import>
	<script src="${pageContext.request.contextPath}/js/add_question.js"></script>
	<script src="${pageContext.request.contextPath}/js/awesomplete.js"></script>
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
    <script>
		var langList = ${lang_list};
    	for (var i = 1; i < 4; i++){
    		var id = "lang" + i;
    		var input = document.getElementById(id);
    		new Awesomplete(input, { list : ${lang_list} });
    	}
    	for (var i = 1; i < 4; i++){
    		var id = "tag" + i;
    		var input = document.getElementById(id);
    		new Awesomplete(input, { list: ${tag_list} });
    	}
    </script>
</body>
</html>