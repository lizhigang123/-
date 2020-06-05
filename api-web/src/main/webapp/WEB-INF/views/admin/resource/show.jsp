<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-title">资源管理</h3>
		<ul class="page-breadcrumb breadcrumb">
			<li><i class="fa fa-home"></i><a href="#">首页</a><i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">资源管理</a><i class="fa fa-angle-right"></i></li>
			<li><a href="#">资源列表</a></li>
		</ul>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="tabbable tabbable-custom boxless tabbable-reversed">
			<div class="portlet box green">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-gift"></i>资源列表
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
<script src="/js/views/resource.js" type="text/javascript"></script>
<script type="text/javascript">
	var ResourceSl = function() {
		var $resourceGridTree = $('#resourceGridTree'), $modal = $("#entity_modal_layer"), $form = $("#entity_modal_form");

		var initGrideTree = function() {
			var loanTreeUri = '/admin/resource/list';
			// 输入树
			$resourceGridTree.treegrid({
				title : '',
				url : loanTreeUri,
				idField : 'id',
				treeField : 'name',
				parentField : 'parentId',
				animate : true,
				rownumbers : true,
				loadMsg : '数据加载中……',
				pagination : false,

				frozenColumns : [ [ {
					width : '250',
					title : '资源名称',
					field : 'name'
				} ] ],
				columns : [ [ {
					width : '580',
					title : '资源路径',
					field : 'url',
					formatter : function(value, row) {
						return htmlEncode(value);
					}
				}, {
					width : '200',
					title : '资源描述',
					field : 'description'
				}, {
					title : '操作',
					field : 'action',
					width : '400',
					formatter : function(value, row) {
						var siblingsRow = getSblings($resourceGridTree, row);
						var str = '<div style="height:40px;">';
						str += '<sl:permission url="/admin/resource/show"><button class="btn btn-xs entity_show_button" style="margin:5px 5px" data-id="' + row.id + '">查看</button></sl:permission>';
						str += '<sl:permission url="/admin/resource/submit"><button class="btn btn-xs yellow entity_edit_button" style="margin:5px 5px" data-id="' + row.id + '">编辑</button></sl:permission>';
						str += '<sl:permission url="/admin/resource/submit"><button class="btn btn-xs blue entity_child_add_button" style="margin:5px 5px" data-id="' + row.id + '">添加下级</button></sl:permission>';
						if (siblingsRow && siblingsRow.length > 1) {
							if (siblingsRow[0].id != row.id) {
								str += '<sl:permission url="/admin/resource/move"><button class="btn btn-xs btn-success entity_up_button" style="margin:5px 5px" data-id="' + row.id + '">上移<i class="fa fa-caret-square-o-up"></i></button></sl:permission>';
							}
							if (siblingsRow[siblingsRow.length - 1].id != row.id) {
								str += '<sl:permission url="/admin/resource/move"><button class="btn btn-xs btn-info entity_down_button" style="margin:5px 5px" data-id="' + row.id + '">下移 <i class="fa fa-caret-square-o-down"></i></button></sl:permission>';
							}
						}
						if (!row.children) {
							str += '<sl:permission url="/admin/resource/delete"><button class="btn btn-xs red entity_delete_button" style="margin:5px 5px" data-id="' + row.id + '">删除</button></sl:permission>';
						}
						str += '</div>';
						return str;
					}
				} ] ],
				onBeforeLoad : function(row, param) {
					// 树初始化还是子节点加载判断
					if (row) {
						$(this).treegrid('options').url = loanTreeUri + '?parentId=' + row.id;
					} else {
						$(this).treegrid('options').url = loanTreeUri;
					}
				},
				loadFilter : function(data, parentId) {
					if (data && data.data) {
						var resourceData = {
							total : 0,
							rows : []
						};
						$.each(data.data, function(i, row) {
							row._id = row.id;
							row._parentId = row.parentId;
							if (row.childNo >= 1) {
								row.state = 'closed';
							} else {
								row.state = 'open';
							}

							resourceData.total = resourceData.total + 1;
							resourceData.rows.push(row);

						});
						data = resourceData;
						return data
					} else {
						return data;
					}
				}

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
			initShow : function() {
				initGrideTree();
			}

		}
	}();
</script>
<script type="text/javascript">
	$(document).ready(function() {
		//初始化控件
		ResourceSl.initShow();
		Resource.initShow();
	});
</script>