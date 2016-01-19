package com.suncreate.spatialquery.web.utils;

import java.util.List;

public class LatitudeLontitudeUtil {  

	// http://blog.charlee.li/location-search/  

	/** 地球半径 */  
	private static final double EARTH_RADIUS = 6371393;  

	static double M_PI = Math.PI;

	public LatitudeLontitudeUtil() {  

	}  

	//点到点距离算法
	public static double getDistance(double lat0, double lng0, double lat1, double lng1) {  

		lat0 = Math.toRadians(lat0);  
		lat1 = Math.toRadians(lat1);  
		lng0 = Math.toRadians(lng0);  
		lng1 = Math.toRadians(lng1);  

		double dlng = Math.abs(lng0 - lng1);  
		double dlat = Math.abs(lat0 - lat1);  
		double h = hav(dlat) + Math.cos(lat0) * Math.cos(lat1) * hav(dlng);  
		double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));  

		return distance;  
	}  

	public static double hav(double theta) {  
		double s = Math.sin(theta / 2);  
		return s * s;  
	}  

	//经纬度转墨卡托
	public static double[] lonLat2Mercator(double lon,double lat)
	{
		double[] xy = new double[2];
		double x = lon *20037508.342789/180;
		double y = Math.log(Math.tan((90+lat)*M_PI/360))/(M_PI/180);
		y = y *20037508.34789/180;
		xy[0] = x;
		xy[1] = y;
		return xy;
	}


	// 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )  
	public static double pointToLine(double x1, double y1, double x2, double y2, double x0, double y0) {  
		double space = 0;  
		double a, b, c;  
		a = lineSpace(x1, y1, x2, y2);// 线段的长度  
		b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离  
		c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离  

		//System.out.println("a:"+a+",b:"+b+",c:"+c);

		if (c + b == a) {//点在线段上  
			space = 0;  
			return space;  
		}  
		if (a <= 0.000001) {//不是线段，是一个点  
			space = b;  
			return space;  
		}  

		if (c * c >= a * a + b * b) { //组成直角三角形或钝角三角形，(x1,y1)为直角或钝角  
			space = b;  
			return space;  
		}  
		if (b * b >= a * a + c * c) {//组成直角三角形或钝角三角形，(x2,y2)为直角或钝角  
			space = c;  
			return space;  
		}  
		//组成锐角三角形，则求三角形的高  
		double p = (a + b + c) / 2;// 半周长  
		double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积  
		space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）  
		return space ;  

	}  

	// 计算两点之间的距离  
	public static double lineSpace(double x1, double y1, double x2, double y2) {  
		double lineLength = 0;  
		lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));  
		return lineLength;  
	}  
	//------------------------------------------------------------ 


	/**
	 * 计算点在多边形内
	 * @param x 点 x坐标
	 * @param y 点 y 坐标
	 * @param points 多边形所有的点
	 * @return
	 */
	public static boolean inPolygon(double x, double y, List<double[]> points) {
		if (points.size() < 3)
			return false;
		int i, j = points.size() - 1;
		boolean oddNodes = false;

		for (i = 0; i < points.size(); i++) {
			
			double polyYi = points.get(i)[1];
			double polyYj = points.get(j)[1];
			double polyXi = points.get(i)[0];
			double polyXj = points.get(j)[0];
			if ((polyYi < y && polyYj >= y || polyYj < y && polyYi >= y)
					&& (polyXi <= x || polyXj <= x)) {
				double d=polyYj - polyYi;
				if(polyYj==polyYi)d=0.0000001;
				oddNodes ^= (polyXi + (y - polyYi) /d * (polyXj - polyXi) < x);
			}
			j = i;
		}
		return oddNodes;
	}


	public static void main(String[] args) {  
		//测试用例
		/*
    	//测试被选中的点和中心点之间的距离
    	//被选中点
    	double lat = 31.833700367886564;  
		double lng = 117.18010393691736;  
		//中心点
		double lat1 = 31.83450054220403 ;  
		double lng1 = 117.1807341005807;  

		double d = LatitudeLontitudeUtil.getDistance(lat, lng, lat1, lng1);  
		System.out.println(d); 

		 */


		//测试用例

		//点到直线的距离算法测试

		//算法一
		double x1 = 31.821018461732;  
		double y1 = 117.18765906006398;  

		double x2 = 31.821018461732;  
		double y2 = 117.18980211911884;  

		double x0 = 31.8226736;
		double y0 = 117.1888475;

		double[] xytemp = new double[2];

		xytemp = LatitudeLontitudeUtil.lonLat2Mercator(y1, x1);
		double x1m = xytemp[1];
		double y1m = xytemp[0];

		xytemp = LatitudeLontitudeUtil.lonLat2Mercator(y2, x2);
		double x2m = xytemp[1];
		double y2m = xytemp[0];

		xytemp = LatitudeLontitudeUtil.lonLat2Mercator(y0, x0);
		double x0m = xytemp[1];
		double y0m = xytemp[0];

		System.out.println("x1m:"+x1m+",y1m:"+y1m);
		System.out.println("x2m:"+x2m+",y2m:"+y2m);
		System.out.println("x0m:"+x0m+",y0m:"+y0m);

		double d = LatitudeLontitudeUtil.pointToLine(x1m, y1m, x2m, y2m, x0m, y0m);  

		System.out.println(d);  

	}  
}  