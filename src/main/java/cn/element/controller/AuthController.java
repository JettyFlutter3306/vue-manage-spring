package cn.element.controller;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.pojo.Right;
import cn.element.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/right")
public class AuthController{

    @Autowired
    private RightService rightService;

    /***
     * 获取全部权限
     */
    @PreAuthorize("hasAuthority('right:select')")
    @GetMapping
    public ResultInfo getRightList(@RequestParam(value = "tree",defaultValue = "") String tree) {
        if (StringUtils.isEmpty(tree)) {
            List<Right> rightList = rightService.getRightList();

            return ResultInfo.ok(Constant.SELECT_SUCCESS,rightList);
        }

        List<Right> rightTree = rightService.getRightListAsTree();

        return ResultInfo.ok(Constant.SELECT_SUCCESS,rightTree);
    }

    /**
     * 获取菜单列表
     */
    @PreAuthorize("hasAuthority('right:select')")
    @GetMapping("/menus")
    public ResultInfo getMenuList() {
        List<Right> rightList = rightService.getRightListAsTree();

        return ResultInfo.ok(Constant.SELECT_SUCCESS,rightList);
    }

    @PreAuthorize("hasAnyRole('超级管理员','管理员')")
    @GetMapping("/menus/{uid}")
    public ResultInfo getRightListById(@RequestParam(value = "tree",defaultValue = "") String tree,
                                       @PathVariable("uid") Integer uid) {
        if (StringUtils.isEmpty(tree)) {
            List<Right> rightList = rightService.getRightList();

            return ResultInfo.ok(Constant.SELECT_SUCCESS, rightList);
        }

        List<Right> rightTree = rightService.getRightListAsTreeByUid(uid);

        return ResultInfo.ok(Constant.SELECT_SUCCESS,rightTree);
    }

}
