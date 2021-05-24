<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/resources/includes/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath == '/' ? '' : pageContext.request.contextPath}" scope="application"/>
<script src="https://kit.fontawesome.com/215ee1d5fa.js" crossorigin="anonymous"></script>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tables</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Board List Page
                            <button id="regBtn" type="button" class="btn btn-xs pull-right">Register New Board</button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>#번호</th>
                                        <th>제목</th>
                                        <th>작성자</th>
                                        <th>작성일</th>
                                        <th>조회수</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<!-- <i class="fas fa-backward"></i> -->
                                	<c:forEach items="${list }" var="list">
                                <tr>
                                		<td>${list.bno }<i class="fas fa-heart"></i></td>
										<td><a href="${ctx }/board/read?bno=${list.bno }">${list.title }</a></td>
										<td>${list.writer }</td>
										<td>${list.regdate }</td>
										<td><span class="badge bg-red">${list.viewcnt }</span></td>
									</tr>
									</c:forEach>
                                </tbody>
                            </table>
                            
                            <!-- Modal창 추가 -->
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModallabel" aria-hidden="true">
                            	<div class="modal-dialog">
                            		<div class="modal-content">
                            			<div class="modal-header">
                            				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            				<h4 class="modal-title" id="myModalLabel">Modal창</h4>
                            			</div>
                            			<div class="modal-body">
                            				처리가 완료되었습니다
                            			</div>
                            			<div class="modal-footer">
                            				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            				<button type="button" class="btn btn-primary">Save Changes</button>
                            			</div>
                            		</div>
                            	</div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <script>
            	$(document).ready(function(){
            		var result = "${result}";
            		
            		checkModal(result);
            		
            		history.replaceState({}, null, null);
            		
            		
            		function checkModal(result){
            			if(result=="" || history.state){
            				return;
            			}
            			if(parseInt(result) > 0){
            				$(".modal-body").html("게시글" +parseInt(result) + "번 글이 등록되었습니다.");
            			}
            			$("#myModal").modal("show");
            		}
            		$("#regBtn").on("click", function(){
            			self.location = "${ctx}/board/register";
            		});
            	});
            </script>
            
<%@include file="/resources/includes/footer.jsp"%>

