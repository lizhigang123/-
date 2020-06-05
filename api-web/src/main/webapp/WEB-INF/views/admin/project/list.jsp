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
			<li><a href="#">项目列表</a></li>
		</ul>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-table"></i><span class="portlet-caption-text">项目列表</span>
				</div>
				<div class="actions">
					<sl:permission url="/admin/project/submit">
						<button class="btn green-jungle btn-sm" id="create-entity-button">
							<i class="fa fa-plus"></i>新增项目
						</button>
					</sl:permission>
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
		var $deverlopers_form = $("#entity_modal_deverlopers_form");

		var $modal = $("#entity_modal_layer");
		var $modal2 = $("#entity_modal_layer2");

		var grid = new Datatable();
		var $datatable = $("#datatable-list");
		grid.init({
			src : $datatable,
			dataTable : {
				"ajax" : {
					"url" : "/admin/project/list",
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
						html += '<sl:permission url="/admin/project"><button type="button" href="" class="btn btn-sm  green-jungle  show_button"><i class=""></i>查看</button></sl:permission>';
						html += '<sl:permission url="/admin/project/submit"><button type="button" href="" class="btn btn-sm  yellow  edit_button"><i class=""></i>修改</button></sl:permission>';
						html += '<sl:permission url="/admin/project/sub"><button href="/admin/project/sub?parentId=' + row.id + '" type="button" class="btn btn-sm bg-purple ajaxify"><i class=""></i>分支管理</button></sl:permission>';
						html += '<sl:permission url="/admin/project/properties"><button href="/admin/project/properties?projectId=' + row.id + '" type="button" class="btn btn-sm ajaxify"><i class=""></i>属性管理</button></sl:permission>';
						html += '<sl:permission url="/admin/project/template"><button href="/admin/project/template?projectId=' + row.id + '" type="button" class="btn btn-sm grey-gallery ajaxify"><i class=""></i>参数管理</button></sl:permission>';
						html += '<sl:permission url="/admin/project/updateProjectUsers"><button type="button" href="" class="btn btn-sm  blue  edit_developers_button"><i class=""></i>分配开发人员</button></sl:permission>';
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
			$modal.find(".modal-title").html(title)
			$modal.find("button.submit").show();
			$modal.modal("show");
		}

		var pageSize = 10;
		$(".multiSelect").select2({
			multiple : true,
			minimumInputLength : 1,
			ajax : {
				url : "/admin/user/match",
				data : function(term, page) {
					return {
						"search_name" : term,
						"search_projectId" : $("#projectId").val(),
						start : (page - 1) * pageSize,
						length : page * pageSize
					};
				},
				results : function(response, page, query) {
					var selectValueArray = [];
					$.each(response.data, function(i, val) {
						selectValueArray.push({
							"id" : val.id,
							"text" : val.name
						});
					});
					return {
						results : selectValueArray,
						more : response.total > (page * pageSize),
						total : response.total
					};
				}
			},
			initSelection : function(element, callback) {
				var data = [];
				var id = $(element).val();
				id = id.substring(0, id.length - 1);
				if (id != "") {
					var json = JSON.parse(id);
					for (var i = 0; i < json.length; i++) {
						data.push({
							id : json[i].id,
							text : json[i].name
						});
					}
				}
				callback(data);
			}
		});
		var projectId;
		function showDeverlopersForm(title, values) {
			resetValidateRule($deverlopers_form);
			formClearHandle($deverlopers_form);
			formRemoveDisabledHandle($deverlopers_form);
			if (values) {
				$("#projectId").attr("value", values.id);
				$("#userIds").attr("value", values.userIds);
				var list = values.userIds;
				var deverlopers = [];
				for (var i = 0; i < list.length; i++) {
					deverlopers.push({
						id : list[i].id,
						text : list[i].name
					});
				}
				$("#userIds").select2("data", deverlopers, true);
				projectId = $("#projectId").val();

			}
			$modal2.find(".modal-title").html(title)
			$modal2.find("button.submit").show();
			$modal2.modal("show");
		}

		$modal2.find("button.submit").click(function() {
			var data = $(".multiSelect").select2("data");
			var userIds = "userIds=";
			for (var i = 0; i < data.length; i++) {
				userIds += data[i].id + ","
			}
			if (formValidate($deverlopers_form)) {
				$.post("/admin/project/updateProjectUsers?projectId=" + projectId, userIds, function(response) {
					slalert(response.message);
					if (response.code == '000000') {
						$modal2.one("hidden.bs.modal", function() {
							grid.refresh();
						}).modal("hide");
					}
				})
			}
		});

		//创建
		$("#create-entity-button").click(function() {
			showForm("创建项目");
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
			showForm("查看项目", row);
			formDisabledHandle($form);
			$modal.find("button.submit").hide();
		}).on("click", "button.edit_button", function() {//编辑
			var row = datatable.row($(this).closest("tr")).data();
			showForm("编辑项目", row);
		}).on("click", "button.edit_developers_button", function() {//分配开发人员
			var row = datatable.row($(this).closest("tr")).data();
			var curentDevelopers;
			$.ajax({
				type : "POST",
				url : "/admin/project/curentDevelopers?projectId=" + row.id,
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				async : false,
				success : function(data) {
					curentDevelopers = data.curentDevelopers;
				}
			});
			row.userIds = curentDevelopers;
			showDeverlopersForm("分配开发人员", row);
		})
	})
</script>
