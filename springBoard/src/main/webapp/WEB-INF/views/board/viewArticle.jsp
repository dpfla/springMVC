<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="article" value="${articleMap.article }" />
<c:set var="imageFileList" value="${articleMap.imageFileList }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세 보기</title>
<style type="text/css">

	.hidden{
		display:none;
	}
</style>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function readImage(input){
		if(input.files && input.files[0]){
			let reader = new FileReader()
			reader.onload=function (event){
				$(".preview").attr("src", event.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	//다른 액션 submit
	function toList(obj) {
		obj.action = "${contextPath}/board/listArticles.do";
		obj.submit();
	}
	
	//글 수정하기
	function fn_enable(obj){
		document.getElementById("id_title").disabled=false;
		document.getElementById("id_content").disabled=false;
		let imageFileName = document.querySelector("#id_imageFileName");
		if(imageFileName != null){
			imageFileName.disabled=false;
		}
		document.getElementById("tr_button_modify").style.display="block";
		
		let file_upload = document.querySelectorAll(".file_upload");
		if(file_upload != null){
			file_upload.forEach(file => {
				file.classList.remove("hidden");
			})
		} else{
			file_upload.forEach(file => {
				file.classList.add("hidden");
			})
		}
		document.getElementById("tr_button").style.display="none";
	}
	
	//수정 액션 submit
	function fn_modify_article(obj){
		obj.action="${contextPath}/board/modArticle.do";
		obj.submit();
	}
	//삭제 함수
	function fn_remove_article(url, articleNo){
		let form=document.createElement("form");
		form.setAttribute("method", "get");
		form.setAttribute("action", url);
		let articleNoInput=document.createElement("input");
		articleNoInput.setAttribute("type", "hidden");
		articleNoInput.setAttribute("name", "articleNo");
		articleNoInput.setAttribute("value", articleNo);
		form.appendChild(articleNoInput);
		document.body.appendChild(form);
		form.submit();
	}
	
	function fn_reply_form(url, parentNo){
		let form=document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", url);
		let parentNoInput=document.createElement("input");
		parentNoInput.setAttribute("type", "hidden");
		parentNoInput.setAttribute("name", "parentNo");
		parentNoInput.setAttribute("value", parentNo);
		form.appendChild(parentNoInput);
		document.body.appendChild(form);
		form.submit();
	}
</script>
</head>
<body>
	<form action="${contextPath }" name="frmArticle" method="post" enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td width="150px" align="center" bgcolor="#ccc">글 번호</td>
				<td><input type="text" value="${article.articleNo}" disabled></td>
				<input type="hidden" name="articleNo" value="${article.articleNo}">
			</tr>
			<tr>
				<td width="150px" align="center" bgcolor="#ccc">작성자</td>
				<td><input type="text" value="${article.id}" name="writer" disabled></td>
			</tr>
			<tr>
				<td width="150px" align="center" bgcolor="#ccc">제목</td>
				<td><input type="text" value="${article.title}" id="id_title" name="title" disabled></td>
			</tr>
			<tr>
				<td width="150px" align="center" bgcolor="#ccc">내용</td>
				<td>
					<textarea rows="20" cols="50" name="content" id="id_content" disabled>${article.content}</textarea>
				</td>
			</tr>
			<%-- 
				<c:if test="${not empty article.imageFileName && article.imageFileName != 'null' }">
					<tr>
						<td width="150px" rowspan="2" align="center" bgcolor="#ccc">이미지</td>
						<td>
							<input type="hidden" name="originalFileName" value="${article.imageFileName}">
							<img style="max-width:373px" src="${contextPath}/download.do?articleNo=${article.articleNo}&imageFileName=${article.imageFileName}" id="preview"><br/>
						</td>
					</tr>
					<tr>
						<td><input type="file" name="imageFileName" id="id_imageFileName" onchange="readImage(this)" disabled></td>
					</tr>
				</c:if>	
			 --%>
			<c:choose>
				<%-- 
					<!-- 한 개의 이미지 모여주기 -->
					<c:when test="${not empty article.imageFileName && article.imageFileName != 'null' }">
						<tr>
							<td width="150px" rowspan="2" align="center" bgcolor="#ccc">이미지</td>
							<td>
								<input type="hidden" name="originalFileName" value="${article.imageFileName}">
								<img style="max-width:373px" src="${contextPath}/download.do?articleNo=${article.articleNo}&imageFileName=${article.imageFileName}" class="preview"><br/>
							</td>
						</tr>
						<tr>
							<td><input type="file" name="imageFileName" id="id_imageFileName" onchange="readImage(this)" disabled></td>
						</tr>
					</c:when>
				 --%>	
				 
				 <c:when test="${not empty imageFileList && imageFileList != 'null' }">
				 	<c:forEach var="item" items="${imageFileList}" varStatus="status">
						<tr>
							<td width="150px" rowspan="2" align="center" bgcolor="#ccc">이미지${status.count }</td>
							<td>
								<input type="hidden" name="originalFileName" value="${item.imageFileName}">
								<img style="max-width:373px" src="${contextPath}/download.do?articleNo=${article.articleNo}&imageFileName=${item.imageFileName}" class="preview"><br/>
							</td>
						</tr>
						<tr>
							<td><input type="file" name="imageFileName" id="id_imageFileName" onchange="readImage(this)" disabled></td>
						</tr>
					</c:forEach>	
				</c:when>
				 <c:otherwise>
					<tr class="file_upload hidden">
						<td width="150px" rowspan="2" align="center" bgcolor="#ccc" >이미지</td>
						<td>
							<input type="hidden" name="originalFileName" value="${article.imageFileName}">
							<input type="file" name="imageFileName" id="id_imageFileName" onchange="readImage(this)">
						</td>
					</tr>
					<tr class="file_upload hidden">
						<td>
							<img style="max-width:330px" src="#" class="preview">
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<td width="150px" align="center" bgcolor="#ccc">등록일자</td>
				<td><input type="text" value='<fmt:formatDate value="${article.writeDate}"/>' disabled></td>
			</tr>
			<tr id="tr_button_modify" class="hidden">
				<td colspan="2" align="center">
					<input type="button" value="수정반영하기" onclick="fn_modify_article(frmArticle)">
					<input type="button" value="취소" onclick="toList(this.form)">
				</td>
			</tr>
			<tr id="tr_button">
				<td colspan="2" align="center">
					<c:if test="${member.id == article.id }">
						<input type="button" value="수정하기" onclick="fn_enable(this.form)">
						<input type="button" value="삭제하기" onclick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNo})">
					</c:if>
					<input type="button" value="목록보기" onclick="toList(this.form)">
					<input type="button" value="답글쓰기" onclick="fn_reply_form('${contextPath}/board/replyForm.do', ${article.articleNo})">
				</td>
			</tr>
			<%-- 
				<c:if test="${member.id == article.id }">
					수정 가능
				</c:if>
			--%>
		</table>
	</form>
</body>
</html>