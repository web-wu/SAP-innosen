package com.tabwu.SAP.user.filter;

import com.alibaba.fastjson.JSON;
import com.tabwu.SAP.common.config.RSAKeyProperty;
import com.tabwu.SAP.common.entity.LoginUser;
import com.tabwu.SAP.common.utils.JwtUtils;
import com.tabwu.SAP.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
 * @DATE: 2021/11/2 16:07
 * @DESCRIPTION:
 */
public class JwtLoginTokenfilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private RSAKeyProperty pro;

    public JwtLoginTokenfilter(AuthenticationManager authenticationManager, RSAKeyProperty pro) {
        this.authenticationManager = authenticationManager;
        this.pro = pro;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = JSON.parseObject(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                HashMap<String, Object> msg = new HashMap<>();
                msg.put("code",HttpServletResponse.SC_UNAUTHORIZED);
                msg.put("message","用户名或密码错误");
                out.write(JSON.toJSONString(msg));
                out.flush();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User principal = (User) authResult.getPrincipal();
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(principal,loginUser);
        loginUser.setRid(principal.getRoles().get(0).getId());
        loginUser.setRoleKey(principal.getRoles().get(0).getRoleKey());
        loginUser.setRoleName(principal.getRoles().get(0).getRoleName());
//        String token = JwtUtils.getJwtToken(principal,pro.getPrivateKey());
        String token = JwtUtils.getJwtToken(loginUser);
        response.setHeader("Authorization","Bearer " + token);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            HashMap<String, Object> msg2 = new HashMap<>();
            msg2.put("code",HttpServletResponse.SC_OK);
            msg2.put("message","认证通过");
            out.write(JSON.toJSONString(msg2));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
