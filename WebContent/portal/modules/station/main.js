define('station/main', ['jquery'], function(require, exports, module) {
  /**
   * 沿线看点 widget
   * @author xuqs
   */
  var $ = require('jquery');
  (function($) {
    var pluginName = 'station',
      defaults = {
        prevArrow: '.route-prev',
        nextArrow: '.route-next'
      }

    function Station(element, options) {
      this.element = element;
      this.$elem = $(this.element);
      this.options = $.extend({}, defaults, options);
      this._defaults = defaults;
      this.init()
    }
    Station.prototype = {
      init: function() {
        this.$prev = $(this.options.prevArrow)
        this.$next = $(this.options.nextArrow)
        var $target = this.$elem.children('li'),
          _self = this;
        this._index = 0;
        // 默认显示 5 个节点
        this._showNode = 5;
        this._length = $target.length;
        this._width = $target.outerWidth(true);
        this.$elem.css('width', this._length * this._width);
        this._node = this._length - this._showNode

        $target.off().on('click', 'a', function(event) {
          event.preventDefault();
          $(this).addClass('active').parent().siblings().children('a').removeClass('active')
        });

        if (this._index === 0) {
          this.$prev.addClass('disable')
        }else{
          this.$prev.removeClass('disable')
        }

        if (this._index === this._node || this._node < 0) {
          this.$next.addClass('disable')
        }else{
          this.$next.removeClass('disable')
        }

        this.$prev.off().click(function(event) {
          event.preventDefault();
          if (!$(this).hasClass('disable')) {
            _self.prevArrow()
          };
        });
        this.$next.off().click(function(event) {
          event.preventDefault();
          if (!$(this).hasClass('disable')) {
            _self.nextArrow()
          };
        });
      },
      prevArrow: function() {
        this._index--;
        this.setAnimate()
      },
      nextArrow: function() {
        this._index++;
        this.setAnimate()
      },
      setAnimate: function() {
        if (this._index >= this._node) {
          this._index = this._node
          this.$next.addClass('disable')
        } else {
          this.$next.removeClass('disable');
        }
        if (this._index <= 0) {
          this._index = 0
          this.$prev.addClass('disable');
        } else {
          this.$prev.removeClass('disable');
        }
        this.$elem.stop().animate({
          marginLeft: -(this._index * this._width)
        });
      }
    }
    $.fn[pluginName] = function(options) {
      return this.each(function() {
        new Station(this, options);
      });
    }
  })(jQuery);
})
