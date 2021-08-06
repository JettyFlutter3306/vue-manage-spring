package cn.element.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUser extends User {

    private Integer userId;

    public MyUser(Integer userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
