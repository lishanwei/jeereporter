//package com.thinkgem.jeesite.common.utils;
//
//import java.text.ParseException;
//import java.util.Date;
//
//import com.fangbaba.gds.face.bean.TripDateInfo;
//
//
///**
// * 业务工具类
// * @author tankai
// *
// */
//public class BusinessUtil {
//    
//    public static void fillTripDate(TripDateInfo dateInfo,Date beginTime,Date endTime) {
//		Integer tripMaxDate = 30;
//		
//		try {
//			if(beginTime!=null){
//				dateInfo.setDefaultStartDate(DateUtil.dateToStr(beginTime, "yyyy-MM-dd"));
//			}else{
//				dateInfo.setDefaultStartDate(DateUtil.dateToStr(new Date(), "yyyy-MM-dd"));
//			}
//			if(endTime!=null){
//				dateInfo.setDefaultEndDate(DateUtil.dateToStr(endTime, "yyyy-MM-dd"));
//			}else{
//				dateInfo.setDefaultEndDate(DateUtil.dateToStr(DateUtil.getDay(-1), "yyyy-MM-dd"));
//			}
//			
//			
//			dateInfo.setMinDate(DateUtil.dateToStr(new Date(), "yyyy-MM-dd"));
//			dateInfo.setMaxDate(DateUtil.dateToStr(DateUtil.getDay(-tripMaxDate+1), "yyyy-MM-dd"));
//		} catch (ParseException e) {
//			
//		}
//	}
//    
//} 
