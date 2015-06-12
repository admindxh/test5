    // Set configuration
   
    seajs.use('avalon', function(avalon) {
        avalon.define({
            $id: "indexView"
        })
    })
    seajs.use("bootstrap/3.3.1/js/dropdown.js",function(){
        $('[data-toggle="dropdown"]').dropdown();
    });
	//记录收索框的值
	var keyWords = "";
	//记录排序
	var orderBy = ORDER_WANTCOUNT_;
	
	var destinationCode = "";
	var viewCode = "";
	
	seajs.use(["jquery"], function($){
		
		$(function(){
			$('input[placeholder]').placeholder();
		});
		
	});
	
	seajs.use(["jquery", "avalon", "map", "paginator"], function($, avalon, myMap){

		myMap.init('西藏');
		function initSelect(){
			var divs = $(".drop-down");
			for(var i=0; i < divs.length; i++){
				var div = divs.eq(i);
				var value = div.find("input").val();
				var dtValue = div.find("a[value='"+value+"']").text();
				if(dtValue){
					div.find("button>label").text(dtValue);
					destinationCode = destinationCode_;
					loadPortalView("viewDorpListOption", value);
				}
			}
		}
		
		
		//分页
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
		var viewVM = avalon.define({
			$id:'viewView',
			data:[],
			$cacheData:{}
		});
		function loadViewList(currentPage){
			var thisCall = loadViewList;
			var pageSize = 15;
			//var pageSize = 2;
			var param = {
				destinationCode: destinationCode,
				viewCode: viewCode,
				keyWords: keyWords, 
				orderBy: orderBy, 
				currentPage: currentPage, 
				pageSize: pageSize
			};
			$.post(basePath_+'tourism/view/searchViewList', param, function(response){
				/* for (var i = 0; i < response.dataList.length; i++) {
					var obj = response.dataList[i];
					if(obj.viewImage){
						obj.img = obj.viewImage.split(",")[0];
					}
				} */
			  	servicesPage("viewPaging", response.currentPage, response.totalPage, thisCall);
			  	viewVM.data = viewVM.$cacheData[currentPage] = response.dataList;
			  	avalon.scan();
			  	if(response.dataList.length==0){
			  		$("#viewPaging").hide();
			  		$("#J_nodata").show();
			  	}else{
			  		$("#viewPaging").show();
			  		$("#J_nodata").hide();
			  	}
				myMap.setGeoData($('#region-menu>label').text(), response.dataList)
			}, 'json');
		}
		function loadData(){
			//loadViewList(1);
			initSelect();
			loadViewList(1);
		}
		loadData();
		
		function search(){
			keyWords = $("#keyWords").val();
			destinationCode = $("#destinationCode").val();
			viewCode = $("#viewCode").val();
			
			loadViewList(1);
		}
		$("#search").click(function(){
			search();
		});
		$(".listing li").click(function(){
			var $this = $(this);
			$this.addClass("down").siblings('li').removeClass("down");
			orderBy = $this.attr("value");
			loadViewList(1);
		});
		$("#destinationDropList a").click(function(){
			var code = $(this).attr("value");
			//var name = $(this).text();
			//myMap.mapChange(name);
			loadPortalView("viewDorpListOption", code);
			
			$("#destinationCode").val(code);
			search();
		});
		$(document).on("click","#viewDropList a",function(){
			var code = $(this).attr("value");
			$("#viewCode").val(code);
			search();
		});
		function loadPortalView(dropListUlId, destinationCode){
			$("#"+dropListUlId).html("");
			$("#"+dropListUlId).parent().find("label").text("相关景点");
			$("#"+dropListUlId).parent().find("input").val("");
			var element = "<li role='presentation'><a target='_blank' role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>全部景点</a></li>";
			$("#"+dropListUlId).append(element);
			if(destinationCode!=""){
				var url = contentPath+"/web/readTibetSgPassMgController/getViewByDescode";
				$.post(url, {destinationCode: destinationCode}, function(data){
					for(var i = 0; i < data.length; i++){
						var element = "<li role='presentation'><a target='_blank' role='menuitem' tabindex='-1' href='javascript:void(0);' value='"+data[i].code+"'>"+data[i].viewName+"</a></li>";
						$("#"+dropListUlId).append(element);
					}
				},"json");
			}
		}
	});
	
	
