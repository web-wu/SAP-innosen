package com.tabwu.SAP.gateway.filter;

import com.tabwu.SAP.common.entity.LoginUser;
import com.tabwu.SAP.common.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/5/26 16:13
 * @DESCRIPTION:   所有的请求都需要经过此 过滤器验证登录token
 */
@Component
//@EnableConfigurationProperties(RSAKeyProperty.class)
public class AuthorizeFilter implements GlobalFilter, Ordered {


    /*@Autowired
    private RSAKeyProperty pro;*/

    private static final String AUTHORIZE_TOKEN = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();
        if (path.equals("/login") || path.equals("/doc.html")) {
            chain.filter(exchange);
        }

        // 从header中获取token
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);

        // 从cookie中获取token
        if (StringUtils.isEmpty(token)) {
            HttpCookie first = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (first != null) {
                token = first.getValue();
            }
        }

        // 从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }

        // 如果 token 为null 直接拦截
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
            return response.setComplete();
        }

        // 验证token有效性
//        if (JwtUtils.checkToken(token,pro.getPublicKey())) {
//            return chain.filter(exchange);
//        }
        token = token.replace("Bearer ","");
        if (JwtUtils.checkToken(token)) {
            return chain.filter(exchange);
        }

        response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
