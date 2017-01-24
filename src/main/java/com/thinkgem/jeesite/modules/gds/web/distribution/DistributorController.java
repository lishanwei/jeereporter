//package com.thinkgem.jeesite.modules.gds.web.distribution;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.fangbaba.basic.face.bean.Tags;
//import com.fangbaba.gds.Message;
//import com.fangbaba.gds.enums.ChannelEnum;
//import com.fangbaba.gds.face.bean.DistributionQueryBean;
//import com.fangbaba.gds.face.bean.KVDomain;
//import com.fangbaba.gds.face.bean.Page;
//import com.fangbaba.gds.face.service.IDistributorConfigService;
//import com.fangbaba.gds.face.service.IHotelSearchService;
//import com.fangbaba.gds.po.DistributorConfig;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.google.gson.Gson;
//import com.lz.mongo.bislog.BisLog;
//import com.lz.mongo.bislog.BisLogDelegate;
//import com.mk.settlement.enums.TripAccntTypeEnums;
//import com.mk.settlement.model.SettlementRule;
//import com.mk.settlement.service.ISettlementRelationService;
//import com.thinkgem.jeesite.common.enums.DistributionTypeEnum;
//import com.thinkgem.jeesite.common.utils.MathUtil;
//import com.thinkgem.jeesite.common.utils.QiniuUtils;
//import com.thinkgem.jeesite.common.web.BaseController;
//import com.thinkgem.jeesite.modules.sys.entity.User;
//import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
//
//
//@Controller
//@RequestMapping(value = "${adminPath}/distributor")
//public class DistributorController extends BaseController{
//	@Autowired
//	private IDistributorConfigService distributorConfigService;
//	@Autowired
//	private ISettlementRelationService settlementRelationService;
//	@Autowired
//	private IHotelSearchService hotelSearchService;
//	@Autowired
//	private BisLogDelegate bisLogDelegate;
//
//	/**
//	 * @param request
//	 * @param response
//	 * @param page
//	 * @param querybean
//	 * 查询分销商列表
//	 */
//	@RequiresPermissions("gds:distributor:view")
//	@RequestMapping(value = "/list", method = RequestMethod.POST)
//    public  ResponseEntity<Map<String, Object>> list(HttpServletRequest request, HttpServletResponse response, Page page,DistributionQueryBean querybean) {
//		logger.info("查询分销商列表开始param:{}",querybean.toString());
//		Map<String, Object> map = distributorConfigService.pageQuery(page,querybean);
//		page.setTotalRecord((Integer) map.get("total"));
//        page.setTotalPage(page.getTotalPage());
//        Map<String, Object> rtnMap = Maps.newHashMap();
//        rtnMap.put("result", map.get("data"));
//        rtnMap.put("page", page);
//        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//    }
//	/**
//	 * 跳转列表页面
//	 * @return
//	 */
//	@RequiresPermissions("gds:distributor:view")
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public  ModelAndView  list() {
//		 ModelAndView modelAndView = new ModelAndView("modules/gds/distributor/list");  
//         return modelAndView; 
//	}
//	/**
//	 * 跳转酒店关系页面
//	 * @return
//	 */
//	 @RequiresPermissions("gds:distributor:view")
//	@RequestMapping(value = "/list/hotelrelation", method = RequestMethod.GET)
//	public  ModelAndView  hotelrelation(Long otatype) {
//		ModelAndView modelAndView = new ModelAndView("modules/gds/distributor/hotelrelation");  
//		
//		//查询标签
//		modelAndView.addObject("busiarea", queryTags(1l));   //商圈位置
////       modelAndView.addObject("pricerange", queryTags(2l)); //房价区间
//	    modelAndView.addObject("feature", queryTags(2l));    //类型特色
//        modelAndView.addObject("facilities", queryTags(3l)); //设施服务
//		modelAndView.addObject("otatype", otatype);
//		return modelAndView; 
//	}
//	
//	
//	 private List<KVDomain> queryTags(Long taggroupid) {
//	        List<KVDomain> rtnList = Lists.newArrayList();
//
//	        List<Tags> tagslist = hotelSearchService.queryTagsByGroupId(taggroupid);
//	        if (tagslist == null)
//	            return rtnList;
//
//	        for (Tags tag : tagslist) {
//	            rtnList.add(new KVDomain(String.valueOf(tag.getId()), tag.getTagname()));
//	        }
//	        return rtnList;
//	    }
//	 
//	@RequiresPermissions("gds:distributor:view")
//	@RequestMapping(value = "/querychannelid", method = RequestMethod.POST)
//    public  ResponseEntity<Message> querychannelid() {
//		Message msg = new Message();
//		List<KVDomain> rtnList = Lists.newArrayList();
//		for (ChannelEnum channelidEnum:ChannelEnum.values()) {
//			rtnList.add(new KVDomain(String.valueOf(channelidEnum.getId()), channelidEnum.getName()));
//		}
//		msg.setRs(rtnList);
//        return new ResponseEntity<Message>(msg, HttpStatus.OK);
//    }
//	
//	
//	/**
//	 * 跳转修改页面
//	 * @return
//	 */
//	 @RequiresPermissions("gds:distributor:edit")
//	@RequestMapping(value = "/list/edit", method = RequestMethod.GET)
//	public  ModelAndView  edit(String id) {
//		ModelAndView modelAndView = new ModelAndView("modules/gds/distributor/edit");  
//		String upToken=QiniuUtils.generateSimpleUploadTicket();
//		modelAndView.addObject("uptoken", upToken);
//		return modelAndView; 
//	}
//	/**
//	 * @param request
//	 * @param response
//	 * @param ids
//	 * 批量删除分销商
//	 */
//	 @RequiresPermissions("gds:distributor:delete")
//	@RequestMapping(value = "/batchdelete", method = RequestMethod.POST)
//	public  ResponseEntity<Map<String,Integer>> batchdelete(HttpServletRequest request, HttpServletResponse response, String ids, String loginnames) {
//		
//         Map<String,Integer> map = new HashMap<String,Integer>();
//		//todo 当前登录用户
//	     logger.info("删除分销商开始param:,user:"+ids+",loginname:"+loginnames);
//	     Integer renum =  distributorConfigService.batchDelete(ids,loginnames);
//	     map.put("result",renum);
//	     
//	     return new ResponseEntity<Map<String,Integer>>(map, HttpStatus.OK);
//	}
//	/**
//	 * @param request
//	 * @param response
//	 * @param id
//	 * 删除分销商
//	 */
//	 @RequiresPermissions("gds:distributor:delete")
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public  ResponseEntity<Map<String,Integer>> delete(HttpServletRequest request, HttpServletResponse response, String id,String loginname) {
//		//todo 当前登录用户
//	     Map<String,Integer> map = new HashMap<String,Integer>();
//		logger.info("删除分销商开始param:,user:"+id+",loginname:"+loginname);
//		Integer renum =  distributorConfigService.delDistributorConfig(Long.parseLong(id),loginname);
//		logger.info("删除分销商开始param:,user:"+id+",loginname:"+loginname+",result:"+renum);
//		map.put("result",renum);
//		try {
//			User user = UserUtils.getUser();
//			if ( user == null ) {
//				logger.error("登录用户有问题");
//			}else{
//				String  loginuser = user.getLoginName();
//				BisLog bisLog = new BisLog();
//				bisLog.setSystem("distribution");
//				bisLog.setOperator(loginuser);
//				bisLog.setBussinessId(id);
//				bisLog.setBussinssType(DistributionTypeEnum.distributionDel.getType());
//				bisLog.setContent("删除主键为"+id+"的分销商");
//				bisLogDelegate.saveBigLog(bisLog);
//			}
//		} catch (Exception e) {
//			logger.error("error:",e);
//		}
//		return new ResponseEntity<Map<String,Integer>>(map, HttpStatus.OK);
//	}
//	
//	/**
//	 * 新增分销商
//	 * @param record
//	 * @throws Exception 
//	 */
//	 @RequiresPermissions("gds:distributor:edit")
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public ResponseEntity<Map<String,String>> save(DistributorConfig distributorConfig) throws Exception {
//		Gson gson = new Gson();
//		Map<String,String> map = new HashMap<String,String>();
//		logger.info("开始设置分销商信息："+gson.toJson(distributorConfig));
//		distributorConfig.setChannelid(ChannelEnum.TRAVEL.getId());//缺省为旅行社渠道
//		if(distributorConfig.getUpper()!=null){
//			distributorConfig.setUpper(distributorConfig.getUpper().divide(new BigDecimal("100")));
//		}
//		int flagNum  = distributorConfigService.saveAllDistributorConfig(distributorConfig);
//		 
//		//999表示正确，其它错误
//		logger.info("结束设置分销商信息："+flagNum);
//		if(flagNum==999){
//		    map.put("result", "999");
//		}
//		if(flagNum==1){
//            map.put("result", "1");
//        }
//		if(flagNum==2){
//            map.put("result", "2");
//        }
//		if(flagNum==3){
//            map.put("result", "3");
//        }
//		if(flagNum==4){
//            map.put("result", "4");
//        }
//		if(flagNum==5){
//            map.put("result", "5");
//        }
//		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
//	}
//	
//	/**
//	 * 获取结算规则
//	 */
////	 @RequiresPermissions("gds:distributor:view")
////	@RequestMapping(value = "/settlementinfo", method = RequestMethod.POST)
////    public  ResponseEntity<Map<String, Object>> settlementinfo() {
////        List<SettlementRule> result = settlementRelationService.getAllRule();
////        Map<String, Object> rtnMap = Maps.newHashMap();
////        rtnMap.put("result", result);
////        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
////    }
//	/**
//	 * 获取10000000~20000000随机数
//	 */
//	 @RequiresPermissions("gds:distributor:view")
//	@RequestMapping(value = "/getRandomNumber", method = RequestMethod.POST)
//	public  ResponseEntity<Map<String, Object>> getRandomNumber() {
//		Long otatype = MathUtil.getRandomNum();	
//		Map<String, Object> rtnMap = Maps.newHashMap();
//		rtnMap.put("result", otatype);
//		return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	}
//	/**
//	 * 获取分销商所有类型
//	 */
//	 @RequiresPermissions("gds:distributor:view")
//	@RequestMapping(value = "/gdstypeinfo", method = RequestMethod.POST)
//	public  ResponseEntity<Map<String, Object>> gdstypeinfo() {
//		List<DistributorConfig> result = distributorConfigService.queryAll();
//		Map<String, Object> rtnMap = Maps.newHashMap();
//		rtnMap.put("result", result);
//		return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	}
//	/**
//	 * @param id
//	 * 查询分销商详细
//	 */
//	 @RequiresPermissions("gds:distributor:view")
//	@RequestMapping(value = "/distributorinfo", method = RequestMethod.POST)
//	public  ResponseEntity<Map<String, Object>> distributorinfo(Long id) {
//		logger.info("查询分销商详细开始param:{}",id);
//		DistributorConfig result = distributorConfigService.getDistributorConfig(id);
//		if(result!=null && result.getUpper()!=null){
//			result.setUpper(result.getUpper().multiply(new BigDecimal("100")));
//		}
//		try {
//			TripAccntTypeEnums type=settlementRelationService.getTripAccntType(result.getOtatype().toString());
//			if (type!=null ) {
//				logger.info("结算返回付费类型结果:{}",type.getIndex());
//				Integer index=type.getIndex();
//				result.setSettlementCurrency(index.toString());
//				//后付费
//				if(2==type.getIndex()){
//					BigDecimal postPayThreshold = settlementRelationService.getPostPayThreshold(result.getOtatype().toString());
//					result.setPostPayThreshold(postPayThreshold);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("error:",e);
//		}
//		Map<String, Object> rtnMap = Maps.newHashMap();
//		rtnMap.put("result", result);
//		return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	}
//	 @RequiresPermissions("gds:distributor:view")
//	@RequestMapping(value = "/rechargecheck", method = RequestMethod.POST)
//	public  ResponseEntity<Map<String, Object>> rechargeCheck(Long otatype) {
//		logger.info("查询分销商充值类型详细开始param:{}",otatype);
//		Integer result=1;
//		if (otatype!=null) {
//			try {
//                TripAccntTypeEnums type=settlementRelationService.getTripAccntType(otatype.toString());
//                if (type!=null ) {
//                	if (type.getIndex()==2) {
//                		result=2;
//                	}
//                }
//            } catch (Exception e) {
//                logger.error("rechargeCheck:{}",e);
//            }
//		}
//		Map<String, Object> rtnMap = Maps.newHashMap();
//		rtnMap.put("result", result);
//		return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	}
//	 
//	 @RequiresPermissions("gds:distributor:view")
//	   @RequestMapping(value = "/checkLoginName", method = RequestMethod.POST)
//	    public  ResponseEntity<Map<String, Object>> checkLoginName(String loginaccount) {
//	        logger.info("传入的用户名param:{}",loginaccount);
//	        DistributorConfig distributorConfig = new DistributorConfig();
//	        distributorConfig.setLoginaccount(loginaccount);
//	        Integer result = distributorConfigService.checkLoginName(distributorConfig);
//	        Map<String, Object> rtnMap = Maps.newHashMap();
//	        rtnMap.put("result", result);
//	        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	    }
//	 
//	 @RequiresPermissions("gds:distributor:view")
//	    @RequestMapping(value = "/checkContactPhone", method = RequestMethod.POST)
//	    public  ResponseEntity<Map<String, Object>> checkContactPhone(String contactphone) {
//	        logger.info("传入的手机号param:{}",contactphone);
//	        DistributorConfig distributorConfig = new DistributorConfig();
//	        distributorConfig.setContactphone(contactphone);
//	        Integer result = distributorConfigService.checkContactPhone(distributorConfig);
//	        Map<String, Object> rtnMap = Maps.newHashMap();
//	        rtnMap.put("result", result);
//	        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	    }
//}
