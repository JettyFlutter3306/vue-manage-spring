package cn.element.manage.pojo.permission;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@TableName("tb_role")
public class Role {

    @TableId
    private Integer roleId;

    private String roleName; //角色名称

    private String roleDesc; //角色描述

    private String roleIdentity;  //角色标识

    @TableField(exist = false)
    private List<Right> children; //权限列表

    @TableField(exist = false)
    private List<String> rightUrlList; //操作路径列表
}
