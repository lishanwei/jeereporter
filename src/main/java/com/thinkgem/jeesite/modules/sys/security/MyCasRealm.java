//package com.thinkgem.jeesite.modules.sys.security;
//
//import com.thinkgem.jeesite.common.config.Global;
//import com.thinkgem.jeesite.common.db.DataSourceContextHolder;
//import com.thinkgem.jeesite.common.utils.SpringContextHolder;
//import com.thinkgem.jeesite.common.web.Servlets;
//import com.thinkgem.jeesite.modules.sys.entity.Menu;
//import com.thinkgem.jeesite.modules.sys.entity.Role;
//import com.thinkgem.jeesite.modules.sys.entity.User;
//import com.thinkgem.jeesite.modules.sys.service.SystemService;
//import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
//import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.cas.CasAuthenticationException;
//import org.apache.shiro.cas.CasRealm;
//import org.apache.shiro.cas.CasToken;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.jasig.cas.client.authentication.AttributePrincipal;
//import org.jasig.cas.client.validation.Assertion;
//import org.jasig.cas.client.validation.Saml11TicketValidator;
//import org.jasig.cas.client.validation.TicketValidationException;
//import org.jasig.cas.client.validation.TicketValidator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by jjh on 16/3/2.
// */
//@Service
//public class MyCasRealm extends CasRealm {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    private SystemService systemService;
//
//    /**
//     * 认证回调函数, 登录时调用
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
//        CasToken casToken = (CasToken) token;
//        if (token == null) {
//            return null;
//        }
//
//        String ticket = (String)casToken.getCredentials();
//        if (!org.apache.shiro.util.StringUtils.hasText(ticket)) {
//            return null;
//        }
//
//        TicketValidator ticketValidator = ensureTicketValidator();
//
//        try {
//            // contact CAS server to validate service ticket
//            Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
//            // get principal, user id and attributes
//            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
//            String userId = casPrincipal.getName();
//            logger.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{
//                    ticket, getCasServerUrlPrefix(), userId
//            });
//
//            Map<String, Object> attributes = casPrincipal.getAttributes();
//            // refresh authentication token (user id + remember me)
//            casToken.setUserId(userId);
//            String rememberMeAttributeName = getRememberMeAttributeName();
//            String rememberMeStringValue = (String)attributes.get(rememberMeAttributeName);
//            boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
//            if (isRemembered) {
//                casToken.setRememberMe(true);
//            }
//
//            // 使用自己的Principal
//            // 从数据库获取用户信息
//            User user = UserUtils.getByLoginName(userId);
//
//            SystemAuthorizingRealm.Principal principal = new SystemAuthorizingRealm.Principal(user, false);
//
////            // create simple authentication info
////            List<Object> principals = CollectionUtils.asList(userId, attributes);
////            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
//            return new SimpleAuthenticationInfo(principal, ticket, getName());
//        } catch (TicketValidationException e) {
//            logger.error("Unable to validate ticket [" + ticket + "]", e);
//            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
//        }
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//
//        SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) getAvailablePrincipal(principals);
//
//        // 获取当前已登录的用户
//        if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
//            Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());
//            if (sessions.size() > 0){
//                // 如果是登录进来的，则踢出已在线用户
//                if (UserUtils.getSubject().isAuthenticated()){
//                    for (Session session : sessions){
//                        getSystemService().getSessionDao().delete(session);
//                    }
//                }
//                // 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
//                else{
//                    UserUtils.getSubject().logout();
//                    throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
//                }
//            }
//        }
//
//        User user = getSystemService().getUserByLoginName(principal.getLoginName());
//
//        if (user != null) {
//            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//            List<Menu> list = UserUtils.getMenuList();
//            for (Menu menu : list){
//                if (StringUtils.isNotBlank(menu.getPermission())){
//                    // 添加基于Permission的权限信息
//                    for (String permission : StringUtils.split(menu.getPermission(),",")){
//                        info.addStringPermission(permission);
//                    }
//                }
//            }
//            // 添加用户权限
//            info.addStringPermission("user");
//            // 添加用户角色信息
//            for (Role role : user.getRoleList()){
//                info.addRole(role.getEnname());
//            }
//
//            // 这里强制切换一下数据库
//            String dbTarget = DataSourceContextHolder.getDbTarget();
//
//            DataSourceContextHolder.setDbTarget("office");
//
//            // 更新登录IP和时间
//            getSystemService().updateUserLoginInfo(user);
//            // 记录登录日志
//            LogUtils.saveLog(Servlets.getRequest(), "系统登录");
//
//            if (!"office".equalsIgnoreCase(dbTarget)) {
//                DataSourceContextHolder.setDbTarget(dbTarget);
//            }
//
//            return info;
//        } else {
//            return null;
//        }
//
//    }
////
////    @Override
////    protected void checkPermission(Permission permission, AuthorizationInfo info) {
////        authorizationValidate(permission);
////        super.checkPermission(permission, info);
////    }
////
////    @Override
////    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
////        if (permissions != null && !permissions.isEmpty()) {
////            for (Permission permission : permissions) {
////                authorizationValidate(permission);
////            }
////        }
////        return super.isPermitted(permissions, info);
////    }
////
////    @Override
////    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
////        authorizationValidate(permission);
////        return super.isPermitted(principals, permission);
////    }
////
////    @Override
////    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
////        if (permissions != null && !permissions.isEmpty()) {
////            for (Permission permission : permissions) {
////                authorizationValidate(permission);
////            }
////        }
////        return super.isPermittedAll(permissions, info);
////    }
//
//    @Override
//    protected TicketValidator createTicketValidator() {
//        String urlPrefix = getCasServerUrlPrefix();
//        if ("saml".equalsIgnoreCase(getValidationProtocol())) {
//            return new Saml11TicketValidator(urlPrefix);
//        }
//        return new MkCas20ServiceTicketValidator(urlPrefix);
//    }
//
//    /**
//     * 获取系统业务对象
//     */
//    public SystemService getSystemService() {
//        if (systemService == null){
//            systemService = SpringContextHolder.getBean(SystemService.class);
//        }
//        return systemService;
//    }
////
////    /**
////     * 授权验证方法
////     * @param permission
////     */
////    private void authorizationValidate(Permission permission){
////        // 模块授权预留接口
////    }
//}
