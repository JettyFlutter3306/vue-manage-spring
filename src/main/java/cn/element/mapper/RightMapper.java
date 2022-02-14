package cn.element.mapper;

import cn.element.pojo.permission.Right;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RightMapper extends BaseMapper<Right> {

    List<Right> selectRightListByUID(@Param("uid") Integer uid);

    List<Right> selectRightListByRoleId(@Param("roleId") Integer roleId);

    int deleteByRoleId(@Param("roleId") Integer roleId);  //根据角色的id删除中间表里的数据

    int insertByRoleId(@Param("roleId") Integer roleId, @Param("list") List<Integer> rightIds);


}
