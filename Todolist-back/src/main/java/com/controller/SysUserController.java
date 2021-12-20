
package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.lang.Const;
import com.common.lang.Result;
import com.entity.SysUser;
import com.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2021-12-19
 */

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;



    @PostMapping("/save")
    public Result save(@Validated @RequestBody SysUser sysUser) {
        // 默认密码
       // String password = passwordEncoder.encode(Const.DEFULT_PASSWORD);
        //sysUser.setPassword(password);

        // 默认头像
        sysUser.setAvatar(Const.DEFULT_AVATAR);

        sysUserService.save(sysUser);
        return Result.succ(sysUser);
    }

    @PostMapping("/update")
    public Result update(@Validated @RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
        return Result.succ(sysUser);
    }
    //更新密码
}

