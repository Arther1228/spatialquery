dojo.require("esri.map");
dojo.require("esri.toolbars.draw");
dojo.require("esri.tasks.query");
dojo.require("esri.tasks.gp");
dojo.require("esri.symbols.SimpleLineSymbol");
dojo.require("esri.tasks.BufferParameters");
dojo.require("esri.tasks.GeometryService");
dojo.require("esri.tasks.RelationParameters");
dojo.require("esri.dijit.Scalebar");
dojo.require("esri.geometry.Polyline");
//dojo.require("esri.geometry.Circle");  //3.6API没有这个接口


var map ;  

function init() {
	map = new esri.Map("map",{logo:false,slider:false}); //创建map,加载到地图
	//地图图层
	basemap = new esri.layers.ArcGISDynamicMapServiceLayer("http://192.168.97.24:6080/arcgis/rest/services/PGISTiled/MapServer");
	map.addLayer(basemap);

	toolbar1 = new esri.toolbars.Draw(map);
	dojo.connect(toolbar1, 'onDrawEnd', drawEnd1);

	toolbar2 = new esri.toolbars.Draw(map);
	dojo.connect(toolbar2, 'onDrawEnd', drawEnd2);

	toolbar3 = new esri.toolbars.Draw(map);
	dojo.connect(toolbar3, 'onDrawEnd', drawEnd3);
	
	toolbar4 = new esri.toolbars.Draw(map);
	dojo.connect(toolbar4, 'onDrawEnd', drawEnd4);
	
	//var scalebar = new esri.dijit.Scalebar({map:map, scalebarUnit:"metric",attachTo:"bottom-left"});

	var scalebar = new esri.dijit.Scalebar({
		map: map,
		scalebarUnit: "metric"
	}, dojo.byId("scalebar"));
	
	//单击点符号
	outSymbolRed = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 12,new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new dojo.Color([ 204, 102, 51 ]), 1), new dojo.Color([158, 184, 71, 1 ]));
	//摄像头符号
	cameraSymbol = new esri.symbol.PictureMarkerSymbol("http://192.168.99.192:8080/spatialquery/skin/darkBlue/images/a0.png",	10, 10);
	//框选多边形符号
	symbolPolygon = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID,new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT,new dojo.Color([ 255, 0, 0 ]), 2), new dojo.Color([255, 255, 255, 0.1 ]));
	//圆符号
	symbolCircle = new esri.symbol.SimpleFillSymbol().setColor(null).outline.setColor("blue");
	//路径符号
	routeSymbol = new esri.symbol.SimpleLineSymbol().setColor(new dojo.Color([ 0, 0, 255, 0.5 ])).setWidth(3);

}
//初始化加载所有摄像头
function addInitCameras(){
	//alert('0');
	$.ajax({
		url : '/spatialquery/Map.do',
		data : {
		method : 'getInitCamera'
	},
	cache : false,
	type : 'post',
	dataType : 'json',
	error : function(txt) {
		alert("\u670d\u52a1\u5668\u5904\u7406\u5931\u8d25\u3002");
	},
	async : false,
	success : function(json) {
		var data=json;
		var templati,templong,tempPoint,graphic;
		for(var i=0;i<data.length;i++){
			templati=data[i].latitude;
			templong=data[i].longitude;
			tempPoint=new esri.geometry.Point(templong, templati, map.spatialReference);
			var otherInfo = new esri.InfoTemplate("摄像头信息:${name}","经度：${lon}</br>纬度：${lat}");
			graphic= new esri.Graphic(tempPoint,cameraSymbol,{"lon" : templong,"lat" :templati,"name" : "摄像头坐标"}, otherInfo); 
			map.graphics.add(graphic);
		}
	}
	});

}	
//点周边
function drawPoint() {
	toolbar2.deactivate();
	toolbar3.deactivate();
	toolbar1.activate(esri.toolbars.Draw.POINT); 
}
//框选区域
function drawPolygon() {
	toolbar1.deactivate();
	toolbar3.deactivate();
	toolbar2.activate(esri.toolbars.Draw.EXTENT);
}
//线周边
function drawPolyline() {
	toolbar1.deactivate();
	toolbar2.deactivate();
	toolbar3.activate(esri.toolbars.Draw.POLYLINE);
}

function drawPolygon2(){
	toolbar1.deactivate();
	toolbar2.deactivate();
	toolbar3.deactivate();
	toolbar4.activate(esri.toolbars.Draw.POLYGON);
}

function drawEnd1(geometry) {
	toolbar1.deactivate();
	
	map.graphics.clear();
	//初始化加载所有摄像头
	addInitCameras();

	var otherInfo = new esri.InfoTemplate("摄像头信息:${name}","经度：${lon}</br>纬度：${lat}");
	var graphic = new esri.Graphic(geometry, outSymbolRed,{"lon" : geometry.x,"lat" :geometry.y,"name" : "摄像头坐标"}, otherInfo);

	var pointx=graphic.geometry.x;
	var pointy=graphic.geometry.y;

	console.log(pointx,pointy);
	map.graphics.add(graphic);

	//寻找圆内部的点
	var radius =200;
	findCameraInCircle(pointx,pointy,radius);
}

//*圆选摄像头
function findCameraInCircle(pointx,pointy,radius) {
	$.ajax({
		url : '/spatialquery/Map.do',
		data : {
		method : 'getCirleCamera',
		pointx : pointx,
		pointy : pointy,
		radius : radius
	},
	cache : false,
	type : 'post',
	dataType : 'json',
	error : function(txt) {
		alert("\u670d\u52a1\u5668\u5904\u7406\u5931\u8d25\u3002");
	},
	async : false,
	success : function(json) {
		var data=json;
		var templati,templong,tempPoint,graphic;
		for(var i=0;i<data.length;i++){
			templati=data[i].latitude;
			templong=data[i].longitude;
			tempPoint=new esri.geometry.Point(templong, templati, map.spatialReference);
			var otherInfo = new esri.InfoTemplate("摄像头信息:${name}","经度：${lon}</br>纬度：${lat}");
			graphic= new esri.Graphic(tempPoint,outSymbolRed,{"lon" : templong,"lat" :templati,"name" : "摄像头坐标"}, otherInfo); 
			map.graphics.add(graphic);
		}
	}
	});

}
function drawEnd2(geometry) {
	toolbar2.deactivate();
	
	map.graphics.clear();
	//初始化加载所有摄像头
	addInitCameras();

	var otherInfo = new esri.InfoTemplate("框选区域信息:${name}","左上x：${left_tx}</br>左上y：${left_ty}</br>右下x：${right_bx}</br>右下y：${right_by}</br>");
	var graphic = new esri.Graphic(geometry, symbolPolygon,{"left_tx" : geometry.xmin,"left_ty" :geometry.ymax,"right_bx" :geometry.xmax,"right_by" :geometry.ymin,"name" : "左上右下坐标"}, otherInfo); 
	map.graphics.add(graphic);

	//对角顶点个经纬度坐标极值
	var xmin=geometry.xmin;
	var ymax=geometry.ymax;
	var xmax=geometry.xmax;
	var ymin=geometry.ymin;
	
	console.log(xmin,ymax,xmax,ymin);

	findCameraInRect(xmin,ymax,xmax,ymin);
}

//框选摄像头
function findCameraInRect(xmin,ymax,xmax,ymin){
	//alert('2');
	$.ajax({
		url : '/spatialquery/Map.do',
		data : {
		method : 'getRectCamera',
		xmin : xmin,
		ymax : ymax,
		xmax : xmax,
		ymin:  ymin
	},
	cache : false,
	type : 'post',
	dataType : 'json',
	error : function(txt) {
		alert("\u670d\u52a1\u5668\u5904\u7406\u5931\u8d25\u3002");
	},
	async : false,
	success : function(json) {
		//alert(json);

		var data=json;
		var templati,templong,tempPoint,graphic;
		for(var i=0;i<data.length;i++){
			templati=data[i].latitude;
			templong=data[i].longitude;
			tempPoint=new esri.geometry.Point(templong, templati, map.spatialReference);
			graphic= new esri.Graphic(tempPoint,outSymbolRed); 
			map.graphics.add(graphic);
		}
	}
	});
}
function drawEnd3(geometry) {
	map.graphics.clear();
	toolbar3.deactivate();

	//初始化加载所有摄像头
	addInitCameras();

	//寻找距离线200米周围的点
	var radius =200;

	//直线
	if(geometry.paths[0].length ==2){  
		//显示信息
		var otherInfo = new esri.InfoTemplate("路径信息:${name}","起点经度：${lon1}</br>起点纬度：${lat1}</br>终点经度：${lon2}</br>终点纬度：${lat2}");
		var graphic = new esri.Graphic(geometry, routeSymbol,{"lon1" : geometry.paths[0][0][0],"lat1" :geometry.paths[0][0][1],"lon2" : geometry.paths[0][1][0],"lat2" :geometry.paths[0][1][1],"name" : "起始点坐标"}, otherInfo);
		//直线的端点一
		var point1x=graphic.geometry.paths[0][0][0];
		var point1y=graphic.geometry.paths[0][0][1];
		//直线的端点二
		var point2x=graphic.geometry.paths[0][1][0];
		var point2y=graphic.geometry.paths[0][1][1];

		map.graphics.add(graphic);

		findCameraByLine(point1x,point1y,point2x,point2y,radius);
	}

	//曲线
	if(geometry.paths[0].length > 2){  
		//循环
		for(var i=0;i<geometry.paths[0].length-1;i++){
			//直线的端点一
			var point1x=geometry.paths[0][i][0];
			var point1y=geometry.paths[0][i][1];
			//直线的端点二
			var point2x=geometry.paths[0][i+1][0];
			var point2y=geometry.paths[0][i+1][1];

			//新建线要给其设置空间参考
			var polylineJson = {"paths":[[[point1x,point1y], [point2x,point2y]]],"spatialReference":{"wkid":4326}};
			var tempPolyline = new esri.geometry.Polyline(polylineJson);

			//显示信息
			var otherInfo = new esri.InfoTemplate("路径信息:${name}","起点经度：${lon1}</br>起点纬度：${lat1}</br>终点经度：${lon2}</br>终点纬度：${lat2}");
			var graphic = new esri.Graphic(tempPolyline, routeSymbol,{"lon1" : point1x,"lat1" :point1y,"lon2" : point2x,"lat2" : point2y,"name" : "起始点坐标"}, otherInfo);

			map.graphics.add(graphic);
			findCameraByLine(point1x,point1y,point2x,point2y,radius);
		}
	}

}
function findCameraByLine(point1x,point1y,point2x,point2y,radius){
	//alert('3');
	$.ajax({
		url : '/spatialquery/Map.do',
		data : {
		method : 'getLineCamera',
		point1x : point1x,
		point1y : point1y,
		point2x : point2x,
		point2y : point2y,
		radius : radius
	},
	cache : false,
	type : 'post',
	dataType : 'json',
	error : function(txt) {
		alert("\u670d\u52a1\u5668\u5904\u7406\u5931\u8d25\u3002");
	},
	async : false,
	success : function(json) {
		//alert(json);

		var data=json;
		var templati,templong,tempPoint,graphic;
		for(var i=0;i<data.length;i++){
			templati=data[i].latitude;
			templong=data[i].longitude;
			tempPoint=new esri.geometry.Point(templong, templati, map.spatialReference);

			var otherInfo = new esri.InfoTemplate("摄像头信息:${name}","经度：${lon}</br>纬度：${lat}");
			graphic= new esri.Graphic(tempPoint,outSymbolRed,{"lon" : templong,"lat" :templati,"name" : "摄像头坐标"}, otherInfo); 

			map.graphics.add(graphic);
		}
	}
	});
}

function drawEnd4(geometry){
	map.graphics.clear();
	toolbar4.deactivate();
	//初始化加载所有摄像头
	addInitCameras();
	
	var graphic = new esri.Graphic(geometry, symbolPolygon); 
	map.graphics.add(graphic);
	
	var points="";
	//拼接多边形点字符串
	for(var i=0;i<geometry.rings[0].length;i++){
		if(i==geometry.rings[0].length-1){
			points=points+geometry.rings[0][i].join(",");
		}else{
			points=points+geometry.rings[0][i].join(",")+",";
		}
	}
	console.log(points);
	
	findCameraByPolygon(points);
}

function findCameraByPolygon(points){
	console.log("findCameraByPolygon");
	$.ajax({
		url : '/spatialquery/Map.do',
		data : {
		method : 'getPolygonCamera',
		points : points
	},
	cache : false,
	type : 'post',
	dataType : 'json',
	error : function(txt) {
		alert("\u670d\u52a1\u5668\u5904\u7406\u5931\u8d25\u3002");
	},
	async : false,
	success : function(json) {
		//alert(json);

		var data=json;
		var templati,templong,tempPoint,graphic;
		for(var i=0;i<data.length;i++){
			templati=data[i].latitude;
			templong=data[i].longitude;
			tempPoint=new esri.geometry.Point(templong, templati, map.spatialReference);

			var otherInfo = new esri.InfoTemplate("摄像头信息:${name}","经度：${lon}</br>纬度：${lat}");
			graphic= new esri.Graphic(tempPoint,outSymbolRed,{"lon" : templong,"lat" :templati,"name" : "摄像头坐标"}, otherInfo); 

			map.graphics.add(graphic);
		}
	}
	});
}

function mapClear(){
	map.graphics.clear();
}

dojo.ready(init);

