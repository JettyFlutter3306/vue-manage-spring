package cn.apple.controller;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.pojo.User;
import cn.apple.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResultInfo getUserList(@RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize){

        Page<User> userPage = userService.getUserList(pageNum, pageSize);

        return ResultInfo.ok(Constant.SELECT_SUCCESS,userPage);
    }

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     */
    @GetMapping("/{id}")
    public ResultInfo getUserById(@PathVariable("id") Integer id){

        User user = userService.selectUserById(id);

        if(user != null){
            return ResultInfo.ok(Constant.SELECT_SUCCESS,user);
        }

        return ResultInfo.notFound(Constant.SYSTEM_ERROR);
    }


    @PostMapping
    public ResultInfo addUser(@RequestBody User user){

        boolean b = userService.addUser(user);

        if(b){
           return ResultInfo.created(Constant.INSERT_USER_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResultInfo deleteUser(@PathVariable("id") Integer id){

        boolean b = userService.deleteUserById(id);

        if(b){
            return ResultInfo.ok(Constant.DELETE_USER_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    @PutMapping
    public ResultInfo editUserStatus(@RequestParam("id") Integer id,
                                     @RequestParam("status") Integer status){

        boolean b = userService.editUserStatus(id,status);

        if(b){
            return ResultInfo.ok(Constant.UPDATE_USER_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }





}
