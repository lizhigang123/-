var contextPath = getContextPath();

function buildComboxPath(name) {
	return contextPath + '/combox' + name;
}

// 获取上下文路径
function getContextPath() {
	return "";
}

/**
 * html 转移
 * 
 * @param t
 * @returns
 */
function htmlEncode(t){
	var e=document.createElement("div");
	return e.appendChild(document.createTextNode(t)),e.innerHTML;
}
/**
 * html 反转移
 * 
 * @param t
 * @returns
 */
function htmlDecode(t){
	var e=document.createElement("div");
	return e.innerHTML=t,e.innerHTML;
}

// 跳转处理
/**
 * url 待跳转地址,跳转后执行回调 notAddToAjaxifyHistory 默认加入队列
 */
var redirectHandleWithCallback = function(url,notAddToAjaxifyHistory,func) {
	var pageContent = $('.page-content');
	var pageContentBody = $('.page-content .page-content-body');
	Metronic.startPageLoading();

	if (Metronic.getViewPort().width < 992 && $('.page-sidebar').hasClass("in")) {
		$('.page-header .responsive-toggler').click();
	}
	$.ajax({
		type : "GET",
		cache : false,
		url : contextPath + url,
		dataType : "html",
		success : function(res) {
			Metronic.stopPageLoading();
			pageContentBody.html(res);
			Layout.fixContentHeight();
			Metronic.initAjax();
			func && func();
			
			// 新增 缓存 每一次的ajaxify请求 且限制大小为30
			if(notAddToAjaxifyHistory==undefined || notAddToAjaxifyHistory!=true)
				Layout.addAjaxifyHistory(contextPath + url);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			// ajaxErrorOp(xhr, ajaxOptions, thrownError);
			
			/* 加载页面出错,跳转到异常页面 */
            pageContentBody.html(viewNotFound(url, xhr.status));
            
			Metronic.stopPageLoading();
		}
	});
}

/**
 * url 待跳转地址 notAddToAjaxifyHistory 默认加入队列
 */
var redirectHandle = function(url,notAddToAjaxifyHistory) {
	var pageContent = $('.page-content');
	var pageContentBody = $('.page-content .page-content-body');
	Metronic.startPageLoading();

	if (Metronic.getViewPort().width < 992 && $('.page-sidebar').hasClass("in")) {
		$('.page-header .responsive-toggler').click();
	}
	$.ajax({
		type : "GET",
		cache : false,
		url : contextPath + url,
		dataType : "html",
		success : function(res) {
			Metronic.stopPageLoading();
			pageContentBody.html(res);
			Layout.fixContentHeight();
			Metronic.initAjax();
			
			
			// 新增 缓存 每一次的ajaxify请求 且限制大小为30
			if(notAddToAjaxifyHistory==undefined || notAddToAjaxifyHistory!=true)
				Layout.addAjaxifyHistory(contextPath + url);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			// ajaxErrorOp(xhr, ajaxOptions, thrownError);
			
			/* 加载页面出错,跳转到异常页面 */
            pageContentBody.html(viewNotFound(url, xhr.status));
            
			Metronic.stopPageLoading();
		}
	});
}
/**
 * 重载当前页面请求
 */
var reload = function(){
	redirectHandle($.ajaxifyHistory[$.ajaxifyHistory.length-1],true);
}

// 跳转处理
var redirectHandlePost = function(url,data,notAddToAjaxifyHistory) {
	data = data ? data :　{};
	var pageContent = $('.page-content');
	var pageContentBody = $('.page-content .page-content-body');
	Metronic.startPageLoading();

	if (Metronic.getViewPort().width < 992 && $('.page-sidebar').hasClass("in")) {
		$('.page-header .responsive-toggler').click();
	}
	$.ajax({
		type : "post",
		cache : false,
		url : contextPath + url,
		dataType : "html",
		data : data,
		success : function(res) {
			Metronic.stopPageLoading();
			pageContentBody.html(res);
			Layout.fixContentHeight();
			Metronic.initAjax();
			
			// 新增 缓存 每一次的ajaxify请求 且限制大小为30
			if(notAddToAjaxifyHistory==undefined || notAddToAjaxifyHistory!=true)
				Layout.addAjaxifyHistory(contextPath + url);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			// ajaxErrorOp(xhr, ajaxOptions, thrownError);
			
			/* 加载页面出错,跳转到异常页面 */
            pageContentBody.html(viewNotFound(url, xhr.status));
            
			Metronic.stopPageLoading();
		}
	});
}

/**
 * 表单直接提交 返回页面 主要用于一个操作经过多个页面流转 中参数保存
 */
var formSubmitDirect = function($form){
	var pageContent = $('.page-content');
	var pageContentBody = $('.page-content .page-content-body');
	Metronic.startPageLoading();

	if (Metronic.getViewPort().width < 992 && $('.page-sidebar').hasClass("in")) {
		$('.page-header .responsive-toggler').click();
	}
	$.ajax({
		type : "post",
		cache : false,
		url : $form.attr("action"),
		data : $form.serialize(),
		dataType : "html",
		success : function(res) {
			Metronic.stopPageLoading();
			pageContentBody.html(res);
			Layout.fixContentHeight();
			Metronic.initAjax();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			// ajaxErrorOp(xhr, ajaxOptions, thrownError);
			pageContentBody
					.html('<h4>无法完成此页面请求.</h4>');
			Metronic.stopPageLoading();
		}
	});	
}


// 处理提交
var formSubmitHandle = function(from, url, redirect) {
	$.ajax({
		cache : true,
		type : "post",
		url : contextPath + url,
		data : from.serialize(),
		async : false,
		success : function(data) {
			if (data.result.success) {
				if (redirect == null || redirect.length == 0) {
					slalert('操作成功');
				} else {
					redirectHandle(redirect)
				}
			} else {
				slalert("请求失败:" + data.result.message);
			}
		}
	});
}


// 处理提交
var formSubmitHandle = function(from, url, redirect, $element) {
	$.ajax({
		cache : true,
		type : "post",
		url : contextPath + url,
		data : from.serialize(),
		async : false,
		error : function(request) {
			removeDisabled($element);
		},
		success : function(data) {
			removeDisabled($element);
			if (data.result.success) {
				if (redirect == null || redirect.length == 0) {
					slalert('操作成功');
				} else {
					redirectHandle(redirect)
				}
			} else {
				slalert("请求失败:" + data.result.message);
			}
		}
	});
}

// 默认的validate样式
var validateDefaultConfiguration = {
	ignore : ".ignore",// 忽略某些元素不验证,不需要验证的元素请添加class='ignore'
	errorElement : 'span',// 用什么标签标记错误,默认是 label
	errorClass : 'help-block help-block-error',// 指定错误提示的 css 类名
	errorPlacement: function (error, element) { // render error placement for
												// each input type
        if (element.parent(".input-group").size() > 0) {
            error.insertAfter(element.parent(".input-group"));
        } else if (element.attr("data-error-container")) { 
            error.appendTo(element.attr("data-error-container"));
        } else if (element.parents('.radio-list').size() > 0) { 
            error.appendTo(element.parents('.radio-list').attr("data-error-container"));
        } else if (element.parents('.radio-inline').size() > 0) { 
            error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
        } else if (element.parents('.checkbox-list').size() > 0) {
            error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
        } else if (element.parents('.checkbox-inline').size() > 0) { 
            error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
        } else {
            error.insertAfter(element);
        }
    },
    highlight: function (element) { 
        $(element).closest('.form-group').addClass('has-error'); 
    },
    unhighlight: function (element) {
        $(element).closest('.form-group').removeClass('has-error');
    }

}


// 重置form验证规则
var resetValidateRule = function(form, rule) {
	$('.form-group', form).each(function() { $(this).removeClass('has-error'); });
	form.validate(rule?rule:validateDefaultConfiguration).resetForm();
}


// 调用validate框架验证form
var formValidate = function(form, rule) {
	if(rule){
		return form.validate(rule).form();
	}else{
		return form.validate(validateDefaultConfiguration).form();
	}
}

// 清空form数据
function formClearHandle(form) {
	$(':input', form).each(function() {
		var type = this.type;
		var tag = this.tagName.toLowerCase(); // normalize case
		if (type == 'text' || type == 'password' || tag == 'textarea' || type == 'hidden')
			this.value = "";
		else if (type == 'checkbox' || type == 'radio')
			this.checked = false;
		else if (tag == 'select')
			this.selectedIndex = 0;
		
		// clear select2's value
		var $thiz = $(this);
		if($thiz.hasClass("select2me")){
			$thiz.select2("val", "");
		}
		$.uniform.update($thiz)
	});
}

// 给指定的form表单所有input添加disabled属性
function formDisabledHandle(form) {
	$(':input', form).each(function(index, element) {
		$(this).attr("disabled", "disabled");
		$.uniform.update($(this))
	});
}

// 移除指定form表单所有input的disabled属性
function formRemoveDisabledHandle(form) {
	$(':input', form).each(function(index, element) {
		$(this).removeAttr("disabled");
		$.uniform.update($(this))
	});
}

// 添加元素disabled属性
function addDisabled(element) {
	element.attr("disabled", "disabled");
}

// 移除元disabled属性
function removeDisabled(element){
	element.removeAttr("disabled");
}

// 清空form表单所有input的值,并移除disabled属性
function formClearAndRemoveDisabledHandle(form) {
	formClearHandle(form);
	formRemoveDisabledHandle(form);
}

function setNormalVal(form, key, val) {
	var rr = form.find('input[name="'+key+'"][type=radio], input[name="'+key+'"][type=checkbox]');
	rr.prop('checked', false);
	rr.each(function(){
		var f = $(this);
		if (f.val() == String(val) || $.inArray(f.val(), $.isArray(val)?val:[val]) >= 0){
			// console.log(this);
			f.prop('checked', true);
		}
	});
	return rr;
};

// form 表单填充
function formLoadHandle(form, data) {
	var formFields = form.find("input[name],select[name],textarea[name]");
	formFields.each(function(){
		var name = this.name;
		var value = eval('data.' + name);
		var rr = setNormalVal(form, name,value);
		if (!rr.length) {
			$("input[name=\"" + name + "\"]", form).val(value);
			$("textarea[name=\"" + name + "\"]", form).val(value);
			$("select[name=\"" + name + "\"]", form).val(value);
			// filling select2 plugin's value
			$(".select2me[name=\"" + name + "\"]", form).each(function(){
				$(this).select2("val",value)
			});
			$(".select2me-data-type[name=\"" + name + "\"]", form).each(function(){
				$(this).select2("val",value)
			});
 		}
		$.uniform.update($(this));
	});
}

function show_div(id) {
	var div = $('#' + id);
	div.show();
	$(div).css({
		"width" : $(div).width(),
		"height" : $(div).height(),
		"position": "absolute",  
		"top": (document.documentElement.clientHeight + 100  - ($(div).height()))/2 ,  
		"left": (jQuery('.page-content-body').width() - $(div).height())/2  
	});	
}


// 查找单个版本信息
var findOne = function(id, url) {
	var obj;
	$.ajax({
		url : contextPath + url,
		async : false,
		type : "POST",
		data : {
			id : id
		},
		dataType : "json",
		success : function(result) {
			obj = result;
		},
		error : function() {
			slalert("服务器异常，请联系管理员解决!");
		}
	});
	return obj;
}

// 确认提示框
var slconfirm = function (message, okCallback, title, cancelCallback) {
	bootbox.dialog({
        message: message,
        title: title == null || title.length == 0 ? "操作提示" : title,
        buttons: {
          ok: { label: "确定", className: "green", callback : okCallback },
          cancel: { label: "取消", className: "red", callback : cancelCallback }
        }
    });
}

// 带自定义的确认提示框
var slconfirm2 = function (message, okCallback, title, cancelCallback, okLabel, cancelLabel) {
	bootbox.dialog({
        message: message,
        title: title == null || title.length == 0 ? "操作提示" : title,
        buttons: {
          ok: { label: okLabel?okLabel:"确定", className: "green", callback : okCallback },
          cancel: { label: cancelLabel?cancelLabel:"取消", className: "red", callback : cancelCallback }
        }
    });
}
/* 错误信息包含的字符 */
var errorStatus = ["sockettimeoutexception"];
// 弹出提示框
var slalert = function (message, title) {
	jQuery.grep(errorStatus, function(n,i){
		if(message.toLowerCase().indexOf(n.toLowerCase()) != -1){
			message = "对不起,服务器出了一点内部小问题,请联系管理员解决!";
		}
	});
	bootbox.dialog({
        message: message==undefined||message==null?"操作失败":message,
        title: title == null || title.length == 0 ? "操作提示" : title,
        buttons: {
          ok: { label: "确定", className: "green"}
        }
    });
}

/*******************************************************************************
 * 下载文件 当为对象时 默认当作 resultvo 当为字符串时 默认当作下载路径
 */
window.download = $.download = function(path, fileName){
	var downloadPath = downloadParamHandler(path,fileName);
	if(downloadPath){
		if(fileName){
			fileName = encodeURIComponent(encodeURIComponent(fileName));
		}else {
			fileName = "";
		}
		downloadPath = getContextPath()+"/download?path="+downloadPath+"&fileName="+fileName;
		downloadIframePopulate(downloadPath);
	}
}
/*******************************************************************************
 * 下载文件 p2p专用 当为对象时 默认当作 resultvo 当为字符串时 默认当作下载路径
 */
window.downloadp2p = $.downloadp2p = function(path, fileName){
	var downloadPath = downloadParamHandler(path,fileName);
	if(downloadPath){
		if(fileName){
			fileName = encodeURIComponent(encodeURIComponent(fileName));
		}else {
			fileName = "";
		}
		downloadPath = getContextPath()+"/p2p/download?path="+downloadPath+"&fileName="+fileName;
		downloadIframePopulate(downloadPath);
	}	
}
/*******************************************************************************
 * 下载模板文件 p2p专用 当为对象时 默认当作 resultvo 当为字符串时 默认当作下载路径
 */
window.downloadTempletp2p = $.downloadTempletp2p = function(path, fileName){
	var downloadPath = downloadParamHandler(path,fileName);
	if(downloadPath){
		if(fileName){
			fileName = encodeURIComponent(encodeURIComponent(fileName));
		}else {
			fileName = "";
		}
		downloadPath = getContextPath()+"/p2p/downloadTempleFile/download?path="+downloadPath+"&fileName="+fileName;
		downloadIframePopulate(downloadPath);
	}	
}

// 处理下载路径
downloadParamHandler = function(path, fileName){
	var downloadPath = "";
	if($.type(path) === "object"){
		if(path.result.success==true){
			if(path.result&&path.result.generatefileList){
				if(path.result.generatefileList.length==0){
					slalert("暂无文档可以下载");
				}else{
					var file = path.result.generatefileList[0];
					if(!file.filePath && !file.fileName){
						slalert("暂无文档可以下载");
					}else{
						downloadPath = file.filePath+"/"+file.fileName;
					}
				}
			}
		}else{
			slalert(path.result.message);
		}
	}else if($.type(path) === "string"){
		downloadPath = path;
	}
	if(downloadPath){
		downloadPath = encodeURIComponent(encodeURIComponent(downloadPath));
	}
	return downloadPath;
}
// 组装下载iframe
downloadIframePopulate = function(downloadPath){
	var $downloadIframe = $("#content_download");
	if($downloadIframe.length<1){
		$(document.body).append("<iframe name='download' src='"+downloadPath+"' class='hide' id='content_download'></iframe>");
	}else{
		$downloadIframe.attr("src",downloadPath);
	}
}

/**
 * 截取 小数 number 待截取数 (needed) length 截取小数位数(default 2)
 * 
 * for example { cutFloat("afeag") return afeag cutFloat("5313.349999") return
 * 5313.34 cutFloat(5313.349999) return 5313.34 cutFloat("5313.349999",4) return
 * 5313.3499 }
 */
window.cutFloat = function(number,length){
	if(number!=undefined&&!isNaN(number)){
		length = (!isNaN(length) && (parseInt(length, 10))>=0)?length:2;
		var power = Math.pow(10,length);
		return (Math.floor( power*parseFloat(number) ) )/ power;
	}else{
		return number;
	}
}

/**
 * 刷新页面并跳转到指定的menu页面
 * <p>
 * <strong>主要用于 某些插件使用完后 可能会对其他模块留下后遗症的话 则需要刷新整个页面重新加载js</strong>
 * </p>
 * 
 * @param menu
 *            刷新页面后需要跳转到的菜单
 */
function refreshIndexPageToMenu(menu) {
	var url = contextPath + "/index";
	menu = menu && menu != "" && menu.indexOf("/") == 0 ? menu.substring(1, menu.length) : menu;
	url = menu && menu != "" ? url + "#" + menu : url;
	window.location.href = url;
	window.location.reload();
}

/**
 * 跳转错误页面
 */
var viewNotFound = function(url, statusCode){
	Layout.addAjaxifyHistory(contextPath + url);
	var obj ;
	$.ajax({
		url : contextPath + "/exception/viewNotFound",
		async : false,
		type : "POST",
		data : {
			"url":url,
			"statusCode":statusCode
		},
		dataType : "html",
		success : function(result) {
			obj = result;
		}
	});
	return obj;
}

/**
 * 指定form下的input属性标签替换为标签value
 */
var replaceTagToText = function($form){
	$(':input', $form).each(function() {
		var type = this.type;
		var tag = this.tagName.toLowerCase();
		if (type == 'text'  || tag == 'textarea'){
			$(this).replaceWith("<p class='form-control-static'>"+$(this).val()+"</p>")
		}
	});
}
/**
 * 加密身份证号码和手机号码的页面显示
 */
var encryptImportantInfo = function(str , len){
	len = len ? len : 4;
	str = str ? str : "";
	return (str.length>len?str.substring(0,len):str) + "****" + (str.length>=len*2 ? str.substring(str.length-len) : (str.length<len*2 && str.length>len)? str.substring(len) : "");
}
