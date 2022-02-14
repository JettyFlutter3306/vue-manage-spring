package cn.element.mapper;

import cn.element.pojo.common.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface GoodsMapper extends BaseMapper<Goods> {

    @Select("select AUTO_INCREMENT from INFORMATION_SCHEMA.TABLES\n" +
            "where TABLE_NAME='tb_goods'")
    Integer selectAutoIncrement();
}
