<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-title">接口管理</h3>
		<ul class="page-breadcrumb breadcrumb">
			<li><i class="fa fa-home"></i><a href="#">首页</a><i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">接口管理管理</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">参数管理</a></li>
		</ul>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="tabbable tabbable-custom boxless tabbable-reversed">
			<div class="portlet box green">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-gift"></i>【${project.name}<c:if test="${subProject!=null}">->${subProject.name}</c:if>->${category.name}->${interfaceEntity.name}】接口参数管理
					</div>
					<div class="actions">
						<a href="#" class="btn btn-sm yellow back ajaxify">返回 <i
							class=" fa  fa-history"></i></a>
					</div>
				</div>
				<div class="portlet-body form">
					<div class="form-body">
						<%@ include file="_form.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="/js/views/datatypeselect.js" type="text/javascript"></script>
<script src="/js/views/param.js" type="text/javascript"></script>
<script type="text/javascript">
	var ParamSl = function() {
		var $inputGridTree = $('#inputGridTree'), $outputGridTree = $('#outputGridTree'), $templateParamTable = $('#datatable-list'), $templateParamLayer = $('#template_param_layer'), $modal = $("#entity_modal_layer"), $form = $("#entity_modal_form");
		var initGrideTree = function(id) {
			var loanTreeUri = '/admin/param/list?interfaceId=' + id;
			// 输入树
			$inputGridTree.treegrid({
				title : '',
				idField : 'id',
				treeField : 'name',
				parentField : 'parentId',
				animate : true,
				rownumbers : true,
				loadMsg : '数据加载中……',
				pagination : false,
				frozenColumns : [ [ {
					width : '200',
					title : '参数名',
					field : 'name'
				} ] ],
				columns : [ [ {
					width : '200',
					title : '数据类型',
					field : 'dataType',
					formatter : function(value, row) {
						return htmlEncode(value);
					}
				}, {
					width : '150',
					title : '中文名称',
					field : 'nameDesc'
				}, {
					width : '70',
					title : '是否必填',
					field : 'need'
				}, {
					title : '操作',
					field : 'action',
					width : '500',
					formatter : function(value, row) {
						var siblingsRow = getSblings($inputGridTree, row);
						var str = '<div style="height:40px;">';
						str += '<sl:permission url="/admin/param/show"><button class="btn btn-xs entity_show_button" style="margin:5px 5px" data-id="' + row.id + '">查看</button></sl:permission>';
						str += '<sl:permission url="/admin/param/submit"><button class="btn btn-xs yellow entity_edit_button" style="margin:5px 5px" data-id="' + row.id + '">编辑</button></sl:permission>';
						str += '<sl:permission url="/admin/param/submit"><button class="btn btn-xs blue entity_child_add_button" style="margin:5px 5px" data-id="' + row.id + '">添加下级</button></sl:permission>';
						str += '<sl:permission url="/admin/param/saveParams"><button class="btn btn-xs blue entity_child_add_params_button" style="margin:5px 5px" data-id="' + row.id + '">批量添加子入参</button></sl:permission>';
 						if (siblingsRow && siblingsRow.length > 1) {
							if (siblingsRow[0].id != row.id) {
								str += '<sl:permission url="/admin/param/move"><button class="btn btn-xs btn-success entity_up_button" style="margin:5px 5px" data-id="' + row.id + '">上移<i class="fa fa-caret-square-o-up"></i></button></sl:permission>';
							}
							if (siblingsRow[siblingsRow.length - 1].id != row.id) {
								str += '<sl:permission url="/admin/param/move"><button class="btn btn-xs btn-info entity_down_button" style="margin:5px 5px" data-id="' + row.id + '">下移 <i class="fa fa-caret-square-o-down"></i></button></sl:permission>';
							}
						}
						if (!row.children) {
							str += '<sl:permission url="/admin/param/del"><button class="btn btn-xs red entity_delete_button" style="margin:5px 5px" data-id="' + row.id + '">删除</button></sl:permission>';
						}
						str += '</div>';
						return str;
					}
				}, {
					width : '150',
					title : '示例值',
					field : 'defaultValue',
					hidden : false
				}, {
					width : '580',
					title : '描述',
					field : 'description',
					hidden : false
				} ] ]
			});

			// 输出树
			$outputGridTree.treegrid({
				idField : 'id',
				treeField : 'name',
				parentField : 'parentId',
				animate : true,
				rownumbers : true,
				loadMsg : '数据加载中……',
				pagination : false,
				frozenColumns : [ [ {
					width : '200',
					title : '参数名',
					field : 'name'
				} ] ],
				columns : [ [ {
					width : '200',
					title : '数据类型',
					field : 'dataType',
					formatter : function(value, row) {
						return htmlEncode(value);
					}
				}, {
					width : '150',
					title : '中文名称',
					field : 'nameDesc'
				}, {
					width : '70',
					title : '是否必填',
					field : 'need'
				}, {
					title : '操作',
					field : 'action',
					width : '500',
					formatter : function(value, row) {
						var siblingsRow = getSblings($outputGridTree, row);
						var str = '<div style="height:40px;">';
						str += '<sl:permission url="/admin/param/show"><button class="btn btn-xs entity_show_button" style="margin:5px 5px" data-id="' + row.id + '">查看</button></sl:permission>';
						str += '<sl:permission url="/admin/param/submit"><button class="btn btn-xs yellow entity_edit_button" style="margin:5px 5px" data-id="' + row.id + '">编辑</button></sl:permission>';
						str += '<sl:permission url="/admin/param/submit"><button class="btn btn-xs blue entity_child_add_button" style="margin:5px 5px" data-id="' + row.id + '">添加下级</button></sl:permission>';
						str += '<sl:permission url="/admin/param/saveParams"><button class="btn btn-xs blue entity_child_add_params_button" style="margin:5px 5px" data-id="' + row.id + '">批量添加子出参</button></sl:permission>';
 						if (siblingsRow && siblingsRow.length > 1) {
							if (siblingsRow[0].id != row.id) {
								str += '<sl:permission url="/admin/param/move"><button class="btn btn-xs btn-success entity_up_button" style="margin:5px 5px" data-id="' + row.id + '">上移<i class="fa fa-caret-square-o-up"></i></button></sl:permission>';
							}
							if (siblingsRow[siblingsRow.length - 1].id != row.id) {
								str += '<sl:permission url="/admin/param/move"><button class="btn btn-xs btn-info entity_down_button" style="margin:5px 5px" data-id="' + row.id + '">下移 <i class="fa fa-caret-square-o-down"></i></button></sl:permission>';
							}
						}
						if (!row.children) {
							str += '<sl:permission url="/admin/param/del"><button class="btn btn-xs red entity_delete_button" style="margin:5px 5px" data-id="' + row.id + '">删除</button></sl:permission>';
						}
						str += '</div>';
						return str;
					}
				}, {
					width : '150',
					title : '示例值',
					field : 'defaultValue',
					hidden : false
				}, {
					width : '580',
					title : '描述',
					field : 'description',
					hidden : false
				} ] ]
			});

			$.get(loanTreeUri, function(response) {
				var inputData = {
					total : 0,
					rows : []
				}, outputData = {
					total : 0,
					rows : []
				};
				$.each(response.data, function(i, row) {
					row._id = row.id;
					row._parentId = row.parentId;
					if (row.type == '输入') {
						inputData.total = inputData.total + 1;
						inputData.rows.push(row);
					} else if (row.type = '输出') {
						outputData.total = inputData.total + 1;
						outputData.rows.push(row);
					}
				});

				$inputGridTree.treegrid("loadData", inputData).treegrid("reload")
				$outputGridTree.treegrid("loadData", outputData).treegrid("reload")
			});
		}
		function getSblings($gridTree, row) {
			if (row.parentId) {
				siblingsRow = $gridTree.treegrid("getChildren", row.parentId);// 包含了所有level的子节点
				var siblingsRowTemp = [];
				$.each(siblingsRow, function(i, val) {
					if (row.parentId == val.parentId) {
						siblingsRowTemp.push(val);
					}
				});
				siblingsRow = siblingsRowTemp;
			} else {
				siblingsRow = $gridTree.treegrid("getRoots", row.parentId);
			}
			return siblingsRow;
		}
		return {
			initShow : function(interfaceId) {
				initGrideTree(interfaceId);
			}
		}
	}();
</script>
<script type="text/javascript">
	$(document).ready(function() {
		//初始化控件
		ParamSl.initShow("${interfaceId}");
		Param.initShow("${interfaceId}","${project.id}");
   	});
</script>