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
<title>회원정보 출력</title>
<c:choose>
	<c:when test="${msg=='addMember' }">
		<script>
			window.onload=function() {
				alert("회원을 등록했습니다");
			}	
		</script>
	</c:when>
	<c:when test="${msg=='modified' }">
		<script>
			window.onload=function() {
				alert("회원정보를 수정했습니다");
			}	
		</script>
	</c:when>
	<c:when test="${msg=='deleted' }">
		<script>
			window.onload=function() {
				alert("회원정보를 삭제했습니다");
			}	
		</script>
	</c:when>
</c:choose>

</head>
<body>
	<h2 align="center">회원정보</h2>
	<table border="1" align="center">
		<tr align="center" bgcolor="#ccc">
			<th>아이디</th><th>비밀번호</th><th>이름</th><th>이메일</th><th>가입일자</th><th>수정</th><th>삭제</th>
		</tr>
		<c:choose>
			<c:when test="${empty memberList }">
				
				 <tr><td colspan="5" align="center">등록된 회원이 없습니다</td></tr>
			</c:when>
			<c:when test="${!empty memberList }">
				<c:forEach var="mem" items="${memberList }">
				 	<tr>
				 		<td>${mem.id }</td>
				 		<td>${mem.pwd }</td>
				 		<td>${mem.name }</td>
				 		<td>${mem.email }</td>
				 		<td>${mem.joinDate }</td>
				 		<td><a href="${contextPath }/member/modMemberForm.do?id=${mem.id}">수정</a></td>
				 		<td><a href="${contextPath }/member/delMember.do?id=${mem.id}">삭제</a></td>
				 	</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<%-- 
		request.setAttribute("memberList", memberList); 에서 속성값을 memberList
		로 넘겨줬기 때문에 memberList를 받아서 사용, 
		JSTL을 사용하면 따로 받아서 사용하지 않고 속성이름을 그대로 사용해도 된다
	 --%>
	<%-- 
		memerList의 요소를 하나씩 mem에 넣어서 처리한다
	--%>
	
	<a href="${contextPath }/member/memberForm.do"> 회원가입</a>
</body>
</html>