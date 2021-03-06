package com.qianfeng.springboot.configuration;

import com.qianfeng.springboot.shiro.MyRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.LinkedHashMap;

@Configuration
public class SpringConfiguration {

    @Bean
    public ShiroFilterFactoryBean provideShiroFilter(MyRealm myRealm){

        System.out.println("----->>>>");
        //spring整合Shiro的过滤器工厂类，在此工厂中由SPring管理Shiro的一些对象。
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        //创建一个SecurityManager
        //厨师
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //需要Realm
        securityManager.setRealm(myRealm);
        factoryBean.setSecurityManager(securityManager);
        //权限拦截的配置
        //为什么要使用有序的map
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("/user/login","anon");
//        linkedHashMap.put("/**","authc");
        factoryBean.setFilterChainDefinitionMap(linkedHashMap);
        // book/query=authc

        return factoryBean;
    }


}
