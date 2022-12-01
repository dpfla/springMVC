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
<title>회원가입 창</title>
</head>
<body>
	<form action="${contextPath}/member/memberInfo.do" methood="post" name="frmMember">
		<table>
			<tr>
				<td width="70">아이디: </td>
				<td>
					<input type="text" name="id">
				</td>
			</tr>
			<tr>
				<td width="70">비밀번호: </td>
				<td>
					<input type="password" name="pwd">
				</td>
			</tr>
			<tr>
				<td width="70">이름: </td>
				<td>
					<input type="text" name="name">
				</td>
			</tr>
			<tr>
				<td width="70">이메일: </td>
				<td>
					<input type="text" name="email">
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="로그인">
				</td>
				<td>	
					<input type="reset" value="다시입력">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>