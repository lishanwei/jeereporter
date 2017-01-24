//package com.thinkgem.jeesite.modules.gds.web.order;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.fangbaba.gds.face.bean.GdsOrder;
//import com.fangbaba.gds.face.bean.GdsOrderDetail;
//import com.fangbaba.gds.face.bean.TripDateInfo;
//import com.fangbaba.gds.face.service.IGdsOrderService;
//import com.fangbaba.order.common.enums.OrderStatusEnum;
//import com.google.common.base.Strings;
//import com.google.common.collect.Maps;
//import com.thinkgem.jeesite.common.utils.BusinessUtil;
//import com.thinkgem.jeesite.common.utils.DateUtil;
//import com.thinkgem.jeesite.common.web.BaseController;
//
///**
// * 订单类
// * @author tankai
// *
// */
//@Controller
//@RequestMapping(value = "${adminPath}/order")
//public class OrderController  extends BaseController{
//
//	@Autowired
//	private IGdsOrderService gdsOrderService;
//	
//	/**
//	 * 查询订单
//	 * @param request
//	 * @param response
//	 * @param hotelQuery
//	 * @return
//	 */
//	@RequiresPermissions("gds:order:view")
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView queryOrder(HttpServletRequest request, HttpServletResponse response,GdsOrder tripOrder,Long otaType) {
//		logger.info("开始查询订单");
//		ModelAndView mav = new ModelAndView();
//		try {
//			
////			if(otaType==null){
////				mav.addObject("tip","用户信息获取失败");
////				return mav;
////			}
//			
//			if(tripOrder == null){
//				tripOrder = new GdsOrder();
//			}
//			//封装页面展示时间
//			TripDateInfo dateInfo = new TripDateInfo();
//			BusinessUtil.fillTripDate(dateInfo, DateUtil.strToDate(tripOrder.getBeginTime(), "yyyy-MM-dd"), DateUtil.strToDate(tripOrder.getEndTime(), "yyyy-MM-dd"));
//			
//			if(StringUtils.isBlank(tripOrder.getBeginTime())){
//				tripOrder.setBeginTime(dateInfo.getDefaultStartDate());
//				
//			}
//			if(StringUtils.isBlank(tripOrder.getEndTime())){
//				tripOrder.setEndTime(dateInfo.getDefaultEndDate());
//			}
//			if (tripOrder.getStatus()!=null) {
//				tripOrder.setBeginTime(null);
//				tripOrder.setEndTime(null);
//			}
//			
//			if(tripOrder.getTotalItem()==0){//等于0时查询总记录数
//				Integer totalItem = gdsOrderService.queryOrderCount(otaType, tripOrder);
//				tripOrder.setTotalItem(totalItem);
//				logger.info("总订单数:"+totalItem);
//			}
//			
//			List<GdsOrder> tripOrders = gdsOrderService.queryOrder(otaType,tripOrder);
//			
//			mav.addObject("tripOrders",tripOrders);
//			mav.addObject("pageItem",tripOrder);
//			mav.addObject("otaType",otaType);
//			
//
//			
//			mav.addObject("tripDateInfo",dateInfo);
//		} catch (Exception e) {
//			mav.addObject("tip","系统正忙，请稍后重试");
//			logger.error("加载订单列表出错",e);
//		}
//		
//		//调用订单接口，下单
//		mav.setViewName("modules/gds/order/list");
//		return mav;
//	}
//    
//	@RequiresPermissions("gds:order:view")
//	@RequestMapping(value = "/detail")
//	public ModelAndView getOrderDetails(String orderid,Long otaType) {
//		logger.info("获取订单详情开始.....:"+orderid);
//		if (Strings.isNullOrEmpty(orderid)) {
//			logger.info("订单id为空");
//			return null;
//		}
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("modules/gds/order/orderDetailList");
//		List<GdsOrderDetail> orderDetails= null;
//       try {
//    	  orderDetails = gdsOrderService.queryOrderDetailsByOrderId(Long.parseLong(orderid), otaType);
//    	  mav.addObject("orderDetails", orderDetails);
//       } catch (Exception e) {
//			logger.error("获取订单详情失败",e);
//			return mav;
//		}
//		
//		return mav;
//	}
//	@RequiresPermissions("gds:order:edit")
//	  @RequestMapping(value = "/confirmOrder",method = RequestMethod.POST)
//       public ResponseEntity<Map<String, Object>> confirmOrder(String orderid,String loginuser){
//           logger.info("确认订单开始，orderid.:"+orderid+",loginuser:"+loginuser);
//           Map<String, Object> rtnMap = Maps.newHashMap();
//           if (Strings.isNullOrEmpty(orderid)) {
//               logger.info("订单id为空");
//               rtnMap.put("result", "订单id为空");
//               return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//           }
//           if (Strings.isNullOrEmpty(loginuser)) {
//               logger.info("登录用户有问题");
//               rtnMap.put("result", "登录用户有问题");
//               return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//           }
//           
//           try {
//               Boolean flag = gdsOrderService.confirmOrder(Long.parseLong(orderid),loginuser);
//               logger.info("确认订单结束，订单中心返回信息,orderid:"+orderid+",loginuser:"+loginuser+",flag:"+flag);
//               if(flag==null){
//                   rtnMap.put("result", "订单中心返回为空！");
//                   return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//               }else{
//                   if(flag==true){
//                       rtnMap.put("result", "SUCCESS");
//                       return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//                   }else{
//                       rtnMap.put("result", "订单确认失败，请重试!");
//                       return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//                   }
//               }
//           } catch (Exception e) {
//               logger.error("确认订单失败",e);
//               rtnMap.put("result", "确认订单异常");
//               return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//           }
//           
//       }
//	  
//	  @RequiresPermissions("gds:order:edit")
//	  @RequestMapping(value = "/refuseOrder",method = RequestMethod.POST)
//      public ResponseEntity<Map<String, Object>> refuseOrder(String orderid,String loginuser){
//          logger.info("拒绝订单开始，orderid.:"+orderid+",loginuser:"+loginuser);
//          Map<String, Object> rtnMap = Maps.newHashMap();
//          if (Strings.isNullOrEmpty(orderid)) {
//              logger.info("订单id为空");
//              rtnMap.put("result", "订单id为空");
//              return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//          }
//          if (Strings.isNullOrEmpty(loginuser)) {
//              logger.info("登录用户有问题");
//              rtnMap.put("result", "登录用户有问题");
//              return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//          }
//          
//          try {
//              Boolean flag = gdsOrderService.refuseOrder(Long.parseLong(orderid),loginuser);
//              logger.info("拒绝订单结束，订单中心返回信息,orderid:"+orderid+",loginuser:"+loginuser+",flag:"+flag);
//              if(flag==null){
//                  rtnMap.put("result", "订单中心返回为空！");
//                  return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//              }else{
//                  if(flag==true){
//                      rtnMap.put("result", "SUCCESS");
//                      return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//                  }else{
//                      rtnMap.put("result", "拒绝确认失败，请重试!");
//                      return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//                  }
//              }
//          } catch (Exception e) {
//              logger.error("拒绝订单失败",e);
//              rtnMap.put("result", "拒绝订单异常");
//              return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//          }
//          
//      }
//	  
//	    @RequiresPermissions("gds:order:view")
//		@RequestMapping(value = "/findNotConfirmedOrderCount")
//		public ResponseEntity<Map<String, Integer>> findOrderCount(Long otaType) {
//			logger.info("获取未确认的订单传来的otatype{}",otaType);
//			 Map<String, Integer> rtnMap = Maps.newHashMap();
//			if (otaType==null) {
//				logger.info("otatype为空");
//				rtnMap.put("count",0);
//		        return new ResponseEntity<Map<String, Integer>>(rtnMap, HttpStatus.OK);
//			}else {
//				GdsOrder gdsOrder = new GdsOrder();
//				gdsOrder.setStatus(OrderStatusEnum.toBeConfirmed.getId());
//				Integer totalItem = gdsOrderService.queryOrderCount(otaType, gdsOrder);
//				rtnMap.put("count",totalItem);
//			}
//			
//			
//		 return new ResponseEntity<Map<String, Integer>>(rtnMap, HttpStatus.OK);
//		}
//}
