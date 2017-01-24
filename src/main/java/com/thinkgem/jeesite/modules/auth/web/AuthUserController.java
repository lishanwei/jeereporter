/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.auth.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.Exception.AuthUserException;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.MD5Utils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.auth.entity.AuthRole;
import com.thinkgem.jeesite.modules.auth.service.AuthRoleService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.auth.entity.AuthUser;
import com.thinkgem.jeesite.modules.auth.service.AuthUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户Controller
 * @author LYN
 * @version 2016-01-26
 */
@Controller
@RequestMapping(value = "${adminPath}/auth/authUser")
public class AuthUserController extends BaseController {

	@Autowired
	private AuthUserService authUserService;

    @Autowired
    private AuthRoleService authRoleService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private OfficeService officeService;

	@ModelAttribute
	public AuthUser get(@RequestParam(required=false) String id) {
		AuthUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = authUserService.get(id);
		}
		if (entity == null){
			entity = new AuthUser();
		}
		return entity;
	}
	
	@RequiresPermissions("auth:authUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(AuthUser authUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AuthUser> page = authUserService.findPage(new Page<AuthUser>(request, response), authUser); 
		model.addAttribute("page", page);
		return "modules/auth/authUserList";
	}

	@RequiresPermissions("auth:authUser:view")
	@RequestMapping(value = "form")
	public String form(AuthUser authUser, Model model) {

        // 若是有用户信息时,把角色信息补全
        if (StringUtils.isNotBlank(authUser.getUsername())) {
            authUser.setAuthRoleList(authUserService.findUserRoleList(authUser));
        }

		model.addAttribute("authUser", authUser);

        // 查询角色信息,方便添加用户时直接选择角色
        model.addAttribute("allAuthRoles", authRoleService.findList(new AuthRole()));

		return "modules/auth/authUserForm";
	}

    @RequiresPermissions("auth:authUser:edit")
    @RequestMapping(value = "resetPass")
    public String resetPass(AuthUser authUser, HttpServletRequest request, HttpServletResponse response, Model model) {

        if (authUserService.resetPass(authUser)) {
            model.addAttribute("message", "用户" + authUser.getUsername() + "重置密码成功");
        } else {
            model.addAttribute("message", "用户" + authUser.getUsername() + "重置密码失败，稍后重试");
        }

        Page<AuthUser> page = authUserService.findPage(new Page<AuthUser>(request, response), authUser);
        model.addAttribute("page", page);

        return "modules/auth/authUserList";
    }

	@RequiresPermissions("auth:authUser:edit")
	@RequestMapping(value = "save")
	public String save(AuthUser authUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, authUser)){
			return form(authUser, model);
        }

        // 对密码进行md5加密
        authUser.setPassword(MD5Utils.md5Tool(authUser.getPassword()));

        try {
            authUserService.save(authUser);
            logger.info("保存用户" + authUser.getUsername() + "成功");
            addMessage(redirectAttributes, "保存系统用户成功");
        } catch (DuplicateKeyException duplicateKeyE) {
            logger.error("保存用户时出现重复数据，请检查", duplicateKeyE);
            addMessage(redirectAttributes, "用户名重复，请检查");
        } catch (Exception e) {
            logger.error("保存用户出现异常", e);
            addMessage(redirectAttributes, "保存用户出现异常，请稍后重试");
        }

		return "redirect:"+Global.getAdminPath()+"/auth/authUser/?repage";
	}
	
	@RequiresPermissions("auth:authUser:edit")
	@RequestMapping(value = "delete")
	public String delete(AuthUser authUser, RedirectAttributes redirectAttributes) {
        authUserService.deleteUserAndRole(authUser);
		addMessage(redirectAttributes, "删除系统用户成功");
		return "redirect:"+Global.getAdminPath()+"/auth/authUser/?repage";
	}

    @RequiresPermissions("auth:authUser:edit")
    @RequestMapping(value = "resetUser")
    public String resetUser(AuthUser authUser, RedirectAttributes redirectAttributes) {
        authUserService.resetUser(authUser);
        addMessage(redirectAttributes, "重启系统用户成功");
        return "redirect:"+Global.getAdminPath()+"/auth/authUser/?repage";
    }

    /**
     * 导入用户数据
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("auth:authUser:edit")
    @RequestMapping(value = "import", method= RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

        logger.info("开始执行用户导入");

        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<AuthUser> list = ei.getDataList(AuthUser.class);
            logger.info("执行" + list.size() + "条用户的导入");
            // 循环插入用户对象
            for (AuthUser authUser : list){
                try{
                    // 检查用户名是否存在
                    if (!authUserService.exist(authUser.getUsername())){

                        // 设置默认密码123456,并加密
                        authUser.setPassword(MD5Utils.md5Tool(AuthUser.originPass));
                        BeanValidators.validateWithException(validator, authUser);
                        authUser.preInsert();
                        authUser.setIsNewRecord(true);

                        List<String> roleCodeList = new ArrayList<String>();
                        roleCodeList.add(authUser.getRoleCode());

                        authUserService.saveUserAndRole(authUser, roleCodeList);
                        successNum++;
                    }else{
                        failureMsg.append("<br/>登录名 " + authUser.getUsername() + " 已存在; ");
                        logger.error("登录名 " + authUser.getUsername() + " 已存在");
                        failureNum++;
                    }
                }catch(ConstraintViolationException ex){
                    failureMsg.append("<br/>登录名 " + authUser.getUsername() + " 导入失败：");
                    logger.error("登录名 " + authUser.getUsername() + " 导入失败:", ex);
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList){
                        failureMsg.append(message+"; ");
                        failureNum++;
                    }
                }catch (Exception ex) {
                    failureMsg.append("<br/>登录名 " + authUser.getUsername() + " 导入失败;");
                    logger.error("登录名 " + authUser.getUsername() + " 导入失败", ex);
                }
            }
            if (failureNum>0){
                failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
            logger.info("成功导入" + successNum + "条用户,导入失败" + failureNum + "条用户");
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
            logger.error("导入用户失败!", e);
        }
        return "redirect:" + adminPath + "/auth/authUser/?repage";
    }

    /**
     * 下载导入用户数据模板
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("auth:authUser:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户数据导入模板.xlsx";
//            List<AuthUser> list = Lists.newArrayList();
//            AuthUser authUser = new AuthUser();
//            authUser.setUsername("admin");
//            authUser.setPassword("123456");
//            authUser.setSource("office");
//            authUser.setRoleCode("ROLE_OFFICE");
//            list.add(authUser);
//            new ExportExcel("用户数据", AuthUser.class, 2).setDataList(list).write(response, fileName).dispose();
            new ExportExcel("templates.excel/template1.xlsx").write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
            logger.error("导入模板下载失败", e);
        }
        return "redirect:" + adminPath + "/auth/authUser/?repage";
    }

    /**
     * 修改个人用户密码
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @RequiresPermissions("auth:authUser:modifyPwd")
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(String oldPassword, String newPassword, Model model) {
        User user = UserUtils.getUser();
        logger.info("用户[" + user.getLoginName() + "]开始修改密码");
        AuthUser authUser = new AuthUser();

        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){

            // 对密码进行加密
            oldPassword = MD5Utils.md5Tool(oldPassword);

            // 校验旧密码是否正确
            authUser = authUserService.getAuthUser(user.getLoginName(), oldPassword);
            if (null != authUser){
                authUser.setPassword(newPassword);
                authUserService.updateUserPass(authUser);
                logger.info("用户[" + user.getLoginName() + "]修改密码成功");
                model.addAttribute("message", "修改密码成功");
            }else{
                authUser = new AuthUser();
                logger.error("用户[" + user.getLoginName() + "]修改密码失败，旧密码错误");
                model.addAttribute("message", "修改密码失败，旧密码错误");
            }
        }

        // 返回页面时,对象不能为null,必须有值
        authUser.setUsername(user.getLoginName());
        model.addAttribute("authUser", authUser);
        return "modules/auth/userModifyPwd";
    }

    /**
     * 同步账号到office系统中
     * @param authUser
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("auth:authUser:edit")
    @RequestMapping(value = "syncUser")
    public String syncUser(AuthUser authUser, RedirectAttributes redirectAttributes) {

        logger.info("开始同步用户[" + authUser.getUsername() + "]信息给office系统");

        // 检查用户名是否已经存在
        if (null != systemService.getUserByLoginName(authUser.getUsername())) {
            logger.info("用户[" + authUser.getUsername() + "]已同步过");
            addMessage(redirectAttributes, "用户[" + authUser.getUsername() + "]已同步过");
        } else {

            User user = new User();

            user.setLoginName(authUser.getUsername());
            user.setName(authUser.getUsername());

            // 得到所有的机构信息,给用户随便赋值一个
            List<Office> officeList = officeService.findList(true);
            user.setCompany(new Office(officeList.get(0).getId()));
            user.setOffice(new Office(officeList.get(0).getId()));

            user.setPassword(authUser.getPassword());

            // 添加普通角色
            List<Role> roleList = Lists.newArrayList();
            for (Role r : systemService.findAllRole()){
                if (r.getName().equalsIgnoreCase("普通用户")){
                    roleList.add(r);
                    break;
                }
            }
            user.setRoleList(roleList);

            // 保存用户信息
            systemService.saveUser(user);

            logger.info("同步用户'" + user.getLoginName() + "'成功");
            addMessage(redirectAttributes, "同步用户'" + user.getLoginName() + "'成功");
        }

        return "redirect:" + adminPath + "/auth/authUser/?repage";
    }

}