<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<title>活动&专题-活动&专题信息管理-活动管理</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxManage}resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}resources/css/travel/formWeb.css" />
	<link rel="stylesheet" href="${ctxManage}resources/css/activity/activity-manage.css" />
	<script src="${ctxManage}resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<form id="thisForm" name="" action="${ctx }web/activityController/updateActivityCheckVoteNum" method="post">
			<!-- hidden -->
			<input type="hidden" name="activityCode" value="${activity.code }">
			
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>活动&专题</a> -</span>
				<span><a>活动&专题信息管理</a> -</span>
				<span><a href="${ctx }web/activityController/showList" target="_self">活动信息管理</a> -</span>
				<span><a href="${ctx }web/activityController/forActivityManage?activityCode=${activity.code}" target="_self">活动管理</a></span>
			</div>

			<!-- 数据操作 -->
			<div class="searchManage">
				<button type="button" class="btn-sure saveBtn">保存修改</button>
			</div>

			<!-- 活动简述 { -->
			<div class="muduleManage details filament_solid_ddd">
				<!-- 活动类型 { -->
				<div class="contClassify bt-n">
					<!--<h2 class="title">活动类型</h2>-->

					<div class="formLine">
						<label class="w-auto">活动名称:</label>
						<span class="dataVal">
							<a href="${ctx}${activity.linkUrl}" target="_blank">${activity.name }</a>
						</span>
						
						<label>发布时间:</label>
						<span class="dataVal"><fmt:formatDate type="both" value="${activity.createTime }" pattern="yyyy-MM-dd" /></span>
						
					</div>

					<div class="formLine">
						<label class="w-auto">开始时间:</label>
						<span class="dataVal"><fmt:formatDate type="both" value="${activity.startDate }" pattern="yyyy-MM-dd" /></span>
						
						<label>结束时间:</label>
						<span class="dataVal"><fmt:formatDate type="both" value="${activity.endDate }" pattern="yyyy-MM-dd" /></span>
						
						<label>查看量:</label>
						<input id="fakeCheckNum" name="fakeCheckNum" maxlength="7" type="text" value="${activity.fakeCheckNum }">
						<button type="button" class="btn-base" onclick="javascript:$('#fakeCheckNum').val(${activity.checkNum });" >恢复默认</button>
					</div>
				</div>
				<!-- } 活动简述 -->

				<!-- 上传 { -->
				<div class="contClassify" style="${activity.isUpload==1?'':'display: none;'}">
					<h2 class="title mb20">上传</h2>
					
					<div class="tableManage mb-0">
						<!-- 数据列表 -->
						<table border="0" cellpadding="0" cellspacing="0" ms-important="actProductView">
							<thead>
								<tr>
									<th>序号</th>
									<th>上传时间</th>
									<th>名称</th>
									<th>用户</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr ms-repeat="data" ms-attr-class="one:$first">
									<td>{{$index+1}}</td>
									<td>{{el.createTime|date("yyyy-MM-dd")}}</td>
									<td>{{el.name}}</td>
									<td><a target="_blank" ms-attr-href="${ctx}portal/othershome/othersStra?memberCode={{el.memberCode}}">{{el.memberName}}</a></td>
									<td>{{el.stateName}}</td>
									<td>
										<a ms-if="el.state==0" class="upload-lookup checkActProduct" ms-attr-code="el.code" ms-attr-url="el.url" ms-attr-activityName="el.activityName" ms-attr-memberName="el.memberName">查看</a>
										<a ms-if="el.state==1" class="upload-edit editActProduct" ms-attr-code="el.code" ms-attr-url="el.url" ms-attr-activityName="el.activityName" ms-attr-memberName="el.memberName" ms-attr-likeNum="el.likeNum" ms-attr-fakeLikeNum="el.fakeLikeNum">编辑</a>
										<a ms-click="deleteActProduct(el.code)">删除</a>
										<a ms-if="el.isTop==0&&el.state==1" ms-click="topActProduct(el.code)">置顶</a>
										<a ms-if="el.isTop==1" href="javascript:void(0);" ms-click="unTopActProduct(el.code)">取消置顶</a>
										<a ms-if="el.state==0" ms-click="passActProduct(el.code)">审核通过</a>
									</td>
								</tr>
							</tbody>
						</table>

						<!-- 表格分页 -->
						<div id="actProductPaging" class="paging"></div>
					</div>
				</div><!-- } 上传 -->
				
				<!-- 报名 { -->
				<div class="contClassify" style="${activity.isEnroll==1?'':'display: none;'}">
					<h2 class="title mb20">报名</h2>
					
					<div>
						<label></label>
						<div id="orderChannelSourceSelect" class="select-base">
							<input type="hidden" id="orderChannelSourceCode" value="${orderChannelSourceCode }">
							<i class="w-140">全部渠道</i>
							<dl>
								<dt name="">全部渠道</dt>
								<dt name="-1">官方网站</dt>
								<c:forEach var="obj" items="${listOrderChannelSource}">
			                    	<dt name="${obj.code}">${obj.name }</dt>
			                    </c:forEach>
							</dl>
						</div>
					</div>
					
					<button type="button" class="btn-base mb15 float_r mt_-35" onclick="exportExcel();">导出 Excel</button>
					<div class="tableManage mb-0" ms-important="memberEnrollView">
						<!-- 数据列表 -->
						<table border="0" cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<th>序号</th>
									<th>报名时间</th>
									<th>来源</th>
									<th>报名付费</th>
									<th>支付状态</th>
									<th>用户</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr ms-repeat="data" ms-attr-class="one:$first">
									<td>{{$index+1}}</td>
									<td>{{el.enrollTime|date("yyyy-MM-dd")}}</td>
									<td>{{el.orderChannelSourceName}}</td>
									<td>{{el.isEnrollPayName}}</td>
									<td>{{el.payStateName}}</td>
									<td><a target="_blank" ms-attr-href="${ctx}portal/othershome/othersStra?memberCode={{el.memberCode}}">{{el.memberName}}</a></td>
									<td>
										<a class="lookupApplyInfo" ms-attr-memberCode="el.memberCode" ms-attr-memberName="el.memberName" ms-attr-enrollTime="el.enrollTime">查看报名资料</a>
										<a ms-if="el.payState==0" ms-click="deleteEnroll(el.memberCode)">删除</a>
										<a ms-if="el.isTop==0" href="javascript:void(0);" ms-click="topEnroll(el.memberCode)">置顶</a>
										<a ms-if="el.isTop==1" href="javascript:void(0);" ms-click="unTopEnroll(el.memberCode)">取消置顶</a>
									</td>
								</tr>
							</tbody>
						</table>

						<!-- 表格分页 -->
						<div id="memberEnrollPaging" class="paging"></div>
					</div>
				</div><!-- } 报名 -->

				<!-- 投票 { -->
				<c:set var="voteDisplay" value="${activity.isVote==0?'display: none;':'' }"/>
				<div class="contClassify" style="${voteDisplay}">
					<h2 class="title">投票相关</h2>
					
					<div class="formLine">
						<label class="w-auto">投票名称:</label>
						<span class="dataVal large">${activity.voteName }</span>

						<label class="ml15">共</label>
						<input id="vote_total" name="" type="text" value="0" readonly="readonly">
						<span class="dataVal">人参与</span>
						<button id="vote_default" type="button" class="btn-base ml30">恢复系统数据</button>
					</div>
					<c:forEach var="obj" varStatus="sta" items="${listVoteOption }">
						<div class="formLine vote">
							<!-- 投票左容器 -->
							<div class="vote-left">
								<!-- 投票 -->
								<span class="dataVal">${obj.options }</span>
								<!-- 投票容器 -->
								<div class="vote-box">
									<!-- 投票色条 -->
									<div class="vote-box-bar mbgc_${sta.index }"></div>
								</div>
								<!-- 投票百分比结果 -->
								<span class="vote-perc">0%</span>
							</div>
							
							<!-- 投票右容器 -->
							<div class="vote-right">
								<input name="listVoteOption[${sta.index }].code" type="hidden" value="${obj.code }" class="w-100">
								<input id="" maxlength="7" name="listVoteOption[${sta.index }].fakeCounts" type="text" value="${obj.fakeCounts }" class="w-100">
								<label class="w-auto">票</label>
							</div>
						</div>
					</c:forEach>
				</div><!-- } 投票 -->
				
			</div><!-- } 模块管理 -->
			
			<!-- ------------------------------------------
							弹出层部分内容
			------------------------------------------- -->
			<!-- 查看上传内容 { -->
			<div class="lookupUploadCont poplayer">
				<!-- 关闭 -->
				<div class="close-tras_black"></div>
				
				<!-- 图片 -->
				<div class="imgBox">
					<img id="checkActProductImg" src="" title="缩略图名称" alt="上传的图片">
				</div>
				
				<!-- 操作区 -->
				<div class="operInfoBar">
				
					<!-- 作品名称 -->
					<div id="actProductActivityName" class="activName"></div>
					
					<!-- 用户名 -->
					<div class="activAccount">
						<label>by:</label>
						<span id="actProductMemberName"></span>
					</div>
					
					<!-- 操作按钮 -->
					<div class="activOperBtn">
						<button type="button" class="btn-base" onclick="deleteActProductWindow()">删除</button>
						<button type="button" class="btn-base" onclick="passActProductWindow()">审核通过</button>
					</div>
				</div>
			</div><!-- } 查看上传内容 -->
			
			<!-- 编辑上传内容 { -->
			<div class="poplayer editUploadCont">
				<!-- 关闭 -->
				<div class="close-tras_black"></div>
				
				<!-- 图片 -->
				<div class="imgBox">
					<img id="editActProductImg" src="" title="缩略图名称" alt="上传的图片">
				</div>
				
				<!-- 操作区 -->
				<div class="operInfoBar">
				
					<!-- 作品名称 -->
					<div id="editActProductActivityName" class="activName"></div>
					
					<!-- 用户名 -->
					<div class="activAccount">
						<label>by:</label>
						<span id="editActProductMemberName"></span>
					</div>
					
					<!-- 操作按钮 -->
					<div class="activOperBtn">
						<label>赞:</label>
						<input id="actProductLike" name="" type="text" maxlength="7">
						<button type="button" class="btn-base" onclick="resetActProductLike()">恢复系统值</button>
						<button type="button" class="btn-base" onclick="updateActProductLike()">保存</button>
						<button type="button" class="btn-base" onclick="deleteActProductWindow()">删除此作品</button>
					</div>
				</div>
			</div><!-- } 编辑上传内容 -->
			
			<!-- 报名资料 { -->
			<div id="enrollWindow" class="poplayer applyInfo">
				<!-- 关闭 -->
				<div class="close-tras_black"></div>
				
				<!-- 内容 -->
				<div class="infoCont">
					
					<!-- 报名资料 -->
					<h1 class="title">报名资料</h1>

					<div class="formLine mt10">
						<label>用户名:</label>
						<span id="enrollMemberName" class="dataVal"></span>
					</div>

					<div class="formLine">
						<label>报名时间:</label>
						<span id="enrollTime" class="dataVal"></span>
					</div>

					<!-- <div class="formLine enrollDetailJson">
						<label>真实姓名:</label>
						<span class="dataVal">张山</span>
					</div>

					<div class="formLine">
						<label>身份证正面照:</label>
						<span class="dataVal">123.jpg</span>
						<a href="#">下载</a>
					</div>

					<div class="formLine">
						<label>资产证明:</label>
						<span class="dataVal">我的资产证明.docx</span>
						<a href="#">下载</a>
					</div> -->
					
					<!-- 关闭按钮 -->
					<div id="enrollCloseBtn" class="formLine txt-ac mt10">
						<button type="button" class="btn-sure close-layer">关闭</button>
					</div>
				</div>
			</div><!-- } 报名资料 -->
		</form>
	</div>
	<!-- } main -->
	
	<!-- JS引用部分 -->
	<script src="${ctxManage}resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-form.js"></script>
    <script src="${ctx}common-js/DateUtil.js"></script>
	<!-- 投票控制 -->
	<script src="${ctxManage}resources/js/activity/vote-ctrl.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
    <script src="${ctxManage}resources/js/dataValidation.js" type="text/javascript"></script>
	
	
	<!-- jsp -->
	<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>
	<script type="text/javascript">
		var contextPath='<%=basePath%>';
		/* 恢复默认投票 */
		$("#vote_default").click(function() {
			var voteItem =  $(".formLine.vote");
			//voteItem.eq(0).children(".vote-right").children("input[type='text']").val(10);
			var index;
			var count;
			<c:forEach var="obj" varStatus="sta" items="${listVoteOption }">
				index = ${sta.index};
				count = ${obj.counts};
				voteItem.eq(index).children(".vote-right").children("input[type='text']").val(count);
			</c:forEach>
			vote_set();
		});
	</script>
	
	<script type="text/javascript">
		$(function(){
			var divs = $(".select-base");
			for(var i=0; i < divs.length; i++){
				var div = divs.eq(i);
				var value = div.find("input").val();
				var dtValue = div.find("dt[name='"+value+"']").text();
				if(dtValue){
					div.find("i").text(dtValue);
					loadMemberEnrollList(1);
				}
			}
		});
		$("#orderChannelSourceSelect dt").click(function(){
			var val = $(this).attr("name");
			$("#orderChannelSourceCode").val(val);
			loadMemberEnrollList(1);
		});
		
		function exportExcel(){
			var activity_ = 'activityCode=' + '${activity.code }';
			var orderChannelSourceCode_ = '&orderChannelSourceCode=' + $("#orderChannelSourceCode").val();
			//window.location.href='${ctx}'+'web/memberEnrollDetailController/exportMemberEnrollDetailExcel?' + activity_ + orderChannelSourceCode_
			window.location.href='${ctx}'+'web/memberEnrollDetailController/testExportMemberEnrollDetailExcel?' + activity_ + orderChannelSourceCode_
		}
		
	</script>
	
	<script type="text/javascript">
		var $currentObj;
		//作品当前页
		var actProductPage=${actProductPage};
		//报名表单当前页
		var enrollFormPage=${enrollFormPage};
		/**上传作品{*/
		//查看
		$(document).on("click", ".checkActProduct", function(){
			$currentObj=$(this);
			var code = $currentObj.attr("code");
			var url = $currentObj.attr("url");
			$("#checkActProductImg").attr("src", contextPath+url);
			
			$("#actProductActivityName").text($currentObj.attr("activityName"));
			$("#actProductMemberName").text($currentObj.attr("memberName"));
			var uploadCont = $(".lookupUploadCont");
			uploadCont.slideDown(300);
		});
		//编辑
		$(document).on("click", ".editActProduct", function(){
			$currentObj=$(this);
			var code = $currentObj.attr("code");
			var url = $currentObj.attr("url");
			$("#editActProductImg").attr("src", contextPath+url);
			
			$("#editActProductActivityName").text($currentObj.attr("activityName"));
			$("#editActProductMemberName").text($currentObj.attr("memberName"));
			$("#actProductLike").val($currentObj.attr("fakeLikeNum"));
			var uploadCont = $(".editUploadCont");
			uploadCont.slideDown(300);
		});
		
		
		//弹出框删除
		function deleteActProductWindow(){
			deleteActProduct($currentObj.attr("code"));
		}
		//弹出框通过审核
		function passActProductWindow(){
			passActProduct($currentObj.attr("code"));
		}
		//弹出框恢复默认赞
		function resetActProductLike(){
			$("#actProductLike").val($currentObj.attr("likeNum"));
		}
		//修改假赞 保存
		function updateActProductLike(){
			if($("#actProductLike").val()==undefined || $("#actProductLike").val()==""){
				$("#actProductLike").val(0);
			}
			var data={
					code:$currentObj.attr("code"),
					fakeLikeNum:$("#actProductLike").val()
			};
			var url="${ctx}web/activityProductController/updateFakeLikeNum";
			var isPass = $.VLDTOR.IsNum(data.fakeLikeNum);
			if(isPass || data.fakeLikeNum == "") {
				$.post(url, data, function(data){
					if(data=='success'){
						$currentObj.attr("fakeLikeNum", $("#actProductLike").val());
						msgBox("保存成功！", "pass", 1200);
					}else if(data=='error'){
						msgBox("保存失败！", "erro", 1200);
					}
				});
			} else {
				msgBox("被赞数只能为整数！", "erro");
			}
		}
		//删除
		function deleteActProduct(code){
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否删除？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				window.location.href="${ctx}web/activityProductController/deleteManageActivityProduct?activityCode=${activity.code}&code="+code + "&actProductPage=" + actProductPage + "&enrollFormPage=" + enrollFormPage;
				closePopup();
			});
		}
		//通过审核
		function passActProduct(code){
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否通过审核？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				window.location.href="${ctx}web/activityProductController/reviewManageActivityProduct?activityCode=${activity.code}&code="+code + "&actProductPage=" + actProductPage + "&enrollFormPage=" + enrollFormPage;
				closePopup();
			});
		}
		//置顶
		function topActProduct(code){
			window.location.href="${ctx}web/activityProductController/topManageActivityProduct?activityCode=${activity.code}&code="+code + "&actProductPage=" + actProductPage + "&enrollFormPage=" + enrollFormPage;
		}
		//取消置顶
		function unTopActProduct(code){
			window.location.href="${ctx}web/activityProductController/unTopManageActivityProduct?activityCode=${activity.code}&code="+code + "&actProductPage=" + actProductPage + "&enrollFormPage=" + enrollFormPage;
		}
		/**上传作品}*/
	</script>
	<script type="text/javascript">
		/**报名{*/
		//查看报名资料
		$(document).on("click", ".lookupApplyInfo", function(){
			$currentObj=$(this);
			var url="${ctx}web/memberEnrollDetailController/getEnrollDetail";
			var data={activityCode: '${activity.code}', memberCode:$currentObj.attr("memberCode") };
			$.post(url, data, function(data){
	            if(data.length>0){
	            	$(".enrollDetailJson").remove();
	            	$("#enrollMemberName").text($currentObj.attr("memberName"));
                    var enrolltime_ = DateUtil.dateToStr('yyyy-MM-dd',new Date($currentObj.attr("enrollTime")));
	            	$("#enrollTime").text(enrolltime_);
	            	
	            	for(var i = 0; i<data.length; i++){
	    				var obj = data[i];
	    				var o='';
	    				
	    				o+="<div class='formLine enrollDetailJson'>";
	    				o+="<label>"+ obj.property +":</label>";
	    				if(obj.propertyType=='${PROPERTY_TYPE_TEXT}'){
	    					o+="<span class='dataVal'>"+obj.propertyValue+"</span>";
	    				}else if(obj.propertyType=='${PROPERTY_TYPE_NUMBER}'){
	    					o+="<span class='dataVal'>"+obj.propertyValue+"</span>";
	    				}else if(obj.propertyType=='${PROPERTY_TYPE_SELECT}'){
	    					o+="<span class='dataVal'>"+obj.propertyValue+"</span>";
	    				}else{
	    					var filename = obj.fileName;
	    					var fileext = filename.substr(filename.lastIndexOf("."));
	    					var name = filename.replace(fileext,"");
	    					if(name.length>15){
	    						name = name.substr(0,15)+"..";
	    					}
	    					filename = name+fileext;
	    					
	    					o+="<span class='dataVal'>"+filename+"</span>";
	    					//o+="<a href='${ctx}"+obj.propertyValue+"'>下载</a>";
	    					//o+="<a href='${ctx}web/activityController/download?url="+obj.propertyValue+'asd'+"&fileName="+obj.fileName+"'>下载</a>";
	    					o+="<a href='${ctx}web/activityController/download?code="+obj.code+"'>下载</a>";
	    				}
	    				o+="</div>";
	    				$("#enrollCloseBtn").before(o);
	    			}
	            	var applyInfo = $(".applyInfo");
	    			applyInfo.slideDown(300);
	            }else{
	            	alert("获取信息失败");
	            }
	        }, "json");
		});
		
		//删除
		function deleteEnroll(memberCode){
			////.log(memberCode)
			//return false;
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否删除？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				window.location.href="${ctx}web/memberEnrollDetailController/deleteManageMemberEnrollDetail?activityCode=${activity.code}&memberCode="+memberCode + "&actProductPage=" + actProductPage + "&enrollFormPage=" + enrollFormPage;
				closePopup();
			});
		}
		//置顶
		function topEnroll(memberCode){
			window.location.href="${ctx}web/memberEnrollDetailController/topManageMemberEnrollDetail?activityCode=${activity.code}&memberCode="+memberCode + "&actProductPage=" + actProductPage + "&enrollFormPage=" + enrollFormPage;
		}
		//取消置顶
		function unTopEnroll(memberCode){
			window.location.href="${ctx}web/memberEnrollDetailController/unTopManageMemberEnrollDetail?activityCode=${activity.code}&memberCode="+memberCode + "&actProductPage=" + actProductPage + "&enrollFormPage=" + enrollFormPage;
		}
		
		/**报名}*/
	</script>
	<script type="text/javascript">
		function servicesPage(pageId, currentPage, totalPage, call_){
			var options = {
		     	currentPage: currentPage || 1,
		     	totalPages: totalPage || 1,
		     	itemTexts: function (type, page, current) {
                    switch (type) {
	                    case "first":
	                        return "首页";
	                    case "prev":
	                        return "上一页";
	                    case "next":
	                        return "下一页";
	                    case "last":
	                        return "末页";
	                    case "page":
	                        return page;
                    }
                },
		     	onPageChanged: function(e,oldPage,newPage){
		     		call_(newPage);
		    	}
			}
			$('#'+pageId).bootstrapPaginator(options);
		}
		
		//加载作品列表{
		var actProductVM = avalon.define({
			$id:'actProductView',
			data:[]
		});
		function loadActProductList(currentPage){
			actProductPage=currentPage;
			var thisCall = loadActProductList;
			var url="${ctx}web/activityProductController/showActManageActivityProductList";
			$.post(url, {activityCode:'${activity.code }',currentPage: currentPage, pageSize: 20}, function(response){
			  	servicesPage("actProductPaging", response.currentPage, response.totalPage, thisCall);
			  	actProductVM.data = response.dataList;
			  	var isUpload = ${activity.isUpload};
			  	if(response.dataList.length==0 && isUpload==1){
			  		msgBox("上传列表暂无数据", "info", 1200);
			  	}
			}, 'json');
		}
		//}
		
		//加载报名列表{
		var memberEnrollVM = avalon.define({
			$id:'memberEnrollView',
			data:[]
		});
		function loadMemberEnrollList(currentPage){
			enrollFormPage=currentPage;
			var thisCall = loadMemberEnrollList;
			var url="${ctx}web/memberEnrollDetailController/showActManageMemberEnrollDetailList";
			var orderChannelSourceCode = $("#orderChannelSourceCode").val();
			var params = {
				activityCode:'${activity.code }', 
				orderChannelSourceCode:orderChannelSourceCode, 
				currentPage: currentPage, 
				pageSize: 20
			};
			$.post(url, params, function(response){
			  	servicesPage("memberEnrollPaging", response.currentPage, response.totalPage, thisCall);
			  	memberEnrollVM.data = response.dataList;
			  	var isEnroll = ${activity.isEnroll};
			  	if(response.dataList.length==0 && isEnroll==1){
			  		msgBox("报名列表暂无数据", "info", 1200);
			  	}
			}, 'json');
		}
		//}
		
		function loadData(){
			actProductPage=actProductPage||1;
			enrollFormPage=enrollFormPage||1;
			loadActProductList(actProductPage);
			loadMemberEnrollList(enrollFormPage);
			avalon.scan();
		}
		loadData();
	</script>
	<script src="${ctxManage}resources/js/activity/activity-manage.js" type="text/javascript"></script>
	<script type="text/javascript">
		/**** 数据验证 ****/
		
		// 查看量
		$("#fakeCheckNum").blur(function() {
			var this_val = $(this).val();
			valid_txtBox_create(this, $.VLDTOR.IsNum(this_val), "只能输入数字", "top");
		});
		
		// 参与数
		$("#vote_total").blur(function() {
			var this_val = $(this).val();
			valid_txtBox_create(this, $.VLDTOR.IsNum(this_val), "只能输入数字", "top");
		});

		// 投票数
		$("[name^='listVoteOption']").blur(function() {
			var this_val = $(this).val();
			valid_txtBox_create(this, $.VLDTOR.IsNum(this_val), "只能输入数字", "top");
		});
		
		// 保存验证
		$(".saveBtn").click(function() {
			// 失焦验证
			$("#fakeCheckNum, #vote_total, [name^='listVoteOption'][type='text']").blur();
			
			var errLen = $(".errMesg").length > 0;
			// 不通过
			if(errLen) {
				msgBox("填写的信息有误，请检查", "erro");
				return;
			}
			// 验证通过
			else {
				msgBox("保存成功！", "pass");
				// 提交表单
				$("#thisForm").submit();
			}
		});
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