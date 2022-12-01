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
<title>파일 업로드</title>
<script src="http://code.jquery.com/jquery-lastest.js"></script>
<script type="text/javascript">
	function fn_addFile() {
		#('#multiFile').apppend("<input type='file' name='file" + cnt + "'><br>");
		cnt++;
	}
</script>
</head>
<body>
	<h2> 파일 업로드 하기 </h2>
	<form action="${contextPath }/upload" method="post" enctype="multipart/form-data">
		이름: <input type="text" name="name"> <br>
		<input type="button" value="파일추가" onclick="fn_addFile()"> <br>
		<div id="multiFile"></div>
		<input type="submit" value="업로드">		
	</form>
</body>
</html>