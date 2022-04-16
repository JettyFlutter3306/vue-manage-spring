package cn.element.manage.test;

import cn.element.manage.mapper.UserMapper;
import cn.element.manage.pojo.permission.User;
import cn.element.manage.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUser {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 测试查询用户列表,手动查询sql,包含角色列表
     */
    @Test
    public void test01() {

        Page<User> page = userService.getUserList("", 1, 5);

        System.out.println(page.getTotal());
        page.getRecords().forEach(System.out::println);

    }
}
