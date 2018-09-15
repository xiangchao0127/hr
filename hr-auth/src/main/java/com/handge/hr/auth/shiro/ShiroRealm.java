package com.handge.hr.auth.shiro;

import com.handge.hr.domain.repository.mapper.EntityAccountMapper;
import com.handge.hr.domain.repository.pojo.EntityAccount;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Liujuhao
 * @date 2018/5/31.
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    EntityAccountMapper entityAccountMapper;

//    @Autowired
//    RepositoryAccount repositoryAccount;

    // TODO: 2018/6/1 基于身份授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.debug("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        // 获取身份信息
        EntityAccount user = (EntityAccount) principals.getPrimaryPrincipal();
        EntityAccount entityAccount = new EntityAccount();
        entityAccount.setUsername(user.getUsername());
        EntityAccount authAccount = entityAccountMapper.selectOne(entityAccount);
//        // 将权限信息封闭为AuthorizationInfo
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        // 添加角色和权限
//        for (TableAuthRoleBasic role : authAccount.getRoleList()) {
//            simpleAuthorizationInfo.addRole(role.getId() + "");
//            for (TableAuthPermission permission : role.getPermissionList()) {
//                simpleAuthorizationInfo.addStringPermission(permission.getPattern());
//            }
//        }
        return simpleAuthorizationInfo;
    }

    // TODO: 2018/6/1 登录身份验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.debug("身份认证方法：ShiroRealm.doGetAuthenticationInfo()");

        String username = (String) authcToken.getPrincipal();

        // 通过 username 从数据库中查询
        EntityAccount user = entityAccountMapper.findByUsername(username);

        if (user == null) {
            throw new UnifiedException(null, ExceptionWrapperEnum.Auth_NOT_Validate);
        }

        /**
         * 获取权限信息
         * 获取之后可以在前端for循环显示所有连接
         */
        // FIXME: 2018/6/5 获取权限信息

        //自动加密有BUG，暂时改用手动加密
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), getName());
    }

}
