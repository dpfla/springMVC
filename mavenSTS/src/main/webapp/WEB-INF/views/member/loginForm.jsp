<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="result" value="${param.result }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인창</title>
<c:choose>
	<c:when test="${result == 'loginFailed' }">
		<script type="text/javascript">
			window.onload=function() { 
				alert("아이디나 비밀번화 틀립니다. 다시 로그인하세요");
			}
		</script>
	</c:when>
</c:choose>
</head>
<body>
	<h2 align="center">로그인창</h2>
	<form action="${contextPath }/member/login.do" method="post">
		<table width="80%" align="center">
			<tr>
				<td width="45%" align="right"> 아이디 </td>
				<td align="left"> <input type="text" name="id"> </td>
			</tr>
			<tr>
				<td width="45%" align="right"> 비밀번호 </td>
				<td align="left"> <input type="password" name="pwd"> </td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<input type="submit" value="로그인">
					<input type="reset" value="다시입력">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>