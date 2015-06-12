/**
* 功能：“门户首页”功能控制
* 日期：2014年11月25日
* 作者：weiye
**/ 

/* 副导航选中效果 */
$(".muduleIndex > a").click(function() {
	var $this = $(this),
		thisIndex = $this.index(),
		parentWin = $(window.parent.document),
		parentNav = parentWin.find(".minor_nav").eq(0).find("a"),
		$otherNav = $(".minor_nav > a");

	// 移除其它导航按钮的选中效果，为本按钮添加选中效果
	parentNav.removeClass("checked");
	parentNav.eq(thisIndex + 1).addClass("checked");

	// 获取按钮位置
	var parentNav_offset_T = parentNav.eq(thisIndex + 1).offset().top;
	// 设置游标位置
	parentWin.find(".navNonius").css("top", (parentNav_offset_T + 12) +"px");
});
