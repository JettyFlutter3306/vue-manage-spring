package cn.apple.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@TableName("tb_category")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    @TableId(type = IdType.AUTO)
    private Integer catId; //分类id

    private String catName; //分类名称

    private Integer parentId; //父节点id

    private Integer catLevel; //分类层级

    private Integer catDeleted; //是否删除

    private String catIcon; //图标

    private String catSrc; //数据源

    @TableField(exist = false)
    private List<Category> children; //子节点集合
}
