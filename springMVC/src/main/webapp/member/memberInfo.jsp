<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2 align="center">회원정보</h2>
	<table align="center" border="1">
		<tr>
			<td>아이디</td><td>비밀번호</td><td>이름</td><td>이메일</td>
		</tr>
		<tr>
			<td>${id }</td>
			<td>${pwd }</td>
			<td>${name }</td>
			<td>${email }</td>
		</tr>
	</table>
</body>
</html>