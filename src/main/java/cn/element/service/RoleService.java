package cn.element.service;

import cn.element.mapper.RoleMapper;
import cn.element.pojo.Right;
import cn.element.pojo.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RightService rightService;

    public List<Role> getRoleList(){

        return roleMapper.selectList(null);
    }

    //根据角色Id和权限Id进行权限级联删除
    @Transactional
    public boolean deleteRightById(Integer roleId, Integer rightId){

        QueryWrapper<Role> wrapper = new QueryWrapper<>();

        //添加查询条件
        wrapper
                .select("right_ids")
                .eq("role_id",roleId);

        Role role = roleMapper.selectOne(wrapper);

        //拿到权限Id的字符串
        String rightIdsStr = role.getRightIds();

        //对权限Id字符串进行一波处理,转成整形列表,方便操作
        List<Integer> list = Arrays.stream(rightIdsStr.split(",")).map(Integer::valueOf).collect(Collectors.toList());

        List<Right> rightIdAndPIdList = rightService.getRightIdAndPIdList();

        //获取数据库中的权限表级联id集合,所有存在父子关联的结点id集合
        List<Integer> list1 = rightService.getCascadeListByRightId(rightId, new ArrayList<>(), rightIdAndPIdList);

        //取差集
        list.removeAll(list1);

        //再转成字符串
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < list.size(); i++) {
            if(i == list.size() - 1){
                sb.append(list.get(i));

                break;
            }

            sb.append(list.get(i)).append(",");
        }

        //更新角色表
        Role role1 = new Role();
        role1.setRoleId(roleId);
        role1.setRightIds(sb.toString());

        int i = roleMapper.updateById(role1);

        return i != -1;
    }

    /**
     * 根据角色Id修改权限字符串
     */
    @Transactional
    public boolean updateRightsByRoleId(Integer roleId,String idStr){

        Role role = new Role();
        role.setRoleId(roleId);
        role.setRightIds(idStr);

        int i = roleMapper.updateById(role);

        return i != -1;
    }

    /**
     * 获取角色名称列表及id
     */
    public List<Role> getRoleNameListAndId(){

        QueryWrapper<Role> wrapper = new QueryWrapper<>();

        wrapper.select("role_id","role_name");

        return roleMapper.selectList(wrapper);
    }


}
