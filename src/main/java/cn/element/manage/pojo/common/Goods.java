package cn.element.manage.pojo.common;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@TableName("tb_goods")
public class Goods { //商品类

    @TableId
    private Integer goodsId; //商品id

    private String goodsName; //商品名称

    private Double goodsPrice; //商品价格

    private Integer goodsNumber; //商品数量

    private Integer goodsWeight; //商品重量

    private Integer catId; //类型id

    private String goodsIntroduce; //商品介绍

    private String goodsBigLogo; //商品大图

    private String goodsSmallLogo; //商品小图

    private Integer isDel; //是否删除 0: 正常 1: 删除

    private Integer catOneId; //一级分类id

    private Integer catTwoId; //二级分类id

    private Integer catThreeId; //三级分类id

    private Integer hotNumber; //热卖数量

    private Integer isPromote; //是否促销 0: 否 1: 是

    private Integer goodsState; //商品状态 0: 未通过 1: 审核中 2: 已审核

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Date updateTime; //修改时间

    @TableField(exist = false)
    private List<Integer> goodsCat; //商品所属分类id数组

    @TableField(exist = false)
    private List<Map<String,String>> attrs; //参数列表 一个参数id对应一条attrVal

    @TableField(exist = false)
    private List<String> pics; //图片数组
}
