<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>RESULT</title>
  </head>
  <body>
	<div>
		<table border="1" style="margin-left: auto; margin-right: auto">
			<tr>
	 			<td>#</td>
				<td>Title</td>
				<td>Artist</td>
				<td>Country</td>
	 			<td>Company</td>
				<td>Year</td>
				<td>Price</td>
			</tr>
			
			<c:forEach items="${requestScope.cd}" var="cd" >
				<tr>
	 			<td><c:out value="${cd.id}"></c:out></td>
				<td><c:out value="${cd.title}"></c:out></td>
				<td><c:out value="${cd.artist}"></c:out></td>
				<td><c:out value="${cd.country}"></c:out></td>
				<td><c:out value="${cd.company}"></c:out></td>
				<td><c:out value="${cd.year}"></c:out></td>
				<td><c:out value="${cd.price}"></c:out></td>
			</tr>
			</c:forEach>
		</table>
		
		<!-- Show a list of pages -->
		<p style="text-align: center">
		<c:set var="pageCount" value="${pageCount}"/>
		<c:forEach begin="1" end="${pageCount + 1}" var="i">
			<a href="Controller?page=${i}&command=SHOW">${i}</a>
		</c:forEach>
		</p>
	</div>

    <br><br>
    <a href="index.jsp">Choose another parser</a>
  
  </body>
</html>