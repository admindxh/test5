/**
* 功能：表格元素公共功能库
* 日期：2014年11月27日
* 作者：weiye
**/ 

//===================================================
//				页面加载完成后执行部分
//===================================================
$(function() {
	/* 鼠标悬浮完整文本显示功能 */
	BASECSS.hoverText(".tableManage td span[class*='maxW']");

	/* IE8及以下浏览器的表格隔行变色 */
	trAlternateColor(".dataTable");

	/* 表格内每行内首个单元格的左间隙 */
	setTrFirstCell_PL(".dataTable", 20);
});

//===================================================
//				  事件绑定及触发部分
//===================================================

/* 表格全选按钮功能 */
$(document).on("click", "input[type='checkbox'].allCheck", function() {
	dataAllCheck(".allCheck");
});

/* 数据表内非全选复选框的功能 */
$(document).on("click", "tbody input[type='checkbox'].dataCheck", function() {
	var $this = $(this),
		allCkd = $("thead .allCheck");
	// 消除选中效果
	if($this.parents("tr").hasClass("trChecked")) {
		$this.parents("tr").removeClass("trChecked");
	}
	// 添加选中效果
	else {
		$this.parents("tr").addClass("trChecked");
	}
	var thisTr = $("tbody tr"),
		tr_len = thisTr.length,
		tr_ckd = $("tbody tr.trChecked"),
		tr_ckd_len = tr_ckd.length;
	// 全部未选中
	if(tr_ckd_len == 0) {
		allCkd.prop("checked", false);
	}
	// 全部选中
	else if(tr_ckd_len == tr_len) {
		allCkd.prop("checked", "checked");
	}
	// 部分选中
	else {
		allCkd.prop("checked", false);
	}
});

/* 弹出是否删除询问框 */
$(document).on("click", ".dataTable tbody a.dele", function() {
	// 弹出询问框
	whetherDelTr();
	// 添加删除状态
	$(this).parents("tr").addClass("dele-waiting");
});

/* 确认删除 */
$(document).on("click", ".popupBox .sureDeleTr", function() {
	// 移除具有删除状态的表行
	$(".dataTable tbody .dele-waiting").remove();
	closePopup();
});

/* 取消删除 */
$(document).on("click", ".popupBox .noDeleTr", function() {
	// 移除具有删除状态的表行
	$(".dataTable tbody .dele-waiting").removeClass("dele-waiting");
});

//===================================================
//					方法函数定义部分
//===================================================

/**
 * 功能：数据全选复选框（动态生成的table）
 * 参数：1.表单元素标识符
 **/
function dataAllCheck(checkbox) {
	var body_tr = $("tbody input[type='checkbox'].dataCheck"),
		tr_len = body_tr.length,
		ckd_sum = 0;
	for(var i = 0; i < tr_len; i++) {
		var this_tr = body_tr.eq(i);
		if(this_tr.prop("checked")) {
			ckd_sum ++;
		}
	}
	// 全未选中
	if(ckd_sum == 0) {
		$(checkbox).prop("checked", "checked");
		body_tr.prop("checked", "checked");
		body_tr.parents("tr").addClass("trChecked");
	}
	// 全选中
	if(ckd_sum == tr_len) {
		$(checkbox).prop("checked", false);
		body_tr.prop("checked", false);
		body_tr.parents("tr").removeClass("trChecked");
	}
	// 部分选中
	else {
		$(checkbox).prop("checked", "checked");
		body_tr.prop("checked", "checked");
		body_tr.parents("tr").addClass("trChecked");
	}
}

/**
 * 功能：IE8及以下浏览器的表格隔行变色
 * 参数：1.元素标识符
 **/
function trAlternateColor(ident) {
	// 如果IE浏览器版本号小于等于8
	if (BASECSS.ltIEVersion(8)) {
		// 为奇偶行设置指定的颜色
		$(ident + " tbody tr:odd").css("background-color", "#faf9f3");
		$(ident + " tbody tr:even").css("background-color", "#fff");
	} else {
		return;
	}
}

/**
 * 功能：数据删除提示和状态附加
 * 参数：无
 **/
function whetherDelTr() {
	// 弹出层内容
	popupLayer(
		'', 
		'<div class="txt-ac">是否删除该条数据？</div>', 
		'<button type="button" class="btn-sure sureDeleTr mr15">确定</button>' +
		'<button type="button" class="btn-sure noDeleTr cancel ml15">取消</button>'
	);
}