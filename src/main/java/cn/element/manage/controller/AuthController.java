package cn.element.manage.controller;

import cn.element.manage.common.Constant;
import cn.element.manage.common.ResultInfo;
import cn.element.manage.pojo.permission.Right;
import cn.element.manage.service.RightService;
import cn.element.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/right")
public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private RightService rightService;
    
    @Autowired
    private RestTemplate restTemplate;

    /***
     * 获取全部权限
     */
    @PreAuthorize("hasAuthority('right:select')")
    @GetMapping
    public ResultInfo getRightList(@RequestParam(value = "tree", defaultValue = "") String tree) {
        if (StringUtils.isEmpty(tree)) {
            List<Right> rightList = rightService.getRightList();

            return ResultInfo.ok(Constant.SELECT_SUCCESS, rightList);
        }

        List<Right> rightTree = rightService.getRightListAsTree();

        return ResultInfo.ok(Constant.SELECT_SUCCESS, rightTree);
    }

    /**
     * 获取菜单列表
     */
    @PreAuthorize("hasAuthority('right:select')")
    @GetMapping("/menus")
    public ResultInfo getMenuList() {
        List<Right> rightList = rightService.getRightListAsTree();

        return ResultInfo.ok(Constant.SELECT_SUCCESS, rightList);
    }

    @PreAuthorize("hasAnyRole('超级管理员', '管理员')")
    @GetMapping("/menus/{uid}")
    public ResultInfo getRightListById(@RequestParam(value = "tree", defaultValue = "") String tree,
                                       @PathVariable("uid") Integer uid) {
        if (StringUtils.isEmpty(tree)) {
            List<Right> rightList = rightService.getRightList();

            return ResultInfo.ok(Constant.SELECT_SUCCESS, rightList);
        }

        List<Right> rightTree = rightService.getRightListAsTreeByUid(uid);

        return ResultInfo.ok(Constant.SELECT_SUCCESS, rightTree);
    }
    
    @GetMapping("/token")
    public ResultInfo getToken(@RequestParam("username") String username,
                               @RequestParam("password") String password) {
        String url = "http://localhost:8081/userLogin";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        return restTemplate.postForObject(url, map, ResultInfo.class);
    }

}
