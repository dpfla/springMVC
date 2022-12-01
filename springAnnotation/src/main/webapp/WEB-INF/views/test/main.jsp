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
<title>회원목록창</title>
</head>
<body>
	<h1>메인페이지입니다</h1>
	<h2>스프링 애노테이션 실습</h2>
	<h2>xml에서 매핑 정보를 설정하는 것이 아닌 애노테이션으로 설정하는 방법</h2>
	<h2>MainController에서 받은 내용: ${msg }</h2>
</body>
</html>