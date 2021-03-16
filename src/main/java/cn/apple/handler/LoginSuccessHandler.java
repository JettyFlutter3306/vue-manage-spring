package cn.apple.handler;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.common.Token;
import cn.apple.pojo.MyUser;
import cn.apple.util.JsonUtil;
import cn.apple.util.JwtUtil;
import cn.apple.util.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        MyUser myUser = (MyUser) SecurityUtil.getCurrentPrinciple();

        Map<String,String> map = new HashMap<>();
        map.put("username", myUser.getUsername());
        map.put("password", myUser.getPassword());

        String tokenName = JwtUtil.getToken(map);

        Token token = new Token(tokenName, myUser.getUsername());

        ResultInfo resultInfo = ResultInfo.ok(Constant.LOGIN_SUCCESS,token);

        JsonUtil.writeValueAsString(resultInfo,response);
    }


}
