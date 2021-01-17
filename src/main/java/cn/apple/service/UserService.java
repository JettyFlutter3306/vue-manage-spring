package cn.apple.service;

import cn.apple.mapper.UserMapper;
import cn.apple.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //分页查询用户列表
    public Page<User> getUserList(Integer pageNum,Integer pageSize){

        Page<User> page = new Page<>(pageNum,pageSize);

        userMapper.selectPage(page,null);

        return page;
    }

    //添加一个用户
    @Transactional
    public boolean addUser(User user){

        try {
            userMapper.insert(user);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //根据用户id删除用户
    @Transactional
    public boolean deleteUserById(Integer id){

        try {
            userMapper.deleteById(id);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public User selectUserById(Integer id){

        return userMapper.selectById(id);
    }

    //根据id修改用户的状态
    @Transactional
    public boolean editUserStatus(Integer id,Integer status){

        switch (status){
            case 0: status = 1;break;
            case 1: status = 0;break;
        }

        User user = new User();
        user.setId(id);
        user.setStatus(status);

        try {
            userMapper.updateById(user);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
