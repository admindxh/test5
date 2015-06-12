/**
* 功能：“活动&专题信息管理-活动信息管理-新建活动”相关功能
* 日期：2014年12月2日
* 作者：weiye
**/ 
// 全局计数器
var COUNTER = 1;

//===================================================
//				页面加载完成后执行部分
//===================================================
$(function() {
	// 运行时间选择控件
	datePicker("startDate", "endDate");
	// 根据“报名”项是否选中来判断是否禁用“报名付费”项
	checkPayItem();
	
	/* 页面加载完成后显示活动选项 */
	$(".activModule").show(1000);

	/* 根据是否选择来加载各模块 */
	var upload_isCkd = $("[name='isUpload']").prop("checked"),
		vote_isCkd = $("[name='isVote']").prop("checked"),
		apply_isCkd = $("[name='isEnroll']").prop("checked"),
		pay_isCkd = $("[name='isEnrollPay']").prop("checked");

	// 投票是否选中
	if(vote_isCkd) {
		// 显示
		$("#voteModule").show();
	} else {
		$("#voteModule").hide();
	}

	// 报名是否选中
	if(apply_isCkd) {
		// 显示
		$("#applyModule").show();
	} else {
		$("#applyModule").hide();
	}

	// 报名付费是否选中
	if(pay_isCkd) {
		// 显示
		$("#payModule").show();
	} else {
		$("#payModule").hide();
	}
});

//===================================================
//				  事件绑定及触发部分
//===================================================

/* 只有选择了“报名”项后才能选择“报名付费”项 */
$(".applyItem").click(function() {
	var thisCkb = $(this).children("input[type='checkbox']"),
		isCkd = thisCkb.prop("checked"),
		applyPay = $(this).next().children("input[type='checkbox']"),
		lbl_pay = $(this).next().children(".lbl_check");
	
	// 如果为选中，则移除“报名付费”项的禁用效果
	if(isCkd) {
		applyPay.removeAttr("disabled");
		// 设置字体颜色我鼠标形状
		lbl_pay.css("color", "#666");
	}
	// 如果为未选中，则添加禁用状态，并将“报名付费”项设为未选中状态
	else {
		applyPay.prop("disabled", "disabled");
		applyPay.prop("checked", false);
		lbl_pay.css("color", "#bcbcbc");
	}
});

/* “报名付费”项为禁用时的提示 */
$(".applyPay").click(function() {
	var thisCkb = $(this).children("input[type='checkbox']"),
		isDisabled = thisCkb.prop("disabled");
	
	// 为禁用状态时
	if(isDisabled) {
		msgBox("请先选择“报名”项！", "info");
	}
});

/**** 勾选/取消选中项显示/隐藏对应模块并移除隐藏模块的内容和提示 ****/

/* 投票 */
$("[name='isVote']").parent(".formEleGroup-squad").click(function() {
	var this_isCkd = $(this).children("[name='isVote']").prop("checked");
	// 选中
	if(this_isCkd) {
		// 初始化内容（清空）
		$("#voteModule input").val("");
		ue2.execCommand('cleardoc');
		// 清空错误提示
		modifErrMesg("[name='voteName'], #voteNum");
		removeErrMesg("#editor2");
		// 重置下拉菜单的值及选项
		$("#voteNum").children("i").text("选项个数");
		$(".optionSet").html("");
		// 显示该模块
		$("#voteModule").show();
	}
	// 取消选中
	else {
		// 初始化内容（清空）
		$("#voteModule input").val("");
		ue2.execCommand('cleardoc');
		// 清空错误提示
		modifErrMesg("[name='voteName'], #voteNum");
		removeErrMesg("#editor2");
		// 重置下拉菜单的值及选项
		$("#voteNum").children("i").text("选项个数");
		$(".optionSet").html("");
		// 隐藏该模块
		$("#voteModule").hide();
	}
});
/* 报名 */
$("[name='isEnroll']").parent(".formEleGroup-squad").click(function() {
	var this_isCkd = $(this).children("[name='isEnroll']").prop("checked");
	// 选中
	if(this_isCkd) {
		// 初始化内容（清空）
//		$("#applyModule input").val("");
//		ap1.execCommand('cleardoc');
//		ap2.execCommand('cleardoc');
//		ap3.execCommand('cleardoc');
		// 清空错误提示
		removeErrMesg("[name='name1'],[name='name2'],[name='name3']");
		// 显示该模块
		$("#applyModule").show();
		removeErrMesg(".activModule .formEleGroup-squad:last");
	}
	// 取消选中
	else {
		// 初始化内容（清空）
//		$("#applyModule input").val("");
//		ap1.execCommand('cleardoc');
//		ap2.execCommand('cleardoc');
//		ap3.execCommand('cleardoc');
		// 清空错误提示
		removeErrMesg("[name='name1'],[name='name2'],[name='name3']");
		// 隐藏该模块
		$("#applyModule").hide();
		$("#payModule").hide();
	}
});
/* 报名支付 */
$("[name='isEnrollPay']").parent(".formEleGroup-squad").click(function() {
	var this_isCkd = $(this).children("[name='isEnrollPay']").prop("checked");
	// 选中
	if(this_isCkd) {
		// 初始化内容（清空）
		$("#payModule input").val("");
		// 清空错误提示
		modifErrMesg("[name='money']");
		// 显示该模块
		$("#payModule").show();
	}
	// 取消选中
	else {
		// 初始化内容（清空）
		$("#payModule input").val("");
		// 清空错误提示
		modifErrMesg("[name='money']");
		// 隐藏该模块
		$("#payModule").hide();
	}
});

/* 投票相关——新建选项个数 */
$(".optionChoose dl dt").click(function() {

	// 添加选项
	var opts_num = parseInt($(this).text()),
		opts_len = $(".optionSet").children(".formLine").length;
	
	// 清除错误提示
	modifErrMesg(".optionChoose");
	
	// 所选项比之前多，且不为空
	if(opts_num > opts_len && opts_len != 0) {
		var addOpt_num = opts_num - opts_len;
		for(var i = opts_len; i < opts_num; i++) {
			$(".optionSet").append(
				'<div class="formLine">'+
				'<label>选项' + (i + 1) + ':</label>'+
				'&nbsp;<input type="text" name="options" maxlength="" class="w-260 voteOption_option">'+
				'&nbsp;<span class="reqItem">*</span>' +
				'</div>'
			);
		}
	}
	// 所选项比之前少
	else if(opts_num < opts_len) {
		var delOpt_num = opts_len - opts_num;
		for(var i = opts_len; i > opts_num; i--) {
			$(".optionSet").children(".formLine").eq(i - 1).remove();
		}
	}
	// 如果相同，且不为空
	else if(opts_num == opts_len && opts_len != 0) {
		return;
	}
	// 如果为空
	else {
		for(var i = 0; i < opts_num; i++){
			$(".optionSet").append(
				'<div class="formLine">'+
				'<label>选项' + (i + 1) + ':</label>'+
				'&nbsp;<input type="text" name="options" maxlength="" class="w-260 voteOption_option">'+
				'&nbsp;<span class="reqItem">*</span>' +
				'</div>'
			);
		}
	}
});

/* 报名表单弹出框功能 */
$(document).on("click", "#addApply", function() {
	// 执行在父页面弹出框的功能
	popupLayer(
		/**** 标题 ****/
		'报名表单设置',
		
		/**** 内容 ****/
		// 字段编号
		'<div class="formLine mr55">' +
		'<label>字段编号:&nbsp;</label>' +
		'<input id="fieldNumber" name="" type="text" maxlength="7" class="w-260 fie_id">' +
		'&nbsp;<span class="reqItem">*</span>' +
		'</div>' +
		// 字段名称
		'<div class="formLine mr55">' +
		'<label>字段名称:&nbsp;</label>' +
		'<input id="fieldName" name="" type="text" maxlength="30" class="w-260 fie_name">' +
		'&nbsp;<span class="reqItem">*</span>' +
		'</div>' +
		// 字段类型
		'<div class="formLine mr55">' +
		'<label>字段类型:&nbsp;</label>' +
		'<div id="addFiedType" class="select-base">' +
		'<input id="fie_ext_type" type="hidden">' +
		'<i class="w-260 fie_type">请选择类型</i>' +
		'<dl>' +
		'<dt name="text">文本</dt>' +
		'<dt name="number">数字</dt>' +
		'<dt name=".jpg,.jpeg,.png,.bmp">图片上传</dt>' +
		'<dt name=".doc,.docx">文档上传</dt>' +
		'<dt name="select">下拉选框</dt>' +
		'</dl>' +
		'</div>&nbsp;<span class="reqItem">*</span></div>' +
		// 是否必填
		'<div class="formLine mr55 mt5">' +
		'<label>是否必填:&nbsp;</label>' +
		'<input id="isRequstItem" type="radio" name="reqItem" class="ml20 mr10" checked value="1"><label for="isRequstItem" class="pointer w-auto mr50">是</label>' +
		'<input id="noRequstItem" type="radio" name="reqItem" class="mr10" value="0"><label for="noRequstItem" class="pointer w-auto">否</label>' +
		'</div>',
		
		/**** 操作按钮 ****/
		'<div class="formLine">' +
		'<button type="button" class="btn-sure sureAddApplyInfo mr60">确定</button>' +
		'<button type="button" class="btn-sure cancel ml20">取消</button>' +
		'</div>'
	);
});

/* 报名表单——将填写的数据添入表格 */
$(document).on("click", ".sureAddApplyInfo", function() {
	
	// 定义字段编号、名称和类型
	var fie_id = $(".fie_id").val(),
		fie_name =  $(".fie_name").val(),
		fie_type =  $(".fie_type").text(),
		fie_ext_type = $("#fie_ext_type").val(),
		fie_option = $("#teamRole").val(),
		req = "", req_val = 0,
		isReq = $("#isRequstItem").prop("checked");
	
	if(isReq) {
		req = "是";
		req_val = 1;
	} else {
		req = "否";
		req_val = 0;
	}
	
	// 数据有效性验证
	$("[id='fieldNumber']").blur();
	$("[id='fieldName']").blur();
	var this_type = $("#addFiedType"),
		type_val = this_type.find("i.fie_type").text(),
		type_OW = this_type.outerWidth();

	// 字段类型验证
	if(type_val == "请选择类型") {
		this_type.next(".reqItem").addClass("errMesg").text("请选择类型").css({
			"width": "0", "height": "0",
			"padding": "4px 0",
			"left": -(type_OW + 8) + "px",
			"top": "-40px",
			"display": "inline-block"
		});
	}
	var erro_len = $(".popupBox").find(".errMesg").length;
	// 验证未通过
	if(erro_len > 0) {
		return;
	}
	// 验证通过
	else {
		// 限制报名表单信息的数量
		var applyInfo_len = $("#applyForm").children("tbody").find("tr").length;
		var allow_applyInfo_len = 30;
		if(applyInfo_len >= allow_applyInfo_len) {
			creatErrMesg(".sureAddApplyInfo", "最多只能添加" + allow_applyInfo_len + "条信息", "top");
			$(".sureAddApplyInfo").next(".errMesg").css({
				"left": "-100px",
				"top": "-46px"
			});
			return;
		}
		// 执行表格添加数据方法
		addApplyInfo(fie_id, fie_name, fie_type, fie_ext_type, fie_option, req, req_val);

		// 关闭弹出框
		closePopup();
		// 清除错误提示
		removeErrMesg(".tableManage");
		msgBox("数据添加完成！", "info", 1200);
	}
});

/* 报名表单——开始编辑 */
$(document).on("click", ".applyInfo_edit", function() {
	// 获取字段编号、名称和类型
	var thisTr = $(this).parents("tr"),
		dataCell = thisTr.children("td"),
		fie_id = dataCell.eq(0).text(),
		fie_name = dataCell.eq(1).text(),
		fie_type = dataCell.eq(2).text(),
		fie_ext_type = dataCell.eq(2).find("input[class='enrollFrom_propertyType']").val(),
		fie_option_val = dataCell.eq(2).find("input[class='enrollFrom_fie_option']").val(),
		fie_code = dataCell.eq(2).find("input[class='enrollFrom_code']").val(),
		isRequest = dataCell.eq(3).text(),
		select_line = "",
		selectVal = "未定义元素值";
	
	// 判断是否加入下拉选框行
	if(fie_type == "下拉选框") {
		select_line = '<div class="formLine">' +
		'<label>选项:&nbsp;</label>' +
		'<input id="teamRole" type="text" class="w-260 enrollFrom_fie_option" placeholder="各选项用英文的逗号“,”分割" value="' +
		fie_option_val + 
		'">&nbsp;<span class="reqItem">*</span></div>'
	} else {
		select_line = "";
	}
	
	// 执行在父页面弹出框的功能
	popupLayer(
		/**** 标题 ****/
		'报名表单设置',
		
		/**** 内容 ****/
		// 字段编号
		'<div class="formLine mr55">' +
		'<label>字段编号:&nbsp;</label>' +
		'<input id="fieldNumber" name="" type="text" maxlength="7" class="w-260 fie_id" value="' +
		fie_id +
		'">&nbsp;<span class="reqItem">*</span></div>' +
		// 字段名称
		'<div class="formLine mr55">' +
		'<label>字段名称:&nbsp;</label>' +
		'<input id="fieldName" name="" type="text" class="w-260 fie_name" value="' +
		fie_name +
		'">&nbsp;<span class="reqItem">*</span></div>' +
		// 字段类型
		'<div class="formLine mr55">' +
		'<label>字段类型:&nbsp;</label>' +
		'<div id="editFiedType" class="select-base">' +
		'<input id="fie_ext_type" type="hidden" value="'+fie_ext_type+'"><i class="w-260 fie_type">' +
		fie_type +
		'</i><dl>' +
		'<dt name="text">文本</dt>' +
		'<dt name="number">数字</dt>' +
		'<dt name=".jpg,.jpeg,.png,.bmp">图片上传</dt>' +
		'<dt name=".doc,.docx">文档上传</dt>' +
		'<dt name="select">下拉选框</dt>' +
		'</dl>' +
		'</div>&nbsp;<span class="reqItem">*</span></div>' +
		// 下拉选框（条件判断是否加入）
		select_line +
		// 是否必填
		'<div class="formLine mr55 mt5">' +
		'<label>是否必填:&nbsp;</label>' +
		'<input id="isRequstItem" type="radio" name="reqItem" class="ml20 mr10" value="1" checked><label for="isRequstItem" class="pointer w-auto mr50">是</label>' +
		'<input id="noRequstItem" type="radio" name="reqItem" class="mr10" value="0"><label for="noRequstItem" class="pointer w-auto">否</label>' +
		//隐藏域
		'<input id="fie_code" type="hidden" value="' + fie_code + '">' + 
		'</div>',
		/**** 操作按钮 ****/
		'<div class="formLine">' +
		'<button type="button" class="btn-sure sureModifApplyInfo mr20">确定</button>' +
		'<button type="button" class="btn-sure cancelEditApplyInfo cancel ml20">取消</button>' +
		'</div>'
	);
	
	// 必填项判断
	if(isRequest == "是") {
		$("#isRequstItem").prop("checked", "checked");
		$("#noRequstItem").prop("checked", false);
	} else {
		$("#isRequstItem").prop("checked", false);
		$("#noRequstItem").prop("checked", "checked");
	}
	
	// 页面载入时判断自定义下拉选框的内容，从而设置字体颜色
	setCustomSelectColor();
	
	// 添加编辑状态
	thisTr.addClass("editting");
});

/* 报名表单——编辑完成 */
$(document).on("click", ".sureModifApplyInfo", function() {
	
	// 定义字段编号、名称和类型
	var fie_id = $(".fie_id").val(),
		fie_name =  $(".fie_name").val(),
		fie_type =  $(".fie_type").text(),
		fie_ext_type =  $("#fie_ext_type").val(),
		fie_option_val = $("#teamRole").val(),
		req = "", req_val = 0,
		isReq = $("#isRequstItem").prop("checked"),
		fie_code = $("#fie_code").val();
	
	if(isReq) {
		req = "是";
		req_val = 1;
	} else {
		req = "否";
		req_val = 0;
	}

	// 数据有效性验证
	$("[id='fieldNumber']").blur();
	$("[id='fieldName']").blur();
	var this_type = $("#addFiedType"),
		type_val = this_type.find("i.fie_type").text(),
		type_OW = this_type.outerWidth();

	// 字段类型验证
	if(type_val == "请选择类型") {
		this_type.next(".reqItem").addClass("errMesg").text("请选择类型").css({
			"width": "0", "height": "0",
			"padding": "4px 0",
			"left": -(type_OW + 8) + "px",
			"top": "-40px",
			"display": "inline-block"
		});
	}
	var erro_len = $(".popupBox").find(".errMesg").length;
	// 验证未通过
	if(erro_len > 0) {
		return;
	} else {
		// 执行表格数据修改方法
		editApplyInfo(fie_id, fie_name, fie_type, fie_ext_type, fie_option_val, req, req_val, fie_code);

		// 关闭弹出框
		closePopup();
		msgBox("修改成功！", "info", 800);
	}
});

/* 报名表单——放弃编辑 */
$(document).on("click", ".cancelEditApplyInfo", function() {
	cancelEditting(".applyList");
});

/* 报名表单——准备删除 */
$(document).on("click", ".applyInfo_dele", function() {
	
	// 获取到可能需要删除的行
	var thisTr = $(this).parents("tr");
	
	// 添加删除状态
	thisTr.addClass("dele_waiting");
	
	// 弹出确认框
	popupLayer(
		'',
		'<div style="text-align:center">您确定要删除该条数据？</div>',
		'<div class="formLine">' +
		'<button type="button" class="btn-sure sureDeleApplyInfo mr20">确定</button>' +
		'<button type="button" class="btn-sure cancelDeleApplyInfo cancel ml20">取消</button>' +
		'</div>'
	);
});
/* 报名表单——确认删除 */
$(document).on("click", ".sureDeleApplyInfo", function() {
	
	// 获取等待删除的数据
	var delTr = $(".applyList").children("tbody").find(".dele_waiting");
	
	// 删除该条数据
	delTr.remove();
	
	// 关闭弹出框
	closePopup();
	msgBox("删除成功！", "info", 800);
});

/* 报名表单——放弃删除 */
$(document).on("click", ".cancelDeleApplyInfo", function() {
	// 取消删除状态
	$(".applyList").children("tbody").find(".dele_waiting").removeClass("dele_waiting");
});

/* 报名表单——字段类型为“下拉选框”时，创建文本框 */
$(document).on("click", "#addFiedType dt, #editFiedType dt", function() {
	var thisTxt = $(this).attr("name"),
		nextForm = $(this).parents(".formLine").next(".formLine"),
		nextInput = nextForm.children(".enrollFrom_fie_option"),
		hasInput = nextInput.length;
	if(thisTxt == "select" && hasInput == 0) {
		// 为该下拉框所在表单行之后创建另一个下拉框
		$(this).parents(".formLine").after(
			'<div class="formLine teamRoleLine mr55">' +
			'<label>选项:&nbsp;</label>' +
			'<input id="teamRole" type="text" class="w-260 enrollFrom_fie_option" placeholder="各选项用英文的逗号“,”分割">&nbsp;<span class="reqItem">*</span>' +
			'</div>'
		);
	} else if(thisTxt == "select" && hasInput != 0) {
		return;
	} else {
		$(this).parents(".formLine").next(".teamRoleLine").remove();
	}
});

/* 其他模块——添加模块 */
/*$(document).on("click", "#addMudle", function() {
	addOtherMudle(this);
	// 计数器叠加
	COUNTER += 1;
	// 消息提示
	msgBox("模块添加成功！", "info");
});*/

/* 其他模块——准备删除 */
$(document).on("click", ".otherMudle .formGroup .btn-delete", function() {
	otherMud_readyDel(this);
});

/* 其他模块——确认删除 */
$(document).on("click", ".sureDelOtherMud", function() {
	// 删除该模块
	$(".otherMudle .formGroup.dele_waiting").remove();
	closePopup();
	msgBox("删除成功！", "info", 800);
});

/* 其他模块——放弃删除 */
$(document).on("click", ".cancelDelOtherMud", function() {
	// 取消删除状态
	$(".otherMudle .formGroup.dele_waiting").removeClass("dele_waiting");
});

//===================================================
//					方法函数定义部分
//===================================================

/** 
* 功能：表单报名——添加信息 
* 日期：2014年12月10日
* 参数：1.字段编号；2.字段名称；3.字段类型；4.字段类型隐藏域类型；5.字段类型为下拉列表的隐藏域值；6.必选项的选择结果； 7.必选项的选择结果值
**/
function addApplyInfo(f_id, f_name, f_type, fie_ext_type, fie_option, req, req_val) {
	
	// 定义表格相关内容
	var tab = $(".applyList"),
		tab_body = tab.children("tbody");
	
	// 处理字段类型未选择的情况
	if( f_type == "请选择类型") {
		f_type = "";
	}
	if(fie_option==undefined){
		fie_option="";
	}
	// 加入数据
	tab_body.append(
		'<tr><td>' +
		f_id + 
		'</td><td>' +
		f_name +
		'<input type="hidden" class="enrollFrom_property" value="'+f_name+'">' + 
		'</td><td>' +
		f_type +
		'<input type="hidden" class="enrollFrom_propertyTypeName" value="'+f_type+'">' + 
		'<input type="hidden" class="enrollFrom_propertyType" value="'+fie_ext_type+'">' + 
		'<input type="hidden" class="enrollFrom_fie_option" value="'+fie_option+'">' + 
		'<input type="hidden" class="enrollFrom_req" value="'+req_val+'">' + 
		'<input type="hidden" class="enrollFrom_sortNum" value="'+f_id+'">' + 
		'</td><td>' +
		req + 
		'</td><td>' +
		'<a class="applyInfo_edit">编辑</a>' +
		'&nbsp;<a class="applyInfo_dele">删除</a>' +
		'</td><td></td></tr>'
	);
}

/** 
* 功能：表单报名——修改信息 
* 日期：2014年12月10日
* 参数：1.字段编号；2.字段名称；3.字段类型；4.字段类型隐藏域类型；5.字段类型为下拉列表的隐藏域值；6.必选项的选择结果；7.必选项的选择结果值
**/
function editApplyInfo(f_id, f_name, f_type, fie_ext_type, fie_option, req, req_val, fie_code) {
	// 定义表格相关内容
	var tab = $(".applyList"),
		tab_body = tab.children("tbody");
	
	// 处理字段类型未选择的情况
	if( f_type == "请选择类型") {
		f_type = "";
	}
	if(fie_option==undefined){
		fie_option="";
	}
	if(fie_code==undefined||fie_code=='undefined'){
		fie_code="";
	}
	// 加入数据
	tab_body.children(".editting").html(
		'<td>' +
		f_id +
		'</td><td>' +
		f_name +
		'<input type="hidden" class="enrollFrom_property" value="'+f_name+'">' + 
		'</td><td>' +
		f_type +
		'<input type="hidden" class="enrollFrom_propertyTypeName" value="'+f_type+'">' +
		'<input type="hidden" class="enrollFrom_propertyType" value="'+fie_ext_type+'">' +
		'<input type="hidden" class="enrollFrom_fie_option" value="'+fie_option+'">' + 
		'<input type="hidden" class="enrollFrom_req" value="'+req_val+'">' + 
		'<input type="hidden" class="enrollFrom_sortNum" value="'+f_id+'">' + 
		'<input type="hidden" class="enrollFrom_code" value="'+fie_code+'">' + 
		'</td><td>' +
		req + 
		'</td><td>' +
		'<a class="applyInfo_edit">编辑</a>' +
		'&nbsp;<a class="applyInfo_dele">删除</a>' +
		'</td><td></td>'
	);
	// 取消编辑状态
	cancelEditting(".applyList");
}

/* 取消表格数据编辑状态 */
function cancelEditting(ident) {
	var tab = $(ident),
		edittingTr = tab.children("tbody").find(".editting");
	// 取消编辑状态
	edittingTr.removeClass("editting");
}

/** 
* 功能：其他模块——添加模块 
* 日期：2014年12月11日
* 参数：1.添加按钮标识符
**/
function addOtherMudle(ident, basePath) {
	// 定义添加按钮
	var $this = $(ident),
		newMudule = '<div class="formGroup">' +
			'<button id="delMudle-' +
			COUNTER +
			'" type="button" title="删除" class="btn-delete"></button>' +
			'<div class="left">' +
			'<div class="formLine">' +
			'<label>图片:&nbsp;</label>' +
			'<div id="otherblockPickId' +
			COUNTER +
			'" type="button" class="btn-base uploadImg">点击上传图片</div> ' +
			//'<span class="reqItem mr10">*</span> ' +
			'<span class="txt-suggest">推荐尺寸为大于317 * 299的正方形</span>' +
			'</div>' +
			'<div class="formLine">' +
			'<label>名称:&nbsp;</label>' +
			'<input id="mud_name-' +
			COUNTER +
			'" name="mud_name-' +
			COUNTER +
			'" type="text" class="w-320 otherblock_name" maxlength="30" /> ' +
			//'<span class="reqItem">*</span>' +
			'</div>' +
			'<div class="formLine">' +
			'<label>连接:&nbsp;</label>' +
			'<input id="mud_link-' +
			COUNTER +
			'" name="mud_link-' +
			COUNTER +
			'" type="text" class="w-320 otherblock_hyperlink" />' +
			'</div>' +
			'</div>' +
			'<div class="right">' +
			'<label>缩略图:&nbsp;</label>' +
			'<img width="221" height="160" id="otherblockPreId' +
			COUNTER +
			'" src="'+basePath+'/manage/resources/img/ele/loadImg_default_c.jpg" title="缩略图名称" alt="请上传图片" class="va_m" />' +
			'<input class="otherblock_url" type="hidden" id="otherblockUrlHiddenId' +
			COUNTER +
			'" />' +
			'</div>' +
			'</div>';

	// 添加一个模块
	$this.parent(".otherMudle").append(newMudule);
}

/** 
* 功能：其他模块——删除准备 
* 日期：2014年12月11日
* 参数：1.删除按钮标识符
**/
function otherMud_readyDel(ident) {
	var $this = $(ident),
		// 获取该删除按钮的ID
		thisID = $this.attr("id");
	
	// 添加删除状态
	$("#" + thisID).parent(".formGroup").addClass("dele_waiting");
	
	// 弹出确认框
	popupLayer(
		'',
		'<div style="text-align:center">您确定要删除该模块？</div>',
		'<div class="formLine">' +
		'<button type="button" class="btn-sure sureDelOtherMud mr20">确定</button>' +
		'<button type="button" class="btn-sure cancelDelOtherMud cancel ml20">取消</button>' +
		'</div>'
	);
}

/* 根据“报名”项是否选中来判断是否禁用“报名付费”项 */
function checkPayItem() {
	var applyItem = $(".activModule .applyItem"),
		isCkd = applyItem.children("input[type='checkbox']").prop("checked"),
		payLbl = applyItem.next(".applyPay").children(".lbl_check"),
		payItem = applyItem.next(".applyPay").children("input[type='checkbox']");
	// 报名项已选中
	if(isCkd) {
		payItem.removeAttr("disabled");
		payLbl.css("color", "#666");
	}
	// 未选中 
	else {
		payItem.prop("disabled", "disabled");
		
	}
	
	var squad = $(".formEleGroup-squad"),
		squad_len = squad.length;
	
	// 页面加载运行查看是否选中
	for(var i = 0; i < squad_len; i++) {
		var squadArr =  squad.eq(i),
			isCkd = squadArr.children("input[type='checkbox']").prop("checked");
		if(isCkd) {
			// 添加选中标识Class
			squadArr.addClass("ckd");
		}
	}
}
