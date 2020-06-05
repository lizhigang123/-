<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<input type="hidden" name="id" />
<h3 class="form-section">
	资源参数
 	<sl:permission url="/admin/resource/submit">
	<button class="btn btn-sm blue-hoki" id="input_resource_add_top"
		style="float: right;">
		添加资源参数<i class="fa fa-plus"></i>
	</button>
	</sl:permission>
</h3>
<div class="row" id="input_grid_tree_row">
	<div class="col-md-12">
		<table id="resourceGridTree"
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
				<h4 class="modal-title">资源详情</h4>
			</div>

			<div class="modal-body">
				<form target="" method="POST" action=""
					enctype="application/x-www-form-urlencoded" id="entity_modal_form"
					class="form-horizontal " novalidate="novalidate">
					<input name="id" type="hidden" value=""> 
					<input name="parentId" type="hidden" value="">
					<div class="form-body " id="">
						<div class="row ">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">资源名称<span
										class="required">*</span></label>
									<div class="col-md-6">
										<input type="text" class="form-control" id="" name="name"
											value="" required="required">
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">资源路径<span
										class="required"></span></label>
									<div class="col-md-6">
										<input type="text" class="form-control" id="" name="url"
											value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-md-4 control-label">资源描述<span
										class="required">*</span></label>
									<div class="col-md-6">
										<input type="text" class="form-control" id="" name="description"
											value="" required="required">
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