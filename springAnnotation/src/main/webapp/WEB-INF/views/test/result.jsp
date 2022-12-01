<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인창</title>
</head>
<body>
<!-- 
	<h2>아이디: ${id }</h2>
	<h2>비밀번호: ${pwd }</h2>
	<h2>비밀번호: ${email }</h2>
 -->
 	<h2>아이디: ${memInfo.id }</h2>
	<h2>비밀번호: ${memInfo.pwd }</h2>
	<h2>비밀번호: ${memInfo.email }</h2>
</body>
</html>