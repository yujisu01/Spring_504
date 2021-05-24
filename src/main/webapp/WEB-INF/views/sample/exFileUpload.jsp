<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath == '/' ? '' : pageContext.request.contextPath}" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${ctx }/sample/exUploadPost" method="post" enctype="multipart/form-data">
		<div>
			<input type="file" name="files" >
		</div>
		<div>
			<input type="file" name="files" >
		</div>
		<div>
			<input type="file" name="files" >
		</div>
		<div>
			<input type="file" name="files" >
		</div>
		<div>
			<input type="file" name="files" >
		</div>
		<div>
			<input type="submit" value="파일등록">
		</div>
	</form>
</body>
</html>