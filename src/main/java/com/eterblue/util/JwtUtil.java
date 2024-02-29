package com.eterblue.util;

import cn.hutool.jwt.JWTUtil;
import com.eterblue.model.pojo.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String signKey="eterblue";

    private static final Long expire=7200000L;
    public static String getToken(Map<String,Object> claim){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,signKey.getBytes(StandardCharsets.UTF_8))
                .setClaims(claim)
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .compact();
    }

    public static User decodeUser(String token){
        Claims body = Jwts.parser()
                .setSigningKey(signKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        Long id = Long.valueOf(body.get("id").toString());
        String phone = body.get("phone").toString();
        User user= User.builder()
                .id(id)
                .phone(phone)
                .build();
        //TODO
        ThreadUtil.setCurrentId(id);
        return user;
    }

}
