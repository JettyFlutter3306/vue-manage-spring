package cn.element.manage.test;

import cn.element.manage.mapper.UserMapper;
import cn.element.manage.pojo.permission.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class TestDemo {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("123456"));
    }

    @Test
    public void test02() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username","洛必达");

        User user = userMapper.selectOne(wrapper);
    }
}
