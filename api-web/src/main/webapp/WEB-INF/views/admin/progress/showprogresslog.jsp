<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div id="progress_log_layer" class="modal fade  in" tabindex="-1" role="dialog" aria-hidden="false">
	<div class="modal-dialog modal-lg ">
  		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">进度管理日志查看</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped table-bordered table-hover dataTable no-footer" id="log-datatable-list"></table>
			</div>
		</div>
	</div>
</div>