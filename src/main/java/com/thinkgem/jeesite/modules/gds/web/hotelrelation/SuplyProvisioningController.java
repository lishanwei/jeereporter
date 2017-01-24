//package com.thinkgem.jeesite.modules.gds.web.hotelrelation;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.fangbaba.basic.face.service.HotelService;
//import com.fangbaba.gds.face.bean.Page;
//import com.fangbaba.gds.face.bean.PriceBean;
//import com.fangbaba.gds.face.bean.SuplyBean;
//import com.fangbaba.gds.face.service.IDistributorConfigService;
//import com.fangbaba.gds.face.service.IHotelSearchService;
//import com.fangbaba.gds.face.service.IPriceService;
//import com.fangbaba.gds.face.service.ISalePriceService;
//import com.fangbaba.gds.face.service.OtaRoomtypeService;
//import com.fangbaba.gds.po.SalePrice;
//import com.google.common.collect.Maps;
//import com.google.gson.Gson;
//import com.lz.mongo.bislog.BisLog;
//import com.lz.mongo.bislog.BisLogDelegate;
//import com.thinkgem.jeesite.common.enums.DistributionTypeEnum;
//import com.thinkgem.jeesite.common.utils.DateUtil;
//import com.thinkgem.jeesite.common.web.BaseController;
//import com.thinkgem.jeesite.modules.sys.entity.User;
//import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
//
//@Controller
//@RequestMapping(value = "${adminPath}/suply")
//public class SuplyProvisioningController extends BaseController {
//	@Autowired
//	private OtaRoomtypeService otaRoomtypeService;
//	@Autowired
//	private IPriceService priceService;
//	@Autowired
//	private ISalePriceService salePriceService;
//	@Autowired
//	private IHotelSearchService hotelSearchService;
//	@Autowired
//	private IDistributorConfigService distributorConfigService;
//	@Autowired
//	private HotelService hotelService;
//	@Autowired
//	private BisLogDelegate bisLogDelegate;
//
//	@RequestMapping(value = "/provisioning", method = RequestMethod.POST)
//	public ResponseEntity<Map<String, Object>> provisioning(Page page, Long hotelid,Long otatype) {
//		List<SuplyBean> suplyBeans = this.otaRoomtypeService.getOtaRoomtypeByHotelid(hotelid,otatype,null);
//		Map<String, Object> rtnMap = Maps.newHashMap();
//		rtnMap.put("result", suplyBeans);
//		rtnMap.put("page", page);
//		rtnMap.put("otatype", otatype);
//		return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	}
//	@RequestMapping(value = "/priceinfos", method = RequestMethod.POST)
//	public ResponseEntity<Map<String, Object>> priceinfos(Long otatype,Long hotelid,@RequestParam(value = "days[]") String[] days) throws Exception {
//		logger.info("otatype:{}",otatype);
//		logger.info("hotelid:{}",hotelid);
//		logger.info("days:{}",new Gson().toJson(days));
//		Map<String, Object> rtnMap = Maps.newHashMap();
//	try {
//		
//		List<Long> dayss = new ArrayList<Long>();
//		for (String date: days) {
//			Long theday = Long.parseLong(DateUtil.dateToStr(DateUtil.strToDate(date, "yyyy-MM-dd"), "yyyyMMdd"));
//			dayss.add(theday);
//		}
//		
//		List<PriceBean> list = priceService.querySaleAndChannelPrice(otatype, hotelid, dayss,null);
//		rtnMap.put("result", list);
//		rtnMap.put("otatype", otatype);
//		rtnMap.put("otaname", distributorConfigService.queryByOtaType(otatype).getName());
//		rtnMap.put("hotelname", hotelService.queryById(hotelid).getHotelname());
//	} catch (Exception e) {
//		logger.error("查询价格错误:{}",e);
//	}
//		return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	}
//
//	/*@RequestMapping(value = "/update", method = RequestMethod.POST)
//	public ResponseEntity<Boolean> update(String suplyBeans) {
//		logger.info("供应配置开始："+suplyBeans);
//		Gson gson=new Gson();
//		 List<SuplyBean> retList=new ArrayList<SuplyBean>();
//		 try {
//			retList= gson.fromJson(suplyBeans, new TypeToken<List<SuplyBean>>() {}.getType());
//		} catch (JsonSyntaxException e) {
//			logger.info(e.getMessage());
//		}  
//		boolean result=false;
//		if (CollectionUtils.isNotEmpty(retList)) {
//			result=otaRoomtypeService.updateOtaRoomtype(retList);
//			//suplyBeans = this.otaRoomtypeService.getOtaRoomtypeByHotelid(suplyBeans.get(0).getHotelid());
//		}
//		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
//	}*/
//	@RequestMapping(value = "/saveSaleRoomNum", method = RequestMethod.POST)
//	public ResponseEntity<Boolean> saveSaleRoomNum(Long otaroomtypeid,Integer roomnum) {
//		otaRoomtypeService.saveSaleRoomNum(otaroomtypeid,roomnum);
//		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//	}
//	@RequestMapping(value = "/saveChannelPrice", method = RequestMethod.POST)
//	public ResponseEntity<Boolean> saveChannelPrice(SalePrice salePrice) throws Exception {
//		salePriceService.saveChannelPrice(salePrice);
//		//刷新价格到缓存
//		priceService.initPriceToRedisSingle(salePrice.getOtatype(), salePrice.getHotelid(), DateUtil.strToDate(salePrice.getDay().toString(), "yyyyMMdd"), salePrice.getRoomtypeid());
//		//单个更新es
//		hotelSearchService.updateEsSingle(salePrice.getOtatype(), salePrice.getHotelid());
//		try {
//			User user = UserUtils.getUser();         
//			if ( user == null ) {
//				logger.error("登录用户有问题");
//			}else{
//				String  loginuser = user.getLoginName();
//				BisLog bisLog = new BisLog();
//				bisLog.setSystem("distributionprice");
//				bisLog.setOperator(loginuser);
//				bisLog.setBussinessId(salePrice.getOtatype().toString()+salePrice.getHotelid().toString());
//				bisLog.setBussinssType(DistributionTypeEnum.specialChannelPrice.getType());
//				bisLog.setContent("保存特殊渠道价:otatype:"+salePrice.getOtatype()+",hotelid:"+salePrice.getHotelid()+",day:"+salePrice.getDay()+",roomtype:"+salePrice.getRoomtypeid()+",price:"+salePrice.getSaleprice());
//				bisLogDelegate.saveBigLog(bisLog);
//			}
//		} catch (Exception e) {
//			logger.error("error:",e);
//		}
//		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//	}
//	
//}
