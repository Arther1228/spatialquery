package com.suncreate.spatialquery.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.suncreate.spatialquery.dao.GisCameraPositionRecordDao;
import com.suncreate.spatialquery.domain.GisCameraPositionRecord;
import com.suncreate.spatialquery.service.GisCameraPositionRecordService;

/**
 * @author Coder AutoGenerator
 * @date 2015-06-04 13:14:50
 */
@Service
public class GisCameraPositionRecordServiceImpl implements GisCameraPositionRecordService {

	@Resource
	private GisCameraPositionRecordDao gisCameraPositionRecordDao;
	

	public Long createGisCameraPositionRecord(GisCameraPositionRecord t) {
		return this.gisCameraPositionRecordDao.insertEntity(t);
	}

	public GisCameraPositionRecord getGisCameraPositionRecord(GisCameraPositionRecord t) {
		return this.gisCameraPositionRecordDao.selectEntity(t);
	}

	public Long getGisCameraPositionRecordCount(GisCameraPositionRecord t) {
		return this.gisCameraPositionRecordDao.selectEntityCount(t);
	}

	public List<GisCameraPositionRecord> getGisCameraPositionRecordList(GisCameraPositionRecord t) {
		return this.gisCameraPositionRecordDao.selectEntityList(t);
	}

	public int modifyGisCameraPositionRecord(GisCameraPositionRecord t) {
		return this.gisCameraPositionRecordDao.updateEntity(t);
	}

	public int removeGisCameraPositionRecord(GisCameraPositionRecord t) {
		return this.gisCameraPositionRecordDao.deleteEntity(t);
	}

	public List<GisCameraPositionRecord> getGisCameraPositionRecordPaginatedList(GisCameraPositionRecord t) {
		return this.gisCameraPositionRecordDao.selectEntityPaginatedList(t);
	}
	
	/******** ????7?????????????????????????????????????????????????????????? ***********/
}
