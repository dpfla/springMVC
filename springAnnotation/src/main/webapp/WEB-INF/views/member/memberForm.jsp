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
<title>회원등록 창</title>
</head>
<body>
	<form action="${contextPath }/member/addMember.do" method="post">
		<h2 align="center">회원등록</h2>
		<table align="center">
			<tr>
				<td width="200">
					<p align="right">아이디</p>
				</td>
				<td width="200">
					<input type="text" name="id">
				</td>
			</tr>
			<tr>
				<td width="200">
					<p align="right">비밃번호</p>
				</td>
				<td width="200">
					<input type="password" name="pwd">
				</td>
			</tr>
			<tr>
				<td width="200">
					<p align="right">이름</p>
				</td>
				<td width="200">
					<input type="text" name="name">
				</td>
			</tr>
			<tr>
				<td width="200">
					<p align="right">이메일</p>
				</td>
				<td width="200">
					<input type="text" name="email">
				</td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<input type="submit" value="가입하기">
					<input type="reset" value="다시입력">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>