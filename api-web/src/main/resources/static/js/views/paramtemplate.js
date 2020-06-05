/**
 * 
 */
var ParamTemplate = function() {
	var $inputGridTree = $('#inputGridTree'), $modal = $("#entity_modal_layer"), $form = $("#entity_modal_form");

 
	var initTreeGridEvent = function(propertiestId) {
		var modalType = "show";// show|edit|add
		var currentTreeGrid = "";// input|output
		var type_input = "输入"
		function showForm(title, values) {
			resetValidateRule($form);
			formClearHandle($form);
			if (values) {
				formLoadHandle($form, values);
			}
			formDisabledHandle($form);
			$modal.find(".modal-title").html(title)
			$modal.find("button.submit").hide();
			$modal.modal("show");
		}
		$("#input_param_add_top").click(function() {
			showForm("新增参数", {
				"propertiestId" : propertiestId,
				"type" : type_input,
				"need" : "必须",
				"visible" : "是"
			});
			formRemoveDisabledHandle($form);
			$modal.find("button.submit").show();
			modalType = "add";
			currentTreeGrid = "input";
		});
		 

		$("#input_grid_tree_row").on("click", ".entity_show_button", function() {
			showForm("查看参数详情", $inputGridTree.treegrid('find', $(this).data("id")));
			modalType = "show";
			currentTreeGrid = "input";
		}).on("click", ".entity_edit_button", function() {
			showForm("编辑参数详情", $inputGridTree.treegrid('find', $(this).data("id")));
			formRemoveDisabledHandle($form);
			$modal.find("button.submit").show();
			modalType = "edit";
			currentTreeGrid = "input";
		}).on("click", ".entity_child_add_button", function() {
			var currentRow = $inputGridTree.treegrid('find', $(this).data("id"));
			showForm("新增子参数", {
				"parentId" : currentRow.id,
				"type" : type_input,
				"propertiestId" : propertiestId,
				"need" : "必须",
				"visible" : "是"
			});
			formRemoveDisabledHandle($form);
			$modal.find("button.submit").show();
			modalType = "add";
			currentTreeGrid = "input";
		}).on("click", ".entity_delete_button", function() {
			var currentRow = $inputGridTree.treegrid('find', $(this).data("id"));
			deleteRow(currentRow.id);
		}).on("click", ".entity_up_button", function() {
			var currentRow = $inputGridTree.treegrid('find', $(this).data("id"));
			moveRow($inputGridTree, currentRow, 1)
		}).on("click", ".entity_down_button", function() {
			var currentRow = $inputGridTree.treegrid('find', $(this).data("id"));
			moveRow($inputGridTree, currentRow, -1)
		});

		 

		$modal.find("button.submit").click(function() {
			if (formValidate($form)) {
				$.post("/admin/project/paramtemplate/submit", $form.serialize(), function(response) {
					if (response.code == "000000") {
						if (modalType == "edit") {
							if (currentTreeGrid == "input") {
								$inputGridTree.treegrid("update", {
									id : response.data.id,
									row : response.data
								})
							}
						} else if (modalType == "add") {
							if (currentTreeGrid == "input") {
								$inputGridTree.treegrid("append", {
									parent : response.data.parentId,
									data : [ response.data ]
								})
							}
						}
						$modal.modal("hide");
					}
					slalert(response.message);
				});
			}
		});
	}

	function deleteRow(id) {
		slconfirm("确定要删除此参数吗,删除后不可找回?", function() {
			$.post("/admin/param/delet/template", {
				"id" : id
			}, function(response) {
				if (response.code = '000000') {
					reload();
				}
				slalert(response.message);
			})
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
	/**
	 * @param type
	 *            >0上移 <=0下移
	 */
	function moveRow($treegrid, row, type) {
		var siblingsRow = getSblings($treegrid, row);
		var updateRowIds = [];
		for (var i = 0; i < siblingsRow.length; i++) {
			var val = siblingsRow[i];
			if (val.id == row.id) {
				if (type > 0 && i > 0) {// 上移
					updateRowIds[i] = updateRowIds[i - 1];
					updateRowIds[i - 1] = val.id;

				} else if (type <= 0 && i < siblingsRow.length - 1) {// 下移
					updateRowIds[i] = siblingsRow[i + 1].id;
					updateRowIds[i + 1] = val.id;

					i++;
				}
			} else {
				updateRowIds.push(val.id);
			}
		}

		$.ajax({
			url : "/admin/param/paramtemplate/move",
			traditional : true,
			type : "post",
			data : {
				"ids" : updateRowIds
			},
			success : function(response) {
				if (response.code = '000000') {
					reload();
				}
			}
		})
	}

	return {
		initShow : function(propertiestId) {
 			initTreeGridEvent(propertiestId);
		}

	}
}();