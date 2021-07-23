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

        return ResultInfo.ok(Constant.SELECT_SUCCESS,roleList);
    }

    @GetMapping("/rights/{id}")
    public ResultInfo getRightListByRoleId(@PathVariable("id") Integer roleId){

        List<Right> list = rightService.getRightListAsTree(roleId);

        if(CollectionUtils.isEmpty(list)){
            return ResultInfo.notFound(Constant.SELECT_FAILED);
        }

        return ResultInfo.ok(Constant.SELECT_SUCCESS,list);
    }

    @PostMapping("/{roleId}")
    public ResultInfo updateRightsByRoleId(@PathVariable("roleId") Integer roleId,
                                           @RequestBody List<Integer> rids){

        boolean b = roleService.updateRightsByRoleId(roleId, rids);

        if(b){
            return ResultInfo.ok(Constant.ALLOT_RIGHTS_SUCCESS);
        }

        return ResultInfo.serverError(Constant.ALLOT_RIGHTS_FAILED);
    }




}
