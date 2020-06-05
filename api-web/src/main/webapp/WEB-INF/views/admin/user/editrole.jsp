<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<div class="modal-dialog modal-lg">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true"></button>
			<h4 class="modal-title">角色分配</h4>
		</div>
		<form id="user_update_role_form" class="form-horizontal"
			onsubmit="return false;">
			<input type="hidden" name="userId" value="${userId }" />
			<div class="modal-body">
				<div class="form-body">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">业务操作角色：</label>
							<div class="col-md-8">
								<div class="checkbox-list operate_checkbox_wrapper">
									<c:forEach items="${roleList }" var="item">
										<label class="checkbox-inline"> <input name="roleIds"
											type="checkbox" value="${item.id }">${item.name } <input
											name="checkboxs" type="hidden"
											value="${item.id }_${item.checked }">
										</label>
									</c:forEach>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" name="button_submit" class="btn green">确定</button>
				<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
			</div>
		</form>
	</div>
	<!-- /.modal-content -->
</div>
<script type="text/javascript">
	(function() {

		$("input[name='checkboxs']").each(function(index, item) {
			var checkbox = this.value.split('_');
			var roleId = checkbox[0];
			var checked = checkbox[1];
			$("input[name='roleIds']").each(function(index, item) {
				if (this.value == roleId) {
					if (checked == 'true') {
						this.checked = true;
					}
				}
			});

		});
		$("#user_update_role_form").submit(function() {
			var roleIds = "";
			$("input[name='roleIds']").each(function(index, item) {
				if (this.checked == true) {
					roleIds += this.value + ",";
				}
			});
			if (roleIds) {
				$.post("/admin/user/saveRoleIds", $("#user_update_role_form").serialize(), function(response) {
					if (response.code == "000000") {
						slalert(response.message);
						$("#user_role_modal").one("hidden.bs.modal", function() {
							grid.refresh();
						}).modal("hide");
					}
				});
			} else {
				slalert('请至少选择一个角色!');
			}
		});

	})()
</script>