var dataTypeSelect = (function() {
	$(".select2me-data-type").select2({
		placeholder : "请选择...",
		allowClear : true,
		//组装默认数据
		data: {
	        results: [
	            { id: "Bigdecimal", text: "Bigdecimal" },
	            { id: "int[]", text: "int[]" },
	            { id: "int", text: "int" },
	            { id: "String[]", text: "String[]" },
	            { id: "Double", text: "Double" },
	            { id: "List", text: "List" },
	            { id: "String", text: "String" },
	            { id: "Integer", text: "Integer" },
	            { id: "Long", text: "Long" },
	            { id: "List<Map<String, Object>>", text: "List<Map<String, Object>>" },
	            { id: "Map<String, Object>", text: "Map<String, Object>" },
	            { id: "List<Map<String,Integer>>", text: "List<Map<String,Integer>>" }
		        ]
	    },
	    //这里初始化加载结果显示
	    initSelection: function (element, callback) {
            var data = [{id: element.val(), text: element.val()}];
            callback({id: element.val(), text: element.val()});
        },
	    // 创建搜索结果（使用户可以输入匹配值以外的其它值）
	    createSearchChoice : function(term, data) {           
	        return { id: term, text: term };
	    }
	});
})();