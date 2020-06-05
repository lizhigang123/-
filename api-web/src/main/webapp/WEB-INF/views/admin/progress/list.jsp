<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-title">进度管理</h3>
		<ul class="page-breadcrumb breadcrumb">
			<li><i class="fa fa-home"></i><a href="#">首页</a><i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">进度管理</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">进度列表</a></li>
		</ul>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-table"></i><span class="portlet-caption-text">进度列表</span>
				</div>
				<div class="actions">
					<button class="btn yellow btn-sm" id="search">
						<i class="fa fa-search"></i>查询
					</button>
					<button class="btn blue btn-sm" id="reset">
						<i class="fa fa-refresh "></i>清空查询
					</button>
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
										<select class="form-control cascade_projectId"
											name="search_projectId">
											<option value=""></option>
											<c:forEach items="${projects.data }" var="item">
												<option value="${item.id }">${item.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">分支</label>
									<div class="col-md-6">
										<select class="form-control cascade_branchId"
											name="search_branchId"></select>
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">类目</label>
									<div class="col-md-6">
										<select class="form-control cascade_categoryId"
											name="search_categoryId"></select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">接口名</label>
									<div class="col-md-6">
										<input name="search_name" class="form-control" />
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">访问路径</label>
									<div class="col-md-6">
										<input name="search_url" class="form-control" />
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">开发人员</label>
									<div class="col-md-6">
										<select class="form-control select2me" name="search_userId">
											<option value=""></option>
											<c:forEach items="${developers }" var="item">
												<option value="${item.id }">${item.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4 ">
								<div class="form-group">
									<label class="col-md-4 control-label">开始时间</label>
									<div class="col-md-6">
										<div class="input-group">
											<input name="search_startDateS" data-date-language="zh-CN"
												data-date-format="yyyy-mm-dd"
												class="form-control date-picker" type="text"
												readonly="readonly"> <span class="input-group-addon">--</span>
											<input name="search_startDateE" data-date-language="zh-CN"
												data-date-format="yyyy-mm-dd"
												class="form-control date-picker" type="text"
												readonly="readonly">
											<iframe id="tmp_downloadhelper_iframe" style="display: none;"></iframe>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-4 ">
								<div class="form-group">
									<label class="col-md-4 control-label">结束时间</label>
									<div class="col-md-6">
										<div class="input-group">
											<input name="search_endDateS" data-date-language="zh-CN"
												data-date-format="yyyy-mm-dd"
												class="form-control date-picker" type="text"
												readonly="readonly"> <span class="input-group-addon">--</span>
											<input name="search_endDateE" data-date-language="zh-CN"
												data-date-format="yyyy-mm-dd"
												class="form-control date-picker" type="text"
												readonly="readonly">
											<iframe id="tmp_downloadhelper_iframe" style="display: none;"></iframe>
										</div>
									</div>
								</div>
							</div>

							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">状态</label>
									<div class="col-md-6">
										<select class="form-control multiSelect select2me"
											multiple="multiple" name="search_status">
											<option value=""></option>
											<option value="新建">新建</option>
											<option value="开始">开始</option>
											<option value="结束">结束</option>
											<option value="逾期">逾期</option>
										</select>
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
<script type="text/javascript">
	$(function() {
		var $form = $("#entity_modal_form");
		var $modal = $("#entity_modal_layer");
		var grid = new Datatable();
		var $datatable = $("#datatable-list");
		var $progressLogTable = $('#log-datatable-list');
		var $progressLogLayer = $('#progress_log_layer')
		grid.init({
			src : $datatable,
			dataTable : {
				"ajax" : {
					"url" : "/admin/progress/list",
					"data" : function(data) {
						var values = '';
						$.each($('#search_form').serializeArray(), function(i, field) {
							if (field.name == 'search_status' && field.value) {
								values += field.value + ',';
							} else {
								data[field.name] = field.value;
							}

						});
						data['search_status'] = values;
					},
				},
				"columns" : [ {
					"title" : "id",
					"data" : "id",
					"visible" : false
				}, {
					"title" : "项目",
					"data" : "projectName"
				}, {
					"title" : "分支",
					"data" : "branchName"
				}, {
					"title" : "类目",
					"data" : "categoryName"
				}, {
					"title" : "接口名",
					"data" : "name",
					"render" : function(val, type, row) {
 						var html = "";
						if(row.interfaceStatus=='发布'){
							html += '<a class=show-detail href="/api/interface/'+row.iInterfaceId+'" target="_blank">' + row.name + '</a>';
 						}else{
 							html +=  row.name;
  						}
						return html;
					}
				}, {
					"title" : "访问路径",
					"data" : "url"
				}, {
					"title" : "开发人员",
					"data" : "developerName"
				}, {
					"title" : "开始时间",
					"data" : "startDate",
					"render" : function(val, type, row) {
						return row.startDate ? new Date(row.startDate).format() : "";
					}
				}, {
					"title" : "结束时间",
					"data" : "endDate",
					"render" : function(val, type, row) {
						return row.endDate ? new Date(row.endDate).format() : "";
					}
				}, {
					"title" : "最后更新时间",
					"data" : "lastUpdateDate",
					"render" : function(val, type, row) {
						return row.lastUpdateDate ? new Date(row.lastUpdateDate).format() : "";
					}
				}, {
					"title" : "完成进度(%)",
					"data" : "progress",
					"render" : function(val, type, row) {
						return row.progress;
					}
				}, {
					"title" : "状态",
					"data" : "status"
				}, {
					"title" : "操作",
					"render" : function(val, type, row) {
						var html = "";
						html += '<sl:permission url="/admin/progress/submit"><button type="button" href="" class="btn btn-sm yellow  edit_button"><i class=""></i>更新进度</button></sl:permission>';
						html += '<sl:permission url="/admin/progresslog/list"><button type="button" href="" class="btn btn-sm green  show_log_button"><i class=""></i>查看进度日志</button></sl:permission>';
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
		var pageSize = 10;
		function showForm(title, values) {
			resetValidateRule($form);
			formClearHandle($form);
			formRemoveDisabledHandle($form);
			if (values) {
				formLoadHandle($form, values);
			}
			$.ajax({
				url : "/admin/project/sub/list",
				type : "post",
				dataType : "json",
				data : {
					"search_parentId" : values.projectId,
					"start" : 0,
					"length" : 1000
				},
				async : false,
				success : function(response) {
					var html = '';
					if (values.branchId) {
						html = '<option value="'+values.branchId+'">' + values.branchName + '</option>';
					} else {
						html = '<option vaule=""></option>';
					}
					$.each(response.data, function(i, val) {
						if (val.id != values.branchId) {
							html += '<option value="'+val.id+'">' + val.name + '</option>';
						}
					});
					$modal.find(".cascade_branchId").html(html);
				}
			});
			$modal.find(".modal-title").html(title)
			$modal.find("button.submit").show();
			$modal.modal("show");
		}

		//提交
		$modal.find("button.submit").click(function() {
			if (formValidate($form)) {
				$.post("/admin/progress/submit", $form.serialize(), function(response) {
					slalert(response.message);
					if (response.code == '000000') {
						$modal.one("hidden.bs.modal", function() {
							grid.refresh();
						}).modal("hide");
					}
				})
			}
		});
		$datatable.on("click", "button.edit_button", function() {//编辑
			var row = datatable.row($(this).closest("tr")).data();
			row.startDate = row.startDate ? new Date(row.startDate).format() : "";
			row.endDate = row.endDate ? new Date(row.endDate).format() : "";
			row.interfaceId = row.interfaceInterfaceId;
			showForm("更新进度", row);
		}).on("click", "button.show_log_button", function() {//编辑
			var grid = new Datatable();
			var row = datatable.row($(this).closest("tr")).data();
			grid.init({
				src : $progressLogTable,
				dataTable : {
					"ajax" : {
						"url" : "/admin/progresslog/list?search_progressId=" + row.id,
					},
					"bDestroy" : true,
					"lengthMenu" : [ [ 5, 6, 7, 8, 10 ], [ 5, 6, 7, 8, 10 ] ],
					"pageLength" : 5, // default records per page
					"columns" : [ {
						"title" : '序号',
						"data" : 'id',
						"visible" : false,
					}, {
						"title" : '备注',
						"data" : 'memo',
					}, {
						"title" : '日志内容',
						"data" : 'content',
					}, {
						"title" : '创建时间',
						"data" : 'createDate',
						"render" : function(val, type, row) {
							return row.createDate ? new Date(row.createDate).format() : "";
						}
					}, {
						"title" : "最后更新时间",
						"data" : "lastUpdateDate",
						"render" : function(val, type, row) {
							return row.lastUpdateDate ? new Date(row.lastUpdateDate).format() : "";
						}
					} ]
				}
			});
			$progressLogLayer.modal("show");
		});

		$(".cascade_projectId").change(function() {
			var $thiz = $(this);
			//分支
			$.ajax({
				url : "/admin/project/sub/list",
				type : "post",
				dataType : "json",
				data : {
					"search_parentId" : $thiz.val(),
					"start" : 0,
					"length" : 1000
				},
				async : false,
				success : function(response) {
					var html = '<option vaule=""></option>';
					$.each(response.data, function(i, val) {
						html += '<option value="'+val.id+'">' + val.name + '</option>';
					});
					$thiz.closest("form").find(".cascade_branchId").html(html);
				}
			});
			//类目
			$.ajax({
				url : "/admin/category/list",
				type : "post",
				dataType : "json",
				data : {
					"search_projectParentId" : $thiz.val(),
					"start" : 0,
					"length" : 1000
				},
				async : false,
				success : function(response) {
					var html = '<option vaule=""></option>';
					$.each(response.data, function(i, val) {
						html += '<option value="'+val.id+'">' + val.name + '</option>';
					});
					$thiz.closest("form").find(".cascade_categoryId").html(html)
				}
			});

		});

	})
</script>