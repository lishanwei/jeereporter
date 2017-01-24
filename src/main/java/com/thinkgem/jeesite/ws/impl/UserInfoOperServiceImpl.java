package com.thinkgem.jeesite.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.Exception.AuthRoleException;
import com.thinkgem.jeesite.common.Exception.AuthUserException;
import com.thinkgem.jeesite.common.utils.MD5Utils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.auth.entity.AuthUser;
import com.thinkgem.jeesite.modules.auth.service.AuthRoleService;
import com.thinkgem.jeesite.modules.auth.service.AuthSysService;
import com.thinkgem.jeesite.modules.auth.service.AuthUserService;
import com.thinkgem.jeesite.ws.UserInfoOperService;
import com.thinkgem.jeesite.ws.model.Result;

/**
 * Created by jjh on 16/1/28.
 */
@Service
public class UserInfoOperServiceImpl implements UserInfoOperService {

    private static Logger logger = LoggerFactory.getLogger(UserInfoOperServiceImpl.class);

    @Autowired
    private AuthSysService authSysService;

    @Autowired
    private AuthRoleService authRoleService;

    @Autowired
    private AuthUserService authUserService;

    @Override
    public Result syncUserInfo(String username, String phone, String password, String sysCode, String roleCode, String source) {

        List<String> roleCodeList = new ArrayList<String>();
        roleCodeList.add(roleCode);

        return this.syncUserInfo(username, password, sysCode, roleCodeList, source);

    }

    public Result syncUserInfo(String username, String password, String sysCode, List<String> roleCodeList, String source) {

        logger.info("接收到数据【username:" + username + ",password:" + password
                + ",sysCode:" + sysCode + ",roleCode:" + roleCodeList.toString() + ",source:" + source + "】");

        Result result = new Result();

        if (StringUtils.isNumAndLetter(username)) {

            // 检查用户名是否已存在
            if (!authUserService.exist(username)) {

                // 查询系统编码和角色编码是否存在
                if (StringUtils.isNotEmpty(sysCode) && authSysService.existSysCode(sysCode)
                        && CollectionUtils.isNotEmpty(roleCodeList) && authRoleService.existRoleCode(roleCodeList)) {

                    // 都存在，把用户名和密码插入到数据库
                    // 先把密码进行加密
                    String passwordMd5Str = MD5Utils.md5Tool(password);

                    AuthUser authUser = new AuthUser();
                    authUser.preInsert();
                    authUser.setUsername(username);
                    authUser.setPassword(passwordMd5Str);
                    authUser.setSource(source);
                    authUser.setIsNewRecord(true);
                    try {
                        authUserService.saveUserAndRole(authUser, roleCodeList);
                        logger.info("username:" + username + ",password:" + password + "保存成功");
                        result.setCode(Result.SUCCESS);
                        result.setMsg("保存成功");
                    } catch (Exception e) {
                        logger.error("username:" + username + ",password:" + password + "插入数据库失败", e);
                        result.setCode(Result.DB_ERROR);
                        result.setMsg("插入数据库失败");
                    }

                } else {
                    logger.error("sysode:" + sysCode + "或者roleCode" + roleCodeList.toString() + "不存在，请检查");
                    result.setCode(Result.CODE_NOT_EXIST);
                    result.setMsg("sysode:" + sysCode + "或者roleCode" + roleCodeList.toString() + "不存在，请检查");
                }
            } else {
                logger.error("username:" + username + "已存在，不能再次插入");

                result.setCode(Result.USER_EXIST);
                result.setMsg("用户名已存在");
            }
        } else {
            logger.error("username:" + username + "包含了特殊字符,请重新输入");
            result.setCode(Result.USERNAME_INVALID);
            result.setMsg("用户名包含了特殊字符,请重新输入");
        }

        return result;
    }

    @Override
    public List<String> queryUserRole(String username, String password) {

        return queryUserRole(username, password, null);

    }

    public List<String> queryUserRole(String username, String password, String sysCode) {

        List<String> resultList = new ArrayList<String>();

        logger.info("验证用户【" + username + "】是否存在，并获取角色名");

        // 对密码进行加密操作
        String passwordMd5Str = MD5Utils.md5Tool(password);

        try {
            // 检查用户名和密码是否存在
            if (authUserService.exist(username)) {
                logger.info("用户【" + username + "】存在，开始获取角色");

                resultList = authRoleService.findRoleCodeList(username, passwordMd5Str, sysCode);
            } else {
                logger.info("用户【" + username + "】不存在，请先注册或检查信息");
            }
        } catch (Exception e) {
            logger.error("获取用户【" + username + "】角色信息时，查询数据库出现异常", e);
        }

        return resultList;
    }

    @Override
    public Result checkUserExist(String username) {

        logger.info("开始检查用户" + username + "是否存在");

        Result result = new Result();

        if (StringUtils.isEmpty(username)) {
            logger.error("用户名为空，无法校验");
            result.setCode(Result.ERROR);
            result.setMsg("用户名为空，无法校验");
        } else {

            try {
                // 检查用户名是否已存在
                if (authUserService.exist(username)) {
                    logger.info("用户" + username + "已经存在");
                    result.setCode(Result.USER_EXIST);
                    result.setMsg("用户" + username + "已经存在");
                } else {
                    logger.info("用户" + username + "不存在");
                    result.setCode(Result.USER_NOT_EXIST);
                    result.setMsg("用户" + username + "不存在");
                }
            } catch (Exception e) {
                logger.error("检查用户" + username + "是否存在时出现异常", e);
                result.setCode(Result.DB_ERROR);
                result.setMsg("检查用户" + username + "是否存在时出现异常，稍后重试");
            }
        }

        return result;
    }

    @Override
    public Result checkPhoneExist(String phone) {
        logger.info("开始检查手机号" + phone + "是否存在");

        Result result = new Result();

        if (StringUtils.isEmpty(phone)) {
            logger.error("手机号为空，无法校验");
            result.setCode(Result.ERROR);
            result.setMsg("手机号为空，无法校验");
        } else {

            try {
                // 检查用户名是否已存在
                if (authUserService.existPhone(phone)) {
                    logger.info("手机号" + phone + "已经存在");
                    result.setCode(Result.PHONE_EXIST);
                    result.setMsg("手机号" + phone + "已经存在");
                } else {
                    logger.info("手机号" + phone + "不存在");
                    result.setCode(Result.PHONE_NOT_EXIST);
                    result.setMsg("手机号" + phone + "不存在");
                }
            } catch (Exception e) {
                logger.error("检查手机号" + phone + "是否存在时出现异常", e);
                result.setCode(Result.DB_ERROR);
                result.setMsg("检查手机号" + phone + "是否存在时出现异常，稍后重试");
            }
        }

        return result;
    }

    @Override
	public boolean resetPassToNew(String username, String password) {

        logger.info("更新用户[" + username + "]的密码[" + password + "]");

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return false;
        }

		AuthUser authUser = new AuthUser();
		authUser.setUsername(username);
        authUser.setPassword(password);
		return authUserService.updateUserPass(authUser);
	}

    /**
     * 删除用户,以及关联的角色
     * @param usernameList
     * @param roleCodeList
     * @return
     */
    public Result deleteUserBySys(List<String> usernameList, List<String> roleCodeList, String sysCode) {

        logger.info("接收删除用户[" + usernameList.toString() + "]的请求,以及删除系统[" + sysCode + "]" +
                "角色[" + roleCodeList.toString() + "]的关联");
        Result result = new Result();

        // 检查传递的参数是否合法
        if (CollectionUtils.isEmpty(usernameList) || CollectionUtils.isEmpty(roleCodeList) || StringUtils.isEmpty(sysCode)) {
            logger.error("删除的用户信息为空,或者角色码为空,或者系统编码为空,请检查");
            result.setCode(Result.DATA_ERROR);
            result.setMsg("删除的用户信息为空,或者角色码为空,或者系统编码为空,请检查");
        } else {

            try {

                // 删除用户以及所对应的角色关联,若用户还有其他角色关联,不允许删除
                authUserService.deleteUserListAndRole(usernameList, roleCodeList, sysCode);

                result.setCode(Result.SUCCESS);
                result.setMsg("删除完成");

            } catch (AuthRoleException authRoleE) {
                result.setCode(Result.SUCCESS);
                result.setMsg(authRoleE.getMessage());
            } catch (AuthUserException authUserE) {
                result.setCode(Result.SUCCESS);
                result.setMsg(authUserE.getMessage());
            } catch (Exception e) {
                logger.error("删除用户以及所对应的角色关联时出现异常", e);
                result.setCode(Result.ERROR);
                result.setMsg("删除用户以及所对应的角色关联时出现异常");
            }
        }

        return result;
    }
}
