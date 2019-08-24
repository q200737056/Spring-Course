package com.springboot2.test5.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.springboot2.test5.shiro.CustomCredentialsMatcher;
import com.springboot2.test5.shiro.UserRealm;

@Configuration
public class ShiroConfig {
	
	/**
	 * shiro过滤器
	 * @param securityManager
	 * @return
	 */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置登录的url，如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/index/loginIndex");
        //未授权界面;配置不会被拦截的链接
       //shiroFilterFactoryBean.setUnauthorizedUrl("/index/noperms");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        
        filterChainDefinitionMap.put("/h2-console/**", "anon");
        filterChainDefinitionMap.put("/index/login", "anon");
        filterChainDefinitionMap.put("/index/noperms", "anon");
       //filterChainDefinitionMap.put("/index/toAdd", "perms[user:add]");
        
        //filterChainDefinitionMap.put("/index/**", "authc");
        
        filterChainDefinitionMap.put("/index/logout", "logout");
        //主要这行代码必须放在所有权限设置的最后，user拦截表示 用户存在或记住我 可以访问
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }
    /**
     * 配置核心安全管理器
     * @param userRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm,MemoryConstrainedCacheManager cacheManager) {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        //设置 领域
        defaultSecurityManager.setRealm(userRealm);
        //设置 缓存
        defaultSecurityManager.setCacheManager(cacheManager());
        //设置 记住我
        defaultSecurityManager.setRememberMeManager(rememberMeManager());
        //设置 session管理器
        defaultSecurityManager.setSessionManager(sessionManager());
        
        return defaultSecurityManager;
    }
    /**
     * 自定义凭证匹配器
     */
    @Bean
    public SimpleCredentialsMatcher customCredentialsMatcher(){
    	return new CustomCredentialsMatcher();
    }
    /**
     * 自定义Realm
     * @return
     */
    @Bean
    public UserRealm userRealm() {
    	UserRealm realm = new UserRealm();
    	realm.setCredentialsMatcher(customCredentialsMatcher());
        return realm;
    }
    /**
     * 使用shiro自带的缓存，当然还可以使用第三方缓存
     * @return
     */
    @Bean
    public MemoryConstrainedCacheManager cacheManager() {
       return new MemoryConstrainedCacheManager();
    }
    /**
     * 会话管理管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
       // Collection<SessionListener> listeners = new ArrayList<SessionListener>();
       // listeners.add(new ShiroSessionListener());
       //sessionManager.setSessionListeners(listeners);
        //全局会话超时时间（单位毫秒），默认30分钟
        sessionManager.setGlobalSessionTimeout(1800000); 
        sessionManager.setSessionDAO(sessionDAO());
        //删除过期的session
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启会话验证器，默认是开启的
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //去掉URL地标后面的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
        sessionManager.setSessionValidationInterval(1800000);
        //sessionID是否保存到cookie中
        sessionManager.setSessionIdCookieEnabled(true);
        //sessionID Cookie
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }
    /**
     * 会话DAO
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        MemorySessionDAO sessionDAO = new MemorySessionDAO();
        return sessionDAO;
    }
    /**
     * 记住我 管理器
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
    	CookieRememberMeManager manager = new CookieRememberMeManager();
    	//cookie加密 密钥 ，默认AES算法
    	manager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
    	manager.setCookie(rememberMeCookie());
    	return manager;
    }
    /** 
     * 自动登录  Cookie
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //构造方法的参数 是cookie的名称
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 记住我cookie生效时间1天 ,单位秒
        cookie.setHttpOnly(true);
        cookie.setMaxAge(86400);
        return cookie;
    }
    /** 
     * 会话Cookie 保存sessionID
     * @return
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        //构造方法的参数 是cookie的名称
        SimpleCookie cookie = new SimpleCookie("sid");
        cookie.setHttpOnly(true);
        //关闭浏览器后 ，此cookie清除
        cookie.setMaxAge(-1);
        return cookie;
    }
    /**
     * *
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * *
     * 配置以下两个bean
     * DefaultAdvisorAutoProxyCreator(可选)
     * AOP式方法级权限检查，扫描上下文，寻找所有的Advistor(通知器），将这些Advisor应用到所有符合切入点的Bean中。
     * AuthorizationAttributeSourceAdvisor AOP式方法级权限检查
     * * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = 
        		new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}

