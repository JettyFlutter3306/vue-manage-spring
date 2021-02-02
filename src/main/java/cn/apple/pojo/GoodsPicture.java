package cn.apple.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品表 和 商品图片表的 中间关联表
 */
@Data
@NoArgsConstructor
@TableName("tb_goods_pics")
public class GoodsPicture {

    @TableId(type = IdType.AUTO)
    private Integer picsId;//id

    private Integer goodsId;//商品的id

    private String picsBig;//商品大图

    private String picsMid;//商品中图

    private String picsSma;//商品小图
}
