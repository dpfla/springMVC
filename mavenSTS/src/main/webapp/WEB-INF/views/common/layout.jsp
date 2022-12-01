<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title" /></title>
<style type="text/css">
	#container{
		width: 100%;
		margin: 0 auto;
		text-align: center;
	} 
	header {
		padding: 5px;
		margin-bottom: 5px;
		background-color: #ccc;
	}
	#sidebar_left {
		width: 15%;
		height: 700px;
		padding: 5px;
		margin-right: 5px;
		margin-bottom: 5px;
		float: left;
		background-color: #b9b;
	}
	#content {
		width: 75%;
		padding: 5px;
		margin-right: 5px;
		float: left;
	}
	footer {
		clear: both;
		paddgin: 5px;
		background-color: #9bb;
	}
	
</style>
</head>
<body>
	<div id="container">
		<header>
			<tiles:insertAttribute name="header" />
		</header>
		<div id="sidebar_left">
			<tiles:insertAttribute name="side" />
		</div>
		<div id="content">
			<tiles:insertAttribute name="body" />
		</div>
		<footer>
			<tiles:insertAttribute name="footer" />
		</footer>
	</div>
</body>
</html>