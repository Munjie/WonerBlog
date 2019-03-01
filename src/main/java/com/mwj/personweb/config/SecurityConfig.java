package com.mwj.personweb.config;

import com.mwj.personweb.filter.VerifyCodeFilter;
import com.mwj.personweb.handler.CustomPermissionEvaluatorHandler;
import com.mwj.personweb.service.serviceimpl.CustomUserDetailsService;
import com.mwj.personweb.service.serviceimpl.Md5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Author: 母哥 @Date: 2019-02-28 15:25 @Version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // 记住我登陆
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
        //        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     * 注入自定义PermissionEvaluator 注意#开启自定义bean
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CustomPermissionEvaluatorHandler());
        return handler;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
    }

    //    @Override
    //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //        auth.userDetailsService(userDetailsService)
    //                .passwordEncoder(
    //                        new PasswordEncoder() {
    //                            @Override
    //                            public String encode(CharSequence charSequence) {
    //                                return charSequence.toString();
    //                            }
    //
    //                            @Override
    //                            public boolean matches(CharSequence charSequence, String s) {
    //                                return s.equals(charSequence.toString());
    //                            }
    //                        });
    //    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/home/**", "/article/**", "/kaptcha")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/admin/login")
                .failureUrl("/admin/login/error")
                .defaultSuccessUrl("/admin/login/success")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                // 自动登录
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                // 有效时间：单位s
                .tokenValiditySeconds(60)
                .userDetailsService(userDetailsService)
                .and()
                // 验证码过滤器
                .addFilterBefore(new VerifyCodeFilter(), UsernamePasswordAuthenticationFilter.class);
        // 关闭CSRF跨域
        http.csrf().disable();
    }

    // 设置拦截忽略文件夹，可以对静态资源放行
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    /**
     * 设置用户密码的加密方式
     *
     * @return
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    /**
     * 自定义UserDetailsService，授权
     *
     * @return
     */
    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    /**
     * AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
  }
}
