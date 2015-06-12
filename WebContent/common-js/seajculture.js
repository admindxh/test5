seajs.use(ctxPortal+'assets/css/index_culture.css');
	seajs.use('jquery', function(){
		$('.clt-other>a').on('click', function(){
			var $index = $(this).index()
			$(this).addClass('active').siblings().removeClass('active')
			$(this).parents('.xz-heading').siblings('.music').eq($index).show().siblings('.music').hide()
		})
	});
	seajs.use(['jquery','raty/jquery.raty.js'],function($){
		$('.m-score').raty({
			readOnly: true,
			space: true,
			score: function() {
				var $this = $(this),
					score = $this.data('score') || ''
					$this.append('&nbsp;&nbsp;<span>' + score + '</span>')
				return (score);
			},
			half: true,
			starHalf: ctxPortal+'assets/icon/star_half.png',
			starOff: ctxPortal+'assets/icon/star_zero.png',
			starOn: ctxPortal+'assets/icon/star_full.png'
			
		})
});