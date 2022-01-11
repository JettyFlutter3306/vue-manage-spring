package cn.element.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * 获取当前用户
     */
    public static Authentication getCurrentUserAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户
     */
    public static Object getCurrentPrinciple() {
        return getCurrentUserAuth().getPrincipal();
    }
}
