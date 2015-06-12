/**
 * 功能：数据有效性验证并给出提示
 * 日期：2014年12月16日
 * 作者：weiye
 **/

//===================================================
//				全局对象和变量定义部分
//===================================================
// 验证结果（true为通过，false（默认）为不通过）
var VLDRST = {
	/******** 个别设置 ********/
	// 浏览数
	Brows_num: false,
	// 收藏数
	Collect_num: false,
	// 商户链接
	CommerLink: false,
	// 商户省
	CommerProvi: false,
	// 商户区域
	CommerDistr: false,
	// 商户名称
	CommerName: false,
	// 详细地址（文本）
	DetailAddr: false,
	// 目的地选择
	Destination: false,
	// 景点选择
	DestScenic: false,
	// 编号
	LinkNum: false,
	// 查看数
	Lookup_num: false,
	// 被赞数
	Praise_num: false,
	// 地区选择
	Region: false,
	// 显示简介
	ShowIntro: false,
	// 显示标题
	ShowTitile: false,
	// 攻略标题
	StrategyTitle: false,
	// 攻略类型
	StrategyType: false,
	// 攻略所属板块
	TheirMudle: false,
	// 链接至
	ToLink: false,
	// 上传用户
	UploadAuthor: false,

	/******** 全局设置 ********/
	// 名称类（只能为中文和英文）
	NameType: false,
	// 数字型
	NumType: false,
	// 文本非空
	NonNull: false,

	/******** 全局二次验证传递值 ********/
	valid_post: true
}

//===================================================
//				  事件绑定及触发部分
//===================================================


//===================================================
//					方法函数定义部分
//===================================================

/** 
 * 功能：自定义下拉选择框
 * 参数：1.下拉框主容器div的标识符；2.错误信息文本内容或html内容；3.错误信息文本；4.错误提示显示位置
 **/
function valid_customSelect(ident, mesg, posi) {
	var $this = $(ident + ".select-base"),
		thisVal_substr = $.trim($this.children("i").text()).substr(0, 3),
		mesgBox = $this.next(".errMesg");

	// 未进行数据选择
	if (thisVal_substr == "请选择") {
		// 错误信息已经存在
		if (mesgBox.length != 0) {
			return false;
		}
		// 错误信息还不存在
		else {
			// 为元素后加入错误提示
			errMesg($this, mesg, posi);
			return false;
		}
	}
	// 已选择内容
	else {
		// 错误信息已经存在
		if (mesgBox.length != 0) {
			mesgBox.text("*").removeClass("errMesg");
			return true;
		}
		// 错误信息还不存在
		else {
			return true;
		}
	}
}

/** 
 * 功能：文本框输入验证
 * 参数：1.需验证元素唯一标识符；2.验证方法返回值（使用封装正则验证）；3.错误信息文本；4.错误提示显示位置
 **/
function valid_txtBox(ident, vldtro, mesg, posi) {
	var $this = $(ident),
		thisVal = $this.val(),
		vld_result = vldtro,
		mesgBox = $this.next(".errMesg");

	// 验证通过
	if (vld_result) {
		// 验证消息已存在
		if (mesgBox.length != 0) {
			$this.next(".errMesg").text("*").removeClass("errMesg");
			return true;
		}
		// 验证消息不存在
		else {
			return true;
		}
	}
	// 验证不通过
	else {
		// 验证消息已存在
		if (mesgBox.length != 0) {
			return false;
		}
		// 验证消息不存在
		else {
			errMesg($this, mesg, posi);
			return false;
		}
	}
}

/** 
 * 功能：文本框输入验证(无“*”创建验证)
 * 参数：1.需验证元素唯一标识符；2.验证方法返回值（使用封装正则验证）；3.错误信息文本；4.错误提示显示位置
 **/
function valid_txtBox_create(ident, vldtro, mesg, posi) {
	var $this = $(ident),
		vld_result = vldtro,
		mesgBox_len = $this.next(".errMesg").length;

	// 验证通过
	if (vld_result) {
		// 验证消息已存在
		if (mesgBox_len != 0) {
			$this.next(".errMesg").remove();
			return true;
		}
		// 验证消息不存在
		else {
			return true;
		}
	}
	// 验证不通过
	else {
		// 验证消息已存在
		if (mesgBox_len != 0) {
			$this.next(".errMesg").remove();
		}
		creatErrMesg($this, mesg, posi);
		return false;
	}
}

/** 
 * 功能：错误信息提示
 * 参数：1.需验证元素唯一标识符；2.错误信息文本内容或html内容；3.定位（缺省为“top”，在元素上方显示。“right”为星号的位置，元素右方显示。“bottom”在元素下放显示）
 **/
function errMesg(ident, mesg, posi) {
	var validElem = $(ident),
		ele_OW = validElem.outerWidth(),
		ele_OH = validElem.outerHeight();

	// 为需验证元素后加入错误提示信息
	validElem.next("span.reqItem").addClass("errMesg").text(mesg);

	var $this = $(ident).next(".errMesg"),
		this_H = $this.height();
	posi = posi == undefined ? "top" : posi;
	// 默认显示在上方
	if (posi == "top") {
		// 信息内容定位
		$this.css({
			"left": -(ele_OW + 8) + "px",
			"top": -(ele_OH / 2 + this_H / 2) + "px"
		});
	}
	// 显示在元素下方
	else if (posi == "bottom") {
		$this.css({
			"left": -(ele_OW + 8) + "px",
			"top": ele_OH / 2 + this_H / 2 + 2 + "px"
		});
	}
}

/** 
 * 功能：创建错误信息提示
 * 参数：1.需验证元素唯一标识符；2.错误信息文本内容或html内容；3.定位（缺省为“right”，在元素后面显示。“top”为星号的位置，元素上面显示）
 **/
function creatErrMesg(ident, mesg, posi) {
	var validElem = $(ident),
		ele_OW = validElem.outerWidth(),
		ele_OH = validElem.outerHeight();

	if (validElem.next(".errMesg").length != 0) {
		removeErrMesg(ident);
	}

	validElem.after(
		'<span class="errMesg">' +
		mesg +
		'</span>'
	);

	posi = posi == undefined ? "right" : posi;
	// 默认显示在元素后方
	if (posi == "right") {
		// 信息内容定位
		var $this = $(ident).next(".errMesg");
		$this.css({
			"width": "0",
			//"height": "0",
			"padding": "0 8px"
		});
	}
	// 在元素上面显示
	else if (posi == "top") {
		// 信息内容定位
		var $this = $(ident).next(".errMesg");
		$this.css({
			"width": "0",
			//"height": "0",
			"padding": "4px 0",
			"left": -(ele_OW) + "px",
			"top": "-26px",
			"display": "inline-block"
		});
	}
	// 显示在元素下方
	else if (posi == "bottom") {
		// 信息内容定位
		var $this = $(ident).next(".errMesg"),
			this_H = $this.height();
		$this.css({
			"width": "0",
			//"height": "0",
			"padding": "0",
			"left": -(ele_OW) + "px",
			"top": ele_OH / 2 + this_H / 2 + 6 + "px",
			"display": "inline-block"
		});
	}
	else if(posi == "top-right") {
		// 信息内容定位
		var $this = $(ident).next(".errMesg");
		$this.css({
			"width": "0",
			//"height": "0",
			"padding": "0",
			"text-align": "right",
			"left": "-186px",
			"top": "-26px",
			"display": "inline-block"
		});
	}
}

/** 
 * 功能：消除创建的错误提示信息
 * 参数：1.需验证元素唯一标识符；
 **/
function removeErrMesg(ident) {
	$(ident).next(".errMesg").remove();
}

function modifErrMesg(ident) {
	$(ident).next(".errMesg").removeClass("errMesg").text("*");
}

/** 
 * 功能：字符长度范围限制
 * 参数：1.需验证元素唯一标识符；2.最小字符数；3.最大字符数
 **/
function inputRange(ident, minCharLen, maxCharLen) {
	var $this = $(ident),
		thisVal = $this.val(),
		thisVal_len = thisVal.length;
	// 不在限制范围内
	if (thisVal_len < minCharLen || thisVal_len > maxCharLen) {
		return false;
	}
	// 符合范围
	else {
		return true;
	}
}
/**
 * 功能：字符长度范围限制(已经取得的字符)
 * 参数：1.字符串；2.最小字符数；3.最大字符数
 **/
function strRange(str, minCharLen, maxCharLen) {
	var this_str = str,
		str_len = this_str.length;
	// 不在限制范围内
	if (str_len < minCharLen || str_len > maxCharLen) {
		return false;
	}
	// 符合范围
	else {
		return true;
	}
}
/**
 * 功能：富文本基本验证方法
 * 参数：1.实例化出的UEditor名称;2.富文本最外层容器标识符；3.最小字符数；4.最大字符数；5.提示文本内容
 **/
function valid_richTxt(editor,ident,min,max,mesg) {
	var count = editor.getContentLength(true),
		str_range = count >= min && count <= max;
	valid_txtBox_create(ident, str_range, mesg, "top-right");
}