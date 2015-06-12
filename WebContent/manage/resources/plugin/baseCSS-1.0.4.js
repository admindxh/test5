/**
 * 作者：Verning Aulence
 * 日期：2014-8-6
 * 版本：1.0.4
 * 功能：页面基本样式控制方法
 **/

/**************************************************************************************
 ***************************************************************************************
 * 在这里选取方法函数：(目录)
 *
 * 1、块元素--水平、垂直居中 ==》 BASECSS.eleCentered(ident)
 * 2、块元素--最大、最小和当前高度等于当前父容器高度 ==》 BASECSS.blockAllHeight(maxormin, prt, ident, head, foot)
 * 3、动作交互--鼠标悬浮显示名称 ==》 BASECSS.hoverText(ident)
 * 4、平均分列指定元素下的直接子元素 ==》 BASECSS.averageCols(ident)
 * 5、判断IE浏览器的版本号是否小于等于指定版本号 ==》 BASECSS.ltIEVersion(versionNum)
 *
 ***************************************************************************************
 **************************************************************************************/

// 框架方法执行
$(function() {
	//BASECSS.setMarginLeft();
	//BASECSS.setMarginTop();
});
// 创建全局对象变量
var BASECSS = {
	/**** 1、块元素--水平、垂直居中 ****/
	// 参数：需要居中的容器标识符
	eleCentered: function (ident) {

		// 初始化该元素的位置（x:0;y:0）
		$(ident).css({
			// 添加或修改position属性为绝对定位
			"position": "absolute",
			// 水平初始化
			"margin-left": "0px",
			"left": "0px",
			// 垂直初始化
			"margin-top": "0px",
			"top": "0px",
			"box-size": "border-box"
		});

		// 获取元素宽度、高度、滚动条距离页面顶部的高度，以及当前文档的高度
		var div_OW = $(ident).outerWidth(),
			div_OH = $(ident).outerHeight(),
			scroll_T = $(window).scrollTop(),
			win_OH = $(window).outerHeight();

		// 分别对水平和垂直方向进行居中定位
		$(ident).css({
			// 水平居中
			"margin-left": "50%",
			"left": -(div_OW / 2) + "px",
			// 垂直居中
			"margin-top": (win_OH / 2) + scroll_T + "px",
			"top": -(div_OH / 2) + "px"
		});
	},

	/**** 2、块元素--最大（或最小）高度等于当前父容器高度（推荐作为左侧导航栏和主内容容器使用） ****/
	// 参数：(1)max(最大)、min(最小)或static(静态)；(2) 父容器标识符（可为document、window）；(3) 本容器标识符；(4) 页面的header（缺省值为0）； (5) 页面的footer（缺省值为0）
	blockAllHeight: function (maxormin, prt, ident, head, foot) {

		// 获取父容器的尺寸
		var prt_OH = $(prt).height();
		// 获取header和footer的高度（缺省为0）
		var head_OH = typeof ($(head).height()) == "undefined" ? 0 : $(head).outerHeight();
		var foot_OH = typeof ($(foot).height()) == "undefined" ? 0 : $(foot).outerHeight();

		// 设置该元素的定位为水平垂直居中
		if(maxormin == "max"){
			$(ident).css({
				"max-Height": (prt_OH - head_OH - foot_OH) + "px",
				"border-top": "none",
				"border-bottom": "none",
				"top": head_OH + "px"
			});
		} else if(maxormin == "min") {
			$(ident).css({
				"min-Height": (prt_OH - head_OH - foot_OH) + "px",
				"border-top": "none",
				"border-bottom": "none",
				"top": head_OH + "px"
			});
		} else if(maxormin == "static") {
			$(ident).css({
				"height": (prt_OH - head_OH - foot_OH) + "px",
				"border-top": "none",
				"border-bottom": "none",
				"top": head_OH + "px"
			});
		} else {
			alert("请检查“BASECSS.blockAllHeight()”对象函数的“maxormin”配置是否正确。")
		}
		
	},

	/**** 3、动作交互--鼠标悬浮显示名称 ****/
	// 参数:该元素的标识符
	hoverText: function (ident) {

		// 绑定鼠标离开事件
		$(document).on("mouseenter", ident, function () {

			// 获取该元素的文本内容
			var oTxt = $(this).text();
			$(this).attr("title", oTxt);
		});
	},

	/**** 4、平均分列指定元素下的直接子元素 ****/
	// 参数：(1) 该元素的标识符
	averageCols: function (ident) {

		// 该元素的jQuery对象
		var $ele = $(ident);
		// 设置该元素的容器属性为裁切
		$ele.css("overflow","hidden");

		// 获取宽度
		var ele_W = $ele.width(),
			// 获取直接子元素个数
			eleChild_Len = $ele.children().length;
		
		// 每个子元素的宽度
		var eleChild_W = ( parseInt(ele_W)/eleChild_Len ) + "px";
		$(ident).children().css({
			"width": eleChild_W,
			"float": "left",
			"margin": "0px 0px 0px 0px"
		});

	},
	
	/**** 5、判断IE浏览器的版本号是否小于等于指定版本号 ****/
	// 参数：（1）IE浏览器版本号（数值，若IE版本号大于等于11，也视为非IE浏览器）
	ltIEVersion: function(versionNum) {
		
		// 获取浏览器信息、判断是否为IE、获取IE版本号
		var BwsInfo = navigator.userAgent,
			isIE = BwsInfo.indexOf('MSIE') > -1,
			thisVer = parseInt(isIE ? /\d+/.exec(BwsInfo.split(';')[1]) : 'no ie');
		
		// 当前是IE浏览器，并且浏览器版本小于等于指定值
		if(isIE && thisVer <= versionNum) {
			return true;
		}
		// 非IE浏览器，或IE浏览器版本大于等于11
		else {
			return false;
		}
	},
	
	/**** 6、设置元素的外边距 ****/
	// 设置上边距（margin-top）
	setMarginTop: function() {
		var thisEle = $("[class*='mt']"),
			ele_leng = thisEle.length;
		
		for(var i = 0; i < ele_leng; i++) {
			var	class_str = thisEle.eq(i).attr("class"),
				class_group = class_str.split(" "),
				class_group_len = class_group.length,
				reg_mt = /mt{1}\d{1}/;
			
			for(var j = 0; j < class_group_len; j++) {
				var class_ele = class_group[j];
				
				// 匹配class内容为“ml+数字”的样式
				if(reg_mt.test(class_ele)) {
					var pixel = parseInt(class_ele.replace(/[^0-9]/ig,""));
					$(thisEle[i]).css("margin-top", pixel + "px");
				}
			}
		}
	},
	
	// 设置左边距（margin-left）
	setMarginLeft: function() {
		var thisEle = $("[class*='ml']"),
			ele_leng = thisEle.length;
		
		for(var i = 0; i < ele_leng; i++) {
			var	class_str = thisEle.eq(i).attr("class"),
				class_group = class_str.split(" "),
				class_group_len = class_group.length,
				reg_ml = /ml{1}\d{1}/;
			
			for(var j = 0; j < class_group_len; j++) {
				var class_ele = class_group[j];
				
				// 匹配class内容为“ml+数字”的样式
				if(reg_ml.test(class_ele)) {
					var pixel = parseInt(class_ele.replace(/[^0-9]/ig,""));
					$(thisEle[i]).css("margin-left", pixel + "px");
				}
			}
		}
	}
}
