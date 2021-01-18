package cn.apple.controller;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.pojo.Right;
import cn.apple.service.RightService;
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

    @GetMapping("/{id}")
    public ResultInfo getRightListByRoleId(@PathVariable("id") Integer roleId){

        List<Right> list = rightService.getRightListAsTree(roleId);

        if(CollectionUtils.isEmpty(list)){
            return ResultInfo.notFound(Constant.SELECT_FAILED);
        }

        return ResultInfo.ok(Constant.SELECT_SUCCESS,list);
    }
}
