<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!-- 서블릿의 주소를 쓸 때 편리하게 쓰기 위해 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 창</title>
</head>
<body>
	<form action="${contextPath }/member/addMember.do" method="post">
		<h2 align="center"> 회원 가입 </h2>
		<table align="center" >
			<tr>
				<td width="100">아이디</td>
				<td width="250"><input type="text" name="id"></td>
			</tr>
			<tr>
				<td width="100">비밀번호</td>
				<td width="250"><input type="password" name="pwd"></td>
			</tr>
			<tr>
				<td width="100">이름</td>
				<td width="250"><input type="text" name="name"></td>
			</tr>
			<tr>
				<td width="100">이메일</td>
				<td width="250"><input type="text" name="email"></td>
			</tr>
			
			<tr>
				<td width="100">&nbsp;</td>
				<td width="250">
					<input type="submit" value="가입하기"/>
					<input type="reset" value="다시입력"/>
				</td>
			</tr>
		</table>
		
	</form>
</body>
</html>