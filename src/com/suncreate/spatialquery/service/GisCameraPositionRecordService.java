package com.suncreate.spatialquery.service;

import java.util.List;

import com.suncreate.spatialquery.domain.GisCameraPositionRecord;

/**
 * @author Coder AutoGenerator
 * @date 2015-06-04 13:14:50
 */
public interface GisCameraPositionRecordService {

	Long createGisCameraPositionRecord(GisCameraPositionRecord t);

	int modifyGisCameraPositionRecord(GisCameraPositionRecord t);

	int removeGisCameraPositionRecord(GisCameraPositionRecord t);

	GisCameraPositionRecord getGisCameraPositionRecord(GisCameraPositionRecord t);

	List<GisCameraPositionRecord> getGisCameraPositionRecordList(GisCameraPositionRecord t);

	Long getGisCameraPositionRecordCount(GisCameraPositionRecord t);

	List<GisCameraPositionRecord> getGisCameraPositionRecordPaginatedList(GisCameraPositionRecord t);

	/******** ????7?????????????????????????????????????????????????????????? ***********/
}