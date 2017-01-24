package com.thinkgem.jeesite.modules.rpt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingWorkTeamReport;

/**
 * 锌层测厚质量分析controller.
 * 
 * @author aiqing.chu
 * @version 2016-07-13
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/zinccoatingQA")
public class ZinccoatingQAController extends BaseController {

	/**
	 * 
	 * @param zinccoatingWorkTeamReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("rpt:zinccoatingQA:view")
	@RequestMapping(value = {"showchart", ""})
	public String showchart(ZinccoatingWorkTeamReport zinccoatingWorkTeamReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("chartoptions", "{}");
		return "modules/rpt/zinccoatingQAChart";
	}
	
}
