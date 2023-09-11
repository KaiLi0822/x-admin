package com.example.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.Result;
import com.example.sys.entity.Role;
import com.example.sys.entity.User;
import com.example.sys.service.IRoleService;
import com.example.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = {"Role Interface List"})
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @GetMapping("/list")
    public Result<Map<String, Object>> getRoleList(@RequestParam(value = "roleName",required = false) String roleName,
                                                   @RequestParam(value = "pageNo") Long pageNo,
                                                   @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Role> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(StringUtils.hasLength(roleName), Role::getRoleName, roleName);
        objectLambdaQueryWrapper.orderByDesc(Role::getRoleId);

        Page<Role> page = new Page<>(pageNo, pageSize);
        roleService.page(page, objectLambdaQueryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addRole(@RequestBody Role role){
        roleService.save(role);
        return Result.success("Add Role Successfully");

    }

    @PutMapping
    public Result<?> updateRole(@RequestBody Role role){
        roleService.updateById(role);
        return Result.success("Update Role Successfully");

    }


    @GetMapping("/{id}")
    public Result<Role> getRoleById(@PathVariable("id") Integer id){
        Role byId = roleService.getById(id);
        return Result.success(byId);
    }

    @DeleteMapping("/{id}")
    public Result<Role> deleteRoleById(@PathVariable("id") Integer id){
        roleService.removeById(id);
        return Result.success("Delete Role Successfully");
    }


}
