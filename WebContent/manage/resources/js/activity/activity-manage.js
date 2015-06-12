/**
* 功能：“活动&专题信息管理-活动信息管理”相关功能
* 日期：2014年12月8日
* 作者：weiye
**/ 

/**** 加载完成后执行 ****/

/* 鼠标悬浮显示完整文本内容 */
BASECSS.hoverText(".poplayer .activName, .poplayer .activAccount > span");

/* 表格内每行内首个单元格的左间隙 */
setTrFirstCell_PL(".tableManage table", 16);

/**** 事件执行部分 ****/

/* 上传列表内的查看功能 */
$(document).on("click", ".upload-lookup", function() {
	// 查看上传内容层
	var uploadCont = $(".lookupUploadCont");
	uploadCont.show(0,function() {
		parentCentered(".lookupUploadCont");
	});
});

/* 上传列表内的编辑功能 */
$(document).on("click", ".upload-edit", function() {
	// 查看上传内容层
	var uploadCont = $(".editUploadCont");
	uploadCont.show(0,function() {
		parentCentered(".editUploadCont");
	});

});

/* 查看报名资料 */
$(document).on("click", ".lookupApplyInfo", function() {
	var applyInfo = $(".applyInfo"),
		scrTop = $(window).scrollTop(),
		main_left_w = $(window.parent.document).find(".main_left").width();
	applyInfo.show(0,function() {
		/*parentCentered(".applyInfo");*/
		applyInfo.css({
			"left": "50%",
			"top": scrTop + 100 + "px",
			"margin-left": -261 - (main_left_w / 2) + "px",
			"margin-bottom": "100px"
		});
	});
});

/* 弹出层关闭按钮 */
$(document).on("click", ".poplayer .close-tras_black, .poplayer .close-layer", function() {
	$(this).parents(".poplayer").slideUp(300);
});

/* 表格数据删除 */
$(document).on("click", ".tableManage a.delThisTr", function() {
	var $thisDel = $(this);
	var code = $thisDel.attr("code");
	
	// 提交删除请求
	$.ajax({
		url : "web/activityProductController/deleteActivityProduct",
		dataType: "json",
		type : "post",
		data : {"code":code},
		success : function(data){
			if (data ==  "success") {
				// 删除该节点
				$thisDel.parents("tr").remove();
			    alert("成功");
			}else {
				alert("失败");
			}
		}
	});
});