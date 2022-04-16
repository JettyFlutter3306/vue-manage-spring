package cn.element.manage.handler;

import cn.element.manage.common.Constant;
import cn.element.manage.common.ResultInfo;
import cn.element.manage.util.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailedHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResultInfo resultInfo = ResultInfo.badRequest(Constant.LOGIN_FAILED);

        JsonUtil.writeValueAsString(resultInfo,response);
    }
}
