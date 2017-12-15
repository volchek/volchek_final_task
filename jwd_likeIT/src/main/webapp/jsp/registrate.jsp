<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>LikeIT</title>
	<link rel="stylesheet" type="text/css" href="../css/styles.css">
<body>
<!-- <div class="header">Регистрация</div>  -->	
	<form action="../Controller" method="post">
		<input type="hidden" name="command" value="REGISTRATE" />
		
		<div class="container">
		    <label><b>Фамилия*</b></label>
		    <input type="text" name="surname" required>
		
		    <label><b>Имя*</b></label>
		    <input type="text" name="name" required>
		
		    <label><b>Дата рождения</b></label>
		    <input type="date" name="date" value="1970-01-01">

		    <label><b>Статус</b></label>
		    <select name="status">
		    	<option value="none">------</option>
			    <option value="student">студент</option>
    			<option value="trainer">тренер</option>
    			<option value="guru">гуру</option>
			  </select>
		    		    		
<!-- 		<label><b>Email</b></label>
		    <input type="text" name="email" required>
 -->		    
		    <label><b>Логин*</b></label>
		    <input type="text" name="login" required>
		
		    <label><b>Пароль*</b></label>
    		<input type="password" name="password" required>
		
		    <label><b>Повторите пароль*</b></label>
    		<input type="password" name="psw-repeat" required>
    	</div>
    	<div class="clearfix">
     		<button type="button"  class="cancelbtn" onclick="location.href='../index.jsp'">Отмена</button>
      		<button type="submit" class="signupbtn">Завершить</button>
    	</div>
	</form>
</body>
</html>