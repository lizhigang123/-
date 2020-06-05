<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
		document.getElementById("search-form").submit();
	}
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
						<a href="/"> 首页 </a>&gt;搜索结果
					</div>
				</div>
			</div>

			<div class="wrap-inner block-docs-wrap" style="display:none;">
				<div class="docs-left">
					<h3 class="menu-title">
						<c:if test="${projectEneity.name==null}">
						项目接口分类
					</c:if>
						${projectEneity.name}
					</h3>
					<ul class="menu-1">
						<c:forEach items="${categoryEntityList}" var="ce" varStatus="status">
							<li><a href="/api/category/${ce.id}" title="${ce.name}API" <c:if test="${ce.id==categoryEntity.id}">class="act"</c:if>>${ce.name}</a></li>
						</c:forEach>
					</ul>
				</div>

				<div class="docs-right">
					<div class="doc-detail-bd" id="bd">
						<table>
							<thead>
								<tr>
									<th width="100%">测试环境地址</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><a href="${projectEneity.testUrl}">${projectEneity.testUrl}</a></td>
								</tr>
							</tbody>
						</table>
						<table>
							<thead>
								<tr>
									<th width="100%">生产环境地址</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><a href="${projectEneity.productionUrl}">${projectEneity.productionUrl}</a></td>
								</tr>
							</tbody>
						</table>
						<table>
							<thead>
								<tr>
									<th width="100%">SVN地址</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><a href="${projectEneity.svnUrl}">${projectEneity.svnUrl}</a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<div class="wrap-inner block-docs-wrap J_FloatContainer">
				<div class="docs-left" id="sideMenu">
					<c:forEach items="${projectList}" var="project">
						<h3 class="menu-title">${project.name }</h3>
							<ul class="menu-1 J_subMenu">
								<c:forEach items="${project.machList}" var="item">
									<li><a href="/api/interface/${item.id}" title="${item.url}">
											${item.url}<p>${item.name}</p>
									</a></li>
								</c:forEach>
							</ul>
						</c:forEach>
				</div>
				<!--E FAQ -->
			</div>
		</div>			
	</div>
</body>
</html>