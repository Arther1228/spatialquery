package com.suncreate.spatialquery.web.struts.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.suncreate.spatialquery.domain.GisCameraPositionRecord;
import com.suncreate.spatialquery.web.BaseAction;
import com.suncreate.spatialquery.web.utils.LatitudeLontitudeUtil;

/**
 * 
 * 
 * 地图服务类
 * 
 * @author fanlianwei
 */
public class MapAction extends BaseAction {

	/**
	 * 不带method时默认响应方法
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.showMap(mapping, form, request, response);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showMap(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ActionForward("/index.html");
	}

	/**
	 * @description 返回摄像头给前台
	 */	
	public ActionForward getInitCamera(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取数据库的数据
		GisCameraPositionRecord camera=new GisCameraPositionRecord();
		List<GisCameraPositionRecord> cameraList = getFacade().getGisCameraPositionRecordService().getGisCameraPositionRecordList(camera);

		if(cameraList.size()>0){
			System.out.println("数据有："+cameraList.size());
		}
		//*返回符合的摄像头点数据
		JSONArray obj = JSONArray.fromObject(cameraList);
		super.renderJson(response, obj.toString());
		return null;
	}

	/**
	 * @description 获取点周边的摄像头
	 */
	public ActionForward getCirleCamera(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject data = new JSONObject();
		List<GisCameraPositionRecord> cameraResult = new ArrayList<GisCameraPositionRecord>(); // *用于保存查找到的ap列表
		try{
			//遍历数据库的数据
			GisCameraPositionRecord camera = new GisCameraPositionRecord();
			List<GisCameraPositionRecord> cameraList = getFacade().getGisCameraPositionRecordService().getGisCameraPositionRecordList(camera);

			// 获取参数
			double pointxDouble = Double.valueOf(request.getParameter("pointx"));
			double pointyDouble = Double.valueOf(request.getParameter("pointy"));
			double radiusDouble = Double.valueOf(request.getParameter("radius"));

			// longitude 经度,latitude 纬度
			double templong, templati, radiusNow;
			for (GisCameraPositionRecord t : cameraList) {
				// 首先要确定经纬度是否为空
				if ((t.getLongitude() != null) && (t.getLatitude() != null)) {
					// 当前记录的经纬度坐标
					templong = Double.valueOf(t.getLongitude());
					templati = Double.valueOf(t.getLatitude());

					radiusNow = LatitudeLontitudeUtil.getDistance(templati,templong, pointyDouble, pointxDouble);

					if (radiusNow <= radiusDouble) {
						cameraResult.add(t);
					}
				}
			}
		}catch (Exception e) {
			data.put("result", false);
			data.put("errorMsg", e.getMessage());
			log.error(e);
		}
		// *返回符合的摄像头点数据
		JSONArray obj = JSONArray.fromObject(cameraResult);
		super.renderJson(response, obj.toString());
		return null;
	}

	/**
	 * @description 获取矩形内部的摄像头
	 */
	public ActionForward getRectCamera(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject data = new JSONObject();
		List<GisCameraPositionRecord> cameraResult = new ArrayList<GisCameraPositionRecord>(); // *用于保存查找到的ap列表
		try{
			GisCameraPositionRecord camera = new GisCameraPositionRecord();
			List<GisCameraPositionRecord> cameraList = getFacade().getGisCameraPositionRecordService().getGisCameraPositionRecordList(camera);

			// 获取参数
			double xminDouble = Double.valueOf(request.getParameter("xmin"));
			double ymaxDouble = Double.valueOf(request.getParameter("ymax"));
			double xmaxDouble = Double.valueOf(request.getParameter("xmax"));
			double yminDouble = Double.valueOf(request.getParameter("ymin"));

			// longitude 经度,latitude 纬度
			double templong, templati;
			for (GisCameraPositionRecord t : cameraList) {
				// 首先要确定经纬度是否为空
				if ((t.getLongitude() != null) && (t.getLatitude() != null)) {
					// 当前记录的经纬度坐标
					templong = Double.valueOf(t.getLongitude());
					templati = Double.valueOf(t.getLatitude());

					if ((templong > xminDouble) && (templong < xmaxDouble)
							&& (templati > yminDouble) && (templati < ymaxDouble)) {
						cameraResult.add(t);
					}
				}
			}
		}catch(Exception e){
			data.put("result", false);
			data.put("errorMsg", e.getMessage());
			log.error(e);
		}

		// *返回符合的摄像头点数据
		JSONArray obj = JSONArray.fromObject(cameraResult);
		super.renderJson(response, obj.toString());
		return null;
	}

	/**
	 * @description 获取直线周围的摄像头
	 */
	public ActionForward getLineCamera(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		List<GisCameraPositionRecord> cameraResult = new ArrayList<GisCameraPositionRecord>(); // *用于保存查找到的ap列表
		//获取直线起始点参数
		double point1xDouble=Double.valueOf(request.getParameter("point1x"));
		double point1yDouble=Double.valueOf(request.getParameter("point1y"));
		double point2xDouble=Double.valueOf(request.getParameter("point2x"));
		double point2yDouble=Double.valueOf(request.getParameter("point2y"));
		double radiusDouble=Double.valueOf(request.getParameter("radius"));
		
		try{
			GisCameraPositionRecord camera = new GisCameraPositionRecord();
			List<GisCameraPositionRecord> cameraList = getFacade().getGisCameraPositionRecordService().getGisCameraPositionRecordList(camera);
			//longitude 经度,latitude 纬度
			double templong , templati , d;

			double[] xytemp = new double[2];
			//将经纬度转化为墨卡托投影坐标
			xytemp = LatitudeLontitudeUtil.lonLat2Mercator(point1xDouble, point1yDouble);
			double x1m = xytemp[0];
			double y1m = xytemp[1];

			xytemp = LatitudeLontitudeUtil.lonLat2Mercator(point2xDouble,point2yDouble);
			double x2m = xytemp[0];
			double y2m = xytemp[1];
			//逐个从数据库中取点,并通过点到直线距离算法判断点是否在直线周边
			for(GisCameraPositionRecord t:cameraList){
				//首先要确定经纬度是否为空
				if((t.getLongitude() !=null) && (t.getLatitude()!=null)){
					//当前记录的经纬度坐标
					templong=Double.valueOf(t.getLongitude());
					templati=Double.valueOf(t.getLatitude());

					xytemp = LatitudeLontitudeUtil.lonLat2Mercator(templong, templati);
					double x0m = xytemp[0];
					double y0m = xytemp[1];

					//计算距离并判断是否在半径覆盖范围之内
					d=LatitudeLontitudeUtil.pointToLine(x1m, y1m, x2m, y2m, x0m, y0m);
					if(d <= radiusDouble){
						cameraResult.add(t);
					}
				}
			}
		}catch(Exception e){
			data.put("result", false);
			data.put("errorMsg", e.getMessage());
			log.error(e);
		}

		//*返回符合的摄像头点数据
		JSONArray obj = JSONArray.fromObject(cameraResult);
		super.renderJson(response, obj.toString());
		return null;
	}

	/**
	 * @description 获取多边形区域内的摄像头
	 */
	public ActionForward getPolygonCamera(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取参数
		String points=request.getParameter("points");//逗号分隔的点

		JSONObject data = new JSONObject();
		List<GisCameraPositionRecord> cameraResult = new ArrayList<GisCameraPositionRecord>(); // *用于保存查找到的cameraResult
		try{
			//遍历数据库的数据
			GisCameraPositionRecord camera = new GisCameraPositionRecord();
			List<GisCameraPositionRecord> cameraList = getFacade().getGisCameraPositionRecordService().getGisCameraPositionRecordList(camera);

			// longitude 经度,latitude 纬度
			double templong, templati;

			String[] pts=points.split(",");
			List<double[]> list=new ArrayList<double[]>();

			for(int i=0;i<pts.length;i+=2){
				if(i+1 >= pts.length){
					break;
				}
				double[] dd = new double[2];
				dd[0] = Double.valueOf(pts[i]);
				dd[1] = Double.valueOf(pts[i+1]);
				dd = LatitudeLontitudeUtil.lonLat2Mercator(dd[0],dd[1]);
				list.add(dd);
			}
			if(list.size()<3){
				return null;
			}
			double[] d00=list.get(0);
			double[] dmm=list.get(list.size()-1);
			if(d00[0]==dmm[0] && d00[1]==dmm[1]){
				list.remove(list.size()-1);
			}
			if(list.size()<3){
				return null;
			}
			double[] xytemp;
			// 逐个从数据库中取点,并通过点到直线距离算法判断点是否在直线周边
			for (GisCameraPositionRecord t : cameraList) {
				// 首先要确定经纬度是否为空
				if ((t.getLongitude()!= null) && (t.getLatitude()!= null)) {
					// 当前记录的经纬度坐标
					templong = Double.valueOf(t.getLongitude());
					templati = Double.valueOf(t.getLatitude());
					xytemp = LatitudeLontitudeUtil.lonLat2Mercator(templong,templati);
					// 计算距离并判断是否在半径覆盖范围之内
					boolean ret = LatitudeLontitudeUtil.inPolygon(xytemp[0], xytemp[1], list);
					if (ret) {
						cameraResult.add(t);
					}
				}
			}
		}catch(Exception e){
			data.put("result", false);
			data.put("errorMsg", e.getMessage());
			log.error(e);
		}
		// *返回符合的摄像头点数据
		JSONArray obj = JSONArray.fromObject(cameraResult);
		super.renderJson(response, obj.toString());
		return null;
	}


}