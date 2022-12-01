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
	<table width="100%">
		<tr>
			<td>
				<a href="${contextPath}/main.do">
					<img src="${contextPath }/resources/images/16.png" alt="">
				</a>
			</td>
			<td>
				<h1>연습 페이지</h1>
			</td>
			<td>
				<c:choose>
					<c:when test="${ isLogOn == true && member != null }">
						<h3>횐영합니다 ${member.name }님!</h3>				
						<a href="${contextPath}/member/logout.do">로그아웃</a>
					</c:when>
					<c:otherwise>			
						<a href="${contextPath}/member/loginForm.do">로그인</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
</body>
</html>