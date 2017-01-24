/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.auth.entity.AuthSys;
import com.thinkgem.jeesite.modules.auth.service.AuthSysService;

import java.util.List;
import java.util.Map;

/**
 * 系统信息Controller
 * @author jiajianhong
 * @version 2016-01-26
 */
@Controller
@RequestMapping(value = "${adminPath}/auth/authSys")
public class AuthSysController extends BaseController {

	@Autowired
	private AuthSysService authSysService;
	
	@ModelAttribute
	public AuthSys get(@RequestParam(required=false) String id) {
		AuthSys entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = authSysService.get(id);
		}
		if (entity == null){
			entity = new AuthSys();
		}
		return entity;
	}
	
	@RequiresPermissions("auth:authSys:view")
	@RequestMapping(value = {"list", ""})
	public String list(AuthSys authSys, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AuthSys> page = authSysService.findPage(new Page<AuthSys>(request, response), authSys); 
		model.addAttribute("page", page);
		return "modules/auth/authSysList";
	}

	@RequiresPermissions("auth:authSys:view")
	@RequestMapping(value = "form")
	public String form(AuthSys authSys, Model model) {
		model.addAttribute("authSys", authSys);
		return "modules/auth/authSysForm";
	}

	@RequiresPermissions("auth:authSys:edit")
	@RequestMapping(value = "save")
	public String save(AuthSys authSys, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, authSys)){
			return form(authSys, model);
		}
		addMessage(redirectAttributes, authSysService.saveAuthSys(authSys));
		return "redirect:"+Global.getAdminPath()+"/auth/authSys/?repage";
	}
	
	@RequiresPermissions("auth:authSys:edit")
	@RequestMapping(value = "delete")
	public String delete(AuthSys authSys, RedirectAttributes redirectAttributes) {
		authSysService.delete(authSys);
		addMessage(redirectAttributes, "删除系统成功");
		return "redirect:"+Global.getAdminPath()+"/auth/authSys/?repage";
	}

    /**
     * 获取机构JSON数据。
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<AuthSys> list = authSysService.findList(new AuthSys());

        for (int i=0; i<list.size(); i++){
            AuthSys e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("name", e.getName());
            mapList.add(map);
        }
        return mapList;
    }

}