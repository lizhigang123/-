<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html class="win">
<head>
<title>阿特门开放平台-首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<link rel="stylesheet" href="/css/apiList/sui.min.css">
<link rel="stylesheet" href="/css/apiList/framework.min.css">
<link rel="stylesheet" href="/css/apiList/font_1429753170_0792055.css" type="text/css">
<link rel="stylesheet" href="/css/apiList/doc-min.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/apiList/docs-detail-v2.css">
<link rel="stylesheet" href="/css/apiList/doc-detail.css">
<script type="text/javascript">
	function submitBtnClick() {
		if(document.getElementById("keyword").value == ""){
			alert("请输入搜索内容");
			return;
		}
		document.getElementById("search-form").submit();
	};
	function toolBtnClick() {
		window.open("/tool");
	};
</script>
</head>

<body>
	<div class="openplatform-nav openplatform-nav-simple " data-spm="1">
		<div class="container-fluid">
			<div class="logo-container">
				<h1>
					<a href="/"><i class="open-iconfont open-icon-alibaba"></i>阿特门开放平台</a>
				</h1>
			</div>
			<div class="top-openplatform-nav pull-left">
				<ul>
					<c:forEach items="${projectEntityList}" var="pe" varStatus="status">
						<li><a href="/api/project/${pe.id}" title="">${pe.name}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>

	<div id="pages">
		<div class="wrap">
			<div class="top-blue-bar">
				<div class="wrap-inner">
					<div class="crumbs">
						<a href="/"> 首页 </a>
						<c:if test="${projectEneity.name!=null}">
							&gt;
						</c:if>
						<a href="/api/project/${projectEneity.id}">${projectEneity.name}</a>
						<c:if test="${categoryEntity.name!=null}">
						&gt;
						</c:if>
						${categoryEntity.name}
					</div>
					<div class="search-wrap" style="margin-top: 23px; line-height: 34px;">
					    <div class="search">
					        <form action="/api/search" name="search-form" id="search-form" target="_blank">
					            <input placeholder="接口名/访问路径" value="" id="keyword" name="keyword" class="ipt-text J_searchIpt" autocomplete="off" style="height: 34px;">
					            <i class="iconfont J_SearchBtn" onclick="submitBtnClick()" style="right:24px;"></i>
					        </form>
					    </div>
					    <button onclick="toolBtnClick()"style="float: left; width: 62px; height: 34px; font-size: 14px; margin-left: 5px; background-color: #1f91c8; border: 1px solid #61b2da; color: #e0e0e0; border-radius: 3px;">工具</button>
					</div>
				</div>
			</div>

			<div class="wrap-inner block-docs-wrap">
				<div class="docs-left">
					<h3 class="menu-title">
						<c:if test="${projectEneity.name==null}">
						项目接口分类
					</c:if>
						${projectEneity.name}
					</h3>
					<ul class="menu-1">
						<c:forEach items="${categoryEntityList}" var="ce" varStatus="status">
							<li><a href="/api/category/${ce.id}" <c:if test="${ce.id==categoryEntity.id}">class="act"</c:if> title="${ce.name}API">${ce.name}</a></li>
						</c:forEach>
					</ul>
				</div>

				<div class="docs-right">
					<div class="mtl">
						<h2 class="mtl-main">${categoryEntity.name }</h2>
						<p class="mtl-desc">${categoryEntity.description }</p>
					</div>
					<div class="doc-detail-bd" id="bd">
						<table>
							<thead>
								<tr>
									<th width="39%">接口名称</th>
									<th width="10%">版本号</th>
									<th width="10%">是否需要授权</th>
									<th width="51%">描述</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${interfaceEntityList}" var="ie">
									<tr>
										<td><a href="/api/interface/${ie.id}">${ie.name}</a></td>
										<td><span class="fees-wrap"><span class="angle"><i class="angle-out"></i><i class="angle-in"></i></span><span class="inner">${ie.v}</span></span></td>
										<td>${'1' eq ie.authorize ? '是':'否'}</td>
										<td>${ie.description}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>