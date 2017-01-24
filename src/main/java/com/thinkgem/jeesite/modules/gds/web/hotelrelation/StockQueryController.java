//package com.thinkgem.jeesite.modules.gds.web.hotelrelation;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.fangbaba.basic.face.bean.RoomtypeModel;
//import com.fangbaba.basic.face.service.HotelService;
//import com.fangbaba.basic.face.service.RoomtypeService;
//import com.fangbaba.gds.face.bean.OtaRoomtype;
//import com.fangbaba.gds.face.bean.Page;
//import com.fangbaba.gds.face.bean.PriceBean;
//import com.fangbaba.gds.face.bean.SuplyBean;
//import com.fangbaba.gds.face.service.IDistributorConfigService;
//import com.fangbaba.gds.face.service.IHotelSearchService;
//import com.fangbaba.gds.face.service.IPriceService;
//import com.fangbaba.gds.face.service.ISalePriceService;
//import com.fangbaba.gds.face.service.OtaRoomtypeService;
//import com.fangbaba.gds.po.SalePrice;
//import com.fangbaba.stock.face.base.RetInfo;
//import com.fangbaba.stock.face.bean.RoomInfo;
//import com.fangbaba.stock.face.bean.RoomTypeStock;
//import com.fangbaba.stock.face.service.IStockService;
//import com.google.common.collect.Maps;
//import com.google.gson.Gson;
//import com.lz.mongo.bislog.BisLog;
//import com.lz.mongo.bislog.BisLogDelegate;
//import com.thinkgem.jeesite.common.enums.DistributionTypeEnum;
//import com.thinkgem.jeesite.common.utils.DateUtil;
//import com.thinkgem.jeesite.common.web.BaseController;
//import com.thinkgem.jeesite.modules.gds.web.hotelrelation.bean.StockBean;
//import com.thinkgem.jeesite.modules.sys.entity.User;
//import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
//
//@Controller
//@RequestMapping(value = "${adminPath}/stockquery")
//public class StockQueryController extends BaseController {
//	@Autowired
//	private IPriceService priceService;
//	@Autowired
//	private IDistributorConfigService distributorConfigService;
//	@Autowired
//	private HotelService hotelService;
//	@Autowired
//	private IStockService iStockService;
//	@Autowired
//	private OtaRoomtypeService otaroomtypeService;
//
//	@RequestMapping(value = "/query", method = RequestMethod.POST)
//	public ResponseEntity<Map<String, Object>> priceinfos(Long otatype,Long hotelid,@RequestParam(value = "days[]") String[] days) throws Exception {
//	    Gson gson =new Gson();
//	    List<Long> dayss = new ArrayList<Long>();
//		for (String date: days) {
//			Long theday = Long.parseLong(DateUtil.dateToStr(DateUtil.strToDate(date, "yyyy-MM-dd"), "yyyyMMdd"));
//			dayss.add(theday);
//		}
//		Map<String, Object> rtnMap = Maps.newHashMap();
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date beginTime = sdf.parse(days[0]);
//		Date endTime = DateUtil.addDateOneDay(sdf.parse(days[days.length-1]),1);
//		List<Long> roomtypeids = new ArrayList<Long>();
//		List<OtaRoomtype> listroom = otaroomtypeService.queryByHotelIdOtatype(otatype, hotelid);
//		for(OtaRoomtype roomtype : listroom){
//		    roomtypeids.add(roomtype.getRoomtypeid());
//		}
//		Long time1 = System.currentTimeMillis();
//		RetInfo<RoomTypeStock> retInfo = new RetInfo<RoomTypeStock>();
//		if(CollectionUtils.isNotEmpty(roomtypeids)){
//		    retInfo = iStockService.selectByDateAndRoomtype(roomtypeids, otatype, beginTime, endTime,true);
//		    Long time2 = System.currentTimeMillis();
//		    logger.info("counttime:"+(time2-time1));
//		    logger.info("retInfo:"+gson.toJson(retInfo));
//		    
//		    List<StockBean> liststock = new ArrayList<StockBean>();
//		    if(CollectionUtils.isNotEmpty(retInfo.getList())){
//    		    for(RoomTypeStock roomTypeStock : retInfo.getList()){
//    		        StockBean stockBean = new StockBean();
//    		        stockBean.setRoomtypeid(roomTypeStock.getRoomtypeid());
//    		        stockBean.setRoomtypename(roomTypeStock.getRoomtypename());
//    		        
//    		        List<Integer> listnum = new ArrayList<Integer>();
//    		        for(RoomInfo roomInfo : roomTypeStock.getRoomInfo()){
//    		            listnum.add(roomInfo.getNumber());
//    		        }
//    		        stockBean.setRoomnum(listnum);
//    		        stockBean.setDays(dayss);
//    		        liststock.add(stockBean);
//    		    }
//		    }
//		    logger.info("liststock:"+gson.toJson(liststock));
//		    rtnMap.put("result", liststock);
//		    rtnMap.put("otatype", otatype);
//		    rtnMap.put("otaname", distributorConfigService.queryByOtaType(otatype).getName());
//		    rtnMap.put("hotelname", hotelService.queryById(hotelid).getHotelname());
//		}
//		return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	}
//}
