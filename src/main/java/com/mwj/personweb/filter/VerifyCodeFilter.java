package com.mwj.personweb.filter;

import com.mwj.personweb.utils.CodeUtil;
import org.springframework.security.authentication.DisabledException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 母哥 @Date: 2019-03-01 15:24 @Version 1.0
 */
public class VerifyCodeFilter extends OncePerRequestFilter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isProtectedUrl(request)) {
            if (!CodeUtil.checkVerifyCode(request)) {
                // 手动设置异常
                request
                        .getSession()
                        .setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new DisabledException("验证码输入错误"));
                // 转发到错误Url
                request.getRequestDispatcher("/admin/login/error").forward(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    // 拦截 /login的POST请求
    private boolean isProtectedUrl(HttpServletRequest request) {
        return "POST".equals(request.getMethod())
                && pathMatcher.match("/admin/login", request.getServletPath());
    }
}
