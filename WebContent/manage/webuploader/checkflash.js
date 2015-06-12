/*var flashVersion = (function() {
	var version;
	try {
		version = navigator.plugins['Shockwave Flash'];
		version = version.description;
	} catch (ex) {
		try {
			version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
					.GetVariable('$version');
		} catch (ex2) {
			version = '0.0';
		}
	}
	version = version.match(/\d+/g);
	return parseFloat(version[0] + '.' + version[1], 10);
})();
*/
$(function () {
	var version;
	try {
		version = navigator.plugins['Shockwave Flash'];
		version = version.description;
	} catch (ex) {
		try {
			version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash').GetVariable('$version');
		} catch (ex2) {
			version = '0.0';
		}
	}
	version = version.match(/\d+/g);
	version = parseFloat(version[0] + '.' + version[1], 10);
	//alert(version);
	if (version!=0 && version < 12 ) {
		alert("Flash版本太低，上传组件可能无法使用，建议安装最新版本。");
	} else if(version==0){
		alert("上传组件无法使用，需要安装Flash。");
	}
})


