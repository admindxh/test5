<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
	<%@include file="/common-html/common.jsp" %>
    <title>骑行专区-装备管理-新建装备</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
    
    <!-- 网页文本编辑器插件 -->
</head>
<body>
    <!-- main { -->
    <div class="main">
		<form id="qxglId"  action="" method="post">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>骑行专区</a> -</span>
				<span><a href="${ctx }/web/equip/list">装备管理</a> -</span>
				<span><a href="${ctx }/web/equip/addUI" target="_self">新建装备</a></span>
			</div>

			<!-- 模块管理 { -->
			<div class="muduleManage details filament_solid_ddd">
				<!-- 分类信息 { -->
				<div class="contClassify">
					<h2 class="title">分类信息</h2>
					
					<div class="formLine"  >
						<label>装备类型:</label>
						<div id="theirMudle"  class="select-base">
							<i class="w-140">请选择所属装备</i>
							<dl>
								<c:forEach var="program" items="${ptypes}">
									<dt  inputValue="${program.code}" inputName="proType">${program.programaName}</dt>
								</c:forEach>
							</dl>
							<input id="proType" type="hidden" value="" name="proType"/>
						</div>
						<span class="reqItem">*</span>
						
						<label>价格:</label>
						<div  id="strategyType" class="select-base">
							<input id="collect_num"  type="text" name="price" maxlength="20">
						</div>
						<span class="reqItem">*</span>
						<span id="storeNum">
							<label>数量:</label>
							<input id="lookup_num"  type="text"  name="count" maxlength="20">
							<span class="reqItem">*</span>
						</span>
						
					</div>
					<div class="formLine">
						<label>推荐首页:</label>
						<input  type="radio" name="recommoned" checked="checked" value="1"  />是&nbsp;&nbsp;&nbsp;&nbsp;<input  type="radio" name="recommoned" value="2"  />否
					</div>
					<div class="formLine">
						<label>支付类型:</label>
						<div class="chooseDest formLine-middle-group">
								<input type="radio" class="cktype" checked="checked" name="payType" value="1" />本站支付<%--本站支付 --%>
								<span style="width: 30px;">&nbsp;</span><input type="radio" class="cktype" name="payType" value="2" />其他站点支付 <%--其他站点支付 --%>
								<div id="urlShow" style="padding-top: 20px;"><%--
									其它站点支付路径:<input id="payurl"  type="text" name="payurl" maxlength="100">
								--%></div>
						</div>
					</div>
					<div class="formLine">
						<label>简介:</label>
						<div class="chooseDest formLine-middle-group">
								<textarea rows="3"  cols="40"   id="summary" name="summary"></textarea>
								<span class="reqItem">*</span>
						</div>
					</div>
					<div class="formLine">
						<label class="w-120">装备主图:</label>
						<div id="pickfiles" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
						<span class="txt-suggest ml20" >必须是400 * 300尺寸的jpg/png格式</span>
                    </div>
	                <div class="formLine">
						<label class="w-120" style="vertical-align: top;">缩略图:</label>
						 <img id="imgshow" class="desImg1" style="width:400px;height:300px;" src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg" alt=""/>
						 <input id="sumaryimg" type="hidden" class="url" name="sumaryimg">
					</div>	
					
				</div><!-- } 分类信息 -->
				
				<!-- 详细信息 { -->
				<div class="contClassify">
					<h2 class="title">详细信息</h2>
					
					<div class="formLine">
						<label>装备标题:</label>
						<input id="name" type="text" name="name" maxlength="30" class="w-260">
						<span class="reqItem">*</span>
					</div>
					
					<!-- 网页文本编辑器 -->
					<div class="formLine">
						<label  class="ueEditor_lbl">装备详细:</label>
								<script id="content" class="ueEditor_cont" name="content"  style="height:500px;" type="text/plain"></script>
								<span class="reqItem">*</span>
					</div>
				</div><!-- } 详细信息 -->
				
				<!-- 运营数据 { -->
				<!-- } 运营数据 -->
				
				
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
      <script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
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
				valid_txtBox_create("#content", false, "内容必须在10-5000个字符之间", "top-right");
				//return false;
			}
			var count = ue.getContentLength(true);
            if (count > 5000||count<10) {
            	valid_txtBox_create("#content", false, "内容必须在10-5000个字符之间", "top-right");
            }else{
            	$(".ueEditor_cont").next(".errMesg").remove();
            }
	      });
	  </script>
    
    <script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
        <script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
    <!-- 数据验证。后台开发人员可以将点击时间内的方法加入自己写的提交时间内 -->
    <script type="text/javascript">
    	//支付类型验证
    	$(document).on("click",".cktype",function(){
				var $this  = $(this);
				var payType  = $($this).val();
				if(payType=="1"){
					$("#urlShow").html("");		
			     }else{
					 $("#urlShow").html('其它站点支付路径:<input id="payurl"  type="text" class="w-260" name="payurl" maxlength="100"><span class="reqItem">*</span>');
					 $("#payurl").blur(function(){
							var thisVal = $(this).val();
					        var regTest = $.VLDTOR.IsHTTP(thisVal),
					            regNull = $.trim(thisVal) != "";
					          valid_txtBox(this, regTest && regNull, "只能以http(s)开头", "right") 
							});
				 }

        	});
		// 装备类型验证
		$(document).on("click", "#theirMudle", function() {
			var $this = $(this),
				this_i = $this.children("i").text().substr(0,3);
			if(this_i != "请选择") {
				valid_customSelect("#theirMudle", "请选择装备类型");
				VLDRST.TheirMudle = true;
			} else {
				VLDRST.TheirMudle = false;
			}
		});
		
		$("#payurl").blur(function(){
			var thisVal = $(this).val();
	        var regTest = $.VLDTOR.IsHTTP(thisVal),
	            regNull = $.trim(thisVal) != "";
	          valid_txtBox(this, regTest && regNull, "只能以http(s)开头", "right") 
			});
		
		
		
		// 攻略标题blur验证
		$("#name").blur(function() {
			var $this = $(this),
				thisVal = $this.val();
			
			// 执行验证并设置验证结果的状态
			/* if(valid_txtBox(this, strRange(thisVal,2,30), "不能为空或空格,并且字符在2-30以内","right")) {
				VLDRST.StrategyTitle = true;
			} else {
				VLDRST.StrategyTitle = false;
			} */
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30), "内容必须在2-30个字符之间", "right");
		});

		$("#summary").blur(function(){
				var $this = $(this),
				thisVal = $this.val();
				// 执行验证并设置验证结果的状态
				/* if(valid_txtBox(this, strRange(thisVal,2,200), "不能为空或空格,并且字符在2-200以内","right")) {
					VLDRST.summary = true;
				} else {
					VLDRST.summary = false;
				} */
				valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,200), "内容必须在2-200个字符之间", "right");
			});
		// 价格数验证
		$("#collect_num").blur(function() {
			var $this = $(this),
				thisVal = $this.val();
			// 执行验证并设置验证结果的状态
			if(valid_txtBox_create(this,inputRange(this, 1, 7) && $.VLDTOR.IsNum(thisVal) && thisVal != "", "只能输入1-7位的正整数","top")) {
				VLDRST.Collect_num = true;
			} else {
				VLDRST.Collect_num = false;
			}
		});
		
		// 数量
		$("#lookup_num").blur(function() {
			if (!isOther) {
				var $this = $(this),
					thisVal = $this.val();
				
				// 执行验证并设置验证结果的状态
				if(valid_txtBox_create(this,inputRange(this, 1, 7) && $.VLDTOR.IsNum(thisVal) && thisVal != "", "只能输入1-7位的正整数","top")){
					VLDRST.Lookup_num = true;
				} else {
					VLDRST.Lookup_num = false;
				}
			}
		});
		
		
		/**
		 * 是否从本站点支付，若为其他站点支付则不处理库存数据
		 */
		var isOther = false;
		$(":radio[name='payType']").click(function () {
			if (this.checked && this.value == "2") {
				isOther = true;
				$("#storeNum").hide();
			} else {
				isOther = false;
				$("#storeNum").show();
			}
		});
		
		
		// 发布验证（通过会返回true，否则返回false）
		$("#issue").click(function() {
			// 攻略所属版块是否选择
			valid_customSelect("#theirMudle", "请选择所属装备");
			
			
			// 攻略标题
			$("#strategyTitle").blur();
			// 收藏数
			$("#collect_num").blur();
			// 查看数
			$("#lookup_num").blur();
			// 被赞数
			$("#payurl").blur();
			$("#summary").blur();
			$("#name").blur();
			
			if ($("#sumaryimg").val() == "") {
				valid_txtBox_create("#pickfiles", false, "必须上传一张主图！", "top-right");
				return false;
			} else {
				removeErrMesg("#pickfiles");
			}
			// 富文本验证
			var ueCont = ue.getContentTxt();
			if(ueCont == "") {
				valid_txtBox_create("#content", false, "内容必须在10-5000个字符之间", "right");
				return false;
			}
			var count = ue.getContentLength(true);
            if (count > 5000||count<10) {
            	msgBox("内容必须在10-5000个字符之间！", "erro");
            	return false;
            }
			// 全部通过则提交
			var    	numberError  = $(".errMesg").length;
			if(numberError<=0) {
				save('${ctx}/web/equip/save','qxglId')
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
