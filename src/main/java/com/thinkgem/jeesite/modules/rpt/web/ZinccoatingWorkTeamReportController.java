package com.thinkgem.jeesite.modules.rpt.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingWorkTeamReport;
import com.thinkgem.jeesite.modules.rpt.service.ZinccoatingWorkTeamReportService;

/**
 * 锌层测厚班组报表controller.
 * 
 * @author aiqing.chu
 * @version 2016-07-07
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/zinccoatingWorkTeamReport")
public class ZinccoatingWorkTeamReportController extends BaseController {

	@Autowired
	private ZinccoatingWorkTeamReportService zinccoatingWorkTeamReportService;
	
	@ModelAttribute
	public ZinccoatingWorkTeamReport get(@RequestParam(required=false) String id) {
		ZinccoatingWorkTeamReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = zinccoatingWorkTeamReportService.get(id);
		}
		if (entity == null){
			entity = new ZinccoatingWorkTeamReport();
		}
		return entity;
	}
	
	/**
	 * 
	 * @param zinccoatingWorkTeamReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("rpt:zinccoatingWorkTeamReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZinccoatingWorkTeamReport zinccoatingWorkTeamReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		long startTime = System.currentTimeMillis();
		Page<ZinccoatingWorkTeamReport> page = new Page<ZinccoatingWorkTeamReport>(request, response);
		page.setOrderBy(" logtime asc ");
		zinccoatingWorkTeamReport.setPage(page);
		List<ZinccoatingWorkTeamReport> list = zinccoatingWorkTeamReportService.queryZinccoatingworkteamReport(zinccoatingWorkTeamReport);
		page.setList(list);
		model.addAttribute("page", page);
		model.addAttribute("principal", SecurityUtils.getSubject().getPrincipal().toString());
		long endTime = System.currentTimeMillis();
		long hs = endTime - startTime;
		model.addAttribute("taketimes", "本次数据加载耗时" + hs+ "毫秒.");
		return "modules/rpt/zinccoatingWorkTeamReportList";
	}
	
	/**
	 * 图表展示
	 * @param zinccoatingWorkTeamReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("rpt:zinccoatingWorkTeamReport:view")
	@RequestMapping(value = "chartform")
	public String chartform(ZinccoatingWorkTeamReport zinccoatingWorkTeamReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/rpt/zinccoatingWorkTeamChartform";
	}
	
	/**
	 * 导出报表数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("rpt:zinccoatingWorkTeamReport:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ZinccoatingWorkTeamReport zinccoatingWorkTeamReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "班组报表数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<ZinccoatingWorkTeamReport> page = new Page<ZinccoatingWorkTeamReport>(request, response);
    		page.setOrderBy(" logtime asc ");
    		zinccoatingWorkTeamReport.setPage(page);
    		List<ZinccoatingWorkTeamReport> list = zinccoatingWorkTeamReportService.queryZinccoatingworkteamReport(zinccoatingWorkTeamReport);
    		page.setList(list);
            new ExportExcel("班组报表数据", ZinccoatingWorkTeamReport.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出报表数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/rpt/zinccoatingWorkTeamReport/list?repage";
    }
	
}
