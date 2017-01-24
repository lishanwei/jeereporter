/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.auth.entity.AuthRole;
import com.thinkgem.jeesite.modules.auth.entity.AuthSys;
import com.thinkgem.jeesite.modules.auth.entity.AuthUser;
import com.thinkgem.jeesite.modules.auth.service.AuthRoleService;
import com.thinkgem.jeesite.modules.auth.service.AuthSysService;
import com.thinkgem.jeesite.modules.auth.service.AuthUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统角色Controller
 * @author LYN
 * @version 2016-01-26
 */
@Controller
@RequestMapping(value = "${adminPath}/auth/authRole")
public class AuthRoleController extends BaseController {

	@Autowired
	private AuthRoleService authRoleService;
	
	@Autowired
	private AuthUserService authUserService;

	@Autowired
	private AuthSysService authSysService;

	@ModelAttribute
	public AuthRole get(@RequestParam(required=false) String id) {
		AuthRole entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = authRoleService.get(id);
		}
		if (entity == null){
			entity = new AuthRole();
		}
		return entity;
	}
	
	@RequiresPermissions("auth:authRole:view")
	@RequestMapping(value = {"list", ""})
	public String list(AuthRole authRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AuthRole> page = authRoleService.findPage(new Page<AuthRole>(request, response), authRole); 
		model.addAttribute("page", page);
		return "modules/auth/authRoleList";
	}

	@RequiresPermissions("auth:authRole:view")
	@RequestMapping(value = "form")
	public String form(AuthRole authRole, Model model) {
		model.addAttribute("authRole", authRole);
		return "modules/auth/authRoleForm";
	}

	@RequiresPermissions("auth:authRole:edit")
	@RequestMapping(value = "save")
	public String save(AuthRole authRole, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, authRole)){
			return form(authRole, model);
		}

		addMessage(redirectAttributes, authRoleService.saveAuthRole(authRole));
		return "redirect:"+Global.getAdminPath()+"/auth/authRole/?repage";
	}
	
	@RequiresPermissions("auth:authRole:edit")
	@RequestMapping(value = "delete")
	public String delete(AuthRole authRole, RedirectAttributes redirectAttributes) {
		authRoleService.delete(authRole);
		addMessage(redirectAttributes, "删除系统角色成功");
		return "redirect:"+Global.getAdminPath()+"/auth/authRole/?repage";
	}

	/**
	 * 角色分配页面
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("auth:authRole:edit")
	@RequestMapping(value = "assign")
	public String assign(AuthRole authRole, Model model) {
		String sysidString = authRole.getAuthsysId();
		AuthSys authSys = new AuthSys();
		if(StringUtils.isNotBlank(sysidString)){
			authSys = authSysService.get(sysidString);
		}
		List<AuthUser> selectedAuthUsers = authUserService.findListByAuthRole(authRole.getId());
		model.addAttribute("userList", selectedAuthUsers);
		model.addAttribute("authSys", authSys);
		return "modules/auth/authRoleAssign";
	}

	/**
	 * 角色分配 -- 打开角色分配对话框
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("auth:authRole:view")
	@RequestMapping(value = "usertorole")
	public String selectUserToRole(AuthRole authRole, Model model) {
		List<AuthUser> allAuthUsers = authUserService.findList(new AuthUser());
		List<AuthUser> selectedAuthUsers = authUserService.findListByAuthRole(authRole.getId());

		model.addAttribute("allAuthUsers", Collections3.subtract(allAuthUsers, selectedAuthUsers));
		model.addAttribute("authRole", authRole);
		model.addAttribute("userList", selectedAuthUsers);
		String selectedUsers = Collections3.extractToString(selectedAuthUsers, "id", ",");
		model.addAttribute("selectIds", selectedUsers);
		return "modules/auth/selectUserToRole";
	}

	/**
	 * 角色分配
	 * @return
	 */
	@RequiresPermissions("auth:authRole:edit")
	@RequestMapping(value = "assignrole")
	public String assignRole(AuthRole authRole, String[] ids, String[] preIds, RedirectAttributes redirectAttributes) {
		StringBuilder msg = new StringBuilder();
		int newNum = authRoleService.assignUserToRole(authRole, ids, preIds);
//		for (int i = 0; i < idsArr.length; i++) {
//			if (null != user) {
//				msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + authRole.getName() + "】！");
//				newNum++;
//			}
//		}
		addMessage(redirectAttributes, "已成功分配 "+newNum+" 个用户"+msg);
		return "redirect:" + adminPath + "/auth/authRole/assign?id="+authRole.getId();
	}


	@RequiresPermissions("auth:authRole:edit")
	@RequestMapping(value = "outrole")
	public String outrole(String userid, String roleid, RedirectAttributes redirectAttributes) {

		authRoleService.outrole(userid, roleid);
		addMessage(redirectAttributes, "用户已移除");
		return "redirect:" + adminPath + "/auth/authRole/assign?id="+roleid;
	}
}