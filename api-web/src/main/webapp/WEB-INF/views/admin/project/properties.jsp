<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-title">项目管理</h3>
		<ul class="page-breadcrumb breadcrumb">
			<li><i class="fa fa-home"></i><a href="#">首页</a><i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">项目管理</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">属性管理</a></li>
		</ul>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-table"></i><span class="portlet-caption-text">【${project.name }】
						属性列表</span>
				</div>
				<div class="actions">

					<sl:permission url="/admin/project/properties/submit">
						<button class="btn green-jungle btn-sm" id="create-entity-button">
							<i class="fa fa-plus"></i>新增属性
						</button>
					</sl:permission>
					<button class="btn btn-sm yellow ajaxify back">
						返回 <i class=" fa  fa-history"></i>
					</button>
				</div>
			</div>
			<div class="portlet-body">
				<table class="table table-striped table-bordered table-hover"
					id="datatable-list"></table>
			</div>
		</div>
	</div>
</div>
<%@ include file="properties-form.jsp"%>
<script src="/js/views/datatypeselect.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		var $form = $("#entity_modal_form");
		var $modal = $("#entity_modal_layer");
		var projectId = "${projectId}";

		var grid = new Datatable();
		var $datatable = $("#datatable-list");
		grid.init({
			src : $datatable,
			dataTable : {
				"ajax" : {
					"url" : "/admin/project/properties/list?search_projectId=${projectId}&search_type=公共请求参数",
				},
				"columns" : [ {
					"title" : "id",
					"data" : "id",
					"visible" : false
				}, {
					"title" : "属性类型",
					"data" : "type"
				}, {
					"title" : "属性名称",
					"data" : "name"
				}, {
					"title" : "属性中文名称",
					"data" : "nameDesc"
				}, {
					"title" : "数据类型",
					"data" : "dataType"
				}, {
					"title" : "是否可见",
					"data" : "visible"
				}, {
					"title" : "是否必须",
					"data" : "need"
				}, {
					"title" : "操作",
					"render" : function(val, type, row) {
						var html = "";
						html += '<sl:permission url="/admin/project/properties"><button type="button" href="" class="btn btn-sm  green-jungle  show_button"><i class=""></i>查看</button></sl:permission>';
						html += '<sl:permission url="/admin/project/properties/submit"><button type="button" href="" class="btn btn-sm  yellow  edit_button"><i class=""></i>修改</button></sl:permission>';
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
			showForm("创建项目属性", {
				"projectId" : projectId
			});
		});

		//提交
		$modal.find("button.submit").click(function() {
			if (formValidate($form)) {
				$.post("/admin/project/properties/submit", $form.serialize(), function(response) {
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
			showForm("查看项目属性", row);
			formDisabledHandle($form);
			$modal.find("button.submit").hide();
		}).on("click", "button.edit_button", function() {//编辑
			var row = datatable.row($(this).closest("tr")).data();
			showForm("编辑项目属性", row);
		})
	})
</script>