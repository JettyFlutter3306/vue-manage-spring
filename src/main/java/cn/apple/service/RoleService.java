package cn.apple.service;

import cn.apple.mapper.RoleMapper;
import cn.apple.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> getRoleList(){

        return roleMapper.selectList(null);
    }


}
