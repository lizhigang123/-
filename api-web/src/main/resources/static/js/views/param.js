/**
 * 
 */
var Param = function() {
	var $inputGridTree = $('#inputGridTree'), $outputGridTree = $('#outputGridTree'), $templateParamTable = $('#datatable-list'), $addParamTable = $('#datatable-paramlist'), $addParamLayer = $('#add_param_layer'), $paramsForm = $('#entity_modal_params_form'), $paramsLayer = $('#params_modal_layer'), $templateParamLayer = $('#template_param_layer'), $modal = $("#entity_modal_layer"), $form = $("#entity_modal_form");

	var initTreeGridEvent = function(interfaceId,projectId) {
		var modalType = "show";// show|edit|add
		var currentTreeGrid = "";// input|output
		var type_input = "输入", type_output = "输出";
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
		function showAddParamsForm(title, values) {
			resetValidateRule($paramsForm);
			formClearHandle($paramsForm);
			$("#paramIds").select2('val', '');
			if (values) {
				$("#interfaceId").attr("value", values.interfaceId);
				$("#type").attr("value", values.type);
				$("#parentId").attr("value", values.parentId);
			}
			formDisabledHandle($paramsForm);
			$paramsLayer.find(".modal-title").html(title)
			$paramsLayer.find("button.submit").hide();
			$paramsLayer.modal("show");
		}
		$("#input_param_add_top").click(function() {
			showForm("新增参数", {
				"interfaceId" : interfaceId,
				"type" : type_input,
				"need" : "必须",
				"visible" : "是"
			});
			formRemoveDisabledHandle($form);
			$modal.find("button.submit").show();
			modalType = "add";
			currentTreeGrid = "input";
		});
		var pageSize = 10;
		$(".multiSelect").select2({
			multiple : true,
			minimumInputLength : 1,
			ajax : {
				url : "/admin/param/match",
				data : function(term, page) {
					return {
						"search_name" : term,
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
		$("#in_params_add_top").click(function() {
			showAddParamsForm("批量添加入参", {
				"interfaceId" : interfaceId,
				"type" : type_input
			});
			formRemoveDisabledHandle($paramsForm);
			$paramsLayer.find("button.submit").show();
		});
		$("#out_params_add_top").click(function() {
			showAddParamsForm("批量添加出参", {
				"interfaceId" : interfaceId,
				"type" : type_output
			});
			formRemoveDisabledHandle($paramsForm);
			$paramsLayer.find("button.submit").show();
		});
		$paramsLayer.find("button.submit").click(function() {
			var data = $(".multiSelect").select2("data");
 			if (data.length>0) {
				var paramIds = "";
				for (var i = 0; i < data.length; i++) {
					paramIds += data[i].id + ","
				}
				if (formValidate($paramsForm)) {
					$.post("/admin/param/saveParams?interfaceId=" + interfaceId + "&type=" + $("#type").val() + "&parentId=" + $("#parentId").val() + "&paramIds=" + paramIds, function(response) {
						slalert(response.message);
						if (response.code == '000000') {
							$paramsLayer.modal("hide").one("hidden.bs.modal", function() {
								reload();
							});
						}
					})
				}
			} else {
				slalert('请至少选择一个参数提交！');
			}

		});
		$("#seach-param").click(function() {
			var grid = new Datatable();
			var name = $("#param-name").val();
 			if (name) {
				grid.init({
					src : $addParamTable,
					dataTable : {
						"ajax" : {
							"url" : "/admin/param/listall?search_name=" + name,
						},
						"bDestroy" : true,
						"lengthMenu" : [ [ 5, 6, 7, 8, 10 ], [ 5, 6, 7, 8, 10 ] ],
						"pageLength" : 5, // default records per page
						"columns" : [ {
							title : '序号',
							data : 'id',
							"visible" : false,
						}, {
							title : '接口名称',
							data : 'interfaceName',
						}, {
							title : '参数名称',
							data : 'name',
						}, {
							title : '参数中文名称',
							data : 'nameDesc',
						}, {
							title : '参数类型',
							data : 'dataType',
						}, {
							"title" : "操作",
							"render" : function(val, type, row) {
								var html = "";
								html += '<button type="button" href=""  class="btn btn-sm green-jungle add_in_param"  data-id="' + row.id + '">选择</button>';
								return html;
							}
						} ]
					}
				});
				$addParamLayer.modal("show");
			} else {
				slalert('请输入参数名称再查询!');
			}
		});

		$addParamTable.on("click", "button.add_in_param", function() {
			var row = $addParamTable.DataTable().row($(this).closest("tr")).data();
			$("#param-name-desc").val(row.nameDesc);
			$("#param-data-type").select2('val', row.dataType);
			$addParamLayer.modal("hide");
		});
		$("#in_template_param_add_top").click(function() {
			var grid = new Datatable();
			grid.init({
				src : $templateParamTable,
				dataTable : {
					"ajax" : {
						"url" : "/admin/project/template/lists?search_type=入参模版&search_projectId="+projectId,
					},
					"bDestroy" : true,
					"lengthMenu" : [ [ 5, 6, 7, 8, 10 ], [ 5, 6, 7, 8, 10 ] ],
					"pageLength" : 5, // default records per page
					"columns" : [ {
						title : '序号',
						data : 'id',
						"visible" : false,
					}, {
						title : '属性名称',
						data : 'name',
					}, {
						title : '属性中文名称',
						data : 'nameDesc',
					}, {
						"title" : "操作",
						"render" : function(val, type, row) {
							var html = "";
							html += '<button type="button" href=""  class="btn btn-sm green-jungle add_in_template"  data-id="' + row.id + '">选择</button>';
							return html;
						}
					} ]
				}
			});
			$templateParamLayer.modal("show");
		});
		$("#out_template_param_add_top").click(function() {
			var grid = new Datatable();
			grid.init({
				src : $templateParamTable,
				dataTable : {
					"ajax" : {
						"url" : "/admin/project/template/lists?search_type=出参模版&search_projectId="+projectId,
					},
					"bDestroy" : true,
					"lengthMenu" : [ [ 5, 6, 7, 8, 10 ], [ 5, 6, 7, 8, 10 ] ],
					"pageLength" : 5, // default records per page
					"columns" : [ {
						title : '序号',
						data : 'id',
						"visible" : false,
					}, {
						title : '属性名称',
						data : 'name',
					}, {
						title : '属性中文名称',
						data : 'nameDesc',
					}, {
						"title" : "操作",
						"render" : function(val, type, row) {
							var html = "";
							html += '<button type="button" href="" class="btn btn-sm green-jungle add_out_template"   data-id="' + row.id + '">选择</button>';
							return html;
						}
					} ]
				}
			});
			$templateParamLayer.modal("show");
		});

		$templateParamTable.on("click", "button.add_in_template", function() {
			var row = $templateParamTable.DataTable().row($(this).closest("tr")).data();
			var type = "输入";
			$.post("/admin/param/submit/templat", {
				propertiesId : row.id,
				interfaceId : interfaceId,
				type : type
			}, function(response) {
				$templateParamLayer.modal("hide").one("hidden.bs.modal", function() {
					reload();
				});
				slalert(response.message);
			});
		});
		$templateParamTable.on("click", "button.add_out_template", function() {
			var row = $templateParamTable.DataTable().row($(this).closest("tr")).data();
			var type = "输出";
			$.post("/admin/param/submit/templat", {
				propertiesId : row.id,
				interfaceId : interfaceId,
				type : type
			}, function(response) {
				$templateParamLayer.modal("hide").one("hidden.bs.modal", function() {
					reload();
				});
				slalert(response.message);
			});
		});

		$("#output_param_add_top").click(function() {
			showForm("新增参数", {
				"interfaceId" : interfaceId,
				"type" : type_output,
				"need" : "必须",
				"visible" : "是"
			});
			formRemoveDisabledHandle($form);
			$modal.find("button.submit").show();
			modalType = "add";
			currentTreeGrid = "output";
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
				"interfaceId" : currentRow.interfaceId,
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
		}).on("click", ".entity_child_add_params_button", function() {
			var currentRow = $inputGridTree.treegrid('find', $(this).data("id"));
			showAddParamsForm("批量添加子入参", {
				"parentId" : currentRow.id,
				"type" : type_input,
				"interfaceId" : currentRow.interfaceId
			});
			formRemoveDisabledHandle($paramsForm);
			$paramsLayer.find("button.submit").show();
		});

		$("#output_grid_tree_row").on("click", ".entity_show_button", function() {
			showForm("查看参数详情", $outputGridTree.treegrid('find', $(this).data("id")));
			modalType = "show";
			currentTreeGrid = "output";
		}).on("click", ".entity_edit_button", function() {
			showForm("编辑参数详情", $outputGridTree.treegrid('find', $(this).data("id")));
			formRemoveDisabledHandle($form);
			$modal.find("button.submit").show();
			modalType = "edit";
			currentTreeGrid = "output";
		}).on("click", ".entity_child_add_button", function() {
			var currentRow = $outputGridTree.treegrid('find', $(this).data("id"));
			showForm("新增子参数", {
				"parentId" : currentRow.id,
				"type" : type_output,
				"interfaceId" : currentRow.interfaceId,
				"need" : "必须",
				"visible" : "是"
			});
			formRemoveDisabledHandle($form);
			$modal.find("button.submit").show();
			modalType = "add";
			currentTreeGrid = "output";
		}).on("click", ".entity_delete_button", function() {
			var currentRow = $outputGridTree.treegrid('find', $(this).data("id"));
			deleteRow(currentRow.id);
		}).on("click", ".entity_up_button", function() {
			var currentRow = $outputGridTree.treegrid('find', $(this).data("id"));
			moveRow($outputGridTree, currentRow, 1)
		}).on("click", ".entity_down_button", function() {
			var currentRow = $outputGridTree.treegrid('find', $(this).data("id"));
			moveRow($outputGridTree, currentRow, -1)
		}).on("click", ".entity_child_add_params_button", function() {
			var currentRow = $outputGridTree.treegrid('find', $(this).data("id"));
			showAddParamsForm("批量添加子出参", {
				"parentId" : currentRow.id,
				"type" : type_output,
				"interfaceId" : currentRow.interfaceId
			});
			formRemoveDisabledHandle($paramsForm);
			$paramsLayer.find("button.submit").show();
		});

		$modal.find("button.submit").click(function() {
			if (formValidate($form)) {
				$.post("/admin/param/submit", $form.serialize(), function(response) {
					if (response.code == "000000") {
						if (modalType == "edit") {
							if (currentTreeGrid == "input") {
								$inputGridTree.treegrid("update", {
									id : response.data.id,
									row : response.data
								})
							} else if (currentTreeGrid == "output") {
								$outputGridTree.treegrid("update", {
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
							} else if (currentTreeGrid == "output") {
								$outputGridTree.treegrid("append", {
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
			$.post("/admin/param/del", {
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
			url : "/admin/param/move",
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
		initShow : function(interfaceId,projectId) {
			initTreeGridEvent(interfaceId,projectId);
		}

	}
}();