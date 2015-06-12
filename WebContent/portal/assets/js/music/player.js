define(function(require, exports, module) {
	require('jplayer');
	exports.init = function() {
		$('[data-toggle="music"]').each(function(index, el) {
			var $this = $(this),
				_parent = '#' + $this.parent().attr('id'),
				_id = $this.attr('id'),
				$url = $this.data('url'),
				$player = $('#' + _id)
			$player.jPlayer({
				preload:'none',
				ready: function(event) {
					$(this).jPlayer("setMedia", {
						mp3: $url
					})
				},
				timeupdate: function(event) {
					var _percent = parseInt(event.jPlayer.status.currentPercentAbsolute, 10)
					var $range = $('[data-mid="' + _id + '"]')
					var slider = $range.data("ionRangeSlider");
					slider.update({from: _percent});
					/*$(_parent).find('.move-wrap').nstSlider('set_position', _percent)
					var _percent = parseInt(event.jPlayer.status.currentPercentAbsolute, 10) + '%';
					$(_parent).find('.mp-btn-move').css('left', _percent);
					$(_parent).find('.m-progress-move').css('width', _percent);*/
				},
				play: function(event) {
					$(this).jPlayer("pauseOthers");
					$('.play', _parent).addClass('active').siblings().removeClass('active')
				},
				pause: function(event) {
					var _status = $(_parent).data('status')
					var ele = _status === 'stop' ? '.stop' : '.pause'
					if(_status === 'play'){
						$(event.target).jPlayer("setMedia", {
							mp3: $url
						});
						$(_parent).find('.J_control>a').removeClass('active').last().addClass('active')
					}else{
						$(ele, _parent).addClass('active').siblings().removeClass('active')
					}
				},
				ended: function(event) {
					// 歌曲播放结束时执行该回调
					var $range = $('[data-mid="' + _id + '"]')
					var slider = $range.data("ionRangeSlider");
					slider.update({from: 0});
					$('.pause', _parent).removeClass('active').siblings().removeClass('active')
				},
				cssSelectorAncestor: _parent,
				supplied: 'mp3',
				wmode: 'window'
			})

			// 注册播放事件
			$('.play', _parent).on('click', function(event) {
					event.preventDefault();
					$player.jPlayer('play')
					$(_parent).data('status', 'play')
				})
				// 注册暂停事件
			$('.pause', _parent).on('click', function(event) {
					event.preventDefault();
					$player.jPlayer('pause')
					$(_parent).data('status', 'pause')
				})
				// 注册停止事件
			$('.stop', _parent).on('click', function(event) {
					event.preventDefault();
					$player.jPlayer('stop')
					$(_parent).data('status', 'stop')
					$(this, _parent).addClass('active').siblings().removeClass('active')
				})
				// 是否开启静音
			$('.volume', _parent).on('click', function(event) {
				event.preventDefault();
				var _off = $(this).hasClass('off')
				if (!_off) {
					$(this).addClass('off')
					$player.jPlayer('mute')
				} else {
					$(this).removeClass('off')
					$player.jPlayer('unmute')
				}
			})
		})
	}
})