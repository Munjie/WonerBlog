package com.mwj.personweb.config;

import com.mwj.personweb.handler.InterceptorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/** @Author: 母哥 @Date: 2019-02-21 17:17 @Version 1.0 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Resource private InterceptorHandler interceptorHandler;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(interceptorHandler).addPathPatterns("/*");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
  }
}
