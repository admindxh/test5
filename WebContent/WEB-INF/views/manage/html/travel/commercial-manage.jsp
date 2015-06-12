<%@ page language="java" import="java.util.*" pageEncoding="GB18030" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏-游西藏首页显示-商户管理</title>
    <%@include file="/common-html/commonxz.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
            type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-tabel.js"
            type="text/javascript"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>
            您当前位置为:
        </label>
        <span><a>游西藏</a> -</span>
        <span><a href="travel.html" target="_self">游西藏首页显示</a> -</span>
        <span><a>商户管理</a> </span>
    </div>

    <!-- 查看按钮 -->
    <div class="operManage">
        <button type="button" class="lookup btn-sure" onclick="javascript:window.open('${ctx}tourism/strage/frontIndex')">
            查看
        </button>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
        <form method="post" id="myForm">
            <!-- 商户推荐位一 { -->
            <div class="contClassify">
                <h2 class="title">
                    商户推荐位一
                </h2>
                <input type="hidden" value="" name="programaCode1"/>

                <div class="formLine mt15">
                    <label>
                        推荐位名称:
                    </label>


                    <div class="select-base ml-10">
                        <c:if test="${not empty mt1}">
                            <i class="w-110">${mt1.name}</i>
                        </c:if>
                        <c:if test="${empty mt1}">
                            <i class="w-110">全部类别</i>
                        </c:if>
                        <dl>
                            <dt inputValue="" inputName="type">
                                全部类别
                            </dt>
                            <c:forEach var="type" items="${typeList}">
                                <dt inputValue="${type.code}" inputName="mm1.merchantTypeCode">
                                        ${type.name}
                                </dt>
                            </c:forEach>
                        </dl>
                        <input id="type" type="hidden" value="${mm1.merchantTypeCode}"
                               name="mm1.merchantTypeCode"/>
                    </div>
                </div>
            </div>

            <div class="formLine">
                <label>
                    商户1链接:
                </label>
                <input id="" name="mm1.merchantUrl1" type="text"
                       class="w-260 to-link" value="${mm1.merchantUrl1}">
                <label class="w-auto">
                    或编号:
                </label>
                <input name="mm1.merchantCode1" type="text" class="w-200 number"
                       value="${mm1.merchantCode1}">
            </div>
            <div class="formLine">
                <label>
                    商户2链接:
                </label>
                <input id="" name="mm1.merchantUrl2" type="text"
                       class="w-260 to-link" value="${mm1.merchantUrl2}">
                <label class="w-auto">
                    或编号:
                </label>
                <input name="mm1.merchantCode2" type="text" class="w-200 number"
                       value="${mm1.merchantCode2}">
            </div>
            <div class="formLine">
                <label>
                    商户3链接:
                </label>
                <input id="" name="mm1.merchantUrl3" type="text"
                       class="w-260 to-link" value="${mm1.merchantUrl3}">
                <label class="w-auto">
                    或编号:
                </label>
                <input name="mm1.merchantCode3" type="text" class="w-200 number"
                       value="${mm1.merchantCode3}">
            </div>
            <div class="formLine">
                <label>
                    商户4链接:
                </label>
                <input id="" name="mm1.merchantUrl4" type="text"
                       class="w-260 to-link" value="${mm1.merchantUrl4}">
                <label class="w-auto">
                    或编号:
                </label>
                <input name="mm1.merchantCode4" type="text" class="w-200 number"
                       value="${mm1.merchantCode4}">
            </div>
    </div>
    <!-- } 商户推荐位一 -->

    <!-- 商户推荐位二 { -->
    <div class="contClassify">
        <h2 class="title">
            商户推荐位二
        </h2>
        <input type="hidden" value="" name="programaCode2"/>

        <div class="formLine mt15">
            <label>
                推荐位名称:
            </label>

            <div class="select-base ml-10">
                <c:if test="${not empty mt2}">
                    <i class="w-110">${mt2.name}</i>
                </c:if>
                <c:if test="${empty mt2}">
                    <i class="w-110">全部类别</i>
                </c:if>
                <dl>
                    <dt inputValue="" inputName="type">
                        全部类别
                    </dt>
                    <c:forEach var="type" items="${typeList}">
                        <dt inputValue="${type.code}" inputName="mm2.merchantTypeCode">
                                ${type.name}
                        </dt>
                    </c:forEach>
                </dl>
                <input id="type" type="hidden" value="${mm2.merchantTypeCode}"
                       name="mm2.merchantTypeCode"/>
            </div>
        </div>

        <div class="formLine">
            <label>
                商户1链接:
            </label>
            <input id="" name="mm2.merchantUrl1" type="text"
                   class="w-260 to-link" value="${mm2.merchantUrl1}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm2.merchantCode1" type="text" class="w-200 number"
                   value="${mm2.merchantCode1}">
        </div>
        <div class="formLine">
            <label>
                商户2链接:
            </label>
            <input id="" name="mm2.merchantUrl2" type="text"
                   class="w-260 to-link" value="${mm2.merchantUrl2}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm2.merchantCode2" type="text" class="w-200 number"
                   value="${mm2.merchantCode2}">
        </div>
        <div class="formLine">
            <label>
                商户3链接:
            </label>
            <input id="" name="mm2.merchantUrl3" type="text"
                   class="w-260 to-link" value="${mm2.merchantUrl3}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm2.merchantCode3" type="text" class="w-200 number"
                   value="${mm2.merchantCode3}">
        </div>
        <div class="formLine">
            <label>
                商户4链接:
            </label>
            <input id="" name="mm2.merchantUrl4" type="text"
                   class="w-260 to-link" value="${mm2.merchantUrl4}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm2.merchantCode4" type="text" class="w-200 number"
                   value="${mm2.merchantCode4}">
        </div>
    </div>
    <!-- } 商户推荐位二 -->

    <!-- 商户推荐位三 { -->
    <div class="contClassify">
        <h2 class="title">
            商户推荐位三
        </h2>
        <input type="hidden" value="" name="programaCode3"/>

        <div class="formLine mt15">
            <label>
                推荐位名称:
            </label>

            <div class="select-base ml-10">
                <c:if test="${not empty mt3}">
                    <i class="w-110">${mt3.name}</i>
                </c:if>
                <c:if test="${empty mt3}">
                    <i class="w-110">全部类别</i>
                </c:if>
                <dl>
                    <dt inputValue="" inputName="type">
                        全部类别
                    </dt>
                    <c:forEach var="type" items="${typeList}">
                        <dt inputValue="${type.code}" inputName="mm3.merchantTypeCode">
                                ${type.name}
                        </dt>
                    </c:forEach>
                </dl>
                <input id="type" type="hidden" value="${mm3.merchantTypeCode}"
                       name="mm3.merchantTypeCode"/>
            </div>
        </div>

        <div class="formLine">
            <label>
                商户1链接:
            </label>
            <input id="" name="mm3.merchantUrl1" type="text"
                   class="w-260 to-link" value="${mm3.merchantUrl1}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm3.merchantCode1" type="text" class="w-200 number"
                   value="${mm3.merchantCode1}">
        </div>
        <div class="formLine">
            <label>
                商户2链接:
            </label>
            <input id="" name="mm3.merchantUrl2" type="text"
                   class="w-260 to-link" value="${mm3.merchantUrl2}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm3.merchantCode2" type="text" class="w-200 number"
                   value="${mm3.merchantCode2}">
        </div>
        <div class="formLine">
            <label>
                商户3链接:
            </label>
            <input id="" name="mm3.merchantUrl3" type="text"
                   class="w-260 to-link" value="${mm3.merchantUrl3}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm3.merchantCode3" type="text" class="w-200 number"
                   value="${mm3.merchantCode3}">
        </div>
        <div class="formLine">
            <label>
                商户4链接:
            </label>
            <input id="" name="mm3.merchantUrl4" type="text"
                   class="w-260 to-link" value="${mm3.merchantUrl4}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm3.merchantCode4" type="text" class="w-200 number"
                   value="${mm3.merchantCode4}">
        </div>
    </div>
    <!-- } 商户推荐位三 -->

    <!-- 商户推荐位四 { -->
    <div class="contClassify">
        <h2 class="title">
            商户推荐位四
        </h2>
        <input type="hidden" value="" name="programaCode4"/>

        <div class="formLine mt15">
            <label>
                推荐位名称:
            </label>

            <div class="select-base ml-10">
                <i class="w-112">团游</i>

                <input id="type" type="hidden" value="gt"
                       name="mm4.merchantTypeCode"/>
            </div>
        </div>

        <div class="formLine">
            <label>
                商户1链接:
            </label>
            <input id="" name="mm4.merchantUrl1" type="text"
                   class="w-260 to-link" value="${mm4.merchantUrl1}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm4.merchantCode1" type="text" class="w-200 number"
                   value="${mm4.merchantCode1}">
        </div>
        <div class="formLine">
            <label>
                商户2链接:
            </label>
            <input id="" name="mm4.merchantUrl2" type="text"
                   class="w-260 to-link" value="${mm4.merchantUrl2}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm4.merchantCode2" type="text" class="w-200 number"
                   value="${mm4.merchantCode2}">
        </div>
        <div class="formLine">
            <label>
                商户3链接:
            </label>
            <input id="" name="mm4.merchantUrl3" type="text"
                   class="w-260 to-link" value="${mm4.merchantUrl3}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm4.merchantCode3" type="text" class="w-200 number"
                   value="${mm4.merchantCode3}">
        </div>
        <div class="formLine">
            <label>
                商户4链接:
            </label>
            <input id="" name="mm4.merchantUrl4" type="text"
                   class="w-260 to-link" value="${mm4.merchantUrl4}">
            <label class="w-auto">
                或编号:
            </label>
            <input name="mm4.merchantCode4" type="text" class="w-200 number"
                   value="${mm4.merchantCode4}">
        </div>
    </div>
    <!-- } 商户推荐位四 -->

    <!-- 操作 -->
    <div class="formLine">
        <div class="saveOper">
            <button type="button" class="btn-sure mr30" onclick="preView()">预览</button>
            <button type="submit" class="save btn-sure mr100" onclick="save()">保存</button>
        </div>
    </div>
    </form>
</div>
<!-- } 模块管理 -->
</div>
<!-- } main -->
<script src="${ctxManage}/resources/js/dataValidation.js"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
<script>
    function preView() {
        $("#myForm").attr("action", "intoFrontTourmMerchantPrev");
        $("#myForm").attr("target", "_blank");
        $("#myForm").submit();
    }
    $(".to-link").blur(function () {
        var thisVal = $(this).val(),
                nextVal = $(this).siblings('.number').val(),
                nextValLen = nextVal.length > 0;
        var regTest = $.VLDTOR.IsWebUrl(thisVal),
                regNull = $.trim(thisVal) != "",
                thisValClip = thisVal.split('code=')[1];
        if (nextValLen) {
            valid_txtBox_create(this, regTest, '不能为中文或空格', 'top');
            if (regTest && regNull) {
                if (thisValClip) {
                    $(this).siblings('.number').val(thisValClip);
                }
            }
        } else {
            valid_txtBox_create(this, regTest && regNull, '不能为中文或空格,且与编号必填一项', 'top');
            if (regTest && regNull) {
                $(this).siblings('.number').next('span.errMesg').remove();
                if (thisValClip) {
                    $(this).siblings('.number').val(thisValClip);
                }
            }
        }
    });
    function save(){
    	 $.ajax( {
    			url : "saveMerchantManage",
    			dataType : "json",
    			type:"post",
    			data : $("#myForm").serialize(),
    			async : false,
    			success : function(data) {
        			alert(data.msg);
    				location.href="${ctx}/web/merchant/gotoMerchantManage";
    			}
    		}); 
    }

</script>
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>
</html>

