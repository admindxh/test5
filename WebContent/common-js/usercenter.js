/**
	  **分页
	  */
		seajs.use(['jquery','avalon','paginator'], function($,avalon) {
		   var _model = avalon.define({
	    		$id: 'view',
	    		list:[],
	    		listP:[],
	    		listS:[],
	    		listA:[],
	    		Stype:[],
	    		Ptype:[],
	    		type:'mess',
	    		delMess: function(code){
	    			delMess(code)
	    		},
	    		delStra: function(code){
	    			delStra(code)
	    		},
	    		delPost: function(code){
	    			delPost(code)
	    		},
	    		delActi: function(code){
	    			delActi(code)
	    		},
	    		pay: function(summ,code){
	    			pay(summ,code)
	    		},
	    		linkTo:function(url){
	    		   linkTo(url)
	    		}
	    	})
            avalon.scan()
		   function loadData(type, currentPage,code,state){
		   		_model.type = type
		        var url='';
		        var data={};
		        if(type=='mess'){
		           url='member/userinfo/getAllMyMsgPage';
		           data={"currentPage": currentPage}
		        }else if(type=='stra'){
		           url='portal/userStrategy/getMyStratPage';
		           data={"currentPage": currentPage,"proCode":code,"judgeState":state}
		        }else if(type=='post'){
		           url='portal/userPost/getPostListPage';
		           data={"currentPage": currentPage,"proCode":code,"judgeState":state}
		        }else if(type=='acti'){
		           url='portal/userActivity/getActListPage';
		           data={"currentPage": currentPage,"paystate":code,"state":state}
		        }
		        //点击返回时，样式变化
		        $("a").removeClass("active");
		        $("a[name="+_model.type+"]").addClass("active");
		        
		       currentPage = currentPage || 1;
		    	$.ajax({
		    		url: contentPath+''+url,
		    		data:data,
		    		type:'post',
		    		dataType: 'json',
		    		success: function(data){
		    			if(type=='mess'){
		 		            //点击了主页,取消显示 提示信息
		    				$('.badge').remove();
		 		        }
		    		   if(data.dataList.length){
			    			createPage(data.currentPage, data.totalPage, type,code,state);
			    			$(".nodata").remove();
		    			}else{
		    			    $(".nodata").remove();
		    			    $('#nodata').append('<div class="nodata"></div>');
		    				$('#paging').empty()
		    			}
		    			if(type=='mess'){
		    			  _model.list = data.dataList;
		    			}else if(type=='stra'){
		    			  _model.listS=data.dataList;
		    			}else if(type=='post'){
		    			  _model.listP=data.dataList;
		    			}else if(type=='acti'){
		    			  _model.listA=data.dataList;
		    			}
		    		}
		    	})
	    	}
	        var type=$('#mtype').val();
	    	loadData(type)
		
			$(document).on('click', '.cancel', function() {
				var $this = $(this);
				//Do Something
				$this.parent().remove(); //删除当前行
			});

			$(".navigation ul li").on('click', function() {
				$(this).addClass('active').nextAll('li').removeClass('active');
				$(this).prevAll('li').removeClass('active');
			});
			
			function createPage(currentPage, totalPage, type,code,state){
		       if(totalPage>=1){
		        var options = {
			     	currentPage: currentPage || 1,
			     	totalPages: totalPage || 1,
			     	//removeClass:'paging-white',
			     	onPageChanged: function(e, oldPage, newPage){
			     		loadData(type, newPage,code,state)
			     	}
				}}
				//分页按钮
				$('#paging').bootstrapPaginator(options);
	      }
			
		

		$(".J-tabs a").on('click',function(){
			//alert('ad');
			var $this = $(this);
			var type=$this.attr('name');
			$('#mtype').val(type);
			if(type=='stra'){
			  $.ajax({
		    		url: contentPath+"portal/userStrategy/straType",
		    		dataType: 'json',
		    		success: function(data){
		    		   _model.Stype=data.straType;
		    		}
		    	})
			}
			if(type=='post'){
			    $.ajax({
		    		url: contentPath+"portal/userPost/getProType",
		    		dataType: 'json',
		    		success: function(data){
		    		    _model.Ptype=data.postType;
		    		}
		    	})
			}
			$this.addClass('active').siblings().removeClass('active')
		    loadData($this.attr('name'))
		    
		   
		});
		
           
			$(document).on('click', '.dropdown-menu li a', function(){
			    $(this).parent().parent().siblings('input[type=hidden]').val($(this).attr('value'));
			   var typ=$('#mtype').val();
			   
			   if(typ=='stra'){
				    var procode=$('#straType').val();
				    var state=$('#straState').val();
				    
					//location.href=contentPath+'portal/userStrategy/getMyStrat?proCode='+procode+'&judgeState='+state;
					loadData(typ,1,procode,state);
				}
				if(typ=='post'){
				   var procode=$('#postType').val();
				   var state=$('#postState').val();
				   //location.href=contentPath+'portal/userPost/getPostList?proCode='+procode+'&judgeState='+state;
				   loadData(typ,1,procode,state);
				}
				if(typ=='acti'){
				   var paystate=$('#paystate').val();
				   var state=$('#astate').val();
				 //  location.href=contentPath+'portal/userActivity/getActList?paystate='+paystate+'&state='+state;
				  loadData(typ,1,paystate,state);
				}
			})
		});

	})
	 //删除消息
       function delMess(obj){
			 var _alert = new Alert();
	          _alert.show("确定删除当前记录？",function(){
		         location.href=contentPath+'member/userinfo/delMsg?msgCode='+obj;
	         });
       }
       //阅读信息
       function readMess(code){
          if(code!=''){
              $.ajax({
	     			type : "post",
	     			url : "<%=basePath%>member/userinfo/readMsg",
	     			data : {msgCode:code},
	     			dataType : "json",
	     			async : true,
	     			success : function(data) {
	     			    
	     	        }
	       })
          }
       }
       //删除攻略
       function delStra(obj){
    	   var _alert = new Alert();
          _alert.show("确定删除当前记录？",function(){
        	  location.href=contentPath+'portal/userStrategy/deleteMyStra?code='+obj;
              });
       }
       //删除帖子
       function delPost(obj){
    	   var _alert = new Alert();
           _alert.show("确定删除当前记录？",function(){
        	   location.href=contentPath+'portal/userPost/deletePost?id='+obj;
               });
       }
       //删除活动
       function delActi(obj){
    	   var _alert = new Alert();
           _alert.show("确定删除当前记录？",function(){
        	   location.href=contentPath+'portal/userActivity/deleteAct?code='+obj;
               });
       }
       //支付
	  	function pay(obj,code){
	  	    if(code==''){
	  	      alert("Code不能为空");
	  	      return;
	  	    }
	  		if(obj=='End'){
	  			alert("活动已结束");
	  			return;
	  		}else{
		  		var url = contentPath+'portal/order/checkPay";
		  		$.post(url, {activityCode: code}, function(response){
		  			if(response=='need_login'){
		  				//alert("请先登录");
		  				// 打开 登录框
						$('[data-toggle="login"]').click();
						return;
		  			}else if(response=='need_enroll'){
		  				alert("请先报名");
		  				return;
		  			}else if(response=='already_pay'){
		  				alert("已经支付");
		  				return;
		  			}else if(response=='to_pay'){
		  				location.href=contentPath+'/alipay/pay?activityCode="+code;
		  		  		//$("#payForm").submit();
		  			}
		  		});
	  		}
	  	}
       	function linkTo(url){
       		 location.href=contentPath+''+url;
       	}
	seajs.use(["jquery", "bootstrap/3.3.1/js/dropdown.js"],function($){
		$('[data-toggle="dropdown"]').dropdown();
		$(function(){
			$(".J-tabs a[name='${type}']").click();
			});
	});
	seajs.use(ctxPortal+'/assets/js/tourism/strategy_list.js');  //  下拉菜单的效果

	  seajs.use('map', function(myMap){
	  	var locat = ${locat}
	  	// 初始化景点地图
	  	/* 初始化景点地图
	  	 * @param  {[string]}   city   在地图中高亮显示该目的地区域。比如：拉萨市
	   	 * @param  {[array object]}   spots    景点数据，包含想去和去过的数据
	     * @param  {Function} callback 点击地图回调函数
	  	*/
	  	myMap.init('西藏', locat, mapClick)
	  	// 响应地图点击事件
	  	function mapClick(param){
	  		//console.log(param)
	  	}
	  })