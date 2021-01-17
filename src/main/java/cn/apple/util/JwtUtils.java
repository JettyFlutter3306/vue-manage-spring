package cn.apple.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JwtUtils {

    //授权签名,尽量随便写
    public static final String TOKEN = "TOKEN@WEIisnasa.cn";

    /**
     * 生成token
     * @param  map 传入payload
     * @return 返回token
     */
    public static String getToken(Map<String,String> map){

        JWTCreator.Builder builder = JWT.create();

        map.forEach(builder::withClaim);

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE,7); //七天过期

        builder.withExpiresAt(calendar.getTime());

        return builder.sign(Algorithm.HMAC256(TOKEN));
    }

    /**
     * 验证token
     */
    public static void verify(String token){

        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }

    /**
     * 获取token中的payload
     */
    public static DecodedJWT getToken(String token){

        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }
}
