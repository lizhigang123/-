<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html class="win ks-webkit537 ks-webkit ks-chrome49 ks-chrome" style="background:#31312E; color:#D2BCBC">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="data-spm" content="a219a">
<title>阿特门开放平台 - 接口详情</title>
<link rel="stylesheet" href="/css/content/sui.min.css">
<link rel="stylesheet" href="/css/content/framework.min.css">
<link rel="stylesheet" href="/css/content/font_1429753170_0792055.css">
<script src="/js/jquery-1.10.2.js"></script>
<link rel="stylesheet" href="/css/content/doc-min.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/content/docs-detail-v2.css">
</head>
<style>
.api .block-docs-wrap .docs-right .doc-detail-bd h1 {
	font-size: 20px;
}

.block-docs-wrap .docs-right .doc-detail-bd h1 {
	margin-bottom: 0px;
}

.doc-detail-bd table {
	margin-bottom: 20px;
}

@font-face {
	font-family: "iconfont1";
	src: url("//at.alicdn.com/t/font_1448507037_6084561.eot?#iefix")
		format("embedded-opentype"),
		url("//at.alicdn.com/t/font_1448507037_6084561.woff") format("woff"),
		url("//at.alicdn.com/t/font_1448507037_6084561.ttf")
		format("truetype"),
		url("//at.alicdn.com/t/font_1448507037_6084561.svg#iconfont")
		format("svg");
}

.iconfont {
	font-family: "iconfont1" !important;
	font-size: 16px;
	font-style: normal;
}

#pages {
	min-height: 700px;
}

.wrap {
	width: 100%;
}

.wrap-inner {
	margin: 0 auto;
	width: 1200px;
}

.openplatform-nav .top-openplatform-nav ul li .sub-nav dl dt a {
	color: #5eb5fe;
}
</style>
<script type="text/javascript">
	$(function(){
		var projectId = $("#projectId").val();
		var categoryId = $("#categoryId").val();
		var interfaceId = $("#interfaceId").val();
		$("#getRequestJson").click(function(){
			var hasDefaultValue = $("#hasDefaultValue").val();
			$.post("/autoGernerateCode/getRequestJson",{"needDefaultValue":hasDefaultValue,"projectId":projectId,"categoryId":categoryId,"interfaceId":interfaceId},function(data){
				$("#content").val(data.name);
				setHasDefaultValue();
			},"json");
		});
		
		function setHasDefaultValue() {
			var hasDefaultValue = $("#hasDefaultValue").val();
			if (hasDefaultValue == "true") {
				$("#hasDefaultValue").val("false");
			} else {
				$("#hasDefaultValue").val("true");
			}
		}
		
		$("#getRequestVo").click(function(){
			var prefixErrorCode = $("#prefixErrorCode").val();
			var hasDefaultValue = $("#hasDefaultValue").val();
			$.post("/autoGernerateCode/getRequestVo",{"needValid":hasDefaultValue,"projectId":projectId,"categoryId":categoryId,"interfaceId":interfaceId,"prefixErrorCode":prefixErrorCode},function(data){
				$("#content").val(data.name);
				setHasDefaultValue();
				window.location="/autoGernerateCode/getRequestVoDownload?needValid="+hasDefaultValue;
			},"json");
		});
		
		$("#getRequestSetCodeNeedDefaultValueTrue").click(function(){
			var hasDefaultValue = $("#hasDefaultValue").val();
			$.post("/autoGernerateCode/getRequestSetOrGetCode",{"needDefaultValue":hasDefaultValue,"setOrget":"set","categoryId":categoryId,"interfaceId":interfaceId},function(data){
				$("#content").val(data.name);
				setHasDefaultValue();
			},"json");
		});
		
		$("#getRequestGetCode").click(function(){
			$.post("/autoGernerateCode/getRequestSetOrGetCode",{"needDefaultValue":true,"setOrget":"get","categoryId":categoryId,"interfaceId":interfaceId},function(data){
				$("#content").val(data.name);
			},"json");
		});
		
		$("#getRequestPutCodeNeedDefaultValueTrue").click(function(){
			var hasDefaultValue = $("#hasDefaultValue").val();
			$.post("/autoGernerateCode/getRequestPutCode",{"needDefaultValue":hasDefaultValue,"interfaceId":interfaceId},function(data){
				$("#content").val(data.name);
				setHasDefaultValue();
			},"json");
		});
		$("#getReponseJson").click(function(){
			var hasDefaultValue = $("#hasDefaultValue").val();
			$.post("/autoGernerateCode/getReponseJson",{"needDefaultValue":hasDefaultValue,"projectId":projectId,"categoryId":categoryId,"interfaceId":interfaceId},function(data){
				$("#content").val(data.name);
				setHasDefaultValue();
			},"json");
		});
		
 		$("#getGenerateReponseCode").click(function(){
 			$.post("/autoGernerateCode/getGenerateReponseCode",{"projectId":projectId,"categoryId":categoryId,"interfaceId":interfaceId},function(data){
				$("#content").val(data.name);
			},"json")
		}); 
		
	});
</script>
<body data-spm="7395905" class="api" style="background:#31312E; color:#D2BCBC">
	<div id="pages">
		<div class="wrap">
			<div class="wrap-inner block-docs-wrap" id="generateAutoCode">
				<input type="hidden" id="projectId" value="${projectId}">
				<input type="hidden" id="categoryId" value="${categoryId}">
				<input type="hidden" id="interfaceId" value="${interfaceId}">
				<input type="hidden" id="hasDefaultValue" value="false"/>
				<input name="prefixErrorCode" type="text" id="prefixErrorCode" style="width:200px;height:20px;background:#1F1F1D;color:#A48FCC; border: 1px solid #656553;" value="" placeholder="请求vo类校验错误码的前缀（三位）">&nbsp;vo类错误码前缀
				<br>
				<button id="getRequestJson" style="width: 80px; height: 30px; background:#A7A0A0">请求json格式</button>
				<button id="getRequestVo" style="width: 80px; height: 30px; background:#A7A0A0">请求vo类</button>
				<button id="getRequestGetCode" style="width: 130px; height: 30px; background:#A7A0A0">vo类的get属性代码</button>
				<button id="getRequestPutCodeNeedDefaultValueTrue" style="width: 130px; height: 30px; background:#A7A0A0">使用map封装请求参数</button>
				<button id="getRequestSetCodeNeedDefaultValueTrue" style="width: 120px; height: 30px; background:#A7A0A0">使用vo封装请求参数</button>
				<button id="getReponseJson" style="width: 80px; height: 30px; background:#A7A0A0">返回json格式</button>
				<button id="getGenerateReponseCode" style="width: 120px; height: 30px; background:#A7A0A0">返回java模拟代码</button>
				&nbsp;
				<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="30" cols="200" style="overflow-x: hidden" id="content" name="content">${approvalInfo.opinionContent }</textarea>
			</div>
		</div>
	</div>
</body>
</html>