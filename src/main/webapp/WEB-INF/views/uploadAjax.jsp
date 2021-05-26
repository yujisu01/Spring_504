<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:set var="ctx" value="${pageContext.request.contextPath == '/' ? '' : pageContext.request.contextPath}" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	.uploadResult{
		width: 100%;
		background-color: gray;
	}
	.uploadResult ul{
		display: flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
		
	}
	.uploadResult ul li{
		list-style: none;
		padding: 10px;
		
	}
	.uploadResult up li img {
		width: 100px;
	}
	.bigPictureWrapper{
		position: absolute;
		display: none;
		justify-content: center;
		align-items: center;
		top: 0%;
		width: 100%;
		height: 100%;
		background-color: gray;
		z-index: 100;
	}
	.bigPicture {
		position: relative;
		display: flex;
		justify-content: center;
		align-items: center;
			
	}
	.bigPicture img{
		width: 600px;
		
	}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">

	function showImage(fileCallPath){
		$(".bigPictureWrapper").css("display","flex").show();
		
		$(".bigPicture")
		.html("<img src='${ctx}/display?fileName=" + fileCallPath + "'>")
		.animate({width:'100%', height:'100%'}, 1000);
	}
	$(document).ready(function(){
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		var maxSize = 5242880; 			/*  = 5mb */
		
		/* 사이즈 초과하는 파일, 형식에 맞지 않는 파일 업로드안되게 하는 함수 */
		function checkExtension(fileName, fileSize){
			if(fileSize >= maxSize){
				alert("파일 사이즈 초과");
				return false;
			}
			
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드할수 없습니다.");
				return false;
			}
			// 이 두가지 조건을 충족하는 이미지만 업로드 성공 
			return true;
		}
		var cloneObj = $(".uploadDiv").clone();
		
		$("#uploadBtn").on("click",function(e){
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			console.log(files);
			
			for(var i=0; i<files.length; i++){
				if(!checkExtension(files[i].name, files[i].size)){
					return false;
				}
				formData.append("uploadFile", files[i]);
			}
			// 비동기방식 
			$.ajax({
				url : "${ctx}/uploadAjaxAction",
				processData : false,		// form데이터 넘길때 반드시 필요하다. (없으면 안넘어감)
				contentType : false,
				data : formData,
				type : "post",
				dataType : "json",
				success : function(result){ 		// 성공하면 뭔가 넘어오는데 그것을 result로 받음.
					console.log(result);
					showUploadedFile(result);
					$(".uploadDiv").html(cloneObj.html());		// DOM객체로 접근
				}
			});
		});
		
		var uploadResult = $(".uploadResult ul");
		
		function showUploadedFile(uploadResultArr){
			var str = "";
			$(uploadResultArr).each(function(i,obj){ 			// jQuery의 반복문 (배열로 넘어오면 반복문 돌리면서 뭔가를 추출한다.)
				if(!obj.image){			// image가 아니라면
					var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
					var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
						
					// 나온 정보를 가지고 결합 (이미지가 아닌것은 attach.png로 표시)
					str += "<li><div><a href='${ctx}/download?fileName=" + fileCallPath + "'>" + 
							"<img src='${ctx}/resources/img/attach.png'>" + obj.fileName + "</a>" +
							"<span data-file=\'"+ fileCallPath +"\' data-type='file'>x</span>" +
							"</div></li>";
				}else{
					var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
					var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
					originPath = originPath.replace(new RegExp(/\\/g), "/");
					
					// 따옴표지옥
					str += "<li><a href=\"javascript:showImage(\'"+ originPath +"\')\">" + 
							"<img src='${ctx}/display?fileName=" + fileCallPath +"'></a>" + 
							"<span data-file=\'"+ fileCallPath +"\' data-type='image'>x</span>" +
							"</li>";
							
				}
			
				$(".bigPictureWrapper").on("click",function(e){
					$(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
					
					// 람다식 
					setTimeout(() => {
						$(this).hide();
						
					}, 1000);
				});
					// 첨부파일의 x자버튼 눌렀을때 처리 (삭제기능)
					// 125Line의 span태그
					$(".uploadResult").on("click","span",function(e){
						var targetFile = $(this).data("file");
						var type = $(this).data("type");
						
						// 여기서 호출
						$.ajax({
							url : "${ctx}/deleteFile",
							data : {fileName : targetFile, type : type},			// 배열처럼 넘김
							dataType : "text",
							type : "POST",
							success : function(result){
								alert(result);
							}
						});
					});
			});		
			uploadResult.append(str);
		}
	});
</script>
</head>
<body>
	<h1>Upload With Ajax</h1>
	<div class="bigPictureWrapper">
		<div class="bigPicture">
		</div>
	</div>
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple="multiple">
	</div>
	<div class="uploadResult">
		<ul>
			
		</ul>
	</div>
	<button id="uploadBtn">Upload</button>
</body>
</html>