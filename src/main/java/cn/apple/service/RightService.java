package cn.apple.service;

import cn.apple.mapper.RightMapper;
import cn.apple.mapper.RoleMapper;
import cn.apple.pojo.Right;
import cn.apple.pojo.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RightService {

    @Autowired
    private RightMapper rightMapper;

    @Autowired
    private RoleMapper roleMapper;

    //获取所有的权限,返回一个列表
    public List<Right> getRightList(){

        return rightMapper.selectList(null);
    }

    //根据角色的Id获取树形的权限列表
    public List<Right> getRightListAsTree(Integer roleId){

        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        //构造查询条件
        wrapper
                .select("right_ids")
                .eq("role_id",roleId);

        Role role = roleMapper.selectOne(wrapper);

        String rightIdStr = role.getRightIds();

        if(!StringUtils.isEmpty(rightIdStr)){
            //将字符串转为整形数组,即使权限编号数组
            List<Integer> rightIds = Stream.of(rightIdStr.split(",")).map(Integer::valueOf).collect(Collectors.toList());

            List<Right> rightList = rightMapper.selectBatchIds(rightIds);

            Map<Integer,Right> map = new HashMap<>();

            //先放置一级菜单
            rightList.forEach(r -> map.put(r.getId(),r));

            for (Right right : rightList) {
                if(map.containsKey(right.getParentId())){
                    map.get(right.getParentId()).getChildren().add(right);
                }
            }

            return rightList.stream().filter(s -> s.getParentId() == 0).collect(Collectors.toList());
        }

        //不能返回空值,否则前端递归遍历树形权限不好处理
        return new ArrayList<>();
    }

    //获取菜单列表
    public List<Right> getRightListAsTree(){

        List<Right> rightList = rightMapper.selectList(null);

        Map<Integer,Right> map = new HashMap<>();

        //先放置一级菜单
        rightList.forEach(r -> {
            map.put(r.getId(),r);
        });

        //处理二级菜单
        for (Right right : rightList) {
            if(map.containsKey(right.getParentId())){
                map.get(right.getParentId()).getChildren().add(right);
            }
        }

        //过滤操作,父节点Id为0留下
        return rightList.stream().filter(s -> s.getParentId() == 0).collect(Collectors.toList());
    }
}
