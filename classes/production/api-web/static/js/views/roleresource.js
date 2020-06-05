/**
 * 
 */
var Resource = function() {
	var $resourceGridTree = $('#resourceGridTree'), $modal = $("#entity_modal_layer"), $form = $("#entity_modal_form");

	var initGrideTree = function() {
		var loanTreeUri = '/admin/resource/listResource';
		// 输入树
		$resourceGridTree.treegrid({
			title : '',
 			idField : 'id',
			treeField : 'name',
			parentField : 'parentId',
			animate : true,
			rownumbers : true,
			loadMsg : '数据加载中……',
			pagination : false,
			checkbox : true,
			frozenColumns : [ [ {
				width : '380px',
				title : '资源名称',
				field : 'name'
			} ] ],
			columns : [ [ {
				width : '680px',
				title : '资源路径',
				field : 'url',
				formatter : function(value, row) {
					return htmlEncode(value);
				}
			} ] ],
 			onCheckNode : function(row, checked) {
				if (checked) {
					var parent = $(this).treegrid('getParent', row.id);
					if (parent) {
						var children = parent.children;
						for (var i = 0; i < children.length; i++) {
							var node = children[i];
							if (node.description == "列表查看") {
								$(this).treegrid('checkNode', node.id);
							}
						}
					}
				} else {
					if (row.description == "列表查看") {
						var parent = $(this).treegrid('getParent', row.id);
						if (parent) {
							var children = parent.children;
							for (var i = 0; i < children.length; i++) {
								var node = children[i];
								$(this).treegrid('uncheckNode', node.id);
							}
						}

					}
				}
			}
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

				inputData.total = inputData.total + 1;
				inputData.rows.push(row);

			});

			$resourceGridTree.treegrid("loadData", inputData).treegrid("reload")
		});

	}

	var initTreeGridEvent = function() {

		$modal.find("button.submit").click(function() {
			if (formValidate($form)) {
				var $hide_wapper = $("#role_create_hide");
				$hide_wapper.html("");
				var value = $resourceGridTree.treegrid('getCheckedNodes');
				var resourceIds = "";
				for ( var i in value) {
					resourceIds += value[i].id + ((i == value.length - 1) ? "" : ",");
				}
				$hide_wapper.append('<input name="resourceIds" value="' + resourceIds + '" />');
				$.post("/admin/role/submit", $form.serialize(), function(response) {
					if (response.code == "000000") {
						slalert(response.message);
						redirectHandle("/admin/role")
					}
				});
			}
		});
	}

	return {
		initShow : function() {
			initGrideTree();
			initTreeGridEvent();
		}

	}
}();