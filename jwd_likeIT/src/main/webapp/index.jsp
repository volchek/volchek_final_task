<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>LikeIT</title>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<div class="header">LikeIT</div>	
	<ul id="menu">
		<li><a href="jsp/login.jsp">Войти</a></li>
		<li><a href="jsp/registrate.jsp">Зарегистрироваться</a></li>
		<li>
		</li>
	</ul>
	<ul id="lang">
		<li><a href="#">RU</a></li>
		<li><a href="#">EN</a></li>
	</ul>
	
	<div class="actions">
		<div class="question"><a href="#">Задать вопрос</a></div>
		<div class="answers"><a href="#">Просмотреть ответы</a></div>
		<div class="find"><a href="#">Найти участника сети</a></div>
	</div>
	<input type="hidden" name="command" value="log" />
</body>
</html>