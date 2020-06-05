/***
Wrapper/Helper Class for datagrid based on jQuery Datatable Plugin
***/
var Datatable = function () {

    var tableOptions; // main options
    var dataTable; // datatable object
    var table; // actual table jquery object
    var tableContainer; // actual table container object
    var tableWrapper; // actual table wrapper jquery object
    var tableInitialized = false;
    var ajaxParams = {}; // set filter mode
    var the;

    var countSelectedRecords = function () {
        var selected = $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        var text = tableOptions.dataTable.language.metronicGroupActions;
        if (selected > 0) {
            $('.table-group-actions > span', tableWrapper).text(text.replace("_TOTAL_", selected));
        } else {
            $('.table-group-actions > span', tableWrapper).text("");
        }
    }

    return {

        //main function to initiate the module
        init: function (options) {

            if (!$().dataTable) {
                return;
            }

            the = this;

            // default settings
            options = $.extend(true, {
                src: "", // actual table  
                filterApplyAction: "filter",
                filterCancelAction: "filter_cancel",
                resetGroupActionInputOnSuccess: true,
                dataTable: {
                    "dom": "<'table-scrollable't><'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>", // datatable layout
                    "lengthMenu": [ // set available records per page
                        [20, 50, 150, 300, 500],
                        [20, 50, 150, 300, 500]
                    ],
                    "pageLength": 20, // default records per page
                    "language": { // language settings
                        // metronic spesific
                        "metronicGroupActions": "_TOTAL_ records selected:  ",
                        "metronicAjaxRequestGeneralError": "无法完成请求,请检查你的网络连接!",
                    	"infoFiltered":"",
                        // data tables spesific
                        "processing": '<img src="' + Metronic.getGlobalImgPath() + 'loading-spinner-grey.gif"/><span>&nbsp;&nbsp;加载中...</span>',
                        "lengthMenu": "<span class='seperator'>页  |</span>每页显示 _MENU_ 条记录", 
                        "info": "<span class='seperator'>|</span>共 _TOTAL_ 条记录",
                        "infoEmpty": "| 共 0条记录",
                        "emptyTable": "没有可用的数据表",
                        "zeroRecords": "没有找到符合条件的数据",
                        "paginate": {
                            "previous": "上一页",
                            "next": "下一页",
                            "last": "尾页",
                            "first": "首页",
                            "page": "当前第",
                            "pageOf": "页,共"
                        }
                    },
                    
					"orderCellsTop": true,
                    "columnDefs": [{ // define columns sorting options(by default all columns are sortable extept the first checkbox column)
                        'orderable': false,
                        'targets': [0]
                    }],

                    "pagingType": "bootstrap_extended", // pagination type(bootstrap, bootstrap_full_number or bootstrap_extended)
                    "autoWidth": false, // disable fixed width and enable fluid table
                    "processing": true, // enable/disable display message box on record load
                    "serverSide": true, // enable/disable server side ajax loading
                    "ordering" : false,
                    "searching" : false,

                    "ajax": { // define ajax settings
                        "url": "", // ajax URL
                        "type": "POST", // request type
                        "timeout": 20000,
                        "data": function (data) { // add request parameters before submit
                            $.each(ajaxParams, function (key, value) {
                                data[key] = value;
                            });
                        },
                        "dataSrc": function (res) { // Manipulate the data returned from the server
                            if (res.customActionMessage) {
                                Metronic.alert({
                                    type: (res.customActionStatus == 'OK' ? 'success' : 'danger'),
                                    icon: (res.customActionStatus == 'OK' ? 'check' : 'warning'),
                                    message: res.customActionMessage,
                                    container: tableWrapper,
                                    place: 'prepend'
                                });
                            }

                            if (res.customActionStatus) {
                                if (tableOptions.resetGroupActionInputOnSuccess) {
                                    $('.table-group-action-input', tableWrapper).val("");
                                }
                            }

                            if ($('.group-checkable', table).size() === 1) {
                                $('.group-checkable', table).attr("checked", false);
                                $.uniform.update($('.group-checkable', table));
                            }

                            if (tableOptions.onSuccess) {
                                tableOptions.onSuccess.call(undefined, the);
                            }
                            //新增服务器数据获取函数 绑定到datatable对象上 参数为服务器端相应json数据
                            if(tableOptions.onLoadSuccess){
                            	tableOptions.onLoadSuccess.call(the, res);
                            }

                            return res.data;
                        },
                        "error": function () { // handle general connection errors
                            if (tableOptions.onError) {
                                tableOptions.onError.call(undefined, the);
                            }

                            Metronic.alert({
                                type: 'danger',
                                icon: 'warning',
                                message:  $('.page-content .page-content-body').html(viewNotFound(options.dataTable.ajax.url,500)),
                                container: tableWrapper,
                                place: 'prepend'
                            });

                            $('.dataTables_processing', tableWrapper).remove();
                        }
                    },

                    "drawCallback": function (oSettings) { // run some code on table redraw
                        if (tableInitialized === false) { // check if table has been initialized
                            tableInitialized = true; // set table initialized
                            table.show(); // display table
                        }
                        Metronic.initUniform($('input[type="checkbox"]', table)); // reinitialize uniform checkboxes on each table reload
                        countSelectedRecords(); // reset selected records indicator
                    },
                    "preDrawCallback" : function(settings){
                    	index = settings._iDisplayStart + 1;
        	     	}
                }
            }, options);

            tableOptions = options;

            // create table's jquery object
            table = $(options.src);
            tableContainer = table.parents(".table-container");

            // apply the special class that used to restyle the default datatable
            var tmp = $.fn.dataTableExt.oStdClasses;

            $.fn.dataTableExt.oStdClasses.sWrapper = $.fn.dataTableExt.oStdClasses.sWrapper + " dataTables_extended_wrapper";
            $.fn.dataTableExt.oStdClasses.sFilterInput = "form-control input-small input-sm input-inline";
            $.fn.dataTableExt.oStdClasses.sLengthSelect = "form-control input-xsmall input-sm input-inline";

            // initialize a datatable
            dataTable = table.dataTable(options.dataTable);

            // revert back to default
            $.fn.dataTableExt.oStdClasses.sWrapper = tmp.sWrapper;
            $.fn.dataTableExt.oStdClasses.sFilterInput = tmp.sFilterInput;
            $.fn.dataTableExt.oStdClasses.sLengthSelect = tmp.sLengthSelect;

            // get table wrapper
            tableWrapper = table.parents('.dataTables_wrapper');

            // build table group actions panel
            if ($('.table-actions-wrapper', tableContainer).size() === 1) {
                $('.table-group-actions', tableWrapper).html($('.table-actions-wrapper', tableContainer).html()); // place the panel inside the wrapper
                $('.table-actions-wrapper', tableContainer).remove(); // remove the template container
            }
            // handle group checkboxes check/uncheck
            $('.group-checkable', table).change(function () {
                var set = $('tbody > tr > td:nth-child(1) input[type="checkbox"]:not(.checkedExclude)', table);
                var checked = $(this).is(":checked");
                $(set).each(function () {
                    $(this).attr("checked", checked);
                });
                $.uniform.update(set);
                countSelectedRecords();
            });

            // handle row's checkbox click
            table.on('change', 'tbody > tr > td:nth-child(1) input[type="checkbox"]', function () {
                countSelectedRecords();
            }); 

            // handle filter submit button click
            table.on('click', '.filter-submit', function (e) {
                e.preventDefault();
                the.submitFilter();
            });
            
            $("#search").on('click', function(e){
            	 e.preventDefault();
                 the.submitFilter();
            });
            
            $("#reset").on('click', function(e){
           	 	e.preventDefault();
                the.resetFilter();
            });
            
            // handle filter cancel button click
            table.on('click', '.filter-cancel', function (e) {
                e.preventDefault();
                the.resetFilter();
            });
            
            
        },

        submitFilter: function () {
            the.setAjaxParam("action", tableOptions.filterApplyAction);
            
            $.each($('#search_form').serializeArray(), function(i, field) {
            	the.setAjaxParam(field.name, field.value);
        	});

            // get all typeable inputs
//            $('textarea.form-filter, select.form-filter, input.form-filter:not([type="radio"],[type="checkbox"])', table).each(function () {
//                the.setAjaxParam($(this).attr("name"), $(this).val());
//            });
//
//            // get all checkboxes
//            $('input.form-filter[type="checkbox"]:checked', table).each(function () {
//                the.addAjaxParam($(this).attr("name"), $(this).val());
//            });
//
//            // get all radio buttons
//            $('input.form-filter[type="radio"]:checked', table).each(function () {
//                the.setAjaxParam($(this).attr("name"), $(this).val());
//            });

            dataTable.fnDraw();
        },
        
        refresh : function() {
        	dataTable.fnDraw();
        },

        resetFilter: function () {
        	formClearHandle($('#search_form'));
//            $('textarea.form-filter, select.form-filter, input.form-filter', table).each(function () {
//                $(this).val("");
//            });
//            $('input.form-filter[type="checkbox"]', table).each(function () {
//                $(this).attr("checked", false);
//            });
        	//额外特殊化的清空操作
        	if(arguments.length>0){
        		var extra = arguments[0]
        		if(jQuery.isFunction(extra)){
        			extra.call(null);
        		}
        	}
        	
            the.clearAjaxParams();
          //清空时把没有默认值的下拉框 查询条件仍然需要从表单内获取(特殊处理那些选择条件没有默认全选的 需要清空后仍然有值)
            $.each($('#search_form').serializeArray(), function(i, field) {
            	the.setAjaxParam(field.name, field.value);
        	});            
            the.addAjaxParam("action", tableOptions.filterCancelAction);
            dataTable.fnDraw();
        },

        getSelectedRowsCount: function () {
            return $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        },

        getSelectedRows: function () {
            var rows = [];
            $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).each(function () {
                rows.push($(this).val());
            });

            return rows;
        },
        //获取选中行的数据
        getSelectedRowData : function(){
            var data = [];
            $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).each(function () {
            	data.push(dataTable.fnGetData($(this).closest("tr")));
            });
            return data;
        },

        setAjaxParam: function (name, value) {
            ajaxParams[name] = value;
        },

        addAjaxParam: function (name, value) {
            if (!ajaxParams[name]) {
                ajaxParams[name] = [];
            }

            skip = false;
            for (var i = 0; i < (ajaxParams[name]).length; i++) { // check for duplicates
                if (ajaxParams[name][i] === value) {
                    skip = true;
                }
            }

            if (skip === false) {
                ajaxParams[name].push(value);
            }
        },

        clearAjaxParams: function (name, value) {
            ajaxParams = {};
        },

        getDataTable: function () {
            return dataTable;
        },

        getTableWrapper: function () {
            return tableWrapper;
        },

        gettableContainer: function () {
            return tableContainer;
        },

        getTable: function () {
            return table;
        }

    };

};