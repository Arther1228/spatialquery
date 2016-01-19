package com.suncreate.spatialquery.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.suncreate.spatialquery.service.Facade;
import com.suncreate.spatialquery.service.GisCameraPositionRecordService;

/**
 * @author Coder AutoGenerator
 * @date 2015-06-04 13:14:50
 */
@Service("facade")
public class FacadeImpl implements Facade {
		
	@Resource
	GisCameraPositionRecordService gisCameraPositionRecordService;
	

	public GisCameraPositionRecordService getGisCameraPositionRecordService() {
		return gisCameraPositionRecordService;
	}
	
	
}
