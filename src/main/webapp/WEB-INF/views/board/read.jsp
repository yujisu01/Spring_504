<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="/resources/includes/header.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath == '/' ? '' : pageContext.request.contextPath}" scope="application"/>


<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">Board Read</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<script>
	$(document).ready(function(){
		// id가 operForm인 애를 가져와라
		var operForm = $("#operForm");
		// modify선택
		$("button[data-oper='modify']").on("click", function(e){
			// 체이닝기법
			operForm.attr("action","${ctx}/board/modify").submit();
		});
		$("button[data-oper='list']").on("click", function(e){
			// id가 bno인 것을 찾아서 삭제
			operForm.find("#bno").remove();
			// submit과 따로따로 실행
			operForm.attr("action","${ctx}/board/list");
			operForm.submit();
		});
	});
</script>

<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">

      <div class="panel-heading">Board Read Page</div>
      <!-- /.panel-heading -->
      <div class="panel-body">

     <%--    <form role="form" action="${ctx}/board/register" method="post"> --%>
          <div class="form-group">
            <label>Bno</label> <input class="form-control" name='bno' value="${board.bno }" readonly="readonly">
          </div>
          <div class="form-group">
            <label>Title</label> <input class="form-control" name='title' value="${board.title }" readonly="readonly">
          </div>

          <div class="form-group">
            <label>Content</label>
            <textarea class="form-control" rows="3" name='content' readonly="readonly">${board.content }</textarea>
          </div>

          <div class="form-group">
            <label>Writer</label> <input class="form-control" name='writer' value="${board.writer }" readonly="readonly">
          </div>
          <button data-oper="modify" class="btn btn-default">Modify</button>
          <button data-oper="list" class="btn btn-info">List</button>
          <form action="${ctx}/board/modify" method="get" id="operForm">
          	<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno }"/>'>
          	<input type="hidden" name="pageNum" value= '<c:out value="${cri.pageNum }"/>'>
          	<input type="hidden" name="amount" value= '<c:out value="${cri.amount }"/>'>
          	<input type="hidden" name="keyword" value= '<c:out value="${cri.keyword }"/>'>
          	<input type="hidden" name="type" value= '<c:out value="${cri.type }"/>'>
          </form>
         <%--  <input type="button" value="삭제" onclick="del(${board.seq})"> --%>
     <!--    </form> -->

      </div>
      <!--  end panel-body -->

    </div>
    <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->
<%@include file="/resources/includes/footer.jsp"%>
