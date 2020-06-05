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
		var projectId = $("#projectId").val();
		var categoryId = $("#categoryId").val();
		var interfaceId = $("#interfaceId").val();
		$("#requestHeaders").hide();
		$("#requestJsonButton").click(function(){
			$("#requestJson").show();
			$("#requestHeaders").hide();
		});
		$("#requestHeadersButton").click(function(){
			$("#requestJson").hide();
			$("#requestHeaders").show();
		});
		$("#sendService").click(function(){
			$('#responseTime').html("");
			$('#sendService').attr('disabled',"true");
			var serviceUrl = $("#serviceUrl").val();
			var requestJson = $("#requestJson").val();
			var requestHeaders = $("#requestHeaders").val();
			var timestamp = Date.parse(new Date());
			$("#reponseContent").val("请求处理中.......");
			var start = new Date().getTime();//起始时间
			var end = start;//结束时间
			var useAlltime;
			var reponseTime;
 			$.post("/httppost/sendService",{"timestamp":timestamp,"serviceUrl":serviceUrl,"requestJson":requestJson,"requestHeaders":requestHeaders,"projectId":projectId,"categoryId":categoryId,"interfaceId":interfaceId},function(data){
 				$("#reponseContent").val(data.data);
 				reponseTime = data.reponseTime;
				if (!reponseTime) {
					end = new Date().getTime();//接受时间
					useAlltime = end - start;
					if (useAlltime >= 1000) {
						useAlltime = useAlltime / 1000;
						reponseTime = useAlltime + "s";
					} else {
						reponseTime = useAlltime + "ms";
					}
				}
				$('#responseTime').html(reponseTime);
				$('#sendService').removeAttr("disabled");
				if("000000" == data.code){
					$.post("/httppost/findFirstByInterfaceIdOrderByCreateDateDes",{"timestamp":timestamp,"serviceUrl":serviceUrl,"requestJson":requestJson,"requestHeaders":requestHeaders,"projectId":projectId,"categoryId":categoryId,"interfaceId":interfaceId},function(response){
						if(response.data.id){
							addRow(response.data.id,interfaceId,response.data.serviceUrl,response.data.requestIp,response.data.responseJson,formatDate(response.data.createDate),response.data.createUser);
						}
					}, "json")
				}
			}, "json")
		});
		$("#getRequestMapCode").click(function() {
			$('#sendService').attr('disabled', "true");
			var requestJson = $("#requestJson").val();
			var hasDefaultValue = $("#hasDefaultValue").val();
			$("#reponseContent").val("请求处理中.......");
			$.post("/httppost/getRequestMapOrListCode", {
				"needDefaultValue" : hasDefaultValue,
				"requestJson" : requestJson
			}, function(data) {
				$("#reponseContent").val(data.data);
				$('#sendService').removeAttr("disabled");
				var hasDefaultValue = $("#hasDefaultValue").val();
				if (hasDefaultValue == "true") {
					$("#hasDefaultValue").val("false");
				} else {
					$("#hasDefaultValue").val("true");
				}
			}, "json")
		});
	});
</script>

<script type="text/javascript">

function   formatDate(time)   {
    var   date= new Date(time);   
    var   year=date.getYear()+1900;     
    var   month=date.getMonth()+1;     
    var   day=date.getDate();
    var   hour=date.getHours();
    var   minute=date.getMinutes();
    var  second=date.getSeconds(); 
   return   year+"-"+month+"-"+day+"   "+hour+":"+minute+":"+second;
};

function addRow(postLogId,interfaceId,serviceUrl,requestIp,responseJson,createDate,createUser) {
		var addTable = document.getElementById("historyTable");
		var newRow = addTable.insertRow(0); //插入新行
		var col1 = newRow.insertCell(0);
		serviceUrl = serviceUrl.length>45?serviceUrl.substring(0,45)+"…… ":serviceUrl;
		col1.innerHTML = "<a href=\"/httppost/httppostLog/"+postLogId+"?interfaceId="+interfaceId+"\">"+serviceUrl+"&nbsp;</a>";
		var col2 = newRow.insertCell(1);
		col2.innerHTML = requestIp;
		var col3 = newRow.insertCell(2);
		col3.innerHTML = responseJson.length>50?responseJson.substring(0,45)+"…… ":responseJson;
		var col4 = newRow.insertCell(3);
		col4.innerHTML = createDate;
		var col5 = newRow.insertCell(4);
		col5.innerHTML=createUser;
	}
</script>

<body data-spm="7395905" class="api" style="background:#31312E; color:#D2BCBC">
		<div class="wrap-inner block-docs-wrap" id="generateAutoCode">
			<input type="hidden" id="projectId" value="${projectId}">
			<input type="hidden" id="categoryId" value="${categoryId}">
			<input type="hidden" id="interfaceId" value="${interfaceId}">
			<input type="hidden" id="hasDefaultValue" value="false"/>
			请求地址:
			<br>
			<input spellcheck="false" id="serviceUrl" name="serviceUrl" style="width:800px; height: 35px; background:#1F1F1D;color:#A48FCC; border: 1px solid #656553;" type="text" id="prefixErrorCode" value="${serviceUrl}" placeholder="服务地址">&nbsp;
			<br>
			<br>
			<button id="requestJsonButton" style="width: 80px; height: 30px; background:#A7A0A0">请求Json体</button>
			<button id="requestHeadersButton" style="width: 80px; height: 30px; background:#A7A0A0">Headers</button>
			<button id="getRequestMapCode" style="width: 120px; height: 30px; background:#A7A0A0">生成请求Map体</button>
			<br>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="18" cols="200" style="overflow-x: hidden;" id="requestJson" name="requestJson" data-msg-required="">${requestJson}</textarea>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="15" cols="200" style="overflow-x: hidden;" id="requestHeaders" name="requestHeaders" data-msg-required="">${requestHeaders }</textarea>
			<br>
			<br>
			<button id="sendService" style="width: 80px; height: 45px; background:#CEC831"><font size="2">发送</font></button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<font id="responseTime" size="3" color="red">${reponseTime}</font>
			<br>
			<br>
			响应信息:
			&nbsp;
			<br>
			<textarea spellcheck="false" style="background:#1F1F1D; color:#A48FCC; border: 1px solid #656553;" rows="10" cols="200" style="overflow-x: hidden;" id="reponseContent" name="reponseContent" data-msg-required="">${reponseContent }</textarea>
			<br>
			<br>
			请求历史记录:
			<br>
			<table class="wrap-inner" id="historyTable" style="float:center;">
				<c:forEach items="${postLogHistory }" var="postLog" varStatus="status">
					<tr style="color:#A48FCC">
						<td width="26%"><a href="/httppost/httppostLog/${postLog.id}?interfaceId=${interfaceId}" ><c:choose><c:when test="${postLog.serviceUrl.length() > 45}">${postLog.serviceUrl.substring(0, 45) }……&nbsp;</c:when><c:otherwise>${postLog.serviceUrl}&nbsp;</c:otherwise></c:choose></a></td>
						<td width="8%">${postLog.requestIp} &nbsp;</td>
						<td width="26%"><c:choose><c:when test="${postLog.responseJson.length() > 50}">${postLog.responseJson.substring(0, 45) }……&nbsp;</c:when><c:otherwise>${postLog.responseJson}&nbsp;</c:otherwise></c:choose></td>
						<td width="15%"><fmt:formatDate value="${postLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
						<td width="8%">${postLog.createUser}&nbsp;</td>
						<c:if test="${status.index >= 200}">
						  		 <td width="8%"><a href="/httppost/deleteHttppostLogById/${postLog.id}?interfaceId=${interfaceId}" onclick="return confirm('你要确定要删除吗');" >删除</a>&nbsp;</td>
                        </c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
</body>
</html>