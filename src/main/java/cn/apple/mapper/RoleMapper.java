package cn.apple.mapper;

import cn.apple.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from tb_role a where a.role_id in " +
            "(select role_id " +
            " from inner_user_role " +
            " where user_id = #{uid})")
    List<Role> selectRoleListByUID(@Param("uid") Integer uid);

}
