<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-title">类目管理</h3>
		<ul class="page-breadcrumb breadcrumb">
			<li><i class="fa fa-home"></i><a href="#">首页</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">类目管理</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">类目列表</a></li>
		</ul>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-table"></i><span class="portlet-caption-text">类目列表</span>
				</div>
				<div class="actions">
  					<button class="btn yellow btn-sm" id="search">
						<i class="fa fa-search"></i>查询
					</button>
 					<button class="btn blue btn-sm" id="reset">
						<i class="fa fa-refresh "></i>清空查询
					</button>
					<sl:permission url="/admin/category/submit">
					<button class="btn green-jungle btn-sm" id="create-entity-button">
						<i class="fa fa-plus"></i>新增类目
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
									<label class="col-md-4 control-label">项目</label>
									<div class="col-md-6">
										<select class="form-control" name="search_projectParentId">
											<option value=""></option>
											<c:forEach items="${projects.data }" var="item">
												<option value="${item.id }">${item.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
				<table class="table table-striped table-bordered table-hover" id="datatable-list"></table>
			</div>
		</div>
	</div>
</div>
<%@ include file="form.jsp"%>
<script type="text/javascript">
	$(function() {
		var $form = $("#entity_modal_form");
		var $modal = $("#entity_modal_layer");

		var grid = new Datatable();
		var $datatable = $("#datatable-list");
		grid.init({
			src : $datatable,
			dataTable : {
				"ajax" : {
					"url" : "/admin/category/list",
				},
				"columns" : [ {
					"title" : "id",
					"data" : "id",
					"visible" : false
				}, {
					"title" : "项目",
					"data" : "projectName"
				}, {
					"title" : "类目名",
					"data" : "name"
				}, {
					"title" : "是否可见",
					"data" : "visible",
					"render" : function(val, type, row) {
						return val == '是' ? "可见" : "不可见";
					}
				},  {
					"title" : "排序",
					"data" : "sort"
				},{
					"title" : "操作",
					"render" : function(val, type, row) {
						var html = "";
						html += '<sl:permission url="/admin/category/list"><button type="button" href="" class="btn btn-sm green-jungle  show_button"><i class=""></i>查看</button></sl:permission>';
						html += '<sl:permission url="/admin/category/submit"><button type="button" href="" class="btn btn-sm yellow  edit_button"><i class=""></i>修改</button></sl:permission>';
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
				formLoadHandle($form, values);
			}
			$modal.find(".modal-title").html(title)
			$modal.find("button.submit").show();
			$modal.modal("show");
		}

		//创建
		$("#create-entity-button").click(function() {
			showForm("创建类目");
		});

		//提交
		$modal.find("button.submit").click(function() {
			if (formValidate($form)) {
				$.post("/admin/category/submit", $form.serialize(), function(response) {
					slalert(response.message);
					if (response.code == '000000') {
						$modal.one("hidden.bs.modal", function() {
							grid.refresh();
						}).modal("hide");
					}
				})
			}
		});

		$datatable.on("click", "button.show_button", function() {//查看
			var row = datatable.row($(this).closest("tr")).data();
			row.startDate = new Date(row.startDate).format();
			showForm("查看类目", row);
			formDisabledHandle($form);
			$modal.find("button.submit").hide();
		}).on("click", "button.edit_button", function() {//编辑
			var row = datatable.row($(this).closest("tr")).data();
			row.startDate = new Date(row.startDate).format();
			showForm("编辑类目", row);
			addDisabled($form.find("select[name='projectId']"));

		})
	})
</script>