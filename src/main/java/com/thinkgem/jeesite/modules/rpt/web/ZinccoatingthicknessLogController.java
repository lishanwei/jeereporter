/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.web;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingthicknessLog;
import com.thinkgem.jeesite.modules.rpt.service.ZinccoatingthicknessLogService;

/**
 * 锌层测厚日志Controller
 * @author aiqing.chu
 * @version 2016-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/zinccoatingthicknessLog")
public class ZinccoatingthicknessLogController extends BaseController {

	@Autowired
	private ZinccoatingthicknessLogService zinccoatingthicknessLogService;
	
	@ModelAttribute
	public ZinccoatingthicknessLog get(@RequestParam(required=false) String id) {
		ZinccoatingthicknessLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = zinccoatingthicknessLogService.get(id);
		}
		if (entity == null){
			entity = new ZinccoatingthicknessLog();
		}
		return entity;
	}
	
	/**
	 * 
	 * @param zinccoatingthicknessLog
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("rpt:zinccoatingthicknessLog:view")
	@RequestMapping(value = {"queryList", ""})
	public String queryList(ZinccoatingthicknessLog zinccoatingthicknessLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ZinccoatingthicknessLog> page = new Page<ZinccoatingthicknessLog>();
		page.setOrderBy(" a.logtime asc limit 100");
		zinccoatingthicknessLog.setPage(page);
		List<ZinccoatingthicknessLog> list = zinccoatingthicknessLogService.queryList(zinccoatingthicknessLog);
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/rpt/zinccoatingthicknessLogQueryList";
	}
	
	@RequiresPermissions("rpt:zinccoatingthicknessLogExceptionList:view")
	@RequestMapping(value = {"exceptionList", ""})
	public String exceptionList(ZinccoatingthicknessLog zinccoatingthicknessLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		long startTime = System.currentTimeMillis();
		
		// 默认查询当天的实时数据
		Date date = new Date();
		if (zinccoatingthicknessLog.getBeginLogtime() == null || "".equals(zinccoatingthicknessLog.getBeginLogtime())) {
			String beginLogtime = com.thinkgem.jeesite.common.utils.DateUtils.formatDate(date, "yyyy-MM-dd 00:00:00");
			zinccoatingthicknessLog.setBeginLogtime(beginLogtime);
		}
		if (zinccoatingthicknessLog.getEndLogtime() == null || "".equals(zinccoatingthicknessLog.getEndLogtime())) {
			String endLogtime = com.thinkgem.jeesite.common.utils.DateUtils.formatDate(com.thinkgem.jeesite.common.utils.DateUtil.addDateOneDay(date, 1), "yyyy-MM-dd 00:00:00");
			zinccoatingthicknessLog.setEndLogtime(endLogtime);
		}
		
		if (zinccoatingthicknessLog.getMaxOffsetFront() == null) {
			zinccoatingthicknessLog.setMaxOffsetFront(10d);
		}
		if (zinccoatingthicknessLog.getMinOffsetFront() == null) {
			zinccoatingthicknessLog.setMinOffsetFront(-10d);
		}
		if (zinccoatingthicknessLog.getMaxOffsetReverse() == null) {
			zinccoatingthicknessLog.setMaxOffsetReverse(10d);
		}
		if (zinccoatingthicknessLog.getMinOffsetReverse() == null) {
			zinccoatingthicknessLog.setMinOffsetReverse(-10d);
		}
		Page<ZinccoatingthicknessLog> pagination = new Page<ZinccoatingthicknessLog>(request, response);
		pagination.setOrderBy(" a.logtime asc ");
		Page<ZinccoatingthicknessLog> page = zinccoatingthicknessLogService.findPage(pagination, zinccoatingthicknessLog); 
		model.addAttribute("page", page);
		model.addAttribute("principal", SecurityUtils.getSubject().getPrincipal().toString());
		long endTime = System.currentTimeMillis();
		long hs = endTime - startTime;
		model.addAttribute("taketimes", "本次数据加载耗时" + hs+ "毫秒.");
		return "modules/rpt/zinccoatingthicknessLogExceptionList";
	}

	@RequiresPermissions("rpt:zinccoatingthicknessLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZinccoatingthicknessLog zinccoatingthicknessLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		long startTime = System.currentTimeMillis();
//		// 默认查询当天的实时数据
//		Date date = new Date();
//		if (zinccoatingthicknessLog.getBeginLogtime() == null || "".equals(zinccoatingthicknessLog.getBeginLogtime())) {
//			String beginLogtime = com.thinkgem.jeesite.common.utils.DateUtils.formatDate(date, "yyyy-MM-dd 00:00:00");
//			zinccoatingthicknessLog.setBeginLogtime(beginLogtime);
//		}
//		if (zinccoatingthicknessLog.getEndLogtime() == null || "".equals(zinccoatingthicknessLog.getEndLogtime())) {
//			String endLogtime = com.thinkgem.jeesite.common.utils.DateUtils.formatDate(com.thinkgem.jeesite.common.utils.DateUtil.addDateOneDay(date, 1), "yyyy-MM-dd 00:00:00");
//			zinccoatingthicknessLog.setEndLogtime(endLogtime);
//		}
		
		Page<ZinccoatingthicknessLog> pagination = new Page<ZinccoatingthicknessLog>(request, response);
		pagination.setOrderBy(" a.logtime asc ");
		Page<ZinccoatingthicknessLog> page = zinccoatingthicknessLogService.findPage(pagination, zinccoatingthicknessLog); 
		model.addAttribute("page", page);
		model.addAttribute("principal", SecurityUtils.getSubject().getPrincipal().toString());
		long endTime = System.currentTimeMillis();
		long hs = endTime - startTime;
		model.addAttribute("taketimes", "本次数据加载耗时" + hs+ "毫秒.");
		return "modules/rpt/zinccoatingthicknessLogList";
	}

	@RequiresPermissions("rpt:zinccoatingthicknessLog:view")
	@RequestMapping(value = "form")
	public String form(ZinccoatingthicknessLog zinccoatingthicknessLog, Model model) {
		model.addAttribute("zinccoatingthicknessLog", zinccoatingthicknessLog);
		return "modules/rpt/zinccoatingthicknessLogForm";
	}

	@RequiresPermissions("rpt:zinccoatingthicknessLog:edit")
	@RequestMapping(value = "save")
	public String save(ZinccoatingthicknessLog zinccoatingthicknessLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, zinccoatingthicknessLog)){
			return form(zinccoatingthicknessLog, model);
		}
		zinccoatingthicknessLogService.save(zinccoatingthicknessLog);
		addMessage(redirectAttributes, "保存锌层测试日志成功");
		return "redirect:"+Global.getAdminPath()+"/rpt/zinccoatingthicknessLog/?repage";
	}
	
	@RequiresPermissions("rpt:zinccoatingthicknessLog:edit")
	@RequestMapping(value = "delete")
	public String delete(ZinccoatingthicknessLog zinccoatingthicknessLog, RedirectAttributes redirectAttributes) {
		zinccoatingthicknessLogService.delete(zinccoatingthicknessLog);
		addMessage(redirectAttributes, "删除锌层测试日志成功");
		return "redirect:"+Global.getAdminPath()+"/rpt/zinccoatingthicknessLog/?repage";
	}

}