/*
 * 公共JS，页面加载完后须用到的
 * dxh 2014-11-26 13:50:24
 *
 * 
 * */

/**
 * 加载事件
 */
$(function(){
	bindingSubmit();
});

/**
 * 绑定事件
 * @return
 */
function bindingSubmit(){	
	
	// 下拉框  选择内容，并隐藏选择菜单(点击) 同时触发 绑定查询条件值
	$(".select-base dt").click(function() {
		select_base.select(this);
		select_base.hide_click(this);
		//同时获取当前 dt的 value 值  ,然后开始绑定
		var inputValue = $(this).attr("inputValue");
		var inputName = $(this).attr("inputName");
		
		var inputTextName = $(this).attr("inputTextName");
		var inputTextValue = $(this).attr("inputTextValue");
		
		var myMethod = $(this).attr("myMethod");
		if(inputTextName!=undefined){
			$("input[name='"+inputTextName+"']").val(inputTextValue);
		}
		if (inputName!=undefined) {
			$("input[name='"+inputName+"']").val(inputValue);
			
			//ajax处理
			if (myMethod) {
				//调用自己的方法
				var  obj  = "input[name='"+inputName+"']";
				var    ajaxMethodString   =  myMethod+'("'+obj+'")';
				eval(ajaxMethodString);
			}
		}
		
		
	});	
	
}


/**
 * 邓晓辉
 * 批量获取codeString 构成  1,2,3,4  传入后台批量删除 或者单个删除
 * @param codeName  
 * @param url 请求路径
 * @param paramName传递参数名称  
 * @return
 */
function deleteBatchCodeOrSingle(codeName,url,paramName,msg){
   var  codes  =  $("input[name='"+codeName+"']:checked");
		if(codes==undefined||$(codes).size()<=0){
			msgBox("请选择你要删除的数据！", "pass", 2600);
			return; 
		}
		if(!msg){
			msg  =  "您确定要删除所选数据？";
		}
		confirm_custom(msg,function my(){
			var codesString= new Array();// code字符串
			$(codes).each(
					function(i){
						codesString.push($(this).val());
					}
			);
			window.location.href=url+"?"+paramName+"="+codesString.join(",");
			msgBox("删除成功！", "pass", 2600);
			
			
		});
}
/**
 * 邓晓辉
 * 批量获取   单个删除
 * @param code  
 * @param url 请求路径
 * @param paramName传递参数名称  
 * @return
 */
function deletebySingle(code,url,paramName,msg){
	if(!msg){
		msg  =  "您确定要删除该数据？";
	}
    confirm_custom(msg,function my(){
    	 window.location.href=url+"?"+paramName+"="+code;
    	 msgBox("删除成功！", "pass", 2600);
    });
}


/**
 * 跳入到添加页面
 * @param url
 * @return
 */
function addUI(url){
	
	window.location.href=url;
}
/**
 * 取消功能
 * @param url
 * @return
 */
function canCel(url){
	
	window.location.href=url;
	
}


/**
 * 保存功能
 * @param url 请求地址（地址中可能传递参数）
 * @param formId 表单id  表单id
 * @return
 */
function save(url,formId){
	$("#"+formId).attr('action',url);
	$("#"+formId).submit();
}


/**
 * 更新数据
 * @param url
 * @param params
 * @return
 */
function update(url,params){
	window.location.href=url+"?"+params;
	
}

/**
 * 前台跳转页面
 * @param url
 * @return
 */
function  skipUrl(url){
	window.location.href=url;
}

/**
 * 清除内容中的所有html标签
 */
function removeTag(str){
	return str.replace(/<[^>]+>/g,"");
}

/**
 * 显示天气预报
 * @param divId 
 * @param areaname 地区名称 （比如lasa,chengdu,）
 * @return
 */
function  showCrWeather(divId,areaname){
	var  weather= '<iframe src="http://cache.xixik.com.cn/11/'+areaname+'/" width="255" height="20" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>';
    $("#"+divId).html(weather);
}
/**
 * 初始化下拉框
 */
function initSelect(){
	var divs = $(".select-base");
	for(var i=0; i < divs.length; i++){
		var div = divs.eq(i);
		var value = div.find("input").val();
		var dtValue = div.find("dt[name='"+value+"']").text();
		if(dtValue){
			div.find("i").text(dtValue);
		}
		//console.log(dtValue);
	}
}

/**
 * 前台加载景点下拉框
 * dropListUlId：ul的id
 * destinationCode： 目的地code
 */
function loadPortalView(dropListUlId, destinationCodeFront){
	$("#"+dropListUlId).html("");
	$("#"+dropListUlId).parent().find("label").text("相关景点");
	$("#"+dropListUlId).parent().find("input").val("");
	var element = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>全部景点</a></li>";
	$("#"+dropListUlId).append(element);
	if(destinationCodeFront!=""){
		var url = contentPath+"/web/readTibetSgPassMgController/getViewByDescode";
		$.post(url, {destinationCode: destinationCodeFront}, function(data){
			for(var i = 0; i < data.length; i++){
				var element = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value='"+data[i].code+"'>"+data[i].viewName+"</a></li>";
				$("#"+dropListUlId).append(element);
			}
		},"json");
	}
}








