package cn.apple.common;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装jwt的产生的token并且包装用户名和角色名称
 * 方便下一步整合Spring Security安全框架
 */
@Data
@NoArgsConstructor
public class Token {

    private String token;//令牌字符串

    private String username;//用户名

    public Token(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
