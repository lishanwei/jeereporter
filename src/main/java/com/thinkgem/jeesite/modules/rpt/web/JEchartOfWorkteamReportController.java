package com.thinkgem.jeesite.modules.rpt.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.series.Bar;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingWorkTeamReport;
import com.thinkgem.jeesite.modules.rpt.service.ZinccoatingWorkTeamReportService;


/**
 * 班组echart图表报表controller.
 * 
 * @author aiqing.chu
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/jechart/workteam")
public class JEchartOfWorkteamReportController extends BaseController {

	@Autowired
	private ZinccoatingWorkTeamReportService zinccoatingWorkTeamReportService;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryEchartOptionData"/*, method = RequestMethod.POST*/)
	public ResponseEntity<JSONObject> queryEchartOptionData(ZinccoatingWorkTeamReport zinccoatingWorkTeamReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		//创建Option
	    Option option = new Option();
	    Object[] names = new String[]{"上锌量(千克)","过钢量(吨)"};
	    option.title("班组上锌量与过钢量对比图").tooltip(Trigger.axis).legend(names);
	    //横轴为值轴
	    //option.xAxis(new ValueAxis().boundaryGap(0d, 0.01));
	    option.yAxis(new ValueAxis().boundaryGap(0d, 0.01));
	    //创建类目轴
	    CategoryAxis category = new CategoryAxis();
	    //柱状数据
	    Bar bar1 = new Bar(String.valueOf(names[0]));
	    //bar1.setCoordinateSystem("cartesian2d");
	    //饼图数据
	    Bar bar2 = new Bar(String.valueOf(names[1]));
	    //bar2.setCoordinateSystem("cartesian2d");
	    
	    Map<String, Object> params = CookieUtils.getParameterMap(request);
//	    if (params.containsKey("beginLogtime")) {
//	    	//
//	    	if (params.get("beginLogtime") != null && !"".equals(params.get("beginLogtime"))) {
//	    	    Date beginLogtime = (Date) params.get("beginLogtime");
//	    	    params.put("beginLogtime", com.thinkgem.jeesite.common.utils.DateUtils.formatDate(beginLogtime));
//	    	}
//	    }
//	    if (params.containsKey("endLogtime")) {
//	    	//
//	    	if (params.get("endLogtime") != null && !"".equals(params.get("endLogtime"))) {
//	    	    Date endLogtime = (Date) params.get("endLogtime");
//	    	    params.put("endLogtime", com.thinkgem.jeesite.common.utils.DateUtils.formatDate(endLogtime));
//	    	}
//	    }
	    List<Map<String, Object>> list = zinccoatingWorkTeamReportService.queryWorkteamReportEChartData(params);
	    //循环数据
	    for (Map<String, Object> objectMap : list) {
	        //设置类目
	        category.data(objectMap.get("loggroup"));
	        //类目对应的柱状图
	        bar1.data(objectMap.get("zinctotalinc"));
	        //饼图数据
	        bar2.data(objectMap.get("totalsteel"));
	    }
	    //设置类目轴
	    option.xAxis(category);
	    //设置数据
	    option.series(bar1, bar2);
		
		//
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", true);
		jsonObj.put("option", option);
		return new ResponseEntity<JSONObject>(jsonObj, HttpStatus.OK);
	}

}
