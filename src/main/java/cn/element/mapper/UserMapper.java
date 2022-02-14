package cn.element.mapper;

import cn.element.pojo.permission.Role;
import cn.element.pojo.permission.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Update("update tb_user set role_name = (select role_name from tb_role where role_id = #{roleId})" +
            "where id = #{userId}")
    Integer allotRoleByUserId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    List<User> selectUserList(@Param("keyword") String keyword,
                              @Param("pageNum") Integer pageNum,
                              @Param("pageSize") Integer pageSize);

    List<Role> selectRoleListByUid(@Param("user_id") Integer userId);



}
