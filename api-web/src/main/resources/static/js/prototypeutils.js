/**
 * Date 原型扩展
 */

// 日期格式化 默认为 yyyy-MM-dd
Date.prototype.format = function(fmt) {
	if (arguments.length == 0) {
		fmt = "yyyy-MM-dd";
	}
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

// 字符串转时间
function convertDate(val) {
	if (typeof val == "undefined")
		return new Date();
	if (typeof val == "date")
		return val;
	//var date = new Date(Date.parse(val));
	//if (isNaN(date)) {
	if(isNaN(val)){
		var arys = val.replace(/:/g, "-").replace(" ", "-").replace(".", "-").split('-');
		switch (arys.length) {
		case 7:
			date = new Date(arys[0], --arys[1], arys[2], arys[3], arys[4], arys[5], arys[6]);
			break;
		case 6:
			date = new Date(arys[0], --arys[1], arys[2], arys[3], arys[4], arys[5]);
			break;
		default:
			date = new Date(arys[0], --arys[1], arys[2]);
			break;
		}
	}
	return date;
}

// 扩展日期对象,判断一个时间是否在另一个时间之后
Date.prototype.after = function(date) {
	return this.getTime() - convertDate(date).getTime() > 0 ? true : false;
}

// 扩展日期对象,判断一个时间是否在另一个时间之前
Date.prototype.before = function(date) {
	return this.getTime() - convertDate(date).getTime() < 0 ? true : false;
}

Date.prototype.equal = function(date) {
	return this.getTime() - convertDate(date).getTime() == 0 ? true : false;
}

/**
 * String 原型扩展
 */
/*
 * ***************************************** 字符串函数扩充
 * *****************************************
 */
String.prototype.endWith = function(str) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substring(this.length - str.length) == str)
		return true;
	else
		return false;
	return true;
}
String.prototype.startWith = function(str) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substr(0, str.length) == str)
		return true;
	else
		return false;
	return true;
}
/*
 * =========================================== //去除左边的空格
 * ===========================================
 * 
 */
String.prototype.lTrim = function() {
	return this.replace(/(^\s*)/g, "");
}

/*
 * =========================================== //去除右边的空格
 * ===========================================
 */
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
}

/*
 * =========================================== //去除前后空格
 * ===========================================
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 剔除掉所有的空格
 */
String.prototype.trimAll = function(){
	return this.replace(/\s/g,"");
}

/*
 * =========================================== //得到左边的字符串
 * ===========================================
 */
String.prototype.left = function(len) {

	if (isNaN(len) || len == null) {
		len = this.length;
	} else {
		if (parseInt(len) < 0 || parseInt(len) > this.length) {
			len = this.length;
		}
	}

	return this.substr(0, len);
}

/*
 * =========================================== //得到右边的字符串
 * ===========================================
 */
String.prototype.right = function(len) {

	if (isNaN(len) || len == null) {
		len = this.length;
	} else {
		if (parseInt(len) < 0 || parseInt(len) > this.length) {
			len = this.length;
		}
	}

	return this.substring(this.length - len, this.length);
}

/*
 * =========================================== //得到中间的字符串,注意从0开始
 * ===========================================
 */
String.prototype.mid = function(start, len) {
	return this.substr(start, len);
}

/*
 * =========================================== //在字符串里查找另一字符串:位置从0开始
 * ===========================================
 */
String.prototype.InStr = function(str) {

	if (str == null) {
		str = "";
	}

	return this.indexOf(str);
}

/*
 * =========================================== //在字符串里反向查找另一字符串:位置0开始
 * ===========================================
 */
String.prototype.inStrRev = function(str) {

	if (str == null) {
		str = "";
	}

	return this.lastIndexOf(str);
}

/*
 * =========================================== //计算字符串打印长度
 * ===========================================
 */
String.prototype.lengthW = function() {
	return this.replace(/[^\x00-\xff]/g, "**").length;
}

/*
 * =========================================== //是否是正确的IP地址
 * ===========================================
 */
String.prototype.isIP = function() {

	var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;

	if (reSpaceCheck.test(this)) {
		this.match(reSpaceCheck);
		if (RegExp.$1 <= 255 && RegExp.$1 >= 0 && RegExp.$2 <= 255 && RegExp.$2 >= 0 && RegExp.$3 <= 255 && RegExp.$3 >= 0 && RegExp.$4 <= 255 && RegExp.$4 >= 0) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}

}

/*
 * =========================================== //是否是正确的长日期
 * ===========================================
 */
String.prototype.isLongDate = function() {
	var r = this.replace(/(^\s*)|(\s*$)/g, "").match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
	if (r == null) {
		return false;
	}
	var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6], r[7]);
	return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4] && d.getHours() == r[5] && d.getMinutes() == r[6] && d.getSeconds() == r[7]);

}

/*
 * =========================================== //是否是正确的短日期
 * ===========================================
 */
String.prototype.isShortDate = function() {
	var r = this.replace(/(^\s*)|(\s*$)/g, "").match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if (r == null) {
		return false;
	}
	var d = new Date(r[1], r[3] - 1, r[4]);
	return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
}

/*
 * =========================================== //是否是正确的日期
 * ===========================================
 */
String.prototype.isDate = function() {
	return this.isLongDate() || this.isShortDate();
}

/*
 * =========================================== //是否是手机
 * ===========================================
 */
String.prototype.isMobile = function() {
	return /^(13|15|18|14|17)\d{9}$/i.test(this);
}

/*
 * =========================================== //是否是邮件
 * ===========================================
 */
String.prototype.isEmail = function() {
	return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(this);
}

/*
 * =========================================== //是否是邮编(中国)
 * ===========================================
 */

String.prototype.isZipCode = function() {
	return /^[\\d]{6}$/.test(this);
}

/*
 * =========================================== //是否是有汉字
 * ===========================================
 */
String.prototype.existChinese = function() {
	// [\u4E00-\u9FA5]為漢字﹐[\uFE30-\uFFA0]為全角符號
	return /^[\x00-\xff]*$/.test(this);
}

/*
 * =========================================== //是否是合法的文件名/目录名
 * ===========================================
 */
String.prototype.isFileName = function() {
	return !/[\\\/\*\?\|:"<>]/g.test(this);
}

/*
 * =========================================== //是否是有效链接
 * ===========================================
 */
String.prototype.isUrl = function() {
	return /^http[s]?:\/\/([\w-]+\.)+[\w-]+([\w-./?%&=]*)?$/i.test(this);
}

/*
 * =========================================== //是否是有效的电话号码(中国)
 * ===========================================
 */
String.prototype.isPhoneCall = function() {
	return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/.test(this);
}

/*
 * =========================================== //是否是数字
 * ===========================================
 */
String.prototype.isNumeric = function(flag) {
	// 验证是否是数字
	if (isNaN(this)) {

		return false;
	}

	switch (flag) {

	case null: // 数字
	case "":
		return true;
	case "+": // 正数
		return /(^\+?|^\d?)\d*\.?\d+$/.test(this);
	case "-": // 负数
		return /^-\d*\.?\d+$/.test(this);
	case "i": // 整数
		return /(^-?|^\+?|\d)\d+$/.test(this);
	case "+i": // 正整数
		return /(^\d+$)|(^\+?\d+$)/.test(this);
	case "-i": // 负整数
		return /^[-]\d+$/.test(this);
	case "f": // 浮点数
		return /(^-?|^\+?|^\d?)\d*\.\d+$/.test(this);
	case "+f": // 正浮点数
		return /(^\+?|^\d?)\d*\.\d+$/.test(this);
	case "-f": // 负浮点数
		return /^[-]\d*\.\d$/.test(this);
	default: // 缺省
		return true;
	}
}

/*
 * =========================================== //是否是颜色(#FFFFFF形式)
 * ===========================================
 */
String.prototype.isColor = function() {
	var temp = this;
	if (temp == "")
		return true;
	if (temp.length != 7)
		return false;
	return (temp.search(/\#[a-fA-F0-9]{6}/) != -1);
}

/*
 * =========================================== //转换成全角
 * ===========================================
 */
String.prototype.toCase = function() {
	var tmp = "";
	for (var i = 0; i < this.length; i++) {
		if (this.charCodeAt(i) > 0 && this.charCodeAt(i) < 255) {
			tmp += String.fromCharCode(this.charCodeAt(i) + 65248);
		} else {
			tmp += String.fromCharCode(this.charCodeAt(i));
		}
	}
	return tmp
}

/*
 * =========================================== //对字符串进行Html编码
 * ===========================================
 */
String.prototype.toHtmlEncode = function() {
	var str = this;

	str = str.replace(/&/g, "&amp;");
	str = str.replace(/</g, "&lt;");
	str = str.replace(/>/g, "&gt;");
	str = str.replace(/\'/g, "&apos;");
	str = str.replace(/\"/g, "&quot;");
	str = str.replace(/\n/g, "<br>");
	str = str.replace(/\ /g, "&nbsp;");
	str = str.replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;");

	return str;
}

/*
 * =========================================== //转换成日期
 * ===========================================
 */
String.prototype.toDate = function() {
	try {
		return new Date(this.replace(/-/g, "\/"));
	} catch (e) {
		return null;
	}
}
/*
 * =======精确到 小数点后几位 直接截取(默认2位)======
 * pointLength 截取小数位数(default 2)
 * 由于 js 的 加减乘除都会 有失去精度的 bug 所以最好不要用加减乘除来截取 只能以string来截取
 * 
 * for example
 * {
 * 	
 * 	5313.349999.cut()			return 5313.34
 * 	5313.349999.cut(4)			return 5313.3499
 * }
 * 
 */
Number.prototype.cut = function(pointLength) {
	try {
		pointLength = pointLength&&!isNaN(pointLength)?pointLength:2;
		if(pointLength==0){
			return Math.floor(this);
		}else{
			var temp = this+"";
			var indexOf = temp.indexOf(".");
			if(indexOf != -1 && ( (temp.length-1) > (indexOf+pointLength) )){
				temp = temp.substring(0,indexOf+pointLength+1);
			}
			return parseFloat(temp);
		}
	} catch (e) {
		return this;
	}
}
/**
 * 格式化金额 三位一逗号
 */
Number.prototype.formatMoney = function(pointLength) {
		var s = this,n = pointLength;
	   n = n > 0 && n <= 20 ? n : 2;   
	   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
	   var l = s.split(".")[0].split("").reverse(),   
	   r = s.split(".")[1];   
	   t = "";   
	   for(i = 0; i < l.length; i ++ )   
	   {   
	      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
	   }   
	   return t.split("").reverse().join("") + "." + r;   
}

/**
 * 费率验证0-100之间小数点后保留4位
 */
String.prototype.slRule = function() {
	var exp =  /^(\d{1,2}(\.\d{1,4})?|100|100.0|100.00|100.000|100.0000)$/;　
	return exp.test(this);
}

/**
 * 费率验证0-100之间小数点后保留1位
 */
String.prototype.slRuleTwo = function() {
	var exp =  /^(\d{1,2}(\.\d{1})?|100|100.0)$/;　
	return exp.test(this);
}

/**
 * 判断是否大于零
 */
String.prototype.isZero = function() {
	return parseFloat(this).sub(parseFloat("0.00"))>0 ? true : false;
}

/**
 * 卖单手续费天数区间验证
 * 非负整数（正整数 + 0）
 */
String.prototype.slDaySection = function() {
	var exp =  /^\+?(0|[1-9][0-9]*)$/;　
	return exp.test(this);
}

/**
 * 验证金额格式，包含0.0且大于0
 */
String.prototype.slAmount = function() {
	var exp = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	return exp.test(this);
}

/**
* 
*整数位最多是9位
*小数位最多2位数
 */
String.prototype.slRuleAmount = function() {
	var exp =  /^(\d{1,9}(\.\d{1,2})?|)$/;　
	return exp.test(this);
}

/**
 * 
 * 必须数字或字母开头，可以有下划线
 * 
*/
 
String.prototype.slRuleProjectNo = function() {
	var exp = /^[a-zA-Z0-9][a-zA-Z0-9_]*$/;
	return exp.test(this);
}

/**
 * 
 * 不能有特殊符号
 * 
*/
String.prototype.slRuleName = function() {
	var exp = /^(?=[0-9a-zA-Z\u4e00-\u9fa5]+$)/;
	return exp.test(this);
}


/**
 * 投资金额、账户管理费、撮合规则、最大金额
 */
var maxAmt = "9999999999.00";

/**
 * 验证金额大小，最大9999999999.00
 */
String.prototype.slMaxAmount = function() {
	return parseFloat(this) > parseFloat(maxAmt) ? false : true;
}

/**
 * 获取字符串的字节数 一个汉字作为2个字符处理
 * 处理方式 把每个字节转为unicode 做处理
 */
String.prototype.byteLength = function(){
	var sum = 0;
    for ( var i = 0; i < this.length; i++ ) {
        var c = this.charCodeAt(i);
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
            sum++;
        } else {
            sum += 2;
        }
    }
    return sum;
}

/**
 * 加法操作
 */
Number.prototype.add = function(number){
	return accAdd(number, this);
}

/**
 * 减法操作
 */
Number.prototype.sub = function(number){
	return subtr(number, this);
}

/**
 * 除法操作
 */
Number.prototype.div = function (number){ 
	return accDiv(this, number); 
}

/**
 * 乘法操作
 */
Number.prototype.mul = function (number){ 
	return accMul(number, this); 
} 
