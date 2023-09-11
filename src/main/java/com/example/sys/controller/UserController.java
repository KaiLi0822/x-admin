package com.example.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.vo.Result;
import com.example.sys.entity.User;
import com.example.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kai
 * @since 2023-08-24
 */
@Api(tags = {"User Interface List"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("User List")
    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> list = userService.list();
        return Result.success(list);
    }

    @ApiOperation("User Login")
    @PostMapping ("/login")
    public Result<Map<String, Object>> login(@RequestBody User user){
//        List<User> list = userService.list();
        Map<String, Object> data = userService.login(user);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20002,"Wrong Username or Password.");
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token){
        // 根据Jwt验证用户信息
        Map<String, Object> data = userService.getUserInfo(token);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20003,"Login Info is Invalid, Please Login Again.");

    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                               @RequestParam(value = "phone", required = false) String phone,
                                               @RequestParam(value = "pageNo") Long pageNo,
                                               @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<User> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(StringUtils.hasLength(username), User::getUsername, username);
        objectLambdaQueryWrapper.eq(StringUtils.hasLength(phone), User::getPhone, phone);
        objectLambdaQueryWrapper.orderByDesc(User::getId);

        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, objectLambdaQueryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return Result.success("Add User Successfully");

    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user){
        user.setPassword(null);
        userService.updateById(user);
        return Result.success("Update User Successfully");

    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User byId = userService.getById(id);
        return Result.success(byId);
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
        userService.removeById(id);
        return Result.success("Delete User Successfully");
    }




}
