define(function(require, exports, module) {
	
jQuery.validator.addMethod("isZipCode", function(value, element) {    
  var zip = /^[0-9]{6}$/;    
  return this.optional(element) || (zip.test(value));    
}, "请正确填写您的邮政编码！");        


// 手机号码验证    
jQuery.validator.addMethod("isMobile", function(value, element) {    
  var length = value.length;    
  return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/.test(value));    
}, "请正确填写您的手机号码！");

// 电话号码验证    
jQuery.validator.addMethod("isPhone", function(value, element) {    
  var tel = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;  
  var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
  return this.optional(element) || (tel.test(value));    
}, "请正确填写您的电话号码！")

// 用户名字符验证    
jQuery.validator.addMethod("userName", function(value, element) {    
  return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);    
}, "用户名只能包括中文字、英文字母、数字和下划线！");   

// 联系电话(手机/电话皆可)验证   
jQuery.validator.addMethod("isTel", function(value,element) {   
    var length = value.length;  
    var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/;   
    var tel = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;    
    return this.optional(element) || (tel.test(value) || mobile.test(value));   
}, "请正确填写您的联系电话！");  

// IP地址验证   
jQuery.validator.addMethod("ip", function(value, element) {    
  return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
}, "请填写正确的IP地址！");

//用于判断是否是给定数字的整数倍
jQuery.validator.addMethod("times", function(value, element,param) {    
  return this.optional(element) || Number(value)%Number(param) == 0;    
}, jQuery.validator.format("必须是{0}的整数倍！")); 

//用于判断账户余额
jQuery.validator.addMethod("accountMax",function(value,element,param){
	return Number(value) <= Number(param);
},jQuery.validator.format("账户余额不足！"));

//用于当前用户可投额度
jQuery.validator.addMethod("tenderMax",function(value,element,param){
	return Number(value) <= Number(param);
},jQuery.validator.format("不能大于当前可投额度！"));

//用于判断是否是给定数字的整数倍
jQuery.validator.addMethod("bigtimes", function(value, element,param) {    
  return this.optional(element) || Number(value) * 10000 % Number(param) == 0;    
}, jQuery.validator.format("必须是{0}的整数倍！")); 

//用于判断一个输入框的值小于另外一个输入框的
jQuery.validator.addMethod("smaller", function(value, element, param) {
	var target = $( param );
//	if ( this.settings.onfocusout ) {
//		target.unbind( ".validate-equalTo" ).bind( "blur.validate-equalTo", function() {
//			$( element ).valid();
//		});
//	}
	return this.optional(element) || Number(value) <= Number(target.val());
}, jQuery.validator.format("请正确填写"));

jQuery.validator.addMethod("isNoChinese", function(value, element) {
	var reg = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
	return this.optional(element) || (!reg.test(value) && /^[\u0391-\uFFE5\w]+$/.test(value)) ;
}, "用户名必须由字母、数字或“_”组成");

//用于判断昵称是字母、数字、特殊字符或“_”组成
jQuery.validator.addMethod("isNickName", function(value, element) {
//	var reg = /^[\w][\w@.]+$/;
	var reg = /^[0-9a-zA-Z_.@]+$/;
	return this.optional(element) || reg.test(value);
}, "用户名必须由字母、数字或特殊符号(@_.)组成");

//用于判断是否含有空格
jQuery.validator.addMethod("isNoSpace", function(value, element) {
	var reg = /^[^ ]+$/;
	return this.optional(element) || reg.test(value);
}, "密码中不能含有任何空格");

jQuery.validator.addMethod("isValidBizUserId", function(value, element) {
	var reg = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
	return this.optional(element) || (!reg.test(value) && /^[\u0391-\uFFE5\w]+$/.test(value)) ;
}, "引荐人工号格式不正确");

//密码复杂度校验
jQuery.validator.addMethod("complexity", function(value, element) {  
	var reg = /^(?=.*\d)(?=.*[a-zA-Z])[\S]+$/;
	return this.optional(element) || reg.test(value);    
}, jQuery.validator.format("密码过于简单，至少要字母和数字的组合")); 

//金额校验
jQuery.validator.addMethod("money", function(value, element) {  
	var reg = /^[0-9]+(.[0-9]{1,2})?$/;
  return this.optional(element) || reg.test(value);    
}, jQuery.validator.format("格式有误，只允许输入两位小数")); 

jQuery.validator.addMethod("yearRate", function(value, element) {  
	var reg = /^[0-9]+(.[0-9]{1})?$/;
  return this.optional(element) || reg.test(value);    
}, jQuery.validator.format("格式有误，只允许输入1位小数")); 

jQuery.validator.addMethod("transferPrice", function(value, element,param) { 
	var account = (parseFloat(param) * 0.75).toString();
	var resultAccount;
	if(account.indexOf('.') >= 0){
		resultAccount = account.substring(0, account.indexOf('.') + 3)
	}
	
	if(parseFloat(value) < parseFloat(resultAccount)){
		return false;
	}else{
		return true;
	}
}, jQuery.validator.format("转让价不能小于剩余本金的75%")); 

jQuery.validator.addMethod("transferMaxPrice", function(value, element,param) {  
	if(parseFloat(value) > parseFloat(param)){
		return false;
	}else{
		return true;
	}
}, jQuery.validator.format("转让价不能大于剩余本金")); 

jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
	var target = $( param );
	return this.optional(element) || Number(value) != Number(target.val());
});

//身份证号码验证
jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
  return this.optional(element) || isIdCardNo(value);
}, "证件号码错误(区分大小写)"); 

//增加身份证验证
function isIdCardNo(num) {
    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
    var varArray = new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        return false;
    }
    // check and set value
    for (i = 0; i < intStrLen; i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            return false;
        } else if (i < 17) {
            varArray[i] = varArray[i] * factorArr[i];
        }
    }
    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6, 14);
        if (isDate8(date8) == false) {
            return false;
        }
        // calculate the sum of the products
        for (i = 0; i < 17; i++) {
            lngProduct = lngProduct + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = parityBit[lngProduct % 11];
        // check last digit
        if (varArray[17] != intCheckDigit) {
            return false;
        }
    }
    else {        //length is 15
        //check date
        var date6 = idNumber.substring(6, 12);
        if (isDate6(date6) == false) {
            return false;
        }
    }
    return true;
}
function isDate6(sDate) {
    if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    if (year < 1700 || year > 2500) return false
    if (month < 1 || month > 12) return false
    return true
}
/**
* 判断是否为“YYYYMMDD”式的时期
*
*/
function isDate8(sDate) {
    if (!/^[0-9]{8}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    day = sDate.substring(6, 8);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if (year < 1700 || year > 2500) return false
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false
    if (day < 1 || day > iaMonthDays[month - 1]) return false
    return true
}

})