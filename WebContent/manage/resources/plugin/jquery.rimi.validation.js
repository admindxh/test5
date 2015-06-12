/**
* 功能: 表单输入验证
* 名称:jQuery验证 v0.21(优化版)
* 最后修改日期: 2014-11-4
* 作者：weiye
* 使用方式：$.VLDTOR.子对象的名称(需要验证的字符串) 
* 示范：
* 		var userInput = document.getElementById('userName').value();
* 		$.VLDTOR.InclWSpace(userInput);	// 返回一个布尔值
**/
(function ($) {
    $.VLDTOR = {};
    // 判断两输入框值是否相等
    $.VLDTOR.ValEuq = function (oneId, twoId) {
        if ($("#" + oneId).val() == $("#" + twoId).val()) {
            return true;
        }
        else {
            return false;
        }
    };
	// 包含一个或者多个空格
    $.VLDTOR.IncWSpace = function (strParam) {
        var thisRegExp = /\s+/;
        return thisRegExp.test(strParam);
    };
    // 中文,英文
    $.VLDTOR.IsEnCn = function (strParam) {
        var thisRegExp = /^([\u4e00-\u9fa5]|[a-zA-Z])+$/;
        return thisRegExp.test(strParam);
    };
	// 英文、数字
    $.VLDTOR.IsEnNum = function (strParam) {
        var thisRegExp = /^[a-zA-Z0-9]+$/;
        return thisRegExp.test(strParam);
    };
    // 手机号验证 或者
    $.VLDTOR.IsPhone = function (strParam) {
    	var sj = /^(13|15|18)\d{1}[-]?\d{4}[-]?\d{4}$/;
    	var sjresutl  = sj.test(strParam);
    	var dh = /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/;
    	var dhresutl  = dh.test(strParam);
    	return dhresutl;
    };
    // 中文、数字
    $.VLDTOR.IsCnNum = function (strParam) {
        var thisRegExp = /^([\u4e00-\u9fa5]|[0-9])+$/;
        return thisRegExp.test(strParam);
    };
    // 中文、英文、数字
    $.VLDTOR.IsEnCnNum = function (strParam) {
        var thisRegExp = /^([\u4e00-\u9fa5]|[a-zA-Z0-9])+$/; 
        return thisRegExp.test(strParam);
    };
    // 中文、英文、数字和逗号
    $.VLDTOR.IsEnCnNum_comma = function (strParam) {
        var thisRegExp = /^([\u4e00-\u9fa5]|[a-zA-Z0-9\，\,])+$/; 
        return thisRegExp.test(strParam);
    };
	// 中文、英文、数字、连接线、下划线和“·”
    $.VLDTOR.IsEnCnNumLnDot = function (strParam) {
        var thisRegExp = /^([\u4e00-\u9fa5]|[a-zA-Z0-9\-\_\·])+$/;
        return thisRegExp.test(strParam);
    };
	// 标题、文章（可以包含常用的文章符号）
    $.VLDTOR.IsArticle = function (strParam) {
        var thisRegExp = /^(\S+)((\S|\s)*)$/;
        return thisRegExp.test(strParam);
    };
	// png图片的路径
	$.VLDTOR.IsPngImg = function (strParam) {
		var thisRegExp = /[^\s]+\.(png)/i;
        return thisRegExp.test(strParam);
    };
	// 英文、数字、连接符、下划线和点
	$.VLDTOR.IsEnNumLnDot = function (strParam) {
        var thisRegExp = /^[a-zA-Z0-9\-\_\.]+$/;
        return thisRegExp.test(strParam);
    };
	// 日期
	$.VLDTOR.IsDate = function (strParam) {
        var thisRegExp = /^20{1}\d{2}(\-|\/)?(0?[1-9]|10|11|12)(\-|\/)?([1-2]?[0-9]|0[1-9]|30|31)$/;
        return thisRegExp.test(strParam);
    };
    // URL
    $.VLDTOR.IsWebUrl = function (strParam) {
        //var thisRegExp = /^(((ht|f)tp(s?))\:\/\/)?(www\.|[a-zA-Z]\.)?[a-zA-Z0-9\-\.\//\///]+\.(com|edu|gov|mil|net|org|biz|info|name|museum|us|ca|uk|cn)(\:[0-9]+)*/i;
        //var thisRegExp = /^(((ht|f)tp(s?))\:\/\/){1}/;
		var thisRegExp = /^[^\s]*$/;
        return thisRegExp.test(strParam);
    };
    // HTTP
    $.VLDTOR.IsHTTP = function (strParam) {
        var thisRegExp = /^(((ht|f)tp(s?))\:\/\/){1}/;
        return thisRegExp.test(strParam);
    };
	// 手机App下载地址
    $.VLDTOR.IsAppUrl = function (strParam) {
        var thisRegExp = /^(http|ftp|https){1}(\:\/\/){1}[a-zA-Z0-9\-\_\.\/\:]+(\.apk|\.ipa|\.xap|\.appx){1}$/i;
        return thisRegExp.test(strParam);
    };
    // 15位工商注册号
    $.VLDTOR.ICRegiID = function (strParam) {
        var thisRegExp = /^\d{15}$/; // 15位数字
        return thisRegExp.test(strParam);
    };
    // 至少两个字的中文名，中间可有·（用于外国中文名）
    $.VLDTOR.IsCnName = function (strParam) {
        var thisRegExp = /^([\u4e00-\u9fa5])+[·]?([\u4e00-\u9fa5])+$/;
        return thisRegExp.test(strParam);
    };
    // 13,15,18开头的11位手机号码(可包含“-”，如：130-0000-0000)
    $.VLDTOR.IsMobile = function (strParam) {
		var thisRegExp = /^(13|15|18)\d{1}[-]?\d{4}[-]?\d{4}$/;
        return thisRegExp.test(strParam);
    };
    // 传真号码验证
    $.VLDTOR.IsFax = function (strParam) {
        var thisRegExp = /^((\+?[0-9]{2,4}((\-)|[ ])?[0-9]{3,4}((\-)|[ ])?)|([0-9]{3,4}((\-)|[ ])?))?([0-9]{7,8})$/;
        return thisRegExp.test(strParam);
    };
    // 邮箱验证
    $.VLDTOR.IsEmail = function (strParam) {
        var thisRegExp = /^([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        return thisRegExp.test(strParam);
    };
    //只包含字母、数字、特殊字符（用于密码设置）
    $.VLDTOR.IsPWD = function (strParam) {
        var thisRegExp = /^[^\s\u4e00-\u9fa5]+$/; // 不包含空格,即只包含字母、数字、特殊字符
        return thisRegExp.test(strParam);
    };
    // 数字
    $.VLDTOR.IsNum = function (strParam) {
        var thisRegExp = /^\d+$/; 
        return thisRegExp.test(strParam);
    };
    // 浮点数
    $.VLDTOR.IsFloatNum = function (strParam) {
        var thisRegExp = /^\d+((\.\d+)?)$/; 
        return thisRegExp.test(strParam);
    };
    // 价格
    $.VLDTOR.IsPrice = function (strParam) {
        var thisRegExp = /^\d+\.?\d?\d?$/;
        return thisRegExp.test(strParam);
    };
    // 字符
    $.VLDTOR.IsChar = function (strParam) {
        var thisRegExp = /^[A-Za-z]+$/; 
        return thisRegExp.test(strParam);
    };
    /**
     * @use 评分,范围在0.0-10.0之内
     * @return {boolean}
     */
    $.VLDTOR.IsGrade = function (strParam) {
        var thisRegExp = /^(\d||10)((\.\d))$/;
        if(thisRegExp.test(strParam)){
            return strParam <= 10.0;
        }
    };
})(jQuery);
