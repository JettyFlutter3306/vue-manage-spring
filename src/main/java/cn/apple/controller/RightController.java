package cn.apple.controller;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.pojo.Right;
import cn.apple.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/right")
public class RightController {

    @Autowired
    private RightService rightService;

    /***
     * 获取全部权限
     */
    @PreAuthorize("hasAnyRole('超级管理员','管理员')")
    @GetMapping
    public ResultInfo getRightList(@RequestParam(value = "tree",defaultValue = "") String tree){

        if(StringUtils.isEmpty(tree)){
            List<Right> rightList = rightService.getRightList();

            return ResultInfo.ok(Constant.SELECT_SUCCESS,rightList);
        }

        List<Right> rightTree = rightService.getRightListAsTree();

        return ResultInfo.ok(Constant.SELECT_SUCCESS,rightTree);
    }

    /**
     * 获取菜单列表
     */
    @PreAuthorize("hasAnyRole('超级管理员','管理员')")
    @GetMapping("/menus")
    public ResultInfo getMenuList(){

        List<Right> rightList = rightService.getRightListAsTree();

        return ResultInfo.ok(Constant.SELECT_SUCCESS,rightList);
    }


}
