package cn.element.pojo;

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
@TableName("tb_role")
public class Role {

    @TableId(type = IdType.AUTO)
    private Integer roleId;

    private String roleName; //角色名称

    private String rightIds; //权限编号字符串

    private String roleDesc; //角色描述

    @TableField(exist = false)
    private List<Right> children = new ArrayList<>(); //权限编号列表

    @TableField(exist = false)
    private List<String> rightUrlList; //操作路径列表





}
