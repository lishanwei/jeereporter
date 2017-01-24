//package com.thinkgem.jeesite.modules.gds.web.recharge;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.mk.settlement.service.ISettlementRelationService;
//import com.thinkgem.jeesite.common.web.BaseController;
//
//@RequestMapping(value = "${adminPath}/recharge")
//@Controller
//public class RechargeController  extends BaseController{
//    protected Logger logger = LoggerFactory.getLogger(getClass().getName());
//    
//    @Autowired
//    private ISettlementRelationService iSettlementRelationService;
//    
//    @RequestMapping(value="/cash", method = RequestMethod.GET)
//	public ModelAndView list(String otatype){
//    	logger.info("查询充值余额开始：otatype:{}",otatype);
//		ModelAndView mav=new ModelAndView("modules/gds/recharge/cash");
//		BigDecimal balance=iSettlementRelationService.getBalance(otatype);
//		if (balance==null ) {
//			balance=BigDecimal.ZERO;
//		}
//		/*Integer total=iSettlementRelationService.getCount4Pagination(otatype, 6);
//		int start=(page.getPageNo()-1)*page.getPageSize();
//		int end=page.getPageNo()*page.getPageSize();
//		List<SettlementAccntDetail> list=iSettlementRelationService.getRechargesWithPagination(otatype, 6, start, end);
//		
//		mav.addObject("page", page);
//		mav.addObject("result", list);*/
//		mav.addObject("otatype", otatype);
//		mav.addObject("balance", balance);
//		logger.info("查询充值余额结束：otatype:{},balance:{}",otatype,balance);
//		return mav;
//	}
//    
//    @RequestMapping(value="/save", method = RequestMethod.POST)
//	public ResponseEntity<Map<String,String>> save(String otatype,BigDecimal cash){
//    	logger.info("充值开始：otatype:{},cash:{}",otatype,cash);
//    	Map<String,String> map = new HashMap<String,String>();
//		boolean result=false;
//		try {
//			result = iSettlementRelationService.recharge(otatype, cash);
//		} catch (Exception e) {
//			logger.error("充值异常：{}",e);
//		}
//		String data="";
//		if (result) {
//			data="success";
//		}else{
//			data="fail";
//		}
//		map.put("result", data);
//		logger.info("充值结束：otatype:{},result:{}",otatype,data);
//		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
//	}
//}
