//package com.thinkgem.jeesite.modules.gds.web.hotelrelation;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.fangbaba.basic.face.bean.HotelModel;
//import com.fangbaba.gds.Message;
//import com.fangbaba.gds.face.bean.HotelModelEsBean;
//import com.fangbaba.gds.face.bean.Page;
//import com.fangbaba.gds.face.service.IGDSHotelTagService;
//import com.fangbaba.gds.face.service.IOtaHotelService;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.google.gson.Gson;
//import com.thinkgem.jeesite.common.web.BaseController;
//
//@Controller
//@RequestMapping(value = "${adminPath}/hotel")
//public class HotelController extends BaseController {
//
//
//    @Autowired
//    private IGDSHotelTagService hotelTagService;
//    @Autowired
//    private IOtaHotelService otaHotelService;
//
//    /**
//     * @param hotelModel
//     * @param page       查询酒店列表
//     */
//    @RequestMapping(value = "/queryhotelsByOtatype", method = RequestMethod.POST)
//    public ResponseEntity<Message> queryotahotels(HotelModelEsBean hotelModelEsBean, 
//    		@RequestParam(value = "busiarea[]", required = false) List<String> busiarea, 
//    		@RequestParam(value = "feature[]", required = false) List<String> feature, 
//    		@RequestParam(value = "facilities[]", required = false) List<String> facilities, 
//    		@RequestParam(value = "other[]", required = false) List<String> other, 
//    		HttpServletRequest httpServletRequest, Page page) {
//        logger.info("查询酒店列表开始param:{}", new Gson().toJson(hotelModelEsBean));
//
//        List<List<Long>> tags = new ArrayList<List<Long>>();
//        Message msg = new Message();
//        
//        fillList(busiarea,tags);
//        fillList(feature,tags);
//        fillList(facilities,tags);
//        fillList(other,tags);
//        
//        msg = otaHotelService.queryhotelsByOtatype(hotelModelEsBean, tags, page.getPageNo(), page.getPageSize());
//        if(msg.getState()==1){
//        	return new ResponseEntity<Message>(msg, HttpStatus.OK);
//        }else{
//        	Map<String, Object> map = (Map<String, Object>)msg.getRs();
//        	page.setTotalRecord((Integer) map.get("total"));
//        	page.setTotalPage(page.getTotalPage());
//        	Map<String, Object> rtnMap = Maps.newHashMap();
//        	rtnMap.put("result", map.get("data"));
//        	rtnMap.put("page", page);
//        	msg.setRs(rtnMap);
//        	return new ResponseEntity<Message>(msg, HttpStatus.OK);
//        }
//    }
//    @RequestMapping(value = "/relateByCondition", method = RequestMethod.POST)
//    public ResponseEntity<Message> relateByCondition(HotelModelEsBean hotelModelEsBean, 
//    		@RequestParam(value = "busiarea[]", required = false) List<String> busiarea, 
//    		@RequestParam(value = "feature[]", required = false) List<String> feature, 
//    		@RequestParam(value = "facilities[]", required = false) List<String> facilities, 
//    		@RequestParam(value = "other[]", required = false) List<String> other, 
//    		HttpServletRequest httpServletRequest, Page page) throws Exception {
//    	logger.info("查询酒店列表开始param:{}", new Gson().toJson(hotelModelEsBean));
//    	
//    	List<List<Long>> tags = new ArrayList<List<Long>>();
//    	Message msg = new Message();
//    	StringBuffer ids = new StringBuffer();
//    	
//    	fillList(busiarea,tags);
//    	fillList(feature,tags);
//    	fillList(facilities,tags);
//    	fillList(other,tags);
//    	
//    	Integer totalrecord;
//    	Integer totalpage;
//    	msg = otaHotelService.queryhotelsByOtatype(hotelModelEsBean, tags, page.getPageNo(), page.getPageSize());
//    	if(msg.getState()==1){
//    		return new ResponseEntity<Message>(msg, HttpStatus.OK);
//		}else{
//			Map<String, Object> map = (Map<String, Object>)msg.getRs();
//			totalrecord = (Integer) map.get("total");
//			page.setTotalRecord(totalrecord);
//			totalpage = page.getTotalPage();
//			page.setPageNo(page.getPageNo()+1);
//			List<HotelModel> list = (List<HotelModel>)map.get("data");
//			for (HotelModel hotelModel : list) {
//				ids.append(hotelModel.getId().toString()).append(",");
//			}
//		}
//    	if(totalrecord==null){
//    		msg.failure("未查询到酒店记录,请更换搜索条件");
//    		return new ResponseEntity<Message>(msg, HttpStatus.OK);
//    	}else{
//    		//分页查询
//    		//循环
//        	for (int i = 2; i <= totalpage; i++) {
//        		msg = otaHotelService.queryhotelsByOtatype(hotelModelEsBean, tags, i, page.getPageSize());
//        		page.setPageNo(page.getPageNo()+1);
//        		if(msg.getState()==1){
//        			continue;
//        		}else{
//        			Map<String, Object> map = (Map<String, Object>)msg.getRs();
//        			List<HotelModel> list = (List<HotelModel>)map.get("data");
//        			for (HotelModel hotelModel : list) {
//        				ids.append(hotelModel.getId().toString()).append(",");
//        			}
//        		}
//    		}
//        	String bindids = "";
//        	if(ids.length()>1){
//        		ids.deleteCharAt(ids.length()-1);
//        		bindids = ids.toString();
//        		msg = otaHotelService.bindrelation(hotelModelEsBean.getOtatype(), bindids);
//        	}
//    	}
//    	return new ResponseEntity<Message>(msg, HttpStatus.OK);
//    	
//    }
//    
//    private void fillList(List<String> list,List<List<Long>> tags){
//    	if (list != null && list.size() > 0) {
//        	List<Long> tags1 = Lists.newArrayList();
//        	for (String tag : list) {
//        		if(StringUtils.isNotBlank(tag)){
//        			tags1.add(Long.valueOf(tag));
//        		}
//			}
//        	tags.add(tags1);
//        }
//    }
//
//
//    @RequestMapping(value = "/getloginuser", method = RequestMethod.POST)
//    public ResponseEntity<Map<String, Object>> getloginuser() {
//        Map<String, Object> rtnMap = Maps.newHashMap();
//        //todo 当前登录用户
//        rtnMap.put("result", "test");
//        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//    }
//
//    /**
//     * 批量更新酒店标签
//     *
//     * @return
//     */
//    @RequestMapping(value = "/synchoteltag", method = RequestMethod.POST)
//    public ResponseEntity<Map<String, Object>> syncHotelTags() {
//        Map<String, Object> rtnMap = Maps.newHashMap();
//        rtnMap.put("update_count", hotelTagService.batchSyncHotelTags(1, 5));
//        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//    }
//}
