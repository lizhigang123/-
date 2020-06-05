<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div id="modify_password_modal_layer" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<form id="modify_password_modal_form" class="form-horizontal">
					<input name="id" type="hidden" /> 
					<div class="form-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">新密码 <span class="required">*</span>
									</label>
									<div class="col-md-6">
										<input type="password" required="required" class="form-control" name="password" id="newpassword"/>
									</div>
								</div>
							</div>
						</div>
						 
						<div class="row password">
							<div class="col-md-12">
								<div class="form-group ">
									<label class="col-md-4 control-label">确认密码 <span class="required">*</span>
									</label>
									<div class="col-md-6">
										<input type="password" required="required" class="form-control" data-rule-equalTo="#newpassword" data-msg-equalTo="确认密码和密码不一致"/>
									</div>
								</div>
							</div>
						</div>
						 
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn green modifysubmit">确认</button>
				<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
			</div>
		</div>
	</div>
</div>