		function orderSearch(order){
			$("#orderId").val(order);
			$("#searchForm").submit();
		}	
		function   searchInfo(){
			$.ajax({
				type:"post",
				url:basePath_+'/tourism/strage/intoTraval',
				data:$("#searchForm").serialize(),
				async:true,
				success:function(){
				      
				
				}
			});
			
		}
			 // Set configuration
			
			seajs.use(['bootstrap/css', 'header/css', 'footer/css']);
			seajs.use("bootstrap/3.3.1/js/dropdown.js",function(){
				$('[data-toggle="dropdown"]').dropdown();
			});
			  seajs.use(basePathPortal+'/assets/js/subStri.js',function(str){
		            
	            	str.strClip('.js_sub_info',100);
		        });
			seajs.use(basePathPortal+'/assets/js/tourism/strategy_list.js');
	  		 seajs.use(["jquery", "avalon", "map", "paginator", "commonjs"], function($, avalon, myMap){
	  			myMap.init('西藏')
		//
		//分页
	  	
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
		var allActivityVM = avalon.define({
			$id:'allActivityView',
			data:[],
			$cacheData:{}
		});
		function getAllActivityList(currentPage){
			var thisCall = getAllActivityList;
			//判断缓存没有就请求服务器
		/*	if(allActivityVM.$cacheData[currentPage]){
				allActivityVM.data = allActivityVM.$cacheData[currentPage];
				seajs.use(basePathPortal+'/assets/js/subStri.js',function(str){
	            	str.strClip('.js_sub_info',100);
		        });
			}else{*/
				$.post(basePath_+'/tourism/strage/getTravalIndexActivityList', {isOfficial:isOfficial_,programCode:programCode_,des:des_,view:view_,order:order,keyword:keyword_,currentPage: currentPage, pageSize: 10}, function(response){
					allActivityVM.$cacheData[currentPage]={};
					servicesPage("allActivityPage", response.currentPage, response.totalPage, thisCall);
				  	allActivityVM.data = response.dataList;
				    var mapview = [];
				  	for(var i = 0; i < response.dataList.length; i++){
				  		var   viewlist =  response.dataList[i].viewList;
				  		mapview = mapview.concat(viewlist)
					}
				  	myMap.setGeoData($('#region-menu>label').text(), mapview);
				  	seajs.use(basePathPortal+'/assets/js/subStri.js',function(str){
		            	str.strClip('.js_sub_info',50);
			        });

				  	if(response.dataList.length<=0){
				  			$(".nodata").remove();
				  		    $("#strategy").append('<div class="nodata" ></div>')
							$("#allActivityPage").remove();
					  	}else{
					  		$(".nodata").remove();
					  	}
			        
				}, 'json');
			//}
		}
		avalon.scan();
		function loadData(){
			getAllActivityList(1);
		}
		function loadData1(){
			getInfoTourm();
			getAllActivityList(1);
		}
		loadData();
		function getInfoTourm(){
			isOfficial_  =  $("#isOfficial").val();
			programCode_  =  $("#programCode").val();
			des_  =  $("#des").val();
			view_  =  $("#view1").val();
			order  =  $("#orderId").val();
			keyword_  =  $("#keyword").val();
			
			
		}
		function loadPortalView1(dropListUlId, destinationCodeFront){
			$("#"+dropListUlId).html("");
			$("#"+dropListUlId).parent().find("label").text("相关景点");
			$("#"+dropListUlId).parent().find("input").val("");
			var element = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>全部景点</a></li>";
			$("#"+dropListUlId).append(element);
			if(destinationCodeFront!=""){
				var url = basePath_+"/web/readTibetSgPassMgController/getViewByDescode";
				$.post(url, {destinationCode: destinationCodeFront}, function(data){
					for(var i = 0; i < data.length; i++){
						var element = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value='"+data[i].code+"'>"+data[i].viewName+"</a></li>";
						$("#"+dropListUlId).append(element);
					}
					$("#diviews a").click(function(){
					    var code = $(this).attr("value");
					    var aNodeText = $(this).text();
			            var listValue = $(this).attr('value');
			            $(this).parent(".dropdown-menu").siblings(".dropdown-toggle").children('label').text(aNodeText);
			            $(this).parent(".dropdown-menu").siblings("input").val(listValue);
					    loadData1();
				});
				},"json");
			}
		}
		$(function(){
				$("#zx").click(function(){
					getInfoTourm();
					order= 1;
					loadData();
					$("#zx").parent().addClass("down");
					$("#sc").parent().removeClass();
					$("#pl").parent().removeClass();
				});
				$("#sc").click(function(){
					getInfoTourm();
					order= 2;
					loadData();
					$("#zx").parent().removeClass();
					$("#sc").parent().addClass("down");
					$("#pl").parent().removeClass();
				});
				$("#pl").click(function(){
					getInfoTourm();
					order= 3;
					loadData();
					$("#zx").parent().removeClass();
					$("#sc").parent().removeClass();
					$("#pl").parent().addClass("down");
				});
				$("#destinationDropList a").click(function(){
					    var code = $(this).attr("value");
					    loadPortalView1("view", code);
					    var aNodeText = $(this).text();
			            var listValue = $(this).attr('value');
			            $(this).parent(".dropdown-menu").siblings(".dropdown-toggle").children('label').text(aNodeText);
			            $(this).parent(".dropdown-menu").siblings("input").val(listValue);
					    loadData1();
				});
				$("#glss,#gllx   a").click(function(){
				    var code = $(this).attr("value");
				    //loadPortalView1("view", code);
				    if(!code){
				    	code = "";
				    }
				    var aNodeText = $(this).text();
		            var listValue = $(this).attr('value');
		            $(this).parent().parent(".dropdown-menu").siblings(".dropdown-toggle").children('label').text(aNodeText);
		            $(this).parent().parent(".dropdown-menu").siblings("input").val(listValue);
				    loadData1();
			    });
			    $("#searchstrety").click(function(){
					getInfoTourm();
					loadData();
				});
				
		})
	 });
	   
		
			//上传攻略
			function  uploadStrage(){
					if(checkPortalUserLongin()){
						         var  href  = window.location.href;
						 		window.open(basePath_+'tourism/upload/intoStragePage?sourceHref='+href);	
				      }else{
				 			 //alert('请登录！登录后可以上传');
				 			$('[data-toggle="login"]').click();
				        }
				}

		    function adver(href,code){
				    frontContentStatic('14dbad51-cbsb-46d1-b5ef-b3838670b3a9','content',code,'click');
				    window.open(href);
			    }
