<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<input type="hidden" name="id" />
<h3 class="form-section">
	请求参数
	<sl:permission url="/admin/project/template/lists">
		<button class="btn btn-sm blue-hoki" id="in_template_param_add_top"
			style="float: right;">
			添加入参模版<i class="fa fa-plus"></i>
		</button>
	</sl:permission>
	<sl:permission url="/admin/param/saveParams">
		<button class="btn btn-sm blue-hoki" id="in_params_add_top"
			style="float: right;">
			批量添加入参<i class="fa fa-plus"></i>
		</button>
	</sl:permission>
	<sl:permission url="/admin/param/submit">
		<button class="btn btn-sm blue-hoki" id="input_param_add_top"
			style="float: right;">
			添加输入参数<i class="fa fa-plus"></i>
		</button>
	</sl:permission>
</h3>
<div class="row" id="input_grid_tree_row">
	<div class="col-md-12">
		<table id="inputGridTree"
			class="table table-striped table-bordered table-hover"></table>
	</div>
</div>

<h3 class="form-section">
	响应参数
	<sl:permission url="/admin/project/template/lists">
		<button class="btn btn-sm blue-hoki" id="out_template_param_add_top"
			style="float: right;">
			添加出参模版<i class="fa fa-plus"></i>
		</button>
	</sl:permission>
	<sl:permission url="/admin/param/saveParams">
		<button class="btn btn-sm blue-hoki" id="out_params_add_top"
			style="float: right;">
			批量添加出参<i class="fa fa-plus"></i>
		</button>
	</sl:permission>
	<sl:permission url="/admin/param/submit">
		<button class="btn btn-sm blue-hoki" id="output_param_add_top"
			style="float: right;">
			添加输出参数<i class="fa fa-plus"></i>
		</button>
	</sl:permission>
</h3>
<div class="row" id="output_grid_tree_row">
	<div class="col-md-12">
		<table id="outputGridTree"
			class="table table-striped table-bordered table-hover"></table>
	</div>
</div>



<div id="entity_modal_layer" class="modal fade  in" tabindex="-1"
	role="dialog" aria-hidden="false">
	<div class="modal-dialog  ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">参数详情</h4>
			</div>

			<div class="modal-body">
				<form target="" method="POST" action=""
					enctype="application/x-www-form-urlencoded" id="entity_modal_form"
					class="form-horizontal " novalidate="novalidate">
					<input name="interfaceId" type="hidden" value=""><input
						name="id" type="hidden" value=""> <input name="parentId"
						type="hidden" value=""> <input name="type" type="hidden"
						value=""> <input name="ordered" type="hidden" value="">
					<div class="form-body " id="">
						<div class="row ">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">参数名称<span
										class="required">*</span></label>
									<div class="col-md-6">
										<input type="text" class="form-control" id="param-name" name="name"
											value="" required="required">
									</div>
									<sl:permission url="/admin/param/listall">
									<div  class="col-md-2">
										<a class="btn btn-sm green" id="seach-param">
											搜索
										</a>
									</div>
									</sl:permission>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">参数中文名称<span
										class="required">*</span></label>
									<div class="col-md-6">
										<input type="text" class="form-control" id="param-name-desc" name="nameDesc"
											value="" required="required">
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
										<input name="dataType" type="hidden" required="required"
											class="form-control select2me-data-type" id="param-data-type"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="row ">
								<div class="col-md-12 ">
									<div class="form-group">
										<label class="col-md-4 control-label">是否可见<span
											class="required">*</span></label>
										<div class="col-md-6">
											<select class="form-control" name="visible"
												required="required">
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
										<input type="text" class="form-control" id=""
											name="defaultValue" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-md-12 ">
								<div class="form-group">
									<label class="col-md-4 control-label">描述</label>
									<div class="col-md-6">
										<textarea rows="5" class="form-control" id=""
											name="description" value=""></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn green submit"
					style="display: none;">确认</button>
				<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
			</div>
		</div>
	</div>
</div>
<%@ include file="add-template.jsp"%>
<%@ include file="add-param.jsp"%>
<%@ include file="add-params-form.jsp"%>
