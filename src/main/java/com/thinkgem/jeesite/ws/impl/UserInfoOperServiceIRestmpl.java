package com.thinkgem.jeesite.ws.impl;

import com.thinkgem.jeesite.common.Exception.AuthRoleException;
import com.thinkgem.jeesite.common.Exception.AuthUserException;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.MD5Utils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.auth.entity.AuthUser;
import com.thinkgem.jeesite.modules.auth.service.AuthRoleService;
import com.thinkgem.jeesite.modules.auth.service.AuthSysService;
import com.thinkgem.jeesite.modules.auth.service.AuthUserService;
import com.thinkgem.jeesite.ws.UserInfoOperService;
import com.thinkgem.jeesite.ws.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jjh on 16/1/28.
 */
@Path("/")
public class UserInfoOperServiceIRestmpl {

    private static Logger logger = LoggerFactory.getLogger(UserInfoOperServiceIRestmpl.class);

    @Autowired
    private UserInfoOperService userInfoOperService;

    @Autowired
    private AuthUserService authUserService;

    @POST
    @Path("/syncUserInfo")
    public String syncUserInfo(String json) {

        logger.info("接收到数据为" + json);

        JsonMapper jsonMapper = new JsonMapper();

        Map<String, String> jsonMap = jsonMapper.fromJson(json, Map.class);

        Result result = userInfoOperService.syncUserInfo(jsonMap.get("username"),
                jsonMap.get("phone"), jsonMap.get("password"),
                jsonMap.get("sysCode"), jsonMap.get("roleCode"), jsonMap.get("source"));

        return jsonMapper.toJson(result);
    }

    @POST
    @Path("/updateUserPass")
    public String updateUserPass(String json) {

        logger.info("接收到的更新用户信息为" + json);

        Result result = new Result();

        JsonMapper jsonMapper = new JsonMapper();
        AuthUser authUser = new AuthUser();

        // 解析数据
        try {
            Map<String, String> jsonMap = jsonMapper.fromJson(json, Map.class);

            authUser.setUsername(jsonMap.get("username"));
            authUser.setPassword(jsonMap.get("newPass"));

            // 若是有旧密码，需要根据旧密码验证用户
            if (StringUtils.isEmpty(jsonMap.get("oldPass")) && authUserService.updateUserPass(authUser)) {

                logger.info("用户" + authUser.getUsername() + "更新密码成功");
                result.setCode(Result.SUCCESS);
                result.setMsg("用户" + authUser.getUsername() + "更新密码成功");

            } else if (!StringUtils.isEmpty(jsonMap.get("oldPass"))
                    && authUserService.updateUserPass(authUser.getUsername(), jsonMap.get("oldPass"), authUser.getPassword())) {
                logger.info("用户" + authUser.getUsername() + "更新密码成功");
                result.setCode(Result.SUCCESS);
                result.setMsg("用户" + authUser.getUsername() + "更新密码成功");
            } else {
                logger.error("用户" + authUser.getUsername() + "更新密码失败，请检查用户名或密码");
                result.setCode(Result.DB_ERROR);
                result.setMsg("用户" + authUser.getUsername() + "更新密码失败，请检查用户名或密码");
            }

        } catch (Exception e) {
            logger.error("用户" + authUser.getUsername() + "更新密码出现异常", e);
            result.setCode(Result.ERROR);
            result.setMsg("用户" + authUser.getUsername() + "更新密码出现异常，请稍后重试");
        }

        return jsonMapper.toJson(result);
    }

    @POST
    @Path("/changeUserRole")
    public String changeUserRole(String json) {

        logger.info("接收到的用户变更角色数据为" + json);

        Result result = new Result();

        String username = "";
        String[] roleCodes = null;
        String sysCode = "";
        JsonMapper jsonMapper = new JsonMapper();

        try {

            Map<String, String> jsonMap = jsonMapper.fromJson(json, Map.class);

            username = jsonMap.get("username");
            sysCode = jsonMap.get("sysCode");

            if (StringUtils.isEmpty(jsonMap.get("roleCodes"))) {
                roleCodes = new String[]{};
            } else {
                roleCodes = jsonMap.get("roleCodes").split(",");
            }

            authUserService.changeUserRole(username, roleCodes, sysCode);

            logger.info("用户" + username + "变更角色成功");
            result.setCode(Result.SUCCESS);
            result.setMsg("用户" + username + "变更角色成功");

        } catch (AuthUserException authUserE) {
            result.setCode(Result.USER_NOT_EXIST);
            result.setMsg(authUserE.getMessage());
        } catch (AuthRoleException authRoleE) {
            result.setCode(Result.ROLE_NOT_EXIST);
            result.setMsg(authRoleE.getMessage());
        } catch (Exception e) {
            logger.error("用户" + username + "变更角色出现异常", e);
            result.setCode(Result.ERROR);
            result.setMsg("用户" + username + "变更角色出现异常，稍后重试");
        }


        return jsonMapper.toJson(result);
    }

}
