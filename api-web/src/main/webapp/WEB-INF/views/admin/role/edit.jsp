<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<div id="entity_modal_layer">
	<div class="row">
		<div class="col-md-12">
			<h3 class="page-title">编辑角色</h3>
			<ul class="page-breadcrumb breadcrumb">
				<li><i class="fa fa-home"></i><a href="#">首页</a><i
					class="fa fa-angle-right"></i></li>
				<li><a href="#">角色管理</a><i class="fa fa-angle-right"></i></li>
				<li><a href="#">编辑角色</a></li>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="tabbable tabbable-custom tabbable-full-width">
				<div class="portlet box green tab-pane active">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>编辑角色
						</div>
					</div>
					<div class="portlet-body">
						<form id="entity_modal_form" class="form-horizontal">
							<input name="id" type="hidden" value="${id}" /> <input
								name="userId" type="hidden" />
							<div style="display: none;" id="role_create_hide"></div>
							<div class="row ">
								<div class="col-md-12">
									<div class="form-group">
										<label class="col-md-1 control-label"><span
											class="required" aria-required="true">* </span>角色名称：</label>
										<div class="col-md-7">
											<input name="name" type="text" id="roleName"
												class="form-control" placeholder="角色名称" value="${name}" />
										</div>
									</div>
								</div>
							</div>
							<div class="row " style="height: 30px"></div>
							<div class="row ">
								<div class="col-md-12">
									<div class="form-group">
										<div class="col-md-8">
											<table id="resourceGridTree"
												class="table table-striped table-bordered table-hover"></table>
										</div>
									</div>
								</div>
							</div>

						</form>
						<div class="row ">
							<div class="col-md-6" style="margin-left:460px">
								<c:set var="type" scope="session" value="${type}" />
								<c:if test="${type == 1}">
									<button type="button" class="btn green submit">确认</button>
								</c:if>
								<button type="button" data-dismiss="modal"
									class="btn btn-default back ajaxify">取消</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
<script src="/js/views/editresource.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//初始化日期控件
		Resource.initShow("${id}","${type}");
 		if(${type}==0){
			document.getElementById("roleName").readOnly="readonly";
		}
	});
 
</script>