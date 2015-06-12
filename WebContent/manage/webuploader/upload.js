// 图片上传demo
 var imgId
	$(".imgNode").click(
		function(){
			var temp = $(this).parent().parent().parent().find("img").attr("id");
			imgId = temp;
		}
	);
 var uploader;
$(function() {
	var $imgWrap = $('#ImageSrc'),
    
	// 优化retina, 在retina下这个值是2
	ratio = window.devicePixelRatio || 1,

	// 缩略图大小
	thumbnailWidth = 1024 * ratio,
	thumbnailHeight = 768 * ratio,
	//图片标签
	img;
	//\
	// Web Uploader实例
	//uploader;	
	
	$(".imgNode").click(
		function(){
			var temp = $(this).parent().parent().parent().find("img").attr("id");
			imgId = temp;
		}
	);
	// 初始化Web Uploader
	uploader = WebUploader.create( {
		
		// 自动上传。
		auto : true,

		// swf文件路径
		swf :  contextPath+'/manage/webuploader/Uploader.swf',

		// 文件接收服务端。
		server : contextPath+'/web/imageController/uploadImage',

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : '.pickfiles',

		// 验证文件总数量, 超出则不允许加入队列x
		fileNumLimit : 10,
		fileSizeLimit :400 * 1024,
		// 设置文件上传域的name
		fileVal : 'file',

		// 只允许选择文件，可选。
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/*'
		},
		runtimeOrder : "flash",
		compress: null
	});

	// 文件加入队列之前触发
	// 因为设置了 fileNumLimit等于1，需要重置一下当前队列，否则不能更改
	uploader.on('beforeFileQueued', function(file) {
		uploader.reset();
	})

	// 当有文件添加进来的时候
	uploader.on('fileQueued', function(file) {
		var $error = $('<div class="error"></div>')
		$img = $imgWrap.find('img');
		$imgWrap.append($error)
		// 创建缩略图
	/*		uploader.makeThumb(file, function(error, src) {
				//			if (error) {
					//				$img.replaceWith('<span>不能预览</span>');
					//				return;
					//			}
					//           
					//	$img.attr('src', src);
					//		img=src;
					//			 document.getElementById('img').src=img;
					// $('#img').attr('src')=img;
				}, thumbnailWidth, thumbnailHeight); 不创建缩略图，用ajax*/ 
		});

	// 文件上传过程中创建进度条实时显示。
	uploader.on('uploadProgress', function(file, percentage) {
		var $percent = $imgWrap.find('.progress');
		$percent.show().find('.progress-bar').css('width',percentage * 100 + '%');
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function(file) {
		$('#' + file.id).addClass('upload-state-done');
		$.ajax( {
			type : "post",
			url : contextPath+"/web/imageController/getUrl",
			dataType : "text",
			async : true,
			success : function(data) {
			// 商户特殊图片
			if(bMerchant.imgId1){
				document.getElementById(bMerchant.imgId1).src = contextPath+data;	
				//不用这种方式，采用找到当前图片紧接的input  url 标签 
				//$('#' + b.imgId).after("<input type='hidden' name='url' value='"+data+"' />");
				$("#"+bMerchant.imgId1).next('.url').val(data);
				return ;
			}
			document.getElementById(b.imgId).src = contextPath+"/"+data;	
			//不用这种方式，采用找到当前图片紧接的input  url 标签 
			//$('#' + b.imgId).after("<input type='hidden' name='url' value='"+data+"' />");
			$("#"+b.imgId).next('.url').val(data);
			 
		},
		error : function() {
			alert("数据异常");
		}
		});

	})
	/**
	 * 获取图片id
	 */
	function getImgId(){
		var temp = $(this).parent().parent().parent().find("img").attr("id");
		imgId = temp;
	}
	uploader.on( 'error', function(type) {
			//alert("错误信息:"+type);
		if(type=="Q_TYPE_DENIED"){
  			msgBox("上传格式错误", "erro");
		}else if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
			msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
		}else{
		   msgBox("上传错误，请重试。", "erro");
		}
    });
	
	// 文件上传失败，现实上传出错。
	uploader.on('uploadError', function(file) {
		//var $error = $imgWrap.find('div.error');
		//$error.show().text('上传失败');
		//图片格式错误，上传失败
		msgBox("图片格式错误，上传失败", "erro");
	});

	uploader.on('uploadBeforeSend', function(obj, data) {
		//data.id = _userId
	})

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on('uploadComplete', function(file) {
		$imgWrap.find('.progress').fadeOut();
	});
	uploader.on('error', function(handler) {
		if (handler == "Q_EXCEED_NUM_LIMIT") {
			msgBox("超出文件最大张数", "erro");
		}
		if (handler == "F_DUPLICATE") {
			msgBox("文件重复", "erro");
		}

		if (handler == "Q_EXCEED_SIZE_LIMIT") {
			//alert("文件大小超出限制");
			msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
		}
	});

});

