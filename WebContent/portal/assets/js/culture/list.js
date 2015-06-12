		function order(type)
		{	
			$("#oder").val(type);
			$("#cpage").val(1);
			$("#frm").submit();
		}
		seajs.use(['common/css','footer/css']);
		seajs.use(['jquery','avalon','paginator'], function(avalon) {
		//console.log(1);
		var options = {
        currentPage: currentPage_,
        totalPages: totalPage_,
        onPageClicked: function(e,originalEvent,type,page)
        {
        	$("#cpage").val(page);
        	$("#frm").submit();
        }
      };
     $('[data-toggle="paging"]').bootstrapPaginator(options);});
     
   	seajs.use(basePathPortal+'assets/js/subStri.js',function(str){
            str.strClip('.js_sub_content',160);
        });
     seajs.use('jquery',function($){
     function classify(){
     	var idTemp = type_;
      		if(idTemp==""||idTemp==null||idTemp=="null"){
      			idTemp = 1120;
      		}
      		$("#"+idTemp).addClass("active");
     }
     function orderClass(){
     	var aNodeActive = null;
     	aNodeActive = orderType_;
     	if(aNodeActive==""||aNodeActive==null||aNodeActive=="null"){
     		aNodeActive = 100;
     	}
     	$("#"+aNodeActive).addClass("active down");
     }
     	$(function(){
      		classify();
      		orderClass();
      });
     });
	
			seajs.use(['avalon', 'jquery',basePathPortal+'assets/js/subStri.js'], function(avalon,$,str) {
			var hotModel = avalon.define({
				$id: "hotView",
				list:[]			
			})
			var otherModel = avalon.define({
				$id: "otherView",
				list:[]			
			})
			function loadOthersData(model,params)
			{
				$.get(encodeURI('others?'+params), function(rep){
					model.list= rep.dataList;
				}, 'json');
			}
			function loadOthersData2(model,params)
			{
				$.get(encodeURI('othercontent?'+params), function(rep){
					model.list= rep.dataList;
				}, 'json');
			}
			var params1='pageSize=5&currentPage=1&contentType='+type_+'&orderType=201';
			var params2='pageSize=5&currentPage=1&contentType='+type_+'&orderType=201';
			$(function(){
				avalon.scan();				
				loadOthersData(hotModel,params1);
				loadOthersData2(otherModel,params2);
				seajs.use(basePathPortal+'assets/js/subStri.js',function(str){
		            setTimeout(function(){str.strClip('.js_sub_summary',25);},100);
		        });
			});
		});
