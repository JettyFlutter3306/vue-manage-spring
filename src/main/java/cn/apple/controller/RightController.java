package cn.apple.controller;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.pojo.Right;
import cn.apple.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @GetMapping
    public ResultInfo getRightList(){

        List<Right> rightList = rightService.getRightList();

        return ResultInfo.ok(Constant.SELECT_SUCCESS,rightList);
    }

    /**
     * 获取菜单列表
     */
    @GetMapping("/menus")
    public ResultInfo getMenuList(){

        List<Right> rightList = rightService.getRightListAsTree();

        return ResultInfo.ok(Constant.SELECT_SUCCESS,rightList);
    }

}
