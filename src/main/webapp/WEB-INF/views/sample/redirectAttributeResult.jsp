<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function result(){
		var msg = "${msg}";
		alert(msg);
	}
</script>
</head>
<body onload="result();">
	RedirectAttributeResult:${msg}
</body>
</html>