define(function(require, exports, module) {
	var $ = require('jquery');

	/*
	 *  把form表单转换为 JSON格式
	 */
	$.fn.serializeJson = function () {
	    var o = {};
	    var a = this.serializeArray();
	    $.each(a, function () {
	        if (o[this.name]) {
	            if (!o[this.name].push) {
	                o[this.name] = [o[this.name]];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	};

	$.mergeJsonObject = function (jsonbject1, jsonbject2) {
	    var resultJsonObject = {};
	    for (var attr in jsonbject1) {
	        resultJsonObject[attr] = jsonbject1[attr];
	    }
	    for (var attr in jsonbject2) {
	        resultJsonObject[attr] = jsonbject2[attr];
	    }

	    return resultJsonObject;
	};

});