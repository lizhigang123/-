// 格式化日期
function formatDate(value) {
	if (value == null) {
		return "";
	}
	var date = new Date(value);
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
}

// 格式化时间
function formatTime(value) {
	if (value == null) {
		return "";
	}
	var date = new Date(value);
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
}

//获取当前系统时间
function getCurrentSystemDate() {
	var date = new Date();
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
}

//获取当前系统时间的天数
function getCurrentSystemDay() {
	var date = new Date();
	return date.getDate();
}

// 格式化时间
function formatFullTime(value) {
	if (value == null) {
		return "";
	}
	var day = new Date(value);

	var Year = 0;
	var Month = 0;
	var Day = 0;
	var CurrentDate = "";
	// 初始化时间
	Year = day.getFullYear();
	Month = day.getMonth() + 1;
	Day = day.getDate();
	Hour = day.getHours();
	Minute = day.getMinutes();
	Second = day.getSeconds();
	CurrentDate += Year + "-";
	var CurrentTime = " ";
	if (Month >= 10) {
		CurrentDate += Month + "-";
	} else {
		CurrentDate += "0" + Month + "-";
	}
	if (Day >= 10) {
		CurrentDate += Day;
	} else {
		CurrentDate += "0" + Day;
	}
	if (Hour >= 10) {
		CurrentTime += Hour + ":";
	} else {
		CurrentTime += "0" + Hour + ":";
	}
	if (Minute >= 10) {
		CurrentTime += Minute + ":";
	} else {
		CurrentTime += "0" + Minute + ":";
	}
	if (Second >= 10) {
		CurrentTime += Second;
	} else {
		CurrentTime += "0" + Second;
	}

	return CurrentDate + CurrentTime;
}

// 格式化客户类型
function formatterCustomerType(value, type, row) {
	if (value == 1)
		return "储备客户";
	else if (value == 2)
		return "客户";
	else if (value == 3)
		return "老客户";
	else
		return "";
}

// 格式化银行卡号 begin
function formatBankNo(BankNo) {
	if (BankNo.value == "")
		return;
	var account = new String(BankNo.value);
	account = account.substring(0, 22); /* 帐号的总数, 包括空格在内 */
	if (account.match(".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null) {
		/* 对照格式 */
		if (account.match(".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null) {
			var accountNumeric = accountChar = "", i;
			for (i = 0; i < account.length; i++) {
				accountChar = account.substr(i, 1);
				if (!isNaN(accountChar) && (accountChar != " "))
					accountNumeric = accountNumeric + accountChar;
			}
			account = "";
			for (i = 0; i < accountNumeric.length; i++) { /* 可将以下空格改为-,效果也不错 */
				if (i == 4)
					account = account + " "; /* 帐号第四位数后加空格 */
				if (i == 8)
					account = account + " "; /* 帐号第八位数后加空格 */
				if (i == 12)
					account = account + " ";/* 帐号第十二位后数后加空格 */
				account = account + accountNumeric.substr(i, 1)
			}
		}
	} else {
		account = " " + account.substring(1, 5) + " " + account.substring(6, 10) + " " + account.substring(14, 18) + "-" + account.substring(18, 25);
	}
	if (account != BankNo.value)
		BankNo.value = account;
}
// 格式化银行卡号 end

// 金额大写转换 begin
function atoc(numberValue) {
	var numberValue = new String(Math.round(numberValue * 100)); // 数字金额
	var chineseValue = ""; // 转换后的汉字金额
	var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
	var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
	var len = numberValue.length; // numberValue 的字符串长度
	var Ch1; // 数字的汉语读法
	var Ch2; // 数字位的汉字读法
	var nZero = 0; // 用来计算连续的零值的个数
	var String3; // 指定位置的数值
	if (len > 15) {
		alert("超出计算范围");
		return "";
	}
	if (numberValue == 0) {
		chineseValue = "零元整";
		return chineseValue;
	}
	String2 = String2.substr(String2.length - len, len); // 取出对应位数的STRING2的值
	for (var i = 0; i < len; i++) {
		String3 = parseInt(numberValue.substr(i, 1), 10); // 取出需转换的某一位的值
		if (i != (len - 3) && i != (len - 7) && i != (len - 11) && i != (len - 15)) {
			if (String3 == 0) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			}
		} else { // 该位是万亿，亿，万，元位等关键位
			if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 != 0 && nZero == 0) {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 == 0 && nZero >= 3) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else {
				Ch1 = "";
				Ch2 = String2.substr(i, 1);
				nZero = nZero + 1;
			}
			if (i == (len - 11) || i == (len - 3)) { // 如果该位是亿位或元位，则必须写上
				Ch2 = String2.substr(i, 1);
			}
		}
		chineseValue = chineseValue + Ch1 + Ch2;
	}
	if (String3 == 0) { // 最后一位（分）为0时，加上“整”
		chineseValue = chineseValue + "整";
	}
	return chineseValue;
}
//金额大写转换 end
//金额按千位逗号分割 ,带小数点后两位四舍五入
function formatMoney(s) {
	var n =2;
    n = n > 0 && n <= 20 ? n : 2;   
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];   
    t = "";   
    for (i = 0; i < l.length; i++) {   
    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
    }   
    return t.split("").reverse().join("") + "." + r;   
}
//截取小数，小数点后保留两位
function subStringMoney(money) {
	var m = parseFloat((money + "").replace(/[^\d\.-]/g, "")) + "";
	if (isNaN(m)) {
		return "";
	}
	var index = m.indexOf(".");
	if (index == -1) {
		m += ".00";
	}
	index  = m.indexOf(".");
	return m.substring(0, index+3);
}

//验证为空
function isNull(val) {
	if (typeof(val) == "undefined" || $.trim(val) == "" || val == null ||  $.trim(val) == "null") {
		return false;
	}
	return true;
}

/**
 * 加法操作
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2))
    return (arg1 * m + arg2 * m) / m
}

/**
 * 减法函数  
 * 
 * @param arg1
 * @param arg2
 * @returns
 */
function subtr(arg1, arg2) {  
    var r1, r2, m, n;  
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }  
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }  
    m = Math.pow(10, Math.max(r1, r2));  
    n = (r1 >= r2) ? r1 : r2;  
    return ((arg2 * m - arg1 * m) / m).toFixed(n);  
}  

/**
 * 除法操作
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {t1 = arg1.toString().split(".")[1].length} catch (e) {}
	try {t2 = arg2.toString().split(".")[1].length} catch (e) {}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""))
		r2 = Number(arg2.toString().replace(".", ""))
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

/**
 * 乘法操作
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {m += s1.split(".")[1].length} catch (e) {}
	try {m += s2.split(".")[1].length} catch (e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}

/**
 * 格式化上传文件名称，长度过长的进行截取
 * 
 * @param filename
 */
function formatFileName(filename,length){
	var _name = filename;
	if(!length){
		length = 60;
	}
	if(filename){
		var filelength = filename.length;
		if(filelength > length){
			var lastIndex = filename.lastIndexOf("."); 
			if(lastIndex != -1){ 
				_name = filename.substring(0,30) + "......" + filename.substring(filelength-10,filelength);
			}
		}
	}
		return _name;
}
	