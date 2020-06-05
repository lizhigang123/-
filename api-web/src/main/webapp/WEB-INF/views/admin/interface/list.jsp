<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-title">接口管理</h3>
		<ul class="page-breadcrumb breadcrumb">
			<li><i class="fa fa-home"></i><a href="#">首页</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">接口管理</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">接口列表</a></li>
		</ul>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-table"></i><span class="portlet-caption-text">接口列表</span>
				</div>
				<div class="actions">
					<button class="btn yellow btn-sm" id="search">
						<i class="fa fa-search"></i>查询
					</button>
					<button class="btn blue btn-sm" id="reset">
						<i class="fa fa-refresh "></i>清空查询
					</button>
					<sl:permission url="/admin/interface/submit">
						<button class="btn green-jungle btn-sm" id="create-entity-button">
							<i class="fa fa-plus"></i>新增接口
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
										<select class="form-control cascade_projectId" name="search_projectId">
											<option value=""></option>
											<c:forEach items="${projects.data }" var="item">
												<option value="${item.id }">${item.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<!-- 
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">分支</label>
									<div class="col-md-6">
										<select class="form-control cascade_branchId"
											name="search_branchId"></select>
									</div>
								</div>
							</div>
							 -->
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">类目</label>
									<div class="col-md-6">
										<select class="form-control cascade_categoryId" name="search_categoryId"></select>
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">是否可见</label>
									<div class="col-md-6">
										<select class="form-control" name="search_visible">
											<option value=""></option>
											<option value="是">是</option>
											<option value="否">否</option>
										</select>
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
									<label class="col-md-4 control-label">是否需要授权</label>
									<div class="col-md-6">
										<select class="form-control" name="search_authorize">
											<option value=""></option>
											<option value="1">需要授权</option>
											<option value="0">不需要授权</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">版本号</label>
									<div class="col-md-6">
										<input name="search_v" class="form-control" />
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label class="col-md-4 control-label">状态</label>
									<div class="col-md-6">
										<select class="form-control" name="search_status">
											<option value=""></option>
											<option value="未发布">未发布</option>
											<option value="发布">发布</option>
											<option value="作废">作废</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row" id="batch_finish_export" style="display: block;">
							<div class="col-md-2">
							<button type="button" class="btn green" id="batchExport" style="margin-top: 10px">批量导出</button>
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
<iframe src = "" id = "download_iframe" style="display:none;"></iframe>
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
					"url" : "/admin/interface/list",
				},
				"columns" : [ {
					"title" : "全选"+"<input type='checkbox' class='group-checkable checkAll' id='checkAll'>",
					"orderable" : false,
					"render" : function(val, type, row) {
						return "<input type='checkbox' class='checkAll' name='checkbox' value='"+row.id+"'>";
						
					}
				}, {
					"title" : "项目",
					"data" : "projectName"
				}, {
					"title" : "类目",
					"data" : "categoryName"
				}, {
					"title" : "接口名", 
					"data" : "name",
					"render" : function(val, type, row) {
						var html = "";
						if (row.status == '发布') {
							html += '<a class=show-detail href="/api/interface/'+row.id+'" target="_blank">' + row.name + '</a>';
						} else {
							html += row.name;
						}
						return html;
					}
				}, {
					"title" : "访问路径",
					"data" : "url"
				}, {
					"title" : "是否需要授权",
					"data" : "authorize",
					"render" : function(val, type, row) {
						return val ? '需要授权' : '不需要授权';
					}
				}, {
					"title" : "接口描述",
					"visible" : false,
					"data" : "description"
				}, {
					"title" : "版本号",
					"data" : "v"
				}, {
					"title" : "状态",
					"data" : "status"
				}, {
					"title" : "是否可见",
					"data" : "visible",
					"render" : function(val, type, row) {
						return val == '是' ? "可见" : "不可见";
					}
				}, {
					"title" : "操作",
					"render" : function(val, type, row) {
						var html = "";
						html += '<sl:permission url="/admin/interface/findOne"><button type="button" href="" class="btn btn-sm green-jungle  show_button"><i class=""></i>查看</button></sl:permission>';
						html += '<sl:permission url="/admin/interface/submit"><button type="button" href="" class="btn btn-sm yellow  edit_button"><i class=""></i>修改</button></sl:permission>';
						html += '<sl:permission url="/admin/param/show"><button href="/admin/param/show?interfaceId=' + row.id + '" type="button" class="btn btn-sm ajaxify"><i class=""></i>参数管理</button></sl:permission>';
						//发布功能
						if (row.status == '未发布') {

							html += '<sl:permission url="/admin/interface/release"><button href="/admin/interface/release?interfaceId=' + row.id + '" type="button" class="blue btn btn-sm status-change-button"><i class=""></i>发布</button></sl:permission>';
						} else if (row.status == '发布') {

							html += '<sl:permission url="/admin/interface/revoke"><button href="/admin/interface/revoke?interfaceId=' + row.id + '" type="button" class="red btn btn-sm status-change-button"><i class=""></i>作废</button></sl:permission>';
						}
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

		function showForm(title, id) {
			resetValidateRule($form);
			$form.find(".cascade_branchId,.cascade_categoryId").html("");
			formClearHandle($form);
			formRemoveDisabledHandle($form);
			if (id) {
				var ob = findOne(id, "/admin/interface/findOne").data;
				formLoadHandle($form, ob);
				$form.find("select").change();
				formLoadHandle($form, ob);
			}
			$modal.find(".modal-title").html(title)
			$modal.find("button.submit").show();
			$modal.modal("show");
		}

		//创建
		$("#create-entity-button").click(function() {
			showForm("创建接口");
		});

		//提交
		$modal.find("button.submit").click(function() {
			if (formValidate($form)) {
				$.post("/admin/interface/submit", $form.serialize(), function(response) {
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
			showForm("查看接口", row.id);
			formDisabledHandle($form);
			$modal.find("button.submit").hide();
		}).on("click", "button.edit_button", function() {//编辑
			var row = datatable.row($(this).closest("tr")).data();
			row.startDate = new Date(row.startDate).format();
			showForm("编辑接口", row.id);
			$form.find(".cascade_projectId,.cascade_branchId").attr("disabled", "disabled");
		}).on("click", "button.status-change-button", function() {
			var $thiz = $(this);
			slconfirm("确定" + $thiz.html() + "此接口", function() {
				$.get($thiz.attr("href"), function(response) {
					slalert(response.message);
					if (response.code == '000000') {
						grid.refresh();
					}
				})

			})
		}).on("change",".checkAll",function(){
			/* if (this.checked) {
				$("input[name = 'checkbox']").each(function(){
					$(this).parent().attr("class","checked");
				})
				
			}else{
				$(".checkOne").parent().attr("class","")
			} */
			$("#checkAll").parent().attr("class","")
			if($("input[name='checkbox']").length==$("input[name='checkbox']:checked").length){
				$("#checkAll").parent().attr("class","checked")
			}else{
				$("#checkAll").parent().attr("class","")
			}
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
						html += '<option value="' + val.id + '">' + val.name + '</option>';
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
						html += '<option value="' + val.id + '">' + val.name + '</option>';
					});
					$thiz.closest("form").find(".cascade_categoryId").html(html)
				}
			});

		});
		
		/* $(".checkAll").click(function(){
			if (this.checked) {
				$("input[name = 'checkbox']").each(function(){
					$(this).parent().attr("class","checked");
				})
				
			}else{
				$(".checkOne").parent().attr("class","")
			}
		}) */
		$("#batchExport").click(function(){
			var interfaceIdList = [];
			$("input[name = 'checkbox']:checked").each(function(){
				interfaceIdList.push($(this).val());
			})
			if(interfaceIdList==""){
				slalert("请选择需要导出的接口");
				return false;
			}
			$.post("/api/reduce",{"interfaceIds":interfaceIdList.toString()},function(data){
				var path = data.message;
				$("#download_iframe").attr("src","/api/downLoadZip?path="+path);
			})
		})
	})
</script>