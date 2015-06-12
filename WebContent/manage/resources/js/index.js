/**
 * 功能：管理后台索引页布局控制
 * 日期：2014年11月21日
 * 作者：weiye
 **/
//===================================================
//				全局对象和变量定义部分
//===================================================
var INDEXGLOBAL = {
	Header_H: $(".header").outerHeight(),
	Footer_H: $(".footer").outerHeight()
}

//===================================================
//				页面加载完成后执行部分
//===================================================
$(function () {
	/* 将指定元素的高度设为合适的高度 */
	resetWinHeight();
	// 页面缩放触发
	$(window).resize(function () {
		// 将指定元素的高度设为合适的高度
		resetWinHeight();
	});

	/* 清除副导航首个按钮的border-top */
	clearMinorFirstNav_BT();

	/* 若该页不是“管理员首页”则显示副导航 */
	//notHomePage();
})

//===================================================
//				  事件绑定及触发部分
//===================================================

/* 管理首页按钮 */
$(".toAdminHome, .adminHome a").click(function () {
	// 隐藏副导航的父容器
	//$(".main_left").css("display", "none");
	// 清除主导航的选中效果
	$(".main_nav a.checked").removeClass("checked");
});

/* 显示副导航 & 导航切换 & 选中效果 */
$(".main_nav a").click(function () {
	var $this = $(this),
		thisIndex = $this.index(),
		minor_nav = $(".minor_nav"),
		main_left = $(".main_left");
	// 隐藏其它导航栏
	minor_nav.addClass("disp-n");

	// 显示该页导航栏，清除其他选中效果，并选中第一个副导航按钮
	minor_nav.eq(thisIndex).removeClass("disp-n");
	minor_nav.eq(thisIndex).find("a").removeClass("checked");
	minor_nav.eq(thisIndex).find("a:first").addClass("checked");

	// 为本按钮添加选中效果，移除其它导航按钮(包括系统首页按钮)的选中效果
	$(this).addClass("checked").siblings("a").removeClass("checked");
	$(".adminHome a").removeClass("checked");
	
	// 游标归位
	var	first_navLi = minor_nav.eq(thisIndex).find("li:first");
		navLi_OffT = first_navLi.offset().top,
		navLi_a_OH = first_navLi.children("a").outerHeight();
	$(".navNonius").css("top", (navLi_OffT + (navLi_a_OH - 26) / 2)+ "px");
});


/* 副导航选中效果 */
$(".minor_nav li > a").click(function () {
	var $this = $(this),
		$otherNav = $(".minor_nav li > a"),
		nav_OH = $this.outerHeight();

	// 移除其它导航按钮的选中效果，为本按钮添加选中效果
	$(".minor_nav li a.checked").removeClass("checked");
	$this.addClass("checked");

	// 若含有二级菜单
	var hasArrow = $this.next("i").length === 1;
	if (hasArrow) {
		// 将所有导航按钮的箭头设为收拢
		$otherNav.nextAll("div").addClass("disp-n");
		$(".minor_nav li > i").removeClass("down").addClass("right");
		// 将本导航按钮设为展开
		$this.nextAll("div").removeClass("disp-n");
		$this.next("i").removeClass("right").addClass("down");
	}
	// 获取按钮位置
	var thisNav_offset_T = $this.offset().top;
	// 设置游标位置
	$(".navNonius").css("top", (thisNav_offset_T + (nav_OH - 26) / 2) + "px");
});

/* 二级导航选中 */
$(".minor_nav li > div > a").click(function () {
	// 清除二级导航的选中效果
	$(".minor_nav li a.checked").removeClass("checked");
	// 选中本按钮和父级按钮
	$(this).addClass("checked").parent().prevAll("a").addClass("checked");
});

/* 管理索引首页后左导航点击选中主导航效果 */
$(".minor_nav").eq(0).click(function () {
	$(".main_nav a").eq(0).addClass("checked");
});

/* 手风琴菜单效果 */
$(document).on("click", ".minor_nav h2", function () {
	var thisNavBtn = $(this);
	// 使导航展开
	if ($(this).next("div").hasClass("disp-n")) {
		// 给该导航组以外的导航组加上隐藏样式类，并改变箭头方向 
		thisNavBtn.siblings("h2").next("div").addClass("disp-n");

		// 移除预先使该导航组隐藏的样式类
		thisNavBtn.next("div").removeClass("disp-n");
	}
	// 收缩自身
	else {
		// 隐藏所属导航组
		thisNavBtn.next("div").addClass("disp-n");
	}
});

/* 退出弹出确认框 */
$(document).on("click", ".adminExit", function () {
	var pl = $(".popupLayer");
	var pl_ml_pBox = pl.children(".maskLayer").children(".popupBox");

	// 信息内容
	pl_ml_pBox.children(".popupBox-content").html('<span class="icon-info2"></span>确定退出，返回到登录界面！');

	// 按钮
	pl_ml_pBox.children(".popupBox-button").html('<a class="gotoLogin mr30">确 定</a><a class="cancel">取 消</a>');
	pl.show();

	// 使其居中
	BASECSS.eleCentered(pl_ml_pBox);
});

/* 基本取消操作 */
$(document).on("click", ".cancel", function () {
	var pl = $(".popupLayer");
	var pl_ml_pBox = pl.children(".maskLayer").children(".popupBox");
	pl_ml_pBox.children(".popupBox-content").html('');
	pl_ml_pBox.children(".popupBox-button").html('');
	pl.hide();
});

/* 退出系统 */
$(document).on("click", ".gotoLogin", function() {
	location.href="logoff";
});
//===================================================
//					方法函数定义部分
//===================================================

/* 清除副导航首个按钮的border-top */
function clearMinorFirstNav_BT() {
	// 取得副导航对象和它的个数
	var minorNav = $(".minor_nav"),
		minorNav_Num = minorNav.length;
	// 循环清除
	for (var i = 0; i < minorNav_Num; i++) {
		minorNav.eq(i).find("li:first").css("border-top", "none");
	}
}

/* 重设页面高度 */
function resetWinHeight() {

	// footer距离窗口顶部的距离
	var foot_Offset_T = $(".footer").offset().top,
		// header的实际高度
		head_OH = $(".header").outerHeight(),
		// 计算出main填满窗口需要的高度
		main_H = (foot_Offset_T - head_OH);

	// 设置main的高度
	$(".main").css("height", main_H);
}