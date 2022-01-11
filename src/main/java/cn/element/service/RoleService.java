package cn.element.service;

import cn.element.mapper.RightMapper;
import cn.element.mapper.RoleMapper;
import cn.element.pojo.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RightMapper rightMapper;

    @Autowired
    private RightService rightService;

    public List<Role> getRoleList() {
        List<Role> roleList = roleMapper.selectList(null);

        for (Role role : roleList) {
            role.setChildren(rightService.getRightListAsTree(role.getRoleId()));
        }

        return roleList;
    }

    /**
     * 根据角色Id修改权限字符串
     */
    @Transactional
    public boolean updateRightsByRoleId(Integer roleId, List<Integer> rids) {
        int j = rightMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(rids)) {
            return j != -1;
        }

        int i = rightMapper.insertByRoleId(roleId, rids);

        return i != -1;
    }

    /**
     * 获取角色名称列表及id
     */
    public List<Role> getRoleNameListAndId() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("role_id", "role_name");

        return roleMapper.selectList(wrapper);
    }

    /**
     * 根据用户id获取角色列表
     */
    public List<Role> getRolesByUserId(Integer uid) {
        return roleMapper.selectRoleListByUID(uid);
    }

    /**
     * 根据用户id和角色id列表分配角色
     * 先将用户原有的角色删除然后再插入新的角色
     */
    @Transactional
    public boolean updateRolesByUserId(Integer uid, List<Integer> roleIdList) {
        int i = roleMapper.deleteUserRoleById(uid);

        if (CollectionUtils.isEmpty(roleIdList)) {
            return i != -1;
        }

        int j = roleMapper.insertUserRoleById(uid, roleIdList);

        return j != -1;
    }


}
