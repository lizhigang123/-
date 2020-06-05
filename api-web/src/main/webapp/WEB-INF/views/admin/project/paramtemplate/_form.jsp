<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglibs.jsp" %>
<input type="hidden" name="id" />
<h3 class="form-section">
	接口参数
	<sl:permission url="/admin/project/paramtemplate/submit">
 	<button class="btn btn-sm blue-hoki" id="input_param_add_top" style="float: right;">
		添加接口参数<i class="fa fa-plus"></i>
	</button>
	</sl:permission>
</h3>
<div class="row" id="input_grid_tree_row">
	<div class="col-md-12">
		<table id="inputGridTree" class="table table-striped table-bordered table-hover"></table>
	</div>
</div>
 
<div id="entity_modal_layer" class="modal fade  in" tabindex="-1" role="dialog" aria-hidden="false">
	<div class="modal-dialog  ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">参数详情</h4>
			</div>
			<div class="modal-body">
				<form target="" method="POST" action="" enctype="application/x-www-form-urlencoded" id="entity_modal_form" class="form-horizontal " novalidate="novalidate">
					<input name="propertiestId" type="hidden" value="${propertiestId }"><input name="id" type="hidden" value=""> <input name="parentId" type="hidden" value=""> <input name="type" type="hidden" value=""> <input name="ordered" type="hidden" value="">
					<div class="form-body " id="">
						<div class="row ">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">参数名称<span class="required">*</span></label>
									<div class="col-md-6">
										<input type="text" class="form-control" id="" name="name" value="" required="required">
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">参数中文名称<span class="required">*</span></label>
									<div class="col-md-6">
										<input type="text" class="form-control" id="" name="nameDesc" value="" required="required">
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">参数类型<span
										class="required">*</span></label>
									<div class="col-md-6">
									 	 <input name="dataType" type="hidden" class="form-control  select2me-data-type"  required="required" />
   									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="row ">
								<div class="col-md-12 ">
									<div class="form-group">
										<label class="col-md-4 control-label">是否可见<span class="required">*</span></label>
										<div class="col-md-6">
											<select class="form-control" name="visible" required="required">
												<option value="是" selected="selected">是</option>
												<option value="否">否</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-12 ">
								<div class="form-group">
									<label class="col-md-4 control-label">是否必填</label>
									<div class="col-md-6">
										<select class="form-control" name="need">
											<option value=""></option>
											<option value="可选">可选</option>
											<option value="必须" selected="selected">必须</option>
											<option value="特殊可选">特殊可选</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-md-12 ">
								<div class="form-group">
									<label class="col-md-4 control-label">默认值</label>
									<div class="col-md-6">
										<input type="text" class="form-control" id="" name="defaultValue" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-md-12 ">
								<div class="form-group">
									<label class="col-md-4 control-label">描述</label>
									<div class="col-md-6">
										<textarea rows="5" class="form-control" id="" name="description" value=""></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn green submit" style="display: none;">确认</button>
				<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
			</div>
		</div>
	</div>
</div>