<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div id="entity_modal_layer" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<form id="entity_modal_form" class="form-horizontal">
					<input name="id" type="hidden" /> 
					<div class="form-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">用户名 <span class="required">*</span>
									</label>
									<div class="col-md-6">
										<input type="text" required="required" class="form-control" name="name" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">登录名 <span class="required">*</span>
									</label>
									<div class="col-md-6">
										<input type="text" required="required" class="form-control" name="loginName" />
									</div>
								</div>
							</div>
						</div>
						<div class="row password">
							<div class="col-md-12">
								<div class="form-group ">
									<label class="col-md-4 control-label">密码 <span class="required">*</span>
									</label>
									<div class="col-md-6">
										<input type="password" required="required" class="form-control" name="password" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">邮箱<span class="required">*</span>
									</label>
									<div class="col-md-6">
										<input type="text" required="required" data-rule-email="true" class="form-control" name="email" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn green submit">确认</button>
				<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
			</div>
		</div>
	</div>
</div>