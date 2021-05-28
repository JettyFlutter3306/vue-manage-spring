package cn.element.controller;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.pojo.Right;
import cn.element.pojo.Role;
import cn.element.service.RightService;
import cn.element.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RightService rightService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色名称及id
     */
    @GetMapping("/roleName")
    public ResultInfo getRoleNameList(){

        List<Role> roleList = roleService.getRoleNameListAndId();

        return ResultInfo.ok(Constant.SELECT_SUCCESS,roleList);
    }

    /**
     * 获取角色列表
     */
    @PreAuthorize("hasAuthority('role:select')")
    @GetMapping
    public ResultInfo getRoleList(){

        List<Role> roleList = roleService.getRoleList();

        if(CollectionUtils.isEmpty(roleList)){
            return ResultInfo.notFound();
        }

        for (Role role : roleList) {
            role.setChildren(rightService.getRightListAsTree(role.getRoleId()));
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

    @DeleteMapping("/{rightId}")
    public ResultInfo deleteRightById(@PathVariable("rightId") Integer rightId,
                                      @RequestParam("roleId") Integer roleId){

        boolean b = roleService.deleteRightById(roleId, rightId);

        if(b){
            List<Right> rightTreeList = rightService.getRightListAsTree(roleId);

            return ResultInfo.ok(Constant.DELETE_SUCCESS,rightTreeList);
        }

        return ResultInfo.serverError(Constant.DELETE_FAILED);
    }

    @PostMapping("/{roleId}")
    public ResultInfo updateRightsByRoleId(@PathVariable("roleId") Integer roleId,
                                           @RequestParam("rids") String rids){

        boolean b = roleService.updateRightsByRoleId(roleId, rids);

        if(b){
            return ResultInfo.ok(Constant.ALLOT_RIGHTS_SUCCESS);
        }

        return ResultInfo.serverError(Constant.ALLOT_RIGHTS_FAILED);
    }




}
