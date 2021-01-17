package cn.apple.interceptor;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.util.JwtUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//全局拦截器
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求头中的token信息
        String token = request.getHeader("token");

        ResultInfo resultInfo = new ResultInfo();

        try {
            JwtUtils.verify(token); //验证token

            return true;
        } catch (SignatureVerificationException e) {
            resultInfo.setMsg(Constant.SIGNATURE_INVALID);

            e.printStackTrace();
        } catch (TokenExpiredException e) {
            resultInfo.setMsg(Constant.TOKEN_EXPIRED);

            e.printStackTrace();
        } catch (AlgorithmMismatchException e) {
            resultInfo.setMsg(Constant.TOKEN_WRONG_ALGORITHM);

            e.printStackTrace();
        } catch (Exception e) {
            resultInfo.setMsg(Constant.TOKEN_INVALID);

            e.printStackTrace();
        }

        resultInfo.setFlag(false);
        resultInfo.setCode(403);

        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);

        return false;
    }


}
