<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>사이드 메뉴</h2>
	<h3><a href="${contextPath }/member/listMembers.do" style="color: #666">회원관리</a></h3>
	<h3>상품목록</h3>
	<h3>커뮤니티</h3>
	<h3><a href="${contextPath }/board/listArticles.do" style="color: #666">게시판관리</a></h3>
</body>
</html>