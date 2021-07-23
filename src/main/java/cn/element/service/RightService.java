package cn.element.service;

import cn.element.mapper.RightMapper;
import cn.element.mapper.RoleMapper;
import cn.element.pojo.Right;
import cn.element.pojo.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RightService {

    @Autowired
    private RightMapper rightMapper;

    //获取所有的权限,返回一个列表
    public List<Right> getRightList(){

        return rightMapper.selectList(null);
    }

    /**
     * 根据角色的Id获取树形的权限列表
     * 以时间换空间,否则的话时间复杂度则是O(n²)
     * 这样一来时间复杂度就是O(n)
     * @param roleId        角色id
     */
    public List<Right> getRightListAsTree(Integer roleId){

        List<Right> rightList = rightMapper.selectRightListByRoleId(roleId);  //先查出来

        Map<Integer,Right> map = new HashMap<>();  //定义一个HashMap
        List<Right> list = new ArrayList<>();  //顶一个list作为最后的结果返回

        for (Right right : rightList) {
            map.put(right.getId(), right);
        }

        for (Right right : rightList) {
            Right parentObj = map.get(right.getParentId());

            if(ObjectUtils.isEmpty(parentObj)){  //父结点是空的表明right是一级结点
                list.add(right);
            }else{  //否则就把right加入到父结点的children子结点列表里面
                parentObj.getChildren().add(right);
            }
        }

        if(!CollectionUtils.isEmpty(rightList)){
            return list;
        }

        return new ArrayList<>();  //不能返回空值,否则前端递归遍历树形权限不好处理
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

    /**
     * 根据权限Id获取它的所有的下级权限Id列表
     * @param rightId           需要查询的权限Id
     * @param childRightList    子节点的Id集合
     * @param rightIdList       用来对照的权限对象的Id和parentId
     * @return                  返回所有子节点的Id集合
     */
    public List<Integer> getCascadeListByRightId(Integer rightId,List<Integer> childRightList,List<Right> rightIdList){

        if(!childRightList.contains(rightId)){
            childRightList.add(rightId);
        }

        for (Right right : rightIdList) {
            if(right.getParentId().equals(rightId)){
                childRightList.add(right.getId());

                if(this.childIdIsExist(right.getId(),rightIdList)){
                    //递归查询,childRightList负责收集子节点的id,rightIdList负责对照
                    this.getCascadeListByRightId(right.getId(),childRightList,rightIdList);
                }
            }
        }

        return childRightList;
    }

    //查询权限Id和ParentId列表
    public List<Right> getRightIdAndPIdList(){

        QueryWrapper<Right> wrapper = new QueryWrapper<>();

        wrapper.select("id","parent_id");

        return rightMapper.selectList(wrapper);
    }

    //查询是否有子节点Id
    private boolean childIdIsExist(Integer rightId,List<Right> rightIdList){

        for (Right right : rightIdList) {
            if(rightId.equals(right.getParentId())){
                return true;
            }
        }

        return false;
    }
}
