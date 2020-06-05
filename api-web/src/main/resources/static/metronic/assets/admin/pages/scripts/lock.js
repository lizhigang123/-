var Lock = function () {

    return {
        //main function to initiate the module
        init: function () {

             $.backstretch([
		        ctx + "/static/metronic/assets/admin/pages/media/bg/1.jpg",
		        ctx + "/static/metronic/assets/admin/pages/media/bg/2.jpg",
		        ctx + "/static/metronic/assets/admin/pages/media/bg/3.jpg",
		        ctx + "/static/metronic/assets/admin/pages/media/bg/4.jpg"
		        ], {
		          fade: 1000,
		          duration: 8000
		      });
             
         	//表单提交
         	$("#lock_login").validate({
                 errorElement: 'span', //default input error message container
                 errorClass: 'help-block help-block-error', // default input error message class
                 focusInvalid: false, // do not focus the last invalid input	
                 ignore:"",
         		rules : {
         			password : {
         				required : true,
         				minlength : 6
         			}
         		},
                 messages: {
                     password: {
                         required: "密码不能为空.",
                         minlength : "至少6位密码"
                     }
                 },
                 invalidHandler: function (event, validator) { //display error alert on form submit              
                     Metronic.scrollTo($("#lock_login").find(".has-error"), -200);
                 },

                 highlight: function (element) { // hightlight error inputs
                     $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                 },

                 unhighlight: function (element) { // revert the change done by hightlight
                     $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                 },

                 success: function (label) {
                     label.closest('.form-group').removeClass('has-error'); // set success class to the control group
                 },
                 submitHandler: function (form) {
                 	form.submit();
                 }
         	});	             
        }

    };

}();