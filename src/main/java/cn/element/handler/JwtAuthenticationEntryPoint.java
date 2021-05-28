package cn.element.handler;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 定义认证失败处理类
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        log.info("认证失败!未登录!");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ResultInfo resultInfo = ResultInfo.forbidden(Constant.NOT_LOGIN);

        JsonUtil.writeValueAsString(resultInfo,response);
    }
}
