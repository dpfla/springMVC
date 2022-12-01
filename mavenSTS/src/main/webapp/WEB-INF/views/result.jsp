<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>업로드 결과창</title>
</head>
<body>
	<h2>${map.name }님의 파일이 업로드 완로되었습니다</h2>
	<div class="result-images">
		<c:forEach var="imageFileName" items="${map.fileList }">
			<img src="${contextPath }/download?imageFileName=${imageFileName}" width="150"><br>
		</c:forEach>
	</div>
</body>
</html>