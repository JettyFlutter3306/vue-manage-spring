package cn.element.manage.handler;

import cn.element.manage.common.Constant;
import cn.element.manage.common.ResultInfo;
import cn.element.manage.util.JsonUtil;
import cn.element.manage.util.JwtUtil;
import cn.element.manage.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * 自定义Jwt过滤器
 */
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        log.info("jwt 校验 filter");
        String token = request.getHeader(JwtUtil.HEADER);

        if (StringUtils.isEmpty(token)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claim = JwtUtil.getClaimByToken(token);

        if (claim == null || JwtUtil.isTokenExpired(claim)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            ResultInfo resultInfo = ResultInfo.notLogin(Constant.NOT_LOGIN);
            JsonUtil.writeValueAsString(resultInfo, response);
            return;
        }

        String username = claim.getSubject();
        log.info("用户---{},正在登录!", username);

        // 获取用户信息
        Collection<? extends GrantedAuthority> authorities = SecurityUtil.getCurrentUserAuth().getAuthorities();
        UsernamePasswordAuthenticationToken uPToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(uPToken);

        chain.doFilter(request, response);  // 放行
    }
}
