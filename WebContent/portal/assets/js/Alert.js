var Alert = function() {}
Alert.prototype.show = function(text, callback) {
	var __alert = document.getElementById('J_AlertWrap')
	if(__alert){return false}
	var _self = this
	text = text || '提示内容'
	var _template = '<div class="rimi-alert-mark"></div><div class="rimi-alert"><div class="rimi-alert-header"><a href="javascript:" class="rimi-alert-close" id="J_close"></a></div><div class="rimi-alert-body">' + text + '</div><div class="rimi-alert-bottom"><button class="btn btn-default btn-sm" id="J_sure">确定</button><button class="btn btn-danger btn-sm" id="J_canel">取消</button></div></div>'
	var elem = document.createElement('div')
	elem.className = 'rimi-alert-wrap'
	elem.id = 'J_AlertWrap'
	elem.innerHTML = _template
	
	document.body.appendChild(elem)
	
	document.getElementById('J_close').onclick = function() {
		_self.destroy()
	}
	document.getElementById('J_canel').onclick = function() {
		_self.destroy()
	}
	document.getElementById('J_sure').onclick = function(){
		_self.destroy()
		if (typeof callback === 'function') {
			callback.call(this)
		}
	}
}

Alert.prototype.destroy = function() {
	document.body.removeChild(document.getElementById('J_AlertWrap'))
}