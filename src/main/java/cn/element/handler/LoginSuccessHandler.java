package cn.element.handler;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.pojo.MyUser;
import cn.element.util.JsonUtil;
import cn.element.util.JwtUtil;
import cn.element.util.SecurityUtil;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        MyUser myUser = (MyUser) SecurityUtil.getCurrentPrinciple();

        String username = myUser.getUsername();

        String tokenName = JwtUtil.getToken(username);

        ResultInfo resultInfo = ResultInfo.ok(Constant.LOGIN_SUCCESS,
                MapUtil.builder()
                        .put("username",myUser.getUsername())
                        .put("userId", myUser.getUserId())
                        .put("Authorization",tokenName).build()
        );

        JsonUtil.writeValueAsString(resultInfo,response);

        log.info("token --- {}",tokenName);
    }


}
