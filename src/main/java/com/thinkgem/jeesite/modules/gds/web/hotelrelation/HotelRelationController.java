//package com.thinkgem.jeesite.modules.gds.web.hotelrelation;
//
//import java.math.BigDecimal;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.fangbaba.gds.Message;
//import com.fangbaba.gds.face.bean.OtaHotel;
//import com.fangbaba.gds.face.bean.Page;
//import com.fangbaba.gds.face.service.IDistributorConfigService;
//import com.fangbaba.gds.face.service.IHotelSearchService;
//import com.fangbaba.gds.face.service.IOtaHotelService;
//import com.fangbaba.gds.face.service.IPriceService;
//import com.google.common.collect.Maps;
//import com.lz.mongo.bislog.BisLog;
//import com.lz.mongo.bislog.BisLogDelegate;
//import com.thinkgem.jeesite.common.enums.DistributionTypeEnum;
//import com.thinkgem.jeesite.common.web.BaseController;
//import com.thinkgem.jeesite.modules.sys.entity.User;
//import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
//
//@Controller
//public class HotelRelationController extends BaseController{
//	@Autowired
//	private IOtaHotelService otaHotelService;
//	@Autowired
//	private IHotelSearchService hotelSearchService;
//	@Autowired
//	private IPriceService priceService;
//	@Autowired
//	private BisLogDelegate bisLogDelegate;
//	//private static ExecutorService pool = Executors.newFixedThreadPool(5);
//	@Autowired
//	private IDistributorConfigService distributorConfigService;
//
//	@RequestMapping(value = "${adminPath}/distributor/list/suply", method = RequestMethod.GET)
//	public ModelAndView suply(Long hotelid,Long otatype) {
//		logger.info("hotelid::{}",hotelid);
//		logger.info("otatype::{}",otatype);
//		ModelAndView modelAndView = new ModelAndView("modules/gds/distributor/suplyprovisioning");
//		modelAndView.addObject("otatype", otatype);
//		modelAndView.addObject("hotelid", hotelid);
//		return modelAndView;
//	}
//	
//	@RequestMapping(value = "${adminPath}/distributor/list/stockquery", method = RequestMethod.GET)
//    public ModelAndView stockquery(Long hotelid,Long otatype) {
//        ModelAndView modelAndView = new ModelAndView("modules/gds/distributor/stockquery");
//        modelAndView.addObject("otatype", otatype);
//        modelAndView.addObject("hotelid", hotelid);
//        return modelAndView;
//    }
//
//	/**
//	 * @param page
//	 * @param otatype
//	 * 查询otatype对应otahotel列表
//	 */
//	@RequestMapping(value = "${adminPath}/otahotel/queryotahotels", method = RequestMethod.POST)
//	public ResponseEntity<Map<String, Object>> queryotahotels(Page page, Long otatype,Long hotelid,String hotelname) {
//		logger.info("查询otatype对应otahotel列表开始param:{},{},{}",otatype,hotelid,hotelname);
//		Map<String,Object> map = this.otaHotelService.queryByOtatype(otatype, page,hotelid,hotelname);
//		page.setTotalRecord((Integer) map.get("total"));
//        page.setTotalPage(page.getTotalPage());
//        Map<String, Object> rtnMap = Maps.newHashMap();
//        rtnMap.put("result", map.get("data"));
//        rtnMap.put("page", page);
//        rtnMap.put("otatypename", distributorConfigService.queryByOtaType(otatype).getName());
//		return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	}
//
//	/**
//	 * @param otatype
//	 * @param ids
//	 * 分销商与酒店绑定关系
//	 * @throws Exception 
//	 */
//	@RequestMapping(value = "${adminPath}/otahotel/bindrelation", method = RequestMethod.POST)
//	public ResponseEntity<Message> bindrelation(final Long otatype, final String ids) {
//		logger.info("分销商与酒店绑定关系开始param:{},{}",otatype,ids);
//		Message msg = new Message();
//		try {
//			msg = otaHotelService.bindrelation(otatype, ids);
//			try {
//				User user = UserUtils.getUser();         
//				if ( user == null ) {
//					logger.error("登录用户有问题");
//				}else{
//					String  loginuser = user.getLoginName();
//					BisLog bisLog = new BisLog();
//					bisLog.setSystem("hotelrelation");
//					bisLog.setOperator(loginuser);
//					bisLog.setBussinessId(otatype.toString());
//					bisLog.setBussinssType(DistributionTypeEnum.distributionBind.getType());
//					bisLog.setContent("分销商与酒店绑定关系otatype:"+otatype+"酒店:"+ids);
//					bisLogDelegate.saveBigLog(bisLog);
//				}
//			} catch (Exception e) {
//				logger.error("error:",e);
//			}
//		} catch (Exception e) {
//			msg.failure("绑定失败");
//			logger.error("分销商与酒店绑定失败",e);
//		}
//		return new ResponseEntity<Message>(msg, HttpStatus.OK);
//	}
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @param ids
//	 * 批量删除绑定关系
//	 * @throws Exception 
//	 */
//	@RequestMapping(value = "${adminPath}/otahotel/batchdelete", method = RequestMethod.POST)
//	public  ResponseEntity<Boolean> batchdelete(HttpServletRequest request, HttpServletResponse response, String ids) throws Exception {
//		logger.info("批量删除绑定关系开始param:{}",ids);
//		otaHotelService.batchDelete(ids);
//		
//		try {
//			User user = UserUtils.getUser();         
//			if ( user == null ) {
//				logger.error("登录用户有问题");
//			}else{
//				String  loginuser = user.getLoginName();
//				BisLog bisLog = new BisLog();
//				bisLog.setSystem("hotelrelation");
//				bisLog.setOperator(loginuser);
//				bisLog.setBussinessId(ids);
//				bisLog.setBussinssType(DistributionTypeEnum.distributionBindDel.getType());
//				bisLog.setContent("分销商与酒店绑定关系主键　:"+ids);
//				bisLogDelegate.saveBigLog(bisLog);
//			}
//		} catch (Exception e) {
//			logger.error("error:",e);
//		}
//		
//		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//	}
//	/**
//	 * @param request
//	 * @param response
//	 * @param id
//	 * 删除绑定关系
//	 */
//	@RequestMapping(value = "${adminPath}/otahotel/delete", method = RequestMethod.POST)
//	public  ResponseEntity<Boolean> delete(HttpServletRequest request, HttpServletResponse response, String id) {
//		logger.info("删除绑定关系开始param:{}",id);
//		otaHotelService.delete(Long.parseLong(id));
//		try {
//			User user = UserUtils.getUser();         
//			if ( user == null ) {
//				logger.error("登录用户有问题");
//			}else{
//				String  loginuser = user.getLoginName();
//				BisLog bisLog = new BisLog();
//				bisLog.setSystem("hotelrelation");
//				bisLog.setOperator(loginuser);
//				bisLog.setBussinessId(id);
//				bisLog.setBussinssType(DistributionTypeEnum.distributionBindDel.getType());
//				bisLog.setContent("分销商与酒店绑定关系主键　:"+id);
//				bisLogDelegate.saveBigLog(bisLog);
//			}
//		} catch (Exception e) {
//			logger.error("error:",e);
//		}
//		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//	}
//	/**
//	 * 查询价格上浮比例及上浮值
//	 */
//	@RequestMapping(value = "${adminPath}/otahotel/queryotahotel", method = RequestMethod.POST)
//	public  ResponseEntity<OtaHotel> queryotahotel(Long otatype,Long hotelid) {
//		logger.info("查询价格上浮比例及上浮值开始param:{},{}",otatype,hotelid);
//		OtaHotel otahotel = otaHotelService.queryByOtatypeAndHotelid(otatype, hotelid);
//		if(otahotel!=null && otahotel.getUpper()!=null){
//			otahotel.setUpper(otahotel.getUpper().multiply(new BigDecimal("100")));
//		}
//		return new ResponseEntity<OtaHotel>(otahotel, HttpStatus.OK);
//	}
//	/**
//	 * 保存价格上浮比例及上浮值
//	 * @throws Exception 
//	 */
//	@RequestMapping(value = "${adminPath}/otahotel/saveConfigUpperPrice", method = RequestMethod.POST)
//	public  ResponseEntity<Boolean> saveConfigUpperPrice(Long otahotelid,BigDecimal upper,BigDecimal upprice) throws Exception {
//		logger.info("保存价格上浮比例及上浮值开始param:{},{},{}",otahotelid,upper,upprice);
//		OtaHotel otaHotel = otaHotelService.queryByPrimaryKey(otahotelid);
//		otaHotel.setId(otahotelid);
//		if(upper!=null){
//			upper = upper.divide(new BigDecimal("100"));
//			otaHotel.setUpper(upper);
//		}
//		otaHotel.setUpprice(upprice);
//		otaHotelService.updateOtaHotel(otaHotel);
//		
//		//刷新价格到缓存
//		priceService.initPriceToRedis(otaHotel.getOtatype(), otaHotel.getHotelid());
//		//批量更新es
//		hotelSearchService.batchUpdateEs(otaHotel.getOtatype());
//		try {
//			User user = UserUtils.getUser();         
//			if ( user == null ) {
//				logger.error("登录用户有问题");
//			}else{
//				String  loginuser = user.getLoginName();
//				BisLog bisLog = new BisLog();
//				bisLog.setSystem("distributionprice");
//				bisLog.setOperator(loginuser);
//				bisLog.setBussinessId(otahotelid.toString());
//				bisLog.setBussinssType(DistributionTypeEnum.priceUpper.getType());
//				bisLog.setContent("保存价格上浮比例及上浮值:otahotelid:"+otahotelid+",upper:"+upper+",upprice:"+upprice);
//				bisLogDelegate.saveBigLog(bisLog);
//			}
//		} catch (Exception e) {
//			logger.error("error:",e);
//		}
//		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//	}
//	
//	
//
//}
