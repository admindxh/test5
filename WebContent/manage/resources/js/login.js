/**
 * 功能：管理员登陆功能
 * 日期：2014年12月12日
 * 作者：weiye
 **/

//===================================================
//				页面加载完成后执行部分
//===================================================

$(function () {
	// 设置背景图高度
	setBgHeight();

	// 页面缩放调整背景图
	$(window).resize(function () {
		setBgHeight();
	});

	/* IE版本小于9时的placeholder处理 */
	if (BASECSS.ltIEVersion(9)) {
		/* IE的placeholder效果 */
		IEPlaceholder("input[placeholder]");
	}
});


//===================================================
//				  事件绑定及触发部分
//===================================================

/**** 将页面内的明文密码框替换为普通密码框 ****/
/* 通过鼠标悬浮 */
$(".loginBox").mouseenter(function () {
	replaceTxtPwd();
});
/* 通过tabel到账号框的焦点时 */
$("#adminName").focus(function () {
	replaceTxtPwd();
});

/* 账号非空验证 */
$("#adminName").blur(function () {
	var this_val = $(this).val();
	if ($.trim(this_val) == "") {
		$(".mesgBox").text("账号不能为空！");
		return;
	} else {
		$(".mesgBox").text("");
	}
});

/* 密码非空验证 */
$(document).on("blur", ".pwd", function () {
	var this_val = $(this).val();
	if ($.trim(this_val) == "") {
		$(".mesgBox").text("密码不能为空");
		return;
	} else {
		$(".mesgBox").text("");
	}
});

/* 非空验证示例 */
$(".btn-login").click(function () {
    var ad=$("#adminName").val();
    if(ad==''){
      $(".mesgBox").text("账号不能为空！");
		return;
    }
	$(".pwd").blur();
	var mesgTxt = $(".mesgBox").text();
	if (mesgTxt == "") {
		$("#loginfrm").submit();
	} else {
		return;
	}
});

/* 回车键触发登录功能 */
$("body").keydown(function() {
	if (event.keyCode==13) {
		$("#login").click();
	}
});

//===================================================
//					方法函数定义部分
//===================================================

/* 将页面内的明文密码框替换为普通密码框 */
function replaceTxtPwd() {
	var createPwd = '<input id="pwd" name="pwd" type="password" class="pwd" placeholder="请输入密码" />',
		hasTxtPwd = $("#pwd[type='text']").length > 0;

	if (hasTxtPwd) {
		// 移除明文密码框
		$("#pwd[type='text']").remove();

		// 创建明文密码框
		$(".login-info .formLine:eq(1)").append(createPwd);
	}

	// 执行框架定位的方法
	BASECSS.setMarginLeft();
	BASECSS.setMarginTop();
}

/* 设置背景图高度 */
function setBgHeight() {
	var winHeight = $(window).height();
	$(".login-bg").css("height", winHeight + "px");
}

/**
 * 功能： IE的placeholder效果
 * 参数：1.表格标识符
 **/
function IEPlaceholder(ident) {
	var $this = $(ident),
		this_len = $this.length;

	// 遍历出所有带有输入提示的文本框
	for (var i = 0; i < this_len; i++) {
		var thisInput = $this.eq(i),
			this_tips = thisInput.attr("placeholder"),
			this_OH = $this.outerHeight();

		// 在文本框前添加输入提示
		thisInput.before(
			'<div class="placeholder">' +
			this_tips +
			'</div>'
		);
		// 设置行高
		thisInput.prev(".placeholder").css("line-height", this_OH + "px");
	}

	// 当输入提示被点击，使它后面的文本框获得焦点
	$(document).on("click", ".placeholder", function () {
		$(this).next("input[placeholder]").focus();
	});

	// 输入或删除内容后，添加或删除错误提示
	$(document).on({
		keyup: function () {
			resetPHolder(this);
		},
		blur: function () {
			resetPHolder(this);
		},
		focus: function() {
			$(this).prev(".placeholder").remove();
		}
	}, "input[placeholder]");
}

/**
 * 功能：当内容被清空后，恢复输入提示
 * 参数：1.表格标识符
 **/
function resetPHolder(ident) {
	var thisTxt = $(ident),
		this_ph = thisTxt.attr("placeholder"),
		this_val = thisTxt.val(),
		this_OuterH = thisTxt.outerHeight();;

	// 当内容为空时
	if ($.trim(this_val) == "") {
		// 在文本框前添加输入提示
		thisTxt.before(
			'<div class="placeholder">' +
			this_ph +
			'</div>'
		);
		// 设置行高
		thisTxt.prev(".placeholder").css("line-height", this_OuterH + "px");
	}
	// 有内容则清空输入提示
	else {
		thisTxt.prev(".placeholder").remove();
	}
}
