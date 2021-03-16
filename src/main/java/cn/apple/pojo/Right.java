package cn.apple.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@TableName("tb_right")
public class Right {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String rightName; //权限名称

    private Integer parentId; //上一级权限父节点

    private String rootPath; //根路径

    private String icon; //图标

    private Integer level; //权限等级

    private String identity;//权限标识

    @TableField(exist = false)
    private List<Right> children = new ArrayList<>(); //子节点
}
