package cn.element.handler;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.util.JsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义权限不足处理器
 */
@Component
public class CustomizedAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        ResultInfo resultInfo = ResultInfo.forbidden(Constant.ACCESS_DENIED);

        JsonUtil.writeValueAsString(resultInfo, response);
    }
}
