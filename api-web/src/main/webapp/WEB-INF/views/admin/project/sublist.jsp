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
			<li><a href="#">分支管理</a></li>
		</ul>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-table"></i><span class="portlet-caption-text">分支列表</span>
				</div>
				<div class="actions">
					<sl:permission url="/admin/project/submit">
						<button class="btn green-jungle btn-sm" id="create-entity-button">
							<i class="fa fa-plus"></i>新增分支
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
<%@ include file="form.jsp"%>
<%@ include file="deverlopers-form.jsp"%>
<script type="text/javascript">
	$(function() {
		var $form = $("#entity_modal_form");
		var $modal = $("#entity_modal_layer");
		var parentId = "${parentId}";

		var grid = new Datatable();
		var $datatable = $("#datatable-list");
		grid.init({
			src : $datatable,
			dataTable : {
				"ajax" : {
					"url" : "/admin/project/sub/list?search_parentId=${parentId}",
				},
				"columns" : [ {
					"title" : "id",
					"data" : "id",
					"visible" : false
				}, {
					"title" : "项目名称",
					"data" : "name"
				}, {
					"title" : "项目开始时间",
					"data" : "startDate",
					"render" : function(val, type, row) {
						return val ? new Date(val).format() : "";
					}
				}, {
					"title" : "负责人",
					"data" : "leaderName"
				}, {
					"title" : "测试环境地址",
					"data" : "testUrl",
					"render" : function(val, type, row) {
						return val == null ? "" : val;
					}
				}, {
					"title" : "生产环境地址",
					"data" : "productionUrl"
				}, {
					"title" : "svn地址",
					"data" : "svnUrl"
				}, {
					"title" : "操作",
					"render" : function(val, type, row) {
						var html = "";
						html += '<sl:permission url="/admin/project/sub"><button type="button" href="" class="btn btn-sm  green-jungle  show_button"><i class=""></i>查看</button></sl:permission>';
						html += '<sl:permission url="/admin/project/submit"><button type="button" href="" class="btn btn-sm  yellow  edit_button"><i class=""></i>修改</button></sl:permission>';
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
			showForm("创建分支", {
				"parentId" : parentId
			});
		});

		//提交
		$modal.find("button.submit").click(function() {
			if (formValidate($form)) {
				$.post("/admin/project/submit", $form.serialize(), function(response) {
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
			showForm("查看分支", row);
			formDisabledHandle($form);
			$modal.find("button.submit").hide();
		}).on("click", "button.edit_button", function() {//编辑
			var row = datatable.row($(this).closest("tr")).data();
			row.startDate = new Date(row.startDate).format();
			showForm("编辑分支", row);
		})
	})
</script>