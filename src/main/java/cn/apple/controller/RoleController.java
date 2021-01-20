package cn.apple.controller;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.pojo.Right;
import cn.apple.pojo.Role;
import cn.apple.service.RightService;
import cn.apple.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RightService rightService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色列表
     */
    @GetMapping
    public ResultInfo getRoleList(){

        List<Role> roleList = roleService.getRoleList();

        if(CollectionUtils.isEmpty(roleList)){
            return ResultInfo.notFound();
        }

        for (Role role : roleList) {
            role.setChildren(rightService.getRightListAsTree(role.getRoleId()));

            System.out.println(role);
        }

        return ResultInfo.ok(Constant.SELECT_SUCCESS,roleList);
    }

    @GetMapping("/{id}")
    public ResultInfo getRightListByRoleId(@PathVariable("id") Integer roleId){

        List<Right> list = rightService.getRightListAsTree(roleId);

        if(CollectionUtils.isEmpty(list)){
            return ResultInfo.notFound(Constant.SELECT_FAILED);
        }

        return ResultInfo.ok(Constant.SELECT_SUCCESS,list);
    }


}
