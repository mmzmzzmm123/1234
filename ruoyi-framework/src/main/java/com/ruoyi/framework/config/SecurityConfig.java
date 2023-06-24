package com.ruoyi.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;
import com.ruoyi.framework.config.properties.PermitAllUrlProperties;
import com.ruoyi.framework.security.filter.JwtAuthenticationTokenFilter;
import com.ruoyi.framework.security.handle.AuthenticationEntryPointImpl;
import com.ruoyi.framework.security.handle.LogoutSuccessHandlerImpl;

/**
 * spring security配置
 *
 * @author ruoyi
 */
@Configuration
public class SecurityConfig
{
    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    /**
     * 允许匿名访问的地址
     */
    @Autowired
    private PermitAllUrlProperties permitAllUrl;

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {

        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf(xx -> xx.disable())
                .headers(xx -> {
                    // 禁用HTTP响应标头
                    xx.cacheControl(yy -> yy.disable());
                    xx.frameOptions(yy -> yy.disable());
                })
                // 认证失败处理类
                .exceptionHandling(t -> t.authenticationEntryPoint(unauthorizedHandler))
                // 基于token，所以不需要session
                .sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        httpSecurity
                // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> {
                            //不需要保护的资源路径允许访问
                            permitAllUrl.getUrls().forEach(url -> authorizeHttpRequests.requestMatchers(url).permitAll());
                            authorizeHttpRequests
                                    // 允许所有OPTIONS请求
                                    .requestMatchers(HttpMethod.OPTIONS, "/*").permitAll()
                                    // -------------
                                    .requestMatchers("/login", "/register", "/captchaImage").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/", "/*.html", "/*/*.html", "/*/*.css", "/*/*.js", "/profile/*").permitAll()
                                    .requestMatchers("/swagger-ui.html", "/swagger-resources/*", "/webjars/*", "/*/api-docs", "/druid/*").permitAll()
                                    // -------------
                                    .anyRequest().authenticated();
                        }
                );

        // 添加Logout filter
        httpSecurity.logout(t->t.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler));
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);

        return httpSecurity.build();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 调用loadUserByUsername获得UserDetail信息，在AbstractUserDetailsAuthenticationProvider里执行用户状态检查
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // DaoAuthenticationProvider 从自定义的 userDetailsService.loadUserByUsername 方法获取UserDetails
        authProvider.setUserDetailsService(userDetailsService);

        // 设置密码编辑器
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

    /**
     * 登录时需要调用AuthenticationManager.authenticate执行一次校验
     *
     * @param config
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
