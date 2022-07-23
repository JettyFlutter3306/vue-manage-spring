package cn.element.manage.service;

import cn.element.manage.pojo.permission.User;
import cn.element.manage.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询用户列表
     */
    public Page<User> getUserList(String keyword, Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        List<User> userList = userMapper.selectUserList(keyword, (pageNum - 1) * pageSize, pageSize);
        Integer count = userMapper.selectCount(null);

        page.setRecords(userList)
            .setTotal(count);

        return page;
    }

    @Transactional
    public boolean addUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        int i = userMapper.insert(user);
        return i != -1;
    }

    @Transactional
    public boolean deleteUserById(Integer id) {
        int i = userMapper.deleteById(id);
        return i != -1;
    }

    public User selectUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Transactional
    public boolean editUserStatus(Integer id, Integer status) {
        switch (status) {
            case User.USER_STATUS_OFF:
                status = User.USER_STATUS_ON;
                break;
            case User.USER_STATUS_ON:
                status = User.USER_STATUS_OFF;
                break;
        }

        User user = new User();
        user.setId(id)
            .setStatus(status);

        int i = userMapper.updateById(user);
        return i != -1;
    }

    @Transactional
    public boolean editUserInfoById(User user) {
        int i = userMapper.updateById(user);
        return i != -1;
    }

    @Transactional
    public boolean allotRoleByUserId(Integer userId, Integer roleId) {
        Integer i = userMapper.allotRoleByUserId(userId, roleId);
        return i != -1;
    }

    public User findUser(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username)
               .eq("status", User.USER_STATUS_ON);

        return userMapper.selectOne(wrapper);
    }

}
