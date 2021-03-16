package cn.apple.mapper;

import cn.apple.pojo.Right;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RightMapper extends BaseMapper<Right> {

    @Select("select * from tb_right as a left join inner_role_right as b " +
            "          on a.id = b.right_id left join inner_user_role as c " +
            "          on c.role_id = b.role_id " +
            "        where c.user_id = #{uid}")
    List<Right> selectRightListByUID(@Param("uid") Integer uid);


}
