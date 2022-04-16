package cn.element.manage.mapper;

import cn.element.manage.pojo.permission.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRoleListByUID(@Param("uid") Integer uid);

    int insertUserRoleById(@Param("uid") Integer uid, @Param("list") List<Integer> roleIdList);

    int deleteUserRoleById(@Param("uid") Integer uid);

    List<Role> selectNotInRoleListByUID(@Param("uid") Integer uid);

}
