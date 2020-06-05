<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-title">用户管理</h3>
		<ul class="page-breadcrumb breadcrumb">
			<li><i class="fa fa-home"></i><a href="#">首页</a><i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">用户管理</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">用户列表</a></li>
		</ul>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-table"></i><span class="portlet-caption-text">用户列表</span>
				</div>
				<div class="actions">
 					<button class="btn yellow btn-sm" id="search">
						<i class="fa fa-search"></i>查询
					</button>
 					<button class="btn blue btn-sm" id="reset">
						<i class="fa fa-refresh "></i>清空查询
					</button>
					<sl:permission url="/admin/user/submit">
						<button class="btn green-jungle btn-sm" id="create-entity-button">
							<i class="fa fa-plus"></i>新增用户
						</button>
					</sl:permission>
				</div>
			</div>
			<div class="portlet-body">
				<form id="search_form" class="form-horizontal">
					<div class="form-body">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">登录名</label>
									<div class="col-md-6">
										<input name="search_loginName" class="form-control" />
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">用户名</label>
									<div class="col-md-6">
										<input name="search_name" class="form-control" />
									</div>
								</div>
							</div>
						</div>

					</div>
				</form>
				<table class="table table-striped table-bordered table-hover"
					id="datatable-list"></table>
			</div>
		</div>
	</div>
</div>
<%@ include file="form.jsp"%>
<%@ include file="modifypassword.jsp"%>
<div id="user_role_modal" class="modal fade" tabindex="-1" role="dialog"></div>
<script type="text/javascript">
	$(function() {
		var $form = $("#entity_modal_form");
 		var $modal = $("#entity_modal_layer");
 		var $passwordform = $("#modify_password_modal_form");
 		var $passwordmodal = $("#modify_password_modal_layer");
 		
		var grid = new Datatable();
		var $datatable = $("#datatable-list");
		grid.init({
			src : $datatable,
			dataTable : {
				"ajax" : {
					"url" : "/admin/user/list",
				},
				"columns" : [ {
					"title" : "id",
					"data" : "id",
					"visible" : false
				}, {
					"title" : "用户名",
					"data" : "name"
				}, {
					"title" : "登录名",
					"data" : "loginName"
				}, {
					"title" : "邮箱",
					"data" : "email"
				}, {
					"title" : "操作",
					"render" : function(val, type, row) {
						var html = "";
						html += '<sl:permission url="/admin/user/editrole"><button type="button" href="" class="btn btn-sm yellow  assign_role"><i class=""></i>分配角色</button></sl:permission>';
						html += '<sl:permission url="/admin/user"><button type="button" href="" class="btn btn-sm green-jungle  show_button"><i class=""></i>查看</button></sl:permission>';
						html += '<sl:permission url="/admin/user/submit"><button type="button" href="" class="btn btn-sm yellow  edit_button"><i class=""></i>修改</button></sl:permission>';
						html += '<sl:permission url="/admin/user/modifypassword"><button type="button" href="" class="btn btn-sm blue  modify_password_button"><i class=""></i>修改密码</button></sl:permission>';
						html += '<sl:permission url="/admin/user/delete"><button href="/admin/user/delete?userId=' + row.id + '" type="button" class="red btn btn-sm status-change-button"><i class=""></i>删除</button></sl:permission>';
						return html;
					}
				} ]
			}
		});

		var datatable = $datatable.DataTable();
		$('.date-picker').datepicker({
			rtl : Metronic.isRTL(),
			autoclose : true
		});

		function showForm(title, values) {
			resetValidateRule($form);
			formClearHandle($form);
			formRemoveDisabledHandle($form);
			if (values) {
				values.startDate = values.startDate ? new Date(values.startDate).format() : "";
				formLoadHandle($form, values);
			}
			if(title == '查看用户'||title == '编辑用户'){
				$modal.find(".password").hide();
			}else{
				$modal.find(".password").show();
			}
 			$modal.find(".modal-title").html(title)
			$modal.find("button.submit").show();
			$modal.modal("show");
		}
		function showModfiyForm(title, values) {
			resetValidateRule($passwordform);
			formClearHandle($passwordform);
			formRemoveDisabledHandle($passwordform);
			if (values) {
				values.startDate = values.startDate ? new Date(values.startDate).format() : "";
				formLoadHandle($passwordform, values);
			}
			$passwordmodal.find("#newpassword").val('');
  			$passwordmodal.find(".modal-title").html(title)
			$passwordmodal.find("button.submit").show();
			$passwordmodal.modal("show");
		}
		
		//提交
		$modal.find("button.submit").click(function() {
			if (formValidate($form)) {
				$.post("/admin/user/submit", $form.serialize(), function(response) {
					slalert(response.message);
					if (response.code == '000000') {
						$modal.one("hidden.bs.modal", function() {
							grid.refresh();
						}).modal("hide");
					}
				})
			}
		});
		$passwordmodal.find("button.modifysubmit").click(function() {
			if (formValidate($passwordform)) {
  				$.post("/admin/user/modifypassword", $passwordform.serialize(), function(response) {
					slalert(response.message);
					if (response.code == '000000') {
						$passwordmodal.one("hidden.bs.modal", function() {
							grid.refresh();
						}).modal("hide");
					}
				})  
			}
		});
 		//创建
		$("#create-entity-button").click(function() {
			showForm("创建用户");
		});
		$datatable.on("click", "button.show_button", function() {//查看
			var row = datatable.row($(this).closest("tr")).data();
			row.startDate = new Date(row.startDate).format();
			showForm("查看用户", row);
			formDisabledHandle($form);
			$modal.find("button.submit").hide();
		}).on("click", "button.edit_button", function() {//编辑
			var row = datatable.row($(this).closest("tr")).data();
			row.startDate = new Date(row.startDate).format();
			showForm("编辑用户", row);
		}).on("click", "button.status-change-button", function() {
			var $thiz = $(this);
			slconfirm("确定" + $thiz.html() + "此用户", function() {
				$.get($thiz.attr("href"), function(response) {
					slalert(response.message);
					if (response.code == '000000') {
						grid.refresh();
					}
				})
			})
		}).on("click", ".assign_role", function() {
			var row = datatable.row($(this).closest("tr")).data();
			$("#user_role_modal").load('/admin/user/editrole?userId=' + row.id, function() {
				$(this).modal("show");
			})
		}).on("click", "button.modify_password_button", function() {
			var row = datatable.row($(this).closest("tr")).data();
			showModfiyForm("修改密码", row);
		});

	})
</script>