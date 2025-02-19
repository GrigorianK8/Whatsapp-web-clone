package com.grigoriank.whatsappWebClone.util;

import com.grigoriank.whatsappWebClone.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", authentication.getName())
                .signWith(key)
                .compact();

        return jwt;
    }

    public String getEmailByToken(String jwt) {

        jwt = jwt.substring(7);
        Claims claim = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claim.get("email"));

        return email;
    }









}
