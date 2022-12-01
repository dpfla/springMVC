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
<title>회원수정 창</title>
</head>
<body>
	<form action="${contextPath }/member/updateMember.do" method="post">
		<h2 align="center">회원등록</h2>
		<table align="center">
			<tr>
				<td width="200">
					<p align="right">아이디</p>
				</td>
				<td width="200">
					<input type="text" value="${member.id }" disabled>
					<input type="hidden" name="id" value="${member.id }" >
				</td>
			</tr>
			<tr>
				<td width="200">
					<p align="right">비밀번호</p>
				</td>
				<td width="200">
					<input type="password" name="pwd" value="${member.pwd }">
				</td>
			</tr>
			<tr>
				<td width="200">
					<p align="right">이름</p>
				</td>
				<td width="200">
					<input type="text" name="name" value="${member.name }">
				</td>
			</tr>
			<tr>
				<td width="200">
					<p align="right">이메일</p>
				</td>
				<td width="200">
					<input type="text" name="email" value="${member.email }">
				</td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<input type="submit" value="수정하기">
					<input type="reset" value="다시입력">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>