package cn.element.controller;

import cn.element.common.ResultInfo;
import cn.element.pojo.User;
import cn.element.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static cn.element.common.Constant.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('user:select')")
    @GetMapping
    public ResultInfo getUserList(@RequestParam(value = "query",defaultValue = "") String keyword,
                                  @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize) {
        Page<User> userPage = userService.getUserList(keyword, pageNum, pageSize);

        return ResultInfo.ok(SELECT_SUCCESS, userPage);
    }

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     */
    @PreAuthorize("hasAuthority('user:select')")
    @GetMapping("/{id}")
    public ResultInfo getUserById(@PathVariable("id") Integer id) {
        User user = userService.selectUserById(id);

        if (user != null) {
            return ResultInfo.ok(SELECT_SUCCESS, user);
        }

        return ResultInfo.notFound(SYSTEM_ERROR);
    }

    @PreAuthorize("hasAuthority('user:insert')")
    @PostMapping
    public ResultInfo addUser(@RequestBody User user) {
        boolean b = userService.addUser(user);

        if (b) {
           return ResultInfo.created(INSERT_USER_SUCCESS);
        }

        return ResultInfo.serverError(SYSTEM_ERROR);
    }

    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResultInfo deleteUser(@PathVariable("id") Integer id) {
        boolean b = userService.deleteUserById(id);

        if (b) {
            return ResultInfo.ok(DELETE_USER_SUCCESS);
        }

        return ResultInfo.serverError(SYSTEM_ERROR);
    }

    @PreAuthorize("hasAuthority('user:update')")
    @PutMapping
    public ResultInfo editUserStatus(@RequestParam("id") Integer id,
                                     @RequestParam("status") Integer status) {
        boolean b = userService.editUserStatus(id,status);

        if (b) {
            return ResultInfo.ok(UPDATE_USER_SUCCESS);
        }

        return ResultInfo.serverError(SYSTEM_ERROR);
    }

    @PreAuthorize("hasAuthority('user:update')")
    @PutMapping("/update")
    public ResultInfo editUserInfo(@RequestBody User user) {
        boolean b = userService.editUserInfoById(user);

        if (b) {
            return ResultInfo.ok(UPDATE_USER_SUCCESS);
        }

        return ResultInfo.serverError(SYSTEM_ERROR);
    }

    /**
     * 根据用户id和传过来的角色id分配角色
     */
    @PreAuthorize("hasAuthority({'user:update','role:update'})")
    @PutMapping("/{id}/role")
    public ResultInfo allotRoleByUserId(@PathVariable("id") Integer userId,
                                        @RequestParam("rid") Integer rid) {
        boolean b = userService.allotRoleByUserId(userId, rid);

        if (b) {
            return ResultInfo.ok(ALLOT_ROLE_SUCCESS);
        }

        return ResultInfo.serverError(ALLOT_ROLE_FAILED);
    }







}
