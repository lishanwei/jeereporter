package com.thinkgem.jeesite.ws;

import com.thinkgem.jeesite.ws.model.Result;

import java.util.List;

/**
 * Created by jjh on 16/1/28.
 */
public interface UserInfoOperService {

    /**
     * 同步用户信息
     * @param username 用户名
     * @param password 明文密码
     * @param sysCode 系统编码
     * @param roleCode 角色编码
     * @param source 来源系统
     * @return
     */
    public Result syncUserInfo(String username, String phone, String password, String sysCode, String roleCode, String source);

    public Result syncUserInfo(String username, String password, String sysCode, List<String> roleCodeList, String source);

    /**
     * 获取用户的一些信息，如角色名
     * @param username 用户名
     * @param password 明文密码
     * @return
     */
    public List<String> queryUserRole(String username, String password);

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return
     */
    public Result checkUserExist(String username);

    /**
     * 检查手机号是否存在
     * @param phone
     * @return
     */
    public Result checkPhoneExist(String phone);
    
    /**
     * 重置密码
     * @param username
     * @return
     */
    public boolean resetPassToNew(String username, String password);

    /**
     * 逻辑删除用户,并把相应的角色关联去除
     * @param usernameList
     * @param roleCodeList
     * @return
     */
    public Result deleteUserBySys(List<String> usernameList, List<String> roleCodeList, String sysCode);

}
