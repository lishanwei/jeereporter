package com.thinkgem.jeesite.modules.rpt.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingMonthlyReport;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingWeeklyReport;
import com.thinkgem.jeesite.modules.rpt.service.ZinccoatingthicknessLogService;

/**
 * 锌层测厚月报controller.
 * 
 * @author aiqing.chu
 * @version 2016-07-10
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/zinccoatingMonthlyReport")
public class ZinccoatingMonthlyReportController extends BaseController {
	
	@Autowired
	private ZinccoatingthicknessLogService zinccoatingthicknessLogService;
	
	/**
	 * 
	 * @param zinccoatingWeeklyReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("rpt:zinccoatingMonthlyReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZinccoatingWeeklyReport zinccoatingWeeklyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		long startTime = System.currentTimeMillis();
		Page<ZinccoatingWeeklyReport> page = new Page<ZinccoatingWeeklyReport>(request, response);
		page.setOrderBy(" logtime asc ");
		zinccoatingWeeklyReport.setPage(page);
		List<ZinccoatingWeeklyReport> list = zinccoatingthicknessLogService.queryZinccoatingmonthlyReport(zinccoatingWeeklyReport);
		page.setList(list);
		model.addAttribute("page", page);
		model.addAttribute("principal", SecurityUtils.getSubject().getPrincipal().toString());
		long endTime = System.currentTimeMillis();
		long hs = endTime - startTime;
		model.addAttribute("taketimes", "本次数据加载耗时" + hs+ "毫秒.");
		return "modules/rpt/zinccoatingMonthlyReportList";
	}
	
	/**
	 * 图表展示
	 * @param zinccoatingMonthlyReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("rpt:zinccoatingMonthlyReport:view")
	@RequestMapping(value = "chartform")
	public String chartform(ZinccoatingMonthlyReport zinccoatingMonthlyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/rpt/zinccoatingMonthlyChartform";
	}
}
