//package com.thinkgem.jeesite.modules.gds.web.distribution;
//
//import java.math.BigDecimal;
//import java.util.Map;
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
//
//import com.fangbaba.gds.face.service.IDistributionPriceService;
//import com.google.common.collect.Maps;
//import com.thinkgem.jeesite.common.web.BaseController;
//
//@Controller
//@RequestMapping(value = "${adminPath}/distributorprice")
//public class DistributorPriceController extends BaseController{
//	@Autowired
//	private IDistributionPriceService iDistributionPriceService;
//	
//	/**
//	 * @param request
//	 * @param response
//	 * @param page
//	 * @param querybean
//	 * 查询分销商列表
//	 */
//	@RequestMapping(value = "/setDistributionPrice", method = RequestMethod.POST)
//    public  ResponseEntity<Map<String, Object>> setDistributionPrice(HttpServletRequest request, HttpServletResponse response) {
//		//logger.info("设置分销价格传来的参数:{}");
//		com.fangbaba.gds.face.base.RetInfo<String> result = iDistributionPriceService.setDistributionPrice(1001, "0.9", "3ZyeYNlYZ6LEhi6WWak123");
//        Map<String, Object> rtnMap = Maps.newHashMap();
//        rtnMap.put("result", result);
//        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//    }
//	
//	@RequestMapping(value = "/findDiscountPrice", method = RequestMethod.POST)
//    public  ResponseEntity<Map<String, Object>> findDiscountPrice(HttpServletRequest request, HttpServletResponse response) {
//		BigDecimal aBigDecimal  = iDistributionPriceService.findDiscountPrice(399L, 1001, new BigDecimal(192L));
//        Map<String, Object> rtnMap = Maps.newHashMap();
//        rtnMap.put("result", aBigDecimal);
//        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//    }
//	
//}
