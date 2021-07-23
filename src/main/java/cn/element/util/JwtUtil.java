package cn.element.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    //授权签名,尽量随便写
    public static final String TOKEN = "TOKEN@WEIisnasa.cn";

    public static final String HEADER = "Authorization";

    public static final Integer EXPIRED_TIME = 24 * 60 * 60;  //过期时间,单位秒

    // 生成jwt
    public static String getToken(String username) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * EXPIRED_TIME);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)// 7天过期
                .signWith(SignatureAlgorithm.HS512, TOKEN)
                .compact();
    }

    /**
     * 解析jwt
     */
    public static Claims getClaimByToken(String jwt) {

        try {
            return Jwts.parser()
                    .setSigningKey(TOKEN)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * jwt是否过期
     */
    public static boolean isTokenExpired(Claims claims) {

        return claims.getExpiration().before(new Date());
    }




}
