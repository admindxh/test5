/**
 * 功能：角色权限添加与修改功能
 * 日期：2014年12月28日
 * 作者：weiye
 **/

//===================================================
//				页面加载完成后执行部分
//===================================================
//$(function () {
//
//});

//===================================================
//				  事件绑定及触发部分
//===================================================

/**
 *
 * <结构说明>：
 * 1.复选框分为4层，部分只有3层；
 * 2.选中父节点，其下面的子节点需全部选中；
 * 3.若子节点选中（非所有子节点全部选中），则父节点和祖先处于部分选中状态；
 * 4.若子节点及同一级的所有子节点已全部选中，则直接父节会处于选中状态
 *
 **/

/* 自定义复选框关联型选择功能 */
$(document).on("click", ".auth_item", function () {
	// 点击父节点选中/取消选中子节点
	toggleCheckAllChild(this);

	// 自定义复选框基本选择功能
	base_customCkb(this);

	// 通过点击子节点级联设置父节点的选中状态
	cascadeSetCheckStatus(this);
});

//===================================================
//					方法函数定义部分
//===================================================

/**
 * 功能：自定义复选框基本选择功能
 * 参数：1.元素标识符
 **/
function base_customCkb(ident) {
	var $this = $(ident),
		ckb = $this.children("input[type='checkbox']"),
		custom_ckb = $this.children("i.custom_ckb"),
		isNone = custom_ckb.hasClass("none"),
		isPart = custom_ckb.hasClass("part"),
		isChecked = custom_ckb.hasClass("checked");
	// 未选中或部分选中
	if (isNone || isPart) {
		custom_ckb.removeClass("none part").addClass("checked");
		ckb.prop("checked", "checked");
	}
	// 已选中
	else {
		custom_ckb.removeClass("checked").addClass("none");
		ckb.prop("checked", false);
	}
}

/**
 * 功能：点击父节点选中/取消选中子节点
 * 参数：1.元素标识符
 **/
function toggleCheckAllChild(ident) {
	var $this = $(ident),
		ckb = $this.children("input[type='checkbox']"),
		ckbName = ckb.attr("class"),
		thisChild = $("[class^='" + ckbName + "-']"),
		custom_ckb = $this.children("i.custom_ckb"),
		isNone = custom_ckb.hasClass("none"),
		isPart = custom_ckb.hasClass("part"),
		isChecked = custom_ckb.hasClass("checked");

	// 该父节点处于未选中或部分选中状态时
	if (isNone || isPart) {
		// 将子节点设为选中状态（自定义复选框）
		thisChild.next("i.custom_ckb").removeClass("none part").addClass("checked");

		// 选中该name下的所有子节点
		thisChild.prop("checked", "checked");
	}
	// 该父节点处于选中状态时
	else {
		// 将子节点设未选中状态（自定义复选框）
		thisChild.next("i.custom_ckb").removeClass("checked").addClass("none");

		// 取消该name下的所有子节点的选中
		thisChild.prop("checked", false);
	}
}

/**
 * 功能：通过点击子节点级联设置父节点的选中状态
 * 参数：1.元素标识符
 **/
function cascadeSetCheckStatus(ident) {
	// 该选项的基本子元素
	var $this = $(ident),
		ckb = $this.children("input[type='checkbox']");

	// 该选项的name属性相关值
	var ckb_name = ckb.attr("class"),
		ckb_name_spl = ckb_name.split("-"),
		ckb_name_spl_len = ckb_name_spl.length;

	//=============================================================
	//				  			  为LV4
	//=============================================================
	if (ckb_name_spl_len == 4) {

		//=======================================================
		//				  根据lv4的选中情况设置lv3
		//=======================================================

		// 拼接出直接父节点lv3的name值，并直接获取其input元素
		var parent_name = ckb_name_spl[0] + "-" + ckb_name_spl[1] + "-" + ckb_name_spl[2],
			this_parent = $("[class='" + parent_name + "']"),
			// 获得该级兄弟元素的选中情况
			thisTd = $this.parents("td"),
			hasNone = thisTd.find(".none").length > 0,
			hasChecked = thisTd.find(".checked").length > 0;

		// 部分选中
		if (hasNone && hasChecked) {
			// 显示自定义的部分选中效果
			this_parent.next(".custom_ckb").removeClass("none checked").addClass("part");
			// 设置input为选中
			this_parent.prop("checked", "checked");
		}
		// 全部选中
		else if (!hasNone) {
			// 显示自定义的全选中效果
			this_parent.next(".custom_ckb").removeClass("none part").addClass("checked");
			this_parent.prop("checked", "checked");
		}
		// 全未选中
		else if (!hasChecked) {
			// 显示自定义的未选中效果
			this_parent.next(".custom_ckb").removeClass("checked part").addClass("none");
			// 设置input为未选中
			this_parent.prop("checked", false);
		}

		//=======================================================
		//				  根据lv3的选中情况设置lv2
		//=======================================================

		// 拼接出lv3的全部包含name值及相关值
		var parents_cludName = ckb_name_spl[0] + "-" + ckb_name_spl[1] + "-",
			this_parents = $("[class^='" + parents_cludName + "']"),
			// lev2
			lev2_name = ckb_name_spl[0] + "-" + ckb_name_spl[1],
			this_lev2 = $("[class='" + lev2_name + "']"),
			hasNone_lv3 = this_parents.next(".custom_ckb").hasClass("none"),
			hasPart_lv3 = this_parents.next(".custom_ckb").hasClass("part"),
			hasChecked_lv3 = this_parents.next(".custom_ckb").hasClass("checked");

		// 部分选中
		if ((hasNone_lv3 && hasPart_lv3 && hasChecked_lv3) || (hasNone_lv3 && hasChecked_lv3)) {
			this_lev2.next(".custom_ckb").removeClass("none checked").addClass("part");
			this_lev2.prop("checked", "checked");
		}
		// 全部选中
		else if (!hasNone_lv3 && !hasPart_lv3) {
			this_lev2.next(".custom_ckb").removeClass("none part").addClass("checked");
			this_lev2.prop("checked", "checked");
		}
		// 全部未选中
		else if (!hasPart_lv3 && !hasChecked_lv3) {
			this_lev2.next(".custom_ckb").removeClass("part checked").addClass("none");
			this_lev2.prop("checked", false);
		}

		//=======================================================
		//				  根据lv2的选中情况设置lv1
		//=======================================================
		// 拼接出lv1的全部包含name值及相关值
		var lv1_name = ckb_name_spl[0],
			this_lev1 = $("[class='" + lv1_name + "']"),
			clud_lev2_name = $("[class^='" + lv1_name + "-']"),
			hasNone_lv2 = clud_lev2_name.next(".custom_ckb").hasClass("none"),
			hasPart_lv2 = clud_lev2_name.next(".custom_ckb").hasClass("part"),
			hasChecked_lv2 = clud_lev2_name.next(".custom_ckb").hasClass("checked");

		// 部分选中
		if ((hasNone_lv2 && hasPart_lv2 && hasChecked_lv2) || (hasNone_lv2 && hasChecked_lv2)) {
			this_lev1.next(".custom_ckb").removeClass("none checked").addClass("part");
			this_lev1.prop("checked", "checked");
		}
		// 全部选中
		else if (!hasNone_lv2 && !hasPart_lv2) {
			this_lev1.next(".custom_ckb").removeClass("none part").addClass("checked");
			this_lev1.prop("checked", "checked");
		}
		// 全部未选中
		else if (!hasPart_lv2 && !hasChecked_lv2) {
			this_lev1.next(".custom_ckb").removeClass("part checked").addClass("none");
			this_lev1.prop("checked", false);
		}
	}

	//=============================================================
	//				  			  为LV3
	//=============================================================
	if (ckb_name_spl_len == 3) {

		//=======================================================
		//				  根据lv3的选中情况设置lv2
		//=======================================================

		// 拼接出lv3的全部包含name值及相关值
		var lve3_cludName = ckb_name_spl[0] + "-" + ckb_name_spl[1] + "-",
			lve3_name = ckb_name_spl[0] + "-" + ckb_name_spl[1] + "-" + ckb_name_spl[0],
			lev3_clud = $("[class^='" + lve3_cludName + "']"),
			lev3 = $("[class^='" + lve3_name + "']"),
			// lev2
			lev2_name = ckb_name_spl[0] + "-" + ckb_name_spl[1],
			lev2 = $("[class='" + lev2_name + "']"),
			hasNone_lv3 = lev3_clud.next(".custom_ckb").hasClass("none"),
			hasPart_lv3 = lev3_clud.next(".custom_ckb").hasClass("part"),
			hasChecked_lv3 = lev3_clud.next(".custom_ckb").hasClass("checked");

		// 部分选中
		if ((hasNone_lv3 && hasPart_lv3 && hasChecked_lv3) || (hasNone_lv3 && hasChecked_lv3)) {
			lev2.next(".custom_ckb").removeClass("none checked").addClass("part");
			lev2.prop("checked", "checked");
		}
		// 全部选中
		else if (!hasNone_lv3 && !hasPart_lv3) {
			lev2.next(".custom_ckb").removeClass("none part").addClass("checked");
			lev2.prop("checked", "checked");
		}
		// 全部未选中
		else if (!hasPart_lv3 && !hasChecked_lv3) {
			lev2.next(".custom_ckb").removeClass("part checked").addClass("none");
			lev2.prop("checked", false);
		}

		//=======================================================
		//				  根据lv2的选中情况设置lv1
		//=======================================================

		// 拼接出lv3的全部包含name值及相关值
		var lve2_cludName = ckb_name_spl[0] + "-",
			lve2_name = ckb_name_spl[0] + "-" + ckb_name_spl[1],
			lev2_clud = $("[class^='" + lve2_cludName + "']"),
			lev2 = $("[class^='" + lve2_name + "']"),
			// lev1
			lev1_name = ckb_name_spl[0],
			lev1 = $("[class='" + lev1_name + "']"),
			hasNone_lv2 = lev2_clud.next(".custom_ckb").hasClass("none"),
			hasPart_lv2 = lev2_clud.next(".custom_ckb").hasClass("part"),
			hasChecked_lv2 = lev2_clud.next(".custom_ckb").hasClass("checked");

		// 部分选中
		if ((hasNone_lv2 && hasPart_lv2 && hasChecked_lv2) || (hasNone_lv2 && hasChecked_lv2)) {
			lev1.next(".custom_ckb").removeClass("none checked").addClass("part");
			lev1.prop("checked", "checked");
		}
		// 全部选中
		else if (!hasNone_lv2 && !hasPart_lv2) {
			lev1.next(".custom_ckb").removeClass("none part").addClass("checked");
			lev1.prop("checked", "checked");
		}
		// 全部未选中
		else if (!hasPart_lv2 && !hasChecked_lv2) {
			lev1.next(".custom_ckb").removeClass("part checked").addClass("none");
			lev1.prop("checked", false);
		}
	}

	//=============================================================
	//				  			  为LV2
	//=============================================================
	if (ckb_name_spl_len == 2) {

		//=======================================================
		//				  根据lv2的选中情况设置lv1
		//=======================================================

		// 拼接出lv3的全部包含name值及相关值
		var lve2_cludName = ckb_name_spl[0] + "-",
			lve2_name = ckb_name_spl[0] + "-" + ckb_name_spl[1],
			lev2_clud = $("[class^='" + lve2_cludName + "']"),
			lev2 = $("[class^='" + lve2_name + "']"),
			// lev1
			lev1_name = ckb_name_spl[0],
			lev1 = $("[class='" + lev1_name + "']"),
			hasNone_lv2 = lev2_clud.next(".custom_ckb").hasClass("none"),
			hasPart_lv2 = lev2_clud.next(".custom_ckb").hasClass("part"),
			hasChecked_lv2 = lev2_clud.next(".custom_ckb").hasClass("checked");

		// 部分选中
		if ((hasNone_lv2 && hasPart_lv2 && hasChecked_lv2) || (hasNone_lv2 && hasChecked_lv2)) {
			lev1.next(".custom_ckb").removeClass("none checked").addClass("part");
			lev1.prop("checked", "checked");
		}
		// 全部选中
		else if (!hasNone_lv2 && !hasPart_lv2) {
			lev1.next(".custom_ckb").removeClass("none part").addClass("checked");
			lev1.prop("checked", "checked");
		}
		// 全部未选中
		else if (!hasPart_lv2 && !hasChecked_lv2) {
			lev1.next(".custom_ckb").removeClass("part checked").addClass("none");
			lev1.prop("checked", false);
		}
	}
}