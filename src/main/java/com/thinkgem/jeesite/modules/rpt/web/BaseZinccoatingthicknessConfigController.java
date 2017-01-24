/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.web;

import java.util.Date;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.rpt.entity.BaseZinccoatingthicknessConfig;
import com.thinkgem.jeesite.modules.rpt.service.BaseZinccoatingthicknessConfigService;

/**
 * 配置上锌量偏差量Controller
 * @author aiqing.chu
 * @version 2016-09-01
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/baseZinccoatingthicknessConfig")
public class BaseZinccoatingthicknessConfigController extends BaseController {

	@Autowired
	private BaseZinccoatingthicknessConfigService baseZinccoatingthicknessConfigService;
	
	@ModelAttribute
	public BaseZinccoatingthicknessConfig get(@RequestParam(required=false) String id) {
		BaseZinccoatingthicknessConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseZinccoatingthicknessConfigService.get(id);
		}
		if (entity == null){
			entity = new BaseZinccoatingthicknessConfig();
		}
		return entity;
	}
	
	@RequiresPermissions("rpt:baseZinccoatingthicknessConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseZinccoatingthicknessConfig baseZinccoatingthicknessConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseZinccoatingthicknessConfig> page = baseZinccoatingthicknessConfigService.findPage(new Page<BaseZinccoatingthicknessConfig>(request, response), baseZinccoatingthicknessConfig); 
		model.addAttribute("page", page);
		return "modules/rpt/baseZinccoatingthicknessConfigList";
	}

	@RequiresPermissions("rpt:baseZinccoatingthicknessConfig:view")
	@RequestMapping(value = "form")
	public String form(BaseZinccoatingthicknessConfig baseZinccoatingthicknessConfig, Model model) {
		model.addAttribute("baseZinccoatingthicknessConfig", baseZinccoatingthicknessConfig);
		return "modules/rpt/baseZinccoatingthicknessConfigForm";
	}

	@RequiresPermissions("rpt:baseZinccoatingthicknessConfig:edit")
	@RequestMapping(value = "save")
	public String save(BaseZinccoatingthicknessConfig baseZinccoatingthicknessConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseZinccoatingthicknessConfig)){
			return form(baseZinccoatingthicknessConfig, model);
		}
		Date date = new Date();
		String userid = SecurityUtils.getSubject().getPrincipal().toString();
		if (baseZinccoatingthicknessConfig.getIsNewRecord()) {
			baseZinccoatingthicknessConfig.setCreatetime(date);
			baseZinccoatingthicknessConfig.setCreateuser(userid);
		}
        baseZinccoatingthicknessConfig.setUpdatetime(date);
        baseZinccoatingthicknessConfig.setUpdateuser(userid);
		baseZinccoatingthicknessConfigService.save(baseZinccoatingthicknessConfig);
		addMessage(redirectAttributes, "保存上锌量配置成功");
		return "redirect:"+Global.getAdminPath()+"/rpt/baseZinccoatingthicknessConfig/?repage";
	}
	
	@RequiresPermissions("rpt:baseZinccoatingthicknessConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseZinccoatingthicknessConfig baseZinccoatingthicknessConfig, RedirectAttributes redirectAttributes) {
		baseZinccoatingthicknessConfigService.delete(baseZinccoatingthicknessConfig);
		addMessage(redirectAttributes, "删除上锌量配置成功");
		return "redirect:"+Global.getAdminPath()+"/rpt/baseZinccoatingthicknessConfig/?repage";
	}

}