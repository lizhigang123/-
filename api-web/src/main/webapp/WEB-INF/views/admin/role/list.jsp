<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-title">角色列表</h3>
		<ul class="page-breadcrumb breadcrumb">
			<li><i class="fa fa-home"></i><a href="#">首页</a><i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">角色管理</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">角色列表</a></li>
		</ul>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<div class="tabbable tabbable-custom tabbable-full-width">
			<!--------------------------------列表 start-------------------------------->
			<div class="portlet box green tab-pane active">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>角色列表
					</div>
					<div class="actions">
						<sl:permission url="/admin/role/create">
							<a href="${ctx }/admin/role/create"
								class="ajaxify btn blue btn-sm"> <i class="fa fa-plus"></i>新增角色
							</a>
						</sl:permission>
					</div>
				</div>
				<div class="portlet-body">
					<table class="table table-striped table-bordered table-hover"
						id="datatable-list"></table>
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>
</div>
<!-- END PAGE CONTENT-->

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
					"url" : "/admin/role/list",
				},
				"columns" : [ {
					"title" : "id",
					"data" : "id",
					"visible" : false
				}, {
					"title" : "角色名称",
					"data" : "name"
				}, {
					"title" : "创建人",
					"data" : "userName"
				}, {
					"title" : "创建时间",
					"data" : "createDate",
					"render" : function(val, type, row) {
						return val ? new Date(val).format() : "";
					}
				}, {
					"title" : "操作",
					"render" : function(val, type, row) {
						var html = "";
						html += '<sl:permission url="/admin/role/edit"><button type="button" href="/admin/role/edit?roleId=' + row.id + '&type=0" class="btn btn-sm  green-jungle  ajaxify"><i class=""></i>查看</button></sl:permission>';
						html += '<sl:permission url="/admin/role/edit"><button type="button" href="/admin/role/edit?roleId=' + row.id + '&type=1" class="btn btn-sm  yellow  ajaxify"><i class=""></i>编辑</button></sl:permission>';
						html += '<sl:permission url="/admin/role/delete"><button type="button" href="/admin/role/delete?roleId=' + row.id + '"  class="red btn btn-sm status-change-button"><i class=""></i>删除</button></sl:permission>';
						return html;
					}
				} ]
			}
		});
		$datatable.on("click", "button.status-change-button", function() {
			var $thiz = $(this);
			slconfirm("您确认要" + $thiz.html() + "此角色,删除角色同时会删除所有用户关联的此角色。", function() {
				$.get($thiz.attr("href"), function(response) {
					slalert(response.message);
					if (response.code == '000000') {
						grid.refresh();
					}
				})

			})
		});

	})
</script>

