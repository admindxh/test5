    // Set configuration
    
    seajs.use("avalon");
    seajs.use(['common/css', 'footer/css','activities/css']);
    
    //seajs.use('activities/js');

//记录排序方式
var orderBy=ORDERBY_DATE_;
//记录活动当前页 
var actCurrentPage=1;
//记录专题当前页
var speCurrentPage=1;
//当前选项卡
var currentTab="spe";
//分页
seajs.use(["jquery", "avalon", "paginator", "commonjs", "dateUtil"], function(){
	avalon.filters.clearHTML = function(str){
    	return str.replace(/<[^>]+>/g,"");
 	}
	function servicesPage(pageId, currentPage, totalPage, call_){
		var options = {
	     	currentPage: currentPage || 1,
	     	totalPages: totalPage || 1,
	     	//removeClass:'paging-white',
	     	onPageChanged: function(e,oldPage,newPage){
	     		call_(newPage);
	    	}
		}
		//分页按钮
		$('#'+pageId).bootstrapPaginator(options);
	}
	//活动
	var allActivityVM = avalon.define({
		$id:'allActivityView',
		data:[],
		$cacheData:{}
	});
	function getAllActivityList(currentPage){
		var thisCall = getAllActivityList;
		actCurrentPage=currentPage;
		$.post(basePath_+'portal/activityController/getActivitiesPageList', {currentPage: currentPage, pageSize: 18, orderBy: orderBy}, function(response){
		  	servicesPage("allActivityPage", response.currentPage, response.totalPage, thisCall);
		  	allActivityVM.data = response.dataList;
			avalon.scan();
		}, 'json');
	}
	//专题
	var allSpecialVM = avalon.define({
		$id:'allSpecialView',
		data:[],
		$cacheData:{}
	});
	function getAllSpecialList(currentPage){
		var thisCall = getAllSpecialList;
		speCurrentPage=currentPage;
		$.post(basePath_+'portal/specialController/getSpecialListOrderBy', {currentPage: currentPage, pageSize: 18, orderBy: orderBy}, function(response){
		  	servicesPage("allSpecialPage", response.currentPage, response.totalPage, thisCall);
		  	allSpecialVM.data = response.dataList;
			avalon.scan();
		}, 'json');
	}
	function loadData(){
		//$("#subject").trigger("click");
		//getAllActivityList(1);
		getAllSpecialList(1);
	}
	$(document).ready(function(){
		loadData();
	});
	
	
	//--
	$("#activity").click(function(){               //切换到活动列表
		currentTab="act";
		getAllActivityList(1);
	    $(this).addClass('activity-active').removeClass('activity-inactive');
	    $("#subject").addClass('subject-inactive').removeClass('subject-active');
	    $("#activity-content").addClass('activity-block').removeClass('activity-none');
	    $("#subject-content").addClass('activity-none').removeClass('activity-block');
	});
	$("#subject").click(function(){               //切换到专题列表
		currentTab="spe";
		getAllSpecialList(1);
	    $(this).addClass('subject-active').removeClass('subject-inactive');
	    $("#activity").addClass('activity-inactive').removeClass('activity-active');
	    $("#subject-content").addClass('activity-block').removeClass('activity-none');
	    $("#activity-content").addClass('activity-none').removeClass('activity-block');
	});
	$("#latest-release").on('click',function(){         //按最新发布查询
		orderBy=ORDERBY_DATE_;
		if(currentTab=="act"){
			getAllActivityList(actCurrentPage);
		}
		if(currentTab=="spe"){
			getAllSpecialList(speCurrentPage);
		}
	    $(this).addClass('active').removeClass('inactive');
	    $("#hot").addClass('inactive').removeClass('active');
	});
	$("#hot").on('click',function(){                    //按热度查询
		orderBy=ORDERBY_CHECKNUM_;
		if(currentTab=="act"){
			getAllActivityList(actCurrentPage);
		}
		if(currentTab=="spe"){
			getAllSpecialList(speCurrentPage);
		}
	    $(this).addClass('active').removeClass('inactive');
	    $("#latest-release").addClass('inactive').removeClass('active');
	});
});
