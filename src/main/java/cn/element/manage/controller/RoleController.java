package cn.element.manage.controller;

import cn.element.manage.common.Constant;
import cn.element.manage.common.ResultInfo;
import cn.element.manage.service.RightService;
import cn.element.manage.pojo.permission.Right;
import cn.element.manage.pojo.permission.Role;
import cn.element.manage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.element.manage.common.Constant.*;

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
    @PreAuthorize("hasAuthority('role:select')")
    @GetMapping("/roleName")
    public ResultInfo getRoleNameList() {
        List<Role> roleList = roleService.getRoleNameListAndId();

        return ResultInfo.ok(SELECT_SUCCESS, roleList);
    }

    /**
     * 获取角色列表
     */
    @PreAuthorize("hasAuthority('role:select')")
    @GetMapping
    public ResultInfo getRoleList() {
        List<Role> roleList = roleService.getRoleList();

        if (CollectionUtils.isEmpty(roleList)) {
            return ResultInfo.notFound();
        }

        return ResultInfo.ok(SELECT_SUCCESS,roleList);
    }

    @PreAuthorize("hasAuthority({'role:select', 'right:select'})")
    @GetMapping("/rights/{id}")
    public ResultInfo getRightListByRoleId(@PathVariable("id") Integer roleId) {
        List<Right> list = rightService.getRightListAsTree(roleId);

        if (CollectionUtils.isEmpty(list)) {
            return ResultInfo.notFound(Constant.SELECT_FAILED);
        }

        return ResultInfo.ok(SELECT_SUCCESS, list);
    }

    @PreAuthorize("hasAuthority({'role:update'})")
    @PostMapping("/{roleId}")
    public ResultInfo updateRightsByRoleId(@PathVariable("roleId") Integer roleId,
                                           @RequestBody List<Integer> rids) {
        boolean b = roleService.updateRightsByRoleId(roleId, rids);

        if (b) {
            return ResultInfo.ok(ALLOT_RIGHTS_SUCCESS);
        }

        return ResultInfo.serverError(ALLOT_RIGHTS_FAILED);
    }

    @PreAuthorize("hasAuthority('role:select')")
    @GetMapping("/user/{uid}")
    public ResultInfo getRolesByUserId(@PathVariable("uid") Integer uid) {
        List<Role> roleList = roleService.getRolesByUserId(uid);

        return ResultInfo.ok(SELECT_SUCCESS, roleList);
    }

    @PreAuthorize("hasAuthority('role:update')")
    @PostMapping("/allot/{uid}")
    public ResultInfo updateRolesByUserId(@PathVariable("uid") Integer uid,
                                          @RequestBody List<Integer> roleIdList) {
        boolean b = roleService.updateRolesByUserId(uid, roleIdList);

        if (b) {
            return ResultInfo.ok(ALLOT_ROLE_SUCCESS);
        }

        return ResultInfo.serverError(SYSTEM_ERROR);
    }







}
