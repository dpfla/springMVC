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
<title>게시글 작성 창</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function readImage(input){
		if(input.files && input.files[0]){
			let reader = new FileReader()
			reader.onload=function (event){
				$("#preview").attr("src", event.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	//다른 액션 submit
	function toList(obj) {
		obj.action = "${contextPath}/board/listArticles.do";
		obj.submit();
	}
	let cnt = 1;
	function fn_addFile(){
		$("#dock_file").append("<input type='file' name='imgFile" + cnt + "'><br>");
		cnt++;
	}
</script>
</head>
<body>
	<h2 align="center">새 게시글 작성</h2>
	<form action="${contextPath }/board/addNewArticle.do" method="post" enctype="multipart/form-data" accept-charset="utf-8">
		<table align="center" width="70%">
			<tr>
				<td align="right">작성자: </td>
				<td colspan="2"><input type="text" size="20"  value="${member.name }" readonly></td>
			</tr>
			<tr>
				<td align="right">글 제목: </td>
				<td colspan="2"><input type="text" size="50"  name="title"></td>
			</tr>
			<tr>
				<td align="right">글 내용: </td>
				<td colspan="2"><textarea rows="10" cols="50" maxlength="4000" name="content"></textarea></td>
			</tr>
			<!-- 한 개 이미지
			<tr>
				<td align="right"> 이미지 첨부: </td>
				<td>
					<input type="file" name="imageFileName" onchange="readImage(this)">
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="left">
					<img id="preview" src="#" width="200" height="200">
				</td>
			</tr> -->
			
			<!-- 여러개 이미지 -->
			<tr>
				<td align="right"> 이미지 첨부: </td>
				<td>
					<input type="button" value="파일추가" onclick="fn_addFile();">
				</td>
			</tr>
			<tr>
				<td></td>
				<td id="dock_file"></td>
			</tr>
			<tr>
				<td width="50%" align="right">
					<input type="submit" value="게시하기">
				</td>
				<td width="50%" align="left">
					<input type="button" value="목록보기" onclick="toList(this.form)">
				</td>
		</table>
	</form>
</body>
</html>