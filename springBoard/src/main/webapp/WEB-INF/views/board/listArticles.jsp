<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%--<c:set var="articleList" value="${articleMap.articleList }"/>
<c:set var="totArticles" value="${articleMap.totArticles }"/>
<c:set var="section" value="${articleMap.section }"/>
<c:set var="pageNum" value="${articleMap.pageNum }"/> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>

<style type="text/css">
	a{
		color: black;
		text-decoration: none;
	}
	a.selPage{
		color: red;
	}

</style>
<script type="text/javascript">
	function fn_articleForm(isLogOn, articleForm, loginForm) {
		if(isLogOn != "" && isLogOn != "false"){
			location.href=articleForm;
		} else {
			alert("로그인 후 게시글을 작성해주세요");
			location.href=loginForm+"?action=/board/articleForm.do"
		}
	}
</script>
</head>
<body>
	<table align="center" border="1" width="70%">
		<tr align="center" bgcolor="#ccc">
			<th>글번호</th><th>작성자</th><th>제목</th><th>작성일</th>
		</tr>
		<c:choose>
			<c:when test="${empty articleList}">
				<tr>
					<td colspan="4">등록된 글이 없습니다.</td>
				</tr>
			</c:when>
			<c:when test="${!empty articleList}">
				<c:forEach var="article" items="${articleList }" varStatus="articleNum">
					<tr align="center">
						<td width="5%">${articleNum.count }</td>
						<td width="10%">${article.id }</td>
						<td align="left">
							<c:choose>
								<c:when test="${article.level > 1 }">
									<c:forEach begin="1" end="${article.level }" step="1">
										<span style="padding-left:20px"></span>
									</c:forEach>
									<span>[답변] 
										<a href="${contextPath }/board/viewArticle.do?articleNo=${article.articleNo}">${article.title}</a>
									</span>
								</c:when>
								<c:otherwise>
									<span style="padding-left: 5px">
										<a href="${contextPath }/board/viewArticle.do?articleNo=${article.articleNo}">${article.title}</a>
									</span>
								</c:otherwise>
							</c:choose>
						</td>
						<td width="10%"><fmt:formatDate value="${article.writeDate }"/></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<div align="center">
		<c:if test="${totArticles != null}">
			<c:choose>
				<c:when test="${totArticles > 100 }">
					<c:forEach var="page" begin="1" end="10" step="1">
						<c:if test="${section > 1 && page == 1 }">  
							<a href="${contextPath }/board/listArticles.do?
							section=${section}&pageNum=${(section-1)*10+1}"> prev</a>
						</c:if>
						<a href="${contextPath }/board/listArticles.do?
						section=${section}&paegNum=${page}">${(section-1)*10+page }</a>
						<c:if test="${page == 10}">
							<a href="${contextPath }/board/listArticles.do?
							section=${section+1}&pageNum=${(section)*10+1}"> next</a>
						</c:if>
					</c:forEach>
				</c:when>
				
				<c:when test="${totArticles == 100 }">
					<c:forEach var="page" begin="1" end="10" step="1">
						<a href="#">${page }</a>
					</c:forEach>
				</c:when>
				
				<c:when test="${totArticles < 100 }">
					<c:forEach var="page" begin="1" end="${totArticles/10+1 }" step="1">
						<c:choose>
							<c:when test="${pageNum == page }">
								<a class="selPage" href="${contextPath }/board/listArticles.do?
								section=${section}&pageNum=${page }">${page }</a> 
							</c:when>
							<c:otherwise>
								<a class="noline" href="${contextPath }/board/listArticles.do?
								section=${section}&pageNum=${page }">${page }</a> 
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
			</c:choose>
		</c:if>
	</div>
	<p align="center">
		<a href="javascript:fn_articleForm('${isLogOn }', '${contextPath }/board/articleForm.do', 
			'${contextPath }/member/loginForm.do')">글쓰기</a>
	</p>
	
</body>
</html>