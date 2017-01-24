//package com.thinkgem.jeesite.modules.gds.web.province;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.fangbaba.basic.face.bean.CityModel;
//import com.fangbaba.basic.face.bean.ProvinceModel;
//import com.fangbaba.basic.face.service.CityService;
//import com.fangbaba.basic.face.service.ProvinceService;
//import com.google.common.collect.Maps;
//import com.thinkgem.jeesite.common.web.BaseController;
//
//@Controller
//@RequestMapping(value = "${adminPath}/province")
//public class ProvinceController extends BaseController{
//	@Autowired
//	private ProvinceService provinceService;
//	@Autowired
//	private CityService cityService;
//	
//	/**
//	 * 查询所有省
//	 */
//	@RequestMapping(value = "/getAllProvinces", method = RequestMethod.POST)
//    public  ResponseEntity<Map<String, Object>> getAllProvinces() {
//		logger.info("查询所有省开始");
//		List<ProvinceModel> result = provinceService.queryAllProvinces();
//        Map<String, Object> rtnMap = Maps.newHashMap();
//        rtnMap.put("result", result);
//        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//    }
//	/**
//	 * @param proid
//	 * 通过省查询所有城市
//	 */
//	@RequestMapping(value = "/getAllCitys", method = RequestMethod.POST)
//	public  ResponseEntity<Map<String, Object>>  getAllCitys(Integer proid) {
//		logger.info("通过省查询所有城市开始param:{}",proid);
//		List<CityModel> result = cityService.queryAllCitysByProvinceCode(proid);
//		Map<String, Object> rtnMap = Maps.newHashMap();
//        rtnMap.put("result", result);
//        return new ResponseEntity<Map<String, Object>>(rtnMap, HttpStatus.OK);
//	}
//}
