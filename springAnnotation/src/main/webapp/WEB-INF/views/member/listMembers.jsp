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
	<h2 align="center">회원정보</h2>
	<table border="1" align="center" width="70%">
		<tr align="center" bgcolor="#ccc">
			<th>아이디</th><th>비밀번호</th><th>이름</th><th>이메일</th><th>가입일자</th><th>삭제</th><th>수정</th>
		</tr>
		<c:forEach var="member" items="${memberList }">
		 	<tr>
		 		<td>${member.id }</td>
		 		<td>${member.pwd }</td>
		 		<td>${member.name }</td>
		 		<td>${member.email }</td>
		 		<td><fmt:formatDate value="${member.joinDate }" pattern="yyyy-mm-dd"/></td>
		 		<td>
		 			<a href="${contextPath }/member/removeMember.do?id=${member.id}">삭제</a>
		 		</td>
		 		<td>
		 			<a href="${contextPath }/member/modMember.do?id=${member.id}">수정</a>
		 		</td>
		 	</tr>
		</c:forEach>
	</table>
	<h3 align="center"><a href="${contextPath }/member/memberForm.do">회원가입</a></h3>
</body>
</html>