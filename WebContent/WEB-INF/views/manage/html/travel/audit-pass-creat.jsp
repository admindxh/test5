<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
	<%@include file="/common-html/common.jsp" %>
    <title>游西藏-攻略管理-已审核的攻略-新建攻略</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
    
    <!-- 网页文本编辑器插件 -->
</head>
<body>
    <!-- main { -->
    <div class="main">
		<form id="qxglId"  action="" method="post">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>游西藏</a> -</span>
				<span><a>攻略管理</a> -</span>
				<span><a href="${ctx }/web/readTibetSgPassMgController/list" target="_self">已审核的攻略</a> -</span>
				<span><a href="#" target="_self">新建攻略</a></span>
			</div>

			<!-- 模块管理 { -->
			<div class="muduleManage details filament_solid_ddd">
				<!-- 分类信息 { -->
				<div class="contClassify">
					<h2 class="title">分类信息</h2>
					
					<div class="formLine"  >
						<label>攻略所属版块:</label>
						<div id="theirMudle"  class="select-base">
							<i class="w-140">请选择所属板块</i>
							<dl>
								<c:forEach var="program" items="${programsList}">
									<dt  inputValue="${program.code}" inputName="programaCode">${program.programaName}</dt>
								</c:forEach>
							</dl>
							<input id="proCode" type="hidden" value="" name="programaCode"/>
						</div>
						<span class="reqItem">*</span>
						
						<label>攻略类型:</label>
						<div  id="strategyType" class="select-base">
							<i class="w-140">请选择攻略类型</i>
							<dl>
								<dt inputValue="1" inputName="isOfficial" onclick="clearUserInfo('1')">官方发布</dt>
								<dt inputValue="0" inputName="isOfficial" onclick="clearUserInfo('0')">用户上传</dt>
							</dl>
							<input id="isOfficial"  maxlength="20" type="hidden" value="" name="isOfficial"/>
						</div>
						<span class="reqItem">*</span>
						<label>上传用户:</label>
						<input id="uploadAuthor" type="text" maxlength="20" value="天上西藏官方" name="authorCode" readonly="readonly">
						<span class="reqItem">*</span>
					</div>
					<div class="formLine">
						<label>相关目的地:</label>
						<div class="chooseDest formLine-middle-group">
								<c:forEach var="destination" items="${destinationList}">
									<span class="formEleGroup-squad">
										<input   value="${destination.code}"  name="dest" type="checkbox">
										<label class="lbl_check">${destination.destinationName}</label>
									</span>
								</c:forEach>
						</div>
					</div>
					<div class="destScenic formLine nowrap">
						<label class="formLine-small-group-lbl">相关景点:</label>
						<!-- 景点选择 -->
						<div class="formLine-large-group">
								<c:forEach var="desandvo" items="${desandviewList}">
							<div class="formLine-small-group hide">
								<span class="region">${desandvo.desName}</span>
								<span class="scenic">
									<c:forEach  var="viewinfo" items="${desandvo.views}">
										<span class="formEleGroup-squad">
										<input name="vies" value="${viewinfo[1]}" type="checkbox">
										<label class="lbl_check">${viewinfo[0]}</label>
									</span>
									</c:forEach>
								</span>
							  </div>
							</c:forEach>
						</div><!-- } 景点选择 -->
					</div>
				</div><!-- } 分类信息 -->
				
				<!-- 详细信息 { -->
				<div class="contClassify">
					<h2 class="title">详细信息</h2>
					
					<div class="formLine">
						<label>攻略标题:</label>
						<input id="strategyTitle" type="text" name="contentTitle" maxlength="30" class="w-260">
						<span class="reqItem">*</span>
					</div>
					
					<!-- 网页文本编辑器 -->
					<div class="formLine">
						<label  class="ueEditor_lbl">攻略详细:</label>
								<script id="content" class="ueEditor_cont" name="content"  style="height:500px;" type="text/plain"></script>
								<span class="reqItem">*</span>
					</div>
				</div><!-- } 详细信息 -->
				
				<!-- 运营数据 { -->
				<div class="contClassify">
					<h2 class="title">运营数据</h2>
					
					<div class="formLine">
						<label>收藏数:</label>
						<input id="collect_num"  type="text" name="falseFavoriteNum" maxlength="20">
						<label>查看数:</label>
						<input id="lookup_num" type="text" name="falseViewcount" maxlength="20">
						<label>被赞数:</label>
						<input  id="praise_num" type="text" name="falsePraise" maxlength="20">
					</div>
				</div><!-- } 运营数据 -->
				
				<!-- SEO信息 { -->
				<div class="contClassify">
					<h2 class="title">页面SEO信息</h2>
					<div class="filament_solid_ddd ov-au mt30">
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle">
						</div>
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription">
						</div>
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords">
							<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
						</div>
					</div>
				</div>
				<!-- } SEO信息 -->
				
				<!-- 确认按钮 -->
				<div class="formLine">
					<div class="saveOper">
						<button type="button" id="issue" class="btn-base mr30"  >发布</button>
						<button type="button" class="btn-base" onclick="back()">取消</button>
					</div>
				</div>
			</div><!-- } 模块管理 -->
    	</form>
    </div><!-- } main -->

    <!-- JS引用部分 -->
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-form.js"></script>
     <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
    	<!-- 配置文件 -->
	  <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
	  <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
	  <!-- 实例化编辑器 -->
	  <script type="text/javascript"> 
	      var ue = UE.getEditor('content',{
				autoHeightEnabled:false

		      });
	     
	      ue.addListener("contentChange", function(e) {
	      	//$("#edui1_wordcount").empty();
	      	var ueCont = ue.getContentTxt();
			if(ueCont == "") {
				valid_txtBox_create("#content", false, "内容必须在100-5000个字符之间", "top-right");
				//return false;
			}
			var count = ue.getContentLength(true);
            if (count > 5000||count<100) {
            	valid_txtBox_create("#content", false, "内容必须在100-5000个字符之间", "top-right");
            }else{
            	$(".ueEditor_cont").next(".errMesg").remove();
            }
	      });
	  </script>
    
      <script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
    <!-- 数据验证。后台开发人员可以将点击时间内的方法加入自己写的提交时间内 -->
    <script type="text/javascript">
		// 攻略所属版块点击验证
		$(document).on("click", "#theirMudle", function() {
			var $this = $(this),
				this_i = $this.children("i").text().substr(0,3);
			if(this_i != "请选择") {
				valid_customSelect("#theirMudle", "请选择所属板块");
				VLDRST.TheirMudle = true;
			} else {
				VLDRST.TheirMudle = false;
			}
		});
		
		// 攻略所属类型点击验证
		$(document).on("click", "#strategyType", function() {
			var $this = $(this),
				this_i = $this.children("i").text().substr(0,3);
			if(this_i != "请选择") {
				valid_customSelect("#strategyType", "请选择所属板块");
				VLDRST.StrategyType = true;
			} else {
				VLDRST.StrategyType = false;
			}
		});
		
		// 上传用户blur验证
		$("#uploadAuthor").blur(function() {
			var $this = $(this),
				thisVal = $this.val();
			
			// 执行验证并设置验证结果的状态
			if(valid_txtBox(this, $.VLDTOR.IsEnCnNumLnDot(thisVal), "不能为特殊字符、空或空格")) {
				VLDRST.UploadAuthor = true;
			} else {
				VLDRST.UploadAuthor = false;
			}
		});
		
		// 攻略标题blur验证
		$("#strategyTitle").blur(function() {
			var $this = $(this),
				thisVal = $this.val();
			
			// 执行验证并设置验证结果的状态
			if(valid_txtBox(this, $.VLDTOR.IsArticle(thisVal), "不能为空或空格,并且字符在2-30以内")) {
				VLDRST.StrategyTitle = true;
			} else {
				VLDRST.StrategyTitle = false;
			}
		});
		
		// 收藏数验证
		$("#collect_num").blur(function() {
			var $this = $(this),
				thisVal = $this.val();
			
			// 执行验证并设置验证结果的状态
			if(valid_txtBox_create(this,inputRange(this, 1, 7) && $.VLDTOR.IsNum(thisVal) || thisVal == "", "只能输入1-7位的正整数","top")) {
				VLDRST.Collect_num = true;
			} else {
				VLDRST.Collect_num = false;
			}
		});
		
		// 查看数验证
		$("#lookup_num").blur(function() {
			var $this = $(this),
				thisVal = $this.val();
			
			// 执行验证并设置验证结果的状态
			if(valid_txtBox_create(this,inputRange(this, 1, 7) && $.VLDTOR.IsNum(thisVal) || thisVal == "", "只能输入1-7位的正整数","top")){
				VLDRST.Lookup_num = true;
			} else {
				VLDRST.Lookup_num = false;
			}
		});
		
		// 被赞数验证
		$("#praise_num").blur(function() {
			var $this = $(this),
				thisVal = $this.val();			
			// 执行验证并设置验证结果的状态
			if(valid_txtBox_create(this,inputRange(this, 1, 7) && $.VLDTOR.IsNum(thisVal) || thisVal == "", "只能输入1-7位的正整数","top")) {
				VLDRST.Praise_num = true;
			} else {
				VLDRST.Praise_num = false;
			}
		});
		
		/* SEO 验证 */
		$(".seoInfo").blur(function () {
			var $this = $(this),
				thisVal = $this.val();
			valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或字母、数字及中文，且长度在2-20','top');
		});
		
		// 发布验证（通过会返回true，否则返回false）
		$("#issue").click(function() {
			// 攻略所属版块是否选择
			valid_customSelect("#theirMudle", "请选择所属板块");
			// 攻略所属类型是否选择
			valid_customSelect("#strategyType", "请选择攻略类型");
			// 上传用户
			$("#uploadAuthor").blur();
			// 景点选择
			valid_destScenic();
			// 攻略标题
			$("#strategyTitle").blur();
			// 收藏数
			$("#collect_num").blur();
			// 查看数
			$("#lookup_num").blur();
			// 被赞数
			$("#praise_num").blur();
			/* SEO 验证 */
			$(".seoInfo").blur();
			
			// 富文本验证
			var ueCont = ue.getContentTxt();
			if(ueCont == "") {
				valid_txtBox_create("#content", false, "内容必须在100-5000个字符之间", "top-right");
				return false;
			}
			var count = ue.getContentLength(true);
            if (count > 5000||count<100) {
            	msgBox("内容必须在100-5000个字符之间！", "erro");
            	return false;
            }
			// 全部通过则提交
			if(VLDRST.TheirMudle && VLDRST.StrategyType && VLDRST.UploadAuthor && VLDRST.DestScenic && VLDRST.StrategyTitle && VLDRST.Collect_num && VLDRST.Lookup_num && VLDRST.Lookup_num) {
				save('${ctx}/web/readTibetSgPassMgController/save','qxglId')
				msgBox("发布成功！", "pass", 2600);
			} else {
				msgBox("输入的内容有误，请检查！", "erro", 2600);
				return false;
			}
		});
		
		// 取消景点选择错误提示
		$(".destScenic .formLine-large-group .formEleGroup-squad").click(function() {
			var isChecked = $(this).children("input[type='checkbox']").prop("checked");
			if (isChecked) {
				// 移除错误提示
				$(".destScenic .errMesg").remove();
			} else {
				return;
			}
		});
		
		
		/* 景点选择验证 */
		function valid_destScenic() {
			var destSce = $(".destScenic .formLine-large-group .formLine-small-group"),
				destSce_len = destSce.length;
			
			// 遍历验证
			for(var i = 0; i < destSce_len; i++) {
				var dest = destSce.eq(i).find("input[name='vies']"),
					dest_len = dest.length;
				
				for(var j = 0; j < dest_len; j++) {
					var isChecked =  dest.eq(j).prop("checked");
					// 发现选中项返回
					if(isChecked) {
						VLDRST.DestScenic = true;
						return true;
					}
				}
			}
			// 未发现选中项给出提示
			if($(".destScenic .errMesg").length == 0) {
				destSce.parent().append('<div class="errMesg" style="vertical-align:middle; display:inline-block; top:5px;">请至少选择一个景点</div>');
			}
//			// 短弹框提示
//			msgBox("请至少选择一个景点!", "info", 2200);
			VLDRST.DestScenic = false;
		}
	</script>

	<script type="text/javascript">
	    function clearUserInfo(info){
            if (info == '1') {
                $("#uploadAuthor").val("天上西藏官方");
                $("#uploadAuthor").attr("readOnly","readOnly");
            } else {
                $("#uploadAuthor").val("");
                $("#uploadAuthor").removeAttr("readOnly");
            }
		}
		function back(){
			popupLayer(
					'',
					'<div style="width: 320px; text-align:center; margin: 0 auto;">返回将不保存数据，是否返回？</div>',
					'<button type="button" class="btn-sure sure mr15">确定</button>' +
					'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				javascript:history.back(-1);
				closePopup();
			});
		}
	</script>
    
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
	
	
</body>
</html>
