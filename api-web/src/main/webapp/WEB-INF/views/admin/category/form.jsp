<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
									<label class="col-md-4 control-label">项目<span class="required">*</span></label>
									<div class="col-md-6">
										<select class="form-control" name="projectId" required="required">
											<option value=""></option>
											<c:forEach items="${projects.data }" var="item">
												<option value="${item.id }">${item.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">类目名 <span class="required">*</span>
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
									<label class="col-md-4 control-label">是否可见<span class="required">*</span></label>
									<div class="radio-list">
										<label class="radio-inline"> <input type="radio" name="visible" value="是" required="required"> 是
										</label> <label class="radio-inline"> <input type="radio" name="visible" value="否" required="required"> 否
										</label>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">排序(降序)</label>
									<div class="col-md-6">
										<input type="text"  class="form-control" name="sort" data-rule-digits="true"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">描述信息</label>
									<div class="col-md-6">
										<textarea rows="5" name="description" class="form-control"></textarea>
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