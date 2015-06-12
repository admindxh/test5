/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	// 界面语言，默认为 'en' 
	config.language = 'zh-cn'; 
	
	// 设置宽高 
	config.width = 1024; 
	config.height = 500; 
	
	// 工具栏是否可以被收缩 
	config.toolbarCanCollapse = false; 
	
	// 开关“拖拽以改变尺寸”功能
	config.resize_enabled = true; 
	
	// 默认的字体名
	config.font_defaultLabel = '宋体';

	// 字体编辑时的字符集
	config.font_names = '宋体;楷体;幼圆;隶书;黑体;微软雅黑;Arial;Times New Roman;Verdana';

	// 自动以工具栏按钮
	config.toolbar = 'Full'; 
	config.toolbar_Full = [
		['Source','-','Save','NewPage','Preview','-','Templates'],
		['Cut','Copy','Paste','PasteText','PasteFromWord'/*,'-','Print', 'SpellChecker', 'Scayt'*/],
		['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
		['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
		//['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
		['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
		'/',
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		['Link','Unlink','Anchor'],
		['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
		//'/',
		[/*'Styles',*/'Format','Font','FontSize'],
	 	['TextColor','BGColor']
	];
	
	// 当提交包含有此编辑器的表单时，是否自动更新元素内的数据 
	config.autoUpdateElement = true; 
	
	// 是否对编辑区域进行渲染 
	config.editingBlock = true; 
	// 图片上传的Action
	config.filebrowserUploadUrl="web/ckeditorControlller/ckeditorUpload";
	var pathName = window.document.location.pathname;
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    config.filebrowserImageUploadUrl = projectName+'/system/upload.do';
};
