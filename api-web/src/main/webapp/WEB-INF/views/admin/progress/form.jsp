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
					<input name="id" type="hidden" /> <input name="interfaceId" type="hidden" />
					<div class="form-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">开始时间<span class="required">*</span></label>
									<div class="col-md-6">
										<div class="input-group  date date-picker" data-date-language="zh-CN" data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control" name="startDate" readonly="readonly" required="required"> <span class="input-group-btn">
												<button class="btn default" type="button">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">结束时间<span class="required">*</span></label>
									<div class="col-md-6">
										<div class="input-group  date date-picker" data-date-language="zh-CN" data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control" name="endDate" readonly="readonly" required="required"> <span class="input-group-btn">
												<button class="btn default" type="button">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">开发人员<span class="required">*</span></label>
									<div class="col-md-6">
										<select name="developer" class="form-control select2me" required="required">
											<option value=""></option>
											<c:forEach items="${developers }" var="item">
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
									<label class="col-md-4 control-label">分支<span class="required">*</span></label>
									<div class="col-md-6">
										<select class="form-control cascade_branchId" name="branchId" required="required"></select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">当前进度<span class="required">*</span></label>
									<div class="col-md-6">
										<div class="input-group">
											<select name="progress" class="form-control" required="required" data-rule-min="0" data-rule-max="100" data-rule-digits="true">
												<option value="0">0</option>
												<option value="10">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="40">40</option>
												<option value="50">50</option>
												<option value="60">60</option>
												<option value="70">70</option>
												<option value="80">80</option>
												<option value="90">90</option>
												<option value="100">100</option>
											</select> <span class="input-group-addon">%</span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">进度备注<span class="required">*</span></label>
									<div class="col-md-6">
									     <input type="text" required="required" class="form-control" name="memo" />
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
<%@ include file="showprogresslog.jsp"%>