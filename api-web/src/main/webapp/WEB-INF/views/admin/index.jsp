<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="renderer" content="webkit">
<link
	href="/metronic/assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="/metronic/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="/metronic/assets/global/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<link
	href="/metronic/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />

<link rel='stylesheet' type='text/css'
	href='/metronic/assets/global/plugins/fullcalendar/fullcalendar/fullcalendar.css' />
<link rel="stylesheet" type="text/css"
	href="/metronic/assets/global/plugins/bootstrap-datepicker/css/datepicker3.css" />
<link rel="stylesheet" type="text/css"
	href="/metronic/assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" />
<link rel="stylesheet" type="text/css"
	href="/metronic/assets/global/plugins/bootstrap-colorpicker/css/colorpicker.css" />
<link rel="stylesheet" type="text/css"
	href="/metronic/assets/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css"
	href="/metronic/assets/global/plugins/bootstrap-datetimepicker/css/datetimepicker.css" />
<link rel="stylesheet" type="text/css"
	href="/metronic/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" />

<link rel="stylesheet" type="text/css"
	href="/metronic/assets/global/plugins/select2/select2.css" />
<link href="/metronic/assets/global/css/components.css" rel="stylesheet"
	type="text/css" />
<link href="/metronic/assets/global/css/plugins.css" rel="stylesheet"
	type="text/css" />
<link href="/metronic/assets/admin/layout/css/layout.css"
	rel="stylesheet" type="text/css" />
<link id="style_color"
	href="/metronic/assets/admin/layout/css/themes/light.css"
	rel="stylesheet" type="text/css" />
<link href="/metronic/assets/admin/layout/css/custom.css"
	rel="stylesheet" type="text/css" />
<link href="/js/plugin/easyui1.4.5/themes/bootstrap/easyui.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
.bootbox-body {
	word-wrap: break-word;
}

.modal {
	top: 10%;
}
</style>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
<title>阿特门开发平台--后台管理系统</title>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body
	class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo">
	<!-- BEGIN HEADER -->
	<div class="page-header navbar navbar-fixed-top">
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner">
			<!-- BEGIN LOGO -->
			<div class="page-logo">
				<a href="/"> <img src="/images/logo-invert.png" alt="logo"
					class="logo-default" />
				</a>
			</div>
			<a href="javascript:;" class="menu-toggler responsive-toggler"
				data-toggle="collapse" data-target=".navbar-collapse"></a>
			<div class="top-menu">
				<ul class="nav navbar-nav pull-right">
					<li class="dropdown dropdown-user"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"
						data-hover="dropdown" data-close-others="true"> <span
							class="username">欢迎您, ${sessionScope.user.name }</span><i
							class="fa fa-angle-down"></i>
					</a>
						<ul class="dropdown-menu">
							<!-- <li><a href="#"> <i class="icon-rocket"></i> 我的任务<span class="badge badge-success"> 7 </span></a></li> -->
							<!-- <li><a href="/lock"> <i class="icon-lock"></i>锁定屏幕 -->
							</a></li>
					<li><a href="/admin/logout"> <i class="icon-key"></i> 退出
					</a></li>
				</ul>
				</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="page-container">
		<div class="page-sidebar-wrapper">
			<div class="page-sidebar navbar-collapse collapse">
				<ul class="page-sidebar-menu" data-slide-speed="200">
					<li class="sidebar-toggler-wrapper">
						<div class="sidebar-toggler"></div>
					</li>
					<li class="sidebar-search-wrapper hidden-xs">
						<form class="sidebar-search" action="extra_search.html"
							method="POST">
							<a href="javascript:;" class="remove"><i class="icon-close"></i></a>
							<div class="input-group">
								<span class="input-group-btn"> </span>
							</div>
						</form>
					</li>
					<li class="start active" id="index_active_li"><a href="/">
							<i class="icon-home"></i> <span class="title">前台首页</span> <span
							class="selected"></span>
					</a></li>
					<sl:permission url="/admin/project">
						<li><a class="ajaxify" href="/admin/project"><i
								class="fa fa-database"></i>项目管理</a></li>
					</sl:permission>
					<sl:permission url="/admin/category">
						<li><a class="ajaxify" href="/admin/category"><i
								class="fa fa-folder"></i>类目管理</a></li>
					</sl:permission>
					<sl:permission url="/admin/interface">
						<li><a class="ajaxify" href="/admin/interface"><i
								class="fa fa-file"></i>接口管理</a></li>
					</sl:permission>
					<sl:permission url="/admin/progress">
						<li><a class="ajaxify" href="/admin/progress"><i
								class="fa fa-spinner"></i>进度管理</a></li>
					</sl:permission>
					<sl:permission url="/admin/user">
						<li><a class="ajaxify" href="/admin/user"><i
								class="fa fa-user"></i>用户管理</a></li>
					</sl:permission>
					<sl:permission url="/admin/role">
						<li><a class="ajaxify" href="/admin/role"><i
								class="fa fa-lock"></i>角色管理</a></li>
					</sl:permission>
					<sl:permission url="/admin/resource">
						<li><a class="ajaxify" href="/admin/resource"><i
								class="fa fa-lock"></i>资源管理</a></li>
					</sl:permission>

					<!--<li>
					<a href="#"><i class="icon-bubble"></i><span class="title">客户管理</span><span class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a class="ajaxify" href="wealth/customer"><i class="icon-home"></i>客户信息</a></li>
						<li><a class="ajaxify" href="wealth/customerModify"><i class="icon-basket"></i>信息变更</a></li>
						<li><a class="ajaxify" href="wealth/customerManager"><i class="icon-home"></i>客户经理变更</a></li>
						<li><a class="ajaxify" href="wealth/coreInfoModify"><i class="icon-basket"></i>核心信息变更</a></li>
					</ul>
				</li> -->
					<li><a href="/metronic/templates/admin/index.html"
						target="_blank"> <i class="icon-share"></i> <span
							class="title">帮助文档</span>
					</a></li>


				</ul>
			</div>
		</div>
		<div class="page-content-wrapper">
			<div class="page-content">
				<div class="theme-panel hidden-xs hidden-sm">
					<div class="toggler"></div>
					<div class="toggler-close"></div>
					<div class="theme-options">
						<div class="theme-option theme-colors clearfix">
							<span>主题颜色</span>
							<ul>
								<li class="color-default current tooltips" data-style="default"
									data-original-title="Default"></li>
								<li class="color-darkblue tooltips" data-style="darkblue"
									data-original-title="Dark Blue"></li>
								<li class="color-blue tooltips" data-style="blue"
									data-original-title="Blue"></li>
								<li class="color-grey tooltips" data-style="grey"
									data-original-title="Grey"></li>
								<li class="color-light tooltips" data-style="light"
									data-original-title="Light"></li>
								<li class="color-light2 tooltips" data-style="light2"
									data-html="true" data-original-title="Light 2"></li>
							</ul>
						</div>
						<div class="theme-option">
							<span>布局</span> <select
								class="layout-option form-control input-small">
								<option value="fluid" selected="selected">Fluid</option>
								<option value="boxed">Boxed</option>
							</select>
						</div>
						<div class="theme-option">
							<span>Header </span> <select
								class="page-header-option form-control input-small">
								<option value="fixed" selected="selected">Fixed</option>
								<option value="default">Default</option>
							</select>
						</div>
						<div class="theme-option">
							<span>Sidebar </span> <select
								class="sidebar-option form-control input-small">
								<option value="fixed">Fixed</option>
								<option value="default" selected="selected">Default</option>
							</select>
						</div>
						<div class="theme-option">
							<span>Sidebar Position </span> <select
								class="sidebar-pos-option form-control input-small">
								<option value="left" selected="selected">Left</option>
								<option value="right">Right</option>
							</select>
						</div>
						<div class="theme-option">
							<span>Footer </span> <select
								class="page-footer-option form-control input-small">
								<option value="fixed">Fixed</option>
								<option value="default" selected="selected">Default</option>
							</select>
						</div>
					</div>
				</div>
				<!-- END STYLE CUSTOMIZER -->
				<div class="page-content-body">
					<!-- HERE WILL BE LOADED AN AJAX CONTENT -->
					<!-- BEGIN PAGE HEADER-->
					<div class="row">
						<div class="col-md-12">
							<!-- BEGIN PAGE TITLE & BREADCRUMB-->
							<h3 class="page-title">控制面板</h3>
							<ul class="page-breadcrumb breadcrumb">
								<li><i class="fa fa-home"></i><a href="#">首页</a><i
									class="fa fa-angle-right"></i></li>
								<li><a href="#">面板</a></li>
								<li class="pull-right">
									<div id="dashboard-report-range"
										class="dashboard-date-range tooltips" data-placement="top"
										data-original-title="Change dashboard date range">
										<i class="icon-calendar"></i><span></span><i
											class="fa fa-angle-down"></i>
									</div>
								</li>
							</ul>
							<!-- END PAGE TITLE & BREADCRUMB-->
						</div>
					</div>
					<!-- END PAGE HEADER-->
					<!-- BEGIN DASHBOARD STATS -->
					<div class="row">
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
							<div class="dashboard-stat blue-madison">
								<div class="visual">
									<i class="fa fa-comments"></i>
								</div>
								<div class="details">
									<div class="number">1349</div>
									<div class="desc">New Feedbacks</div>
								</div>
								<a class="more" href="#">View more <i
									class="m-icon-swapright m-icon-white"></i></a>
							</div>
						</div>
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
							<div class="dashboard-stat red-intense">
								<div class="visual">
									<i class="fa fa-bar-chart-o"></i>
								</div>
								<div class="details">
									<div class="number">12,5M$</div>
									<div class="desc">Total Profit</div>
								</div>
								<a class="more" href="#">View more <i
									class="m-icon-swapright m-icon-white"></i></a>
							</div>
						</div>
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
							<div class="dashboard-stat green-haze">
								<div class="visual">
									<i class="fa fa-shopping-cart"></i>
								</div>
								<div class="details">
									<div class="number">549</div>
									<div class="desc">New Orders</div>
								</div>
								<a class="more" href="#">View more <i
									class="m-icon-swapright m-icon-white"></i></a>
							</div>
						</div>
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
							<div class="dashboard-stat purple-plum">
								<div class="visual">
									<i class="fa fa-globe"></i>
								</div>
								<div class="details">
									<div class="number">+89%</div>
									<div class="desc">Brand Popularity</div>
								</div>
								<a class="more" href="#">View more <i
									class="m-icon-swapright m-icon-white"></i></a>
							</div>
						</div>
					</div>
					<!-- END DASHBOARD STATS -->
					<div class="clearfix"></div>

					<div class="row">
						<div class="col-md-6 col-sm-6">
							<!-- BEGIN PORTLET-->
							<div class="portlet solid bordered grey-cararra">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa-bar-chart-o"></i>Site Visits
									</div>
									<div class="tools">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn grey-steel btn-sm active"><input
												type="radio" name="options" class="toggle" id="option1">New</label>
											<label class="btn grey-steel btn-sm"><input
												type="radio" name="options" class="toggle" id="option2">Returning</label>
										</div>
									</div>
								</div>
								<div class="portlet-body">
									<div id="site_statistics_loading">
										<img src="/metronic/assets/admin/layout/img/loading.gif"
											alt="loading" />
									</div>
									<div id="site_statistics_content" class="display-none">
										<div id="site_statistics" class="chart"></div>
									</div>
								</div>
							</div>
							<!-- END PORTLET-->
						</div>
						<div class="col-md-6 col-sm-6">
							<!-- BEGIN PORTLET-->
							<div class="portlet solid grey-cararra bordered">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa-bullhorn"></i>Revenue
									</div>
									<div class="tools">
										<div class="btn-group pull-right">
											<a href="" class="btn grey-steel btn-sm dropdown-toggle"
												data-toggle="dropdown" data-hover="dropdown"
												data-close-others="true"> Filter <span
												class="fa fa-angle-down"> </span></a>
											<ul class="dropdown-menu pull-right">
												<li><a href="javascript:;"> Q1 2014 <span
														class="label label-sm label-default"> past </span></a></li>
												<li><a href="javascript:;"> Q2 2014 <span
														class="label label-sm label-default"> past </span></a></li>
												<li class="active"><a href="javascript:;"> Q3 2014<span
														class="label label-sm label-success"> current </span></a></li>
												<li><a href="javascript:;"> Q4 2014 <span
														class="label label-sm label-warning"> upcoming </span></a></li>
											</ul>
										</div>
									</div>
								</div>

								<div class="portlet-body">
									<div id="site_activities_loading">
										<img src="/metronic/assets/admin/layout/img/loading.gif"
											alt="loading" />
									</div>
									<div id="site_activities_content" class="display-none">
										<div id="site_activities" style="height: 228px;"></div>
									</div>
									<div style="margin: 20px 0 10px 30px">
										<div class="row">
											<div class="col-md-3 col-sm-3 col-xs-6 text-stat">
												<span class="label label-sm label-success"> Revenue:</span>
												<h3>$13,234</h3>
											</div>
											<div class="col-md-3 col-sm-3 col-xs-6 text-stat">
												<span class="label label-sm label-info"> Tax: </span>
												<h3>$134,900</h3>
											</div>
											<div class="col-md-3 col-sm-3 col-xs-6 text-stat">
												<span class="label label-sm label-danger"> Shipment:</span>
												<h3>$1,134</h3>
											</div>
											<div class="col-md-3 col-sm-3 col-xs-6 text-stat">
												<span class="label label-sm label-warning"> Orders: </span>
												<h3>235090</h3>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- END PORTLET-->
						</div>
					</div>
					<div class="clearfix"></div>

					<div class="row ">
						<div class="col-md-6 col-sm-6">
							<div class="portlet box purple-wisteria">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa-calendar"></i>General Stats
									</div>
									<div class="actions">
										<a href="javascript:;"
											class="btn btn-sm btn-default easy-pie-chart-reload"><i
											class="fa fa-repeat"></i> Reload </a>
									</div>
								</div>
								<div class="portlet-body">
									<div class="row">
										<div class="col-md-4">
											<div class="easy-pie-chart">
												<div class="number transactions" data-percent="55">
													<span>+55 </span>%
												</div>
												<a class="title" href="#">Transactions <i
													class="icon-arrow-right"></i></a>
											</div>
										</div>
										<div class="margin-bottom-10 visible-sm"></div>
										<div class="col-md-4">
											<div class="easy-pie-chart">
												<div class="number visits" data-percent="85">
													<span>+85 </span>%
												</div>
												<a class="title" href="#">New Visits <i
													class="icon-arrow-right"></i></a>
											</div>
										</div>
										<div class="margin-bottom-10 visible-sm"></div>
										<div class="col-md-4">
											<div class="easy-pie-chart">
												<div class="number bounce" data-percent="46">
													<span>-46 </span>%
												</div>
												<a class="title" href="#">Bounce <i
													class="icon-arrow-right"></i></a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="col-md-6 col-sm-6">
							<div class="portlet box red-sunglo">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa-calendar"></i>Server Stats
									</div>
									<div class="tools">
										<a href="" class="collapse"></a> <a href="#portlet-config"
											data-toggle="modal" class="config"></a> <a href=""
											class="reload"></a> <a href="" class="remove"></a>
									</div>
								</div>
								<div class="portlet-body">
									<div class="row">
										<div class="col-md-4">
											<div class="sparkline-chart">
												<div class="number" id="sparkline_bar"></div>
												<a class="title" href="#">Network <i
													class="icon-arrow-right"></i></a>
											</div>
										</div>
										<div class="margin-bottom-10 visible-sm"></div>
										<div class="col-md-4">
											<div class="sparkline-chart">
												<div class="number" id="sparkline_bar2"></div>
												<a class="title" href="#">CPU Load <i
													class="icon-arrow-right"></i></a>
											</div>
										</div>
										<div class="margin-bottom-10 visible-sm"></div>
										<div class="col-md-4">
											<div class="sparkline-chart">
												<div class="number" id="sparkline_line"></div>
												<a class="title" href="#">Load Rate <i
													class="icon-arrow-right"></i></a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<!-- BEGIN CONTENT -->
		</div>
		<!-- END CONTENT -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="page-footer">
		<div class="page-footer-inner">2014 &copy; 阿特门.</div>
		<div class="page-footer-tools">
			<span class="go-top"><i class="fa fa-angle-up"></i></span>
		</div>
	</div>
	<!-- 无权限访问 弹层提示 -->
	<div class="modal fade" id="basic" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">Modal Title</h4>
				</div>
				<div class="modal-body">Modal body goes here</div>
				<div class="modal-footer">
					<button type="button" class="btn default" data-dismiss="modal">Close</button>
					<button type="button" class="btn blue">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 弹层异步加载可公用包装层 -->
	<div id="every_layer_wrapper"></div>

	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
<script src="/metronic/assets/global/plugins/respond.min.js"></script>
<script src="/metronic/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
	<!-- 原生js 原型扩展 和兼容 -->
	<script src="/js/prototypeutils.js" type="text/javascript"></script>

	<script src="/js/jquery-1.10.2.js" type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/jquery-migrate-1.2.1.min.js"
		type="text/javascript"></script>

	<!-- jquery ueasyui  放在jquery ui前面 不然 日历插件 没有拖拽效果-->
	<script type="text/javascript"
		src="/js/plugin/easyui1.4.5/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="/js/plugin/easyui1.4.5/locale/easyui-lang-zh_CN.js"></script>

	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script
		src="/metronic/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
		type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/jquery.cokie.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/uniform/jquery.uniform.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
		type="text/javascript"></script>

<!-- 	<script src="/metronic/assets/global/plugins/flot/jquery.flot.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/flot/jquery.flot.resize.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/flot/jquery.flot.categories.min.js"
		type="text/javascript"></script> -->
	<script src="/metronic/assets/global/plugins/jquery.pulsate.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/bootstrap-daterangepicker/moment.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.js"
		type="text/javascript"></script>

	<script
		src="/metronic/assets/global/plugins/fullcalendar/fullcalendar/fullcalendar.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/jquery-easypiechart/jquery.easypiechart.min.js"
		type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/jquery.sparkline.min.js"
		type="text/javascript"></script>
	<script
		src="/metronic/assets/global/plugins/gritter/js/jquery.gritter.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/select2/select2.min.js"></script>
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/select2/select2_locale_zh-CN.js"></script>
	<script src="/metronic/assets/global/scripts/metronic.js"
		type="text/javascript"></script>
	<script src="/metronic/assets/admin/layout/scripts/layout.js"
		type="text/javascript"></script>
	<script src="/metronic/assets/admin/layout/scripts/quick-sidebar.js"
		type="text/javascript"></script>

	<script src="/metronic/assets/admin/pages/scripts/index.js"
		type="text/javascript"></script>
	.

	<!-- 日历插件 -->
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>

	<!-- 表单验证 -->
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/jquery-validation/js/additional-methods.js"></script>
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/jquery-validation/js/localization/messages_zh.js"></script>

	<!-- 弹出框 -->
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/bootbox/bootbox.js"></script>

	<!-- 图片列表和弹层 -->
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/jquery-mixitup/jquery.mixitup.min.js"></script>
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/fancybox/source/jquery.fancybox.pack.js"></script>

	<!-- 自定义js文件 -->
	<script src="/js/common.js" type="text/javascript"></script>
	<script src="/js/format.js" type="text/javascript"></script>
	<script src="/js/constant.js" type="text/javascript"></script>

	<!-- 数据表格 需要引入的js文件-->
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/datatables/media/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="/metronic/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
	<script type="text/javascript"
		src="/metronic/assets/global/scripts/datatable.js"></script>

	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout
			QuickSidebar.init() // init quick sidebar
			$('.page-sidebar .ajaxify.start').click() // load the content for the dashboard page.
			Index.init();
			Index.initDashboardDaterange();
			//Index.initJQVMAP(); // init index page's custom scripts
			Index.initCalendar(); // init index page's custom scripts
			Index.initCharts(); // init index page's custom scripts
			Index.initChat();
			Index.initMiniCharts();
			//Tasks.initDashboardWidget();

			window.$wrapper = $("#every_layer_wrapper");

		});

		/* 	var topmenubox=$(".top-menu-nav");
		 topmenubox.each(function (a) {
		 var topmenub=$(this).children("li").children("a");
		 topmenub.click(function (a) {
		 var topmenuc=$(this).attr("href");
		 if(topmenuc.charAt(0)=="#") {
		 a.preventDefault();
		 topmenub.parent().removeClass("active");
		 $(this).parent().addClass("active");
		 $(topmenuc).parent(".topmenu-content").children("div").css({
		 display:"none"
		 });
		 $(topmenuc).css({
		 display:"block"
		 })
		 var initLoadURL = $(this).data("href")
		 if(initLoadURL&&initLoadURL!=""){
		 redirectHandle(initLoadURL);
		 }
		 }
		 })
		 }); */

		//剔除掉空子菜单的 菜单
		/* 	$(".topmenu-content .sub-menu").each(function(){
		 if($(this).find("li").length==0){
		 var $topMenu = $(this).closest("div.page-sidebar-menu");
		 $(this).closest("li").remove()
		 }
		 }); */
		//顶部导航处理
		/* 	$(".topmenu-content .page-sidebar-menu").each(function(){
		 if($(this).find("li").length==0){
		 var id = $(this).empty().attr("id");
		 if($(".hor-menu-light li."+id).length==0){
		 alert("系统导航故障，请联系管理员");
		 }else{
		 $(".hor-menu-light li."+id).remove();
		 }
		
		 }
		 }); */
		$.ajaxErrorOp = window.ajaxErrorOp = function(xhr, textStatus, errorThrown) {
			var httpStatus = xhr && xhr.status ? xhr.status : "";
			httpStatus = textStatus && textStatus.status ? textStatus.status : "";

			switch (httpStatus) {
			case (500):
				slalert("服务器系统内部错误");
				break;
			case (401):
				slalert("未登录");
				break;
			case (403):
				slalert("无权限执行此操作,如需此权限 请联系管理员开放此权限");
				break;
			case (408):
				slalert("请求超时");
				break;
			case (200)://当需要返回json但是返回错误页面
				$(".error_page_identify", textStatus.responseText).length > 0 && $.ajaxifyHistory.push("/") && $('.page-content .page-content-body').html(textStatus.responseText);
				break;
			default:
				slalert("网络异常,请检查网络");
			}
		}
		$(document).ajaxError($.ajaxErrorOp);

		/*屏蔽Backspace和刷新(f5|ctrl+r)刷新页面 ，输入内容不屏蔽*/
		document.onkeydown = function(e) {
			var event = e || window.event, keyCode = event.keyCode || event.which || event.charCode;//支持IE、FF
			var obj = event.target || event.srcElement;
			var t = obj.type || obj.getAttribute('type');
			var type = 0;
			if (t == "text" || t == "password" || t == "textarea") {
				/** f5   ctrl+r 页面刷新 */
				if ((keyCode == 116) || (event.ctrlKey && keyCode == 82)) {
					type = 1;
					disableRefreshPage(event, type);
					/**IE 兼容*/
					event.keyCode = 0;
					event.returnValue = false;
					return false;//返回值 兼容fixfox				
				}
			} else {
				if (keyCode == 8) {
					/** backspace 返回 */
					type = 2;
				} else if ((keyCode == 116) || (event.ctrlKey && keyCode == 82)) {
					/** f5   ctrl+r 页面刷新 */
					type = 1;
				} else {
					return true;
				}
				disableRefreshPage(event, type);
				/**IE 兼容*/
				event.keyCode = 0;
				event.returnValue = false;
				return false;//返回值 兼容fixfox
			}
		}
		/**1:刷新  2:后退*/
		function disableRefreshPage(event, type) {
			if (type && (type == 1 || type == 2)) {
				event.returnValue = false;
				if (type == 1) {
					var url = window.location.href;
					var menu = url.indexOf("#") == -1 ? "" : url.substring(url.indexOf("#") + 1, url.length);
					var $menu = $(".topmenu-content .sub-menu li a[href='" + menu + "']");
					var toPage = menu && $menu.length > 0 ? $menu.text() + "列表页" : "首页";
					if ($(".index_refresh_page_symbol").length == 0) {
						bootbox.dialog({
							message : "本系统刷新后跳转到【" + toPage + "】，确定刷新此页面吗？",
							title : "【刷新页面】操作提示",
							className : "index_refresh_page_symbol",
							buttons : {
								ok : {
									label : "确定",
									className : "green",
									callback : function() {
										window.location.reload();
									}
								},
								cancel : {
									label : "取消",
									className : "red"
								}
							}
						});
					}
				} else if (type == 2) {
					/* 				if($(".index_backspace_page_symbol").length==0){
					 bootbox.dialog({
					 message : "本系统后退操作可能离开此页面，确定离开此页面吗？",
					 title : "【回退页面】操作提示",
					 className : "index_backspace_page_symbol",
					 buttons : {
					 ok : {
					 label : "确定",
					 className : "green",
					 callback : function() {
					 window.history.go(-1);
					 }
					 },
					 cancel : {
					 label : "取消",
					 className : "red"
					 }
					 }
					 });
					 } */
				}
			}
		}
		/** 禁止右键菜单来操作 */
		/* 	document.oncontextmenu = function() {
		 if ($(".index_contextMenu_page_symbol").length == 0) {
		 bootbox.dialog({
		 message : "亲，你想多了吧。本页面禁止此操作！",
		 title : "【右键】操作提示",
		 className : "index_contextMenu_page_symbol",
		 buttons : {
		 ok : {
		 label : "是的",
		 className : "green"
		 }
		 }
		 });
		 }
		 return false;
		 } */

		$(function() {
			/** 加载页面时需要跳转到的menu位置 */
			(function(window) {

				var url = window.location.href;
				var menu = url.indexOf("#") != -1 ? url.substring(url.indexOf("#") + 1, url.length) : "";
				if (menu && menu != "") {
					var $menu = $(".topmenu-content .sub-menu li a[href='" + menu + "']");
					if ($menu.length > 0) {
						var id = $menu.closest(".page-sidebar-menu").attr("id");
						$(".top-menu-nav a[href='#" + id + "']").click();//顶部导航选中
						$menu.closest(".sub-menu").siblings("a").click();//左部导航一级菜单展开
						$menu.click();//左部导航二级菜点击事件
					}
				}
			})(window)
			/** 左导航每次点击时修改url添加锚点 以便刷新后跳到相应的menu上 */
			$(".topmenu-content a").click(function() {
				var url = window.location.href;
				url = url.indexOf("#") == -1 ? url + "#" + $(this).attr("href") : url.substring(0, url.indexOf("#") + 1) + $(this).attr("href");
				window.location.href = url;
			});
		})
		/*点击top菜单显示左菜单的首页提醒*/
		function showIndexMess(index) {
			if (index) {
				/*点击首页提醒加载到右边DIV*/
				$("#_index_active_" + index).click();
			}
			$("#index_active_li").addClass("active");
		}
	</script>
</body>
</html>