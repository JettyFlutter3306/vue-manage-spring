package cn.apple.mapper;

import cn.apple.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {

    @Update("update tb_user set role_name = (select role_name from tb_role where role_id = #{roleId})" +
            "where id = #{userId}")
    Integer allotRoleByUserId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);



}
