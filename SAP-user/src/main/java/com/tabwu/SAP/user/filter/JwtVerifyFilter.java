package com.tabwu.SAP.user.filter;

import com.alibaba.fastjson.JSON;
import com.tabwu.SAP.common.config.RSAKeyProperty;
import com.tabwu.SAP.common.utils.JwtUtils;
import com.tabwu.SAP.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @PROJECT_NAME: springsecurity_sso
 * @USER: tabwu
 * @DATE: 2021/11/3 8:59
 * @DESCRIPTION:
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RSAKeyProperty pro;

    public JwtVerifyFilter(AuthenticationManager authenticationManager,RSAKeyProperty pro) {
        super(authenticationManager);
        this.pro = pro;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                chain.doFilter(request,response);
                tokenVerifyError(response);
            } else {
                token = token.replace("Bearer ","");
                User sysUser = JwtUtils.getMemberIdByJwtToken(token, User.class, pro.getPublicKey());
                if (sysUser != null) {
                    UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(sysUser, sysUser.getRoles(), sysUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authResult);
                    chain.doFilter(request,response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            tokenVerifyError(response);
        }
    }


    public void tokenVerifyError(HttpServletResponse response) throws IOException {
        response.setContentType("application/jsom;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        HashMap<String, Object> msg = new HashMap<>();
        msg.put("code",HttpServletResponse.SC_UNAUTHORIZED);
        msg.put("message","token检验失败");
        out.write(JSON.toJSONString(msg));
        out.flush();
        out.close();
    }


}
