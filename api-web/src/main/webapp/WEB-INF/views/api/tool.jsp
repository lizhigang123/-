<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		var needDefaultValue = true;
		$("#formatJson").click(function(){
			var requestContent = $("#requestContent").val();
			if(!requestContent){
				var formatJsonLastHide = $("#formatJsonLastHide").val();
				if(formatJsonLastHide){
					$("#requestContent").val(formatJsonLastHide);
				}else{
					$("#requestContent").val($("#formatJsonReminderHide").val());
				}
				return;
			}
			$("#reponseContent").val("请求处理中.......");
 			$.post("/tool/formatJson",{"requestContent":requestContent},function(data){
				$("#reponseContent").val(data.data);
				$("#formatJsonLastHide").val($("#requestContent").val());
			},"json")
		});
		
		$("#decodeURIComponent").click(function(){
			var requestContent = $("#requestContent").val();
			if(!requestContent){
				var decodeURIComponentLastHide = $("#decodeURIComponentLastHide").val();
				if(decodeURIComponentLastHide){
					$("#requestContent").val(decodeURIComponentLastHide);
				}else{
					$("#requestContent").val($("#decodeURIComponentHide").val());
				}
				return;
			}else{
				$("#decodeURIComponentLastHide").val(requestContent)
			}
			$("#reponseContent").val("解码中.......");
			try {
				$("#decodeURIComponentLastHide").val($("#requestContent").val());
				$("#reponseContent").val(decodeURIComponent(requestContent));
			}catch(err) {
				$("#reponseContent").val("解码失败");
			}
		});
		
		$("#generatedEntitie").click(function(){
			var requestContent = $("#requestContent").val();
			if(!requestContent){
				var generatedEntitieLastHide = $("#generatedEntitieLastHide").val();
				if(generatedEntitieLastHide){
					$("#requestContent").val(generatedEntitieLastHide);
				}else{
					$("#requestContent").val($("#generatedEntitieReminderHide").val());
				}
				return;
			}
			$("#reponseContent").val("请求处理中.......");
 			$.post("/tool/generatedEntitie",{"requestContent":requestContent},function(data){
				$("#generatedEntitieLastHide").val(requestContent);
 				if("000000"==data.code){
					$("#reponseContent").val(data.result);
					window.location="/tool/generatedEntitieDownLoad";
				}else{
					$("#reponseContent").val(data.message);
				}
			},"json")
		});
		
		$("#getRequestMapOrListCode").click(function(){
			var requestContent = $("#requestContent").val();
			if(!requestContent){
				var formatJsonLastHide = $("#formatJsonLastHide").val();
				if(formatJsonLastHide){
					$("#requestContent").val(formatJsonLastHide);
				}else{
					$("#requestContent").val($("#formatJsonReminderHide").val());
				}
				return;
			}
			var requestContent = $("#requestContent").val();
			$("#reponseContent").val("请求处理中.......");
 			$.post("/tool/getRequestMapOrListCode",{"requestContent":requestContent,"needDefaultValue":needDefaultValue},function(data){
				$("#reponseContent").val(data.data);
				if(needDefaultValue){
					needDefaultValue = false;
				}else{
					needDefaultValue = true;
				}
				$("#formatJsonLastHide").val(requestContent);
			},"json")
		});
		
		$("#requestJsonParsingToReveiveVo").click(function(){
			var requestContent = $("#requestContent").val();
			if(!requestContent){
				var formatJsonLastHide = $("#formatJsonLastHide").val();
				if(formatJsonLastHide){
					$("#requestContent").val(formatJsonLastHide);
				}else{
					$("#requestContent").val($("#formatJsonReminderHide").val());
				}
				return;
			}
			$("#reponseContent").val("请求处理中.......");
 			$.post("/tool/requestJsonParsingToReveiveVo",{"requestContent":requestContent,"needDefaultValue":needDefaultValue},function(data){
			
				if(needDefaultValue){
					needDefaultValue = false;
				}else{
					needDefaultValue = true;
				}
				$("#getRequestMapOrListCodeLastHide").val(requestContent);
				if("000000"==data.code){
					$("#reponseContent").val(data.result);
					window.location="/tool/requestJsonParsingToReveiveVoDownload";
				}else{
					$("#reponseContent").val(data.message);
				}
			},"json")
		});
		
		$("#requestJsonParsingForReveiveVoSetCode").click(function(){
			var requestContent = $("#requestContent").val();
			if(!requestContent){
				var formatJsonLastHide = $("#formatJsonLastHide").val();
				if(formatJsonLastHide){
					$("#requestContent").val(formatJsonLastHide);
				}else{
					$("#requestContent").val($("#formatJsonReminderHide").val());
				}
				return;
			}
			$("#reponseContent").val("请求处理中.......");
 			$.post("/tool/requestJsonParsingForReveiveVoSetCode",{"requestContent":requestContent,"needDefaultValue":needDefaultValue},function(data){
				$("#reponseContent").val(data.data);
				if(needDefaultValue){
					needDefaultValue = false;
				}else{
					needDefaultValue = true;
				}
				$("#getRequestMapOrListCodeLastHide").val(requestContent);
			},"json")
		});
		
		$("#requestJsonParsingForReveiveVoGetCode").click(function(){
			var requestContent = $("#requestContent").val();
			if(!requestContent){
				var formatJsonLastHide = $("#formatJsonLastHide").val();
				if(formatJsonLastHide){
					$("#requestContent").val(formatJsonLastHide);
				}else{
					$("#requestContent").val($("#formatJsonReminderHide").val());
				}
				return;
			}
			$("#reponseContent").val("请求处理中.......");
 			$.post("/tool/requestJsonParsingForReveiveVoGetCode",{"requestContent":requestContent,"needDefaultValue":needDefaultValue},function(data){
				$("#reponseContent").val(data.data);
				if(needDefaultValue){
					needDefaultValue = false;
				}else{
					needDefaultValue = true;
				}
				$("#getRequestMapOrListCodeLastHide").val(requestContent);
			},"json")
		});
		
		$("#sqlIndentation").click(function(){
			var requestContent = $("#requestContent").val();
			if(!requestContent){
				var getRequestMapOrListCodeLastHide = $("#sqlIndentationLastHide").val();
				if(getRequestMapOrListCodeLastHide){
					$("#requestContent").val(getRequestMapOrListCodeLastHide);
				}else{
					$("#requestContent").val($("#sqlIndentationHide").val());
				}
				return;
			}
			$("#reponseContent").val("请求处理中.......");
 			$.post("/tool/sqlIndentation",{"requestContent":requestContent,"needDefaultValue":needDefaultValue},function(data){
				$("#reponseContent").val(data.data);
				if(needDefaultValue){
					needDefaultValue = false;
				}else{
					needDefaultValue = true;
				}
				$("#sqlIndentationLastHide").val(requestContent);
			},"json")
		});
		
		$("#analogHttppost").click(function(){
			window.open("/httppost?interfaceId=");
		});
		
	});
</script>
<body data-spm="7395905" class="api" style="background:#31312E; color:#D2BCBC">
		<div class="wrap-inner block-docs-wrap" id="generateAutoCode">
			<input type="hidden" id="projectId" value="${projectId}">
			<input type="hidden" id="categoryId" value="${categoryId}">
			<input type="hidden" id="interfaceId" value="${interfaceId}">
			<input type="hidden" id="hasDefaultValue" value="false"/>
			<br>
			请求信息:
			&nbsp;
			<br>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="20" cols="200" style="overflow-x: hidden;" id="requestContent" name="requestContent" data-msg-required=""></textarea>
			<br>
			<button id="formatJson" style="width: 80px; height: 30px; background:#A7A0A0">json格式化</button>
			<button id="getRequestMapOrListCode" style="width: 80px; height: 30px; background:#A7A0A0">json对应map</button>
			<button id="requestJsonParsingToReveiveVo" style="width: 110px; height: 30px; background:#A7A0A0">json对应Vo(对象)</button>
			<button id="requestJsonParsingForReveiveVoSetCode" style="width: 110px; height: 30px; background:#A7A0A0">json对应Vo(set)</button>
			<button id="requestJsonParsingForReveiveVoGetCode" style="width: 110px; height: 30px; background:#A7A0A0">json对应Vo(get)</button>
			<button id="generatedEntitie" style="width: 110px; height: 30px; background:#A7A0A0">建表sql生成实体</button>
			<button id="sqlIndentation" style="width: 90px; height: 30px; background:#A7A0A0">sql缩进成一行</button>
			<button id="decodeURIComponent" style="width: 90px; height: 30px; background:#A7A0A0">URL参数解码</button>
			<button id="analogHttppost" style="width: 90px; height: 30px; background:#A7A0A0">模拟http请求</button>
			<br>
			<br>
			响应信息:
			&nbsp;
			<br>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="15" cols="200" style="overflow-x: hidden;" id="reponseContent" name="reponseContent" data-msg-required="">${reponseContent }</textarea>
			<br>
		</div>
		
		<div style="display:none">
		<textarea  spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="12" cols="200" style="overflow-x: hidden;" id="generatedEntitieReminderHide" data-msg-required="">
目前支持：PowerDesigner 12、15、Navicat 11、PL/SQL Developer导出的全部表结构sql贴入到此框中点击“sql生存实体”即可(关于生成注释请查看"示例"下面的"PowerDesigner 12、15生成注释的注意事项")。

示例:
/*==============================================================*/
/* Table: CT_T_AT_ADD                                           */
/*==============================================================*/
create table CT_T_AT_ADD
(
   TYPE                 VARCHAR2(50)
);
comment on table CT_T_AT_ADD is
'示例：地址信息表';
comment on column CT_T_AT_ADD.TYPE is
'户籍地址、单位地址、住宅地址、其他';

PowerDesigner 12、15生成注释的注意事项：
PowerDesigner设计库表结构的时候可能Comment没有填写内容(此内容生成vo的注释用)，执行下面脚本（最下面一陀英文的部分为脚本内容）就可以把name内容拷贝到Comment中 然后重新导出sql。
粘贴执行脚本位置：PowerDesigner中Tools->Execute Commands->Edit/Run Scripts（Ctrl Shift X），将脚本粘贴到对应的输入框，运行即可
单独编辑Comment中的内容： 选中准备编辑的表,【右键】->【Properties】->【Columns】->【Customize Columns and Filter】->【Comment】->【OK】

Option Explicit
ValidationMode = True
InteractiveMode = im_Batch
Dim mdl ' the current model
' get the current active model
Set mdl = ActiveModel
If (mdl Is Nothing) Then
  MsgBox "There is no current Model "
ElseIf Not mdl.IsKindOf(PdPDM.cls_Model) Then
  MsgBox "The current model is not an Physical Data model. "
Else
  ProcessFolder mdl
End If
' This routine copy name into comment for each table, each column and each view
' of the current folder
Private sub ProcessFolder(folder)
  Dim Tab 'running   table
  for each Tab in folder.tables
    if not tab.isShortcut then
         if trim(tab.comment)="" then '如果有表的注释,则不改变它.如果没有表注释.则把name添加到注释里面.
       tab.comment = tab.name
         end if
      Dim col ' running column
      for each col in tab.columns
        if trim(col.comment)="" then '如果col的comment为空,则填入name,如果已有注释,则不添加;这样可以避免已有注释丢失.
         col.comment= col.name
        end if
      next
    end if
  next
  Dim view 'running view
  for each view in folder.Views
    if not view.isShortcut and trim(view.comment)="" then
      view.comment = view.name
    end if
  next
  ' go into the sub-packages
  Dim f ' running folder
  For Each f In folder.Packages
    if not f.IsShortcut then
      ProcessFolder f
    end if
  Next
end sub</textarea>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="10" cols="200" style="overflow-x: hidden;" id="generatedEntitieLastHide" data-msg-required=""></textarea>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="10" cols="200" style="overflow-x: hidden;" id="formatJsonReminderHide" data-msg-required="">{"key1":"测试","key2":{"innerKey1":"测试"},"ket3":[{"innerKey2":"测试"}]}</textarea>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="10" cols="200" style="overflow-x: hidden;" id="formatJsonLastHide" data-msg-required=""></textarea>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="10" cols="200" style="overflow-x: hidden;" id="getRequestMapOrListCodeLastHide" data-msg-required=""></textarea>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="10" cols="200" style="overflow-x: hidden;" id="sqlIndentationHide" data-msg-required="">
SELECT
	*
FROM
	emp;

 </textarea>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="10" cols="200" style="overflow-x: hidden;" id="sqlIndentationLastHide" data-msg-required=""></textarea>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="10" cols="200" style="overflow-x: hidden;" id="decodeURIComponentHide" data-msg-required="">js解码函数：decodeURIComponent()
userid=shslsw01&sign=700b1344cb7c5bcd224511f1a4e9797f&params=%7B%22data%22%3A%7B%22familyContactMobile%22%3A%22%22%2C%22idNo%22%3A%22522132197808265418%22%2C%22otherContactName%22%3A%22%22%2C%22addressCity%22%3A%22%E9%9A%8F%E5%B7%9E%E5%B8%82%22%2C%22jobRefereeName%22%3A%22%22%2C%22companyName%22%3A%22%E4%B8%8A%E6%B5%B7%E5%A4%A7%E5%B1%B1%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%22%2C%22addressDetail%22%3A%22%E6%B9%96%E5%8C%97%E7%9C%81%E9%9A%8F%E5%B7%9E%E5%B8%82%E9%9A%8F%E5%8E%BF%E4%BF%9D%E5%BE%B7%E8%B7%AF1120%E5%BC%84101%E5%AE%A4%22%2C%22otherContactMobile%22%3A%22%22%2C%22companyPhoneNumber%22%3A%22021
			</textarea>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="10" cols="200" style="overflow-x: hidden;" id="decodeURIComponentLastHide" data-msg-required=""></textarea>
		
		</div>
</body>
</html>