/**
* 功能：表单元素公共功能库
* 日期：2014年11月27日
* 作者：weiye
**/ 

//===================================================
//				页面加载完成后执行部分
//===================================================

//===================================================
//				  事件绑定及触发部分
//===================================================
/* 复选框父节点点击选择功能 */
$(".formEleGroup-squad").click(function() {
	form_checkChild(this);
});

/* 景点选择显示 */
$(".chooseDest .formEleGroup-squad").click(function() {
	var $this = $(this),
		isDest = $(this).children("input[name='dest']").length != 0,
		childCkb = $(this).hasClass("ckd"),
		thisIndex = 0;
	// 目的地的表单组
	if(isDest) {
		// 已选中
		if(childCkb) {
			// 获取该目的地的按钮容器的索引值
			thisIndex = $this.index();
			// 并显示对应的景点
			$(".destScenic").find(".formLine-small-group").eq(thisIndex).css("display", "inline-block");
		}
		// 未选中或取消选中
		else {
			// 获取该目的地的按钮容器的索引值
			thisIndex = $this.index();
			
			var scenic_span = $(".destScenic").find(".formLine-small-group").eq(thisIndex),
				scenic = scenic_span.find("input[name=scenic]"),
				scenic_edit = scenic_span.find("input[name=vies]");
			
			// 取消景点的选中
			scenic.prop("checked", false);
			scenic_edit.prop("checked", false);
			
			// 隐藏对应的景点
			scenic_span.hide(300);
		}
	}
});

//===================================================
//					方法函数定义部分
//===================================================

/**
* 功能：复选框父节点点击选择功能 
* 参数：父节点的标识符
**/
function form_checkChild(ident, eleType) {
	// 选择按钮框的父节点
	var $this = $(ident),
		hasCkd = $this.hasClass("ckd");
		
	// 已被选中，改为未选中
	if(hasCkd){
		$this.children("input[type='checkbox']").prop("checked", false);
		// 移除选中标识Class
		$this.removeClass("ckd");
	}
	// 未被选中，改为选中
	else {
		// 是否为禁用状态
		var isDisabled = $this.children("input[type='checkbox']").prop("disabled");
		// 为禁用状态
		if(isDisabled) {
			return;
		} 
		// 非禁用状态
		else {
			$this.children("input[type='checkbox']").prop("checked", "checked");
			$this.addClass("ckd");
		}
	}
}

/**
 * 功能：事件选择控件的实现过程
 * 参数：1.开始时间ID标识符；2.结束时间ID标识符
 **/
function datePicker(startD, endD) {
	$("#" + startD).datepicker({
		format: 'yyyy-mm-dd',
		showMeridian: true,
		pickTime: true,
		minView: 2,
		weekStart: 1,
		autoclose: true,
		todayBtn: true,
		language: 'zh-CN',
		"onRender":function(){
			alert($("#"+startD).valueOf()+"--------"+$("#"+endD).valueOf());
		
		
		}
	})/*.on("changeDate", function(ev) {
		var staVal = $(this).val(),
			endVal = $("#" + endD).val(),
			staArr = staVal.split("-"),
			endArr = endVal.split("-"),
			sta = new Date(staArr[0],staArr[1],staArr[2]),
			end = new Date(endArr[0],endArr[1],endArr[2]);
	})*/;
	$('#' + endD).datepicker({
		format: 'yyyy-mm-dd',
		showMeridian: true,
		pickTime: true,
		minView: 2,
		weekStart: 1,
		autoclose: true,
		todayBtn: true,
		language: 'zh-CN'
	});
}