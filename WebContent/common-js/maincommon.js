    /**
	 * 判断当前电脑是否登录  首页
	 * @return
	 */
	function isRecored(code,vs){
		$.ajax(
            {
                url:contentPath+"/web/praiseController/isRecored",
                data:"code="+code,
                type: 'POST',
                success:function(data){
                    if(data=="true"){
                    	alert("已赞");
                        //表示改电脑已经点击赞了
                        //background-image: url(http://localhost:8080/ctibet/portal/assets/icon/zan_gray.png);
                        // $(vs).css("backgroundImage","url(${ctx}/portal/assets/icon/zan_gray.png)");//

                         $(vs).addClass("zan_active");
                         //$(vs).removeAttr('onclick');
                        // $(vs).find("img").css("backgroundImage","url(${ctx}/portal/assets/icon/zan_gray.png)");//;
                    }else{
                    	alert("点赞成功");
                        //就保存
                        clickPraiseFront(code,vs);
                        if($(vs).attr('indexSgType')){
                            var  ptye  =  $(vs).attr('indexSgType');
                            frontContentStatic(ptye,"content",code,"praise");
                        }else{
                            frontContentStatic('${content.programaCode}','content','${content.code}','praise');
                        }
                        $(vs).addClass("zan_active");
                        //$(vs).removeAttr('onclick');
                    }
                }
            }
	    )
    }
	/**
	 * 点击赞
	 * @return
	 */
	function clickPraiseFront(code,vs){
		$.ajax(
            {
                url:contentPath+"/web/praiseController/clickPraiseFront",
                data:"code="+code,
                type: 'POST',
                success:function(data){
                         $(vs).parent().find("label").html(data);
                         //天上社區frontContentStatic('tssq','conetnt','${post.code}','favate');
                          if($(vs).parent().find("label")){
                              frontContentStatic('tssq','conetnt',code,'praise');
                         }
                            //赞成功
                        $(vs).parent().find("p").html(data);
                        //攻略页面特殊位置
                        $("#praiseCount").html(data);
                        //alert('点击赞成功');

                }
            }
	    );
    }
	/**
	 * 点击 浏 览
	 * @return
	 */
	function clickViewCount(code){
		$.ajax(
            {
                url:contentPath+"/web/praiseController/clickViewCount",
                data:"code="+code,
                type: 'POST',
                success:function(data){
                }
           }
	    )
    }
	/**
	 * 判断当前电脑是否登录 收藏
	 * @return
	 */
	function isRecoredFavate(code,vs){
		if(!checkPortalUserLongin()){
				// 打开 登录框
				$('[data-toggle="login"]').click();
				return  ;
        }
        $.ajax(
            {
                url:contentPath+"/web/praiseController/isRecoredFavate",
                data:"code="+code,
                type: 'POST',
                success:function(data){
                    if(data=="true"){
                        //表示改电脑已经点收藏了
                        $(vs).addClass("star_active");
                        //alert('你已经收藏了');
                    }else{
                        //就保存
                        clickFavateFront(code,vs);
                        $(vs).addClass("star_active");
                        frontContentStatic('${content.programaCode}','content','${content.code}','favate');
                    }
                }
            }
	    )
   }
	/**
	 * 点击收藏
	 * @return
	 */
	function clickFavateFront(code,vs){
		$.ajax(
            {
                url:contentPath+"/web/praiseController/clickFavateFront",
                data:"code="+code,
                type: 'POST',
                success:function(data){
                        //攻略页面特殊位置
                        $("#favateCount").html(data);


                }
           }
	    )
    }
	/**
	 * 商户  判断当前电脑是否登录 收藏
	 * @return
	 */
	function isRecoredMerchantFavate(code,vs,flag){
		if(!checkPortalUserLongin()){
				// 打开 登录框
				$('[data-toggle="login"]').click();
				return  ;
		 }
		$.ajax(
    			{
    				url:contentPath+"/web/praiseController/isRecoredMerchantFavate",
    				data:"code="+code,
    			    type: 'POST',
    				success:function(data){
                        if(data=="true"){
                            //表示改电脑已经点收藏了
                            flag !="min"? $(vs).addClass("star_active"):$(vs).addClass("star_min_active");
                            alert('已收藏');
                        }else{
                            //就保存
                            clickFavateMerchantFront(code,vs);
                            flag !="min"? $(vs).addClass("star_active"):$(vs).addClass("star_min_active");
                            frontContentStatic($(vs).attr('merchantType'),'merchant',code,'favate');
                            alert('收藏成功');
                        }
    				}
        	   }
	    )
   }
	/**
	 * 商户  点击收藏
	 * @return
	 */
	function clickFavateMerchantFront(code,vs){
		$.ajax(
    			{
    				url:contentPath+"/web/praiseController/clickFavateMerchantFront",
    				data:"code="+code,
    			    type: 'POST',
    				success:function(data){
							//攻略页面特殊位置
						    $(vs).next('label').html(data);
						    if($("#heart")){
								$("#heart").html(data);
                            }
    				}
        	   }
	    );
    }
    //===========================xz======================
    /**
	 * 商户  判断当前电脑是否登录 收藏
	 * @return
	 */
	function isRecoredMerchantFavatexz(code,vs,flag){

		if(!checkPortalUserLongin()){
				// 打开 登录框
				$('[data-toggle="login"]').click();
				return  ;
        }
		$.ajax(
    			{
    				url:contentPath+"/web/praiseController/isRecoredMerchantFavate",
    				data:"code="+code,
    			    type: 'POST',
    				success:function(data){
								if(data=="true"){
                                   	//表示改电脑已经点收藏了
                                    flag !="min"? $(vs).addClass("star_active"):$(vs).addClass("star_min_active");
                                   //	alert('你已经收藏了');
								}else{
									//就保存
									clickFavateMerchantFrontxz(code,vs);
                                    flag !="min"? $(vs).addClass("star_active"):$(vs).addClass("star_min_active");
									frontContentStatic($(vs).attr('merchantType'),'merchant',code,'favate');
							    }
    				}
        	   }
	    )
   }
	/**
	 * 商户  点击收藏
	 * @return
	 */
	function clickFavateMerchantFrontxz(code,vs){
		$.ajax(
    			{
    				url:contentPath+"/web/praiseController/clickFavateMerchantFront",
    				data:"code="+code,
    			    type: 'POST',
    				success:function(data){
							//攻略页面特殊位置
						    $(vs).next('label').html(data);
						    if($("#heart")){
								$("#heart").html(data);
							    }
						  	//alert('点击收藏成功');

    				}
        	   }
	    )
    }
	/**
	 *  js 中获取会员用户
	 * @return
	 */
	function getFrontUser(){
		return loguser;
	}
	/**
	 * 判断当前会员用户是否登录
	 * @return
	 */
	function checkPortalUserLongin(){
		if (loguser) {
			return true;
		}else{
			return false;
		}
	}
	
    /**
    * 退出
    */
	function logout(){
    	var _alert = new Alert()
    	_alert.show("是否退出", function(){
		    window.location.href=contentPath+'portal/clientLog/logout';
    	})
	}
	function toUserCenter(){
	   if(!checkPortalUserLongin()){
				alert('请 先登录!');
				// 打开 登录框
				$('[data-toggle="login"]').click();
				return  ;
		}
	   window.location.href=contentPath+'member/userinfo/getAllMyMsg';
	}
	