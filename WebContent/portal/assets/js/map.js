define(['../../modules/echarts/echarts-all.js', '../../modules/echarts/geoJson/china-main-city/china-main-city-map.js'], function(require, exports, module) {
  var _dom = document.getElementById('map')
  var myMap,
    mapGeoData = echarts.util.mapData.params,
    mapType = [], cityName = {}, __spots, geoFlag = false;
  for (var city in cityMap) {
    mapType.push(city)
      // 自定义扩展地图类型
    mapGeoData.params[city] = {
      getGeoJson: (function(c) {
        var geoJsonName = cityMap[c]
        return function(callback) {
          $.getJSON(contentPath + 'portal/modules/echarts/geoJson/china-main-city/' + geoJsonName + '.json?' + (new Date() - 0), callback).done(function(data){
            cityName[c] = geoJsonName
            myMap.hideLoading()
          })
        }
      })(city)
    }
  }


  function createScale(){
    var html = '<div class="scale-wrap"><a href="javascript:;" class="scale-max"></a><a href="javascript:;" class="scale-min"></a></div>'
    $(_dom).after(html)

  }
  var currentCity = '西藏'

  // 地图放大
  $(document).on('click', '.scale-max', function(event) {
    event.preventDefault();
    option.series[0].mapLocation.width += 100
    option.series[0].mapLocation.height += 100
    myMap.setOption(option, true)
    $(this).siblings('.scale-min').removeClass('scale-disable')
  });

  // 地图缩小
  $(document).on('click', '.scale-min', function(event) {
    event.preventDefault();
    if (option.series[0].mapLocation.width>350){
      option.series[0].mapLocation.width -= 100
      option.series[0].mapLocation.height -= 100
      myMap.setOption(option, true)
      if (option.series[0].mapLocation.width<=350) {
        $(this).addClass('scale-disable')
      };
    }
  });


  // 创建地图实例，并绑定响应事件
  function _init(city, spots, callback){

    myMap = echarts.init(_dom)

    _setOption(city, spots)

    // 响应标注点击事件
    myMap.on(echarts.config.EVENT.CLICK, function(param) {
      if (typeof spots === 'function') {
        spots.call(this, param)
      }
      if (typeof callback === 'function') {
        callback.call(this, param)
      }
    })

    // 地图区域选中事件
    myMap.on(echarts.config.EVENT.MAP_SELECTED, function(param) {
      var obj = {};
      var target = param.target,
        geoData = !!mapGeoData.params[target],
        _city = currentCity = geoData ? target : '西藏'
      option.series[0].mapType = _city;
      if (geoData && !cityName[_city]) {
        myMap.showLoading({text:'加载中'})
      }

      if (_city === '西藏') {
        obj = batch(__spots)
      }else{
        obj = getGeoData(_city, __spots)
      }
      option.series[0].markPoint.data = obj.data || []
      option.series[0].markPoint.geoCoord = obj.geoCoord || {}
      myMap.setOption(option, true)
    })

  }

  // 对外提供
  exports.init = function(city, spots, callback){
    _init(city, spots, callback)
    createScale()
  }
  exports.mapChange = function(city){
	city = mapGeoData.params[city] ? city : '西藏'
    var obj = {}
    if (city === '西藏') {
      obj = batch(__spots)
    } else {
      obj = getGeoData(city, __spots)
    }
    option.series[0].data[0].name = city
    option.series[0].mapType = city
    option.series[0].markPoint.data = obj.data || []
    option.series[0].markPoint.geoCoord = obj.geoCoord || {}
    myMap.setOption(option, true)
  }

  exports.setGeoData = function(city, spots){
    currentCity = city = mapGeoData.params[city] ? city : '西藏'
    __spots = toObject(spots)
    _setOption(city, __spots)
  }

  function toObject(array){
    var obj = {}
    for (var i = 0; i < array.length; i++) {
      obj[array[i].destinationName] = []
    }
    for (var i = 0; i < array.length; i++) {
      obj[array[i].destinationName].push(array[i])
    }
    return obj
  }

  // _batch()
  function _setOption(city, spots){
    var obj = {}
    var result = typeof city === 'string'
    option.series[0].mapType = currentCity
    if (typeof city === 'string') {
      option.series[0].data[0].name = city
    }
    if(typeof city === 'object'){
      __spots = city
    } else if(typeof city === 'string' && typeof spots === 'object'){
      __spots = spots
    }
    if (city === '西藏') {
      obj = batch(__spots)
    } else {
      obj = getGeoData(city, __spots)
    }
    option.series[0].markPoint.data = obj.data || []
    option.series[0].geoCoord = obj.geoCoord || {}
    myMap.setOption(option, true)
  }

  var wanaOrGne = {wanna:'go4m', gone:'been4m'}

  // 根据地区获取景点
  function getGeoData(city, object) {
    var obj = {},
      _spots = [];
    if (object && object[city]) {
      for (var i = 0, _spot = object[city]; i < _spot.length; i++) {
    	 var icon = wanaOrGne[_spot[i].wanaOrGne] || 'been4m'
        _spots.push({
          name: _spot[i].viewName,
          symbol: 'image://' + contentPath + 'portal/assets/icon/' + icon + '.png'
        })
        obj[_spot[i].viewName] = _spot[i].xy.split(',')
      }
    }
    return {
      data: _spots,
      geoCoord: obj
    }
  }


  function batch(object) {
    var obj = {},
      _spots = [];
    for (var name in object) {
      for (var i = 0, _spot = object[name]; i < _spot.length; i++) {
    	  var icon = wanaOrGne[_spot[i].wanaOrGne] || 'been4m'
        _spots.push({
          name: _spot[i].viewName,
          symbol: 'image://' + contentPath + 'portal/assets/icon/' + icon + '.png'
        })
        obj[_spot[i].viewName] = _spot[i].xy.split(',')
      }
    };
    return {
      data: _spots,
      geoCoord: obj
    }
  }

  var option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}'
    },
    series: [{
      type: 'map',
      mapType: '西藏',
      selectedMode: 'single',
      roam: 'move',
      // hoverable: false,
      itemStyle: {
        normal: {
          color: 'rgba(67, 67, 67, 0.4)',
          borderWidth: 2,
          borderColor: 'white',
          label: {
            show: true,
            textStyle: {
              color: '#333',
              fontFamily: 'Microsoft Yahei'
            }
          }
        },
        emphasis: {
          borderWidth: 2,
          label: {
            show: true,
            textStyle: {
              color: '#333',
              fontFamily: 'Microsoft Yahei'
            }
          }
        }
      },
      mapLocation:{
        x:'center',
        y:'center',
        width: 450,
        height: 300
      },
      data: [{
        name: '',
        itemStyle: {
          normal: {
            color: '#cf1423',
            borderWidth: 2,
            borderColor: '#70000a',
            label: {
              show: true,
              textStyle: {
                color: '#fff',
                fontFamily: 'Microsoft Yahei'
              },
            }
          },
          emphasis: {
            borderColor: '#70000a',
            label: {
              show: true,
              textStyle: {
                color: '#fff',
                fontFamily: 'Microsoft Yahei'
              }
            }
          }
        }
      }],
      markPoint: {
        symbolSize: 10,
        itemStyle: {
          normal: {
            label: {
              show: true
            }
          }
        },
        data:[]
      },
      geoCoord: {}
    }]
  }
  // myMap.setOption(option)

})