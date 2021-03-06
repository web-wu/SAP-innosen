package com.tabwu.SAP.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.tabwu.SAP.common.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * @author helen
 * @since 2019/10/16
 */
public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final String SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";
    public static final String USER_KEY = "user";

    /*普通字符串加密、解密*/

    public static String getJwtToken(Object userInfo){
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("SAP-INNOSEN")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim(USER_KEY, JSON.toJSONString(userInfo))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static <T> T getMemberIdByJwtToken(HttpServletRequest request, Class<T> userType) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (T) claims.get(USER_KEY);
    }


    /**
     * 根据token获取登录用户信息
     * @param request
     * @return
     */
    public static LoginUser getLoginUserByToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwtToken)) {
            return null;
        }
        jwtToken = jwtToken.replace("Bearer ","");
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return JSON.parseObject((String) claims.get(USER_KEY),LoginUser.class);
    }


    /**
     * 根据token获取会员id
     * @param token
     * @return
     */
    public static <T> T getMemberIdByJwtToken(String token,Class<T> userType) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return JSON.parseObject((String) claims.get(USER_KEY),userType);
    }



    /*公钥、私钥加密、解密*/

    public static String getJwtToken(Object userInfo, PrivateKey privateKey){
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("SAP-INNOSEN")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim(USER_KEY, JSON.toJSONString(userInfo))
                .signWith(SignatureAlgorithm.HS256, privateKey)
                .compact();
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken, PublicKey publicKey) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request,PublicKey publicKey) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static <T> T getMemberIdByJwtToken(HttpServletRequest request, Class<T> userType,PublicKey publicKey) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (T) claims.get(USER_KEY);
    }

    /**
     * 根据token获取会员id
     * @param token
     * @return
     */
    public static <T> T getMemberIdByJwtToken(String token,Class<T> userType,PublicKey publicKey) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return JSON.parseObject((String) claims.get(USER_KEY),userType);
    }
}
