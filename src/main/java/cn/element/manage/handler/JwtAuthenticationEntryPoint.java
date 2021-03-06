package cn.element.manage.handler;

import cn.element.manage.common.ResultInfo;
import cn.element.manage.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.element.manage.common.Constant.NOT_LOGIN;

/**
 * 定义认证失败处理类
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        log.info("认证失败!未登录!");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResultInfo resultInfo = ResultInfo.notLogin(NOT_LOGIN);

        JsonUtil.writeValueAsString(resultInfo,response);
    }
}
