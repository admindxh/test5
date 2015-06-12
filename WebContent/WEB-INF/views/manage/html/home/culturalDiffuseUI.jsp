<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>西藏文化传播管理</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css"/>
    <style type="text/css">
        .lookup {
            top: 0;
            right: 0;
        }
    </style>
    <script type="text/javascript">


        //显示最新
        function showNew(program, contentType) {
            window.location.href = "${ctx}/web/indexWhMgController/showNew?program=" + program + "&contentType=" + contentType;
        }
        //显示保存
        function save1(saveType, avaliable, subForm) {
            $(".show .toLink").blur();
            $(".show .number").blur();
            var regSpan = $(".show span.errMesg").length > 0;
            if (regSpan) {
                msgBox("填写的信息有误，请检查", "erro", 1000);
            } else {
                if (saveType == "preview") {
                    $("#" + subForm).attr("target", "_blank");
                } else {
                    $("#" + subForm).attr("target", "");
                }
                $("#" + subForm).attr("action", "${ctx}/web/indexWhMgController/save?saveType=" + saveType + "&avaliable=" + avaliable);
                $("#" + subForm).submit();
                if(saveType != "preview") {
                    msgBox("保存成功", "pass", 1500);
                }
            }

        }
    </script>
</head>
<body>
<!-- main { -->
<div class="main">

    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>门户首页</a> -</span>
        <span><a href="#" target="_self">西藏文化传播管理</a></span>
    </div>

    <!-- 查看按钮 -->
    <div class="searchManage">
        <a class="lookup btn-anchor" onclick="javascript:window.open('${ctx}')">查看</a>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd pos-rela">

        <!-- 推荐位选择标签 { -->
        <div class="posidTab">
            <label class="checked">音乐</label>
            <label>小说</label>
            <label>游戏</label>
            <%--					<label>其他</label>--%>
        </div>
        <!-- } 推荐位选择标签 -->

        <!-- 各模块链接设置 { -->
        <div class="posidSet filament_solid_ccc">
            <!-- 音乐 { -->
            <div class="posidSet-posid show">
                <form target="_blank" id="list1" method="post" action="${ctx}/web/indexWhMgController/save">
                    <!-- 操作按钮 -->
                    <div class="formLine mt25">
                        <div class="operManage">
                            <a class="btn-anchor" onclick="showNew('09c7cb0d-7e8c-11e4-b6ce-005056a05bc9','2010')">显示最高分</a>
                            <a class="btn-anchor" onclick="save1('preview','0','list1')">预览</a>
                            <a class="save btn-anchor mr20" onclick="save1('save','1','list1')">保存</a>
                            <input type="hidden" value="09c7cb0d-7e8c-11e4-b6ce-005056a05bc9" name="program"/>
                            <input type="hidden" value="indexyy" name="springurl"/>
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>音乐一</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list1[0].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list1[0].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list1[0].code}" name="codes" class="w-320">

                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>音乐二</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list1[1].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list1[1].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list1[1].code}" name="codes" class="w-320">
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>音乐三</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list1[2].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list1[2].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list1[2].code}" name="codes" class="w-320">
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>音乐四</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list1[3].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list1[3].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list1[3].code}" name="codes" class="w-320">
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>音乐五</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list1[4].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list1[4].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list1[4].code}" name="codes" class="w-320">
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>音乐六</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list1[5].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list1[5].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list1[5].code}" name="codes" class="w-320">
                        </div>
                    </div>
                </form>
            </div>
            <!-- } 音乐 -->
            <!-- 小说 { -->
            <div class="posidSet-mores">

                <form target="_blank" id="list2" method="post" action="${ctx}/web/indexWhMgController/save">
                    <!-- 操作按钮 -->
                    <div class="formLine mt25">
                        <div class="searchManage">
                            <a class="btn-anchor" class="btn-sure" onclick="showNew('45e0fd29-7e8c-11e4-b6ce-005056a05bc9','2020')">显示最高分</a>
                            <a class="btn-anchor" class="btn-sure" onclick="save1('preview','0','list2')">预览</a>
                            <a class="btn-anchor" class="save btn-sure mr20" onclick="save1('save','1','list2')">保存</a>
                            <input type="hidden" value="45e0fd29-7e8c-11e4-b6ce-005056a05bc9" name="program"/>
                            <input type="hidden" value="indexxs" name="springurl"/>
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>小说一</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list2[0].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list2[0].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list2[0].code}" name="codes" class="w-320">
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>小说二</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list2[1].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list2[1].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list2[1].code}" name="codes" class="w-320">
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>小说三</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list2[2].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list2[2].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list2[2].code}" name="codes" class="w-320">
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>小说四</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list2[3].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list2[3].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list2[3].code}" name="codes" class="w-320">
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>小说五</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list2[4].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list2[4].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list2[4].code}" name="codes" class="w-320">
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>小说六</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list2[5].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list2[5].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list2[5].code}" name="codes" class="w-320">
                        </div>
                    </div>

                </form>
            </div>
            <!-- } 小说 -->


            <!-- 游戏 { -->
            <div class="posidSet-history">
                <form target="_blank" id="list3" method="post" action="${ctx}/web/indexWhMgController/save">

                    <!-- 操作按钮 -->
                    <div class="formLine mt25">
                        <div class="searchManage">
                            <a class="btn-anchor" class="btn-sure" onclick="showNew('61d19785-7e8c-11e4-b6ce-005056a05bc9','2030')">显示最高分</a>
                            <a class="btn-anchor" class="btn-sure" onclick="save1('preview','0','list3')">预览</a>
                            <a class="btn-anchor" class="save btn-sure mr20" onclick="save1('save','1','list3')">保存</a>
                            <input type="hidden" value="61d19785-7e8c-11e4-b6ce-005056a05bc9" name="program"/>
                            <input type="hidden" value="indexyx" name="springurl"/>
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>游戏一</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list3[0].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list3[0].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list3[0].code}" name="codes" class="w-320">
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>游戏二</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list3[1].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list3[1].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list3[1].code}" name="codes" class="w-320">
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>游戏三</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list3[2].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list3[2].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list3[2].code}" name="codes" class="w-320">
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>游戏四</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list3[3].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list3[3].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list3[3].code}" name="codes" class="w-320">
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>游戏五</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list3[4].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list3[4].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list3[4].code}" name="codes" class="w-320">
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>游戏六</i>

                        <div class="formLine mt35">
                            <label>链接:</label>
                            <input type="text" value="${list3[5].url}" name="urls" class="w-320 toLink">
                            <label class="w_auto">或编号:</label>
                            <input type="text" value="${list3[5].number}" name="numbers" class="w-200 number">
                            <input type="hidden" value="${list3[5].code}" name="codes" class="w-320">
                        </div>
                    </div>
                </form>
            </div>


            <!-- } 其他 -->


            <%--					<div class="posidSet-history1">--%>
            <%--					<form target="_blank" id="list4" method="post"  action="${ctx}/web/indexWhMgController/save">--%>
            <%--						<!-- 操作按钮 -->--%>
            <%--						<div class="formLine mt25">--%>
            <%--							<div class="searchManage">--%>
            <%--								<button type="button" class="btn-sure"   onclick="showNew('611bs12785-7w2e8c-11e4-b6ce-005056a05bc9','2040')">显示最高分</button>--%>
            <%--								<button type="button" class="btn-sure"   onclick="save('preview','0','list4')">预览</button>--%>
            <%--								<button type="button" class="save btn-sure mr20"  onclick="save('save','1','list4')">保存</button>--%>
            <%--								<input type="hidden"  value="611bs12785-7w2e8c-11e4-b6ce-005056a05bc9" name="program" />--%>
            <%--								<input type="hidden"  value="indexother" name="springurl" />--%>
            <%--							</div>--%>
            <%--						</div>--%>
            <%--							--%>
            <%--						<div class="posidSet-unit">--%>
            <%--							<i>其他一</i>--%>
            <%----%>
            <%--							<div class="formLine mt35">--%>
            <%--								<label class="w_auto">链接:</label>--%>
            <%--					<input type="text" value="${list4[0].url}" name="urls" class="w-320">--%>
            <%--					<label class="w_auto">或编号:</label>--%>
            <%--					<input type="text" value="${list4[0].number}" name="numbers" class="w-200 number">--%>
            <%--					<input type="hidden" value="${list4[0].code}" name="codes" class="w-320">--%>
            <%--							</div>--%>
            <%--						</div>--%>
            <%--						--%>
            <%--						<div class="posidSet-unit">--%>
            <%--							<i>其他二</i>--%>
            <%--							<div class="formLine mt35">--%>
            <%--								<label class="w_auto">链接:</label>--%>
            <%--					<input type="text" value="${list4[1].url}" name="urls" class="w-320">--%>
            <%--					<label class="w_auto">或编号:</label>--%>
            <%--					<input type="text" value="${list4[1].number}" name="numbers" class="w-200 number">--%>
            <%--					<input type="hidden" value="${list4[1].code}" name="codes" class="w-320">--%>
            <%--							</div>--%>
            <%--						</div>--%>
            <%--						--%>
            <%--						<div class="posidSet-unit">--%>
            <%--							<i>其他三</i>--%>
            <%--							<div class="formLine mt35">--%>
            <%--								<label class="w_auto">链接:</label>--%>
            <%--					<input type="text" value="${list4[2].url}" name="urls" class="w-320">--%>
            <%--					<label class="w_auto">或编号:</label>--%>
            <%--					<input type="text" value="${list4[2].number}" name="numbers" class="w-200 number">--%>
            <%--					<input type="hidden" value="${list4[2].code}" name="codes" class="w-320">--%>
            <%--							</div>--%>
            <%--						</div>--%>
            <%--						--%>
            <%--						<div class="posidSet-unit">--%>
            <%--							<i>其他四</i>--%>
            <%----%>
            <%--							<div class="formLine mt35">--%>
            <%--								<label class="w_auto">链接:</label>--%>
            <%--								<input type="text" value="${list4[3].url}" name="urls" class="w-320">--%>
            <%--								<label class="w_auto">或编号:</label>--%>
            <%--								<input type="text" value="${list4[3].number}" name="numbers" class="w-200 number">--%>
            <%--								<input type="hidden" value="${list4[3].code}" name="codes" class="w-320">--%>
            <%--							</div>--%>
            <%--						</div>--%>
            <%--						<div class="posidSet-unit">--%>
            <%--							<i>其他五</i>--%>
            <%--							<div class="formLine mt35">--%>
            <%--								<label class="w_auto">链接:</label>--%>
            <%--								<input type="text" value="${list4[4].url}" name="urls" class="w-320">--%>
            <%--								<label class="w_auto">或编号:</label>--%>
            <%--								<input type="text" value="${list4[4].number}" name="numbers" class="w-200 number">--%>
            <%--								<input type="hidden" value="${list4[4].code}" name="codes" class="w-320">--%>
            <%--							</div>--%>
            <%--						</div>--%>
            <%--						<div class="posidSet-unit">--%>
            <%--							<i>其他六</i>--%>
            <%----%>
            <%--							<div class="formLine mt35">--%>
            <%--								<label class="w_auto">链接:</label>--%>
            <%--								<input type="text" value="${list4[5].url}" name="urls" class="w-320">--%>
            <%--								<label class="w_auto">或编号:</label>--%>
            <%--								<input type="text" value="${list4[5].number}" name="numbers" class="w-200 number">--%>
            <%--								<input type="hidden" value="${list4[5].code}" name="codes" class="w-320">--%>
            <%--							</div>--%>
            <%--						</div>--%>
            <%--					</form>--%>
            <%--					</div>--%>


        </div>
        <!-- } 各模块链接设置 -->
    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->


<!-- JS引用部分 -->

<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script type="text/javascript">
    /* 标签页切换 */
    $(".posidTab label").click(function () {
        var $this = $(this),
                thisIndex = $this.index();
        $this.addClass("checked").siblings().removeClass("checked");
        $(".posidSet > div").eq(thisIndex).addClass("show").siblings("div[class^='posidSet-']").removeClass("show");
    });
    /* 链接验证 */
    $(".toLink").blur(function () {
//			var thisVal = $(this).val(),
//					nextVal = $(this).siblings('.number').val(),
//					nextValLen = nextVal.length > 0;
//			var regTest = $.VLDTOR.IsWebUrl(thisVal),
//					regNull = $.trim(thisVal) != "",
//					thisValClip = thisVal.split('code=')[1];
//			if(nextValLen){
//				valid_txtBox_create(this,regTest,'不能为中文或空格','top');
//				if(regTest && regNull){
//					if(thisValClip){
//						$(this).siblings('.number').val(thisValClip);
//					}
//				}
//			}else{
//				valid_txtBox_create(this,regTest && regNull,'不能为中文或空格,且与编号必填一项','top');
//				if(regTest && regNull){
//					$(this).siblings('.number').next('span.errMesg').remove();
//					if(thisValClip){
//						$(this).siblings('.number').val(thisValClip);
//					}
//				}
//			}
        var this_val = $(this).val(),
            isNull = this_val == "",
            reg_test = $.VLDTOR.IsWebUrl(this_val);
        if(isNull || reg_test) {
            removeErrMesg(this);
        } else {
            creatErrMesg(this, "不能为中文或空格", "top");
        }
    });
    /* 编号验证 */
    $(".number").blur(function () {
//			var thisVal = $(this).val(),
//					prevVal = $(this).siblings('.toLink').val(),
//					preValLen = prevVal.length > 0;
//			var regTest = $.VLDTOR.IsEnNum(thisVal),
//					regNull = $.trim(thisVal) != "";
//			if(preValLen){
//				valid_txtBox_create(this,regTest || thisVal == "",'只能为数字、英文或空','top');
//			}else{
//				valid_txtBox_create(this,regTest && regNull,'只能为数字、英文,且与连接必填一项','top');
//				if(regTest && regNull){
//					$(this).siblings('.toLink').next('span.errMesg').remove();
//				}
//			}
        var this_val = $(this).val(),
            isNull = this_val == "",
            reg_test = $.VLDTOR.IsEnNum(this_val);
        if(isNull || reg_test) {
            removeErrMesg(this);
        } else {
            creatErrMesg(this, "只能为1-30位的数字及字母", "top");
        }
    });
</script>
<!-- 利用空闲时间预加载指定页面 -->

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>
</html>