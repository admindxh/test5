<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title>游西藏-商户管理-商户类别管理</title>
		<%@include file="/common-html/commonxz.jsp"%>
		<script type="text/javascript" src="<%=basePath%>manage/resources/js/dataValidation.js"></script>
	</head>

	<body>
		<!-- main { -->
		<div class="main">
			<!-- 页面位置-->
			<div class="location">
				<label>
					您当前位置为:
				</label>
				<span><a>骑行专区</a> -</span>
				<span><a>装备推荐</a> -</span>
				<span><a>装备类型管理</a></span>
			</div>

			<%-- 
				<!-- 数据操作 -->
				<div class="searchManage">
					<button id="category-add" type="button" class="btn-sure">
						新建类别
					</button>
				</div>
			--%>
			
			<!-- 模块管理 { -->

			<div class="tableManage pos-rela">
				<form method="post" action="searchMerchantType">
					<!-- 搜索查询栏 { -->
					<div style="height: 26px;">
					</div>
				</form>
				<!-- } 搜索查询栏 -->
					<!-- 数据列表 -->
					<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
						<thead>
							<tr>
								<th>
									编号
								</th>
								<th>
									类别名
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<tbody  ms-important="infoView" id="tbodyList">
						   <tr ms-if="!data.size()">
			                	<td></td>
			                	<td>
			                		<p style="color: red;text-align: center;">暂无相关搜索数据</p>
			                	<td>
               				</tr>
							<tr ms-repeat="data">
								<td >
								{{el.code}}
								</td>
								<td>
								{{el.programaName}}
								</td>
								<td>
									<%--
									<a   ms-click="deletetype(el.code)">删除</a>
									 --%>
									<a class="update" ms-attr-name="el.programaName" ms-attr-code="el.code">修改</a>
								</td>
							</tr>
						</tbody>
					</table>
					<div id="infoPage" class="paging"></div>
			</div>
			<!-- } 模块管理 -->
		</div>
		<!-- } main -->
<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>
<script type="text/javascript">

		var originalName = "";

	    function servicesPage(pageId, currentPage, totalPage, call_) {
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
	            onPageChanged: function (e, oldPage, newPage) {
	                call_(newPage);
	            }
	        };
	        $('#' + pageId).bootstrapPaginator(options);
	    }

	    //加载作品列表{
	    var infoVM = avalon.define({
	        $id: 'infoView',
	        data: [],
	        deletetype: function (code) {
	        	deletetype(code);
	        }
	    });
	    
	    $(document).on("click", ".update", function () {
	    	var name = $(this).attr("name"),
	    		code = $(this).attr("code");
	    	originalName = name;
	    	updatetype(name, code);
	    });

	    function updatetype(name,code){
	    	popupLayer(
					"<h1>修改装备类别</h1>", 
					'<div class="formLine mt30 mb30" style="text-align:center">' +
	                '<form action="updateetype" method="post" id="merchantTypeForm" onsubmit="return false">'+
					'<label style="width:auto">装备类别:&nbsp;</label>' +
					'<input id="code" maxlength="30" type="hidden" class="w-200" name="code" value="'+
					code+'">&nbsp;' +
					'<input id="mType" maxlength="30" type="text" class="w-200" name="name" value="'+
					name+'">&nbsp;' +
					'<span class="reqItem">*</span>' +
					'</div>',
					'<button type="button" class="btn-sure sureupdate mr15">确定</button>' +
					'<button type="button" class="btn-sure noAdd cancel ml15">取消</button>'+
					'</form>'
			);
      	}
     
	 	function deletetype(code) {
			//删除的ajax	
		  	popupLayer( '',
                '<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除？</div>',
                '<button type="button" class="btn-sure sureDele mr15">确定</button>' +
                '<button type="button" class="btn-sure noDele cancel ml15">取消</button>'
        	);
	        $(document).on('click', '.sureDele', function () {
	            var url = "deleteetype?code=" + code;
			  	$.ajax({
		            type: "POST",
		            url:url ,
		            dataType: "json",
		            success: function (data) {
		            	if(data.state==0){
		            		 msgBox("删除成功！", "info", 1000);
		 	            	setTimeout(function(){
		 	            		window.location.href=location.href;
		 	            	},2000);
		            	}
		            	else{
		            		 msgBox(data.content, "erro", 1000);
		            	}
		            }
		        });
				closePopup();
	        });
		}
	    avalon.ready(function () {
	        avalon.scan();
	    });
	    function loadInfoList(currentPage) {
	        var thisCall = loadInfoList;
	        var url = "infosearch.action";
	        // {activityCode:'${activity.code }',currentPage: currentPage, pageSize: 2}
	        var params = $('#paramsfrm').serialize() + "&currentPage=" + currentPage + "&pageSize=20";
	        $.post(url, params, function (response) {
	            servicesPage("infoPage", response.currentPage, response.totalPage, thisCall);
	            infoVM.data = response.dataList;
	        }, 'json');
	    }
		//}
		$(function () {
		    loadInfoList(1);
		    /* 延迟0.3秒为IE8表格添加隔行变色效果 */
		    setTimeout("trAlternateColor('.dataTable')",600);
		});

     $(document).on("click", "#category-add", function () {
   		popupLayer(
			"<h1>新建装备类别</h1>", 
			'<div class="formLine mt30 mb30" style="text-align:center">' +
            '<form action="addetype" method="post" id="merchantTypeForm" onsubmit="return false;">'+
			'<label style="width:auto">装备类别:&nbsp;</label>' +
			'<input id="mType" maxlength="30" type="text" class="w-200" name="name">&nbsp;' +
			'<span class="reqItem">*</span>' +
			'</div>',
			'<button type="button" class="btn-sure sureAdd mr15">确定</button>' +
			'<button type="button" class="btn-sure noAdd cancel ml15">取消</button>'+
			'</form>'
   		);
    });
     
     $(document).on("click", ".sureupdate", function() {
 		$("#mType").blur();
 		var hasErr = $(".errMesg").length > 0;
 		if(hasErr) {
 			return;
 		} else {
 			if (validateName($("#mType").val())) {
 				validateTypeName("update", function () {
 	 				var $form = $('#merchantTypeForm');
 	 	 		  	var url = 'updateetype?random='+Math.random();
 	 	 		  	var params= $form.serialize();
 	 	 		  	$.ajax({
 	 	 	            type: "POST",
 	 	 	            url: url,
 	 	 	            dataType: "json",
 	 	 	            data: params,
 	 	 	            success: function (data) {
 	 	 	            	if(data.state==0){
 	 	 	            		 msgBox("修改成功！", "info", 1000);
 	 	 	            	}
 	 	 	            	else{
 	 	 	            		 msgBox(data.content, "erro", 1000);
 	 	 	            	}
 	 	 	            	closePopup();
		  	 	            setTimeout(function(){
	 	 	 	            	window.location.href=location.href;
		  	 	            },2000);
 	 	 	            }
 	 	 	        });
 				});
 			} else msgBox("已经存在装备类别：" + $("#mType").val() + "！", "info", 1000);
 		}
 	});

     
	$(document).on("click", ".sureAdd", function() {
		$("#mType").blur();
		var hasErr = $(".errMesg").length > 0;
		if(hasErr) {
			return;
		} else {
			validateTypeName("add", function () {
				var $form = $('#merchantTypeForm');
	 		  	var url = 'addetype?random=' + Math.random();
	 		  	var params= $form.serialize();
	 		  	$.ajax({
	 	            type: "POST",
	 	            url: url,
	 	            dataType: "json",
	 	            data: params,
	 	            success: function (data) {
	 	            	if(data.state==0){
	 	            		 msgBox("添加成功！", "info", 1000);
	 	            	}
	 	            	else{
	 	            		 msgBox(data.content, "erro", 1000);
	 	            	}
	 	            	closePopup();
	 	            	setTimeout(function(){
	 	            		window.location.href=location.href;
	 	            	},2000);
	 	            }
	 	        });
			});
		}
	});

	/**
	 * 验证装备类别名（用于下拉选中的的Item，故不能重复出现）
	 *
	 * callback 验证通过的回调函数
	 */
	function validateTypeName (execType, callback) {
		var type = $("#mType").val();
		$.post("valid", { programaName : type }, function (response) {
			if (execType == "add") {
				if (response.result == 0) {
					callback();
				} else msgBox("已经存在装备类别：" + type + "！", "info", 1000);
			} else if (execType == "update") {
				if (response.result <= 1) {
					callback();
				} else msgBox("已经存在装备类别：" + type + "！", "info", 1000);
			}
		}, "json");
	}
	
	function validateName (targetName) {
		var name = "";
		var result = true;
		$("#tbodyList .update").each(function () {
			name = $(this).attr("name");
			if (name != originalName) { // 排除当前行
				if (targetName.replace(/\s/g, "") == name) {
					result = false;
					return;
				}
			}
		});
		return result;
	}
	
	$(document).on('blur','#mType',function(){
		var $this = $(this),
			this_val = $this.val();
		valid_txtBox(this, strRange(this_val, 2, 30) && $.trim(this_val) != "",'装备类别只能为2-30位字符','top');
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
