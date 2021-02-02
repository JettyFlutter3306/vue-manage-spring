package cn.apple.controller;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.pojo.User;
import cn.apple.service.UserService;
import cn.apple.util.JwtUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PutMapping("/{id}")
    public ResultInfo editUserInfo(@PathVariable("id") Integer id,
                                   @RequestParam("email") String email,
                                   @RequestParam("mobile") String mobile){
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setMobile(mobile);

        boolean b = userService.editUserInfoById(user);

        if(b){
            return ResultInfo.ok(Constant.UPDATE_USER_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    /**
     * .模糊查询
     * @param keyword   关键字
     * @param pageNum   当前页码
     * @param pageSize  页面大小
     */
    @GetMapping("/fuzzySearch")
    public ResultInfo fuzzySearch(@RequestParam("query") String keyword,
                                  @RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize){

        Page<User> page = userService.fuzzySearchUsers(keyword, pageNum, pageSize);

        if(page != null){
            return ResultInfo.ok(Constant.SELECT_FAILED,page);
        }

        return ResultInfo.notFound(Constant.SYSTEM_ERROR);
    }


    @PostMapping("/login")
    public ResultInfo login(@RequestParam("username") String username,
                            @RequestParam("password") String password){

        boolean b = userService.checkLogin(username, password);

        if(b){
            Map<String,String> map = new HashMap<>();
            map.put("username",username);
            map.put("password",password);

            String token = JwtUtils.getToken(map);

            return ResultInfo.ok(Constant.LOGIN_SUCCESS,token);
        }

        return ResultInfo.notFound(Constant.LOGIN_FAILED);
    }

    /**
     * 根据用户id和传过来的角色id分配角色
     */
    @PutMapping("/{id}/role")
    public ResultInfo allotRoleByUserId(@PathVariable("id") Integer userId,
                                        @RequestParam("rid") Integer rid){

        boolean b = userService.allotRoleByUserId(userId, rid);

        if(b){
            return ResultInfo.ok(Constant.ALLOT_ROLE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.ALLOT_ROLE_FAILED);
    }







}
