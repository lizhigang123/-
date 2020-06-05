<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div id="params_modal_layer" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<form id="entity_modal_params_form" class="form-horizontal">
				    <input name="interfaceId" id="interfaceId" type="hidden"/>
				    <input name="parentId" id="parentId" type="hidden"/>
				    <input name="type" id="type" type="hidden"/>
 					<input name="paramIds" id="paramIds" type="text" class="multiSelect" style="width:550px"/>
				</form>	
			</div>
			<div class="modal-footer">
				<button type="button" class="btn green submit">确认</button>
				<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
			</div>
		</div>
	</div>
</div>

