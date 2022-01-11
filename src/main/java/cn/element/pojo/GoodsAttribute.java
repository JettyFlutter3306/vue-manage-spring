package cn.element.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品表 和 参数表 的中间关联表
 */
@Data
@NoArgsConstructor
@TableName("tb_goods_attribute")
public class GoodsAttribute {

    @TableId
    private Integer id;

    private Integer goodsId;//商品的id

    private Integer attrId;//参数的id

    private String attrValue;//参数的值

    private Double addPrice;//该属性要增加的额外价钱

}
