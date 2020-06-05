<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>登录页面--互金接口资源平台</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="renderer" content="webkit">
<!-- BEGIN GLOBAL MANDATORY STYLES -->

<link href="/metronic/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="/metronic/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
<link href="/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="/metronic/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="/metronic/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="/metronic/assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css" />
<link href="/metronic/assets/admin/pages/css/login-soft.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="/metronic/assets/global/css/components.css" rel="stylesheet" type="text/css" />
<link href="/metronic/assets/global/css/plugins.css" rel="stylesheet" type="text/css" />
<link href="/metronic/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css" />
<link id="style_color" href="/metronic/assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css" />
<link href="/metronic/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
</head>
<body class="login">
	<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
	<div class="menu-toggler sidebar-toggler"></div>
	<!-- END SIDEBAR TOGGLER BUTTON -->
	<!-- BEGIN LOGIN -->
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<form class="login-form" action="/admin/login" method="post">
			<div class="logo"><img src="/images/logo-login.png"></div>
			<div class="form-group">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9">用户名</label>
				<div class="input-icon">
					<i class="fa fa-user"></i> <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" name="username" value="${username }" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">密 码</label>
				<div class="input-icon">
					<i class="fa fa-lock"></i> <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="密  码" name="password" onload="this.value=''" />
				</div>
			</div>
			<div class="alert alert-danger display-hide">
				<!-- <button class="close" data-close="alert"></button> -->
				<span>${message }. </span>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn blue pull-right">
					<i class="m-icon-login"></i> 登录系统
				</button>
			</div>
		</form>
		<!-- END LOGIN FORM -->

		<!-- BEGIN COPYRIGHT -->
		<div class="copyright">
			2014 &copy; <a href="https://www.bjxcaifu.com" target="_blank">传送门.</a>
		</div>
		<!-- END COPYRIGHT -->

	</div>
	<!-- END LOGIN -->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
<script src="/metronic/assets/global/plugins/respond.min.js"></script>
<script src="/metronic/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
	<script src="/metronic/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="/metronic/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="/metronic/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="/metronic/assets/global/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="/metronic/assets/global/plugins/select2/select2.min.js"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="/metronic/assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="/metronic/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
	<script src="/metronic/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
	<script src="/metronic/assets/admin/pages/scripts/login-soft.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout
			QuickSidebar.init() // init quick sidebar
			Login.init();

			if ("${message}") {//登录错误信息
				$('.alert-danger', $('.login-form')).show();
			}
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>