/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rpt.entity.BaseZinccoatingthicknessException;
import com.thinkgem.jeesite.modules.rpt.service.BaseZinccoatingthicknessExceptionService;

/**
 * 异常历史Controller
 * @author aiqing.chu
 * @version 2016-09-01
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/baseZinccoatingthicknessException")
public class BaseZinccoatingthicknessExceptionController extends BaseController {

	@Autowired
	private BaseZinccoatingthicknessExceptionService baseZinccoatingthicknessExceptionService;
	
	@ModelAttribute
	public BaseZinccoatingthicknessException get(@RequestParam(required=false) String id) {
		BaseZinccoatingthicknessException entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseZinccoatingthicknessExceptionService.get(id);
		}
		if (entity == null){
			entity = new BaseZinccoatingthicknessException();
		}
		return entity;
	}
	
	@RequiresPermissions("rpt:baseZinccoatingthicknessException:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseZinccoatingthicknessException baseZinccoatingthicknessException, HttpServletRequest request, HttpServletResponse response, Model model) {
		long startTime = System.currentTimeMillis();
		Page<BaseZinccoatingthicknessException> page = baseZinccoatingthicknessExceptionService.findPage(new Page<BaseZinccoatingthicknessException>(request, response), baseZinccoatingthicknessException); 
		model.addAttribute("page", page);
		model.addAttribute("principal", SecurityUtils.getSubject().getPrincipal().toString());
		long endTime = System.currentTimeMillis();
		long hs = endTime - startTime;
		model.addAttribute("taketimes", "本次数据加载耗时" + hs+ "毫秒.");
		return "modules/rpt/baseZinccoatingthicknessExceptionList";
	}

	@RequiresPermissions("rpt:baseZinccoatingthicknessException:view")
	@RequestMapping(value = "form")
	public String form(BaseZinccoatingthicknessException baseZinccoatingthicknessException, Model model) {
		model.addAttribute("baseZinccoatingthicknessException", baseZinccoatingthicknessException);
		return "modules/rpt/baseZinccoatingthicknessExceptionForm";
	}

	@RequiresPermissions("rpt:baseZinccoatingthicknessException:edit")
	@RequestMapping(value = "save")
	public String save(BaseZinccoatingthicknessException baseZinccoatingthicknessException, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseZinccoatingthicknessException)){
			return form(baseZinccoatingthicknessException, model);
		}
		baseZinccoatingthicknessExceptionService.save(baseZinccoatingthicknessException);
		addMessage(redirectAttributes, "保存异常历史数据成功");
		return "redirect:"+Global.getAdminPath()+"/rpt/baseZinccoatingthicknessException/?repage";
	}
	
	@RequiresPermissions("rpt:baseZinccoatingthicknessException:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseZinccoatingthicknessException baseZinccoatingthicknessException, RedirectAttributes redirectAttributes) {
		baseZinccoatingthicknessExceptionService.delete(baseZinccoatingthicknessException);
		addMessage(redirectAttributes, "删除异常历史数据成功");
		return "redirect:"+Global.getAdminPath()+"/rpt/baseZinccoatingthicknessException/?repage";
	}

}