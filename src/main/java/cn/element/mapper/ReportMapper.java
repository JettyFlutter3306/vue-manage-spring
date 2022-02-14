package cn.element.mapper;

import cn.element.pojo.common.Report;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReportMapper extends BaseMapper<Report> {

    @Select("select rp1_user_count from `tb_report_1` where rp1_area = #{area}")
    List<Integer> selectUserCountByArea(@Param("area") String area);

}
