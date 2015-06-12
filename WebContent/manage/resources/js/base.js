/**
 * 功能：公共功能库 日期：2014年11月25日 作者：weiye
 */

// ===================================================
// 					全局对象和变量定义部分
// ===================================================
var BASEGLOBAL = {}

// ===================================================
// 					页面加载完成后执行部分
// ===================================================
$(function () {
    /* 根据载入的子页面名称设置父页导航的选中效果 */
    setParentNavChecked();
    /* 页面载入时判断自定义下拉选框的内容，从而设置字体颜色 */
    setCustomSelectColor();

    /* 清除单、复选框在FireFox浏览器中点击后的虚线边框 */
    $(document).on("focus", "input[type='checkbox'], input[type='radio']",
        function () {
            $(this).blur();
        });

    /**** 响应式设置 ****/
    var win_W = $(window).width();
    // 实际水平像素1024
    if (win_W < 839) {

    }
});

// ===================================================
// 					事件绑定及触发部分
// ===================================================

/** ** 自定义下拉选择菜单功能集 *** */
// 显示或隐藏选择菜单
$(document).on("click", ".select-base i", function () {
    var $this = $(this), dlStatus = $this.next("dl").css("display");
    // 隐藏
    if (dlStatus == "block") {
        select_base.hide_iClick(this);
    }
    // 显示
    else {
        select_base.show(this);
    }
});
// 隐藏选择菜单(鼠标移开)
$(document).on("mouseleave", ".select-base", function () {
    select_base.hide_mouseleave(this);
});
// 选择内容，并隐藏选择菜单(点击)
$(document).on("click", ".select-base dt", function () {
    select_base.select(this);
    select_base.hide_click(this);
    $(this).parent("dl").prevAll("i").css("color", "#666");
});

// ===================================================
// 					方法函数定义部分
// ===================================================

/* 根据载入的子页面名称设置父页导航的选中效果 */
function setParentNavChecked() {
    // 获取父页面文档和当前页面的位置链接（分三级）
    var pDoc = $(window.parent.document), nav_loc = $(".location span"), lv1_aTxt = nav_loc
            .eq(0).children("a").text(), lv2_aTxt = nav_loc.eq(1).children("a")
            .text(), lv3_aTxt = nav_loc.eq(2).children("a").text(),
    // 父页面主导航和总个数
        main_nav = pDoc.find(".main_nav").children("a"), main_nav_len = main_nav.length,
    // 父页面副导航和总个数
        minor_nav = pDoc.find(".main_left .minor_nav"), minor_nav_len = minor_nav.length,
    // 导航游标
        navNonius = pDoc.find(".main_left .navNonius");
    // 为系统首页
    if (lv1_aTxt == "后台首页") {
        // 清除主导航内的选中样式，并隐藏游标
        main_nav.removeClass("checked");
        minor_nav.find("a.checked").removeClass("checked");
        // 选中“home”主页键
        pDoc.find(".adminHome a").addClass("checked");
        return;
    }
    else if (lv1_aTxt == "系统操作员修改密码" || lv1_aTxt == "后台首页") {
        navNonius.hide();
    }
    else {
        navNonius.show();
    }

    // 根据当前页面的1级超链接选中父页面顶部的超链接按钮
    for (var i = 0; i < main_nav_len; i++) {
        // 获取主导航按钮及文本
        var thisMainNav = main_nav.eq(i), thisMainNav_aTxt = thisMainNav.text();

        // 匹配到指定相同的超链接内容时
        if ($.trim(lv1_aTxt) == $.trim(thisMainNav_aTxt)) {

            // 为主导航指定按钮加上选中效果,并清除其它按钮的选中效果
            thisMainNav.addClass("checked").siblings().removeClass("checked");

            // 显示对应的副导航，隐藏其它副导航
            minor_nav.eq(i).siblings(".minor_nav").addClass("disp-n").end().removeClass("disp-n");

            // 副导航1级超链接及个数
            var minor_lev1_nav = minor_nav.eq(i).find("li > a"), minor_lev1_nav_len = minor_lev1_nav.length, lev1_nav_OH = minor_lev1_nav
                .outerHeight();

            // 根据当前页位置的2级项选中父页面副导航的1级项
            for (var j = 0; j < minor_lev1_nav_len; j++) {

                // 获取父页面副导航的1级项及文本
                var thisMinorLv1 = minor_lev1_nav.eq(j), thisMinorLv1_aTxt = thisMinorLv1.text();

                // 匹配二级超链接
                if ($.trim(lv2_aTxt) == $.trim(thisMinorLv1_aTxt)) {

                    // 清除所有副导航一级项的选中效果,并为副导航指定按钮加上选中效果
                    minor_lev1_nav.removeClass("checked");
                    thisMinorLv1.addClass("checked");

                    // 设置游标位置
                    var thisNav_OT = thisMinorLv1.offset().top;
                    navNonius.css("top", (thisNav_OT + (lev1_nav_OH - 26) / 2) + "px");

                    // 若该项含有二级项，则展开
                    if (thisMinorLv1.nextAll("div").length == 1) {

                        // 隐藏所有二级项，将箭头设为折叠状
                        minor_lev1_nav.nextAll("div").addClass("disp-n");
                        minor_lev1_nav.next("i.down").removeClass("down").addClass("right");

                        // 显示当前页对应的导航项，将箭头设为展开状
                        thisMinorLv1.nextAll("div.disp-n")
                            .removeClass("disp-n");
                        thisMinorLv1.next("i.right").removeClass("right").addClass("down");

                        // 副导航2级超链接及个数
                        var minor_lev2_nav = minor_lev1_nav.eq(j)
                            .nextAll("div").children("a"), minor_lev2_nav_len = minor_lev2_nav.length;

                        // 根据当前页位置的3级项选中父页面副导航的2级项
                        for (var k = 0; k < minor_lev2_nav_len; k++) {

                            // 获取父页面副导航的2级项及文本
                            var thisMinorLv2 = minor_lev2_nav.eq(k), thisMinorLv2_aTxt = thisMinorLv2
                                .text();

                            // 如果3级项为空
                            if ($.trim(lv3_aTxt) == "") {

                                // 移除的3级项被选中效果
                                minor_nav.find("li div > a").removeClass(
                                    "checked");
                                return;
                            }
                            // 将副导航匹配的2级项选中
                            else if ($.trim(lv3_aTxt) == $
                                    .trim(thisMinorLv2_aTxt)) {

                                // 移除的3级项被选中效果
                                minor_nav.find("li div > a").removeClass(
                                    "checked");

                                // 为指定项添加选中效果
                                thisMinorLv2.addClass("checked");
                                return;
                            }
                        }
                    }
                    return;
                }
            }
            return;
        }
    }
}

/* 自定义下拉选择菜单功能 */
var select_base = {
    // 显示选择项
    show: function (ident) {
        var $this = $(ident), parent_W = $this.outerWidth();
        $this.next("dl").css("width", (parent_W - 2) + "px").slideDown(120);
    },
    // 隐藏选择项(选择项点击)
    hide_click: function (ident) {
        var $this = $(ident);
        $this.parent("dl").slideUp(200);
    },
    // 隐藏选择项(显示框点击)
    hide_iClick: function (ident) {
        var $this = $(ident);
        // $this.next("dl").slideUp(200);
    },
    // 隐藏选择项(鼠标移开)
    hide_mouseleave: function (ident) {
        var $this = $(ident);
        $this.children("dl").slideUp(200);
    },
    // 选择内容
    select: function (ident) {
        var $this = $(ident), this_val = $this.text(), this_name = $this
            .attr("name");
        $this.parent("dl").prev("i").text(this_val).end().slideUp(200);
        $this.parent().prevAll("input[type='hidden']").attr("value", this_name);
    }
};

/**
 * 功能：于父框架弹出框 日期：2014年12月4日 参数：1.弹出框HTML内容（'弹出框内容'）；2.弹出框HTML按钮组（按钮组）
 */
function parentPopup(pb_cont, pb_btn) {
    var parentWin = $(window.parent.document), pw_pl = parentWin
        .find(".popupLayer"), pw_pl_pb = pw_pl.children(".maskLayer")
        .children(".popupBox");

    // 信息内容
    pw_pl_pb.children(".popupBox-content").html(pb_cont);

    // 按钮
    pw_pl_pb.children(".popupBox-button").html(pb_btn);
    pw_pl.show();

    // 获取父框架窗口的高度、弹出框的实际宽和高
    var pw_H = $(window.parent.document).height(), popBox_OW = pw_pl_pb
        .outerWidth(), popBox_OH = pw_pl_pb.outerHeight();
    // 进行CSS设置偏移定位
    pw_pl_pb.css({
        "position": "obsolute",
        "top": (pw_H / 2) + "px",
        "left": "50%",
        "margin-left": -(popBox_OW / 2) + "px",
        "margin-top": -(popBox_OH / 2) + "px"
    });
}

/**
 * 功能：弹出框+父页面弹出层 日期：2014年12月4日 作者：weiye
 * 参数：1.头部标题HTML内容；2.弹出框HTML内容；3.弹出框底部HTML内容
 */
function popupLayer(top, cont, btom) {
    var maskLayer_len = $(".maskLayer").length,
        popupBox_len = $(".popupBox").length;

    // 如果弹出框不存在
    if(maskLayer_len == 0 && popupBox_len == 0) {
        // 处理可能未定义的情况
        top = top == undefined ? "" : top;
        cont = cont == undefined ? "" : cont;
        btom = btom == undefined ? "" : btom;

        // 定义一个弹出框的基本样式和自定义的内容
        var popup = '<div class="popupLayer">'
                + '<div class="maskLayer"></div><div class="popupBox"><div class="popupBox-top">'
                + top + '</div><div class="popupBox-content">' + cont
                + '</div><div class="popupBox-bottom">' + btom
                + '</div></div></div>',

        // 父框架相关元素
        parentWin = $(window.parent.document), pWin_main_W = parentWin
            .find(".main").width(), pWin_main_H = parentWin.find(".main")
            .height(), win_W = $(window).width(),

        // 父框架半遮罩层
        maskLayer_part = '<div class="maskLayer-top"></div><div class="maskLayer-left"></div><div class="maskLayer-right"></div><div class="maskLayer-bottom"></div>';

        // 为页面加入该弹出框
        $("body").append(popup);

        // 为父页面添加半遮罩层
        parentWin.find("body").append(maskLayer_part);

        // 获取当前页面的宽度
        var window_W = $(window).width();
        // 页面水平像素小于839(屏幕像素1024)
        if (window_W < 839) {
            // 设置部分部分遮罩层的大小
            parentWin.find(".maskLayer-right").css("width", (pWin_main_W - win_W - 185) + "px");
        }
        // 页面水平像素在840-1095(屏幕像素1025-1280)
        else if (window_W >= 840 && window_W <= 1095) {
            // 设置部分部分遮罩层的大小
            parentWin.find(".maskLayer-right").css("width", (pWin_main_W - win_W - 185) + "px");
        }
        // 页面水平像素在1096-1219(屏幕像素1281-1440)
        else if (window_W >= 1096 && window_W <= 1219) {
            // 设置部分部分遮罩层的大小
            parentWin.find(".maskLayer-right").css("width", (pWin_main_W - win_W - 221) + "px");
        }
        // 页面水平像素大于1219(屏幕像素1440)
        else {
            // 设置部分部分遮罩层的大小
            parentWin.find(".maskLayer-right").css("width", (pWin_main_W - win_W - 241) + "px");
        }

        parentWin.find(".maskLayer-left, .maskLayer-right").css("height",
            pWin_main_H + "px");

        // 居中
        parentCentered(".popupLayer .popupBox");
    }
    else {
        return;
    }
    // 弹出框关闭
    $(document).on("click", ".popupBox-bottom .cancel", function () {
        closePopup();
    });
}

/**
 * 功能：显示缩略图+父子页面遮罩层组 日期：2014年12月4日 作者：weiye
 * 参数：1.头部标题HTML内容；2.弹出框HTML内容；3.弹出框底部HTML内容
 */
function popupImg(img) {

    // 处理可能未定义的情况
    img = img == undefined ? "未定义图片" : img;

    // 定义一个弹出框的基本样式和自定义的内容
    var popup = '<div class="popupImg">' + '<div class="maskLayer"></div>'
            + '<div class="imgDiv">' + '<div class="closeImgBox"></div>'
            + '<div class="imgBox">' + img + '</div></div></div>',

    // 父框架相关元素
        parentWin = $(window.parent.document), pWin_main_W = parentWin
            .find(".main").width(), pWin_main_H = parentWin.find(".main")
            .height(), win_W = $(window).width(),

    // 父框架半遮罩层
        maskLayer_part = '<div class="maskLayer-top"></div><div class="maskLayer-left"></div><div class="maskLayer-right"></div><div class="maskLayer-bottom"></div>';

    // 为页面加入该弹出框
    $("body").append(popup);

    // 为父页面添加半遮罩层
    parentWin.find("body").append(maskLayer_part);

    // 设置部分部分遮罩层的大小
    parentWin.find(".maskLayer-right").css("width",
        (pWin_main_W - win_W - 241) + "px");
    parentWin.find(".maskLayer-left, .maskLayer-right").css("height",
        pWin_main_H + "px");

    // 居中
    parentCentered(".popupImg .imgDiv");

    // 弹出框关闭
    $(document).on("click", ".popupImg .closeImgBox", function () {
        closeImg();
    });
}

/**
 * 功能：自定义确定弹出框 参数：1.需要提示的文本内容；2.回调函数 说明：该方法返回的是一个布尔值，用于事件判断
 */
function confirm_custom(deleStyle, callback) {
    // 调用弹出框方法
    popupLayer(
        '',
        '<div class="fontStyle_1">' + deleStyle + '</div>',
        '<button type="button" class="btn-sure confirm_sure mr35 mb5">确定</button>' + '<button type="button" class="btn-sure confirm_cancel mb5">取消</button>');

    // “确认”按钮绑定
    $(document).on("click", ".confirm_sure", function () {
        callback.apply(this)
        closePopup();
        return true;
    });

    // “取消”按钮绑定
    $(document).on("click", ".confirm_cancel", function () {
        closePopup();
        return false;
    });
}

/* 页面内弹出框关闭方法 */
function closePopup() {
    var pWin = $(window.parent.document);

    // 移除该弹出框
    $(".popupLayer").remove();

    // 移除父框架的半遮罩层
    pWin.find(".maskLayer-top, .maskLayer-left, .maskLayer-right, .maskLayer-bottom").remove();
}

/* 页面内缩略图关闭方法 */
function closeImg() {
    var pWin = $(window.parent.document);

    // 移除该弹出框
    // $(".popupImg").remove();
    $(".popupImg").slideUp(300, function () {
        $(this).remove();
    });

    // 移除父框架的半遮罩层
    pWin
        .find(
        ".maskLayer-top, .maskLayer-left, .maskLayer-right, .maskLayer-bottom")
        .remove();
}

/**
 * 功能：消息盒子 日期：2014年12月11日
 * 参数：1.HTML内容；2.图标类型：none(无)、info（蓝色信息图标）、pass（绿勾）、erro(红叉)，缺省为“info”；3.显示时间，缺省为1600毫秒
 */
function msgBox(msgContent, icons, timeSet) {
    // 图标类型设置
    var iconType;
    switch (icons) {
        case "none":
            iconType = "icon-none";
            break;
        case "info":
            iconType = "icon-info";
            break;
        case "pass":
            iconType = "icon-pass";
            break;
        case "erro":
            iconType = "icon-erro";
            break;
        default:
            iconType = "icon-info";
    }

    // 盒子内容设置
    var msgBox = '<div class="msgBox">' + '<i class="' + iconType + '"></i>'
        + msgContent + '</div>';

    // 为页面加入消息盒子
    $("body").append(msgBox);

    // 定位
    parentCentered(".msgBox");

    // 默认为1600毫秒，若设置则按照设置的时间显示
    timeSet = timeSet == undefined ? 1600 : timeSet;
    // 显示指定时间后消除该盒子
    setTimeout("$('.msgBox').fadeOut(300, function() { $(this).remove(); })",
        timeSet);

}

/**
 * 功能：相对父框架的居中定位 日期：2014年12月15日 参数：1.需定位容器标识符
 */
function parentCentered(ident) {
    // 获取弹出框相应的布局属性值
    var popBox = $(ident), popBox_OW = popBox.outerWidth(), popBox_OH = popBox
        .outerHeight(), p_win = $(window.parent.document), win_W = p_win
        .width(), p_left_OW = p_win.find(".main_left").outerWidth(), p_right_H = p_win
        .find(".main_right").height(), win_Scroll_T = $(window).scrollTop();

    // 重设弹出框的位置
    $(ident).css({
        "left": (win_W / 2) + "px",
        "top": (win_Scroll_T + (p_right_H / 2)) + "px",
        "margin-top": -(popBox_OH / 2) + "px",
        "margin-left": -((popBox_OW / 2) + p_left_OW) + "px"
    });
}

/**
 * 功能：表格内每行内首个单元格的左间隙 参数：1.表格标识符；2.左间隔的像素数值
 */
function setTrFirstCell_PL(ident, pixel) {
    var tab_tr = $(ident).find("tr"), tr_len = tab_tr.length;

    // 为每个tr的第一个td加上左间隙
    for (var i = 0; i < tr_len; i++) {
        tab_tr.eq(i).children("td:first, th:first").css("padding-left",
            pixel + "px");
    }
}

/* 页面载入时判断自定义下拉选框的内容，从而设置字体颜色 */
function setCustomSelectColor() {
    var sltBase = $(".select-base"), sltBase_len = sltBase.length;
    // 为默认的值则浅色字体，否则为深色字体
    for (var i = 0; i < sltBase_len; i++) {
        var thisVal = sltBase.eq(i).children("i"), thisTxt = thisVal.text(), defVal = thisTxt
            .substr(0, 3);
        if (defVal == "请选择" || defVal == "按年份") {
            thisVal.css("color", "#bcbcbc");
        } else {
            thisVal.css("color", "#666");
        }
    }
}

/**
 * 功能：表格内每行内首个单元格的左间隙 参数：1.表格标识符；2.左间隔的像素数值
 * 参数：容器标识符
 */
function dataLoading(ident) {
    var loadEle = '<div class="dataLoading">' +
        '<div class="loaddingIcon"></div>' +
        '</div>';
    var hasLoading = $(".dataLoading").length > 0;
    if(!hasLoading) {
        $(ident).append(loadEle);
    }
}
/* 消除等待效果 */
function closeLoading(ident) {
    $(ident).find(".dataLoading").remove();
}

// ===================================================
// 					响应式设置
// ===================================================
