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
<title>로그인 창</title>
</head>
<body>
	<form action="${contextPath}/member/login.do" methood="post" name="frmLogin">
		<table>
			<tr>
				<td>
					아이디: <input type="text" name="id">
				</td>
			</tr>
			<tr>
				<td>
					비밀번호: <input type="password" name="pwd">
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