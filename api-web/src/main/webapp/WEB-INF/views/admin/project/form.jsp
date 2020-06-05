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
					<input name="id" type="hidden" /> <input name="parentId" type="hidden" value="${parentId }" />
					<div class="form-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">${empty parentId?'项目名称':'分支名称' } <span class="required">*</span>
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
									<label class="col-md-4 control-label">责任人<span class="required">*</span></label>
									<div class="col-md-6">
										<select name="leader" class="form-control select2me" required="required">
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
									<label class="col-md-4 control-label">svn地址 <span class="required">*</span>
									</label>
									<div class="col-md-6">
										<input type="text" required="required" class="form-control" name="svnUrl" data-rule-url="true"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">开始时间</label>
									<div class="col-md-6">
										<div class="input-group input-medium date date-picker" data-date-format="yyyy-mm-dd" data-date-language="zh-CN">
											<input type="text" class="form-control" name="startDate" /> <span class="input-group-btn">
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
									<label class="col-md-4 control-label">测试环境地址 </label>
									<div class="col-md-6">
										<input type="text" class="form-control" name="testUrl" data-rule-url=true />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">生产环境地址 </label>
									<div class="col-md-6">
										<input type="text" class="form-control" name="productionUrl" data-rule-url=true />
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label"> 项目描述</label>
									<div class="col-md-6">
										<textarea class="form-control" rows="5" name="description" maxlength="50"></textarea>
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

