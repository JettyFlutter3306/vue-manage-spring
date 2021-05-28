package cn.element.service;

import cn.element.mapper.RightMapper;
import cn.element.mapper.RoleMapper;
import cn.element.mapper.UserMapper;
import cn.element.pojo.MyUser;
import cn.element.pojo.Right;
import cn.element.pojo.Role;
import cn.element.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RightMapper rightMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);

        User user = userMapper.selectOne(wrapper);

        if(user != null){
            //声明一个权限集合
            List<GrantedAuthority> authorityList = new ArrayList<>();

            //查询用户的角色
            List<Role> roleList = roleMapper.selectRoleListByUID(user.getId());

            if(!CollectionUtils.isEmpty(roleList)){
                for (Role role : roleList) {
                    //创建权限对象
                    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role.getRoleName());

                    authorityList.add(authority);
                }
            }

            //查询用户的权限
            List<Right> rightList = rightMapper.selectRightListByUID(user.getId());

            if(!CollectionUtils.isEmpty(rightList)){
                for (Right right : rightList) {
                    if(!StringUtils.isEmpty(right.getIdentity())){
                        //创建权限对象
                        GrantedAuthority authority = new SimpleGrantedAuthority(right.getIdentity());

                        authorityList.add(authority);
                    }
                }
            }

            log.info("用户ID ----{} ---- 拥有的权限: {}",user.getId(),authorityList);

            return new MyUser(user.getUsername(),user.getPassword(),authorityList);
        }

        return null;
    }
}
